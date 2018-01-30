package com.giljulio.crunch.text.sample;

import com.giljulio.crunch.text.Crunch;

import java.io.File;
import java.io.FileNotFoundException;

public class LargeTextFileSample {

    private static final String PLAIN_TEXT_FILE_PATH = "./bible.txt";
    private static final String COMPRESSED_FILE_PATH = "./bible.bin";
    private static final String DECOMPRESSED_FILE_PATH = "./bible-decompressed.txt";

    public static void main(String[] args) throws FileNotFoundException {

        File plainTextFile = new File(PLAIN_TEXT_FILE_PATH);
        File compressedFile = new File(COMPRESSED_FILE_PATH);
        File decompressedFile = new File(DECOMPRESSED_FILE_PATH);
        Crunch crunch = new Crunch.Builder().build();

        System.out.println("Plain text file: " + plainTextFile.length());

        crunch.compress(plainTextFile, compressedFile);
        System.out.println("Compressed file: " + compressedFile.length());

        crunch.decompress(compressedFile, decompressedFile);
        System.out.println("Decompressed file: " + decompressedFile.length());
    }
}
