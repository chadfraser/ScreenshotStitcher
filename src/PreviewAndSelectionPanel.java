import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PreviewAndSelectionPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 512;
    private static final int HEIGHT = 448;

    private BufferedImage previewPanel;
    private MapMakerWindow mapMakerWindow;
    private DirectionalButtonPanel buttonPanel;
    private PasteButtonPanel pasteButtonPanel;
    private ImagePreviewPanel imagePreviewPanel;

    PreviewAndSelectionPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

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
}
