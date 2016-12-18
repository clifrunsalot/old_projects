import javax.swing.table.*;
import javax.swing.*;
import java.text.*;

public class EventTableModel extends AbstractTableModel
{
    public EventTableModel(Workout w)
    {
	    d = new Debug();
		data = getData(w);
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
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 1 || col == 4) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) 
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public String calculatePace(WorkoutEvent we)
    {
        TewlUtilities util = new TewlUtilities();
        DecimalFormat speedFormat = new DecimalFormat("###.##");
        WorkoutTime t = new WorkoutTime();
        String pace = "";

        if (we.getName().equals("run") && we.getDistance() > 0)
        {
	        pace = t.formatTime
		        (util.getMinsPerMile(we.getTime().getConvertedTime(),
		            we.getDistance()));
        }
	    else if(we.getName().equals("bike") && we.getDistance() > 0)
	    {
			pace = speedFormat.format
		        (util.getAvgSpeed(we.getName(),we.getTime().getConvertedTime(),
		            we.getDistance()));

	    }
	    else if(we.getName().equals("swim") && we.getDistance() > 0)
	    {
			pace = t.formatTime
		        (util.getMinsPer100yds(we.getTime().getConvertedTime(),
		            (double)(we.getDistance())));
        }
        return pace;
    }
    
    public Object [][] getData(Workout w)
    {
        Object [][] localData = new Object [MAXROWS][MAXCOLS];;
		int colIndex = 0;
		int rowIndex = 0;
        
        if (w.getNumberOfEvents() < 2)
        {
			for (rowIndex=0; rowIndex<MAXROWS; rowIndex++)
			{
				localData[rowIndex][0] = new Boolean(false);
				if(rowIndex == 0)
					localData[rowIndex][1] = "run";
				else if (rowIndex == 1)
					localData[rowIndex][1] = "bike";
				else if (rowIndex == 2)
					localData[rowIndex][1] = "swim";
				
				localData[rowIndex][2] = "0.0";
				localData[rowIndex][3] = "0:00:00";
				localData[rowIndex][4] = "*";
				if (rowIndex == 0)
					localData[rowIndex][5] = "min/mile";
				else if (rowIndex == 1)
					localData[rowIndex][5] = "mph";
				else if (rowIndex == 2)
					localData[rowIndex][5] = "mins/100yds";
 				localData[rowIndex][6] = "*";
				
			}
		}
		else
		{
	        WorkoutEvent [] we = w.getEvents();

			for (rowIndex=0; rowIndex<MAXROWS; rowIndex++)
			{
				if (we[rowIndex].getDistance() > 0)
					localData[rowIndex][0] = new Boolean(true);
				else
					localData[rowIndex][0] = new Boolean(false);
					
				localData[rowIndex][1] = we[rowIndex].getName();
				localData[rowIndex][2] = Double.toString(we[rowIndex].getDistance());
				localData[rowIndex][3] = we[rowIndex].getTime().getTime();
				localData[rowIndex][4] = calculatePace(we[rowIndex]);
				if (rowIndex == 0)
					localData[rowIndex][5] = "min/mile";
				else if (rowIndex == 1)
					localData[rowIndex][5] = "mph";
				else if (rowIndex == 2)
					localData[rowIndex][5] = "mins/100yds";
				
				localData[rowIndex][6] = we[rowIndex].getRoute().getCode();
			}
 		}

		return localData;
    }
    
    public boolean validEvents()
    {
	    WorkoutTime timeTest = new WorkoutTime();
	    TewlUtilities util = new TewlUtilities();
	    boolean selected = false;
	    boolean valid = false;
	    int numberedSelected = 0;
	    String distance = "";
	    String time = "";
	    String route = "";
	    
		for (int rowIndex=0; rowIndex<MAXROWS; rowIndex++)
		{
			selected = false;
			distance = "";
			time = "";
			
			if (String.valueOf(data[rowIndex][0]) == "true")
				selected = true;
			distance = String.valueOf(data[rowIndex][2]);
			time = (String)(data[rowIndex][3]);
			
// 			d.print("selected - " + selected);
// 			d.print(distance + " - " + util.validDouble(distance,10));
// 			d.print(time +  " - " + timeTest.validTime(time));			
			
			if (selected &&									//Checkbox
				(util.validDouble(distance,10)) &&			//Distance
				(Double.parseDouble(distance) > 0) &&
				(timeTest.validTime(time)) &&	 			//Time
				(timeTest.getConvertedTime(time) > 0.0))
			{
				valid = true;
				numberedSelected++;
			}
				
// 			d.print("valid: " + valid);
		}
		return (valid && (numberedSelected > 0));
    }
    
    public WorkoutEvent [] extractWorkoutEvents()
    {
	    WorkoutEvent [] we = new WorkoutEvent[MAXROWS];
		for (int rowIndex=0; rowIndex<MAXROWS; rowIndex++)
		{
			String name = (String)data[rowIndex][1];
			double distance = Double.parseDouble((String)data[rowIndex][2]);
			String time = (String)data[rowIndex][3];
			String route = (String)data[rowIndex][6];
			we[rowIndex] = new WorkoutEvent(name,distance,time,route);
		}
		return we;
    }
    
	private Debug d;
	private Object [][] data;
	private final static int MAXROWS = 3;
	private final static int MAXCOLS = 7;
    private final String[] columnNames = {"Select",
	    							"Event",
                                  "Distance",
                                  "Time",
                                  "Pace",
                                  "Measurement",
                                  "Route Code"};
}
