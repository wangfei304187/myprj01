package com.my.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

public class MsgPackTest
{

    public static void main(String[] args) throws IOException
    {
        FileOutputStream fo = new FileOutputStream("/home/wf/xx/a.bin");

        // Create serialize objects.
        List<String> src = new ArrayList<String>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");

        MessagePack msgpack = new MessagePack();
        // Serialize
        byte[] raw = msgpack.write(src);

        // save raw into file
        fo.write(raw);
        fo.flush();
        fo.close();

        // Deserialize directly using a template
        List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));
        System.out.println(dst1.get(0));
        System.out.println(dst1.get(1));
        System.out.println(dst1.get(2));

        // Or, Deserialze to Value then convert type.
        Value dynamic = msgpack.read(raw);
        List<String> dst2 = new Converter(dynamic)
                .read(Templates.tList(Templates.TString));
        System.out.println(dst2.get(0));
        System.out.println(dst2.get(1));
        System.out.println(dst2.get(2));
    }

}
