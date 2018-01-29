package com.giljulio.compression.text.compressor.reader;

/**
 * StringReader is a in-memory {@link String } reader.
 */
public final class StringReader implements CompressorReader {

    private char[] chars;
    private int index = 0;

    public StringReader(String input) {
        chars = input.toCharArray();
    }

    @Override
    public char read() {
        if (index >= chars.length) {
            return '\u0000';
        }
        return chars[index++];
    }

    @Override
    public void close() {
        chars = null;
    }
}
