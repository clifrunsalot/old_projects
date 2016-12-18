/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Bike.java
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
Instantiates the Bike class.
*/
public class Bike extends Equipment
{
    Bike()
    {
		bikeFrameSize = "*";
		bikeModel = "*";
		bikeName = "*";
		bikeSN = "*";
		bikeTireSize = "*";
		seatPostDiameter = "*";
    }

    /**
    Returns bike frame size.
    
    @param none
    
    @return Bike size in String format
    */
    public String getBikeFrameSize()
    {
        return bikeFrameSize;
    }

    /**
    Returns bike model.
    
    @param none
    
    @return Bike model in String format
    */
    public String getBikeModel()
    {
        return bikeModel;
    }

    /**
    Returns bike name.
    
    @param none
    
    @return Bike name in String format
    */
    public String getBikeName()
    {
        return bikeName;
    }

    /**
    Returns bike tire size.
    
    @param none
    
    @return Bike tire size in String format
    */
    public String getBikeTireSize()
    {
        return bikeTireSize;
    }

    /**
    Returns bike serial number.
    
    @param none
    
    @return Bike serial number in String format
    */
    public String getBikeSN()
    {
        return bikeSN;
    }

    /**
    Returns bike seat post diameter.
    
    @param none
    
    @return Bike seat post diameter in String format
    */
    public String getSeatPostDiameter()
    {
        return seatPostDiameter;
    }

    /**
    Sets bike frame size.
    
    @param bSize Bike size in String format
    
    @return none
    */
    public void setBikeFrameSize(String bSize)
    {
        bikeFrameSize = bSize;
    }

    /**
    Sets bike model.
    
    @param bModel Bike model in String format
    
    @return none
    */
    public void setBikeModel(String bModel)
    {
        bikeModel = bModel;
    }

    /**
    Sets bike name.
    
    @param bName Bike name in String format
    
    @return none
    */
    public void setBikeName(String bName)
    {
        bikeName = bName;
    }

    /**
    Sets bike tire size.
    
    @param bTireSize Bike tire size in String format
    
    @return none
    */
    public void setBikeTireSize(String bTireSize)
    {
        bikeTireSize = bTireSize;
    }

    /**
    Sets bike serial number.
    
    @param bSN Bike serial number
    
    @return none
    */
    public void setBikeSN(String bSN)
    {
        bikeSN = bSN;
    }

    /**
    Sets seat post diameter.
    
    @param bDiameter Bike seat post diameter in String format
    
    @return void
    */
    public void setSeatPostDiameter(String bDiameter)
    {
        seatPostDiameter = bDiameter;
    }

    /**
    Reads tokens passed in and sets bike attributes.
    
    @param t StringTokenizer
    
    @return void
    */
    public void readData(StringTokenizer t)
    {
        super.readData(t);
        setBikeFrameSize(t.nextToken());
        setBikeModel(t.nextToken());
        setBikeName(t.nextToken());
        setBikeTireSize(t.nextToken());
        setBikeSN(t.nextToken());
        setSeatPostDiameter(t.nextToken());
    }//~public void readData(Str...

    /**
    Prints bike attribute to stream passed in.
    
    @param f PrintStream
    
    @return void
    */
    public void print(PrintStream f)
    {
        f.print("equipbike" + "|");
        super.print(f);
        f.print(getBikeFrameSize() + "|");
        f.print(getBikeModel() + "|");
        f.print(getBikeName() + "|");
        f.print(getBikeTireSize() + "|");
        f.print(getBikeSN() + "|");
        f.print(getSeatPostDiameter() + "|");
    }//~public void print(PrintW...

    /**
    Bike frame size
    */
    private String bikeFrameSize = "*";
    
    /**
    Bike model
    */
    private String bikeModel = "*";
    
    /**
    Bike name
    */
    private String bikeName = "*";
    
    /**
    Bike serial number
    */
    private String bikeSN = "*";
    
    /**
    Bike tire size
    */
    private String bikeTireSize = "*";
    
    /**
    Bike seat post diameter
    */
    private String seatPostDiameter = "*";

}//~class Bike extends Equip...




