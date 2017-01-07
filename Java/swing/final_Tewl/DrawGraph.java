/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: DrawGraph.java
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

public class DrawGraph extends JPanel
{
    public DrawGraph(Vector log, String event, String leftAxis, String rightAxis)
    {
	    d = new Debug();
	    localLeftAxis = leftAxis;
	    localRightAxis = rightAxis;
        records = log.size();
        numberCoords = records;

//         //polyline coords from first to last point
//         xPoints = new int [numberCoords];
//         yPoints = new int [numberCoords];

        localLog = new Vector();
        localLog = log;
        localEvent = event;
        setPreferredSize(new Dimension
        	(records*(XINCREMENT+1),MAXYAXISSIZE));
// 		setPreferredSize(new Dimension(550,400));
        f = new Font("SansSerif",Font.PLAIN,9);
        f1 = new Font("SansSerif",Font.PLAIN,10);
	    setBackground(Color.white);

    }
    
    public DrawGraph(){}

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setFont(f);
        drawXAxis(g,localLog.size());
        plotGraph(g,localEvent,localLog,"left",localLeftAxis);
		plotGraph(g,localEvent,localLog,"right",localRightAxis);
//		g.drawPolyline(xPoints, yPoints, numberCoords);
    }

    public void drawXAxis(Graphics chart, int eventNumber)
    {
    	int x=10;

    	chart.setColor(Color.black);

        //draw horizontal line
        chart.drawLine(10,300,eventNumber*XINCREMENT,300);

		//draw x-axis tic marks
        for(int ticCount=0; ticCount<eventNumber; ticCount++)
        {
			chart.drawLine(x,TOPOFTIC,x,BOTTOMOFTIC);
			x = x + XINCREMENT;
        }
    }

    public void plotPoint (Graphics chart,
    						int x,
    						int point,
    						String axisSide)
	{
        //draw points on graph
		//Since the point is measured from the top on the window
		// subtract the point from BTMOFYAXIS, which is 290 pixels
		// from the top on the y-axis.
		if (axisSide.equals("left"))
		{
        	chart.setColor(Color.red);

            // x-3 compensates for the width of the oval, thus
            // centering it.
            chart.fillOval((x - 2),(point - 2),5,5);
        }
        else if (axisSide.equals("right"))
        {
			chart.setColor(Color.blue);

			// x-3 compensates for the width of the rectangle, thus
        	// centering it.
            chart.drawRect((x - 3),(point - 3),6,6);
        }

	}

    public void plotGraph(Graphics chart,
    						String localEvent,
    						Vector eventList,
    						String axisSide,
    						String axisName)
    {
	    //point corresponding to axis passed in.
        int point = MAXYAXISSIZE;

        //spacer between records
        int x = 10;

        int recordStrAdjusted;
        WorkoutEvent [] events;

        // variables to manage printing the dates
        int lastMonth = 0;
        int lastYear = 0;
        int currentMonth = 0;
        int currentYear = 0;

        for(int record=0; record<eventList.size(); record++)
        {
            Workout w = new Workout();
            w = (Workout)(eventList.elementAt(record));
            events = w.getEvents();

         	for (int i=0; i<events.length; i++)
            {
	            if(events[i].getName().equals(localEvent))
	            {
		            if (axisName.equals("Mph"))
		            {
			            point = getPacePoint(events[i].getEvent());
			            plotPoint(chart,x,point,axisSide);
 		            }
		            else if (axisName.equals("Min/100yds"))
		            {
			            point = getPacePoint(events[i].getEvent());
			            plotPoint(chart,x,point,axisSide);
 		            }
 		            else if (axisName.equals("Miles"))
 		            {
			            point = getDistancePoint(events[i].getEvent());
			            plotPoint(chart,x,point,axisSide);
		            }
 		            else if (axisName.equals("Yards"))
 		            {
			            point = getDistancePoint(events[i].getEvent());
			            plotPoint(chart,x,point,axisSide);
		            }
 		            else if (axisName.equals("Pulse"))
 		            {
			            point = getPulsePoint(w);
			            plotPoint(chart,x,point,axisSide);
		            }
		            else if (axisName.equals("Weight"))
		            {
		            	point = getWeightPoint(w);
		            	plotPoint(chart,x,point,axisSide);
	            	}
		            else if (axisName.equals("Temp"))
		            {
		            	point = getTempPoint(w);
		            	plotPoint(chart,x,point,axisSide);
	            	}

// 		            //=============== Polyline ==========================//

// 		            chart.setColor(Color.black);
// 		            chart.drawLine(x,leftPoint,x,rightPoint);

// 		            //assign the x and y coordinates to the corresponding arrays
// 		            // of the polyline.
// 		            xPoints[record] = x;
// 		            yPoints[record] = leftAxisPoint;

		            //===============  record number ===================//

		            //increment record because log is zero-based.
		            recordStrAdjusted=record+1;

		            chart.setFont(f);
					chart.setColor(Color.black);

		            //draw record number
		            chart.drawString
		            (""+recordStrAdjusted,x-2,BOTTOMOFTIC+10);
		            	x = XINCREMENT + x;

		            //===============  Month and Year ==================//

		            //get current month and year
		            //
				    currentMonth = w.getDate().getMonth();
		            currentYear = w.getDate().getYear();

		            //if the previous month doesn't equal the current month
		            // print the current month and current year.
		            //
		            if (currentMonth != lastMonth)
		            {
				        chart.setFont(f1);
			            chart.drawString("" + currentMonth + "/" + currentYear,
			            					x,
			            					BOTTOMOFTIC + 25);
			        	lastMonth = currentMonth;
			        	lastYear = currentYear;
		         	}
	            }
            }
        }
    }

	//=========== Convert value to point on Graph ==================//
	
	public int translateToPointOnGraph(double value, 
										int BtmOfAxisInPixels, 
										int YAxisHeightInPixels, 
										int UpperAxisLimitDisplayed)
	{
		double point = 0;
		double tBtmOfAxisInPixels = BtmOfAxisInPixels;
		double tYAxisHeightInPixels = YAxisHeightInPixels;
		double tUpperAxisLimitDisplayed = UpperAxisLimitDisplayed;
		
		point = tBtmOfAxisInPixels - ((((double)value)/((double)tUpperAxisLimitDisplayed)) * tYAxisHeightInPixels);
		return ((int)point);
	}

	//================== Speed to point conversion methods =============================//

    public int convertSpeedToPoint(double speed)
    {
	    int upperLimit = 0;
	    
	    // if the speed is 0, then this translates to 0 on the y-axis,
	    // which is the same as 298 pixels from the top of the window.
	    if (speed <= 0)
		    return 300;
		else
		{
			if (localEvent.equals("swim"))
			{
				upperLimit = 5;
			}
			else
			{
				upperLimit = 25;
	    	}
			return (translateToPointOnGraph(speed,BTMOFYAXIS,YAXISHEIGHT,upperLimit));
    	}
    }

    public int getPacePoint(WorkoutEvent event)
    {
	    TewlUtilities util = new TewlUtilities();
        double avgSpeed;
        double distance;
        double time;
        int point;

        time = event.getTime().getConvertedTime();
        distance = event.getDistance();
        if (event.getName().equals("swim"))      	
       		point = convertSpeedToPoint(util.getMinsPer100yds(time,distance));
       	else
       		point = convertSpeedToPoint(util.getAvgSpeed(event.getName(),time,distance));

        return point;
    }//~public int getPoint(Work...

	//========== Distance to point conversion methods ================//

    public int getDistancePoint(WorkoutEvent event)
    {
		int upperLimit = 0;
	    if (event.getName().equals("run") || event.getName().equals("bike"))
	    {
		    upperLimit = 125;
		}
		else if (event.getName().equals("swim"))
		{
			upperLimit = 5000;
		}
		return (translateToPointOnGraph(event.getDistance(),BTMOFYAXIS,YAXISHEIGHT,upperLimit));
    }//~public int getPoint(Work...

	//========== Pulse to point conversion methods =================//

	public int getPulsePoint(Workout w)
	{
		int upperLimit = 200;
		return (translateToPointOnGraph(w.getRestingHR(),BTMOFYAXIS,YAXISHEIGHT,upperLimit));
	}
	
	//========== Weight to point conversion methods =================//

	public int getWeightPoint(Workout w)
	{
		int upperLimit = 400;
		return (translateToPointOnGraph(w.getWeight(),BTMOFYAXIS,YAXISHEIGHT,upperLimit));
	}
	
	//========== Temperature to point conversion methods =================//

	public int getTempPoint(Workout w)
	{
		int upperLimit = 120;
		return (translateToPointOnGraph(w.getTemp(),BTMOFYAXIS,YAXISHEIGHT,upperLimit));
	}
	
	private Debug d;
	private int records;
    private Vector localLog;
    private final int MAXYAXISSIZE = 300;
    private final int BTMOFYAXIS = 300;
    private final int YAXISHEIGHT = 250;
    private final int TOPOFTIC = 295;
    private final int BOTTOMOFTIC = 305;
    private final int XINCREMENT = 25;
    private final int BTMSPACER = 15;
    private final int LEFTSPACER = 7;
    private String localEvent = "",
    				localLeftAxis = "",
    				localRightAxis = "";
    private int recordCount = 0;
    private int [] xPoints;
    private int [] yPoints;
    private int numberCoords = 0;
    private Font f, f1;
}//~class DrawGraph extends ...




