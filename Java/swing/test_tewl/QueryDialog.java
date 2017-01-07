/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: QueryDialog.java
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

public class QueryDialog extends JDialog implements ActionListener,
    KeyListener
{
    public QueryDialog()
    {
        setModal(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        setTitle("Search");

        //Data needed to position dialog in the middle of the window.
        int baseofframe = 225;
        int heightofframe = 150;
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        int y_loc = (screen.height/2) - (heightofframe/2);
        int x_loc = (screen.width/2) - (baseofframe/2);
        setLocation(x_loc,y_loc);

        //Panel to hold three textfields
        datePanel = new JPanel();

        //Month,Day,Year textfields
        monthField = new JTextField(4);
        monthField.setToolTipText("Month - \"mm\"");
        monthField.setActionCommand("monthField");
        monthField.addKeyListener(this);
        monthField.setHorizontalAlignment
        (JTextField.RIGHT);
        dayField = new JTextField(4);
        dayField.setToolTipText("Day - \"dd\"");
        dayField.setActionCommand("dayField");
        dayField.addKeyListener(this);
        dayField.setHorizontalAlignment
        (JTextField.RIGHT);
        yearField = new JTextField(8);
        yearField.setToolTipText("Year - \"yyyy\"");
        yearField.setActionCommand("yearField");
        yearField.addKeyListener(this);
        yearField.setHorizontalAlignment
        (JTextField.RIGHT);

        //Add textfields to datePanel
        datePanel.add(monthField);
        datePanel.add(dayField);
        datePanel.add(yearField);

        //Panel to hold buttons
        buttonPanel = new JPanel();

        //Find,clear,close buttons
        findButton = new JButton("Find");
        findButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        //Add buttons to buttonPanel
        buttonPanel.add(findButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);

        //Main panel on which the datePanel and buttonPanel
        //will sit.  This is necessary because this
        //dialog frame uses the flowlayout, which forces
        //no component position control when the dialog is resized
        //Building each panel and then adding each
        //mainPanel will ensure a certain appearance everytime
        //the dialog is invoked.
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(datePanel,BorderLayout.NORTH);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
    }//~public QueryDialog()...

    //Retrieves the three date strings from dialog
    //and return a string array.
    public String [] getDateStrings()
    {
        boolean badvalue = false;
        String m = monthField.getText();
        String d = dayField.getText();
        String y = yearField.getText();
        String [] stringArray =
        {
            m,d,y
        };

        return stringArray;
    }//~public String [] getDate...

    //Accesses class field used as flag for Log class
    public boolean isValidRequest()
    {
        return validRequest;
    }

    //Validates each string in the array and returns
    //true if each string is convertible to a digit
    //and contains valid month and day values.
    public boolean validDate(String [] da)
    {
        //System.out.println("inside validDate()");

        /*
da[0] is the month
da[1] is the day
da[2] is the year
*/

        boolean valid = false;
        validRequest = false;
        WorkoutDate dateStr = new WorkoutDate();

        int m = Integer.parseInt(da[0]);
        int d = Integer.parseInt(da[1]);
        int y = Integer.parseInt(da[2]);

        if(dateStr.validDate(m,d,y))
        {
            valid = true;
            validRequest = true;
        }

        return valid;
    }//~public boolean validDate...

    public void keyPressed(KeyEvent k)
    {
    }

    public void keyTyped(KeyEvent k)
    {
        Component comp = k.getComponent();

        if(comp instanceof JTextField)
        {
            char ch = k.getKeyChar();
            Object source = k.getSource();
            if(ch < '0' || ch > '9')
            	k.consume();
            else if(((source == monthField) && (monthField.getText().length() >= 2)) ||
	            ((source == dayField) && (dayField.getText().length() >= 2)) ||
	            ((source == yearField) && (yearField.getText().length() >= 4)))
	            k.consume();
        }//~if(comp instanceof JText...
    }//~public void keyTyped(Key...

    public void keyReleased(KeyEvent k)
    {
    }

    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();

        if (source == findButton)
        {
            String [] dateArray = getDateStrings();
            if((dateArray[0].length() > 0) &&
            (dateArray[1].length() > 0) &&
            (dateArray[2].length() > 0))
            {
                if (validDate(dateArray))
                setVisible(false);
                else
                {
                    JOptionPane.showMessageDialog(
                        this,
                        "Invalid date",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }//~(dateArray[2].length() >...

            else
            JOptionPane.showMessageDialog(
                this,
                "Invalid date",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//~if (source == findButton...

        else if (source == clearButton)
        {
            monthField.setText("");
            dayField.setText("");
            yearField.setText("");
        }

        else if (source == cancelButton)
        {
            monthField.setText("");
            dayField.setText("");
            yearField.setText("");
            setVisible(false);
        }
    }//~public void actionPerfor...

    //Private types
    private JFrame dialogFrame;
    private JPanel mainPanel,
        datePanel,
        buttonPanel;
    private JTextField monthField,
        dayField,
        yearField;
    private JButton findButton,
        clearButton,
        cancelButton;

    //Used as a flag by the Log class to trigger a search
    //for the record in the db.
    private boolean validRequest;
}//~KeyListener...
