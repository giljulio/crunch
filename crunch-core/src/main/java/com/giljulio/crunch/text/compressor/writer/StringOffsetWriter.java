package com.giljulio.crunch.text.compressor.writer;

/**
 * <p>StringOffsetWriter is useful for debugging offsets. Example output:</p>
 *
 * <blockquote><pre>
 *     (0,'s')(0,'h'),(0,'e'),(0,' '),(0,'s'),(0,'e'),
 *     (0,'l'),(0,'l'),(0,'s'),(0,' '),(1,-5,2),(0,'a'),
 *     (0,' '),(1,-13,3),...
 * </pre></blockquote>
 */
public final class StringOffsetWriter implements CompressorWriter<String> {

    private static final String START_BRACKET = "(";
    private static final String END_BRACKET = ")";
    private static final String COMER = ",";

    private final StringBuilder output = new StringBuilder();

    @Override
    public void writeCharacter(char character) {
        output.append(character);
    }

    @Override
    public void writeReference(short relativeOffset, int length) {
        output.append(START_BRACKET);
        output.append(relativeOffset);
        output.append(COMER);
        output.append(length);
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
