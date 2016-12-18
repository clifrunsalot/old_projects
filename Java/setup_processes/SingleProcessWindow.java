import java.io.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class SingleProcessWindow implements ActionListener
{
	public SingleProcessWindow (String n)
	{
		DEBUG = false;
		p = new MyPrint();
		
		name = new String(n);
		label = new JLabel(name);

		commentedOutCheckBox = new JCheckBox();
		commentedOutCheckBox.addActionListener(this);
		commentedOutCheckBox.setActionCommand("comment");
		commentedOutCheckBox.setSelected(false);
		
		heartbeatRequiredCheckBox = new JCheckBox();
		heartbeatRequiredCheckBox.addActionListener(this);
		heartbeatRequiredCheckBox.setActionCommand("heartbeats");
		heartbeatRequiredCheckBox.setSelected(false);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setActionCommand("reset");
	}
	
	public String getName()
	{
		return name;
	}
	
	public JLabel getLabel()
	{
		return label;
	}
	
	public JCheckBox getCommentedOutCheckBox()
	{
		return commentedOutCheckBox;
	}
	
	public void setCommentedOutCheckBox(boolean b)
	{
		commentedOutCheckBox.setSelected(b);
	}
	
	public JCheckBox getHeartbeatRequiredCheckBox()
	{
		return heartbeatRequiredCheckBox;
	}
	
	public void setHeartbeatRequiredCheckBox(boolean b)
	{
		heartbeatRequiredCheckBox.setSelected(b);
	}
	
	public JButton getResetButton()
	{
		return resetButton;
	}
	
	public void resetProcess()
	{
		commentedOutCheckBox.setSelected(false);
		heartbeatRequiredCheckBox.setSelected(false);
	}
		
    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();
	    
	    if(DEBUG)
	    	p.print(source);
	    	
	    if (source.equals("comment"))
	    {
//		    changeTitle(" *** Settings Modified *** ");
	    }
	    else if (source.equals("heartbeats"))
	    {
//		    changeTitle(" *** Settings Modified *** ");
	    }
	    else if (source.equals("reset"))
	    {
//		    changeTitle(" *** Settings Modified *** ");
	    }
    }
		    	
	private String name;
	private JLabel label;
	private JCheckBox commentedOutCheckBox;
	private JCheckBox heartbeatRequiredCheckBox;
	private JButton resetButton;
	private MyPrint p;
	private boolean DEBUG;
}
