import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePreviewPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = PreviewAndSelectionPanel.getConstantWidth();
    private static final int HEIGHT = PreviewAndSelectionPanel.getConstantWidth();

    private BufferedImage previewPanel;
    private MapMakerWindow mapMakerWindow;

    ImagePreviewPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLUE);
        setFocusable(true);
    }
}
