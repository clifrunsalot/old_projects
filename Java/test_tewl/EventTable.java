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

public class EventTable extends JPanel
{
    public EventTable(Workout w) 
    {
        d = new Debug();
	    
	    localWorkout = new Workout();
	    localWorkout = w;
	    
    	myModel = new EventTableModel(localWorkout);
      	table = new JTable(myModel);
	    
        setUpRouteColumn(table.getColumnModel().getColumn(6),localRoutes,localWorkout);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(525,75));
    }
    
    public void refreshTable(Workout w, String [] routes)
    {	    
	    myModel = new EventTableModel(w);
	   	table.setModel(myModel);
        setColumnSize(table);
	    setUpRouteColumn(table.getColumnModel().getColumn(6),routes,w);
    }
    
    public void setColumnSize(JTable table)
    {
	    table.getColumnModel().getColumn(5).setPreferredWidth(100);
	}

    public void setUpRouteColumn(TableColumn routeColumn, String [] tempRoutes, Workout tempWorkout )
    {
        //Set up the editor for the sport cells.
        comboBox = new JComboBox();
        comboBox.addItem("*");
        
        for(int i=0; i<tempRoutes.length; i++)
        {
	        comboBox.addItem(tempRoutes[i]);
        }

        routeColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        routeColumn.setCellRenderer(renderer);
    }
    
    public WorkoutEvent [] extractWorkoutEvents()
    {
	    return myModel.extractWorkoutEvents();
	}
	
	public boolean validEvents()
	{
		return myModel.validEvents();
	}
	    
    
    private Debug d;
    private Workout localWorkout;
    private  String [] localRoutes = new String[10];
    private JComboBox comboBox;
    private JCheckBox checkBox;
    private JTable table;
    private Container contentPane;
	private JScrollPane scrollPane;
	private static final int MAXROWS = 3;
	private  static final int MAXCOLUMNS = 7;
	private EventTableModel myModel;
}

