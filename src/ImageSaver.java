import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;


public class ImageSaver implements ClipboardOwner {
    public static void main(String[] args) {
    }

    static BufferedImage cropClipboardImage() {
        BufferedImage bufferedImage = getImageFromClipboard();
        if (bufferedImage != null) {
            bufferedImage = cropImage(bufferedImage);
            return bufferedImage;
        }
        return null;
    }

    private static BufferedImage getImageFromClipboard() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                return (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
//            } catch (UnsupportedFlavorException | IOException e) {
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("You do not have a screenshot currently copied. Press 'enter' to try again.");
                scanner.nextLine();
            }
        }
//        return null;
    }

    private void copyImage(BufferedImage bufferedImage)
    {
        TransferableImage transferableImage = new TransferableImage(bufferedImage);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(transferableImage, this);
    }

    private static BufferedImage cropImage(int x, int y, int width, int height, BufferedImage image) {
        return image.getSubimage(x, y, width, height);
    }

    private static BufferedImage cropImage(BufferedImage image) {
        return image.getSubimage(8, 67, 512, 384);
    }

    public void lostOwnership(Clipboard clipboard, Transferable transferable) {
        System.out.println("Lost Clipboard Ownership");
    }

    private class TransferableImage implements Transferable {
        Image image;

        TransferableImage(Image image) {
            this.image = image;
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (flavor.equals(DataFlavor.imageFlavor) && image != null ) {
                return image;
            }
            else {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (DataFlavor f : flavors) {
                if ( flavor.equals(f) ) {
                    return true;
                }
            }
            return false;
        }
    }
}
