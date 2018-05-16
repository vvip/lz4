package lz4;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4FrameInputStream;

import java.io.*;

public class Lz4File
{
    public static void main(String[] args)
    {
        lz4Compress("/Users/vipscu/Soft/v5y3pr9m1zpj8vv2-alexa-top1m.20180330T0205.csv","./v5y3pr9m1zpj8vv2-alexa-top1m.20180330T0205.csv.lz4");
        //lz4Compress("/Users/vipscu/Soft/csdn.part.txt", "csdn.part.txt.lz4");
        //lz4UnCompress("csdn.part.txt.lz4", "csdn.part.txt");
        //lz4UnCompress2("/Users/vipscu/Soft/v5y3pr9m1zpj8vv2-alexa-top1m.20180330T0205.csv.lz4", "./alexa-top1m.csv");
        /*lz4UnCompress2(
                40960,
                "/Users/vipscu/Soft/2cmih9n78bwab390-80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results.json.lz4",
                "/Users/vipscu/Soft/80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results1.json");
                */
        /*lz4UnCompress2(
                40960,
                "/Users/vipscu/Soft/2cmih9n78bwab390-80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results.json.lz4",
                "/Users/vipscu/Soft/80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results2.json");
        lz4UnCompress2(
                409600,
                "/Users/vipscu/Soft/2cmih9n78bwab390-80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results.json.lz4",
                "/Users/vipscu/Soft/80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results3.json");
                */
    }

    private static void lz4Compress(String filename, String lz4filename)
    {
        byte[] buf = new byte[4096];
        try
        {
            LZ4BlockOutputStream out = new LZ4BlockOutputStream(new FileOutputStream(lz4filename));
            FileInputStream in = new FileInputStream(filename);
            int len = -1;
            while((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
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

    private static void lz4UnCompress(String lz4filename, String filename)
    {
        byte[] buf = new byte[4096];
        try
        {
            LZ4BlockInputStream in = new LZ4BlockInputStream(new FileInputStream(lz4filename));
            FileOutputStream out = new FileOutputStream(filename);
            int len = -1;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
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

    //不带缓冲区写
    //Buffsize: 4096, cost time: 99806
    //Buffsize: 40960, cost time: 88168

    //带缓冲区写
    //Buffsize: 4096, cost time: 75313
    //Buffsize: 40960, cost time: 51163
    //Buffsize: 409600, cost time: 48288
    private static void lz4UnCompress2(int bufsize, String lz4filename, String filename)
    {
        long startTime = System.currentTimeMillis();
        byte[] buf = new byte[bufsize];
        try
        {
            LZ4FrameInputStream in = new LZ4FrameInputStream(new FileInputStream(lz4filename));
            FileOutputStream out = new FileOutputStream(filename);
            BufferedOutputStream bw = new BufferedOutputStream(out);
            int len = -1;
            while ((len = in.read(buf)) > 0)
            {
                bw.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Buffsize: " + bufsize + ", cost time: " + (endTime - startTime));
    }


}
