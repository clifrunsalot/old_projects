/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Workout.java
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



public class Workout{
	//============= Constructor =============//
	//Empty constructor that sets all data types to default values
	public Workout()
	{
		//System.out.println("Workout");
		events = new WorkoutEvent[MAXEVENTS];
		date = new WorkoutDate();
		len = 0;
		weather = "*";
		restingHR = 0;
		weight = 0;
		temp = 0;
		summary = "*";
		sitUps = 0;
		pushUps = 0;
		
		for(int i=0; i<MAXEVENTS; i++)
		{
			events[i] = new WorkoutEvent("*",0.0,"00:00:00","*");
		}
	}
	
	//============= Accessors =============//
	public WorkoutDate getDate()
	{
		//System.out.println("WorkoutDate.getDate");
		return date;
	}

	public WorkoutEvent [] getEvents()
	{
		//System.out.println("Workout.getEvents");

		for (int i=0; i<events.length; i++)
		{
			events[i].getEvent();
		}
		return events;
	}

	public String getWeather()
	{
		//System.out.println("Workout.getWeather");
		return weather;
	}

	public int getTemp()
	{
		//System.out.println("Workout.getTemp");
		return temp;
	}

	public String getSummary()
	{
		//System.out.println("Workout.getSummary");
		return summary;
	}

	public int getRestingHR()
	{
		//System.out.println("Workout.getRestingHR");
		return restingHR;
	}

	public int getWeight()
	{
		//System.out.println("Workout.getWeight");
		return weight;
	}

	public int getPu()
	{
		//System.out.println("Workout.getPu");
		return pushUps;
	}

	public int getSu()
	{
		//System.out.println("Workout.getSu");
		return sitUps;
	}
	
	public int getNumberOfEvents()
	{
		return len;
	}

	//============= Mutators =============//
	public void setDate(int m,int d,int y)
	{
		//System.out.println("Workout.setDate");
		try
		{
			date = new WorkoutDate(m,d,y);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}//~public void setDate(int ...

	public void setWeather(String w)
	{
		//System.out.println("Workout.setWeather");
		weather = w;
	}

	public void setTemp(int tmp)
	{
		//System.out.println("Workout.setTemp");
		temp = tmp;
	}

	public void setEvent(String e, double d, String t, String routeCode)
	{
		//System.out.println("Workout.setEvent");
		if(len <= MAXEVENTS)
		{
			event = new WorkoutEvent(e,d,t,routeCode);
			events[len] = event.getEvent();
			len++;
		}
	}

	public void setSummary(String s)
	{
		//System.out.println("Workout.setSummary");
		summary = s;
	}

	public void setRestingHR(int hr)
	{
		//System.out.println("Workout.setRestingHR");
		restingHR = hr;
	}

	public void setWeight(int w)
	{
		//System.out.println("Workout.setWeight");
		weight = w;
	}

	public void setPu(int p)
	{
		//System.out.println("Workout.setPu");
		pushUps = p;
	}

	public void setSu(int s)
	{
		//System.out.println("Workout.setSu");
		sitUps = s;
	}

	//Reads one record from input stream
	public void readData(StringTokenizer t)
		throws    IOException
	{
		//System.out.println("Workout.readData");

		int month = Integer.parseInt(t.nextToken());
		int day = Integer.parseInt(t.nextToken());
		int year = Integer.parseInt(t.nextToken());
		setDate(month,day,year);
		setTemp(Integer.parseInt(t.nextToken()));
		setWeather(t.nextToken());
		setRestingHR(Integer.parseInt(t.nextToken()));
		setWeight(Integer.parseInt(t.nextToken()));

		for (int i=0; i<MAXEVENTS; i++)
		{
			setEvent(t.nextToken(),
					Double.parseDouble(t.nextToken()),
					t.nextToken(),
					t.nextToken());
		}

		setSummary(t.nextToken());
		setPu(Integer.parseInt(t.nextToken()));
		setSu(Integer.parseInt(t.nextToken()));
	}//~IOException...

	public void printData (PrintStream fout)
	{
		//System.out.println("Workout.print");
		fout.print
			(getDate().getMonth() + "|" +
			getDate().getDay() + "|" +
			getDate().getYear() + "|" +
			getTemp() + "|" +
			getWeather() + "|" +
			getRestingHR() + "|" +
			getWeight() + "|");

		for (int i=0; i<MAXEVENTS; i++)
		{
			events[i].printData(fout);
		}

		fout.print
			(getSummary() + "|" +
			getPu() + "|" +
			getSu() + "|");

	}//~public void print (Print...

	//Private types
	private WorkoutDate date;
	private WorkoutEvent [] events;
	private WorkoutEvent event;
	private final int MAXEVENTS = 3;
	private int len;
	private String weather;
	private int restingHR;
	private int weight;
	private int temp;
	private String summary;
	private int sitUps;
	private int pushUps;
}//~class Workout...	??




