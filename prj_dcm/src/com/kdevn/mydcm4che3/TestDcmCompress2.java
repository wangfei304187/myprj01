package com.kdevn.mydcm4che3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.imageio.codec.Transcoder;

public class TestDcmCompress2
{
    public static void main(String[] args) throws Exception
    {
        new TestDcmCompress2().todo();
    }

    public void todo() throws Exception
    {
        File src = new File("resource/1_0.dcm");
        File dest = new File("resource/out2.dcm");
        try (Transcoder transcoder = new Transcoder(src))
        {
            transcoder.setIncludeFileMetaInformation(true);
            transcoder.setRetainFileMetaInformation(true);
            // transcoder.setEncodingOptions(encOpts);
            String tsuid = "1.2.840.10008.1.2.1";
            transcoder.setDestinationTransferSyntax(tsuid);
            // transcoder.setCompressParams(params.toArray(new Property[params.size()]));
            transcoder.transcode(new Transcoder.Handler()
            {
                @Override
                public OutputStream newOutputStream(Transcoder transcoder, Attributes dataset) throws IOException
                {
                    return new FileOutputStream(dest);
                }
            });
        }
        catch (Exception e)
        {
            Files.deleteIfExists(dest.toPath());
            throw e;
        }
    }

}
