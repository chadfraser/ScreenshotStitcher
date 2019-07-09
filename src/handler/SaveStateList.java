package handler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveStateList extends ArrayList<BufferedImage>  implements Serializable {
    private static final int MAX_CAPACITY = 10;
    private transient List<BufferedImage> images;
    private int currentImageIndex;

    public SaveStateList() {
        images = new ArrayList<>();
        currentImageIndex = -1;
    }

    public SaveStateList(List<BufferedImage> images) {
        this.images = images;
        currentImageIndex = -1;
    }

    public SaveStateList(List<BufferedImage> images, int currentImageIndex) {
        this.images = images;
        this.currentImageIndex = currentImageIndex;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(images.size());
        for (BufferedImage eachImage : images) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(eachImage, "png", byteArrayOutputStream);
            out.writeInt(byteArrayOutputStream.size());
            byteArrayOutputStream.writeTo(out);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        final int imageCount = in.readInt();
        images = new ArrayList<>(imageCount);
        for (int i = 0; i < imageCount; i++) {
            int size = in.readInt();
            byte[] buffer = new byte[size];
            in.readFully(buffer);
            images.add(ImageIO.read(new ByteArrayInputStream(buffer)));
        }
    }

    public BufferedImage getPreviousState() {
        if (currentImageIndex <= 0 || currentImageIndex >= images.size()) {
            return null;
        }
        currentImageIndex--;
        return images.get(currentImageIndex);
    }

    public BufferedImage getNextState() {
        if (currentImageIndex < 0 || currentImageIndex >= images.size() - 1) {
            return null;
        }
        currentImageIndex++;
        return images.get(currentImageIndex);
    }

    @Override
    public boolean add(BufferedImage bufferedImage) {
        // First, delete all elements from the list past the currentImageIndex
        // This way, we prevent discard future save states that would revert the state of the image
        if (currentImageIndex < images.size() - 1 && currentImageIndex >= 0) {
            images = images.subList(0, currentImageIndex + 1);
        }

        // If the list has more elements than MAX_CAPACITY, remove the first element from the list to keep it under the
        // size constraint
        // This ensures that at most the past (n <= 10) states are always stored
        if (images.size() >= MAX_CAPACITY) {
            images.remove(0);
        }
        boolean result = images.add(bufferedImage);
        currentImageIndex = images.size() - 1;
        return result;
    }
}
