package com.giljulio.compression.text;

import com.giljulio.compression.text.reader.CrunchReader;
import com.giljulio.compression.text.writer.CrunchWriter;

import java.util.ArrayList;

final class Compressor<T> {

    private final Crunch crunch;
    private final CrunchReader source;
    private final CrunchWriter<T> destination;

    private int readerIndex = 0;

    Compressor(Crunch crunch, CrunchReader source, CrunchWriter<T> destination) {
        this.crunch = crunch;
        this.source = source;
        this.destination = destination;
    }

    void compress() {
        if (readerIndex != 0) {
            throw new IllegalStateException("Compressor.compress() can only be executed once.");
        }

        char[] searchBuffer = new char[crunch.bufferSize];
        int character;
        ArrayList<Integer> characters = new ArrayList<>();
        while ((character = getNextCharacter()) != -1) {
            characters.add(character);


        }
    }

    private int getNextCharacter() {
        readerIndex++;
        return source.read();
    }
}
