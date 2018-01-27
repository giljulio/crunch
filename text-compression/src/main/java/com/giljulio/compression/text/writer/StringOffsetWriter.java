package com.giljulio.compression.text.writer;

public class StringOffsetWriter implements CrunchWriter<String> {

    private static final String START_BRACKET = "(";
    private static final String END_BRACKET = ")";
    private static final String COMER = ",";

    private final StringBuilder output = new StringBuilder();

    @Override
    public void writeCharacter(char character) {
        output.append(character);
    }

    @Override
    public void writeReference(int relativeOffset, int length) {
        output.append(START_BRACKET);
        output.append(relativeOffset);
        output.append(COMER);
        output.append(length);
        output.append(END_BRACKET);
    }

    @Override
    public String output() {
        return output.toString();
    }
}
