import net.jpountz.lz4.LZ4BlockInputStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UnLz4
{
    private JPanel Panel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton buttonFilePath;
    private JButton buttonDirPath;
    private JLabel labelFilePath;
    private JLabel labelDirPath;
    private JButton buttonUncompress;
    private JProgressBar progressBar1;
    private JLabel labelProcess;

    public UnLz4()
    {
        buttonFilePath.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setMultiSelectionEnabled(false);       // 是否允许多选
                int returnVal = jFileChooser.showOpenDialog(buttonFilePath);
                System.out.println("returnVal: " + returnVal);

                if (returnVal == jFileChooser.APPROVE_OPTION)
                {
                    String filePath = jFileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println(filePath);
                    textField1.setText(filePath);
                }
            }
        });

        buttonDirPath.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = jFileChooser.showOpenDialog(buttonDirPath);
                System.out.println("returnVal: " + returnVal);

                if (returnVal == jFileChooser.APPROVE_OPTION)
                {
                    String dirPath = jFileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println(dirPath);
                    textField2.setText(dirPath);
                }
            }
        });

        buttonUncompress.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                lz4Uncompress(textField1.getText(), textField2.getText()+"/dest.csv");
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("UnLz4");
        frame.setContentPane(new UnLz4().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void lz4Uncompress(String lz4file, String filename)
    {
        byte[] buf = new byte[2048];
        try
        {
            LZ4BlockInputStream lz4BlockInputStream = new LZ4BlockInputStream(new FileInputStream(lz4file));
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            int len;
            while((len = lz4BlockInputStream.read(buf)) > 0)
            {
                fileOutputStream.write(buf, 0 , len);
            }
            lz4BlockInputStream.close();
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
