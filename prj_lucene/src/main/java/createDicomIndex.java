import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.StopTagInputHandler;
import org.dcm4che2.util.TagUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class createDicomIndex
{

    public static void main(String[] args) throws IOException
    {
//        new createDicomIndex().todo();
         new createDicomIndex().createIndex();
    }

    private Analyzer getAnalyzer()
    {
        // return new StandardAnalyzer();
        return new IKAnalyzer(true);
    }

    public void createIndex()
    {
        Date start = new Date();
        try
        {
            Directory dir = FSDirectory.open(Paths.get("/home/wf/db.index"));
            Analyzer analyzer = getAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            boolean create = true;
            if (create)
            {
                iwc.setOpenMode(OpenMode.CREATE);
            }
            else
            {
                // Add new documents to an existing index:
                iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
            }
            
            IndexWriter writer = new IndexWriter(dir, iwc);
            
            File docDir = new File("/data/dicomImage");
            indexDocs(writer, docDir.toPath());

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        }
        catch (IOException e)
        {
            System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
        }
    }

    private void indexDocs(final IndexWriter writer, Path path) throws IOException
    {
        if (Files.isDirectory(path))
        {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    try
                    {
                        indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    }
                    catch (IOException ignore)
                    {
                        // don't index files that can't be read.
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        else
        {
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
    }

    /** Indexes a single document */
    private void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException
    {
        // try (InputStream stream = Files.newInputStream(file))
        {
            // make a new, empty document
            Document doc = new Document();

            // Add the path of the file as a field named "path". Use a
            // field that is indexed (i.e. searchable), but don't tokenize
            // the field into separate words and don't index term frequency
            // or positional information:
            Field pathField = new StringField("path", file.toString(), Field.Store.YES);
            doc.add(pathField);

            // Add the last modified date of the file a field named "modified".
            // Use a LongPoint that is indexed (i.e. efficiently filterable with
            // PointRangeQuery). This indexes to milli-second resolution, which
            // is often too fine. You could instead create a number based on
            // year/month/day/hour/minutes/seconds, down the resolution you require.
            // For example the long value 2011021714 would mean
            // February 17, 2011, 2-3 PM.
            doc.add(new LongPoint("modified", lastModified));

            // Add the contents of the file to a field named "contents". Specify a Reader,
            // so that the text of the file is tokenized and indexed, but not stored.
            // Note that FileReader expects the file to be in UTF-8 encoding.
            // If that's not the case searching for special characters will fail.

            // doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
            doc.add(new TextField("contents", getDicomFile(file.toFile()), Store.YES));

            if (writer.getConfig().getOpenMode() == OpenMode.CREATE)
            {
                // New index, so we just add the document (no old document can be there):
                System.out.println("adding " + file);
                writer.addDocument(doc);
            }
            else
            {
                // Existing index (an old copy of this document may have been indexed) so
                // we use updateDocument instead to replace the old one matching the exact
                // path, if present:
                System.out.println("updating " + file);
                writer.updateDocument(new Term("path", file.toString()), doc);
            }
        }
    }

    public void todo() throws IOException
    {
        String s = "/data/dicomImage/100163315469/1.2.156.112679.160601001.12.100163315469300163315533/1.2.156.112679.160601001.13.10163315469301633155330163315609/1.dcm";
        File f = new File(s);
        try (DicomInputStream dis = new DicomInputStream(new FileInputStream(f)))
        {
            dis.setHandler(new StopTagInputHandler(Tag.PixelData));
            DicomObject dcmObj = new BasicDicomObject();
            dis.readDicomObject(dcmObj, -1);
            System.out.println(getDicomHeaderString(dcmObj));
        }
    }

    public String getDicomFile(File f) throws IOException
    {
        try (DicomInputStream dis = new DicomInputStream(new FileInputStream(f)))
        {
            dis.setHandler(new StopTagInputHandler(Tag.PixelData));
            DicomObject dcmObj = new BasicDicomObject();
            dis.readDicomObject(dcmObj, -1);
            return getDicomHeaderString(dcmObj);
        }
    }

    public String getDicomHeaderString(DicomObject dcmObj)
    {
        StringBuilder sb = new StringBuilder();
        collectDicomHeaderString(dcmObj, sb, 0);
        return sb.toString();
    }

    private void collectDicomHeaderString(DicomObject dicomObj, StringBuilder sb, int depth)
    {
        String prefix = createDicomIndex.generatePrefix(depth);
        depth++;
        Iterator<DicomElement> iter = dicomObj.datasetIterator();
        while (iter.hasNext())
        {
            DicomElement element = iter.next();
            int tag = element.tag();
            try
            {
                String tagName = dicomObj.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dicomObj.vrOf(tag).toString();
                if (tagVR.equals("SQ"))
                {
                    if (element.hasItems())
                    {
                        sb.append(prefix + tagAddr + " [" + tagVR + "] " + tagName + "\n");
                        collectDicomHeaderString(element.getDicomObject(), sb, depth);
                    }
                }
                String tagValue = dicomObj.getString(tag);
                sb.append(prefix + tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]\n");
            }
            catch (Exception e)
            {
                // e.printStackTrace();
            }
        }
    }

    private static String generatePrefix(int depth)
    {
        StringBuilder sb = new StringBuilder();
        while (depth-- > 0)
        {
            sb.append('>');
        }

        return sb.toString();
    }
}
