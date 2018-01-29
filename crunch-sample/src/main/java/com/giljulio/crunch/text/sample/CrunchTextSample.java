package com.giljulio.crunch.text.sample;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.StringReader;
import com.giljulio.crunch.text.compressor.writer.StringWriter;

import java.io.File;
import java.io.FileNotFoundException;

public class CrunchTextSample {

    private static final String INPUT_TEXT = "she sells sea shells on the sea shore";
    private static final String DIVIDER = "-----";
    private static final String ANCIENT_EGYPTIAN_TEXT = "Ancient Egyptian literature was written in the Egyptian language " +
            "from Ancient Egypt's pharaonic period until the end of Roman domination. Along with Sumerian literature, it " +
            "is considered the world's earliest literature. Writing in Ancient Egypt first appeared in the late 4th mille" +
            "nnium BC. By the Old Kingdom, literary works included funerary texts, epistles and letters, religious hymns " +
            "and poems, and commemorative autobiographical texts. It was not until the early Middle Kingdom that a narrat" +
            "ive Egyptian literature was created. Middle Egyptian, the spoken language of the Middle Kingdom, became a cl" +
            "assical language during the New Kingdom, when the vernacular language known as Late Egyptian first appeared " +
            "in writing. Scribes of the New Kingdom canonized and copied many literary texts written in Middle Egyptian, " +
            "which remained the language used for oral readings of sacred hieroglyphic texts. Ancient Egyptian literature" +
            " has been preserved on a wide variety of media, including papyrus scrolls and packets, limestone or ceramic " +
            "ostraca, wooden writing boards, monumental stone edifices and coffins. Hidden caches of literature, buried f" +
            "or thousands of years, have been discovered in settlements on the dry desert margins of Egyptian civilization.";

    public static void main(String[] args) throws FileNotFoundException {
        smallInput();

        System.out.println(DIVIDER);

        largeInput();

        System.out.println(DIVIDER);

        smallSearchCharacterBuffer();

        stringToFile();

        System.out.println(DIVIDER);

        fileToString();

        System.out.println(DIVIDER);

        fileToFile();
    }

    private static void fileToFile() throws FileNotFoundException {
        Crunch crunch = new Crunch.Builder().build();

        File source = new File("./test.bin");
        File destination = new File("./test.txt");

        crunch.decompress(source, destination);
    }

    private static void smallInput() {
        Crunch crunch = new Crunch.Builder().build();

        String compressedOutput = crunch.compress(new StringReader(INPUT_TEXT), new StringWriter());

        System.out.println(INPUT_TEXT);
        System.out.println(compressedOutput);
    }

    private static void largeInput() {
        Crunch crunch = new Crunch.Builder()
                .minimumCharacterReferenceLength(10)
                .build();

        String compressedOutput = crunch.compress(new StringReader(ANCIENT_EGYPTIAN_TEXT), new StringWriter());

        System.out.println(ANCIENT_EGYPTIAN_TEXT);
        System.out.println(compressedOutput);
    }

    private static void smallSearchCharacterBuffer() {
        Crunch crunch = new Crunch.Builder()
                .searchCharacterBufferSize(11)
                .minimumCharacterReferenceLength(3)
                .build();

        String compressedOutput = crunch.compress(new StringReader(INPUT_TEXT), new StringWriter());

        System.out.println(INPUT_TEXT);
        System.out.println(compressedOutput);
    }

    private static void stringToFile() throws FileNotFoundException {
        File file = new File("./test.bin");
        new Crunch.Builder().build().compress(INPUT_TEXT, file);
    }

    private static void fileToString() throws FileNotFoundException {
        Crunch crunch = new Crunch.Builder().build();

        File file = new File("./test.bin");
        System.out.println(crunch.decompress(file));
    }
}