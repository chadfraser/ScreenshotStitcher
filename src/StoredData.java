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
    private List<BufferedImage> undoListImages;
    // TODO: Implement data settings: save settings, shortcuts
    // TODO: Test serialization methods

    public static void serializeData(MapMakerWindow mapMakerWindow, String fileName) {
        StoredData storedData = new StoredData();
        storedData.setData(mapMakerWindow);

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

    public static void deserializeData(MapMakerWindow mapMakerWindow, File file) {
        StoredData storedData;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            storedData = (StoredData) in.readObject();
            in.close();
            fileIn.close();
            storedData.applyData(mapMakerWindow);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // TODO: Find better name for this method
    private void setData(MapMakerWindow mapMakerWindow) {
        storedImageByteArray = buildByteArray(mapMakerWindow);
        if (storedImageByteArray == null) {
            return;
        }
        zoomValue = mapMakerWindow.getZoomValue();
        backgroundColor = mapMakerWindow.getBackgroundColor();
//        imageFile = mapMakerWindow.get TODO: Include serialization of JTextArea strings
        cropX = mapMakerWindow.getCropX();
        cropY = mapMakerWindow.getCropY();
        cropWidth = mapMakerWindow.getCropWidth();
        cropHeight = mapMakerWindow.getCropHeight();
        offsets = mapMakerWindow.getOffsets();
    }

    private byte[] buildByteArray(MapMakerWindow mapMakerWindow) {
        BufferedImage tempImage = mapMakerWindow.getMapMakerImagePanel().getStoredImage();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(tempImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] tempByteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return tempByteArray;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mapMakerWindow,
                    "An error occurred while trying to serialize the currently-stored image.",
                    "Data Serialization Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // TODO: Find better name for this method
    private void applyData(MapMakerWindow mapMakerWindow) {
        BufferedImage tempImage = buildBufferedImage(storedImageByteArray, mapMakerWindow);
        mapMakerWindow.getMapMakerImagePanel().setStoredImage(tempImage);
        mapMakerWindow.setZoomValue(zoomValue);
        mapMakerWindow.setBackgroundColor(backgroundColor);
        mapMakerWindow.setCropX(cropX);
        mapMakerWindow.setCropY(cropY);
        mapMakerWindow.setCropWidth(cropWidth);
        mapMakerWindow.setCropHeight(cropHeight);
        mapMakerWindow.setOffsets(offsets);

        mapMakerWindow.getMapMakerImagePanel().updateImages();
    }

    private BufferedImage buildBufferedImage(byte[] bytes, MapMakerWindow mapMakerWindow) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            return ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mapMakerWindow,
                    "An error occurred while trying to deserialize the saved stored image.",
                    "Data Deserialization Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
