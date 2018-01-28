package com.giljulio.compression.text;

import com.giljulio.compression.text.compressor.reader.CompressorReader;
import com.giljulio.compression.text.compressor.reader.PlainTextFileReader;
import com.giljulio.compression.text.compressor.reader.StringReader;
import com.giljulio.compression.text.compressor.writer.BinaryFileWriter;
import com.giljulio.compression.text.compressor.writer.CompressorWriter;

import java.io.File;
import java.io.FileNotFoundException;

public final class Crunch {

    final int bufferSize;
    final int minRefSize;

    private Crunch(int bufferSize, int minRefSize) {
        this.bufferSize = bufferSize;
        this.minRefSize = minRefSize;
    }

    public <T> T compress(CompressorReader source, CompressorWriter<T> destination) {
        return new Compressor<>(this, source, destination).execute();
    }

    public File compress(String text, File destination) throws FileNotFoundException {
        StringReader reader = new StringReader(text);
        BinaryFileWriter writer = new BinaryFileWriter(destination);
        return compress(reader, writer);
    }

    public File compress(File source, File destination) throws FileNotFoundException {
        PlainTextFileReader reader = new PlainTextFileReader(source);
        BinaryFileWriter writer = new BinaryFileWriter(destination);
        return compress(reader, writer);
    }

    public final static class Builder {

        private int searchCharacterBufferSize = 256;
        private int minimumCharacterReferenceLength = 3;

        public Builder searchCharacterBufferSize(int size) {
            if (size < 3) {
                throw new IllegalArgumentException("SearchCharacterBufferSize must be at least 3");
            }
            if (size > Short.MAX_VALUE) {
                throw new IllegalArgumentException("SearchCharacterBufferSize must be less than " + Short.MAX_VALUE);
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
