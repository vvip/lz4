package lz4;

import net.jpountz.lz4.LZ4FrameInputStream;
import net.jpountz.lz4.LZ4FrameOutputStream;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lz4Tool
{
    public static void main(String[] args)
    {
        printTimeStr("LZ4 Tool Start Time");
        String srcFile = null;
        String dstFile = null;
        // 生成第二个参数的文件名
        if (args.length == 2)
        {
            if (!(new File(args[1]).exists()))
            {
                printArgsErrInfo(args[1] + " -- File Not Exists!");
            }
            // 生成的目的文件没有名字，采用默认的名字
            if (args[0].equals("-d"))
            {
                srcFile = args[1];
                dstFile = args[1].substring(0, args[1].length() - 4);
                lz4UnCompress(srcFile, dstFile);
            }
            else if (args[0].equals("-e"))
            {
                srcFile = args[1];
                dstFile = args[1] + ".lz4";
                lz4Compress(srcFile, dstFile);
            }
            else
            {
                printArgsErrInfo("Args Type Error!");
            }
        }
        else if (args.length == 3)
        {
            if (!(new File(args[1]).exists()))
            {
                printArgsErrInfo(args[1] + " -- File Not Exists!");
            }
            // 生成的目的文件已有名字，检查文件名/路径的合法性
            if (args[0].equals("-d"))
            {
                srcFile = args[1];
                dstFile = args[2];
                lz4UnCompress(srcFile, dstFile);
            }
            else if (args[0].equals("-e"))
            {
                srcFile = args[1];
                dstFile = args[2];
                lz4Compress(srcFile, dstFile);
            }
            else
            {
                printArgsErrInfo("Args Type Error!");
            }
        }
        else
        {
            printArgsErrInfo("Args Num Error!");
        }

        //lz4UnCompress("/Users/vipscu/Soft/2cmih9n78bwab390-80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results.json.lz4",
        //        "/Users/vipscu/Soft/80-http-open_proxy-full_ipv4-20160107T035825-zgrab-results1.json");
    }

    /**
     * 打印带有时间戳的提示字段
     *
     * @param info
     */
    private static void printTimeStr(String info)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(info + "  -  " + dateFormat.format(date));
    }

    /**
     * 打印正确的格式
     *
     * @param ErrorType
     */
    private static void printArgsErrInfo(String ErrorType)
    {
        System.out.println(ErrorType);
        System.out.println("eg: java -jar xxxx.jar -e unCompressFilename CompressFilename");
        System.out.println("    java -jar xxxx.jar -e unCompressFilename");
        System.out.println("eg: java -jar xxxx.jar -d CompressFilename unCompressFilename");
        System.out.println("    java -jar xxxx.jar -d CompressFilename");
        System.exit(0);
    }

    /**
     * @param filename
     * @param lz4filename
     */
    private static void lz4Compress(String filename, String lz4filename)
    {
        long startTime = System.currentTimeMillis();
        byte[] buf = new byte[40960];
        try
        {
            LZ4FrameOutputStream out = new LZ4FrameOutputStream(new FileOutputStream(lz4filename));
            BufferedOutputStream bo = new BufferedOutputStream(out);

            FileInputStream in = new FileInputStream(filename);
            BufferedInputStream bi = new BufferedInputStream(in);
            int len = -1;
            while ((len = bi.read(buf)) > 0)
            {
                bo.write(buf, 0, len);
            }
            bi.close();
            bo.close();
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
        System.out.println("lz4Compress Cost time: " + (endTime - startTime));
    }

    /**
     * 解压lz文件
     *
     * @param lz4filename
     * @param filename
     */
    private static void lz4UnCompress(String lz4filename, String filename)
    {
        long startTime = System.currentTimeMillis();
        byte[] buf = new byte[40960];
        try
        {
            LZ4FrameInputStream in = new LZ4FrameInputStream(new FileInputStream(lz4filename));
            BufferedInputStream bi = new BufferedInputStream(in);

            FileOutputStream out = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(out);
            int len = -1;
            while ((len = bi.read(buf)) > 0)
            {
                bo.write(buf, 0, len);
            }
            bi.close();
            bo.close();
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
        System.out.println("lz4UnCompress Cost time: " + (endTime - startTime));
    }
}
