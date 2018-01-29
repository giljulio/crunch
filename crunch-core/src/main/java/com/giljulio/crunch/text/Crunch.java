package com.giljulio.crunch.text;

import com.giljulio.crunch.text.compressor.Compressor;
import com.giljulio.crunch.text.compressor.reader.CompressorReader;
import com.giljulio.crunch.text.compressor.reader.PlainTextFileReader;
import com.giljulio.crunch.text.compressor.reader.StringReader;
import com.giljulio.crunch.text.compressor.writer.BinaryFileWriter;
import com.giljulio.crunch.text.compressor.writer.CompressorWriter;
import com.giljulio.crunch.text.compressor.writer.StringWriter;
import com.giljulio.crunch.text.decompressor.Decompressor;
import com.giljulio.crunch.text.decompressor.reader.BinaryFileDecompressReader;
import com.giljulio.crunch.text.decompressor.reader.DecompressReader;
import com.giljulio.crunch.text.decompressor.writer.DecompressWriter;
import com.giljulio.crunch.text.decompressor.writer.PlainTextFileDecompressWriter;
import com.giljulio.crunch.text.decompressor.writer.StringDecompressWriter;

import java.io.File;
import java.io.FileNotFoundException;

public final class Crunch {

    private final int bufferSize;
    private final int minRefSize;

    private Crunch(int bufferSize, int minRefSize) {
        this.bufferSize = bufferSize;
        this.minRefSize = minRefSize;
    }

    /**
     * Generic compress from {@link CompressorReader } to {@link CompressorWriter }
     *
     * @param source reader
     * @param destination writer
     *
     * @return writer's output
     */
    public <T> T compress(CompressorReader source, CompressorWriter<T> destination) {
        return new Compressor<>(this, source, destination).execute();
    }

    /**
     * Compresses in-memory {@link String } to a in-memory {@link String }
     *
     * @param source text to compress
     *
     * @return compressed text
     * @throws FileNotFoundException if destination file does not exist
     */
    public String compress(String source) throws FileNotFoundException {
        return compress(new StringReader(source), new StringWriter());
    }

    /**
     * Compresses in-memory {@link String } to a {@link File }
     *
     * @param text to compress
     * @param destination to hold the compressed file
     *
     * @return file that contains the compressed text
     * @throws FileNotFoundException if destination file does not exist
     */
    public File compress(String text, File destination) throws FileNotFoundException {
        StringReader reader = new StringReader(text);
        BinaryFileWriter writer = new BinaryFileWriter(destination);
        return compress(reader, writer);
    }

    /**
     * Streams compress from a plain text {@link File } to a binary format {@link File }
     *
     * @param source raw text file
     * @param destination file to write the compressed binary
     *
     * @return file that is compressed
     * @throws FileNotFoundException if source or destination files do not exist
     */
    public File compress(File source, File destination) throws FileNotFoundException {
        PlainTextFileReader reader = new PlainTextFileReader(source);
        BinaryFileWriter writer = new BinaryFileWriter(destination);
        return compress(reader, writer);
    }

    /**
     * Generic decompress from {@link DecompressReader } to {@link DecompressWriter<T> }
     *
     * @param source reader
     * @param destination writer
     *
     * @return writer's output
     */
    public <T> T decompress(DecompressReader source, DecompressWriter<T> destination) {
        return new Decompressor<>(this, source, destination).execute();
    }

    /**
     * Decompress to in-memory {@link String }
     *
     * @param source reader
     *
     * @return decompressed string
     */
    public String decompress(DecompressReader source) {
        return decompress(source, new StringDecompressWriter());
    }

    /**
     * Decompress stream from {@link File} to in-memory {@link String }
     *
     * @param source file to decompress
     *
     * @return decompressed text
     * @throws FileNotFoundException when the source is not found
     */
    public String decompress(File source) throws FileNotFoundException {
        return decompress(new BinaryFileDecompressReader(source));
    }

    /**
     * Decompress stream from {@link File } to {@link File }
     *
     * @param source file to decompress
     * @param destination file to for plain text
     *
     * @return decompressed file
     * @throws FileNotFoundException when source or destination files are not found
     */
    public File decompress(File source, File destination) throws FileNotFoundException {
        return decompress(new BinaryFileDecompressReader(source), new PlainTextFileDecompressWriter(destination));
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getMinRefSize() {
        return minRefSize;
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
