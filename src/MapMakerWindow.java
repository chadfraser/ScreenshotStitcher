import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EventListener;

public class MapMakerWindow extends JFrame implements Serializable {
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
//    private ZoomValue zoomValue = ZoomValue.FIT_TO_SCREEN;
    private ZoomValue zoomValue = ZoomValue.ONE_HUNDRED_PERCENT;
    private JScrollPane mapMakerImageScrollPane;

    private MapMakerImagePanel mapMakerImagePanel;
    private ImagePreviewPanel imagePreviewPanel;
    private DirectionalButtonPanel directionalButtonPanel;
    private ZoomPanel zoomPanel;
    private SavePanel savePanel;
    private UndoButtonPanel undoButtonPanel;
    private TrimPanel trimPanel;
    private DataPanel dataPanel;

    private JPanel imagePreviewSubPanel;
    private JPanel directionalButtonSubPanel;
    private JPanel zoomSubPanel;
    private JPanel saveSubPanel;
    private JPanel undoSubPanel;
    private JPanel trimSubPanel;
    private JPanel dataSubPanel;

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
        trimPanel = new TrimPanel(this);
        dataPanel = new DataPanel(this);

        imagePreviewSubPanel = new JPanel();
        directionalButtonSubPanel = new JPanel();
        directionalButtonAndImagePreviewPanel = new JPanel();
        zoomSubPanel = new JPanel();
        zoomAndImagePreviewPanel = new JPanel();
        saveSubPanel = new JPanel();
        saveAndImagePreviewPanel = new JPanel();
        undoSubPanel = new JPanel();
        undoAndImagePreviewPanel = new JPanel();
        trimSubPanel = new JPanel();
        dataSubPanel = new JPanel();

        optionTabbedPane = new JTabbedPane(SwingConstants.RIGHT, JTabbedPane.SCROLL_TAB_LAYOUT);
        optionTabbedPane.addTab("EDIT", null, directionalButtonAndImagePreviewPanel, "Edit");
        optionTabbedPane.addTab("ZOOM", null, zoomAndImagePreviewPanel, "Zoom");
        optionTabbedPane.addTab("SAVE", null, saveAndImagePreviewPanel, "Save/Open");
        optionTabbedPane.addTab("UNDO", null, undoAndImagePreviewPanel, "Undo/Redo");
        optionTabbedPane.addTab("TRIM", null, trimSubPanel, "Trim");
        optionTabbedPane.addTab("OPT.", null, dataSubPanel, "Settings");
        optionTabbedPane.addChangeListener(e -> {
            JPanel selectedPanel = (JPanel) optionTabbedPane.getSelectedComponent();
            moveImagePreviewPanelToActivePanel(selectedPanel);
        });

        initializePanels();
        initializeLayout();
        pack();
        setLocationRelativeTo(null);
        mapMakerImagePanel.updateImages();
        setVisible(true);
        revalidate();
        // TODO: Add component listener for window resize
        // TODO: Fix initialization of MapMakerImagePanel size
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

        imagePreviewSubPanel.setBackground(Color.YELLOW);  // TODO: Remove after testing size of panel is done
//        initializeDirectionalButtonSubPanel();  // TODO: Figure out how to prevent this panel resizing immediately
        initializeSubPanel(directionalButtonSubPanel, directionalButtonPanel);
        initializeSubPanel(zoomSubPanel, zoomPanel);
        initializeSubPanel(saveSubPanel, savePanel);
        initializeSubPanel(undoSubPanel, undoButtonPanel);
        initializeSubPanel(dataSubPanel, dataPanel);
        initializeSubPanel(trimSubPanel, trimPanel);

        initializeSidePanel(directionalButtonAndImagePreviewPanel, directionalButtonSubPanel);
        initializeSidePanel(zoomAndImagePreviewPanel, zoomSubPanel);
        initializeSidePanel(saveAndImagePreviewPanel, saveSubPanel);
        initializeSidePanel(undoAndImagePreviewPanel, undoSubPanel);

        moveImagePreviewPanelToActivePanel(directionalButtonAndImagePreviewPanel);
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
//        separator.setBackground(Color.GRAY);
        sidePanel.add(Box.createRigidArea(new Dimension(5, 5)));
        sidePanel.add(separator);
        sidePanel.add(Box.createRigidArea(new Dimension(5, 5)));
    }

    private void moveImagePreviewPanelToActivePanel(JPanel activePanel) {
        activePanel.add(imagePreviewSubPanel);
        activePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

    public void setZoomValue(ZoomValue zoomValue) {
        this.zoomValue = zoomValue;
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

    public ZoomValue getZoomValue() {
        return zoomValue;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public JScrollPane getMapMakerImageScrollPane() {
        return mapMakerImageScrollPane;
    }

    public int getScrollPanelHeight() {
        return mapMakerImageScrollPane.getViewport().getHeight();
    }

    public int getScrollPanelWidth() {
        return mapMakerImageScrollPane.getViewport().getWidth();
    }
}
