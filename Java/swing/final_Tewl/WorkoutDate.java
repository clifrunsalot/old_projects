/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: WorkoutDate.java
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

public class WorkoutDate
{
    WorkoutDate()
    {
		//System.out.println("WorkoutDate");
        Calendar today = new GregorianCalendar();
        setDate(today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),today.get(Calendar.YEAR));
    }

    WorkoutDate(int m,int d,int y)
    {
		//System.out.println("WorkoutDate");
        try
        {
            if(validDate(m,d,y))
            	setDate(m,d,y);
            else
            	throw new IllegalArgumentException ("Invalid date");
        }

        catch(IllegalArgumentException e)
        {
            throw e;
        }
    }//~WorkoutDate(int m,int d,...

    //Validates month and day.  Year passed in for future
    //purposes.
    public boolean validDate(int m,int d,int y)
    {
		//System.out.println("WorkoutDate.validDate");
        boolean monthAndDayOk = false;
        boolean result = false;
        Calendar today = new GregorianCalendar();

        //month(1..12)
        //day(1..31)
        if ((m >= 1)&&(m <= 12)&&
        (d >= 1)&&(d <= 31))
        {
            //January
            if ((m == 1)&&(d <= 31))
            monthAndDayOk = true;
            //February
            else if ((m == 2)&&(d <= 28))
            monthAndDayOk = true;
            //March
            else if ((m == 3)&&(d <= 31))
            monthAndDayOk = true;
            //April
            else if ((m == 4)&&(d <= 30))
            monthAndDayOk = true;
            //May
            else if ((m == 5)&&(d <= 31))
            monthAndDayOk = true;
            //June
            else if ((m == 6)&&(d <= 30))
            monthAndDayOk = true;
            //July
            else if ((m == 7)&&(d <= 31))
            monthAndDayOk = true;
            //August
            else if ((m == 8)&&(d <= 31))
            monthAndDayOk = true;
            //September
            else if ((m == 9)&&(d <= 30))
            monthAndDayOk = true;
            //October
            else if ((m == 10)&&(d <= 31))
            monthAndDayOk = true;
            //November
            else if ((m == 11)&&(d <= 30))
            monthAndDayOk = true;
            //Must be December
            else if ((m == 12)&&(d <= 31))
            monthAndDayOk = true;
            else;
        }//~(d >= 1)&&(d <= 31))...

        if (monthAndDayOk)
        	result = true;
        return result;
    }//~public boolean validDate...

    //============= Accessors =============//
    public Calendar getDate()
    {
		//System.out.println("WorkoutDate.getDate");
        return date;
    }

    public int getMonth()
    {
		//System.out.println("WorkoutDate.getMonth");
        return (month + 1);
    }

    public int getDay()
    {
		//System.out.println("WorkoutDate.getDay");
        return day;
    }

    public int getYear()
    {
		//System.out.println("WorkoutDate.getYear");
        return year;
    }

    //============= Mutators =============//
    public void setDate(int m,int d,int y)
    {
		//System.out.println("WorkoutDate.setDate");

		//Since dates are zero-based, meaning that January
        //thru December range from 0 .. 11,
        //the month passed in is converted to the
        //appropriate position number before being
        //used to create a calendar date.
        int mon = m-1;
        date = new GregorianCalendar(y,mon,d);
        setMonth();
        setDay();
        setYear();
    }//~public void setDate(int ...

    public void setMonth()
    {
		//System.out.println("WorkoutDate.setMonth");
        month = date.get(Calendar.MONTH);
    }

    public void setDay()
    {
		//System.out.println("WorkoutDate.setDay");
        day = date.get(Calendar.DAY_OF_MONTH);
    }

    public void setYear()
    {
		//System.out.println("WorkoutDate.setYear");
        year = date.get(Calendar.YEAR);
    }

    //Private data types
    private Calendar date;
    private int month;
    private int day;
    private int year;
}//~class WorkoutDate...






