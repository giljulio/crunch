package com.giljulio.compression.text.decompressor.reader;

import com.giljulio.compression.text.io.BitInputStream;
import org.junit.Before;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BinaryFileDecompressReaderTest {


    private BinaryFileDecompressReader underTest;
    private BitInputStream bitInputStream;

    @Before
    public void setup() throws FileNotFoundException {
        bitInputStream = new MockBitInputStream();
        underTest = new BinaryFileDecompressReader(bitInputStream);
    }


    static class MockBitInputStream implements BitInputStream {

        final ArrayList<Boolean> bits = new ArrayList<>();
        private int position = 0;

        @Override
        public boolean hasNext() {
            return bits.size() > position;
        }

        @Override
        public boolean readBit() {
            return bits.get(position);
        }

        @Override
        public void close() {
            // no-op
        }
    }

}