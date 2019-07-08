package serialize;

import main.MainFrame;
import zoom.ZoomValue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class StoredData implements Serializable {
    private byte[] storedImageByteArray;
    private ZoomValue zoomValue;
    private Color backgroundColor;
    private String imageFile;
    private String dataFile;
    private int cropX;
    private int cropY;
    private int cropWidth;
    private int cropHeight;
    private int offsets;
    private List<BufferedImage> listOfChangedImages;
    // TODO: Implement data settings: save settings, shortcuts
    // TODO: Test serialization methods

    public static void serializeData(MainFrame mainFrame, String fileName) {
        StoredData storedData = new StoredData();
        storedData.setData(mainFrame);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(storedData);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializeData(MainFrame mainFrame, File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            StoredData storedData = (StoredData) in.readObject();
            in.close();
            fileIn.close();
            storedData.applyData(mainFrame);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // TODO: Find better name for this method
    private void setData(MainFrame mainFrame) {
        storedImageByteArray = buildByteArray(mainFrame);
        if (storedImageByteArray == null) {
            return;
        }
        zoomValue = mainFrame.getZoomValue();
        backgroundColor = mainFrame.getBackgroundColor();
//        imageFile = mainFrame.get TODO: Include serialization of JTextArea strings
        cropX = mainFrame.getCropX();
        cropY = mainFrame.getCropY();
        cropWidth = mainFrame.getCropWidth();
        cropHeight = mainFrame.getCropHeight();
        offsets = mainFrame.getOffsets();
    }

    private byte[] buildByteArray(MainFrame mainFrame) {
        BufferedImage tempImage = mainFrame.getMainStoredImage();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(tempImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] tempByteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return tempByteArray;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,
                    "An error occurred while trying to serialize the currently-stored image.",
                    "Data Serialization Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // TODO: Find better name for this method
    private void applyData(MainFrame mainFrame) {
        BufferedImage tempImage = buildBufferedImage(storedImageByteArray, mainFrame);
        mainFrame.setZoomValue(zoomValue);
        mainFrame.setBackgroundColor(backgroundColor);
        mainFrame.setCropX(cropX);
        mainFrame.setCropY(cropY);
        mainFrame.setCropWidth(cropWidth);
        mainFrame.setCropHeight(cropHeight);
        mainFrame.setOffsets(offsets);
        mainFrame.getImagePanel().getImageHandler().updateImages(tempImage);
    }

    private BufferedImage buildBufferedImage(byte[] bytes, MainFrame mainFrame) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            return ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,
                    "An error occurred while trying to deserialize the saved stored image.",
                    "Data Deserialization Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
