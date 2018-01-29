package com.giljulio.crunch.text.compressor.writer;

/**
 * An in-memory {@link String } writer. Useful for debugging compression.
 *
 * Example output:
 *
 * <blockquote><pre>
 *     she sells( se)a (she)(lls )on t(he s)(ea s)hore
 * </pre></blockquote>
 */
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
    public void writeReference(short relativeOffset, int length) {
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
    public int maxReferenceLength() {
        return -1;
    }

    @Override
    public String output() {
        return output.toString();
    }
}
