package com.giljulio.compression.text;

import com.giljulio.compression.text.reader.CrunchReader;
import com.giljulio.compression.text.writer.CrunchWriter;

import java.util.ArrayList;
import java.util.LinkedList;

final class Compressor<T> {

    private final Crunch crunch;
    private final CrunchReader reader;
    private final CrunchWriter<T> writer;

    private int readerCount = 0;
    private final ArrayList<Character> characters = new ArrayList<>();
    private final LinkedList<Character> readerBuffer = new LinkedList<>();

    Compressor(Crunch crunch, CrunchReader reader, CrunchWriter<T> writer) {
        this.crunch = crunch;
        this.reader = reader;
        this.writer = writer;
    }

    //''she sells sea shells on the sea shore''
    void execute() {
        if (readerCount != 0) {
            throw new IllegalStateException(".compress() must only be executed once.");
        }

        char character;
        int currentIndex = 0;
        while ((character = getCharacterAt(currentIndex)) != '\u0000') {
            int maxStartIndex = -1;
            int maxLength = 0;
            for (int i = 0; i < currentIndex - crunch.minimumCharacterReferenceSize; i++) {
                int length = calculateMax(i, currentIndex);
                if (length > maxLength && length >= crunch.minimumCharacterReferenceSize) {
                    maxStartIndex = i;
                    maxLength = length;
                }
            }

            if (maxStartIndex == -1) {
                writer.writeCharacter(character);
                currentIndex++;
                readerBuffer.removeFirst();
            } else {
                int offset = maxStartIndex - currentIndex;
                writer.writeReference(offset, maxLength);
                currentIndex += maxLength;
                for (int i = 0; i < maxLength; i++) {
                    readerBuffer.removeFirst();
                }
            }
        }
    }

    private int calculateMax(int searchIndex, int currentIndex) {
        int counter = 0;
        while (characters.size() > searchIndex &&
                characters.get(searchIndex++) == getCharacterAt(currentIndex++)) {
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
            characters.add(character);
            readerBuffer.add(character);
            if (index == readerCount++) {
                return character;
            }
        }
    }
}
