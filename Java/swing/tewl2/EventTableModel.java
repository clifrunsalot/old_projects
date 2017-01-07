import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class EventTableModel extends AbstractTableModel{

	public EventTableModel(Object [][] d){
		data = d;
	}

	public int getColumnCount(){
		return columnNames.length;
	}
    
	public int getRowCount(){
		return data.length;
	}

   public String getColumnName(int col){
		return columnNames[col].toString();
	}

	public Object getValueAt(int row, int col){
		return data[row][col];
	}

	public Class getColumnClass(int c){
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col){
		if (col == 3){
			return false;
		} else{
			return true;
		}
	}

   /**
	 * Don't need to implement this method unless your table's
	 * data can change.
	 */
	public void setValueAt(Object value, int row, int col){
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}
	
	final String[] columnNames = {"Event","Distance","Time","Pace","Location"};
	final Object[][] data; 
}

