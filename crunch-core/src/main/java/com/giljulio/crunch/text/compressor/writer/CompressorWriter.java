package com.giljulio.crunch.text.compressor.writer;

public interface CompressorWriter<T> {

    void writeCharacter(char character);

    void writeReference(short relativeOffset, int length);

    int maxReferenceLength();

    T output();
}
