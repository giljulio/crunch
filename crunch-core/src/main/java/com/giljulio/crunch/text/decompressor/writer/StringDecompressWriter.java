package com.giljulio.crunch.text.decompressor.writer;

public class StringDecompressWriter implements DecompressWriter<String> {

    private final StringBuilder output = new StringBuilder();

    @Override
    public void write(char character) {
        output.append(character);
    }

    @Override
    public String output() {
        return output.toString();
    }
}
