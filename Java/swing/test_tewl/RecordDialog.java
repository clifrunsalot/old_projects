/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: RecordDialog.java
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

public class RecordDialog extends JFrame
{
	public RecordDialog(Workout w)
	{
		d = new Debug();
		this.setName("recordDialog");
		
		localWorkout = new Workout();
		localWorkout = w;
		
		myModel = new RecordTableModel(localWorkout);
		table = new JTable(myModel);
		
        table.setPreferredScrollableViewportSize(new Dimension(400,135));
        table.getColumnModel().getColumn(0).setPreferredWidth(75);
        table.getColumnModel().getColumn(1).setPreferredWidth(425);
        
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);
        
        summaryLabel = new JLabel("Summary");
        
        summaryTextArea = new JTextArea();
        summaryTextArea = new JTextArea(4,45);
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setWrapStyleWord(true);
        summaryTextArea.setEditable(false);
        summaryScrollPane = new JScrollPane
        (summaryTextArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        summaryTextArea.insert("",0);

        p = new JPanel(new BorderLayout());
        p.add(scrollPane,BorderLayout.NORTH);
        p.add(summaryLabel,BorderLayout.CENTER);
        p.add(summaryScrollPane,BorderLayout.SOUTH);
        getContentPane().add(p);
        setResizable(false);
	}
	
	public void refreshTable(Workout w)
	{
		myModel = new RecordTableModel(w);
		table.setModel(myModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(75);
        table.getColumnModel().getColumn(1).setPreferredWidth(425);
        summaryTextArea.setText(w.getSummary());
	}
	
    public void windowClosing(WindowEvent we)
    {
    }

    public void windowOpened(WindowEvent we)
    {
	}

    public void windowIconified(WindowEvent we)
    {
	}

    public void windowDeiconified(WindowEvent we)
    {
	}

    public void windowClosed(WindowEvent we)
    {
	}

    public void windowActivated(WindowEvent we)
    {
	}

    public void windowDeactivated(WindowEvent we)
    {
	}

	private Debug d;
	private Workout localWorkout;
	private JScrollPane scrollPane,
						summaryScrollPane;
	private RecordTableModel myModel;
	private JTable table;
	private JPanel p;
	private JTextArea summaryTextArea;
	private JLabel summaryLabel;
	
}//~class RecordDialog exten...


