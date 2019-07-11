package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CheckboxAction extends AbstractAction {
    MainFrame mainFrame;
    private JCheckBox checkBox;

    CheckboxAction(MainFrame mainFrame, JCheckBox checkBox) {
        this.mainFrame = mainFrame;
        this.checkBox = checkBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkBox.isEnabled()) {
            checkBox.setSelected(!checkBox.isSelected());
        }
    }
}