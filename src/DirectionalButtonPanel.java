import javafx.scene.Group;

import javax.swing.*;
import java.awt.*;

class DirectionalButtonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 150;

    private MapMakerWindow mapMakerWindow;

    DirectionalButtonPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.YELLOW);
        setFocusable(true);
        JButton upButton = new JButton("UP");
        JButton downButton = new JButton("DOWN");
        JButton leftButton = new JButton("LEFT");
        JButton rightButton = new JButton("RIGHT");

        JButton[] arrayOfButtons = {upButton, downButton, leftButton, rightButton};

        setButtonSizes(arrayOfButtons);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets (5, 0, 0, 0);
        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx = 3;
        c.gridy = 0;
        add(upButton, c);

        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 1;
        add(leftButton, c);

        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx = 6;
        c.gridy = 1;
        add(rightButton, c);

        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx = 3;
        c.gridy = 2;
        add(downButton, c);
    }

    private void setButtonSizes(JButton[] arrayOfButtons) {
        int maxButtonWidth = 0;
        for (JButton button : arrayOfButtons) {
            if (button.getPreferredSize().getWidth() > maxButtonWidth) {
                maxButtonWidth = (int) button.getPreferredSize().getWidth();
            }
        }
        for (JButton button : arrayOfButtons) {
            button.setPreferredSize(new Dimension(maxButtonWidth,
                                                  (int) button.getPreferredSize().getHeight()));
        }
    }
}
