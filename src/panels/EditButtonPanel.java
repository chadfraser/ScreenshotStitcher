package panels;

// TODO: Make scrollpane follow cursor, adjust size responsiveness
import handler.ImageHandler;
import handler.ImageSaver;
import main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;

public class EditButtonPanel extends JPanel implements ActionListener {
//    private static final int WIDTH = 250;
//    private static final int HEIGHT = 450;

    private JPanel leftButtonPanel;
    private JPanel upDownButtonPanel;
    private JPanel rightButtonPanel;
    private JPanel pasteDeleteButtonPanel;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton pasteButton;
    private JButton deleteButton;

    private MainFrame mainFrame;

    public EditButtonPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);


        leftButtonPanel = new JPanel(new GridBagLayout());
        upDownButtonPanel = new JPanel(new GridBagLayout());
        rightButtonPanel = new JPanel(new GridBagLayout());
        pasteDeleteButtonPanel = new JPanel(new GridBagLayout());

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

        c.weightx = 1;
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
        c.gridwidth = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(pasteDeleteButtonPanel, c);
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
        initializePasteDeleteButtonPanel(c);
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

    private void initializePasteDeleteButtonPanel(GridBagConstraints c) {
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        pasteDeleteButtonPanel.add(pasteButton, c);

        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        pasteDeleteButtonPanel.add(Box.createHorizontalGlue(), c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        pasteDeleteButtonPanel.add(deleteButton, c);
    }

    private Dimension getDimensionOfLargestButtonText() {
        JButton tempButton = new JButton();
        int maxButtonWidth = 0;
        int maxButtonHeight = 0;
        String[] buttonTexts = new String[] {"UP", "DOWN", "LEFT", "RIGHT", "PASTE", "DELETE"};

        tempButton.setMargin(new Insets(0, 0, 0, 0));
        for (String text : buttonTexts) {
            tempButton.setText(text);
            if (tempButton.getPreferredSize().width > maxButtonWidth) {
                maxButtonWidth = tempButton.getPreferredSize().width;
            }
            if (tempButton.getPreferredSize().height > maxButtonHeight) {
                maxButtonHeight = tempButton.getPreferredSize().height;
            }
        }

        return new Dimension(maxButtonWidth, maxButtonHeight);
    }

    @Override
    public final Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        Dimension prefSize;
        Component component = getParent();

        // TODO: move to new method
        if (component == null) {
            prefSize = new Dimension((int) dimension.getWidth(), (int) dimension.getHeight());
        } else if (component.getWidth() > dimension.getWidth() && component.getHeight() > dimension.getHeight()) {
            prefSize = component.getSize();
        } else {
            prefSize = dimension;
        }
        int width = (int) prefSize.getWidth();
        int height = (int) prefSize.getHeight();
        int newWidth = (width > height * 1.2) ? (int) (height * 1.2) : width;
        int newHeight = (height > width * 1.2) ? (int) (width * 1.2) : height;  // TODO: Replace magic number
        return new Dimension(newWidth, newHeight);
    }

    @Override
    public final Dimension getMinimumSize() {
        Dimension dimensionOfLargestButton = getDimensionOfLargestButtonText();
        int minimumWidth = dimensionOfLargestButton.width * 3;
        int minimumHeight = dimensionOfLargestButton.width * 3;  // TODO: Replace magic number

        return new Dimension(minimumWidth, minimumHeight);  // TODO: Test this method
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
        int newWidth = (width > height * 1.2) ? (int) (height * 1.2) : width;
        int newHeight = (height > width * 1.2) ? (int) (width * 1.2) : height;  // TODO: Replace magic number
        return new Dimension(newWidth, newHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pasteButton) {
            mainFrame.getActionHandler().getPasteAction().actionPerformed(e);
        } else if (e.getSource() == deleteButton) {
            mainFrame.getActionHandler().getDeleteAction().actionPerformed(e);
        } else {
            handleMovement(e);
        }
    }

    private void handleMovement(ActionEvent e) {
        if (e.getSource() == upButton) {
            mainFrame.getActionHandler().getMoveCursorUp().actionPerformed(e);
        } else if (e.getSource() == downButton) {
            mainFrame.getActionHandler().getMoveCursorDown().actionPerformed(e);
        } else if (e.getSource() == leftButton) {
            mainFrame.getActionHandler().getMoveCursorLeft().actionPerformed(e);
        } else if (e.getSource() == rightButton) {
            mainFrame.getActionHandler().getMoveCursorRight().actionPerformed(e);
        }
    }
}
