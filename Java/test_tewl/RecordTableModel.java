import javax.swing.table.*;
import java.text.*;
import java.lang.Integer;


public class RecordTableModel extends AbstractTableModel
{
	public RecordTableModel(Workout w)
	{
		d = new Debug();
		localWorkout = new Workout();
		localWorkout = w;
		data = getData(localWorkout);
	}
	
    public int getColumnCount() 
    {
        return columnNames.length;
    }

	public int getRowCount() 
	{
        return data.length;
    }

    public String getColumnName(int col) 
    {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) 
    {
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
		return false;
    }

    public void setValueAt(Object value, int row, int col) 
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public Object [][] getData(Workout w)
    {
        Object [][] localData = new Object [MAXROWS][MAXCOLS];;
	    TewlUtilities util = new TewlUtilities();
	    WorkoutTime t = new WorkoutTime();
        DecimalFormat speedFormat = new DecimalFormat("###.##");
        DecimalFormat yds = new DecimalFormat("#####");
		int colIndex = 0;
		int rowIndex = 0;
		
		int col = 0;
		localData[0][col] = "Date";
		localData[1][col] = "Temp";
		localData[2][col] = "Weather";
		localData[3][col] = "Pulse";
		localData[4][col] = "Weight";
		localData[5][col] = "Run";
		localData[6][col] = "Bike";
		localData[7][col] = "Swim";
		
		col = 1;
		localData[0][col] = "" + w.getDate().getMonth() + "/" + w.getDate().getDay() + "/" + w.getDate().getYear();
		localData[1][col] = Integer.toString(w.getTemp());
		localData[2][col] = w.getWeather();
		localData[3][col] = Integer.toString(w.getRestingHR());
		localData[4][col] = Integer.toString(w.getWeight());
		
		WorkoutEvent [] we = w.getEvents();
		
		localData[5][col] = "" + we[0].getDistance() + " mi in " +
	        	we[0].getTime().getTime() + " at " +
	        	t.formatTime
	        		(util.getMinsPerMile(we[0].getTime().getConvertedTime(),
	        			we[0].getDistance())) + " mins/mile";
	        			
	    localData[6][col] = "" + we[1].getDistance() + " mi in " +
	        	we[1].getTime().getTime() + " at " +
	        	speedFormat.format
	        		(util.getAvgSpeed(we[1].getName(),
	        			we[1].getTime().getConvertedTime(),
	        			we[1].getDistance())) + " mph";
	        			
	    localData[7][col] = "" + yds.format(we[2].getDistance()) + " yds in " +
	        	we[2].getTime().getTime() + " at " +
				t.formatTime(util.getMinsPer100yds(we[2].getTime().getConvertedTime(),
		                    		(double)we[2].getDistance())) + " mins/100 yds (" +
		        speedFormat.format(
		        	util.getAvgSpeed(we[2].getName(),
		        		we[2].getTime().getConvertedTime(),
		        		(double)we[2].getDistance())) + " mph)";
        
		return localData;
    }
    
    private Debug d;
    private final static int MAXROWS = 8;
    private final static int MAXCOLS = 2;
    private Object [][] data;
    private final String[] columnNames = {"Item",
                                  "Description"};
	private Workout localWorkout;
}