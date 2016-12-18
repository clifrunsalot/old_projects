import javax.swing.table.*;
import javax.swing.*;
import java.text.*;

public class UserConfigTableModel extends AbstractTableModel
{
    public UserConfigTableModel(String [][] options)
    {
	    d = new Debug();
// 	    d.print("inside UserConfigTableModel()");
		data = getData(options);
	}
    
    public int getColumnCount() {
        return columnNames.length;
    }

	public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) 
    {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) 
    {
		return true;
    }

    public void setValueAt(Object value, int row, int col) 
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public Object [][] getData(String [][] optsArray)
    {
        Object [][] localData = new Object [MAXROWS][MAXCOLS];;
		int colIndex = 0;
		int rowIndex = 0;
        
		for (rowIndex=0; rowIndex<MAXROWS; rowIndex++)
		{
			for (colIndex=0; colIndex<MAXCOLS; colIndex++)
			{
				if (optsArray[rowIndex][colIndex] == "" ||
					optsArray[rowIndex][colIndex] == null)
					optsArray[rowIndex][colIndex] = "*";
				localData[rowIndex][colIndex] = optsArray[rowIndex][colIndex] ;
			}
		}
		
		return localData;
    }
    
    public String [][] extractOptions()
    {
// 	    d.print("inside extractOptions()");
        String [][] optsArray = new String [MAXROWS][MAXCOLS];;
		int colIndex = 0;
		int rowIndex = 0;
        
		for (rowIndex=0; rowIndex<MAXROWS; rowIndex++)
		{
			for (colIndex=0; colIndex<MAXCOLS; colIndex++)
			{
				optsArray[rowIndex][colIndex] = (String)data[rowIndex][colIndex] ;
			}
		}

		return optsArray;
    }
    
	private Debug d;
	private Object [][] data;
	private final static int MAXROWS = 10;
	private final static int MAXCOLS = 2;
    private final String[] columnNames = {"Code","Description"};
}
