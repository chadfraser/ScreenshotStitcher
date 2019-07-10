package main;

import actions.ActionHandler;
import actions.ActionShortcutHandler;
import panels.*;
import zoom.ZoomValue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 650;

    private int cropX = 8;
    private int cropY = 67;
    private int cropWidth = 512;
    private int cropHeight = 384;
    private int offsets = 6;
    private Color backgroundColor = Color.WHITE;
    private ZoomValue zoomValue = ZoomValue.FIT_TO_SCREEN;
    private JScrollPane mapMakerImageScrollPane;

    private ImagePanel imagePanel;
    private ImagePreviewPanel imagePreviewPanel;
    private EditButtonPanel editButtonPanel;
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
    private JPanel trimAndImagePreviewPanel;
    private JPanel dataAndImagePreviewPanel;
    private JTabbedPane optionTabbedPane;

    private ActionHandler actionHandler;
    private ActionShortcutHandler actionShortcutHandler;

    private MainFrame() {
        setTitle("NES Map Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
//        setMinimumSize(new Dimension(700, 600));

        imagePanel = new ImagePanel(this);
        mapMakerImageScrollPane = new JScrollPane(imagePanel);
        imagePreviewPanel = new ImagePreviewPanel(this);
        editButtonPanel = new EditButtonPanel(this);
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
        trimAndImagePreviewPanel = new JPanel();
        dataSubPanel = new JPanel();
        dataAndImagePreviewPanel = new JPanel();

        optionTabbedPane = new JTabbedPane(SwingConstants.RIGHT, JTabbedPane.SCROLL_TAB_LAYOUT);
        optionTabbedPane.addTab("EDIT", null, directionalButtonAndImagePreviewPanel, "Edit");
        optionTabbedPane.addTab("ZOOM", null, zoomAndImagePreviewPanel, "Zoom");
        optionTabbedPane.addTab("SAVE", null, saveAndImagePreviewPanel, "Save/Open");
        optionTabbedPane.addTab("UNDO", null, undoAndImagePreviewPanel, "Undo/Redo");
        optionTabbedPane.addTab("TRIM", null, trimAndImagePreviewPanel, "Trim");
        optionTabbedPane.addTab("OPT.", null, dataAndImagePreviewPanel, "Settings");
        optionTabbedPane.addChangeListener(e -> {
            JPanel selectedPanel = (JPanel) optionTabbedPane.getSelectedComponent();
            moveImagePreviewPanelToActivePanel(selectedPanel);
        });

        initializePanels();
        initializeLayout();
        pack();
        setLocationRelativeTo(null);
        imagePanel.updateImages();
        setVisible(true);
        revalidate();

        imagePreviewPanel.getTimer().start();

        actionHandler = new ActionHandler(this);
        actionShortcutHandler = new ActionShortcutHandler(this);
        add(actionShortcutHandler);

        // TODO: Add component listener for window resize
        // TODO: Fix initialization of MapMakerImagePanel size
    }

    public static void main(String[] args) {
        new MainFrame();
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
        initializeSubPanel(directionalButtonSubPanel, editButtonPanel);
        initializeSubPanel(zoomSubPanel, zoomPanel);
        initializeSubPanel(saveSubPanel, savePanel);
        initializeSubPanel(undoSubPanel, undoButtonPanel);
        initializeSubPanel(trimSubPanel, trimPanel);
        initializeSubPanel(dataSubPanel, dataPanel);

        initializeSidePanel(directionalButtonAndImagePreviewPanel, directionalButtonSubPanel);
        initializeSidePanel(zoomAndImagePreviewPanel, zoomSubPanel);
        initializeSidePanel(saveAndImagePreviewPanel, saveSubPanel);
        initializeSidePanel(undoAndImagePreviewPanel, undoSubPanel);
        initializeSidePanel(trimAndImagePreviewPanel, trimSubPanel);
        initializeSidePanel(dataAndImagePreviewPanel, dataSubPanel);

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

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public ImagePreviewPanel getImagePreviewPanel() {
        return imagePreviewPanel;
    }

    public SavePanel getSavePanel() {
        return savePanel;
    }

    public UndoButtonPanel getUndoButtonPanel() {
        return undoButtonPanel;
    }

    public String getSelectedTabTitle() {
        int selectedIndex = optionTabbedPane.getSelectedIndex();
        return optionTabbedPane.getTitleAt(selectedIndex);
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

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public int getScrollPanelHeight() {
        return mapMakerImageScrollPane.getViewport().getHeight();
    }

    public int getScrollPanelWidth() {
        return mapMakerImageScrollPane.getViewport().getWidth();
    }

    public BufferedImage getMainStoredImage() {
        return imagePanel.getImageHandler().getStoredImage();
    }
}
