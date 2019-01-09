import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MapMaker {
    private static int SCREENSHOT_WIDTH = 512;
    private static int SCREENSHOT_HEIGHT = 384;
    private static int OFFSET = 6;

    private static int WIDTH = 4000;
    private static int HEIGHT = 4000;
    private int lastX = WIDTH / 2;
    private int lastY = HEIGHT / 2;

    private BufferedImage nesScreenMap;
    private Scanner scanner;

    public static void main(String[] args) {
        MapMaker mapMaker = new MapMaker();
        mapMaker.initializeMap();
        mapMaker.addRoomsToMap();
        mapMaker.saveImage();
    }

    private void initializeMap() {
        scanner = new Scanner(System.in);
        nesScreenMap = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = nesScreenMap.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, nesScreenMap.getWidth(), nesScreenMap.getHeight());
        g.dispose();
    }

    private void addRoomsToMap() {
        while (true) {
            Graphics g = nesScreenMap.getGraphics();
            BufferedImage currentImage = ImageSaver.cropClipboardImage();
            g.drawImage(currentImage, lastX, lastY, null);

            while (true) {
                System.out.println("Input direction to next room, or 'x' to exit. If the input begins with a '*', " +
                        "a new image will not be added to the map with this movement.");
                String currentLine = scanner.nextLine();
                if ("x".equals(currentLine.toLowerCase())) {
                    return;
                } else if (getDirectionFromInput(currentLine.toLowerCase())) {
                    break;
                }
            }
            if (lastX < 0 || lastX > nesScreenMap.getWidth() || lastY < 0 || lastY > nesScreenMap.getHeight()) {
                adjustImageSize();
            }
            System.out.println(lastX + " " + lastY);
        }
    }

    private boolean getDirectionFromInput(String currentLine) {
        if (currentLine.contains("up")) {
            lastY -= SCREENSHOT_HEIGHT + OFFSET;
//        } else if ("down".equals(currentLine.toLowerCase()) || "d".equals(currentLine.toLowerCase())) {
        } else if (currentLine.contains("down")) {
            lastY += SCREENSHOT_HEIGHT + OFFSET;
        } else if (currentLine.contains("left")) {
            lastX -= SCREENSHOT_WIDTH + OFFSET;
        } else if (currentLine.contains("right")) {
            lastX += SCREENSHOT_WIDTH + OFFSET;
        } else {
            System.out.println("Input not recognized.");
            return false;
        }
        return (currentLine.indexOf("*") != 0);
    }

    private void adjustImageSize() {
        int newWidth  = nesScreenMap.getWidth();
        int newHeight = nesScreenMap.getHeight();
        int shiftedXValue = 0;
        int shiftedYValue = 0;

        if (lastX < 0) {
            newWidth += -lastX;
            shiftedXValue = -lastX;
            lastX = 0;
        } else if (lastX > nesScreenMap.getWidth()) {
            newWidth += (lastX - nesScreenMap.getWidth());
        }

        if (lastY < 0) {
            newHeight += -lastY;
            shiftedYValue = -lastY;
            lastY = 0;
        } else if (lastY > nesScreenMap.getHeight()) {
            newHeight += (lastY - nesScreenMap.getHeight());
        }

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        g.drawImage(nesScreenMap, shiftedXValue, shiftedYValue, null);
        g.dispose();

        nesScreenMap = newImage;
    }

    private void saveImage() {
        try {
            int fileCount = 0;
            File outputFile;
            do {
                String fileName = "saved_map_" + fileCount + ".png";
                outputFile = new File(fileName);
                fileCount++;
            } while (outputFile.exists() && fileCount < 50);
            ImageIO.write(nesScreenMap, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void test(BufferedImage currentImage, Direction direction) {
//        int width = currentImage.getWidth();
//        int height = currentImage.getHeight();
//        if (direction == Direction.UP || direction == Direction.DOWN) {
//            height += screenshotHeight + offset;
//        } else {
//            width += screenshotWidth + offset;
//        }
//        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        Graphics g = newImage.getGraphics();
//        if (direction == Direction.UP) {
//            g.drawImage(currentImage, 0, newImage.getHeight(), null);
//        } else if (direction == Direction.DOWN) {
//            g.drawImage(currentImage, 0, newImage.getHeight(), null);
//        } else if (direction == Direction.LEFT) {
//            g.drawImage(currentImage, newImage.getWidth(), 0, null);
//        } else {
//            g.drawImage(currentImage, newImage.getWidth(), 0, null);
//        }
//    }
}
