package com.giljulio.compression.text.compressor.writer;

public interface CompressorWriter<T> {

    void writeCharacter(char character);

    void writeReference(int relativeOffset, int length);

    T output();
}
