package com.giljulio.compression.text.writer;

public final class StringCrunchWriter implements CrunchWriter<String> {

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
