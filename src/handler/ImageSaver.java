package handler;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageSaver implements ClipboardOwner {
    public static void main(String[] args) {
    }

    public static BufferedImage cropClipboardImage(int x, int y, int width, int height)
            throws UnsupportedFlavorException, IOException {
        BufferedImage bufferedImage = getImageFromClipboard();
        if (bufferedImage != null) {
            bufferedImage = cropImage(x, y, width, height, bufferedImage);
            return bufferedImage;
        }
        return null;
    }

    private static BufferedImage getImageFromClipboard() throws UnsupportedFlavorException, IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        return (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
    }

    private static BufferedImage cropImage(int x, int y, int width, int height, BufferedImage image) {
        return image.getSubimage(x, y, width, height);
    }

    public void lostOwnership(Clipboard clipboard, Transferable transferable) {
        System.out.println("Lost Clipboard Ownership");
    }
}
