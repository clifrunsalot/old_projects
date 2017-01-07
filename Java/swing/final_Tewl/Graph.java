/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Graph.java
*/

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.lang.Integer;
import java.lang.Character;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.io.*;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class Graph extends JFrame implements ActionListener,
										WindowListener
{
    public Graph(Vector log,
    			String event,
    			Component logParent,
    			String leftAxis,
    			String rightAxis)
    {
	    d = new Debug();
	    addWindowListener(this);
	    this.setName("graphWindow");
	    
        setTitle(event.toUpperCase() + " Graph");
        localEvent = event;
        localLeftAxis = getShortAxisName(event,leftAxis);
        localRightAxis = getShortAxisName(event,rightAxis);

        //create a list of the event to be graphed
        eventList = new Vector();
        eventList = getEventList(event,log);

        DrawGraph graph = new DrawGraph(eventList,event,localLeftAxis,localRightAxis);
        scrollPaneGraph = new JScrollPane(graph);

        DrawAxisPanel leftPanel = new DrawAxisPanel(localLeftAxis,Color.red);
        scrollPaneYPanel = new JScrollPane(leftPanel);

        splitPane1Vertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        splitPane1Vertical.setLeftComponent(leftPanel);
        splitPane1Vertical.setRightComponent(scrollPaneGraph);
        splitPane1Vertical.setDividerLocation(75);
        splitPane1Vertical.setDividerSize(1);
        innerPanel.add(splitPane1Vertical,BorderLayout.CENTER);

        innerAndRightPanel = new JPanel();
        innerAndRightPanel.setLayout(new BorderLayout());
        DrawAxisPanel rightPanel = new DrawAxisPanel(localRightAxis,Color.blue);
        scrollPaneZPanel = new JScrollPane(rightPanel);
        splitPane2Vertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane2Vertical.setLeftComponent(innerPanel);
        splitPane2Vertical.setRightComponent(rightPanel);
        splitPane2Vertical.setDividerLocation(460);
        splitPane2Vertical.setDividerSize(1);
        innerAndRightPanel.add(splitPane2Vertical,BorderLayout.CENTER);

        buttonPanel = new JPanel();
        displayButton = new JButton("Display Record");
        displayButton.addActionListener(this);
        displayButton.setActionCommand("display");
        buttonPanel.add(displayButton);
        displayField = new JTextField(5);
        buttonPanel.add(displayField);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(innerAndRightPanel,"Center");
		mainPanel.add(buttonPanel,"South");
        mainPanel.setPreferredSize(new Dimension(550,400));

        Container contentPane = getContentPane();
        contentPane.add(mainPanel);

        Dimension graphSize = getSize();
        Point parentLoc = logParent.getLocation();
        recordDialog = new RecordDialog(new Workout());
        recordDialog.setLocation(graphSize.width + 550, parentLoc.y - 50);
        recordDialog.pack();
        
	    setBackground(Color.white);

    }//~public Graph(Vector log)...

    public String getShortAxisName(String event, String axisOption)
    {
	    String shortAxisName = "";
		if (axisOption.equals("Distance in Miles") &&
			(event.equals("run") || event.equals("bike")))
				shortAxisName = "Miles";
		else if (axisOption.equals("Distance in Yards") &&
			event.equals("swim"))
				shortAxisName = "Yards";
		else if (axisOption.equals("Pace (mph)") &&
			(event.equals("run") || event.equals("bike")))
			shortAxisName = "Mph";
		else if (axisOption.equals("Pace (mins/100 yds)") &&
			event.equals("swim"))
			shortAxisName = "Min/100yds";
		else if (axisOption.equals("Resting Heart Rate"))
			shortAxisName = "Pulse";
		else if (axisOption.equals("Weight"))
			shortAxisName = "Weight";
		else if (axisOption.equals("Temperature"))
			shortAxisName = "Temp";

		return shortAxisName;
    }

    public Vector getEventList(String event,Vector localLog)
    {
    	Vector eventList = new Vector();
    	for(int rec=0; rec<localLog.size(); rec++)
    	{
        	Workout w = new Workout();
            w = (Workout)(localLog.elementAt(rec));
        	WorkoutEvent [] events = w.getEvents();

        	for (int i=0; i<events.length; i++)
        	{
	            if (events[i].getName().equals(event))
	            {
	            	eventList.add(w);
	            }
            }
    	}
    	return eventList;
    }

    public int getRecordRequest()
    {
        return record;
    }

    public boolean isValid (String s)
    {
        boolean valid = false;
        char [] recordArray = s.toCharArray();

        for (int i=0; i<recordArray.length; i++)
        {
            char c = recordArray[i];
            if (Character.isDigit(c))
            {
                valid = true;
            }

            else
            {
                valid = false;
                break;
            }
        }//~for (int i=0; i<recordAr...

        return valid;
    }//~public boolean isValid (...

    public Workout find (int record)
    {
        Workout w = new Workout();
        if (record > 0 && record <= eventList.size())
        {
            for(int i=0; i<=eventList.size(); i++)
            {
                if (record == i)
                {
                    i = i-1;
                    w = (Workout)eventList.elementAt(i);
                    break;
                }
            }
        }//~if (record > 0 && record...

        return w;
    }//~public Workout find (int...
    
    public boolean isOpen()
    {
	    return windowOpened;
    }

    public void windowClosing(WindowEvent we)
    {
	    	recordDialog.hide();
	    	windowOpened = false;
    }

    public void windowOpened(WindowEvent we)
    {
	    	windowOpened = true;
	}

    public void windowIconified(WindowEvent we)
    {}

    public void windowDeiconified(WindowEvent we)
    {
	}

    public void windowClosed(WindowEvent we)
    {
	    	windowOpened = false;
	}

    public void windowActivated(WindowEvent we)
    {
	}

    public void windowDeactivated(WindowEvent we)
    {
	}

    public void actionPerformed(ActionEvent evt)
    {
		String source = evt.getActionCommand();

        if (source.equals("display") &
        evt.getSource() instanceof JButton)
        {
			recordStr = displayField.getText().trim();
	        if (isValid(recordStr))
	        {
	            record = Integer.parseInt(recordStr);
	            if(record > 0 && record <= eventList.size())
	            {
	                Workout w = new Workout();
	                w = find(record);
	                if (recordDialog.getName().equals("recordDialog") &&
	                	!recordDialog.isVisible())
	                {
	                    recordDialog.setVisible(true);
	                }
	                recordDialog.refreshTable(w);
			        recordDialog.pack();
	            }//~if(record > 0 && record ...

	            else
	            {
	                JOptionPane.showMessageDialog(
	                    this,
	                    "Invalid Record!\n" +
	                    "That record does not exist.",
	                    "Error",
	                    JOptionPane.ERROR_MESSAGE);
	            }
	        }//~if (isValid(recordStr))...

	        else
	        {
	            JOptionPane.showMessageDialog(
	                this,
	                "Invalid Record!\n" +
	                "Record must be an integer.",
	                "Error",
	                JOptionPane.ERROR_MESSAGE);
	        }
        }
        repaint();
    }//~public void actionPerfor...

    private Debug d;
    private JScrollPane scrollPaneGraph,
        scrollPaneYPanel,
        scrollPaneZPanel;
    private JPanel buttonPanel,
        innerPanel,
        rightPanel,
        innerAndRightPanel,
        mainPanel;
    private JSplitPane splitPane1Vertical,
    					splitPane2Vertical;
    private JButton displayButton;
    private JTextField displayField;
    private JComboBox zOptionComboBox;
    private JLabel zOptionLabel;
    private int record;
    private String recordStr,
    			localEvent,
    			localLeftAxis,
    			localRightAxis;
    private RecordDialog recordDialog;
    private Vector localLog,
    			eventList;
    private static boolean windowOpened = false;
}//~class Graph extends JFra...






