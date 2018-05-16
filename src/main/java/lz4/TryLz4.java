package lz4;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.lz4.LZ4SafeDecompressor;

import java.io.UnsupportedEncodingException;

public class TryLz4
{
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        example();
    }

    private static void example() throws UnsupportedEncodingException
    {
        LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
        byte[] data = "1234567890".getBytes("UTF-8");
        final int decompressedLength = data.length;

        // compress data
        LZ4Compressor lz4Compressor = lz4Factory.fastCompressor();
        int maxCompressedLength = lz4Compressor.maxCompressedLength(decompressedLength);
        byte[] compressed = new byte[maxCompressedLength];
        int compressedLength = lz4Compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);

        // decompress data
        // method 1: when the decompressed length is known
        LZ4FastDecompressor decompressor = lz4Factory.fastDecompressor();
        byte[] restored = new byte[decompressedLength];
        int compressedLength2 = decompressor.decompress(compressed, 0, restored, 0, decompressedLength);
        // compressedLength == compressedLength2

        // method 2: when the compressed length is known (a little slower)
        // the destination buffer needs to be over-sized
        LZ4SafeDecompressor decompressor2 = lz4Factory.safeDecompressor();
        int decompressedLength2 = decompressor2.decompress(compressed, 0, compressedLength, restored, 0);
        // decompressedLength == decompressedLength2

    }
}
