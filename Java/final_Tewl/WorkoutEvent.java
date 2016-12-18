/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: WorkoutEvent.java
*/

import java.io.*;

public class WorkoutEvent
{
	WorkoutEvent(String e, double d, String t, String routeCode)
	{
		//System.out.println("WorkoutEvent");
		setEvent(e,d,t,routeCode);
	}

	public WorkoutEvent getEvent()
	{
		//System.out.println("WorkoutEvent.getEvent");
		return this;
	}

	public String getName()
	{
		//System.out.println("WorkoutEvent.getName");
		return name;
	}

	public double getDistance()
	{
		//System.out.println("WorkoutEvent.getDistance");
		return distance;
	}

	public WorkoutTime getTime()
	{
		//System.out.println("WorkoutEvent.getTime");
		return time;
	}

	public Route getRoute()
	{
		//System.out.println("WorkoutEvent.getRoute");
		return route;
	}

	public void setEvent(String n, double d, String t, String routeCode)
	{
		//System.out.println("WorkoutEvent.setEvent");
		name = n;
		distance = d;
		setTime(t);

		route = new Route();
		route.setCode(routeCode);
		route.setDescription("*");
	}

	public void setTime(String t)
	{
		//System.out.println("WorkoutEvent.setTime");
		try
		{
			time = new WorkoutTime(t);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}//~public void setTime(Stri...

	public void printData(PrintStream fout)
	{
		fout.print
			(getName() + "|" +
			getDistance() + "|" +
			getTime().getTime().toString() + "|" +
			getRoute().getCode() + "|" );
	}

	
	private String name;
	private double distance;
	private WorkoutTime time;
	private Route route;
}






