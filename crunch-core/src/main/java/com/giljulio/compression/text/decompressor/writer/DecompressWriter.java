package com.giljulio.compression.text.decompressor.writer;

public interface DecompressWriter<T> {

    void write(char character);

    T output();
}
