package handler;

import utils.RectCursor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageState implements Serializable {
    private transient BufferedImage image;
    private RectCursor rectCursor;

    ImageState(BufferedImage image, RectCursor baseRectCursor) {
        this.image = image;
        this.rectCursor = baseRectCursor.getClone();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        out.writeInt(byteArrayOutputStream.size());
        byteArrayOutputStream.writeTo(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int size = in.readInt();
        byte[] buffer = new byte[size];
        in.readFully(buffer);
        image = ImageIO.read(new ByteArrayInputStream(buffer));
    }

    public BufferedImage getImage() {
        return image;
    }

    public RectCursor getRectCursor() {
        return rectCursor;
    }
}
