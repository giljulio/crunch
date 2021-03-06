package com.giljulio.crunch.text.compressor.writer;

import com.giljulio.crunch.text.io.BitOutputStream;
import com.giljulio.crunch.text.io.FileBitOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * BinaryFileWriter to write binary to {@link File}. This writes on-the-fly.
 */
public class BinaryFileWriter implements CompressorWriter<File> {

    private final static int MAX_REFERENCE_LENGTH = 66;

    private final BitOutputStream out;
    private File file;

    public BinaryFileWriter(File file) throws FileNotFoundException {
        this(new FileBitOutputStream(new FileOutputStream(file)));
        this.file = file;
    }

    BinaryFileWriter(BitOutputStream bitOutputStream) {
        out = bitOutputStream;
    }

    @Override
    public void writeCharacter(char character) {
        out.writeBit(false);

        out.writeBit((character & 0x80) != 0);
        out.writeBit((character & 0x40) != 0);
        out.writeBit((character & 0x20) != 0);
        out.writeBit((character & 0x10) != 0);
        out.writeBit((character &    8) != 0);
        out.writeBit((character &    4) != 0);
        out.writeBit((character &    2) != 0);
        out.writeBit((character &    1) != 0);
    }

    @Override
    public void writeReference(short relativeOffset, int length) {
        out.writeBit(true);

        out.writeBit((relativeOffset & 0x8000) != 0);
        out.writeBit((relativeOffset & 0x4000) != 0);
        out.writeBit((relativeOffset & 0x2000) != 0);
        out.writeBit((relativeOffset & 0x1000) != 0);
        out.writeBit((relativeOffset &  0x800) != 0);
        out.writeBit((relativeOffset &  0x400) != 0);
        out.writeBit((relativeOffset &  0x200) != 0);
        out.writeBit((relativeOffset &  0x100) != 0);
        out.writeBit((relativeOffset &   0x80) != 0);
        out.writeBit((relativeOffset &   0x40) != 0);
        out.writeBit((relativeOffset &   0x20) != 0);
        out.writeBit((relativeOffset &   0x10) != 0);
        out.writeBit((relativeOffset &      8) != 0);
        out.writeBit((relativeOffset &      4) != 0);
        out.writeBit((relativeOffset &      2) != 0);
        out.writeBit((relativeOffset &      1) != 0);

        byte referenceLength = (byte) (length - 3);
        out.writeBit((referenceLength & 0x20) != 0);
        out.writeBit((referenceLength & 0x10) != 0);
        out.writeBit((referenceLength &    8) != 0);
        out.writeBit((referenceLength &    4) != 0);
        out.writeBit((referenceLength &    2) != 0);
        out.writeBit((referenceLength &    1) != 0);
    }

    @Override
    public int maxReferenceLength() {
        return MAX_REFERENCE_LENGTH;
    }

    @Override
    public File output() {
        out.close();
        return file;
    }
}
