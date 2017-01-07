/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: DrawZPanel.java
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

public class DrawAxisPanel extends JPanel
{
	public DrawAxisPanel()
	{
		localAxis = "";
        setPreferredSize(new Dimension(70,300));
        setMinimumSize(new Dimension(70,300));
    }

    public DrawAxisPanel(String zAxis, Color color)
    {
	    d = new Debug();
	    localColor = color;
	    localAxisName = zAxis;
	    localLowerLimit = getLower(zAxis);
	    localUpperLimit = getUpper(zAxis);
	    localIncrementSize = getIncrementSize(localLowerLimit,localUpperLimit);
        setPreferredSize(new Dimension(70,300));
        setMinimumSize(new Dimension(70,300));
    }

    public void paintComponent(Graphics g)
    {
	    g.setColor(localColor);
		drawAxis(g,localAxisName,localLowerLimit,localUpperLimit,localIncrementSize);
    }
    
    public int getLower(String name)
    {
	    int lowerLimit = 0;
	    if (name.equals("Pulse"))
	    	lowerLimit = 0;
	    else if (name.equals("Kilos"))
	    	lowerLimit = 0;
	    else if (name.equals("Miles"))
	    	lowerLimit = 0;
	    else if (name.equals("Yards"))
	    	lowerLimit = 0;
	    else if (name.equals("Mph"))
	    	lowerLimit = 0;
	    else if (name.equals("Min/100yds"))
	    	lowerLimit = 0;
	    else if (name.equals("Weight"))
	    	lowerLimit = 0;
	    else if (name.equals("Temp"))
	    	lowerLimit = 0;
	    	
	    return lowerLimit;
    }
    
    public int getUpper(String name)
    {
	    int upperLimit = 0;
	    if (name.equals("Pulse"))
	    	upperLimit = 200;
	    else if (name.equals("Kilos"))
	    	upperLimit = 200;
	    else if (name.equals("Miles"))
	    	upperLimit = 125;
	    else if (name.equals("Yards"))
	    	upperLimit = 5000;
	    else if (name.equals("Mph"))
	    	upperLimit = 25;
	    else if (name.equals("Min/100yds"))
	    	upperLimit = 5;
	    else if (name.equals("Weight"))
	    	upperLimit = 400;
	    else if (name.equals("Temp"))
	    	upperLimit = 120;
	    	
	    return upperLimit;
    }
    
    public int getIncrementSize(int lower, int upper)
    {
	 	int span = upper - lower;
	 	int increment = 1;
	 	int numberOfIncrements = (int)(span/increment);
	 	
	 	while(numberOfIncrements > 10)
	 	{
		 	increment++;
		 	numberOfIncrements = (int)(span/increment);
	 	}
	 	return increment;
    }
    
    public int translateToPointOnAxisY(int y, int upper)
    {
	    DrawGraph dg = new DrawGraph();
	    return(dg.translateToPointOnGraph(y,BTMOFYAXIS,YAXISHEIGHT,upper));
    }
    
    public void drawAxis(Graphics g, String axisName, int lower, int upper, int increment)
    {
	 	int remainder = 0;
	 	int axisY = 0;
	 	
	 	g.drawLine(22,50,22,300);
	 	g.drawString(axisName,LEFTENDBIGTIC - 10,30);
	 	
	 	for (int y=lower; y<=upper; y=y+increment)
	 	{
		 	remainder = (int)(Math.IEEEremainder(y,increment));
		 	if(remainder == 0)
		 	{
			 	axisY = translateToPointOnAxisY(y,upper);
			 	g.drawLine(LEFTENDBIGTIC,axisY,RIGHTENDBIGTIC,axisY);
			 	g.drawString("" + y,LEFTENDBIGTIC+17,axisY);
		 	}
	 	}
 	}

   public void actionPerformed(ActionEvent evt)
    {
        //hide();
    }

    private Debug d;
    private final int BTMOFYAXIS = 300;
    private final int YAXISHEIGHT = 250;
    private final int LEFTENDBIGTIC = 15;
    private final int RIGHTENDBIGTIC = 30;
    private final int LEFTENDSMALLTIC = 20;
    private final int RIGHTENDSMALLTIC = 25;
    private final int YINCREMENT = 10;
    private String localAxis = "";
    private String localAxisName = "";
    private Color localColor;
    private int localLowerLimit = 0;
    private int localUpperLimit = 0;
    private int localIncrementSize = 0;
}//~class DrawYPanel extends...






