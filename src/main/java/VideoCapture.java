import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import quicktime.QTRuntimeException;
import quicktime.QTRuntimeHandler;
import quicktime.QTSession;
import quicktime.qd.PixMap;
import quicktime.qd.QDConstants;
import quicktime.qd.QDGraphics;
import quicktime.qd.QDRect;
import quicktime.std.StdQTConstants;
import quicktime.std.sg.SequenceGrabber;
import quicktime.std.sg.SGVideoChannel;
import quicktime.util.RawEncodedImage;

public class VideoCapture {
    private SequenceGrabber grabber;
    private SGVideoChannel channel;
    private RawEncodedImage rowEncodedImage;

    private int width;
    private int height;
    private int videoWidth;

    private int[] pixels;
    private BufferedImage image;
    private WritableRaster raster;

    public VideoCapture(int width, int height) throws Exception {
        this.width = width;
        this.height = height;
        try {
            QTSession.open();
            QDRect bounds = new QDRect(width, height);
            QDGraphics graphics = new QDGraphics(bounds);
            grabber = new SequenceGrabber();
            grabber.setGWorld(graphics, null);
            channel = new SGVideoChannel(grabber);
            channel.setBounds(bounds);
            channel.setUsage(StdQTConstants.seqGrabPreview);
            channel.settingsDialog();
            grabber.prepare(true, false);
            grabber.startPreview();
            PixMap pixMap = graphics.getPixMap();
            rowEncodedImage = pixMap.getPixelData();

            videoWidth = width + (rowEncodedImage.getRowBytes() - width * 4) / 4;
            pixels = new int[videoWidth * height];
            image = new BufferedImage(
                    videoWidth, height, BufferedImage.TYPE_INT_RGB);
            raster = WritableRaster.createPackedRaster(DataBuffer.TYPE_INT,
                    videoWidth, height,
                    new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff }, null);
            raster.setDataElements(0, 0, videoWidth, height, pixels);
            image.setData(raster);
            QTRuntimeException.registerHandler(new QTRuntimeHandler() {
                public void exceptionOccurred(
                        QTRuntimeException e, Object eGenerator,
                        String methodNameIfKnown, boolean unrecoverableFlag) {
                    System.out.println("what should i do?");
                }
            });
        } catch (Exception e) {
            QTSession.close();
            throw e;
        }
    }

    public void dispose() {
        try {
            grabber.stop();
            grabber.release();
            grabber.disposeChannel(channel);
            image.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            QTSession.close();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public int getVideoHeight() {
        return height;
    }

    public void getNextPixels(int[] pixels) throws Exception {
        grabber.idle();
        rowEncodedImage.copyToArray(0, pixels, 0, pixels.length);
    }

    public Image getNextImage() throws Exception {
        grabber.idle();
        rowEncodedImage.copyToArray(0, pixels, 0, pixels.length);
        raster.setDataElements(0, 0, videoWidth, height, pixels);
        image.setData(raster);
        return image;
    }
}
