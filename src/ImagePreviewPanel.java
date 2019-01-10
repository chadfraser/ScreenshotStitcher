import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePreviewPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;

    private BufferedImage previewImage;
    private MapMakerWindow mapMakerWindow;

    ImagePreviewPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        setFocusable(true);

        previewImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public void updatePreviewPanel(BufferedImage newPreviewImage) {
        previewImage = newPreviewImage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(previewImage, 0, 0, null);
    }

    @Override
    public final Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        Dimension prefSize;
        Component component = getParent();

        if (component == null) {
            prefSize = new Dimension((int) dimension.getWidth(), (int) dimension.getHeight());
        } else if (component.getWidth() > dimension.getWidth() && component.getHeight() > dimension.getHeight()) {
            prefSize = component.getSize();
        } else {
            prefSize = dimension;
        }
        int width = (int) prefSize.getWidth();
        int height = (int) prefSize.getHeight();
        int sideLength = (width > height ? height : width);
        return new Dimension(sideLength, sideLength);
    }

    @Override
    public final Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public BufferedImage getPreviewImage() {
        return previewImage;
    }
}
