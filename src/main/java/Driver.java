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
import java.io.File;
import java.io.IOException;

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

        //d.printImage("/Users/pbakkum/Desktop/Lenna.png");

        VideoCapture vc = new VideoCapture(512, 512);

        while(true) {
            BufferedImage img = (BufferedImage) vc.getNextImage();
            d.printImage(img);
            Thread.sleep(100);
        }
    }




}
