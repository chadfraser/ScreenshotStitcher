import javax.swing.*;
import java.awt.*;

public class MapMakerWindow extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 650;

    private MapMakerImagePanel mapMakerImagePanel;
    private ImagePreviewPanel imagePreviewPanel;
    private DirectionalButtonPanel directionalButtonPanel;

    private JPanel directionalButtonAndImagePreviewPanel;
    private JTabbedPane optionTabbedPane;

    private MapMakerWindow() {
        setTitle("NES Map Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
//        setResizable(true);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
//        setMinimumSize(new Dimension(700, 600));

        mapMakerImagePanel = new MapMakerImagePanel(this);
        imagePreviewPanel = new ImagePreviewPanel(this);
        directionalButtonPanel = new DirectionalButtonPanel(this);
//        settingsPanel = new SettingsPanel(this);

        directionalButtonAndImagePreviewPanel = new JPanel();

        optionTabbedPane = new JTabbedPane(SwingConstants.RIGHT, JTabbedPane.SCROLL_TAB_LAYOUT);
        optionTabbedPane.addTab("EDIT", null, directionalButtonAndImagePreviewPanel, "Edit");
        optionTabbedPane.addTab("ZOOM", null, new JPanel(), "Zoom");
        optionTabbedPane.addTab("SAVE", null, new JPanel(), "Save/Open");
        optionTabbedPane.addTab("UNDO", null, new JPanel(), "Undo/Redo");
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

//    public static int getConstantWidth() {
//        return WIDTH;
//    }
//
//    public static int getConstantHeight() {
//        return HEIGHT;
//    }

    private void initializeLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        add(mapMakerImagePanel);
//        add(directionalButtonAndImagePreviewPanel);
        add(optionTabbedPane);
//        setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        c.fill = GridBagConstraints.NONE;
//        c.anchor = GridBagConstraints.LINE_START;
//        c.insets = new Insets(5, 5, 5, 5);
//        c.weightx = 0.5;
//        c.weighty = 0.5;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.gridx = 0;
//        c.gridy = 0;
//        add(mapMakerImagePanel, c);
//
//        c.weightx = 0.5;
//        c.weighty = 0.5;
//        c.gridwidth = 1;
//        c.gridheight = 2;
//        c.gridx = 1;
//        c.gridy = 0;
//        add(directionalButtonAndImagePreviewPanel, c);

//        c.weightx = 0.5;
//        c.weighty = 0.5;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.gridx = 1;
//        c.gridy = 2;
//        add(Box.createGlue(), c);
//
//        c.weightx = 0.5;
//        c.weighty = 0.5;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.gridx = 1;
//        c.gridy = 3;
//        add(imagePreviewPanel, c);
    }

    private void initializePanels() {
        initializeDirectionalImagePreviewPanel();
    }

    private void initializeDirectionalImagePreviewPanel() {
        directionalButtonAndImagePreviewPanel.setLayout(new BoxLayout(directionalButtonAndImagePreviewPanel,
                                                                      BoxLayout.PAGE_AXIS));
        directionalButtonAndImagePreviewPanel.add(directionalButtonPanel);
//        directionalButtonAndImagePreviewPanel.add(Box.createVerticalGlue());
        JSeparator separator = new JSeparator();
        separator.setBackground(Color.GRAY);
//        directionalButtonAndImagePreviewPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        directionalButtonAndImagePreviewPanel.add(separator);
        directionalButtonAndImagePreviewPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        directionalButtonAndImagePreviewPanel.add(imagePreviewPanel);
        directionalButtonAndImagePreviewPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public MapMakerImagePanel getMapMakerImagePanel() {
        return mapMakerImagePanel;
    }
}
