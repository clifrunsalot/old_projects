import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

public class UserConfigTable extends JPanel
{
    public UserConfigTable(String [][] options) 
    {
        d = new Debug();
//         d.print("inside UserConfigTable()");
	    
        localOptionsArray = new String[MAXROWS][MAXCOLUMNS];
	    localOptionsArray = options;
	    
    	optionsTableModel = new UserConfigTableModel(localOptionsArray);
      	optionsTable = new JTable(optionsTableModel);
      	
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(optionsTable);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(300,200));
    }
    
    public void refreshTable(String [][] optsArray)
    {	    
	    optionsTableModel = new UserConfigTableModel(optsArray);
	   	optionsTable.setModel(optionsTableModel);
        setColumnSize(optionsTable);
    }
    
    public void setColumnSize(JTable table)
    {
	    table.getColumnModel().getColumn(1).setPreferredWidth(100);
	}

    public String [][] extractOptions()
    {
	    return optionsTableModel.extractOptions();
	}
	
    private Debug d;
    private JTable optionsTable;
    private Container contentPane;
	private JScrollPane scrollPane;
	private static final int MAXROWS = 10;
	private static final int MAXCOLUMNS = 2;
    private String [][] localOptionsArray;
	private UserConfigTableModel optionsTableModel;
}

