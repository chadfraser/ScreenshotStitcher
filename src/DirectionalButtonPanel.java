import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;

class DirectionalButtonPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JPanel leftButtonPanel;
    private JPanel upDownButtonPanel;
    private JPanel rightButtonPanel;
    private JPanel editButtonPanel;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton pasteButton;
    private JButton deleteButton;

    private MapMakerWindow mapMakerWindow;

    DirectionalButtonPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.RED);
        setFocusable(true);

        leftButtonPanel = new JPanel(new GridBagLayout());
        upDownButtonPanel = new JPanel(new GridBagLayout());
        rightButtonPanel = new JPanel(new GridBagLayout());
        editButtonPanel = new JPanel(new GridBagLayout());

        initializeButtons();
        initializePanels();
        initializeLayout();
    }

    private void initializeButtons() {
        upButton = new JButton("UP");
        downButton = new JButton("DOWN");
        leftButton = new JButton("LEFT");
        rightButton = new JButton("RIGHT");
        pasteButton = new JButton("PASTE");
        deleteButton = new JButton("DELETE");

        upButton.addActionListener(this);
        downButton.addActionListener(this);
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
        pasteButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.5;
        c.weighty = 0.45;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(leftButtonPanel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        add(upDownButtonPanel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 0;
        add(rightButtonPanel, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(Box.createGlue(), c);

        c.weighty = 0.1;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 2;
        add(editButtonPanel, c);
    }

    private void initializePanels() {
        int insetWidth = WIDTH / 125;
        int insetHeight = HEIGHT / 90;

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(insetHeight, insetWidth, insetHeight, insetWidth);

        initializeSidePanel(c, leftButtonPanel, leftButton);
        initializeSidePanel(c, rightButtonPanel, rightButton);
        initializeUpDownButtonPanel(c);
        initializeEditButtonPanel(c);
    }

    private void initializeSidePanel(GridBagConstraints c, JPanel sidePanel, JButton sideButton) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        sidePanel.add(Box.createGlue(), c);

        c.gridwidth = 1;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 1;
        sidePanel.add(sideButton, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        sidePanel.add(Box.createGlue(), c);
    }

    private void initializeUpDownButtonPanel(GridBagConstraints c) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        upDownButtonPanel.add(upButton, c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        upDownButtonPanel.add(downButton, c);
    }

    private void initializeEditButtonPanel(GridBagConstraints c) {
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        editButtonPanel.add(pasteButton, c);

        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        editButtonPanel.add(Box.createHorizontalGlue(), c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        editButtonPanel.add(deleteButton, c);
    }

    private void moveDirectionalBox(ActionEvent e) {
        MapMakerImagePanel mapMakerImageWindow = mapMakerWindow.getMapMakerImagePanel();
        BufferedImage mapMakerStoredImage = mapMakerImageWindow.getStoredImage();
        BufferedImage tempImage;

        int x = mapMakerImageWindow.getCursorX();
        int y = mapMakerImageWindow.getCursorY();
        int cropWidth = mapMakerWindow.getCropWidth();
        int cropHeight = mapMakerWindow.getCropHeight();
        int offsets = mapMakerWindow.getOffsets();

        int newWidth = mapMakerStoredImage.getWidth();
        int newHeight = mapMakerStoredImage.getHeight();
        int horizontalAdjustment = 0;
        int verticalAdjustment = 0;

        if (e.getSource() == upButton) {
            y -= (cropHeight + offsets);
            if (y < 0) {
                newHeight -= y;
                verticalAdjustment -= y;
                y = 0;
            }
        } else if (e.getSource() == downButton) {
            y += (cropHeight + offsets);
            if ((y + cropHeight) > mapMakerStoredImage.getHeight()) {
                newHeight = y + cropHeight;
            }
        } else if (e.getSource() == leftButton) {
            x -= (cropWidth + offsets);
            if (x < 0) {
                newWidth -= x;
                horizontalAdjustment -= x;
                x = 0;
            }
        } else if (e.getSource() == rightButton) {
            x += (cropWidth + offsets);
            if ((x + cropWidth) > mapMakerStoredImage.getWidth()) {
                newWidth = x + cropWidth;
            }
        }

        tempImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tempImage.createGraphics();
        g.drawImage(mapMakerStoredImage, horizontalAdjustment, verticalAdjustment, null);
        mapMakerImageWindow.setStoredImage(tempImage);
        g.dispose();

        mapMakerImageWindow.setCursorX(x);
        mapMakerImageWindow.setCursorY(y);
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
        int newWidth = (width > height * 1.2 ? (int) (height * 1.2) : width);
        int newHeight = (height > width * 1.2 ? (int) (width * 1.2) : height);
        return new Dimension(newWidth, newHeight);
    }

    @Override
    public final Dimension getMinimumSize() {
        return new Dimension(200, 200);  // TODO: Fix this method to return a size based on JButton contents
    }

    @Override
    public final Dimension getMaximumSize() {
        Dimension dimension = super.getMaximumSize();
        Dimension maxSize;
        Component component = getParent();

        if (component == null) {
            maxSize = new Dimension((int) dimension.getWidth(), (int) dimension.getHeight());
        } else if (component.getWidth() > dimension.getWidth() && component.getHeight() > dimension.getHeight()) {
            maxSize = component.getSize();
        } else {
            maxSize = dimension;
        }
        int width = (int) maxSize.getWidth();
        int height = (int) maxSize.getHeight();
        int newWidth = (width > height * 1.2 ? (int) (height * 1.2) : width);
        int newHeight = (height > width * 1.2 ? (int) (width * 1.2) : height);
        return new Dimension(newWidth, newHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MapMakerImagePanel mapMakerImageWindow = mapMakerWindow.getMapMakerImagePanel();
        BufferedImage mapMakerStoredImage = mapMakerImageWindow.getStoredImage();

        int x = mapMakerImageWindow.getCursorX();
        int y = mapMakerImageWindow.getCursorY();
        int cropX = mapMakerWindow.getCropX();
        int cropY = mapMakerWindow.getCropY();
        int cropWidth = mapMakerWindow.getCropWidth();
        int cropHeight = mapMakerWindow.getCropHeight();

        if (e.getSource() == pasteButton) {
            Graphics2D g = mapMakerStoredImage.createGraphics();
            try {
                BufferedImage currentImage = ImageSaver.cropClipboardImage(cropX, cropY, cropWidth, cropHeight);
                g.drawImage(currentImage, x, y, null);
            } catch (RasterFormatException except) {
                JOptionPane.showMessageDialog(mapMakerWindow,
                        "The current width or height values are too large to crop.",
                        "Clipboard Image Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (UnsupportedFlavorException except) {
                JOptionPane.showMessageDialog(mapMakerWindow,
                        "You do not currently have an image copied to the clipboard.",
                        "Clipboard Image Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException except) {
                except.printStackTrace();
            } finally {
                g.dispose();
            }

        } else if (e.getSource() == deleteButton) {
            Graphics2D g = mapMakerStoredImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(x, y, cropWidth, cropHeight);
            g.dispose();

        } else {
            moveDirectionalBox(e);
        }

        mapMakerImageWindow.updateImages();
    }
}
