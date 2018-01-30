package com.giljulio.crunch.text.sample;

import com.giljulio.crunch.text.Crunch;
import com.giljulio.crunch.text.compressor.reader.StringReader;
import com.giljulio.crunch.text.compressor.writer.StringWriter;

public class SimpleStringSample {

    private static final String INPUT_TEXT = "she sells sea shells on the sea shore";

    public static void main(String[] args) {

        Crunch crunch = new Crunch.Builder().build();

        String compressedOutput = crunch.compress(new StringReader(INPUT_TEXT), new StringWriter());

        System.out.println(compressedOutput);
    }
}
