/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: PrintPanel.java
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.util.*;
import javax.swing.*;

public class PrintPanel extends JPanel implements Printable
{
	PrintPanel(Workout w, int record)
	{
		wkout = new Workout();
		wkout = w;
		leftXBound = 0;
		topYBound = 10;
		rec = record;
	}

	public void paintComponent(Graphics g, PageFormat pf)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawPage(g2,pf);
	}

	public int print (Graphics g, PageFormat pf, int page)
		throws PrinterException
	{
		if (page >= 1) return Printable.NO_SUCH_PAGE;
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(pf.getImageableX(),pf.getImageableY());
		drawPage(g2,pf);
		return Printable.PAGE_EXISTS;
	}

	public void drawPage(Graphics2D g2, PageFormat pf)
	{
		WorkoutEvent [] events = wkout.getEvents();
		f = new Font("Serif",Font.PLAIN,12);
		fm = g2.getFontMetrics(f);
		g2.setFont(f);

		int x = leftXBound;
		int y = topYBound;
		final int rightXBound = (int)pf.getImageableWidth() - 50;
		String recStr = "" + rec;
		String monthStr = "" + wkout.getDate().getMonth();
		String dayStr = "" + wkout.getDate().getDay();
		String yearStr = "" + wkout.getDate().getYear();
 		String eventStr = "";
 		String timeStr = "";
 		String distanceStr = "";
		String tempStr = "" + wkout.getTemp();
		String weatherStr = wkout.getWeather();
		String summaryStr = wkout.getSummary();
		String puStr = "" + wkout.getPu();
		String suStr = "" + wkout.getSu();

		String recLabel = "Record: ";
		String dateLabel = "Date: ";
		String eventLabel = "Event: ";
		String timeLabel = "Time: ";
		String distanceLabel = "Distance: ";
		String tempLabel = "Temp: ";
		String weatherLabel = "Weather: ";
		String summaryLabel = "Summary: ";
		String puLabel = "Push-up: ";
		String suLabel = "Sit-up: ";

		//Record Line
		g2.drawString(recLabel,x,y);
		x += fm.stringWidth(recLabel);

		g2.drawString(recStr,x,y);

		//Date Line
		x = leftXBound;
		y += 15;

		g2.drawString(dateLabel,x,y);
		x += fm.stringWidth(dateLabel);

		g2.drawString(monthStr + "/",x,y);
		x += fm.stringWidth(monthStr + "/");

		g2.drawString(dayStr + "/",x,y);
		x += fm.stringWidth(dayStr + "/");

		g2.drawString(yearStr,x,y);

		for (int i=0; i<events.length; i++)
		{
			//Event Line
			x = leftXBound;
			y += 15;

			g2.drawString(eventLabel,x,y);
			x += fm.stringWidth(eventLabel);

			g2.drawString(events[i].getName(),x,y);

			//Distance Line
			x = leftXBound;
			y += 15;

			g2.drawString(distanceLabel,x,y);
			x += fm.stringWidth(distanceLabel);

			g2.drawString("" + events[i].getDistance(),x,y);

			//Time Line
			x = leftXBound;
			y += 15;

			g2.drawString(timeLabel,x,y);
			x += fm.stringWidth(timeLabel);

			g2.drawString(events[i].getTime().getTime(),x,y);
		}

		//Weather Line
		x = leftXBound;
		y += 15;

		g2.drawString(weatherLabel,x,y);
		x += fm.stringWidth(weatherLabel);

		g2.drawString(weatherStr,x,y);

		//Temp Line
		x = leftXBound;
		y += 15;

		g2.drawString(tempLabel,x,y);
		x += fm.stringWidth(tempLabel);

		g2.drawString(tempStr,x,y);

		//Push-up Line
		x = leftXBound;
		y += 15;

		g2.drawString(puLabel,x,y);
		x += fm.stringWidth(puLabel);

		g2.drawString(puStr,x,y);

		//Sit-up Line
		x = leftXBound;
		y += 15;

		g2.drawString(suLabel,x,y);
		x += fm.stringWidth(suLabel);

		g2.drawString(suStr,x,y);

		//Summary Line
		x = leftXBound;
		y += 15;

		g2.drawString(summaryLabel,x,y);
		x += fm.stringWidth(summaryLabel);

		g2.drawString(summaryStr,x,y);

		String para = summaryLabel + summaryStr;
		StringTokenizer t = new StringTokenizer(para," ");
		while(t.hasMoreTokens())
		{
			String token = t.nextToken();
			g2.drawString(token + " ",x,y);
			x += fm.stringWidth(token + " ");
			if (x > rightXBound)
			{
				x = leftXBound;
				y += 15;
			}
		}

    }

	private Workout wkout;
	FontMetrics fm;
	Font f;
	private final int rec;
	private final int leftXBound;
	private final int topYBound;
}




