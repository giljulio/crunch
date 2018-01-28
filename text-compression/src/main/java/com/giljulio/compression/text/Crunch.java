package com.giljulio.compression.text;

import com.giljulio.compression.text.compressor.reader.CompressorReader;
import com.giljulio.compression.text.compressor.writer.CompressorWriter;

public final class Crunch {

    final int bufferSize;
    final int minRefSize;

    private Crunch(int bufferSize, int minRefSize) {
        this.bufferSize = bufferSize;
        this.minRefSize = minRefSize;
    }

    public <T> T compress(CompressorReader source, CompressorWriter<T> destination) {
        Compressor<T> compressor = new Compressor<>(this, source, destination);
        compressor.execute();
        return destination.output();
    }

    public final static class Builder {

        private int searchCharacterBufferSize = 256;
        private int minimumCharacterReferenceLength = 3;

        public Builder searchCharacterBufferSize(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("SearchCharacterBufferSize must be > 0");
            }
            this.searchCharacterBufferSize = size;
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
            if (searchCharacterBufferSize < minimumCharacterReferenceLength) {
                throw new IllegalArgumentException("SearchCharacterBufferSize " +
                        "(" + searchCharacterBufferSize + ") must be equal or " +
                        "larger than MinimumCharacterReferenceLength " +
                        "( " + minimumCharacterReferenceLength + ")");
            }

            return new Crunch(searchCharacterBufferSize, minimumCharacterReferenceLength);
        }
    }
}
