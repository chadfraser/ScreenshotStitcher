import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class TrimPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JButton trimHorizontalButton;
    private JButton trimVerticalButton;
    private JCheckBox trimOffsetsCheckBox;

    private JPanel buttonPanel;
    private JPanel checkBoxPanel;

    private MapMakerWindow mapMakerWindow;

    public TrimPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        initializeButtons();
        initializePanels();
        initializeLayout();
    }

    private void initializeButtons() {
        trimHorizontalButton = new JButton("TRIM HORIZONTALLY");
        trimVerticalButton = new JButton("TRIM VERTICALLY");
        trimOffsetsCheckBox = new JCheckBox("Include offsets in trim");
        trimOffsetsCheckBox.setSelected(true);

        trimHorizontalButton.addActionListener(this);
        trimVerticalButton.addActionListener(this);
    }

    private void initializePanels() {
        buttonPanel = new JPanel(new GridBagLayout());
        checkBoxPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 10, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        initializeButtonPanel(c);
        initializeCheckBoxPanel(c);
    }

    private void initializeCheckBoxPanel(GridBagConstraints c) {
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        checkBoxPanel.add(trimOffsetsCheckBox, c);
    }

    private void initializeButtonPanel(GridBagConstraints c) {
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonPanel.add(trimHorizontalButton, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)), c);  // TODO: Make this line adjustable

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        buttonPanel.add(trimVerticalButton, c);
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(buttonPanel, c);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(checkBoxPanel, c);
    }

    private int getOffsetAdjustment() {
        if (trimOffsetsCheckBox.isSelected()) {
            return mapMakerWindow.getOffsets();
        }
        return 0;
    }

    private void trimImageHorizontally() {
        BufferedImage storedImage = mapMakerWindow.getMapMakerImagePanel().getStoredImage();
        int cropHeight = mapMakerWindow.getCropHeight();
        int cursorY = mapMakerWindow.getMapMakerImagePanel().getCursorY();
        int offsetAdjustment = getOffsetAdjustment();
        BufferedImage upperSubImage;
        BufferedImage lowerSubImage;
        BufferedImage newImage;

        if (storedImage.getHeight() - (cropHeight + 2 * offsetAdjustment) <= 0) {
            return;
        }

        newImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight() - (cropHeight + offsetAdjustment),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        if (cursorY - offsetAdjustment > 0) {
            upperSubImage = storedImage.getSubimage(0, 0, storedImage.getWidth(), cursorY - offsetAdjustment);
            g.drawImage(upperSubImage, 0, 0, null);
        }
        if (cursorY + cropHeight + offsetAdjustment < storedImage.getHeight()) {
            lowerSubImage = storedImage.getSubimage(0, cursorY + cropHeight + offsetAdjustment, storedImage.getWidth(),
                    storedImage.getHeight() - (cursorY + cropHeight + offsetAdjustment));
            g.drawImage(lowerSubImage, 0, cursorY, null);
        }
        g.dispose();

        mapMakerWindow.getMapMakerImagePanel().setStoredImage(newImage);
        adjustCursorAfterTrimming();
        mapMakerWindow.getMapMakerImagePanel().updateImages();
    }

    private void trimImageVertically() {
        BufferedImage storedImage = mapMakerWindow.getMapMakerImagePanel().getStoredImage();
        int cropWidth = mapMakerWindow.getCropWidth();
        int cursorX = mapMakerWindow.getMapMakerImagePanel().getCursorX();
        int offsetAdjustment = getOffsetAdjustment();
        BufferedImage leftSubImage;
        BufferedImage rightSubImage;
        BufferedImage newImage;

        if (storedImage.getWidth() - (cropWidth + 2 * offsetAdjustment) <= 0) {
            return;
        }

        // subtract offsetAdjustment once from the width of storedImage. If offsetAdjustment = 0, this has no effect
        // otherwise,
        newImage = new BufferedImage(storedImage.getWidth() - (cropWidth - offsetAdjustment), storedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);  // TODO: Look into how this works
        Graphics2D g = newImage.createGraphics();
        if (cursorX - offsetAdjustment > 0) {
            leftSubImage = storedImage.getSubimage(0, 0, cursorX - offsetAdjustment, storedImage.getHeight());
            g.drawImage(leftSubImage, 0, 0, null);
        }
        if (cursorX + cropWidth + offsetAdjustment <= storedImage.getWidth()) {
            rightSubImage = storedImage.getSubimage(cursorX + cropWidth + offsetAdjustment, 0,
                    storedImage.getWidth() - (cursorX + cropWidth + offsetAdjustment), storedImage.getHeight());
            g.drawImage(rightSubImage, cursorX, 0, null);
        }

        g.dispose();

        mapMakerWindow.getMapMakerImagePanel().setStoredImage(newImage);
        adjustCursorAfterTrimming();
        mapMakerWindow.getMapMakerImagePanel().updateImages();
    }

    private void adjustCursorAfterTrimming() {
        BufferedImage storedImage = mapMakerWindow.getMapMakerImagePanel().getStoredImage();
        int cropWidth = mapMakerWindow.getCropWidth();
        int cropHeight = mapMakerWindow.getCropHeight();
        int cursorX = mapMakerWindow.getMapMakerImagePanel().getCursorX();
        int cursorY = mapMakerWindow.getMapMakerImagePanel().getCursorY();

        if (cropWidth > storedImage.getWidth()) {
            mapMakerWindow.setCropWidth(storedImage.getWidth());
            cropWidth = storedImage.getWidth();
        }
        if (cropHeight > storedImage.getHeight()) {
            mapMakerWindow.setCropHeight(storedImage.getHeight());
            cropHeight = storedImage.getHeight();
        }
        if (cursorX + cropWidth > storedImage.getWidth()) {
            mapMakerWindow.getMapMakerImagePanel().setCursorX(storedImage.getWidth() - cropWidth);
        }
        if (cursorY + cropHeight > storedImage.getHeight()) {
            mapMakerWindow.getMapMakerImagePanel().setCursorY(storedImage.getHeight() - cropHeight);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == trimHorizontalButton) {
            trimImageHorizontally();
        } else if (e.getSource() == trimVerticalButton) {
            trimImageVertically();
        }
    }
}
