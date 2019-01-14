import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class StoredData implements Serializable {
    private BufferedImage imagePanelStoredImage;
    private String ZoomValue;
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
        storedData.imagePanelStoredImage = mapMakerWindow.getMapMakerImagePanel().getStoredImage();
        storedData.backgroundColor = mapMakerWindow.getBackgroundColor();
        storedData.cropX = mapMakerWindow.getCropX();
        storedData.cropY = mapMakerWindow.getCropY();
        storedData.cropWidth = mapMakerWindow.getCropWidth();
        storedData.cropHeight = mapMakerWindow.getCropHeight();
        storedData.offsets = mapMakerWindow.getOffsets();

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

    public static StoredData deserializeData(String fileName) {
        StoredData storedData = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            storedData = (StoredData) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return storedData;
    }
}
