package actions;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AdvanceComboBoxAction extends AbstractAction {
    private MainFrame mainFrame;
    private JComboBox comboBox;

    AdvanceComboBoxAction(MainFrame mainFrame, JComboBox comboBox) {
        this.mainFrame = mainFrame;
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int indexOfSelectedObject = comboBox.getSelectedIndex();
        if (0 <= indexOfSelectedObject && indexOfSelectedObject < comboBox.getItemCount() - 1)
        {
            comboBox.setSelectedIndex(indexOfSelectedObject + 1);
        }
    }
}