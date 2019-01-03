import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class DataPanel extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    private static final int PANEL_WIDTH = 250;
    private static final int PANEL_HEIGHT = 150;

    private int width = 512;
    private int height = 384;
    private int offset = 4;

    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel offsetLabel;

    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JFormattedTextField offsetField;

    private NumberFormat widthFormat;
    private NumberFormat heightFormat;
    private NumberFormat offsetFormat;

    private MapMakerWindow mapMakerWindow;

    public DataPanel(MapMakerWindow mapMakerWindow) {
        this.mapMakerWindow = mapMakerWindow;

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(new GridLayout(0, 1));
        initializeFormats();

        widthLabel = new JLabel("Width", SwingConstants.RIGHT);
        widthLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        widthField = new JFormattedTextField(widthFormat);
        widthField.setColumns(4);
        widthField.setValue(width);
        widthField.addPropertyChangeListener("value", this);
        widthLabel.setLabelFor(widthField);

        heightLabel = new JLabel("Height", SwingConstants.RIGHT);
        heightLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        heightField = new JFormattedTextField(heightFormat);
        heightField.setColumns(4);
        heightField.setValue(height);
        widthField.addPropertyChangeListener("value", this);
        heightLabel.setLabelFor(heightField);

        offsetLabel = new JLabel("Offsets", SwingConstants.RIGHT);
        offsetLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        offsetField = new JFormattedTextField(offsetFormat);
        offsetField.setColumns(2);
        offsetField.setValue(offset);
        widthField.addPropertyChangeListener("value", this);
        offsetLabel.setLabelFor(offsetField);

        add(widthLabel);
        add(widthField);
        add(heightLabel);
        add(heightField);
        add(offsetLabel);
        add(offsetField);

        setBackground(Color.ORANGE);
    }

    private void initializeFormats() {
        widthFormat = NumberFormat.getIntegerInstance();
        heightFormat = NumberFormat.getIntegerInstance();
        offsetFormat = NumberFormat.getIntegerInstance();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source == widthField) {
            width = ((int) widthField.getValue());
        } else if (source == heightField) {
            height = ((int) heightField.getValue());
        } else if (source == offsetField) {
            offset = ((int) offsetField.getValue());
        }
    }
}
