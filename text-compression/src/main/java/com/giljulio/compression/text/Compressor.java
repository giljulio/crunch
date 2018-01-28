package com.giljulio.compression.text;

import com.giljulio.compression.text.compressor.reader.CompressorReader;
import com.giljulio.compression.text.compressor.writer.CompressorWriter;

import java.util.LinkedList;

final class Compressor<T> {

    private final Crunch crunch;
    private final CompressorReader reader;
    private final CompressorWriter<T> writer;

    private final LinkedList<Character> characterBuffer = new LinkedList<>();
    private final LinkedList<Character> readerBuffer = new LinkedList<>();

    private int readerCount = 0;
    private int characterBufferStartIndex = 0;

    Compressor(Crunch crunch, CompressorReader reader, CompressorWriter<T> writer) {
        this.crunch = crunch;
        this.reader = reader;
        this.writer = writer;
    }

    void execute() {
        if (readerCount != 0) {
            throw new IllegalStateException(".compress() must only be executed once.");
        }

        char character;
        int currentIndex = 0;
        while ((character = getCharacterAt(currentIndex)) != '\u0000') {
            int maxStartIndex = -1;
            int maxLength = 0;

            int relativeStartIndex = Math.max(currentIndex - crunch.bufferSize, 0);
            int relativeEndIndex = currentIndex - crunch.minRefSize;
            for (int i = relativeStartIndex; i < relativeEndIndex; i++) {
                int absoluteSearchIndex = i - characterBufferStartIndex;
                int length = calculateMax(absoluteSearchIndex, currentIndex);
                if (length > maxLength && length >= crunch.minRefSize) {
                    maxStartIndex = i;
                    maxLength = length;
                }
            }

            if (maxStartIndex == -1) {
                writer.writeCharacter(character);
                currentIndex++;
                readerBuffer.removeFirst();
                characterBuffer.add(character);
            } else {
                int offset = maxStartIndex - currentIndex;
                writer.writeReference(offset, maxLength);
                currentIndex += maxLength;
                for (int i = 0; i < maxLength; i++) {
                    Character firstCharacter = readerBuffer.removeFirst();
                    characterBuffer.add(firstCharacter);
                }
            }
            cleanCharacterBuffer();
        }
    }

    private int calculateMax(int searchIndex, int currentIndex) {
        int counter = 0;
        while (characterBuffer.size() > searchIndex &&
                characterBuffer.get(searchIndex++) == getCharacterAt(currentIndex++)) {
            // TODO: characterBuffer.get(searchIndex) is very inefficient. Replace with custom circular buffer
            counter++;
        }
        return counter;
    }

    private char getCharacterAt(int index) {
        if (readerCount - readerBuffer.size() <= index && readerCount > index) {
            return readerBuffer.get(index - readerCount + readerBuffer.size());
        }
        while (true) {
            char character = reader.read();
            readerBuffer.add(character);
            if (index == readerCount++) {
                return character;
            }
        }
    }

    private void cleanCharacterBuffer() {
        while (characterBuffer.size() > crunch.bufferSize) {
            characterBuffer.removeFirst();
            characterBufferStartIndex++;
        }
    }
}
