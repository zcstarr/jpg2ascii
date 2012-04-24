import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: pbakkum
 * Date: 4/24/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColoredImage {
    private class Pixel {
        public String color;
        public char ch;
    }

    private static int red(int px) {
        return (px >> 16) & 0xFF;
    }

    private static int green(int px) {
        return (px >> 8) & 0xFF;
    }

    private static int blue(int px) {
        return px & 0xFF;
    }

    public void printImage(String imgpath) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(imgpath));
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }

        printImage(img);
    }

    public void printImage(BufferedImage img) {
        Pixel[][] sample = sampler(img);
        render(sample);
    }

    private void render(Pixel[][] sample) {
        for(int row = 0; row < sample[0].length; row++) {
            String s = "";

            for(int col = 0; col < sample.length; col++) {
                s += "\033[" + sample[col][row].color + "m" + sample[col][row].ch + "\033[0m";
            }

            System.out.println(s);
        }
    }

    private Pixel[][] sampler(BufferedImage img) {
        int xsamplesize = 5;
        int ysamplesize = 10;
        int width = img.getWidth();
        int height = img.getHeight();

        int samplewidth = width / xsamplesize;
        int sampleheight = height / ysamplesize;

        Pixel[][] sample = new Pixel[samplewidth][sampleheight];

        for(int i = 0; i < samplewidth; i++) {
            for(int j = 0; j < sampleheight; j++) {
                int startx = i * xsamplesize;
                int starty = j * ysamplesize;
                int red = 0;
                int blue = 0;
                int green = 0;

                for(int x = startx; x < startx + xsamplesize && x < width; x++) {
                    for(int y = starty; y < starty + ysamplesize && y < height; y++) {
                        int color = img.getRGB(x, y);
                        red += red(color);
                        green += green(color);
                        blue += blue(color);
                    }
                }

                int sampletotal = xsamplesize * ysamplesize;
                //System.out.println(red + "  " + green);
                red /= sampletotal;
                green /= sampletotal;
                blue /= sampletotal;

                sample[i][j] = new Pixel();
                sample[i][j].color = PixelConverter.getTranslatedColor(red, green, blue);
                sample[i][j].ch = '@';
            }
        }

        return sample;
    }
}
