package com.my.extract.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.textmining.text.extraction.WordExtractor;

public class ExtractFromWord {

    public static void main(String[] args) throws FileNotFoundException, Exception
    {
        String bodyText = new WordExtractor().extractText(new FileInputStream(new File("resources/test2.doc")));
        System.out.println(bodyText);
    }
}
