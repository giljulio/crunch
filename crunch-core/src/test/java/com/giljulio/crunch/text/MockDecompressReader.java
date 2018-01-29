package com.giljulio.crunch.text;

import com.giljulio.crunch.text.decompressor.model.ReaderItem;
import com.giljulio.crunch.text.decompressor.reader.DecompressReader;

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

    @Override
    public void close() {
        // no-op
    }
}
