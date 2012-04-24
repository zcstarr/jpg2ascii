import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    public static void main(String args[]) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("/Users/pbakkum/Desktop/Lenna.png"));
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }

        System.out.println("width " + img.getWidth());
        System.out.println(img.getRGB(10, 100));
    }
}
