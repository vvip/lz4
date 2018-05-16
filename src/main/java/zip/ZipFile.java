package zip;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile
{
    public static void main(String[] args)
    {
        //String zipFilePath = "/Users/vipscu/Soft/chromedriver_mac64.zip";

        String zipFileName = "/Users/vipscu/Soft/csdn.part.txt.zip";
        String unZipFileName = "/Users/vipscu/Soft/csdn.part.txt";
        String[] entries = new String[1];
        entries[0] = unZipFileName;
        zip(zipFileName, entries);


    }

    private static void zip(String zipFileName, String[] zipEntries)
    {
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName))))
        {
            zipOutputStream.setLevel(Deflater.BEST_COMPRESSION);
            for (int i = 0 ; i < zipEntries.length; i++)
            {
                File entryFile = new File(zipEntries[i]);
                if (!entryFile.exists())
                {
                    System.out.println("The entry file " + entryFile.getAbsolutePath() + " dose not exist!");
                    return;
                }

                ZipEntry zipEntry = new ZipEntry(zipEntries[i]);
                zipOutputStream.putNextEntry(zipEntry);
                addEntryContent(zipOutputStream, zipEntries[i]);
                zipOutputStream.closeEntry();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void addEntryContent(ZipOutputStream zipOutputStream, String entryFileName) throws IOException
    {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(entryFileName));
        byte[] buffer = new byte[10240];
        int count = -1;
        while ((count = bufferedInputStream.read(buffer)) != -1)
        {
            zipOutputStream.write(buffer, 0, count);
        }
        bufferedInputStream.close();
    }
}
