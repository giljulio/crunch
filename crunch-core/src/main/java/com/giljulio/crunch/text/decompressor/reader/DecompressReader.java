package com.giljulio.crunch.text.decompressor.reader;

import com.giljulio.crunch.text.decompressor.model.ReaderItem;

public interface DecompressReader {

    ReaderItem read();

    void close();
}
