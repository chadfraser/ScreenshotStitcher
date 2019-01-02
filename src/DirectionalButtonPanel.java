import javafx.scene.Group;

import javax.swing.*;
import java.awt.*;

public class DirectionalButtonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 250;
    private static final int HEIGHT = 350;

    private MapMakerWindow mapMakerWindow;

    public DirectionalButtonPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        JButton upButton = new JButton("UP");
        JButton downButton = new JButton("DOWN");
        JButton leftButton = new JButton("LEFT");
        JButton rightButton = new JButton("RIGHT");

        GroupLayout groupLayout = new GroupLayout(this);
        setLayout(groupLayout);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(leftButton)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(upButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(downButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(rightButton));

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(upButton)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(leftButton)
                                .addComponent(rightButton))
                        .addComponent(downButton));

//        add(upButton, BorderLayout.NORTH);
//        add(leftButton, BorderLayout.WEST);
//        add(rightButton, BorderLayout.EAST);
//        add(downButton, BorderLayout.SOUTH);
    }
}
