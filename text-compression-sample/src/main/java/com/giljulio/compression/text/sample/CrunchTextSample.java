package com.giljulio.compression.text.sample;

import com.giljulio.compression.text.Crunch;
import com.giljulio.compression.text.reader.StringReader;
import com.giljulio.compression.text.writer.StringWriter;

public class CrunchTextSample {

    private static final String INPUT_TEXT = "she sells sea shells on the sea shore";

    public static void main(String[] args) {
        Crunch crunch = new Crunch.Builder()
                .searchCharacterBufferSize(1024)
                .minimumCharacterReferenceLength(3)
                .build();

        String compressedOutput = crunch.compress(new StringReader(INPUT_TEXT), new StringWriter());

        System.out.println(INPUT_TEXT);
        System.out.println(compressedOutput);
    }
}
