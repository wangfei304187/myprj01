package com.kdevn.mydcm4che3;

import java.io.File;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Fragments;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.imageio.codec.Compressor;
import org.dcm4che3.imageio.codec.Decompressor;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.util.SafeClose;

public class TestDcmCompress
{
    public static void main(String[] args) throws Exception
    {
        new TestDcmCompress().todo();
    }

    public void todo() throws Exception
    {
        File src = new File("resource/1_0.dcm");
        File dest = new File("resource/out.dcm");
        Attributes fmi;
        Attributes dataset;
        DicomInputStream dis = new DicomInputStream(src);
        try
        {
            dis.setIncludeBulkData(IncludeBulkData.URI);
            fmi = dis.readFileMetaInformation();
            dataset = dis.readDataset(-1, -1);
        }
        finally
        {
            dis.close();
        }
        Object pixeldata = dataset.getValue(Tag.PixelData);
        System.out.println(pixeldata);
        Compressor compressor = null;
        DicomOutputStream dos = null;
        try
        {
            // String tsuid = this.tsuid;
            // String tsuid = "1.2.840.10008.1.2.4.90";
            String tsuid = "1.2.840.10008.1.2.1";
            if (pixeldata != null)
            {
                // if (tstype.isPixeldataEncapsulated())
                // {
                // tsuid = adjustTransferSyntax(tsuid,
                // dataset.getInt(Tag.BitsStored, 8));
                // compressor = new Compressor(dataset, dis.getTransferSyntax());
                // compressor.compress(tsuid, params.toArray(new Property[params.size()]));
                // }
                // else
                if (pixeldata instanceof Fragments)
                {
                    Decompressor.decompress(dataset, dis.getTransferSyntax());
                }
            }
            // if (nofmi)
            // {
            // fmi = null;
            // }
            // else if (retainfmi && fmi != null)
            // {
            // fmi.setString(Tag.TransferSyntaxUID, VR.UI, tsuid);
            // }
            // else
            // {
            // fmi = dataset.createFileMetaInformation(tsuid);
            // }

            fmi.setString(Tag.TransferSyntaxUID, VR.UI, tsuid);
            fmi = dataset.createFileMetaInformation(tsuid);

            dos = new DicomOutputStream(dest);
            // dos.setEncodingOptions(encOpts);
            dos.writeDataset(fmi, dataset);
        }
        finally
        {
            SafeClose.close(compressor);
            SafeClose.close(dos);
        }
    }

}
