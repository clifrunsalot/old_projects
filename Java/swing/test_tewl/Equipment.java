/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Equipment.java
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
Instantiates Equipment class.
*/
public class Equipment
{
    Equipment()
    {
		shoeModel = "*";
		shoeSize = "*";
    }

    /**
    Returns shoe model.
    
    @param none
    
    @return Shoe model in String format
    */
    public String getShoeModel()
    {
        return shoeModel;
    }

    /**
    Returns shoe size.
    
    @param none
    
    @return Shoe size in String format
    */
    public String getShoeSize()
    {
        return shoeSize;
    }

    /**
    Sets shoe model.
    
    @param model Shoe model in String format
    
    @return Shoe model in String format
    */
    public void setShoeModel(String model)
    {
        shoeModel = model;
    }

    /**
    Sets shoe size.
    
    @param size Shoe size in String format
    
    @return void
    */
    public void setShoeSize(String size)
    {
        shoeSize = size;
    }

    /**
    Reads data from stream passed in.
    
    @param t StringTokenizer
    
    @return void
    */
    public void readData(StringTokenizer t)
    {
        setShoeSize(t.nextToken());
        setShoeModel(t.nextToken());
    }

    /**
    Prints data to stream passed in.
    
    @param f PrintStream
    
    @return void
    */
    public void print(PrintStream f)
    {
        f.print(shoeSize + "|");
        f.print(shoeModel + "|");
    }

    /**
    Shoe model
    */
    protected String shoeModel = "*";
    
    /**
    Shoe size
    */
    protected String shoeSize = "*";
    
}//~class Equipment...





