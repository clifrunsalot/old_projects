/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Swim.java
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


/**
Instantiates Swim class
*/
public class Swim extends Equipment
{
    Swim()
    {
		goggleSize = "*";    
	}

    /**
    Returns goggle size.
    
    @param none
    
    @return Goggle size in String format
    */
    public String getGoggleSize()
    {
        return goggleSize;
    }

    /**
    Sets goggle size
    
    @param gSize Goggle size in String format
    
    @return void
    */
    public void setGoggleSize(String gSize)
    {
        goggleSize = gSize;
    }

    /**
    Reads data from stream passed in.
    
    @param t StringTokenizer
    
    @return void
    */
    public void readData(StringTokenizer t)
    {
        setGoggleSize(t.nextToken());
    }

    /**
    Prints data to stream passed in.
    
    @param f PrintStream
    
    @return void
    */
    public void print(PrintStream f)
    {
        f.print("equipswim" + "|");
        f.print(goggleSize + "|");
    }

    private String goggleSize = "*";
}//~class Swim extends Equip...





