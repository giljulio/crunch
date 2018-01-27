package com.giljulio.compression.text.reader;

public final class StringReader implements CrunchReader {

    private final char[] chars;
    private int index = 0;

    public StringReader(String input) {
        chars = input.toCharArray();
    }

    @Override
    public int read() {
        if (index >= chars.length) {
            return -1;
        }
        return chars[index++];
    }
}
