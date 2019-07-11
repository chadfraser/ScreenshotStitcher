package panels;

import main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrimPanel extends JPanel implements ActionListener {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 450;

    private JButton trimHorizontalButton;
    private JButton trimVerticalButton;
    private JCheckBox trimOffsetsCheckBox;

    private JPanel buttonPanel;
    private JPanel checkBoxPanel;

    private MainFrame mainFrame;

    public TrimPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == trimHorizontalButton) {
            mainFrame.getActionHandler().getTrimHorizontallyAction().actionPerformed(e);
        } else if (e.getSource() == trimVerticalButton) {
            mainFrame.getActionHandler().getTrimVerticallyAction().actionPerformed(e);
        }
    }

    public JCheckBox getTrimOffsetsCheckBox() {
        return trimOffsetsCheckBox;
    }
}

