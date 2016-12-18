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

class Equipment
{
    Equipment()
    {
    }

    public String getShoeModel()
    {
        return shoeModel;
    }

    public String getShoeSize()
    {
        return shoeSize;
    }

    public void setShoeModel(String model)
    {
        shoeModel = model;
    }

    public void setShoeSize(String size)
    {
        shoeSize = size;
    }

    public void readData(StringTokenizer t)
    {
        setShoeSize(t.nextToken());
        setShoeModel(t.nextToken());
    }

    public void print(PrintWriter f)
    {
        f.print(shoeSize + "|");
        f.print(shoeModel + "|");
    }

    protected String shoeModel = "*";
    protected String shoeSize = "*";
}//~class Equipment...




