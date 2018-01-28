package com.giljulio.compression.text.compressor.writer;

public final class StringWriter implements CompressorWriter<String> {

    private static final String START_BRACKET = "(";
    private static final String END_BRACKET = ")";

    private final StringBuilder output = new StringBuilder();
    private final StringBuilder raw = new StringBuilder();

    @Override
    public void writeCharacter(char character) {
        output.append(character);
        raw.append(character);
    }

    @Override
    public void writeReference(int relativeOffset, int length) {
        output.append(START_BRACKET);
        int startIndex = raw.length() + relativeOffset;
        for (int i = 0; i < length; i++) {
            char charAt = raw.charAt(startIndex  + i);
            raw.append(charAt);
            output.append(charAt);
        }
        output.append(END_BRACKET);
    }

    @Override
    public String output() {
        return output.toString();
    }
}
