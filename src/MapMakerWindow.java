import javax.swing.*;
import java.awt.*;

public class MapMakerWindow extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 650;

    private MapMakerImagePanel mapMakerImagePanel;
    private ImagePreviewPanel imagePreviewPanel;
    private DirectionalButtonPanel directionalButtonPanel;

    private JPanel imagePreviewSubPanel;
    private JPanel directionalButtonSubPanel;
    private JPanel directionalButtonAndImagePreviewPanel;
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
        imagePreviewPanel = new ImagePreviewPanel(this);
        directionalButtonPanel = new DirectionalButtonPanel(this);
//        settingsPanel = new SettingsPanel(this);

        imagePreviewSubPanel = new JPanel();
        directionalButtonSubPanel = new JPanel();
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

    private void initializeLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        add(mapMakerImagePanel);
//        add(directionalButtonAndImagePreviewPanel);
        add(optionTabbedPane);
    }

    private void initializePanels() {
        initializeImagePreviewSubPanel();
        initializeDirectionalButtonSubPanel();  // TODO: Figure out how to prevent this panel resizing immediately
        initializeDirectionalImagePreviewPanel();
    }

    private void initializeImagePreviewSubPanel() {
        imagePreviewSubPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.NONE;
        imagePreviewSubPanel.add(imagePreviewPanel, c);
    }

    private void initializeDirectionalButtonSubPanel() {
        directionalButtonSubPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.NONE;
        directionalButtonSubPanel.add(directionalButtonPanel, c);
    }

    private void initializeDirectionalImagePreviewPanel() {
        directionalButtonAndImagePreviewPanel.setLayout(new BoxLayout(directionalButtonAndImagePreviewPanel,
                                                                      BoxLayout.PAGE_AXIS));
        directionalButtonAndImagePreviewPanel.add(directionalButtonSubPanel);
//        directionalButtonAndImagePreviewPanel.add(Box.createVerticalGlue());
        JSeparator separator = new JSeparator();
        separator.setBackground(Color.GRAY);
        directionalButtonAndImagePreviewPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        directionalButtonAndImagePreviewPanel.add(separator);
        directionalButtonAndImagePreviewPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        directionalButtonAndImagePreviewPanel.add(imagePreviewSubPanel);
        directionalButtonAndImagePreviewPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public MapMakerImagePanel getMapMakerImagePanel() {
        return mapMakerImagePanel;
    }
}
