import quicktime.QTSession;
import quicktime.qd.QDGraphics;
import quicktime.qd.QDRect;
import quicktime.std.sg.SGDeviceList;
import quicktime.std.sg.SGDeviceName;
import quicktime.std.sg.SGVideoChannel;
import quicktime.std.sg.SequenceGrabber;
import quicktime.util.RawEncodedImage;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: pbakkum
 * Date: 4/24/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {

    public static void main(String args[]) throws Exception {
        ColoredImage d = new ColoredImage();

        VideoCapture vc = new VideoCapture(512, 512);


        String sentence;
        String modifiedSentence;
        Socket clientSocket = new Socket("10.101.200.153", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));



        for(int i = 0; i < 999999999; i++) {
            BufferedImage img = (BufferedImage) vc.getNextImage();
            String s = d.printImage(img);
            outToServer.writeBytes(s);
            Thread.sleep(400);
        }

        clientSocket.close();
    }
}
