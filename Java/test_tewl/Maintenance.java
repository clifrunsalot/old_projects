/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Maintenance.java
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

public class Maintenance
{
    Maintenance()
    {
		maintDate = "*";
		maintSummary = "*";
    }

    public void setMaintDate(String s)
    {
        maintDate = s;
    }

    public void setMaintSummary(String s)
    {
        maintSummary = s;
    }

    public String getMaintDate()
    {
        return maintDate;
    }

    public String getMaintSummary()
    {
        return maintSummary;
    }

    public void readData(StringTokenizer t) throws
    IOException
    {
        setMaintDate(t.nextToken());
        setMaintSummary(t.nextToken());
    }

    public void printData(PrintStream fout)
    {
        fout.print(getMaintDate() + "|" +
            getMaintSummary() + "|");
    }

    private String maintDate = "*";
    private String maintSummary = "*";
}//~class Maintenance...





