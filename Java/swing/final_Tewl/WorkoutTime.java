/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: WorkoutTime.java
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

public class WorkoutTime
{
    WorkoutTime()
    {
		//System.out.println("WorkoutTime");
    }

    //Constructor: uses set methods to build time object
    WorkoutTime(String t)
    {
		//System.out.println("WorkoutTime");
        try
        {
            if(validTime(t))
            {
                setTime(t);
            }

            else throw new IllegalArgumentException("Invalid Time");
        }

        catch(IllegalArgumentException e)
        {
            throw e;
        }
    }//~WorkoutTime(String t)...

    public String getTime()
    {
		//System.out.println("WorkoutTime.getTime");
        return time;
    }

    public void setTime(String t)
    {
		//System.out.println("WorkoutTime.setTime");
        time = t;
    }

    public String formatTime(double time)
    {
		//System.out.println("WorkoutTime.formatTime");

		String convertedTime = "";
        DecimalFormat df = new DecimalFormat("##");
        String secsStr = "",
            minsStr = "";

        //use cast to get hours
        int hours = (int)(time/60);
        //use modulus to get mins
        int mins = ((int)time)%60;
        //get seconds
        double secsInFraction = time - (int)time;

        int secs = (int)(secsInFraction * 60);

        if (secs >= 0 && secs <= 9)
        {
            secsStr = "0" + secs;
        }

        else
        {
            secsStr = df.format(secs);
        }

        if (mins >= 0 && mins <= 9)
        {
            minsStr = "0" + mins;
        }

        else
        {
            minsStr = df.format(mins);
        }

        convertedTime = " " + hours + ":" + minsStr + ":" + secsStr;
        return convertedTime;
    }//~public String formatTime...

    public double getConvertedTime(String str)
    {
		//System.out.println("WorkoutTime.getConvertedTime");

        double convertedTime = 0.0;
        StringTokenizer t = new StringTokenizer(str,":");

        //convert strings to ints
        int hours = Integer.parseInt(t.nextToken());
        int mins = Integer.parseInt(t.nextToken());
        int secs = Integer.parseInt(t.nextToken());

        double hoursConvertedToMins = hours*60;
        double secsConvertedToFraction = secs/60.0;
        convertedTime = hoursConvertedToMins + mins + secsConvertedToFraction;
        return convertedTime;
    }//~public double getConvert...

    public double getConvertedTime()
    {
		//System.out.println("WorkoutTime.getConvertedTime");
        return getConvertedTime(time);
    }

    public boolean validTime(String str)
    {
		//System.out.println("WorkoutTime.validTime");

        boolean isValid = false;
        int strLen = str.length();
        StringTokenizer t = new StringTokenizer(str,":");
        char c = ' ';
        char [] charArray = {' '};

        boolean validString = false;
        int s = 0,
            m = 0,
            h = 0,
            firstColonIndex = 0,
            lastColonIndex = 0,
            colonCount = 0;

        if(strLen > 0)
        {
            //check if the str has two colons
            firstColonIndex = str.indexOf(':');
            lastColonIndex = str.lastIndexOf(':');

            if (//first colon should always be in the
            //sixth position from the end
            (firstColonIndex == (strLen - 6)) &&

            //last colon should always be in the
            //third position from the end
            (lastColonIndex == (strLen - 3)) &&

            //firstColonIndex should not be at beginning of string
            (firstColonIndex != 0) &&

            //lastColonIndex shoud not be at end of string
            (lastColonIndex != (strLen - 1)))
            {
                charArray = str.toCharArray();
                //check if the str has ONLY colons and digits
                for (int i=0; i<strLen; i++)
                {
                    c = charArray[i];
                    if (Character.isDigit(c) ^ (c == ':'))
                    {
                        if (c == ':')
                        {
                            colonCount++;

                            //Ensure there are no more than 2 colons in str
                            if (colonCount < 3)
                            {
                                validString = true;
                            }

                            else
                            {
                                validString = false;
                                break;
                            }
                        }//~if (c == ':')...
                    }//~if (Character.isDigit(c)...

                    else
                    {
                        validString = false;
                        break;
                    }
                }//~for (int i=0; i<strLen; ...

                if (validString)
                {
                    //parse str into colons and numbers
                    //get hours
                    h = Integer.parseInt(t.nextToken());
                    //get minutes
                    m = Integer.parseInt(t.nextToken());
                    //get seconds
                    s = Integer.parseInt(t.nextToken());

                    if ((m >= 0 && m <= 59) &&
                    (s >= 0 && s <= 59))
                    {
                        isValid = validString;
                    }
                }//~if (validString)...
            }//~(lastColonIndex != (strL...

        }//~if(strLen > 0)...

        return isValid;
    }//~public boolean validTime...

    private String time;
}//~class WorkoutTime...
