package com.giljulio.crunch.text.compressor;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.CompressorReader;
import com.giljulio.crunch.text.compressor.writer.CompressorWriter;

import java.util.LinkedList;

public final class Compressor<T> {

    private final Crunch crunch;
    private final CompressorReader reader;
    private final CompressorWriter<T> writer;

    private final LinkedList<Character> characterBuffer = new LinkedList<>();
    private final LinkedList<Character> readerBuffer = new LinkedList<>();

    private int readerCount = 0;
    private int characterBufferStartIndex = 0;

    public Compressor(Crunch crunch, CompressorReader reader, CompressorWriter<T> writer) {
        this.crunch = crunch;
        this.reader = reader;
        this.writer = writer;
    }

    public T execute() {
        if (readerCount != 0) {
            throw new IllegalStateException(".compress() must only be executed once.");
        }

        char character;
        int currentIndex = 0;
        while ((character = getCharacterAt(currentIndex)) != '\u0000') {
            int maxStartIndex = -1;
            int maxLength = 0;

            int relativeStartIndex = Math.max(currentIndex - crunch.getBufferSize(), 0);
            int relativeEndIndex = currentIndex - crunch.getMinRefSize();
            for (int i = relativeStartIndex; i < relativeEndIndex; i++) {
                int absoluteSearchIndex = i - characterBufferStartIndex;
                int length = calculateMax(absoluteSearchIndex, currentIndex);
                if (length > maxLength && length >= crunch.getMinRefSize()) {
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
                if (offset > Short.MAX_VALUE) {
                    throw new IllegalStateException("Offset " + offset + " larger than " + Short.MAX_VALUE);
                }
                writer.writeReference((short) offset, maxLength);
                currentIndex += maxLength;
                for (int i = 0; i < maxLength; i++) {
                    Character firstCharacter = readerBuffer.removeFirst();
                    characterBuffer.add(firstCharacter);
                }
            }
            cleanCharacterBuffer();
        }

        reader.close();
        return writer.output();
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
        while (characterBuffer.size() > crunch.getBufferSize()) {
            characterBuffer.removeFirst();
            characterBufferStartIndex++;
        }
    }
}
