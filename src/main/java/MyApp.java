import org.apache.commons.cli.*;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.*;

import com.jhlabs.image.GaussianFilter;


public class MyApp {

    /**
     * Runs the application
     *
     * @param args an array of String arguments to be parsed
     */

    private static final String RADIUS = "5";

    public static void main(String[] args) {

        try {

            CommandLine line = parseArguments(args);

            String fileName = line.getOptionValue("filename");
            String radiusStr = line.getOptionValue("radius");


            if (radiusStr == null) {
                radiusStr = RADIUS;
            }

            float radius = 0;

            radius = Float.parseFloat(radiusStr);
            if (radius <= 0) {
                System.out.println("Radius should be a positive number");
                return;
            }


            BufferedImage img = null;
            img = ImageIO.read(new File(fileName));


            String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
            String newFileName = tokens[0] + "_blur." + tokens[1];

            GaussianFilter gFilter = new GaussianFilter(radius);


            BufferedImage blurImage = gFilter.filter(img, img);


            ImageIO.write(blurImage, "jpg", new File(newFileName));


        } catch (NumberFormatException e) {
            System.out.println("Radius should be a number");
        } catch (NullPointerException e) {
            System.out.println("File name is required");
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private static CommandLine parseArguments(String[] args) {


        Options options = new Options();
        options.addOption("f", "filename", true, "file name of image");
        options.addOption("r", "radius", true, "radius of Gaussian Filter");

        CommandLine line = null;
        CommandLineParser parser = new DefaultParser();
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return line;
    }


}

