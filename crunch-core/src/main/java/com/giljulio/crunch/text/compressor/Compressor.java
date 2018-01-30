package com.giljulio.crunch.text.compressor;

import com.giljulio.crunch.text.CircularQueue;
import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.CompressorReader;
import com.giljulio.crunch.text.compressor.writer.CompressorWriter;

import java.util.LinkedList;

public final class Compressor<T> {

    private final Crunch crunch;
    private final CompressorReader reader;
    private final CompressorWriter<T> writer;

    private final CircularQueue<Character> searchBuffer;
    private final LinkedList<Character> readerBuffer = new LinkedList<>();

    private int readerCount = 0;

    public Compressor(Crunch crunch, CompressorReader reader, CompressorWriter<T> writer) {
        this.crunch = crunch;
        this.reader = reader;
        this.writer = writer;
        this.searchBuffer = new CircularQueue<>(crunch.getBufferSize());
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

            // Search for largest match
            for (int i = 0; i < searchBuffer.size() - crunch.getMinRefSize(); i++) {
                int length = calculateMax(i, currentIndex);
                if (length > maxLength && length >= crunch.getMinRefSize()) {
                    maxStartIndex = currentIndex + (i - searchBuffer.size());
                    maxLength = length;
                }
            }

            trimSearchBuffer(maxLength);

            // write character or reference
            if (maxStartIndex == -1) {
                writer.writeCharacter(character);
                currentIndex++;
                readerBuffer.removeFirst();
                searchBuffer.add(character);
            } else {
                int offset = maxStartIndex - currentIndex;
                writer.writeReference((short) offset, maxLength);
                currentIndex += maxLength;
                for (int i = 0; i < maxLength; i++) {
                    Character firstCharacter = readerBuffer.removeFirst();
                    searchBuffer.add(firstCharacter);
                }
            }
        }

        reader.close();
        return writer.output();
    }

    private int calculateMax(int searchIndex, int currentIndex) {
        int counter = 0;
        while (searchBuffer.size() > searchIndex &&
                searchBuffer.get(searchIndex++) == getCharacterAt(currentIndex++) &&
                !isMaxReferenceLength(counter)) {
            counter++;
        }
        return counter;
    }

    private boolean isMaxReferenceLength(int length) {
        return length >= writer.maxReferenceLength() && writer.maxReferenceLength() != -1;
    }

    private char getCharacterAt(int index) {
        boolean inReadBuffer = readerCount - readerBuffer.size() <= index && readerCount > index;
        if (inReadBuffer) {
            int readerIndex = index - readerCount + readerBuffer.size();
            return readerBuffer.get(readerIndex);
        }
        while (true) {
            char character = reader.read();
            readerBuffer.add(character);
            if (index == readerCount++) {
                return character;
            }
        }
    }

    private void trimSearchBuffer(int maxLength) {
        while (crunch.getBufferSize() < searchBuffer.size() + Math.max(1, maxLength)) {
            searchBuffer.removeLast();
        }
    }
}
