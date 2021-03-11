package com.my.tika;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TestTika01 {

    public static void main(String[] args) throws Exception
    {
        new TestTika01().todo();
    }
    
    public void todo() throws Exception
    {
        System.out.println(parseToPlainText());
    }
    
    public String parseToStringExample() throws IOException, SAXException, TikaException {
        Tika tika = new Tika();
        try (InputStream stream = getClass().getResourceAsStream("resources/test2.doc")) {
            return tika.parseToString(stream);
        }
    }
    
    public String parseToPlainText() throws IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = getClass().getResourceAsStream("/test2.doc")) {
//        try (InputStream stream = getClass().getResource("test2.doc").openStream()) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }
    
    public String parseToHTML() throws IOException, SAXException, TikaException {
        ContentHandler handler = new ToXMLContentHandler();
     
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = getClass().getResourceAsStream("/resources/test2.doc")) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }
    
    public String parseBodyToHTML() throws IOException, SAXException, TikaException {
        ContentHandler handler = new BodyContentHandler(
                new ToXMLContentHandler());
     
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = getClass().getResourceAsStream("test.doc")) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }
    
//    public String microsoftTranslateToFrench(String text) {
//        MicrosoftTranslator translator = new MicrosoftTranslator();
//        // Change the id and secret! See http://msdn.microsoft.com/en-us/library/hh454950.aspx.
//        translator.setId("dummy-id");
//        translator.setSecret("dummy-secret");
//        try {
//            return translator.translate(text, "fr");
//        } catch (Exception e) {
//            return "Error while translating.";
//        }
//    }
    
    public String identifyLanguage(String text) {
        LanguageIdentifier identifier = new LanguageIdentifier(text);
        return identifier.getLanguage();
    }
}
