import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class StoredData implements Serializable {
    private BufferedImage imagePanelStoredImage;
    private ZoomValue zoomValue;
    private int cropX;
    private int cropY;
    private int cropWidth;
    private int cropHeight;
    private int offsets;
    private Color backgroundColor;
    private List<BufferedImage> undoListImages;
    // TODO: Implement data settings: save settings, shortcuts

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
        imagePanelStoredImage = mapMakerWindow.getMapMakerImagePanel().getStoredImage();
        zoomValue = mapMakerWindow.getZoomValue();
        backgroundColor = mapMakerWindow.getBackgroundColor();
        cropX = mapMakerWindow.getCropX();
        cropY = mapMakerWindow.getCropY();
        cropWidth = mapMakerWindow.getCropWidth();
        cropHeight = mapMakerWindow.getCropHeight();
        offsets = mapMakerWindow.getOffsets();
    }

    // TODO: Find better name for this method
    private void applyData(MapMakerWindow mapMakerWindow) {
        mapMakerWindow.getMapMakerImagePanel().setStoredImage(imagePanelStoredImage);
        mapMakerWindow.setZoomValue(zoomValue);
        mapMakerWindow.setBackgroundColor(backgroundColor);
        mapMakerWindow.setCropX(cropX);
        mapMakerWindow.setCropY(cropY);
        mapMakerWindow.setCropWidth(cropWidth);
        mapMakerWindow.setCropHeight(cropHeight);
        mapMakerWindow.setOffsets(offsets);

        mapMakerWindow.getMapMakerImagePanel().updateImages();
    }
}
