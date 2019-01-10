import javax.swing.*;
import java.awt.*;

public class MapMakerWindow extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 650;

    private int cropX = 8;
    private int cropY = 67;
    private int cropWidth = 512;
    private int cropHeight = 384;
//    private int cropX;
//    private int cropY;
//    private int cropWidth;
//    private int cropHeight;
    private int offsets = 6;
    private Color backgroundColor = Color.WHITE;
    private JScrollPane mapMakerImageScrollPane;

    private MapMakerImagePanel mapMakerImagePanel;
    private ImagePreviewPanel imagePreviewPanel;
    private DirectionalButtonPanel directionalButtonPanel;
    private ZoomPanel zoomPanel;
    private SavePanel savePanel;
    private UndoButtonPanel undoButtonPanel;
    private JPanel imagePreviewSubPanel;

    private JPanel directionalButtonSubPanel;
    private JPanel zoomSubPanel;
    private JPanel saveSubPanel;
    private JPanel undoSubPanel;
    private JPanel directionalButtonAndImagePreviewPanel;

    private JPanel zoomAndImagePreviewPanel;
    private JPanel saveAndImagePreviewPanel;
    private JPanel undoAndImagePreviewPanel;
    private JTabbedPane optionTabbedPane;
    
    private MapMakerWindow() {
        setTitle("NES Map Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
//        setMinimumSize(new Dimension(700, 600));

        mapMakerImagePanel = new MapMakerImagePanel(this);
        mapMakerImageScrollPane = new JScrollPane(mapMakerImagePanel);
        imagePreviewPanel = new ImagePreviewPanel(this);
        directionalButtonPanel = new DirectionalButtonPanel(this);
        zoomPanel = new ZoomPanel(this);
        savePanel = new SavePanel(this);
        undoButtonPanel = new UndoButtonPanel(this);

        imagePreviewSubPanel = new JPanel();
        directionalButtonSubPanel = new JPanel();
        directionalButtonAndImagePreviewPanel = new JPanel();
        zoomSubPanel = new JPanel();
        zoomAndImagePreviewPanel = new JPanel();
        saveSubPanel = new JPanel();
        saveAndImagePreviewPanel = new JPanel();
        undoSubPanel = new JPanel();
        undoAndImagePreviewPanel = new JPanel();

        optionTabbedPane = new JTabbedPane(SwingConstants.RIGHT, JTabbedPane.SCROLL_TAB_LAYOUT);
        optionTabbedPane.addTab("EDIT", null, directionalButtonAndImagePreviewPanel, "Edit");
        optionTabbedPane.addTab("ZOOM", null, zoomAndImagePreviewPanel, "Zoom");
        optionTabbedPane.addTab("SAVE", null, saveAndImagePreviewPanel, "Save/Open");
        optionTabbedPane.addTab("UNDO", null, undoAndImagePreviewPanel, "Undo/Redo");
        optionTabbedPane.addTab("OPT.", null, new JPanel(), "Settings");

        initializePanels();
        initializeLayout();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MapMakerWindow();
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        add(mapMakerImageScrollPane);
//        add(directionalButtonAndImagePreviewPanel);
        add(optionTabbedPane);
    }

    private void initializePanels() {
        initializeSubPanel(imagePreviewSubPanel, imagePreviewPanel);
//        initializeDirectionalButtonSubPanel();  // TODO: Figure out how to prevent this panel resizing immediately
        initializeSubPanel(directionalButtonSubPanel, directionalButtonPanel);
//        initializeSubPanel(zoomSubPanel, zoomPanel);
//        initializeSubPanel(saveSubPanel, savePanel);
//        initializeSubPanel(undoSubPanel, undoButtonPanel);

        initializeSidePanel(directionalButtonAndImagePreviewPanel, directionalButtonSubPanel);
//        initializeSidePanel(zoomAndImagePreviewPanel, zoomSubPanel);
//        initializeSidePanel(saveAndImagePreviewPanel, saveSubPanel);
//        initializeSidePanel(undoAndImagePreviewPanel, undoSubPanel);
    }

    private void initializeSubPanel(JPanel subPanel, JPanel mainPanel) {
        subPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.NONE;
        subPanel.add(mainPanel, c);
    }

    private void initializeSidePanel(JPanel sidePanel, JPanel partialPanel) {
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        sidePanel.add(partialPanel);
//        directionalButtonAndImagePreviewPanel.add(Box.createVerticalGlue());
        JSeparator separator = new JSeparator();
        separator.setBackground(Color.GRAY);
        sidePanel.add(Box.createRigidArea(new Dimension(5, 5)));
        sidePanel.add(separator);
        sidePanel.add(Box.createRigidArea(new Dimension(5, 5)));
        sidePanel.add(imagePreviewSubPanel);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void setCropX(int cropX) {
        this.cropX = cropX;
    }

    public void setCropY(int cropY) {
        this.cropY = cropY;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    public void setOffsets(int offsets) {
        this.offsets = offsets;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public MapMakerImagePanel getMapMakerImagePanel() {
        return mapMakerImagePanel;
    }

    public ImagePreviewPanel getImagePreviewPanel() {
        return imagePreviewPanel;
    }

    public int getCropX() {
        return cropX;
    }

    public int getCropY() {
        return cropY;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public int getOffsets() {
        return offsets;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getScrollPanelHeight() {
        return mapMakerImageScrollPane.getViewport().getHeight();
    }

    public int getScrollPanelWidth() {
        return mapMakerImageScrollPane.getViewport().getWidth();
    }
}
