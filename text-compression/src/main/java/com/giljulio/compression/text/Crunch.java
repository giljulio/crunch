package com.giljulio.compression.text;

import com.giljulio.compression.text.reader.CrunchReader;
import com.giljulio.compression.text.writer.CrunchWriter;

public final class Crunch {

    final int bufferSize;
    final int minimumCharacterReferenceSize;

    private Crunch(int bufferSize, int minimumCharacterReferenceSize) {
        this.bufferSize = bufferSize;
        this.minimumCharacterReferenceSize = minimumCharacterReferenceSize;
    }

    public <T> T compress(CrunchReader source, CrunchWriter<T> destination) {
        Compressor<T> compressor = new Compressor<>(this, source, destination);
        compressor.compress();
        return destination.output();
    }

    public final static class Builder {

        private int bufferSize = 256;
        private int minimumCharacterReferenceSize = 3;

        public Builder searchCharacterBufferSize(int size) {
            if (size < 0) {
                throw new IllegalArgumentException("SearchCharacterBufferSize must be >= 0");
            }
            this.bufferSize = size;
            return this;
        }

        public Builder minimumCharacterReferenceSize(int size) {
            if (size < 0) {
                throw new IllegalArgumentException("MinimumCharacterReferenceSize must be >= 0");
            }
            this.minimumCharacterReferenceSize = size;
            return this;
        }

        public Crunch build() {
            return new Crunch(bufferSize, minimumCharacterReferenceSize);
        }
    }
}
