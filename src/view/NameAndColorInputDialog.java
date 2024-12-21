package view;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NameAndColorInputDialog {
    public static PlayerConfig showNameAndColorInputDialog(Component parent, String message, String title, String[] colors) {
        JPanel panel = new JPanel(new GridLayout(2,2,5,5));
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(20);
        
        panel.add(nameLabel); panel.add(nameField);
        JLabel colorLabel = new JLabel("Color: ");
        JComboBox<String> colorComboBox = new JComboBox<>(colors);
        panel.add(colorLabel);
        panel.add(colorComboBox);
        
        Object[] options = {"OK"};
        
        int result = JOptionPane.showOptionDialog(
            parent,
            panel,
            title,
            JOptionPane.OK_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String selectedColor = (String) colorComboBox.getSelectedItem();
            return new PlayerConfig(name, selectedColor);
        }
        return null; 
    }
}
