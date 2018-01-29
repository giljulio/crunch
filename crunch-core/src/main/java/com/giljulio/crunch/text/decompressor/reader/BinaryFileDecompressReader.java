package com.giljulio.crunch.text.decompressor.reader;

import com.giljulio.crunch.text.decompressor.model.ReaderItem;
import com.giljulio.crunch.text.io.BitInputStream;
import com.giljulio.crunch.text.io.FileBitInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class BinaryFileDecompressReader implements DecompressReader {

    private final BitInputStream in;

    public BinaryFileDecompressReader(File file) throws FileNotFoundException {
        this(new FileBitInputStream(file));
    }

    BinaryFileDecompressReader(BitInputStream bitInputStream) {
        in = bitInputStream;
    }

    @Override
    public ReaderItem read() {
        if (in.hasNext()) {
            if (in.readBit()) {
                return readReference();
            } else {
                return readCharacter();
            }
        }
        return null;
    }

    private ReaderItem readReference() {
        short offset = 0;
        for (int i = 0; i < 16; i++) {
            offset = (short) ((offset << 1) | (in.readBit() ? 1 : 0));
        }

        int length = 0;
        for (int i = 0; i < 6; i++) {
            length = (length << 1) | (in.readBit() ? 1 : 0);
        }
        return new ReaderItem.Reference(offset, length + 3);
    }

    private ReaderItem readCharacter() {
        try {
            byte character = 0;
            for (int i = 0; i < 8; i++) {
                character = (byte) ((character << 1) | (in.readBit() ? 1 : 0));
            }
            return new ReaderItem.Character((char) character);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public void close() {
        in.close();
    }
}
