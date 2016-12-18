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

class Swim extends Equipment
{
    Swim()
    {
    }

    public String getGoggleSize()
    {
        return goggleSize;
    }

    public void setGoggleSize(String gSize)
    {
        goggleSize = gSize;
    }

    public void readData(StringTokenizer t)
    {
        setGoggleSize(t.nextToken());
    }

    public void print(PrintWriter f)
    {
        f.print("equipswim" + "|");
        f.print(goggleSize + "|");
    }

    private String goggleSize = "*";
}//~class Swim extends Equip...




