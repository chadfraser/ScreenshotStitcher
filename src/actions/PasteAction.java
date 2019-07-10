package actions;

import handler.ImageSaver;
import main.MainFrame;

import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;

public class PasteAction extends AbstractAction {
    private MainFrame mainFrame;

    PasteAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage croppedImage = cropImage();
        mainFrame.getImagePanel().getImageHandler().pasteToImage(croppedImage);
    }

    private BufferedImage cropImage() {
        int cropX = mainFrame.getCropX();
        int cropY = mainFrame.getCropY();
        int cropWidth = mainFrame.getCropWidth();
        int cropHeight = mainFrame.getCropHeight();

        try {
            return ImageSaver.cropClipboardImage(cropX, cropY, cropWidth, cropHeight);
        } catch (RasterFormatException except) {
            JOptionPane.showMessageDialog(mainFrame,
                    "The current width or height values are too large to crop.",
                    "Clipboard Image Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedFlavorException except) {
            JOptionPane.showMessageDialog(mainFrame,
                    "You do not currently have an image copied to the clipboard.",
                    "Clipboard Image Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException except) {
            except.printStackTrace();
        }
        return null;
    }
}