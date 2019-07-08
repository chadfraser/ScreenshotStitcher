import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PreviewAndSelectionPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
//    private static final int HEIGHT = MapMakerWindow.getConstantHeight();

    private BufferedImage previewPanel;
    private MapMakerWindow mapMakerWindow;
    private DirectionalButtonPanel buttonPanel;
    private PasteButtonPanel pasteButtonPanel;
    private ImagePreviewPanel imagePreviewPanel;

    PreviewAndSelectionPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        final int HEIGHT = mapMakerWindow.getHeight();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        buttonPanel = new DirectionalButtonPanel(mapMakerWindow);
        pasteButtonPanel = new PasteButtonPanel(mapMakerWindow);
        imagePreviewPanel = new ImagePreviewPanel(mapMakerWindow);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(buttonPanel);
        add(Box.createVerticalGlue());
        add(pasteButtonPanel);
        add(imagePreviewPanel);
//        pack();
//        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public static int getConstantWidth() {
        return WIDTH;
    }

    public static int getConstantHeight() {
        return HEIGHT;
    }
}
