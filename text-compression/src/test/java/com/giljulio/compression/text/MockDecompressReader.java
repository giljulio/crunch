package com.giljulio.compression.text;

import com.giljulio.compression.text.decompressor.model.ReaderItem;
import com.giljulio.compression.text.decompressor.reader.DecompressReader;

class MockDecompressReader implements DecompressReader {
    private final ReaderItem[] items;
    private int position;

    MockDecompressReader(ReaderItem[] items) {
        this.items = items;
    }

    @Override
    public ReaderItem read() {
        return items.length > position ? items[position++] : null;
    }
}
