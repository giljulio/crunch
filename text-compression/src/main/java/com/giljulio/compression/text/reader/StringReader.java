package com.giljulio.compression.text.reader;

public final class StringReader implements CrunchReader {

    private final char[] chars;
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
}
