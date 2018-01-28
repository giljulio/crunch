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
        compressor.execute();
        return destination.output();
    }

    public final static class Builder {

        private int bufferSize = 256;
        private int minimumCharacterReferenceLength = 3;

        public Builder searchCharacterBufferSize(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("SearchCharacterBufferSize must be > 0");
            }
            this.bufferSize = size;
            return this;
        }

        public Builder minimumCharacterReferenceLength(int length) {
            if (length <= 0) {
                throw new IllegalArgumentException("MinimumCharacterReferenceLength must be > 0");
            }
            this.minimumCharacterReferenceLength = length;
            return this;
        }

        public Crunch build() {
            if (bufferSize < minimumCharacterReferenceLength) {
                throw new IllegalArgumentException("SearchCharacterBufferSize (" + bufferSize + ") must be " +
                        "larger than MinimumCharacterReferenceLength ( " + minimumCharacterReferenceLength + ")");
            }
            return new Crunch(bufferSize, minimumCharacterReferenceLength);
        }
    }
}
