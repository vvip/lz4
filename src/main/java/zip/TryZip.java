package zip;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class TryZip
{
    public static void main(String[] args) throws UnsupportedEncodingException, DataFormatException
    {
        String input = "Hello World!AAAAAAAAAAAAAAAAAAA";
        byte[] uncompressedData = input.getBytes("UTF-8");
        byte[] compressedData = compress(uncompressedData, Deflater.BEST_COMPRESSION, false);
        byte[] decompressedData = decompress(compressedData, false);
        String output = new String(decompressedData, "UTF-8");
        System.out.println("uncompressedData: " + new String(uncompressedData, "UTF-8") + ", length: " + uncompressedData.length);
        System.out.println("compressedData: " + new String(compressedData, "UTF-8") + ", length: " + compressedData.length);
        System.out.println("decompressedData: " + output + ", length: " + decompressedData.length);
    }

    public static byte[] compress(byte[] input, int compressionLevel, boolean GZIPFormat)
    {
        Deflater compressor = new Deflater(compressionLevel, GZIPFormat);

        compressor.setInput(input);
        compressor.finish();

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[10240];
        int readCount = 0;

        while(!compressor.finished())
        {
            readCount = compressor.deflate(readBuffer);
            if(readCount > 0)
            {
                bao.write(readBuffer, 0, readCount);
            }
        }

        compressor.end();
        return bao.toByteArray();
    }

    public static byte[] decompress(byte[] input, boolean GZIPFormat) throws DataFormatException
    {
        Inflater decompressor = new Inflater(GZIPFormat);
        decompressor.setInput(input);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[10240];
        int readCount = 0;

        while (!decompressor.finished())
        {
            readCount = decompressor.inflate(readBuffer);
            if(readCount > 0)
            {
                bao.write(readBuffer, 0, readCount);
            }
        }
        decompressor.end();
        return bao.toByteArray();
    }
}
