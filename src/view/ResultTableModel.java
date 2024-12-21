package view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import persistence.Result;

public class ResultTableModel extends AbstractTableModel {
    private final ArrayList<Result> results;
    private final String[] colName = new String[]{ "Name", "Count of win" };
    
    public ResultTableModel(ArrayList<Result> results) {
        this.results = results;
    }
    
    @Override
    public int getRowCount() { return results.size(); }

    @Override
    public int getColumnCount() { return 2; }
    
    @Override
    public Object getValueAt(int r, int c) {
        Result res = results.get(r);
        if      (c == 0) return res.name;
        else return res.cntWin;
    }
    
    @Override
    public String getColumnName(int i) { return colName[i]; }
}
