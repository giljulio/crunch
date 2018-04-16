package com.giljulio.crunch.text.sample;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.StringReader;
import com.giljulio.crunch.text.compressor.writer.StringWriter;

public class SimpleStringSample {

    private static final String INPUT_TEXT = "The mission of the Wikimedia Foundation is to empower and engage people " +
            "around the world to collect and develop educational content under a free license or in the public domain," +
            " and to disseminate it effectively and globally.";

    public static void main(String[] args) {

        Crunch crunch = new Crunch.Builder().build();

        String compressedOutput = crunch.compress(new StringReader(INPUT_TEXT), new StringWriter());

        System.out.println(compressedOutput);
    }
}
