package handler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveStateList extends ArrayList<BufferedImage> implements Serializable {
    private static final int MAX_CAPACITY = 10;
    private transient List<BufferedImage> images;
    private int currentImageIndex;

    SaveStateList() {
        images = new ArrayList<>();
        currentImageIndex = -1;
    }

    // Write int fields as normal, and each image in the images list to a byte array output stream for serialization
    // This prevents the loss or corruption of data from images getting written and read incompletely
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

    // Read int fields as normal, and fully read each image from the byte array input stream to deserialize
    // This prevents the loss or corruption of data from images getting written and read incompletely
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

    // Returns the BufferedImage in the images list one index behind the currentImageIndex, or null if no such image
    // exists
    // Also decrements currentImageIndex to represent that the returned image is the new 'current image"
    BufferedImage getPreviousState() {
        if (currentImageIndex <= 0 || currentImageIndex >= images.size()) {
            return null;
        }
        currentImageIndex--;
        return images.get(currentImageIndex);
    }


    // Returns the BufferedImage in the images list one index ahead of the currentImageIndex, or null if no such image
    // exists
    // Also increments currentImageIndex to represent that the returned image is the new 'current image"
    BufferedImage getNextState() {
        if (currentImageIndex < 0 || currentImageIndex >= images.size() - 1) {
            return null;
        }
        currentImageIndex++;
        return images.get(currentImageIndex);
    }

    // Returns the BufferedImage in the images list one index behind the currentImageIndex, or null if no such image
    // exists
    // Does not alter the currentImageIndex, as the returned image is *not* the new 'current image"
    public BufferedImage pollPreviousState() {
        if (currentImageIndex <= 0 || currentImageIndex >= images.size()) {
            return null;
        }
        return images.get(currentImageIndex - 1);
    }


    // Returns the BufferedImage in the images list one index ahead of the currentImageIndex, or null if no such image
    // exists
    // Does not alter the currentImageIndex, as the returned image is *not* the new 'current image"
    public BufferedImage pollNextState() {
        if (currentImageIndex < 0 || currentImageIndex >= images.size() - 1) {
            return null;
        }
        return images.get(currentImageIndex + 1);
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
