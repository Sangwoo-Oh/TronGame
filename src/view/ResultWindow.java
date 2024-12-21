package view;

import persistence.Result;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class ResultWindow extends JDialog {
    private final JTable table;
    
    public ResultWindow(ArrayList<Result> results, JFrame parent) {
        super(parent, true);
        table = new JTable(new ResultTableModel(results));
        add(table);
        pack();
        setTitle("Highscores");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
