package com.giljulio.compression.text;

import com.giljulio.compression.text.reader.CrunchReader;
import com.giljulio.compression.text.writer.CrunchWriter;

import java.io.File;

public final class CrunchText {

    private final int bufferSize;

    private CrunchText(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void compress(CrunchReader source, CrunchWriter destination) {
        char[] buffer = new char[bufferSize];
        int charater;
        int index = 0;
        while ((charater = source.read()) != -1) {
            index++;
        }
    }

    public void decompress(File file, File destination) {

    }

    public final static class Builder {

        private int bufferSize = 256;

        public void searchCharacterBufferSize(int size) {
            if (size < 0) {
                throw new IllegalArgumentException("searchCharacterBufferSize must be >= 0");
            }
            this.bufferSize = size;
        }

        public CrunchText build() {
            return new CrunchText(bufferSize);
        }
    }
}
