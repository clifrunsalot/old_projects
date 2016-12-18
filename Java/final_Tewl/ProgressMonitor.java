/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: ProgressMonitor.java
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

public class ProgressMonitor extends JDialog
{
    public ProgressMonitor(int records)
    {
        int baseofframe = 310;
        int heightofframe = 80;
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        int y_loc = (screen.height/2) - (heightofframe/2);
        int x_loc = (screen.width/2) - (baseofframe/2);
        setLocation(x_loc,y_loc);
        setTitle("Loading ...");
        setSize(baseofframe,heightofframe);
        totalRecords = records;
        p = new JPanel();
        recordLabel = new JLabel("Waiting to load.                       ");
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(280,20));
        progress.setMinimum(0);
        progress.setMaximum(totalRecords);
        progress.setValue(0);
        progress.setBounds(20,35,260,20);
        p.add(recordLabel);
        p.add(progress);
        getContentPane().add(p);
    }//~public ProgressMonitor(i...

    public void update(int i)
    {
        recordLabel.setText ("Loading record " + i + " of " + totalRecords);
        Rectangle labelRect = recordLabel.getBounds();
        labelRect.x = 0;
        labelRect.y = 0;
        recordLabel.paintImmediately(labelRect);
        
        progress.setValue(i);
        Rectangle progressRect = progress.getBounds();
        progressRect.x = 0;
        progressRect.y = 0;
        progress.paintImmediately(progressRect);
    }//~public void update(int i...

    private int totalRecords;
    private JPanel p;
    private JProgressBar progress;
    private JLabel recordLabel;
}//~class ProgressMonitor ex...
