/*
* T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Tewl.java
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
import java.awt.print.*;
import java.awt.font.*;
import java.awt.geom.*;



/**
This instantiates the Triathlon Electronic Workout Log application.  It offers five
features:<br>
<br>
&nbsp;&nbsp;&nbsp;- Electronic logging of daily workouts involving running, swimming, and biking;<br>
&nbsp;&nbsp;&nbsp;- Graphing capability for any of the three activities over time from first to last entry;<br>
&nbsp;&nbsp;&nbsp;- Electronic maintenance logging of exercise equipment (i.e. - bike);<br>
&nbsp;&nbsp;&nbsp;- Maintains specifications on exercise equipment and attire used for running, swimming, and biking;<br>
&nbsp;&nbsp;&nbsp;- A distance-time-pace conversion calculator.<br>
<br>	
All information used by the program is stored in a single file named by the user and which
must end with the file extension ".dat."
*/
public class Tewl extends JFrame implements ActionListener,
										ChangeListener
{
	/*
	* Constructor: Tewl
	*/
    public Tewl()
    {
		d = new Debug();
		DEBUG = false;

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        l = new Font("SansSerif",Font.ITALIC,12);
        f = new Font("SansSerif",Font.PLAIN,12);
        m = new Font("SansSerif",Font.PLAIN,11);

        /*
        *Layout specifications 
        */       
        GridBagConstraints gbc = new GridBagConstraints();
        //All components will not grow/shrink with window
        gbc.fill = GridBagConstraints.NONE;
        
        /*
        *Panel initialization
        */
        makeMenuBar();
        makeActionPanel();
        makeAerobicPanel(gbc);
        makeNavigationButtonPanel();
        makeStatusPanel();
        makeBottomPanel();
        makeStrengthPanel(gbc);
        makeStatisticsPanel(gbc);
        makeMaintenancePanel(gbc);
        makeEquipPanel(gbc);
        makeCalculatorPanel(gbc);
        makeTabbedPanel();

        /*
        * Frame 
        */
        contentPane.add(actionPanel,BorderLayout.EAST);
        contentPane.add(tabPanel,BorderLayout.CENTER);
        contentPane.add(bottomPanel,BorderLayout.SOUTH);
        setResizable(true);
        setTitle("T.E.W.L.");

        /*
        * initialize all logs here
		*/
        Workout w = new Workout();
        logbook = new Vector();
//         logbook.add(w);
        mntlogbook = new Vector();
        equiplogbook = new Vector();
        optionslogbook = new Vector();
        
        origlogbook = new Vector();
        origmntlogbook = new Vector();
        origequiplogbook = new Vector();
        origoptionslogbook = new Vector();

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
	            if (DEBUG)
	            {
		            d.print("logbooks identical = " + identical(origlogbook,logbook));
		            d.print("mntlogbooks identical = " + identical(origmntlogbook,mntlogbook));
		            d.print("equiplogbooks identical = " + identical(origequiplogbook,equiplogbook));
		            d.print("optionslogbooks identical = " + identical(origoptionslogbook,optionslogbook));
		            
		            d.print("origlogbook size = " + origlogbook.size());
		            d.print("logbook size = " + logbook.size());
		            d.print("origmntlogbook size = " + origmntlogbook.size());
		            d.print("mntlogbook size = " + mntlogbook.size());
		            d.print("origequiplogbook size = " + origequiplogbook.size());
		            d.print("equiplogbook size = " + equiplogbook.size());
		            d.print("origoptionslogbook size = " + origoptionslogbook.size());
		            d.print("optionslogbook size = " + optionslogbook.size());
            	}
	            
	            
	            if (!identical(origlogbook,logbook) ||
	            !identical(origmntlogbook,mntlogbook) ||
	            !identical(origequiplogbook,equiplogbook) ||
	            !identical(origoptionslogbook,optionslogbook) &&
	            !origlogbook.isEmpty() &&
	            !logbook.isEmpty())
	            {
	                int option = JOptionPane.showConfirmDialog(
	                    contentPane,
	                    "Do you want to save your latest changes?",
	                    "Save Latest Changes?",
	                    JOptionPane.YES_NO_OPTION);
	                if (option == JOptionPane.YES_OPTION)
	                {
	                    boolean dummy = saveCurrentFile(contentPane);
	                }
	            }//~else...
                dispose();
                System.exit(0);
            }
        });

    }//Tewl

	/**
	Fills a JComboBox with numbers beginning with "start" and ending with "last"
	
	@param comboBox JComboBox component to fill
	@param start first integer in list
	@param last last integer in list
	
	@return void
	*/
    public void makeNumberedComboList(JComboBox comboBox, int start, int last)
    {
	    for (int i=start; i<=last; i++)
	    {
		    comboBox.addItem("" + i);
	    }
    }

    /**
    Fills a JComboBox with an array of Strings.
    
    @param comboBox JComboBox component to fill
    @param strArray Array of strings used to fill comboBox
    
    @return void
    */
    public void makeStringComboList(JComboBox comboBox, String [] strArray)
    {
		if (DEBUG)
		{
	    	d.print("makeStringComboList()");
    	}

    	comboBox.addItem("*");
    	for (int j=0; j < strArray.length; j++)
    	{
	    	comboBox.addItem(strArray[j]);
    	}
    }

    /**
    Extracts options from optionslogbook
    
    @param optionslogbook Vector containing list of options
    @param optionName String name for set of options to extract from vector
    
    @return Array of Strings.
    */
    public String [] extractOptions(Vector optionslogbook, String optionName)
    {
		if (DEBUG)
		{
	     	d.print("extractOptions()");
     	}

	    String [] strArray = new String[10];
	    Options option = new Options();

	    if (optionName.equals("route"))
	    {
		    for(int i=0; i<optionslogbook.size()-1; i++)
		    {
			    option = (Options)optionslogbook.elementAt(i);
			    if (option instanceof Route)
			    {
				 	strArray[i] = option.getCode();
			    }
		    }
	    }
	    else if (optionName.equals("exercise"))
	    {
		    for(int i=0; i<optionslogbook.size()-1; i++)
		    {
			    option = (Options)optionslogbook.elementAt(i);
			    if (option instanceof Exercise)
			    {
				 	strArray[i] = option.getCode();
			    }
		    }
	    }
	    return strArray;
    }

    /**
    Constructs menu bar.
    
    @param none
    
    @return void
    */
    public void makeMenuBar()
    {
        menuBar = new JMenuBar();
        databaseMenu = new JMenu("Database");
        databaseMenu.setFont(m);
        openItem = new JMenuItem("Open");
        openItem.setFont(m);
        openItem.setActionCommand("open");
        newItem = new JMenuItem("New");
        newItem.setFont(m);
        newItem.setActionCommand("new");
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem = new JMenuItem("Save");
        saveItem.setFont(m);
        saveItem.setActionCommand("save");
        saveItem.setFont(m);
        saveItem.addActionListener(this);
        saveItem.setEnabled(false);
        saveAsItem = new JMenuItem("Save As");
        saveAsItem.setFont(m);
        saveAsItem.setActionCommand("saveas");
        saveAsItem.addActionListener(this);
        saveAsItem.setEnabled(false);
        backupItem = new JMenuItem("Backup");
        backupItem.setFont(m);
        backupItem.addActionListener(this);
        backupItem.setActionCommand("backup");
        backupItem.setEnabled(false);
        printItem = new JMenuItem("Print");
        printItem.setFont(m);
        printItem.addActionListener(this);
        printItem.setActionCommand("print");
        printItem.setEnabled(false);
        closeItem = new JMenuItem("Close");
        closeItem.setFont(m);
        closeItem.addActionListener(this);
        closeItem.setActionCommand("close");
        closeItem.setEnabled(false);
        exitItem = new JMenuItem("Exit");
        exitItem.setFont(m);
        exitItem.setActionCommand("exit");
        exitItem.addActionListener(this);
        
        databaseMenu.add(new JMenuItem(""));
        databaseMenu.add(openItem);
        databaseMenu.add(newItem);
        databaseMenu.add(printItem);
        databaseMenu.add(saveItem);
        databaseMenu.add(saveAsItem);
        databaseMenu.add(backupItem);
        databaseMenu.addSeparator();
        databaseMenu.add(closeItem);
        databaseMenu.add(exitItem);

        editMenu = new JMenu("Edit");
        editMenu.setFont(m);
        removeallItem = new JMenuItem("Remove All Records");
        removeallItem.setFont(m);
        removeallItem.setActionCommand("remove all");
        removeallItem.addActionListener(this);
        removeallItem.setEnabled(false);

        optionsItem = new JMenuItem("Options");
        optionsItem.setFont(m);
        optionsItem.setActionCommand("options");
        optionsItem.addActionListener(this);
        optionsItem.setEnabled(false);
        
        editMenu.add(new JMenuItem(""));
        editMenu.add(removeallItem);
        editMenu.addSeparator();
        editMenu.add(optionsItem);

        helpMenu = new JMenu("Help");
        helpMenu.setFont(m);
        howToItem = new JMenuItem("How To ...");
        howToItem.setActionCommand("howto");
        howToItem.addActionListener(this);
        howToItem.setFont(m);
        howToItem.setEnabled(true);

        aboutItem = new JMenuItem("About T.E.W.L.");
        aboutItem.setActionCommand("about");
        aboutItem.addActionListener(this);
        aboutItem.setFont(m);
        aboutItem.setEnabled(true);
        
        helpMenu.add(new JMenuItem(""));
        helpMenu.add(howToItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

        menuBar.add(databaseMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /**
    Constructs action panel.
    
    @param none
    
    @return void
    */
    public void makeActionPanel()
    {
        /*
        * Buttons
        */
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(7,1));

        findButton = new JButton("Find");
        findButton.setFont(f);
        findButton.addActionListener(this);
        findButton.setActionCommand("find");
        actionPanel.add(findButton);

        newButton = new JButton("New");
        newButton.setFont(f);
        newButton.addActionListener(this);
        newButton.setActionCommand("new");
        actionPanel.add(newButton);

        deleteButton = new JButton("Delete");
        deleteButton.setFont(f);
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("delete");
        actionPanel.add(deleteButton);

        editButton = new JButton("Edit");
        editButton.setFont(f);
        editButton.addActionListener(this);
        editButton.setActionCommand("edit");
        actionPanel.add(editButton);

        saveButton = new JButton("Save");
        saveButton.setFont(f);
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        actionPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(f);
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand("cancel");
        actionPanel.add(cancelButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(f);
        clearButton.addActionListener(this);
        clearButton.setActionCommand("clear");
        actionPanel.add(clearButton);

        Component [] nab =
        {
            findButton,newButton,deleteButton,
                editButton,saveButton,cancelButton,clearButton
        };

        componentsEnabled(nab,false);
    }

    /**
    Returns today's date.
    
    @param none
    
    @return Date in string format
    */
    public String getTodaysDate()
    {
        today = new GregorianCalendar();
        int calmonth = today.get(Calendar.MONTH);
        int caldate = today.get(Calendar.DATE);
        int calyear = today.get(Calendar.YEAR);
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        String weekDay = "";
        String monthStr = "";

        switch (dayOfWeek)
        {
            case 1 : weekDay = "Sunday"; break;
            case 2 : weekDay = "Monday"; break;
            case 3 : weekDay = "Tuesday"; break;
            case 4 : weekDay = "Wednesday"; break;
            case 5 : weekDay = "Thursday"; break;
            case 6 : weekDay = "Friday"; break;
            case 7 : weekDay = "Saturday"; break;
        }//~switch (dayOfWeek)...

        switch (calmonth)
        {
            case 0 : monthStr = "January"; break;
            case 1 : monthStr = "February"; break;
            case 2 : monthStr = "March"; break;
            case 3 : monthStr = "April"; break;
            case 4 : monthStr = "May"; break;
            case 5 : monthStr = "June"; break;
            case 6 : monthStr = "July"; break;
            case 7 : monthStr = "August"; break;
            case 8 : monthStr = "September"; break;
            case 9 : monthStr = "October"; break;
            case 10 : monthStr = "November"; break;
            case 11 : monthStr = "December"; break;
        }//~switch (calmonth)...
        return (weekDay + " - " + monthStr
	        + " " + caldate + ", " + calyear);
    }//~public String getTodaysD...

    /**
    Constructs aerobic panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeAerobicPanel(GridBagConstraints gbc)
    {
//
//         (column,row)
//
// 			---------------------------
// 			|        |        |       |
// 			|  1,1   |  2,1   |  3,1  |
// 			|        |        |       |
// 			---------------------------
// 			|        |        |       |
// 			|  1,2   |  2,2   |  3,2  |
// 			|        |        |       |
// 			---------------------------
// 			|        |        |       |
// 			|  1,3   |  2,3   |  3,3  |
// 			|        |        |       |
// 			---------------------------

	    String month = "";
	    String day = "";
	    String year = "";
	    String summary = "";
        mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //============= Date =============//

        dateTempPanel = new JPanel(new GridBagLayout());

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.EAST;
        dateLabel = new JLabel("Date: ");
        dateLabel.setFont(f);
        add(dateTempPanel,dateLabel,gbc,1,1,1,1);

        gbc.insets = new Insets(5,1,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        monthField = new JTextField(3);
        monthField.setName("monthField");
        monthField.setHorizontalAlignment
        (JTextField.RIGHT);
        monthField.setText(month);
        monthField.setToolTipText("Month - \"MM\"");
        add(dateTempPanel,monthField,gbc,2,1,1,1);

        gbc.insets = new Insets(5,1,2,2);
        gbc.anchor = GridBagConstraints.EAST;
        dayField = new JTextField(3);
        dayField.setHorizontalAlignment
        (JTextField.RIGHT);
        dayField.setText(day);
        dayField.setToolTipText("Day - \"DD\"");
        add(dateTempPanel,dayField,gbc,3,1,1,1);

        gbc.insets = new Insets(5,1,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        yearField = new JTextField(4);
        yearField.setHorizontalAlignment
        (JTextField.RIGHT);
        yearField.setText(year);
        yearField.setToolTipText("Year - \"YYYY\"");
        add(dateTempPanel,yearField,gbc,4,1,1,1);

        //============= Temperature =============//

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.EAST;
        tempLabel = new JLabel("Temp: ");
        tempLabel.setFont(f);
        add(dateTempPanel,tempLabel,gbc,5,1,1,1);

        gbc.insets = new Insets(5,2,2,2);
        gbc.anchor = GridBagConstraints.WEST;
		tempComboBox = new JComboBox();
		makeNumberedComboList(tempComboBox,0,120);
		tempComboBox.setEnabled(false);
		add(dateTempPanel,tempComboBox,gbc,6,1,1,1);

        //============= Weather =============//

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.EAST;
        weatherLabel = new JLabel("Weather: ");
        weatherLabel.setFont(f);
		add(dateTempPanel,weatherLabel,gbc,7,1,1,1);

        gbc.insets = new Insets(5,2,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        String [] weatherType = {"hot","cold","mild","rainy","rainyhot"};
        weatherComboBox = new JComboBox(weatherType);
        weatherComboBox.setEnabled(false);

		add(dateTempPanel,weatherComboBox,gbc,8,1,1,1);

        //============= Pulse =============//

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.EAST;
        pulseLabel = new JLabel("Resting HR: ");
        pulseLabel.setFont(f);
		add(dateTempPanel,pulseLabel,gbc,9,1,1,1);

        gbc.insets = new Insets(5,2,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        pulseComboBox = new JComboBox();
        makeNumberedComboList(pulseComboBox,0,120);
        pulseComboBox.setEnabled(false);
		add(dateTempPanel,pulseComboBox,gbc,10,1,1,1);

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.WEST;

		add(mainPanel,dateTempPanel,gbc,1,1,10,1);

		//============= Events =============//

        eventPanel = new JPanel(new GridBagLayout());

        eventTable = new EventTable(new Workout());
        add(eventPanel,eventTable,gbc,1,1,10,4);
		
        gbc.insets = new Insets(20,10,2,2);
        gbc.anchor = GridBagConstraints.CENTER;
        add(mainPanel,eventPanel,gbc,1,3,10,4);

        //============= Summary =============//

        summaryPanel = new JPanel(new GridBagLayout());

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        summaryLabel = new JLabel("Summary: ");
        summaryLabel.setFont(f);
        add(summaryPanel,summaryLabel,gbc,1,1,1,1);

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        summaryTextArea = new JTextArea(4,45);
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setWrapStyleWord(true);
        summaryTextArea.setToolTipText("Summarize workout.");
        summaryTextArea.setEditable(false);
        scrollPane = new JScrollPane
        	(summaryTextArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        summaryTextArea.insert(summary,0);
        add(summaryPanel,scrollPane,gbc,2,1,7,4);

        //============= Weight =============//

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.EAST;
        weightLabel = new JLabel("Weight: ");
        weightLabel.setFont(f);
		add(summaryPanel,weightLabel,gbc,9,1,1,1);

        gbc.insets = new Insets(5,2,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        weightComboBox = new JComboBox();
        makeNumberedComboList(weightComboBox,0,400);
        weightComboBox.setEnabled(false);
		add(summaryPanel,weightComboBox,gbc,10,1,2,1);

        gbc.insets = new Insets(20,10,2,2);
        gbc.anchor = GridBagConstraints.WEST;

        add(mainPanel,summaryPanel,gbc,1,10,10,5);

        //disable all textfields
        JTextField [] tf =
        {
            monthField,
            dayField,
            yearField,
            statusField,
            puField,
            suField
        };//~JTextField [] tf =...

        componentsEditable(tf,false);

    }

    /**
    Constructs Strength Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeStrengthPanel(GridBagConstraints gbc)
    {
        stationaryPanel = new JPanel(new GridBagLayout());
		stationaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //============= Strength Training =============//

        gbc.anchor = GridBagConstraints.EAST;
        puLabel = new JLabel("Push-ups: ");
        puLabel.setFont(f);
        add(stationaryPanel,puLabel,gbc,1,4,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);
        puField = new JTextField(3);
        puField.setToolTipText("Pushups - \"nnn\"");
        puField.setHorizontalAlignment
        (JTextField.RIGHT);
        puField.setEditable(false);
        add(stationaryPanel,puField,gbc,2,4,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2,2,2,2);
        suLabel = new JLabel("Sit-ups: ");
        suLabel.setFont(f);
        add(stationaryPanel,suLabel,gbc,1,5,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);
        suField = new JTextField(3);
        suField.setToolTipText("Situps - \"nnn\"");
        suField.setHorizontalAlignment
        (JTextField.RIGHT);
        suField.setEditable(false);
        add(stationaryPanel,suField,gbc,2,5,1,1);
    }//~public void makeStrength...

    /**
    Constructs Maintenance Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeMaintenancePanel(GridBagConstraints gbc)
    {
        maintenancePanel = new JPanel(new GridBagLayout());
		maintenancePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2,2,2,2);
        maintDateLabel = new JLabel("Maintenance Date: ");
        maintDateLabel.setFont(f);
        add(maintenancePanel,maintDateLabel,gbc,1,1,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);
        maintDateField = new JTextField(10);
        maintDateField.setHorizontalAlignment
        (JTextField.RIGHT);
        maintDateField.setToolTipText("Date - \"MM/DD/YYYY\"");
        maintDateField.setEditable(false);
        add(maintenancePanel,maintDateField,gbc,2,1,1,1);

        gbc.insets = new Insets(2,2,2,5);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        maintWorkLabel = new JLabel("Work Completed: ");
        maintWorkLabel.setFont(f);
        add(maintenancePanel,maintWorkLabel,gbc,1,2,1,1);

        gbc.insets = new Insets(10,2,10,2);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        maintWorkTextArea = new JTextArea(4,30);
        maintWorkTextArea.setLineWrap(true);
        maintWorkTextArea.setWrapStyleWord(true);
        maintWorkTextArea.setToolTipText("Describe work completed");
        scrollPane = new JScrollPane
        (maintWorkTextArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(maintenancePanel,scrollPane,gbc,2,2,6,4);

        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.CENTER;
        maintButtonPanel = new JPanel();
        maintOpenButton= new JButton("Open Log");
        maintOpenButton.setFont(f);
        maintOpenButton.setActionCommand("openlog");
        maintOpenButton.addActionListener(this);
        maintButtonPanel.add(maintOpenButton);
        add(maintenancePanel,maintButtonPanel,gbc,1,8,8,1);
    }//~public void makeMaintena...

    /**
    Constructs Navigation Button Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeNavigationButtonPanel()
    {
        navPanel = new JPanel();
        ImageIcon beginarrow = new ImageIcon("begin.gif");
        ImageIcon prevarrow = new ImageIcon("prev.gif");
        ImageIcon nextarrow = new ImageIcon("next.gif");
        ImageIcon endarrow = new ImageIcon("end.gif");

        beginButton = new JButton(beginarrow);
        beginButton.addActionListener(this);
        beginButton.setActionCommand("beginning");
        beginButton.setToolTipText("First Record");
        navPanel.add(beginButton);

        prevButton= new JButton(prevarrow);
        prevButton.addActionListener(this);
        prevButton.setActionCommand("previous");
        prevButton.setToolTipText("Previous Record");
        navPanel.add(prevButton);

        nextButton = new JButton(nextarrow);
        nextButton.addActionListener(this);
        nextButton.setActionCommand("next");
        nextButton.setToolTipText("Next Record");
        navPanel.add(nextButton);

        endButton = new JButton(endarrow);
        endButton.addActionListener(this);
        endButton.setActionCommand("end");
        endButton.setToolTipText("Last Record");
        navPanel.add(endButton);

        //enable all navigation and action buttons
        JButton [] nab =
        {
            beginButton,prevButton,
                nextButton,endButton
        };

        componentsEnabled(nab,false);
    }

    /**
    Constructs Status Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeStatusPanel()
    {
        datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout());
        String todaysDate = getTodaysDate();
        todaysDateField = new JTextField(10);
        todaysDateField.setHorizontalAlignment
        (JTextField.CENTER);
        todaysDateField.setEditable(false);
        todaysDateField.setBackground(Color.lightGray);
        todaysDateField.setFont(f);
        todaysDateField.setText(todaysDate);

        datePanel.add(todaysDateField);

        statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusField = new JTextField(10);
        statusField.setEditable(false);
        statusField.setBackground(Color.lightGray);
        statusField.setHorizontalAlignment
        (JTextField.CENTER);
        statusPanel.add(statusField);

        mainStatusPanel = Box.createHorizontalBox();
        mainStatusPanel.add(datePanel);
        mainStatusPanel.add(statusPanel);
    }

    /**
    Constructs Bottom Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeBottomPanel()
    {
        bottomPanel = new JPanel(new BorderLayout());
        Border etchedBorder = BorderFactory.createEtchedBorder();
        bottomPanel.setBorder(etchedBorder);
        bottomPanel.add(navPanel,BorderLayout.CENTER);
        bottomPanel.add(mainStatusPanel,BorderLayout.SOUTH);

    }//~public void makeBottomPa...

    /**
    Constructs Statistics Panel.
    
    @param gbc GridBagConstraints to establish layout.
    @return void
    */
    public void makeStatisticsPanel(GridBagConstraints gbc)
    {
        statsPanel = new JPanel(new BorderLayout());
		statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //         Border etchedBorder = BorderFactory.createEtchedBorder();
//         statsPanel.setBorder(etchedBorder);

        statsEventPanel = new JPanel();
        gbc.insets = new Insets(10,2,2,2);
        statsEventLabel = new JLabel("Select an Event: ");
        statsEventLabel.setFont(f);
        gbc.anchor = GridBagConstraints.WEST;
        statsEventGroup = new ButtonGroup();

        statsEventPanel.add(statsEventLabel);

        statsRunRadio = new JRadioButton("Run");
        statsRunRadio.setFont(f);
        statsRunRadio.addActionListener(this);
        statsRunRadio.setActionCommand("run");
        statsRunRadio.setSelected(true);
        statsEventGroup.add(statsRunRadio);
        statsEventPanel.add(statsRunRadio);

        statsBikeRadio = new JRadioButton("Bike");
        statsBikeRadio.setFont(f);
        statsBikeRadio.addActionListener(this);
        statsBikeRadio.setActionCommand("bike");
        statsBikeRadio.setSelected(false);
        statsEventGroup.add(statsBikeRadio);
        statsEventPanel.add(statsBikeRadio);

        statsSwimRadio = new JRadioButton("Swim");
        statsSwimRadio.setFont(f);
        statsSwimRadio.addActionListener(this);
        statsSwimRadio.setActionCommand("swim");
        statsSwimRadio.setSelected(false);
        statsEventGroup.add(statsSwimRadio);
        statsEventPanel.add(statsSwimRadio);

        statsPanel.add(statsEventPanel,BorderLayout.NORTH);

        statsFieldPanel = new JPanel(new GridBagLayout());

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2,2,2,2);
        totalMilesToDateLabel = new JLabel("Total Miles: ");
        totalMilesToDateLabel.setFont(f);
        add(statsFieldPanel,totalMilesToDateLabel,gbc,1,2,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);
        totalMilesToDateField = new JTextField(10);
        totalMilesToDateField.setHorizontalAlignment
        (JTextField.RIGHT);
        totalMilesToDateField.setEditable(false);
        add(statsFieldPanel,totalMilesToDateField,gbc,2,2,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10,2,2,2);
        avgMilesLabel = new JLabel("Average Miles Per Day: ");
        avgMilesLabel.setFont(f);
        add(statsFieldPanel,avgMilesLabel,gbc,1,3,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,2,2,2);
        avgMilesField = new JTextField(10);
        avgMilesField.setHorizontalAlignment
        (JTextField.RIGHT);
        avgMilesField.setEditable(false);
        add(statsFieldPanel,avgMilesField,gbc,2,3,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10,2,2,2);
        statsAvgSpeedLabel = new JLabel("Average Speed Per Workout: ");
        statsAvgSpeedLabel.setFont(f);
        add(statsFieldPanel,statsAvgSpeedLabel,gbc,1,4,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,2,2,2);
        statsAvgSpeedField = new JTextField(10);
        statsAvgSpeedField.setHorizontalAlignment
        (JTextField.RIGHT);
        statsAvgSpeedField.setEditable(false);
        add(statsFieldPanel,statsAvgSpeedField,gbc,2,4,1,1);

        statsAvgPaceLabel = new JLabel("mph");
        add(statsFieldPanel,statsAvgPaceLabel,gbc,3,4,1,1);

        statsPanel.add(statsFieldPanel,BorderLayout.CENTER);

        statsGraphPanel = new JPanel(new GridBagLayout());

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10,2,2,2);
        statsLeftAxisOptionLabel = new JLabel("Display left side as: ");
        add(statsGraphPanel,statsLeftAxisOptionLabel,gbc,1,5,1,1);

        gbc.insets = new Insets(10,2,2,2);
        gbc.anchor = GridBagConstraints.WEST;
        statsLeftAxisOptionComboBox = new JComboBox();
        statsLeftAxisOptionComboBox.addActionListener(this);
        add(statsGraphPanel,statsLeftAxisOptionComboBox,gbc,2,5,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10,2,2,2);
        statsRightAxisOptionLabel = new JLabel("Display right side as: ");
        add(statsGraphPanel,statsRightAxisOptionLabel,gbc,1,6,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,2,2,2);
        statsRightAxisOptionComboBox = new JComboBox();
        statsRightAxisOptionComboBox.addActionListener(this);
        add(statsGraphPanel,statsRightAxisOptionComboBox,gbc,2,6,1,1);

        statsGraphButton = new JButton("Show Graph");
        statsGraphButton.setActionCommand("showgraph");
        statsGraphButton.addActionListener(this);
        add(statsGraphPanel,statsGraphButton,gbc,2,7,1,1);

        statsPanel.add(statsGraphPanel,BorderLayout.SOUTH);

    }

    /**
    Constructs Equipment Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeEquipPanel(GridBagConstraints gbc)
    {
        equipEventGroup = new ButtonGroup();

        equipEventPanel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);

        equipSelectOption = new JLabel("Select an option: ");
        equipSelectOption.setFont(f);
        add(equipEventPanel,equipSelectOption,gbc,1,1,1,1);

        equipRunRadio = new JRadioButton("Run");
        equipRunRadio.setFont(f);
        equipRunRadio.setActionCommand("equiprun");
        equipRunRadio.addActionListener(this);
        equipEventGroup.add(equipRunRadio);
        add(equipEventPanel,equipRunRadio,gbc,2,1,1,1);

        equipBikeRadio = new JRadioButton("Bike");
        equipBikeRadio.setFont(f);
        equipBikeRadio.setActionCommand("equipbike");
        equipBikeRadio.addActionListener(this);
        equipEventGroup.add(equipBikeRadio);
        add(equipEventPanel,equipBikeRadio,gbc,3,1,1,1);

        equipSwimRadio = new JRadioButton("Swim");
        equipSwimRadio.setFont(f);
        equipSwimRadio.setActionCommand("equipswim");
        equipSwimRadio.addActionListener(this);
        equipEventGroup.add(equipSwimRadio);
        add(equipEventPanel,equipSwimRadio,gbc,4,1,1,1);

        equipWalkRadio = new JRadioButton("Walk");
        equipWalkRadio.setFont(f);
        equipWalkRadio.setActionCommand("equipwalk");
        equipWalkRadio.addActionListener(this);
        equipEventGroup.add(equipWalkRadio);
        add(equipEventPanel,equipWalkRadio,gbc,5,1,1,1);

        equipSpecPanel = new JPanel(new GridBagLayout());

        equipOuterEventPanel = new JPanel();
        equipOuterEventPanel.setLayout(new BorderLayout());
        equipOuterEventPanel.add(equipEventPanel,BorderLayout.CENTER);

        equipPanel = new JPanel();
        equipPanel.setLayout(new BorderLayout());
        equipPanel.add(equipEventPanel,BorderLayout.NORTH);
        equipPanel.add(equipSpecPanel,BorderLayout.CENTER);

		equipPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }//~public void makeEquipPan...

    /**
    Constructs Calculator Panel.
    
    @param gbc GridBagConstraints to establish layout
    
    @return void
    */
    public void makeCalculatorPanel(GridBagConstraints gbc)
    {
        calcPanel = new JPanel(new GridBagLayout());
		calcPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
        calcSelectionGroup = new ButtonGroup();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);

        //---------------  row 1 ----------------//

        calcSelectionLabel = new JLabel("Step 1 - Select: ");
        calcSelectionLabel.setFont(f);
        add(calcPanel,calcSelectionLabel,gbc,1,1,1,1);

        gbc.anchor = GridBagConstraints.CENTER;
        calcMilesCheck = new JCheckBox("Miles");
        calcMilesCheck.setFont(f);
        calcMilesCheck.setActionCommand("calcmilescheck");
        calcMilesCheck.addActionListener(this);
        calcSelectionGroup.add(calcMilesCheck);
        add(calcPanel,calcMilesCheck,gbc,2,1,1,1);

        calcKilosCheck = new JCheckBox("Kilos");
        calcKilosCheck.setFont(f);
        calcKilosCheck.setActionCommand("calckiloscheck");
        calcKilosCheck.addActionListener(this);
        calcSelectionGroup.add(calcKilosCheck);
        add(calcPanel,calcKilosCheck,gbc,3,1,1,1);

        calcResultGroup = new ButtonGroup();

        gbc.anchor = GridBagConstraints.WEST;
        calcResultLabel = new JLabel("Step 2 - Calculate: ");
        calcResultLabel.setFont(f);
        calcResultLabel.setEnabled(false);
        add(calcPanel,calcResultLabel,gbc,1,2,1,1);

        //---------------  row 2 ----------------//

        gbc.anchor = GridBagConstraints.CENTER;
        calcTimeRadio = new JRadioButton("Time");
        calcTimeRadio.setFont(f);
        calcTimeRadio.setActionCommand("calctimeradio");
        calcTimeRadio.addActionListener(this);
        calcTimeRadio.setEnabled(false);
        calcResultGroup.add(calcTimeRadio);
        add(calcPanel,calcTimeRadio,gbc,2,2,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcDistanceRadio = new JRadioButton("Distance");
        calcDistanceRadio.setFont(f);
        calcDistanceRadio.setActionCommand("calcdistanceradio");
        calcDistanceRadio.setEnabled(false);
        calcDistanceRadio.addActionListener(this);
        calcResultGroup.add(calcDistanceRadio);
        add(calcPanel,calcDistanceRadio,gbc,3,2,1,1);

        gbc.anchor = GridBagConstraints.WEST;
        calcPaceRadio = new JRadioButton("Pace");
        calcPaceRadio.setFont(f);
        calcPaceRadio.setActionCommand("calcpaceradio");
        calcPaceRadio.addActionListener(this);
        calcPaceRadio.setEnabled(false);
        calcResultGroup.add(calcPaceRadio);
        add(calcPanel,calcPaceRadio,gbc,5,2,1,1);

        //---------------  row 3 ----------------//

        gbc.anchor = GridBagConstraints.WEST;
        calcDataLabel = new JLabel("Step 3 - Enter values: ");
        calcDataLabel.setFont(f);
        calcDataLabel.setEnabled(false);
        add(calcPanel,calcDataLabel,gbc,1,3,1,1);

        calcPaceMileRadioGroup = new ButtonGroup();
        calcPaceKmRadioGroup = new ButtonGroup();

        //---------------  row 4 ----------------//

        gbc.anchor = GridBagConstraints.EAST;
        calcDistanceMilesLabel = new JLabel("Distance (mi):");
        calcDistanceMilesLabel.setFont(f);
        calcDistanceMilesLabel.setEnabled(false);
        add(calcPanel,calcDistanceMilesLabel,gbc,1,4,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcDistanceMilesField = new JTextField(6);
        calcDistanceMilesField.setToolTipText("Miles - \"nnn.nn\"");
        calcDistanceMilesField.setEnabled(false);
        calcDistanceMilesField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcDistanceMilesField,gbc,2,4,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMphLabel = new JLabel("Miles/Hr:");
        calcPaceMphLabel.setFont(f);
        calcPaceMphLabel.setEnabled(false);
        add(calcPanel,calcPaceMphLabel,gbc,3,4,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMphRadio = new JRadioButton();
        calcPaceMphRadio.setFont(f);
        calcPaceMphRadio.setActionCommand("calcpacemphradio");
        calcPaceMphRadio.addActionListener(this);
        calcPaceMphRadio.setEnabled(false);
        calcPaceMileRadioGroup.add(calcPaceMphRadio);
        add(calcPanel,calcPaceMphRadio,gbc,4,4,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMphField = new JTextField(6);
        calcPaceMphField.setToolTipText("Mph - \"nnn.nn\"");
        calcPaceMphField.setEnabled(false);
        calcPaceMphField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcPaceMphField,gbc,5,4,1,1);

        //---------------  row 5 ----------------//

        gbc.anchor = GridBagConstraints.EAST;
        calcMinPerMileLabel = new JLabel("Time/Mile:");
        calcMinPerMileLabel.setFont(f);
        calcMinPerMileLabel.setEnabled(false);
        add(calcPanel,calcMinPerMileLabel,gbc,3,5,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinPerMileRadio = new JRadioButton();
        calcPaceMinPerMileRadio.setFont(f);
        calcPaceMinPerMileRadio.setActionCommand("calcpaceminpermileradio");
        calcPaceMinPerMileRadio.addActionListener(this);
        calcPaceMinPerMileRadio.setEnabled(false);
        calcPaceMileRadioGroup.add(calcPaceMinPerMileRadio);
        add(calcPanel,calcPaceMinPerMileRadio,gbc,4,5,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinPerMileField = new JTextField(6);
        calcPaceMinPerMileField.setToolTipText("Time/Mile - \"hh:mm:ss\"");
        calcPaceMinPerMileField.setEnabled(false);
        calcPaceMinPerMileField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcPaceMinPerMileField,gbc,5,5,1,1);

        //---------------  row 6 ----------------//

        gbc.anchor = GridBagConstraints.EAST;
        calcDistanceKiloLabel = new JLabel("Distance (km):");
        calcDistanceKiloLabel.setFont(f);
        calcDistanceKiloLabel.setEnabled(false);
        add(calcPanel,calcDistanceKiloLabel,gbc,1,6,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcDistanceKiloField = new JTextField(6);
        calcDistanceKiloField.setToolTipText("Kilometers - \"nnn.nn\"");
        calcDistanceKiloField.setEnabled(false);
        calcDistanceKiloField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcDistanceKiloField,gbc,2,6,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcMinPer100ydsLabel = new JLabel("Mins/100yds:");
        calcMinPer100ydsLabel.setFont(f);
        calcMinPer100ydsLabel.setEnabled(false);
        add(calcPanel,calcMinPer100ydsLabel,gbc,3,6,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinsPer100ydsRadio = new JRadioButton();
        calcPaceMinsPer100ydsRadio.setFont(f);
        calcPaceMinsPer100ydsRadio.setActionCommand("calcpaceminsper100ydsradio");
        calcPaceMinsPer100ydsRadio.addActionListener(this);
        calcPaceMinsPer100ydsRadio.setEnabled(false);
        calcPaceMileRadioGroup.add(calcPaceMinsPer100ydsRadio);
        add(calcPanel,calcPaceMinsPer100ydsRadio,gbc,4,6,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinPer100ydsField = new JTextField(6);
        calcPaceMinPer100ydsField.setToolTipText("Time/100yds - \"hh:mm:ss\"");
        calcPaceMinPer100ydsField.setEnabled(false);
        calcPaceMinPer100ydsField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcPaceMinPer100ydsField,gbc,5,6,1,1);

        //---------------  row 7 ----------------//

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceKphLabel = new JLabel("Kms/Hr:");
        calcPaceKphLabel.setFont(f);
        calcPaceKphLabel.setEnabled(false);
        add(calcPanel,calcPaceKphLabel,gbc,3,7,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceKphRadio = new JRadioButton();
        calcPaceKphRadio.setFont(f);
        calcPaceKphRadio.setActionCommand("calcpacekphradio");
        calcPaceKphRadio.addActionListener(this);
        calcPaceKphRadio.setEnabled(false);
        calcPaceKmRadioGroup.add(calcPaceKphRadio);
        add(calcPanel,calcPaceKphRadio,gbc,4,7,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceKphField = new JTextField(6);
        calcPaceKphField.setToolTipText("Kph - \"nnn.nn\"");
        calcPaceKphField.setEnabled(false);
        calcPaceKphField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcPaceKphField,gbc,5,7,1,1);

        //---------------  row 8 ----------------//

        gbc.anchor = GridBagConstraints.EAST;
        calcTimeLabel = new JLabel("Time (hrs:mins:secs):");
        calcTimeLabel.setFont(f);
        calcTimeLabel.setEnabled(false);
        add(calcPanel,calcTimeLabel,gbc,1,8,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcTimeField = new JTextField(6);
        calcTimeField.setToolTipText("Enter \"hh:mm:ss\"");
        calcTimeField.setEnabled(false);
        calcTimeField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcTimeField,gbc,2,8,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcMinPerKmLabel = new JLabel("Time/Km:");
        calcMinPerKmLabel.setFont(f);
        calcMinPerKmLabel.setEnabled(false);
        add(calcPanel,calcMinPerKmLabel,gbc,3,8,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinPerKmRadio = new JRadioButton();
        calcPaceMinPerKmRadio.setFont(f);
        calcPaceMinPerKmRadio.setActionCommand("calcpaceminperkmradio");
        calcPaceMinPerKmRadio.addActionListener(this);
        calcPaceMinPerKmRadio.setEnabled(false);
        calcPaceKmRadioGroup.add(calcPaceMinPerKmRadio);
        add(calcPanel,calcPaceMinPerKmRadio,gbc,4,8,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinPerKmField = new JTextField(6);
        calcPaceMinPerKmField.setToolTipText("Time/Km - \"hh:mm:ss\"");
        calcPaceMinPerKmField.setEnabled(false);
        calcPaceMinPerKmField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcPaceMinPerKmField,gbc,5,8,1,1);

        //---------------  row 9 ----------------//

        gbc.anchor = GridBagConstraints.EAST;
        calcMinPer100mLabel = new JLabel("Mins/100m:");
        calcMinPer100mLabel.setFont(f);
        calcMinPer100mLabel.setEnabled(false);
        add(calcPanel,calcMinPer100mLabel,gbc,3,9,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinsPer100mRadio = new JRadioButton();
        calcPaceMinsPer100mRadio.setFont(f);
        calcPaceMinsPer100mRadio.setActionCommand("calcpaceminsper100mradio");
        calcPaceMinsPer100mRadio.addActionListener(this);
        calcPaceMinsPer100mRadio.setEnabled(false);
        calcPaceKmRadioGroup.add(calcPaceMinsPer100mRadio);
        add(calcPanel,calcPaceMinsPer100mRadio,gbc,4,9,1,1);

        gbc.anchor = GridBagConstraints.EAST;
        calcPaceMinPer100mField = new JTextField(6);
        calcPaceMinPer100mField.setToolTipText("Time/100m - \"hh:mm:ss\"");
        calcPaceMinPer100mField.setEnabled(false);
        calcPaceMinPer100mField.setHorizontalAlignment
        (JTextField.RIGHT);
        add(calcPanel,calcPaceMinPer100mField,gbc,5,9,1,1);

        calcButtonPanel = new JPanel();

        calcButton = new JButton("Calculate");
        calcButton.setFont(f);
        calcButton.setActionCommand("calcbutton");
        calcButton.addActionListener(this);
        calcButtonPanel.add(calcButton);

        calcResetButton = new JButton("Reset");
        calcResetButton.setFont(f);
        calcResetButton.setActionCommand("calcresetbutton");
        calcResetButton.addActionListener(this);
        calcButtonPanel.add(calcResetButton);

        add(calcPanel,calcButtonPanel,gbc,1,10,3,1);

    }

    /**
    Constructs tabbed pane.
    
    @param none
    
    @return void
    */
    public void makeTabbedPanel()
    {
        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
        tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());

        tabbedPane.setFont(m);
        tabbedPane.addTab("Aerobic",mainPanel);
        tabbedPane.addTab("Strength",stationaryPanel);
        tabbedPane.addTab("Statistics",statsPanel);
        tabbedPane.addTab("Maintenance",maintenancePanel);
        tabbedPane.addTab("Equipment",equipPanel);
        tabbedPane.addTab("Calculator",calcPanel);

        tabbedPane.setEnabledAt(2,false);
        tabbedPane.setEnabledAt(3,false);
        tabbedPane.setEnabledAt(4,false);

        tabPanel.add(tabbedPane,BorderLayout.CENTER);
    }//~public void makeTabbedPa...

    /**
    Constructs About Dialog.
    
    @param none
    
    @return void
    */
    public void showAboutDialog()
    {
        JOptionPane about = new JOptionPane();
        about.setFont(f);
        about.showConfirmDialog
        (this,
            "T.E.W.L.\n" +
            "Triathlon Electronic Workout Log\n" +
            "Copyright 2002 by Clif Hudson\n" +
            "Version: 1.0\n" +
            "Date: September 4, 2002",
            "About T.E.W.L.",JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null);
    }//~public void showAboutDia...

    /**
    Adds components to panel.
    
    @param c Panel component to add to panel
    @param gbc GridBagConstraints to establish layout
    
    @result void
    */
    public void add(JPanel p, Component c, GridBagConstraints gbc,
        int x, int y, int w, int h)
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        p.add(c,gbc);
    }

	/**
	Compares date from query to each one in list and, if
    applicable, returns the index of the matching
    record.  Otherwise, it returns -1, which means the record
    was not found.
    
    @param s An array of Strings representing the Month, Day, and Year
    
    @return The index of the record containing the date
    */
    public int recordFound(String [] s)
    {
	    if (DEBUG)
	    {
		    d.print("recordFound");
	    }

	    int currentIndex = 1;

        int m = Integer.parseInt(s[0]);
        int d = Integer.parseInt(s[1]);
        int y = Integer.parseInt(s[2]);

        ListIterator iter = logbook.listIterator();

        while (iter.hasNext())
        {
            currentIndex = iter.nextIndex();
            Workout w = (Workout)iter.next();
            if ((m == w.getDate().getMonth()) &&
            	(d == w.getDate().getDay()) &&
            	(y == w.getDate().getYear()))
            {
                break;
            }
            else
            	currentIndex = -1;
        }//~while (iter.hasNext())...

        return currentIndex;
    }//~public int recordFound(S...

    /**
    Resets all JComboBox components.
    
    @param none
    
    @return void
    */
    public void resetComboBoxes()
    {
	    //remove all items in each combobox
	    tempComboBox.removeAllItems();
	    pulseComboBox.removeAllItems();
	    weightComboBox.removeAllItems();

	    //fill in numbered comboboxes
	    makeNumberedComboList(tempComboBox,0,120);
	    makeNumberedComboList(pulseComboBox,0,120);
	    makeNumberedComboList(weightComboBox,0,400);

	    //fill in weather combobox
        String [] weatherType = {"hot","cold","mild","rainy","rainyhot"};
        weatherComboBox.addItem("hot");
        weatherComboBox.addItem("cold");
        weatherComboBox.addItem("mild");
        weatherComboBox.addItem("rainy");
        weatherComboBox.addItem("rainyhot");
    }

	/**
	Resets all text components to blank and radio buttons to bike (event) and mild (weather).
	
	@param none
	
	@return void
	*/
    public void resetFields()
    {
	    if (DEBUG)
	    {
		    d.print("resetFields");
	    }

	    JPanel [] panel = {mainPanel, dateTempPanel, stationaryPanel,summaryPanel};
	    Component c = null;

	    for (int i=0; i<panel.length; i++)
	    {
		    Component [] compArray = panel[i].getComponents();
	        for(int j=0; j<compArray.length; j++)
	        {
	            c = compArray[j];
	            if (c instanceof JTextField)
	            {
	                ((JTextField)c).setText("");
	                ((JTextField)c).setEditable(true);
	            }
	            else if(c instanceof JComboBox)
	            {
		            ((JComboBox)c).setEnabled(false);
	            }
	        }//~for(int i=0; i<compArray...
		}

		resetComboBoxes();

        String [] routes = extractOptions(optionslogbook,"route");
		eventTable.refreshTable(new Workout(), routes);

        //call this directly because textarea is inside a
        //scrollpane
        summaryTextArea.setText("");

        //Status Panel
        statusField.setText("");

		
    }//~public void resetFields(...

    /**
    Compares date from window to matching date in list and returns its index.
    Otherwise, it returns -1, which indicates the date was not found.
    
    @param none
    
    @returns The index of the current record.
    */
    public int getCurrentWindowIndex()
    {
	    if (DEBUG)
	    {
		    d.print("getCurrentWindowIndex");
	    }

        Workout current = new Workout();
        int cindex = -1;

        //Get data from window
        int cmonth = Integer.parseInt(monthField.getText());
        int cday = Integer.parseInt(dayField.getText());
        int cyear = Integer.parseInt(yearField.getText());

        ListIterator iter = logbook.listIterator();

        //Iterate thru list to find date match between window date
        // one of the dates in the vector.
        Workout w = null;
        while(iter.hasNext())
        {
            int iterindex = iter.nextIndex();
            w = (Workout)iter.next();

            if ((w.getDate().getMonth() == cmonth) &&
	            (w.getDate().getDay() == cday) &&
    	        (w.getDate().getYear() == cyear))
            {
                //If a match is found copy iterindex into cindex
                cindex = iterindex;
                break;
            }

            else
        	    cindex = -1;
        }//~while(iter.hasNext())...

        return cindex;
    }//~public int getCurrentWin...

    /**
    Validates all field info, and if all correct, returns filled Workout object.
    
    @param none
    
    @return Workout object
    */
    public Workout getFields()
    {
	    if (DEBUG)
	    {
	    	d.print("getFields");
    	}

	    TewlUtilities util = new TewlUtilities();
        Workout recordToSave = new Workout();
        WorkoutDate dateTest = new WorkoutDate();
        WorkoutTime timeTest = new WorkoutTime();
        boolean validMonth = false,
            validDay = false,
            validYear = false,
            validTemp = false,
            validWeather = false,
            validPulse = false,
            validWeight = false,
            validEvents = false,
            validPu = false,
            validSu = false;
	    newRecordValid = false;
        int nmonth = 0;
        int nday = 0;
        int nyear = 0;
        int ntemp = 0;
        String nweather = "";
        int npulse = 0;
        int nweight = 0;
        String nsummary = "";
        int npushups = 0;
        int nsitups = 0;

        String smonth = monthField.getText().trim();
        String sday = dayField.getText().trim();
        String syear = yearField.getText().trim();
		String stemp = (String)tempComboBox.getSelectedItem();
		String sweather = (String)weatherComboBox.getSelectedItem();
		String spulse = (String)pulseComboBox.getSelectedItem();
		String sweight = (String)weightComboBox.getSelectedItem();
        String ssummary = summaryTextArea.getText().trim();
        String spushups = puField.getText().trim();
        String ssitups = suField.getText().trim();

        //If text area is blank, then plug in '*'. This value will be
        //written to the file when saved, which in turn will allow
        //the StringTokenizer to safely read the record into
        //the list when the file is read in.  If left blank,
        //the file will become unreadable by the StringTokenizer
        //because it can't handle two consecutive pipes, as in "||",
        //when this field is written to the file.
        if(ssummary.length() == 0)
    	    ssummary = "*";

        if(util.validDigit(smonth,2) && util.validDigit(sday,2) && util.validDigit(syear,4))
        {
            nmonth = Integer.parseInt(smonth);
            nday = Integer.parseInt(sday);
            nyear = Integer.parseInt(syear);
            if (dateTest.validDate(nmonth,nday,nyear))
            {
                validMonth = true;
                validDay = true;
                validYear = true;
            }
        }//~if(validDigit(smonth,2) ...

		if(!((weatherComboBox.getSelectedItem()).equals("")))
	        validWeather = true;
        if (util.validDigit(stemp,3))
    	    validTemp = true;
		if(!((pulseComboBox.getSelectedItem()).equals("")))
	        validPulse = true;
		if(!((weightComboBox.getSelectedItem()).equals("")))
	        validWeight = true;
	        
	    if (eventTable.validEvents())
	    	validEvents = true;

        if(util.validDigit(spushups,3) && util.validDigit(ssitups,3))
        {
            validSu = true;
            validPu = true;
        }

        if(DEBUG)
        {
			d.print("validMonth = " + validMonth);
			d.print("validDay = " + validDay);
			d.print("validYear = " + validYear);
			d.print("validTemp = " + validTemp);
			d.print("validWeather = " + validWeather);
			d.print("validEvents = " + validEvents);
			d.print("validSu = " + validSu);
			d.print("validPu = " + validPu);
        }

        if(validMonth && validDay && validYear &&
        	validWeather && validTemp && validPulse && validWeight &&
			validEvents &&
    	    validSu && validPu)
        {
	        //set month, day, year
            recordToSave.setDate(nmonth,nday,nyear);

            //set temp
            ntemp = Integer.parseInt(stemp);
            recordToSave.setTemp(ntemp);

            //set weather
			nweather = (String)weatherComboBox.getSelectedItem();
            recordToSave.setWeather(nweather);

            //set pulse
            npulse = Integer.parseInt(pulseComboBox.getSelectedItem().toString());
            recordToSave.setRestingHR(npulse);

            //set weight
            nweight = Integer.parseInt(weightComboBox.getSelectedItem().toString());
            recordToSave.setWeight(nweight);

		    WorkoutEvent [] we = new WorkoutEvent [3];
	    	we = eventTable.extractWorkoutEvents();
		    recordToSave.setEvent("run",
		    						we[0].getDistance(),
		    						we[0].getTime().getTime(),
		    						we[0].getRoute().getCode());

		    recordToSave.setEvent("bike",
		    						we[1].getDistance(),
		    						we[1].getTime().getTime(),
		    						we[1].getRoute().getCode());

			recordToSave.setEvent("swim",
		    						we[2].getDistance(),
		    						we[2].getTime().getTime(),
		    						we[2].getRoute().getCode());
		    						
            //set summary
            nsummary = ssummary;
	        recordToSave.setSummary(nsummary);

	        //set pushups
            npushups = Integer.parseInt(spushups);
            recordToSave.setPu(npushups);

			//set situps
            nsitups = Integer.parseInt(ssitups);
	        recordToSave.setSu(nsitups);
	        
	        if (DEBUG)
				d.print(recordToSave);

            newRecordValid = true;
        }//~validPu)...
        
        else
        JOptionPane.showMessageDialog(
            this,
            "There are some INVALID or BLANK entries!\n" +
            "Be sure to properly format all selected event fields.\n" +
            "Please check both workout pages.",
            "Error",
            JOptionPane.ERROR_MESSAGE);

        return recordToSave;
    }
    
    /**
    Populates window with workout object passed in and makes the record uneditable.
    
    @param w Workout object to populate Aerobic form.
    
    @return void
    */
    public void populateWindow(Workout w)
    {
		if (DEBUG)
		{
			d.print("populateWindow");
		}

		TewlUtilities util = new TewlUtilities();
	    DecimalFormat df = new DecimalFormat("###.#");
	    DecimalFormat dfint = new DecimalFormat("#####");
        DecimalFormat speedFormat = new DecimalFormat("###.##");
        WorkoutTime t = new WorkoutTime();
        WorkoutEvent [] events = w.getEvents();

        resetFields();

        //Set each field with appropriate strings
        monthField.setText(String.valueOf(w.getDate().getMonth()));
        monthField.setEditable(false);
        dayField.setText(String.valueOf(w.getDate().getDay()));
        dayField.setEditable(false);
        yearField.setText(String.valueOf(w.getDate().getYear()));
        yearField.setEditable(false);
		tempComboBox.setSelectedItem(String.valueOf(w.getTemp()));
		tempComboBox.setEnabled(false);
		pulseComboBox.setSelectedItem(String.valueOf(w.getRestingHR()));
		pulseComboBox.setEnabled(false);
		weatherComboBox.setSelectedItem(w.getWeather());
		weatherComboBox.setEnabled(false);
		weightComboBox.setSelectedItem(String.valueOf(w.getWeight()));
		weightComboBox.setEnabled(false);

        //set events
        String [] routes = extractOptions(optionslogbook,"route");        
        eventTable.refreshTable(w,routes);

        summaryTextArea.insert(w.getSummary(),0);
        summaryTextArea.setEditable(false);
        puField.setText(String.valueOf(w.getPu()));
        puField.setEditable(false);
        suField.setText(String.valueOf(w.getSu()));
        suField.setEditable(false);

        statusField.setText("Workout Record " +
            (logbook.indexOf(w) + 1) + " of " + logbook.size());

        //set variables for use inside statusField
        visibleWorkoutRecord = logbook.indexOf(w) + 1;
        logbooksize = logbook.size();

        JButton [] ba3 =
        {
            findButton,editButton,cancelButton,
                newButton,saveButton,deleteButton,
                clearButton,beginButton,prevButton,
                nextButton,endButton
        };

        componentsEnabled(ba3,true);

    }//~public void populateWind...

    /**
    Uses user input from queryDialog to locate matching record in db.
    
    @param none
    
    @return void
    */
    public void find()
    {
	    if (DEBUG)
	    {
		    d.print("find");
	    }

        if(!logbook.isEmpty())
        {
            int recordIndex = 0;
            findDialog = new QueryDialog();
            findDialog.pack();
            findDialog.setVisible(true);
            if(findDialog.isValidRequest())
            {
                String [] mdy = findDialog.getDateStrings();
                recordIndex = recordFound(mdy);
                if(recordIndex >= 0)
                populateWindow((Workout)logbook.elementAt(recordIndex));
                else
                {
                    JOptionPane errorDialog = new JOptionPane();
                    errorDialog.showMessageDialog(
                        this,
                        "No record found",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }//~if(findDialog.isValidReq...
        }//~if(!logbook.isEmpty())...

        else
        {
            JOptionPane errorDialog = new JOptionPane();
            errorDialog.showMessageDialog(
                this,
                "To use this feature, there must be more\n" +
                "than one record in the database.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//~else...
    }//~public void find()...

    /**
    Makes all text components editable.
    
    @param none
    
    @return void
    */
    public void edit()
    {
	    if (DEBUG)
	    {
			d.print("edit");
		}

	    if(!logbook.isEmpty())
        {
            monthField.setEditable(false);
            dayField.setEditable(false);
            yearField.setEditable(false);
			tempComboBox.setEnabled(true);
			pulseComboBox.setEnabled(true);
			weatherComboBox.setEnabled(true);
            summaryTextArea.setEditable(true);
            weightComboBox.setEnabled(true);
            puField.setEditable(true);
            suField.setEditable(true);

            //Place focus in Month textfield
            monthField.requestFocus();

            //Disable all buttons except cancel, save,
            //and clear.
            JButton [] ba =
            {
                findButton,editButton,
                    newButton,beginButton,prevButton,
                    nextButton,endButton
            };

            componentsEnabled(ba,false);
        }//~if(!logbook.isEmpty())...

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "A new record must be created\n" +
                "before it can be edited.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//~public void edit()...

    /**
    Cancels edit mode.
    
    @param none
    
    @return void
    */
    public void cancel()
    {
	    if (DEBUG)
	    {
		    d.print("cancel");
	    }

        if (!logbook.isEmpty())
        {
			if(tempComboBox.isEnabled() && puField.isEditable())
            {
                if(monthField.getText().trim().length() > 0 &&
                dayField.getText().trim().length() > 0 &&
                yearField.getText().trim().length() > 0)
                {
                    int cindex = getCurrentWindowIndex();
                    if(cindex != -1)
                    {
                        resetFields();
                        //Reload current index
                        populateWindow
                        ((Workout)logbook.elementAt(cindex));
                    }

                    else
                    {
                        //Load last record
                        populateWindow((Workout)logbook.lastElement());
                    }

                    //Enable all button in case they were
                    //disabled previously
                    Component [] compOn =
                    {
                        findButton,
                            editButton,
                            cancelButton,
                            newButton,
                            saveButton,
                            deleteButton,
                            clearButton,
                            beginButton,
                            prevButton,
                            nextButton,
                            endButton
                    };//~Component [] compOn =...

                    componentsEnabled(compOn,true);
                }//~yearField.getText().trim...

                else
                {
                    //Load last record
                    populateWindow((Workout)logbook.lastElement());

                    //Enable all button in case they were
                    //disabled previously
                    Component [] compOn =
                    {
                        findButton,
                            editButton,
                            cancelButton,
                            newButton,
                            saveButton,
                            deleteButton,
                            clearButton,
                            beginButton,
                            prevButton,
                            nextButton,
                            endButton
                    };//~Component [] compOn =...

                    componentsEnabled(compOn,true);
                }//~else...
            }//~if(distanceField.isEdita...

            else
            JOptionPane.showMessageDialog
            (this,
                "This record must be in the Edit mode first",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//~if (!logbook.isEmpty())...

        else
        {
            resetFields();
            //get all textfields and textareas in the aerobic panel
            //and make them uneditable.
            Component [] compOff = mainPanel.getComponents();
            componentsEditable(compOff,false);
            compOff = stationaryPanel.getComponents();
            componentsEditable(compOff,false);
            //manually make summary text area uneditable
            //because it is nested inside a scrollpane
            summaryTextArea.setEditable(false);
        }//~else...

    }

    /**
    Resets all fields and makes the blank record.
    
    @param none
    
    @return void
    */
    public void create()
    {
	    if (DEBUG)
	    {
		    d.print("create");
	    }

	    String [] strArray = new String[10];

	    resetFields();

        //make all fields editable
        monthField.setEditable(true);
        dayField.setEditable(true);
        yearField.setEditable(true);
		tempComboBox.setEnabled(true);
		pulseComboBox.setEnabled(true);
		weatherComboBox.setEnabled(true);
        summaryTextArea.setEditable(true);
        weightComboBox.setEnabled(true);
        puField.setEditable(true);
        suField.setEditable(true);

        //Place focus in Month textfield
        monthField.requestFocus();

        //Disable all buttons except cancel, save,
        //and clear.
        JButton [] ba =
        {
            findButton,editButton,
            newButton,beginButton,prevButton,
            nextButton,endButton
        };

        componentsEnabled(ba,false);
    }//~public void create()...

    /**
    Saves the current record to list.
    
    @param none
    
    @return void
    */
    public void save()
    {
	    if (DEBUG)
	    {
		    d.print("save");
	    }

	    Workout w = new Workout();
        int index = 0;
		if(tempComboBox.isEnabled() && puField.isEditable())
        {
            w = getFields();
            if(newRecordValid)
            {
                index = getCurrentWindowIndex();
                if((index >= 0) && (!logbook.isEmpty()))
                {
                    //record exists already; ask to modify
                    int answer = JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to save/modify this existing record?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    if(answer == JOptionPane.YES_OPTION)
                    {
                        //Save modifed existing record to list
                        logbook.setElementAt(w,index);
                        //Reload record and make it uneditable
                        populateWindow(w);
                    }

                    else
                    //Reload original, unmodified record
                    populateWindow((Workout)logbook.elementAt(index));
                }//~if((index >= 0) && (!log...

                else
                {
                    //Add new record to end of list
                    logbook.addElement(w);
                    //Reload record and make it uneditable
                    populateWindow(w);
                }
            }//~if(newRecordValid)...
        }//~if(distanceField.isEdita...

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "This record must be in the Edit mode first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//~public void save()...

    /**
    Deletes the current record from list.
    
    @param none
    
    @return void
    */
    public void delete()
    {
	    if (DEBUG)
	    {
		    d.print("delete");
	    }

		if(tempComboBox.isEnabled() && puField.isEditable())
        {
            int answer = JOptionPane.showConfirmDialog(
                this,
                "Delete record",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if(answer == JOptionPane.YES_OPTION)
            {
                int deleteIndex = getCurrentWindowIndex();
                if (!logbook.isEmpty())
                {
                    logbook.removeElementAt(deleteIndex);
                    resetFields();
                    populateWindow((Workout)logbook.lastElement());
                }

                else
                {
                    resetFields();
                }
            }//~if(answer == JOptionPane...
        }//~if(distanceField.isEdita...

        else
        JOptionPane.showMessageDialog(
            this,
            "This record must be in the Edit mode first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }//~public void delete()...

    /**
    Clears current record.  Doesn't delete from list.
    
    @param none
    
    @result void
    */
    public void clear()
    {
	    if (DEBUG)
	    {
		    d.print("clear");
	    }

	    //If all field are not editable, then ask operator to place state in Edit mode.
        if(!monthField.isEditable() && !tempComboBox.isEnabled() && !puField.isEditable())
        {
            JOptionPane.showMessageDialog(
                this,
                "This record must be in the Edit mode first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        //if only monthField is editable, then clear all fields, except the date.
        else if(!monthField.isEditable() && tempComboBox.isEnabled() && puField.isEditable())
        {
            //reset all fields on aerobic and strength panels
            //minus date fields
            Component [] comp =
            {
                statusField,
                puField,
                suField
            };//~Component [] comp =...

            Component c = null;
            for(int i=0; i<comp.length; i++)
            {
                c = comp[i];
                ((JTextField)c).setText("");
            }

            summaryTextArea.setText("");
            Component [] compOff =
            {
                findButton,
                editButton,
                deleteButton,
                newButton,
                beginButton,
                prevButton,
                nextButton,
                endButton
            };//~Component [] compOff =...

            componentsEnabled(compOff,false);
        }//~else if(!monthField.isEd...

        //if all fields are editable, clear all.
        else if(monthField.isEditable() && tempComboBox.isEnabled() && puField.isEditable())
        {
            resetFields();
            Component [] compOff =
            {
                findButton,
                    editButton,
                    deleteButton,
                    newButton,
                    beginButton,
                    prevButton,
                    nextButton,
                    endButton
            };//~Component [] compOff =...

            componentsEnabled(compOff,false);
        }//~else if(monthField.isEdi...
    }

    /**
    Populates window with first record in list.
    
    @param none
    
    @return void
    */
    public void getFirst()
    {
	    if (DEBUG)
	    {
		    d.print("getFirst");
	    }

        if(!logbook.isEmpty())
        populateWindow((Workout)logbook.firstElement());
        else
        JOptionPane.showMessageDialog(
            this,
            "There are no records in the log.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    /**
    Populates window with previous record in list.
    
    @param none
    
    @return void
    */
    public void getPrev()
    {
	    if (DEBUG)
	    {
		    d.print("getPrev");
	    }

        if(!logbook.isEmpty())
        {
            int index = 0;
            index = getCurrentWindowIndex();
            ListIterator iter = logbook.listIterator(index);

            if (iter.hasPrevious())
            populateWindow((Workout)iter.previous());
            else
            {
                JOptionPane errorDialog = new JOptionPane();
                errorDialog.showMessageDialog
                (this,
                    "There are no more previous records to view",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~if(!logbook.isEmpty())...

        else
        JOptionPane.showMessageDialog(
            this,
            "There are no records in the log.",
            "Error",
            JOptionPane.ERROR_MESSAGE);

    }

    /**
    Populates window with next record in list
    
    @param none
    
    @return void
    */
    public void getNext()
    {
	    if (DEBUG)
	    {
		    d.print("getNext");
	    }

        if(!logbook.isEmpty())
        {
            int index = 0;
            index = getCurrentWindowIndex();
            ListIterator iter = logbook.listIterator(index);

            if (iter.hasNext())
            {
                iter.next();
                if (iter.hasNext())
                populateWindow((Workout)iter.next());
                else
                {
                    JOptionPane.showMessageDialog
                    (this,
                        "There are no more records to view",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }//~if (iter.hasNext())...

            else
            JOptionPane.showMessageDialog
            (this,
                "There are no more records to view",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//~if(!logbook.isEmpty())...

        else
        JOptionPane.showMessageDialog(
            this,
            "There are no records in the log.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    /**
    Populates window with last record in list.
    
    @param none
    
    @return void
    */
    public void getLast()
    {
	    if (DEBUG)
	    {
		    d.print("getLast");
	    }

        if(!logbook.isEmpty())
        populateWindow((Workout)(logbook.lastElement()));
        else
        JOptionPane.showMessageDialog(
            this,
            "There are no records in the log.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    /**
    Populates Maintenance Tab.
    
    @param none
    
    @return void
    */
    public void populateMaintenanceTab(Maintenance m)
    {
	    if (DEBUG)
	    {
		    d.print("populateMaintenanceTab");
	    }

        maintDateField.setText(m.getMaintDate());
        maintWorkTextArea.setText(m.getMaintSummary());
        maintDateField.setEditable(false);
        maintWorkTextArea.setEditable(false);

        JButton [] ba3 =
        {
            editButton,cancelButton,
                newButton,saveButton,deleteButton,
                clearButton,prevButton,nextButton
        };

        componentsEnabled(ba3,true);
    }//~public void populateMain...

    /**
    Returns index of current Maintenance record.
    
    @param none
    
    @return The index of the current Maintenance record.
    */
    public int getMaintWindowIndex()
    {
	    if (DEBUG)
	    {
		    d.print("getMaintWindowIndex");
	    }

        int cindex = -1;
        ListIterator miter = mntlogbook.listIterator();
        Maintenance m = new Maintenance();

        while(miter.hasNext())
        {
            int index = miter.nextIndex();
            m = (Maintenance)miter.next();
            if(maintDateField.getText().equals(m.getMaintDate()))
            {
                cindex = index;
                break;
            }
        }//~while(miter.hasNext())...

        return cindex;
    }//~public int getMaintWindo...

    /**
    Gets previous maintenance record.
    
    @param none
    
    @return void
    */
    public void getPrevMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("getPrevMaintRecord");
	    }

        int pindex = getMaintWindowIndex();
        if(pindex != -1)
        {
            ListIterator piter = mntlogbook.listIterator(pindex);
            if(piter.hasPrevious())
            populateMaintenanceTab((Maintenance)piter.previous());
            else
            JOptionPane.showMessageDialog(
                this,
                "There are no previous records to view.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//~if(pindex != -1)...

        else
        JOptionPane.showMessageDialog(
            this,
            "There are no previous records to view.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }//~public void getPrevMaint...

    /**
    Gets next maintenance record.
    
    @param none
    
    @return void
    */
    public void getNextMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("getNextMaintRecord");
	    }

        int nindex = getMaintWindowIndex();
        if(nindex != -1)
        {
            ListIterator niter = mntlogbook.listIterator(nindex);
            if(niter.hasNext())
            {
                niter.next();
                if(niter.hasNext())
                populateMaintenanceTab((Maintenance)niter.next());
                else
                JOptionPane.showMessageDialog(
                    this,
                    "There are no more records to view.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }//~if(niter.hasNext())...

            else
            JOptionPane.showMessageDialog(
                this,
                "There are no previous records to view.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//~if(nindex != -1)...

        else
        JOptionPane.showMessageDialog(
            this,
            "There are no more records to view.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }//~public void getNextMaint...

    /**
    Initializes maintenance record form to default values.
    
    @param none
    
    @return void
    */
    public void createMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("createMaintRecord");
	    }

        maintDateField.setText("");
        maintDateField.setEditable(true);
        maintWorkTextArea.setText("");
        maintWorkTextArea.setEditable(true);
    }

    /**
    Makes maintenance editable.
    
    @param none
    
    @return void
    */
    public void editMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("editMaintRecord");
	    }

        Maintenance m = new Maintenance();

        if(mntlogbook.size() == 1)
        {
            m = (Maintenance)mntlogbook.firstElement();
            if ((m.getMaintDate()).equals("*"))
            maintDateField.setEditable(true);
        }

        maintWorkTextArea.setEditable(true);

        JButton [] ba =
        {
            deleteButton,findButton,cancelButton,
                clearButton
        };

        componentsEnabled(ba,true);
        JButton [] ba2 =
        {
            editButton,newButton,prevButton,
                nextButton
        };

        componentsEnabled(ba2,false);
    }//~public void editMaintRec...

    /**
    Cancels modifications to current maintenance record.
    
    @param none
    
    @return void
    */
    public void cancelMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("cancelMaintRecord");
	    }

        if(maintWorkTextArea.isEditable())
        {
            populateMaintenanceTab
            ((Maintenance)mntlogbook.lastElement());
            maintDateField.setEditable(false);
            maintWorkTextArea.setEditable(false);
            //Enable all button in case they were
            //disabled previously
            JButton [] ba1 =
            {
                editButton,cancelButton,
                    newButton,saveButton,deleteButton,
                    clearButton,prevButton,
                    nextButton
            };

            componentsEnabled(ba1,true);
        }//~if(maintWorkTextArea.isE...

        else
        JOptionPane.showMessageDialog(
            this,
            "This record must be in the Edit mode first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }//~public void cancelMaintR...

    /**
    Saves current maintenance record
    
    @param none
    
    @return void
    */
    public void saveMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("saveMaintRecord");
	    }

	    Maintenance m = new Maintenance();
        String mntDate = maintDateField.getText().trim();
        String mntSummary = maintWorkTextArea.getText().trim();
        int index = -1;
        if(maintWorkTextArea.isEditable())
        {
            index = getMaintWindowIndex();
            if((index >= 0) && (!mntlogbook.isEmpty()))
            {
                //record exists already; ask to modify
                int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to save/modify this existing record?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if(answer == JOptionPane.YES_OPTION)
                {
                    m.setMaintDate(mntDate);
                    m.setMaintSummary(mntSummary);

                    //Save modifed existing record to list
                    mntlogbook.setElementAt(m,index);
                    //Reload record and make it uneditable
                    populateMaintenanceTab(m);
                }//~if(answer == JOptionPane...

                else
                //Reload original, unmodified record
                populateMaintenanceTab
                ((Maintenance)mntlogbook.elementAt(index));
            }//~if((index >= 0) && (!mnt...

            else
            {
                if(index < 0)
                {
                    m.setMaintDate(mntDate);
                    m.setMaintSummary(mntSummary);
                }

                if(mntlogbook.isEmpty())
                {
                    m.setMaintDate("*");
                    m.setMaintSummary("*");
                }

                if (mntDate.length() < 1 || mntDate == null)
                m.setMaintDate("*");
                if (mntSummary.length() < 1 || mntSummary == null)
                m.setMaintSummary("*");

                //Add new record to end of list
                mntlogbook.addElement(m);
                //Reload record and make it uneditable
                populateMaintenanceTab(m);
            }//~else...
        }//~if(maintWorkTextArea.isE...

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "This record must be in the Edit mode first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//~public void saveMaintRec...

    /**
    Deletes current maintenance record
    
    @param none
    
    @return void
    */
    public void deleteMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("deleteMaintRecord");
	    }

        if(maintWorkTextArea.isEditable())
        {
            int answer = JOptionPane.showConfirmDialog(
                this,
                "Delete record",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if(answer == JOptionPane.YES_OPTION)
            {
                JButton [] ba =
                {
                    findButton,saveButton,
                        editButton,newButton,cancelButton,
                        clearButton
                };

                componentsEnabled(ba,true);
                JButton [] ba2 =
                {
                    deleteButton
                };

                componentsEnabled(ba2,false);
                int deleteIndex = getMaintWindowIndex();
                if(!mntlogbook.isEmpty())
                {
                    mntlogbook.removeElementAt(deleteIndex);
                    populateMaintenanceTab
                    ((Maintenance)mntlogbook.lastElement());
                }

                else
                {
                    maintDateField.setText("");
                    maintWorkTextArea.setText("");
                }
            }//~if(answer == JOptionPane...
        }//~if(maintWorkTextArea.isE...

        else
        JOptionPane.showMessageDialog(
            this,
            "This record must be in the Edit mode first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

	/**
	Clears current maintenance record
	
	@param none
	
	@return void
	*/
    public void clearMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("cleatMaintRecord");
    	}

        if(maintWorkTextArea.isEditable())
        maintWorkTextArea.setText("");
        else
        JOptionPane.showMessageDialog(
            this,
            "This record must be in the Edit mode first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }//~public void clearMaintRe...

    /**
    Provides simple way to enable or disable buttons during an edit.
    
    @param c Array of Components
    @param b True to make components enabled or False to make components unenabled.
    
    @return void
    */
    public void componentsEnabled(Component [] c, boolean b)
    {
	    if (DEBUG)
	    {
		    d.print("componentsEnabled");
	    }

        Component comp = null;
        for(int i=0; i<c.length; i++)
        {
            comp = c[i];
            if(comp instanceof JButton)
            ((JButton)comp).setEnabled(b);
            else if (comp instanceof JMenuItem)
            ((JMenuItem)comp).setEnabled(b);
            else if (comp instanceof JTextField)
            ((JTextField)comp).setEnabled(b);
            else if (comp instanceof JRadioButton)
            ((JRadioButton)comp).setEnabled(b);
            else if (comp instanceof JTextArea)
            ((JTextArea)comp).setEnabled(b);
        }//~for(int i=0; i<c.length;...
    }

    /**
    Provides simple way to make textfields or textareas editable.
    
    @param c Array of components
    @param b True to make components editable or False to make them uneditable
    
    @return void
    */
    public void componentsEditable(Component [] c, boolean b)
    {
	    if (DEBUG)
	    {
		    d.print("componentsEditable");
	    }

        Component comp = null;
        for(int i=0; i<c.length; i++)
        {
            comp = c[i];
            if (comp instanceof JTextField)
            ((JTextField)comp).setEditable(b);
            else if (comp instanceof JTextArea)
            ((JTextArea)comp).setEditable(b);
        }
    }//~public void componentsEd...

    /**
    Populates Statistics Panel.
    
    @param event Name of event to use to populate Statistics Form
    
    @return void
    */
    public void populateStatsTab(String event)
    {
	    if (DEBUG)
	    {
		    d.print("populateStatsTab");
	    }

	    TewlUtilities util = new TewlUtilities();
        DecimalFormat df = new DecimalFormat("###.##");
        double distance = 0;
        double avgDistance = 0;
        double avgSpeed = 0;
        WorkoutTime t = new WorkoutTime();
        totalMilesToDateField.setText("");
        avgMilesField.setText("");
        statsAvgSpeedField.setText("");

	    if (statsRunRadio.isSelected())
	    {
			totalMilesToDateLabel.setText("Total miles to date: ");
			avgMilesLabel.setText("Average miles per day: ");
			statsAvgSpeedLabel.setText("Average speed per workout: ");
			statsAvgPaceLabel.setText("mins/mile");
	        distance = util.getTotal(event,logbook);
		    totalMilesToDateField.setText(df.format(distance));
	    	avgDistance = util.getAvgPerDay(event,logbook);
	        avgMilesField.setText(df.format(avgDistance));
		    avgSpeed = util.getAvgSpeedForAllWorkouts(event,logbook);
	    	statsAvgSpeedField.setText(t.formatTime(avgSpeed));
	    }
	    else if (statsBikeRadio.isSelected())
	    {
			totalMilesToDateLabel.setText("Total miles to date: ");
			avgMilesLabel.setText("Average miles per day: ");
			statsAvgSpeedLabel.setText("Average speed per workout: ");
			statsAvgPaceLabel.setText("mph");
	        distance = util.getTotal(event,logbook);
		    totalMilesToDateField.setText(df.format(distance));
	    	avgDistance = util.getAvgPerDay(event,logbook);
	        avgMilesField.setText(df.format(avgDistance));
		    avgSpeed = util.getAvgSpeedForAllWorkouts(event,logbook);
	    	statsAvgSpeedField.setText(df.format(avgSpeed));
	    }
	    else
        {
			totalMilesToDateLabel.setText("Total yards to date: ");
			avgMilesLabel.setText("Average yards per day: ");
			statsAvgSpeedLabel.setText("Average speed per workout: ");
			statsAvgPaceLabel.setText("mins/100 yds");
	        distance = util.getTotal(event,logbook);
		    totalMilesToDateField.setText(df.format(distance));
	    	avgDistance = util.getAvgPerDay(event,logbook);
	        avgMilesField.setText(df.format(avgDistance));
		    avgSpeed = util.getAvgSpeedForAllWorkouts(event,logbook);
	    	statsAvgSpeedField.setText(t.formatTime(avgSpeed));
        }

    }//~public void populateStat...

    /**
    Calculates fields based on entered values.
    
    @param none
    
    @return void
    */
    public void calcFields()
    {
	    if (DEBUG)
	    {
		    d.print("calcFields");
	    }

	    TewlUtilities util = new TewlUtilities();
        double factor1,factor2,result,convertToKiloDist,convertToKiloPace,
            convertToMileDist,convertToMilePace,mins,secs = 0.0;
        DecimalFormat formatter = new DecimalFormat("###.##");
        String resultStr = null;
        WorkoutTime t = new WorkoutTime();

        //miles -> time -> mph
        //Time = Miles/(mph*60)
        if (calcMilesCheck.isSelected() &&
        	calcTimeRadio.isSelected() &&
        	calcPaceMphRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("1");
	        }
            if(util.validDouble(calcPaceMphField.getText().trim(),6) &&
            util.validDouble(calcDistanceMilesField.getText().trim(),6))
            {
                //Pace (mph)
                factor1 = Double.parseDouble(calcPaceMphField.getText().trim());

                //Distance (miles)
                factor2 = Double.parseDouble(calcDistanceMilesField.getText().trim());

                //Time
                result = factor2/(factor1/60.0);
                resultStr = t.formatTime(result);
                calcTimeField.setText(resultStr);

                //Pace (kph)
                convertToKiloPace = factor1/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //Distance (kilos)
                convertToKiloDist = factor2/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/factor1));

                //mins per 100yds
                //mph to mins/100yds factor = (60 mins * 100yds)/1760
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((60*100)/(factor1*1760)));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/convertToKiloPace));

                //mins per 100m
                //mph to mins/100m factor = (0.625 miles * 60 mins * 100 m) / 1000 m
                calcPaceMinPer100mField.setText
                	(t.formatTime((0.625*60*100)/(factor1*1000)));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~util.validDouble(calcDistance...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~else if (calcMilesCheck....

		//miles -> time -> min/mile
        //Time = (mins / mile) * (Distance in miles)
        else if (calcMilesCheck.isSelected() &&
        	calcTimeRadio.isSelected() &&
        	calcPaceMinPerMileRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("2");
	        }
            if(t.validTime(calcPaceMinPerMileField.getText().trim()) &&
            util.validDouble(calcDistanceMilesField.getText().trim(),6))
            {
                //Pace (MinPerMile)
                factor1 = t.getConvertedTime(calcPaceMinPerMileField.getText().trim());

                //Distance (miles)
                factor2 = Double.parseDouble(calcDistanceMilesField.getText().trim());

                //Time
                result = factor1 * factor2;
				resultStr = t.formatTime(result);
                calcTimeField.setText(resultStr);


                //Pace (kph)
                convertToKiloPace = (1/factor1)*(1/MILE_TO_KM)*60;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //Distance (kilos)
                convertToKiloDist = factor2/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Mph
                resultStr = formatter.format(60/factor1);
                calcPaceMphField.setText(resultStr);

                //mins per 100yds
                //minpermile to mins/100yds factor = (60 mins * 100yds)/1760
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor1*100)/1760));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/convertToKiloPace));

                //mins per 100m
                //minpermile to mins/100m factor = (0.625 miles * 100 m) / 1000 m
                calcPaceMinPer100mField.setText
                	(t.formatTime((factor1*0.625*100)/1000));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~util.validDouble(calcDistance...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        //miles -> time -> mins/100yds
        //
        else if (calcMilesCheck.isSelected() &&
			calcTimeRadio.isSelected() &&
        	calcPaceMinsPer100ydsRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("3");
	        }
            if(util.validDouble(calcDistanceMilesField.getText().trim(),6) &&
            t.validTime(calcPaceMinPer100ydsField.getText().trim()))
            {
                //Distance (miles)
                factor1 = Double.parseDouble(calcDistanceMilesField.getText().trim());

                //mins/100yds
                factor2 = t.getConvertedTime(calcPaceMinPer100ydsField.getText().trim());

                //Pace (mph) = (60 mins * 100 yds)/ 1760 yds
                result = (60 * 100) / (factor2 * 1760);
                resultStr = formatter.format(result);
                calcPaceMphField.setText(resultStr);

                //Distance (kilo)
                convertToKiloDist = factor1/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Time
                //mins = miles * (hr/(x)miles) * (60mins/1hr)
				resultStr = t.formatTime((factor1*60)/result);
                calcTimeField.setText(resultStr);

                //Min Per Mile
                //min per mile = (1/((x)miles/hr) * (60 mins/ 1 hr)
                calcPaceMinPerMileField.setText(t.formatTime(60/result));

                //Pace (kilo)
                convertToKiloPace = result/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //Min Per Km factor = (1760 * .625)/100
                calcPaceMinPerKmField.setText
                	(t.formatTime((factor2 * 1760 * 0.625)/100));

                //mins per 100m
                //minpermile to mins/100m factor = (1760yds * .625mi * 100m)/1000m
                calcPaceMinPer100mField.setText
                	(t.formatTime((factor2 * 1760 * 0.625)/1000));

                 String sel = calcSelectionGroup.getSelection().getActionCommand();
             }//~t.validTime(calcTimeFiel...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
       }//~else if (calcMilesCheck....

        //miles -> distance -> mph
        //miles = (mph*60)/Time
        else if(calcMilesCheck.isSelected() &&
        	calcDistanceRadio.isSelected() &&
        	calcPaceMphRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("4");
	        }
            if(util.validDouble(calcPaceMphField.getText().trim(),6) &&
            t.validTime(calcTimeField.getText().trim()))
            {
                //Pace(mph)
                factor1 = Double.parseDouble(calcPaceMphField.getText().trim());

                //Time
                factor2 = t.getConvertedTime(calcTimeField.getText().trim());

                //Distance (miles)
                result = (factor1/60.0)*factor2;
                resultStr = formatter.format(result);
                calcDistanceMilesField.setText(resultStr);

                //Pace (kph)
                convertToKiloPace = factor1/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //Distance (kilo)
                convertToKiloDist = result/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/factor1));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/convertToKiloPace));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~t.validTime(calcTimeFiel...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~if(calcMilesCheck.isSele...

        //miles -> distance -> min/mile
        //Miles = Time / MinsPerMile
        else if (calcMilesCheck.isSelected() &&
        	calcDistanceRadio.isSelected() &&
        	calcPaceMinPerMileRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("5");
	        }
	     	if (t.validTime(calcTimeField.getText().trim()) &&
	     	t.validTime(calcPaceMinPerMileField.getText().trim()))
	     	{
				//Time
				factor1 = t.getConvertedTime(calcTimeField.getText().trim());

				//MinsPerMile
				factor2 = t.getConvertedTime(calcPaceMinPerMileField.getText().trim());

				//Miles
				result = factor1 / factor2;
                resultStr = formatter.format(result);
                calcDistanceMilesField.setText(resultStr);

                //Pace (mph)
                convertToMilePace = (result/factor1) * 60;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr);

                //Pace (kph)
                convertToKiloPace = convertToMilePace/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //Distance (kilo)
                convertToKiloDist = result/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/convertToKiloPace));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
	     	}
	     	else
	     	{
               JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        //miles -> distance -> min/100yds
        //
        else if (calcMilesCheck.isSelected() &&
			calcDistanceRadio.isSelected() &&
        	calcPaceMinsPer100ydsRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("6");
	        }
	     	if (t.validTime(calcTimeField.getText().trim()) &&
	     	t.validTime(calcPaceMinPer100ydsField.getText().trim()))
	     	{
				//Time
				factor1 = t.getConvertedTime(calcTimeField.getText().trim());

				//MinsPer100yds
				factor2 = t.getConvertedTime(calcPaceMinPer100ydsField.getText().trim());

				//Miles
				result = (factor1 * 100)/ (factor2 * 1760);
                resultStr = formatter.format(result);
                calcDistanceMilesField.setText(resultStr);

                //Pace (mph)
                convertToMilePace = (result/factor1) * 60;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr);

                //Pace (kph)
                convertToKiloPace = convertToMilePace/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //min per mile
                calcPaceMinPerMileField.setText
                	(t.formatTime((factor2 * 1760)/100));

                //Distance (kilo)
                convertToKiloDist = result/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/convertToKiloPace));

                //min per 100m
                calcPaceMinPer100mField.setText
                	(t.formatTime((factor2 * 1760 * 0.625)/1000));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
	     	}
	     	else
	     	{
               JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        //miles -> pace -> distance in miles & time
        //Pace = Miles/(Time/60)
        else if (calcMilesCheck.isSelected() &&
        calcPaceRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("7");
	        }
            if(util.validDouble(calcDistanceMilesField.getText().trim(),6) &&
            t.validTime(calcTimeField.getText().trim()))
            {
                //Distance (miles)
                factor1 = Double.parseDouble(calcDistanceMilesField.getText().trim());

                //Time
                factor2 = t.getConvertedTime(calcTimeField.getText().trim());

                //Pace (mph)
                result = factor1/(factor2/60.0);
                resultStr = formatter.format(result);
                calcPaceMphField.setText(resultStr);

                //Distance (kilo)
                convertToKiloDist = factor1/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloDist);
                calcDistanceKiloField.setText(resultStr);

                //Pace (kilo)
                convertToKiloPace = result/MILE_TO_KM;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr );

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/result));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/convertToKiloPace));

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor2 * 100)/(factor1 * 1760)));

                //min per 100m
                calcPaceMinPer100mField.setText
                	(t.formatTime((factor2 * .625 * 100)/(factor1 * 1000)));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~t.validTime(calcTimeFiel...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~else if (calcMilesCheck....

		//kilos -> time -> kph
        //Time = Kilos/((distance in km)/60)
        else if (calcKilosCheck.isSelected() &&
        calcTimeRadio.isSelected() &&
        calcPaceKphRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("8");
	        }
            if(util.validDouble(calcPaceKphField.getText().trim(),6) &&
            util.validDouble(calcDistanceKiloField.getText().trim(),6))
            {
                //Pace (kph)
                factor1 = Double.parseDouble(calcPaceKphField.getText().trim());

                //Distance (kilos)
                factor2 = Double.parseDouble(calcDistanceKiloField.getText().trim());

                //Time
                result = factor2/(factor1/60.0);
                resultStr = t.formatTime(result);
                calcTimeField.setText(resultStr);

                //Pace (mph)
                convertToMilePace = factor1*KM_TO_MILE;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr );

                //Distance (miles)
                convertToMileDist = factor2*KM_TO_MILE;
                resultStr = formatter.format(convertToMileDist);
                calcDistanceMilesField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/convertToMilePace));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/factor1));

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((100*60)/(factor1 * .625 * 1760)));

                //min per 100m
                calcPaceMinPer100mField.setText
                	(t.formatTime((100 * 60)/(factor1 * 1000)));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~util.validDouble(calcDistance...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~else if (calcKilosCheck....

        //kilos -> time -> min/km
        else if (calcKilosCheck.isSelected() &&
	        calcTimeRadio.isSelected() &&
    	    calcPaceMinPerKmRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("9");
	        }
            if(t.validTime(calcPaceMinPerKmField.getText().trim()) &&
            util.validDouble(calcDistanceKiloField.getText().trim(),6))
            {
                //Pace (MinPerKm)
                factor1 = t.getConvertedTime(calcPaceMinPerKmField.getText().trim());

                //Distance (km)
                factor2 = Double.parseDouble(calcDistanceKiloField.getText().trim());

                //Time
                result = factor1 * factor2;
                resultStr = t.formatTime(result);
                calcTimeField.setText(resultStr);

                //Pace (mph)
                convertToMilePace = (1/factor1) * MILE_TO_KM * 60;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr );

                //Distance (mile)
                convertToMileDist = factor2 * MILE_TO_KM;
                resultStr = formatter.format(convertToMileDist);
                calcDistanceMilesField.setText(resultStr);

                //min per mile
                calcPaceMinPerMileField.setText(t.formatTime(factor1 * (1/KM_TO_MILE)));

                //kph
                resultStr = formatter.format((1/factor1) * 60);
                calcPaceKphField.setText(resultStr);

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor1 * 100)/(.625 * 1760)));

                //min per 100m
				calcPaceMinPer100mField.setText
					(t.formatTime((factor1 * 100)/1000));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~util.validDouble(calcDistance...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        //kilos -> time -> mins/100m
        else if (calcKilosCheck.isSelected() &&
        	calcTimeRadio.isSelected() &&
        	calcPaceMinsPer100mRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("10");
	        }

            if(util.validDouble(calcDistanceKiloField.getText().trim(),6) &&
            t.validTime(calcPaceMinPer100mField.getText().trim()))
            {
                //Distance (kilos)
                factor1 = Double.parseDouble(calcDistanceKiloField.getText().trim());

                //Mins per 100m
                factor2 = t.getConvertedTime(calcPaceMinPer100mField.getText().trim());

                //Pace (mph)
                result = (60 * .625 * 100)/(factor2 * 1000);
                resultStr = formatter.format(result);
                calcPaceMphField.setText(resultStr);

                //time
                calcTimeField.setText
                	(t.formatTime((factor1 * factor2 * 1000)/100));

                //Distance (miles)
                resultStr = formatter.format(factor1 * .625);
                calcDistanceMilesField.setText(resultStr);

                //Pace (kph)
                resultStr = formatter.format((100 * 60)/(factor2 * 1000));
                calcPaceKphField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText
                	(t.formatTime((factor2 * 1000)/(100 * .625)));

                //Min Per Km
                calcPaceMinPerKmField.setText
                	(t.formatTime((factor2 * 1000)/100));

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor2 * 1000 * 100)/(100 * .625 * 1760)));

                 String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~t.validTime(calcTimeFiel...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
       }//~else if (calcMilesCheck....

        //kilometers -> distance -> kph
        else if(calcKilosCheck.isSelected() &&
	        calcDistanceRadio.isSelected() &&
    	    calcPaceKphRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("11");
	        }
            if(util.validDouble(calcPaceKphField.getText().trim(),6) &&
            t.validTime(calcTimeField.getText().trim()))
            {
                //Pace (kph)
                factor1 = Double.parseDouble(calcPaceKphField.getText().trim());

                //Time
                factor2 = t.getConvertedTime(calcTimeField.getText().trim());

                //Distance (kilos)
                result = (factor1/60.0)*factor2;
                resultStr = formatter.format(result);
                calcDistanceKiloField.setText(resultStr);

                //Pace (mph)
                convertToMilePace = factor1*KM_TO_MILE;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr );

                //Distance (miles)
                convertToMileDist = result*KM_TO_MILE;
                resultStr = formatter.format(convertToMileDist);
                calcDistanceMilesField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/convertToMilePace));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/factor1));

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((60 * 100)/(factor1 * .625 * 1760)));

                //min per 100m
                calcPaceMinPer100mField.setText
                	(t.formatTime((100 * 60)/(factor1 * 1000)));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~t.validTime(calcTimeFiel...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~else if(calcKilosCheck.i...

        //kilometers -> distance -> min/km
        else if (calcKilosCheck.isSelected() &&
	        calcDistanceRadio.isSelected() &&
    	    calcPaceMinPerKmRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("12");
	        }

	     	if (t.validTime(calcTimeField.getText().trim()) &&
	     	t.validTime(calcPaceMinPerKmField.getText().trim()))
	     	{
				//Time
				factor1 = t.getConvertedTime(calcTimeField.getText().trim());

				//MinsPerKm
				factor2 = t.getConvertedTime(calcPaceMinPerKmField.getText().trim());

				//Distance (km)
				result = factor1 / factor2;
                resultStr = formatter.format(result);
                calcDistanceKiloField.setText(resultStr);

                //Pace (kph)
                convertToKiloPace = (result/factor1) * 60;
                resultStr = formatter.format(convertToKiloPace);
                calcPaceKphField.setText(resultStr);

                //Pace (mph)
                convertToMilePace = convertToKiloPace*KM_TO_MILE;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr );

                //Distance (mile)
                convertToMileDist = result*KM_TO_MILE;
                resultStr = formatter.format(convertToMileDist);
                calcDistanceMilesField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/convertToMilePace));

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor2 * 100)/(.625 * 1760)));

                //min per 100m
                calcPaceMinPer100mField.setText
                	(t.formatTime((factor2 * 100)/1000));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
	     	}
	     	else
	     	{
               JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        //kilometers -> distance -> mins/100m
        else if (calcKilosCheck.isSelected() &&
        	calcDistanceRadio.isSelected() &&
        	calcPaceMinsPer100mRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("13");
	        }

	     	if (t.validTime(calcTimeField.getText().trim()) &&
	     	t.validTime(calcPaceMinPer100mField.getText().trim()))
	     	{
				//Time
				factor1 = t.getConvertedTime(calcTimeField.getText().trim());

				//MinsPer100m
				factor2 = t.getConvertedTime(calcPaceMinPer100mField.getText().trim());

				//Distance (kilos)
				result = (100 * factor1)/(factor2 * 1000);
                resultStr = formatter.format(result);
                calcDistanceKiloField.setText(resultStr);

                //Pace (kph)
                resultStr = formatter.format
                	((100 * 60)/(factor2 * 1000));
                calcPaceKphField.setText(resultStr);

                //Pace (mph)
                resultStr = formatter.format
                	((100 * 60 * .625)/(factor2 * 1000));
                calcPaceMphField.setText(resultStr );

                //Distance (mile)
                resultStr = formatter.format((factor1 * 100 * .625)/(factor2 * 1000));
                calcDistanceMilesField.setText(resultStr);

                //Min Per Mile
                calcPaceMinPerMileField.setText
                	(t.formatTime((factor2 * 1000)/(100 * .625)));

                //min per km
                calcPaceMinPerKmField.setText
                	(t.formatTime((factor2 * 1000)/100));

                //min per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor2 * 1000 * 100)/(100 * .625 * 1760)));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
	     	}
	     	else
	     	{
               JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        //kilometers -> pace
        else if (calcKilosCheck.isSelected() &&
	        calcPaceRadio.isSelected())
        {
	        if (DEBUG)
	        {
		        d.print("14");
	        }
            if(util.validDouble(calcDistanceKiloField.getText().trim(),6) &&
            t.validTime(calcTimeField.getText().trim()))
            {
                //Distance (kilos)
                factor1 = Double.parseDouble(calcDistanceKiloField.getText().trim());

                //Time
                factor2 = t.getConvertedTime(calcTimeField.getText().trim());

                //Pace (kph)
                result = factor1/(factor2/60.0);
                resultStr = formatter.format(result);
                calcPaceKphField.setText(resultStr);

                //Distance (miles)
                convertToMileDist = factor1*KM_TO_MILE;
                resultStr = formatter.format(convertToMileDist);
                calcDistanceMilesField.setText(resultStr);

                //Pace (miles)
                convertToMilePace = result*KM_TO_MILE;
                resultStr = formatter.format(convertToMilePace);
                calcPaceMphField.setText(resultStr );

                //Min Per Mile
                calcPaceMinPerMileField.setText(t.formatTime(60/convertToMilePace));

                //Min Per Km
                calcPaceMinPerKmField.setText(t.formatTime(60/result));

                //Min Per 100yds
                calcPaceMinPer100ydsField.setText
                	(t.formatTime((factor2 * 100)/(factor1 * .625 * 1760)));

                //Min Per 100m
                calcPaceMinPer100mField.setText
                	(t.formatTime((factor2 * 100)/(factor1 * 1000)));

                String sel = calcSelectionGroup.getSelection().getActionCommand();
            }//~t.validTime(calcTimeFiel...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Please correct all invalid entries.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }//~else if (calcKilosCheck....

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "Please correct all invalid entries.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//~public void calcFields()...

    /**
    Enables textfields in Calculator Panel.
    
    @param tf Array of JTextField components.
    @param b Boolean value set to True to make fields editable or False to
             make them uneditable.
             
    @result void
    */
    public void calcFieldsArrayEnabled(JTextField [] tf,boolean b)
    {
	    if (DEBUG)
	    {
		    d.print("calcFieldsArrayEnabled");
	    }

        Component c = null;
        for(int i=0; i<tf.length; i++)
        {
            c = tf[i];
            ((JTextField)c).setText("");
            ((JTextField)c).setEditable(b);
            ((JTextField)c).setEnabled(b);
        }
    }//~public void calcFieldsAr...

    /**
    Resets all JTextFields on the Calculator Panel.
    
    @param none
    
    @return void
    */
    public void calcResetFields()
    {
	    if (DEBUG)
	    {
		    d.print("calcResetFields");
	    }

        Component [] compArray = calcPanel.getComponents();
        Component c = null;
        for(int i=0; i<compArray.length; i++)
        {
            c = compArray[i];
            if (c instanceof JTextField)
            {
                ((JTextField)c).setText("");
                ((JTextField)c).setEnabled(false);
            }

            else if(c instanceof JLabel)
            	((JLabel)c).setEnabled(false);

            else if(c instanceof JRadioButton)
            {
            	((JRadioButton)c).setSelected(false);
            	((JRadioButton)c).setEnabled(false);
            }
            else if(c instanceof JCheckBox)
            {
            	((JCheckBox)c).setSelected(false);
            	((JCheckBox)c).setEnabled(false);
            }
        }//~for(int i=0; i<compArray...
    }

    /**
    Determines whether or not the first record in the log is valid.
    
    @param none
    
    @return Returns True or False.
    */
    public boolean hasValidFirstRecord()
    {
	    if (DEBUG)
	    {
		    d.print("hasValidFirstRecord");
	    }
        boolean validRecord = false;

	    try
	    {
	        Workout w = new Workout();
	        WorkoutTime t = new WorkoutTime();

	        w = (Workout)logbook.firstElement();
 	        if (DEBUG)
 	        {
	 	        d.print(w);
 	        }
	        WorkoutEvent [] events = w.getEvents();

	        if(w.getDate().getMonth() >= 0 &&
		        w.getDate().getDay() >= 0 &&
	    	    w.getDate().getYear() > 0 &&
 		        w.getTemp() >= 0 &&
	        	w.getWeather().length() != 0 &&
	        	(
	        		(events[0].getName() != "" &&
	    	    	events[0].getDistance() > 0 &&
	    	    	events[0].getTime().toString() != "") ||
	    	    	(events[1].getName() != "" &&
	    	    	events[1].getDistance() > 0 &&
		    	    events[1].getTime().toString() != "") ||
		    	    (events[2].getName() != "" &&
		    	    events[2].getDistance() > 0 &&
		    	    events[2].getTime().toString() != "")
		    	) &&
		        w.getPu() >= 0 &&
	        	w.getSu() >= 0)
	        {
	            validRecord = true;
	        }
        }
        catch(Exception e)
        {
	        d.print(e.toString() + " while checking first record!");
        }
        return validRecord;

    }

    /**
    Determines whether or not the first record in the maintenance log is valid.
    
    @param none
    
    @return True or False.
    */
    public boolean hasValidFirstMaintRecord()
    {
	    if (DEBUG)
	    {
		    d.print("hasValidFirstMaintRecord");
	    }

        boolean validRecord = false;
        Maintenance m = new Maintenance();
        m = (Maintenance)mntlogbook.firstElement();

        if(m.getMaintDate().length() > 0 &&
	        m.getMaintSummary().length() > 0)
        {
            validRecord = true;
        }

        return validRecord;
    }//~public boolean hasValidF...

    /**
    Clears JTextField and JTextArea components.
    
    @param c Array of components
    
    @param c Array of components.
    */
    public void resetComponents(Component [] c)
    {
	    if (DEBUG)
	    {
		    d.print("resetComponents");
	    }

        Component comp = null;
        for(int i=0; i<c.length; i++)
        {
            comp = c[i];
            if (comp instanceof JTextField)
            ((JTextField)comp).setText("");
            else if (comp instanceof JTextArea)
            ((JTextArea)comp).setText("");
        }
    }

    /**
    Makes Equipment Panel textfields editable.
    
    @param none
    
    @return void
    */
    public void editEquipPanel()
    {
	    if (DEBUG)
	    {
		    d.print("editEquipPanel");
	    }

        String selectedEvent = equipEventGroup.getSelection().getActionCommand();
        if (selectedEvent.equals("equiprun"))
        {
            Component [] compOn =
            {
                equipRunShoeSizeField,
                    equipRunShoeModelField
            };

            componentsEditable(compOn,true);
        }//~if (selectedEvent.equals...

        else if(selectedEvent.equals("equipbike"))
        {
            Component [] compOn =
            {
                equipBikeNameField,
                equipBikeModelField,
                equipBikeSNField,
                equipBikeFrameSizeField,
                equipBikeTireSizeField,
                equipBikeShoeSizeField,
                equipBikeShoeModelField,
                equipBikeSeatPostDiameterField
            };//~Component [] compOn =...

            componentsEditable(compOn,true);
        }//~else if(selectedEvent.eq...

        else if(selectedEvent.equals("equipswim"))
        {
            Component [] compOn =
            {
                equipSwimGoggleSizeField
            };

            componentsEditable(compOn,true);
        }

        else if(selectedEvent.equals("equipwalk"))
        {
            Component [] compOn =
            {
                equipWalkShoeSizeField,
                    equipWalkShoeModelField
            };

            componentsEditable(compOn,true);
        }//~else if(selectedEvent.eq...
    }//~public void editEquipPan...

    /**
    Saves current Equipment record.
    
    @param none
    
    @return void
    */
    public void saveEquipPanel()
    {
	    if (DEBUG)
	    {
		    d.print("saveEquipPanel");
	    }

        boolean panelIsEditable = false;

        Component [] comp = equipSpecPanel.getComponents();
        Component c = null;
        for(int i=0; i<comp.length; i++)
        {
            c = comp[i];
            if(c instanceof JTextField)
    	        if(((JTextField)c).isEditable())
        		    panelIsEditable = true;
        }

        if (panelIsEditable)
        {
            String selectedEvent = equipEventGroup.getSelection().getActionCommand();
            if (selectedEvent.equals("equiprun"))
            {
                Run r = new Run();
                r.setShoeSize(equipRunShoeSizeField.getText());
                r.setShoeModel(equipRunShoeModelField.getText());
                equiplogbook.set(0,r);
            }

            else if(selectedEvent.equals("equipbike"))
            {
                Bike b = new Bike();
                b.setBikeName(equipBikeNameField.getText());
                b.setBikeModel(equipBikeModelField.getText());
                b.setBikeSN(equipBikeSNField.getText());
                b.setBikeFrameSize(equipBikeFrameSizeField.getText());
                b.setBikeTireSize(equipBikeTireSizeField.getText());
                b.setShoeSize(equipBikeShoeSizeField.getText());
                b.setShoeModel(equipBikeShoeModelField.getText());
                b.setSeatPostDiameter(equipBikeSeatPostDiameterField.getText());
                equiplogbook.set(1,b);
            }//~else if(selectedEvent.eq...

            else if(selectedEvent.equals("equipswim"))
            {
                Swim s = new Swim();
                s.setGoggleSize(equipSwimGoggleSizeField.getText());
                equiplogbook.set(2,s);
            }

            else if(selectedEvent.equals("equipwalk"))
            {
                Walk w = new Walk();
                w.setShoeSize(equipWalkShoeSizeField.getText());
                w.setShoeModel(equipWalkShoeModelField.getText());
                equiplogbook.set(3,w);
            }

            componentsEditable(comp,false);
        }//~if (panelIsEditable)...

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "This record must be in the Edit mode first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    Cancels all modifications to the current Equipment record.
    
    @param none
    
    @return void
    */
    public void cancelEquipPanel()
    {
	    if (DEBUG)
	    {
		    d.print("cancelEquipPanel");
	    }

        boolean panelIsEditable = false;

        Component [] comp = equipSpecPanel.getComponents();
        Component c = null;
        for(int i=0; i<comp.length; i++)
        {
            c = comp[i];
            if(c instanceof JTextField)
	            if(((JTextField)c).isEditable())
    		        panelIsEditable = true;
        }

        if (panelIsEditable)
        {
            String selectedEvent = equipEventGroup.getSelection().getActionCommand();
            Component [] compOff = equipSpecPanel.getComponents();
            componentsEditable(compOff,false);
            populateEquipWindow(selectedEvent);
        }

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "This record must be in the Edit mode first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    Clears all JTextFields on the Equipment Panel.
    
    @param none
    
    @return void.
    */
    public void clearEquipPanel()
    {
	    if (DEBUG)
	    {
		    d.print("clearEquipPanel");
	    }

        boolean panelIsEditable = false;

        Component [] comp = equipSpecPanel.getComponents();
        Component c = null;
        for(int i=0; i<comp.length; i++)
        {
            c = comp[i];
            if(c instanceof JTextField)
            	if(((JTextField)c).isEditable())
            		panelIsEditable = true;
        }

        if (panelIsEditable)
        {
            String selectedEvent = equipEventGroup.getSelection().getActionCommand();
            Component [] compOff = equipSpecPanel.getComponents();
            resetComponents(compOff);
        }

        else
        {
            JOptionPane.showMessageDialog(
                this,
                "This record must be in the Edit mode first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    Populates Equipment Panel.
    
    @param event Name of event to use to populate the Equipment form
    
    @return void
    */
    public void populateEquipWindow(String event)
    {
	    if (DEBUG)
	    {
		    d.print("populateEquipWindow");
	    }

        if (event.equals("equiprun"))
        {
            Run r = new Run();
            r = (Run)equiplogbook.elementAt(0);
            equipRunShoeModelField.setText(r.getShoeModel());
            equipRunShoeSizeField.setText(r.getShoeSize());
        }

        else if (event.equals("equipbike"))
        {
            Bike b = new Bike();
            b = (Bike)equiplogbook.elementAt(1);
            equipBikeShoeModelField.setText(b.getShoeModel());
            equipBikeShoeSizeField.setText(b.getShoeSize());
            equipBikeFrameSizeField.setText(b.getBikeFrameSize());
            equipBikeSeatPostDiameterField.setText(b.getSeatPostDiameter());
            equipBikeModelField.setText(b.getBikeModel());
            equipBikeSNField.setText(b.getBikeSN());
            equipBikeTireSizeField.setText(b.getBikeTireSize());
            equipBikeNameField.setText(b.getBikeName());
        }//~else if (event.equals("e...

        else if (event.equals("equipswim"))
        {
            Swim s = new Swim();
            s = (Swim)equiplogbook.elementAt(2);
            equipSwimGoggleSizeField.setText(s.getGoggleSize());
        }

        else if (event.equals("equipwalk"))
        {
            Walk w = new Walk();
            w = (Walk)equiplogbook.elementAt(3);
            equipWalkShoeModelField.setText(w.getShoeModel());
            equipWalkShoeSizeField.setText(w.getShoeSize());
        }
    }//~public void populateEqui...

    /**
    Creates a new Vector database.
    
    @param none
    
    @return void
    */
    public void createNewDatabase()
    {
	    if (DEBUG)
	    {
		    d.print("createNewDatabase");
	    }

        logbook = new Vector(5);

        //create mntlogbook with default values
        mntlogbook = new Vector(5);
        Maintenance m = new Maintenance();
        mntlogbook.add(m);

        //create equiplogbook with default values
        equiplogbook = new Vector(EQUIPRECORDMAX);

        //These must be added in order: run,bike,swim,walk
        Run r = new Run();
        equiplogbook.add(r);
        Bike b = new Bike();
        equiplogbook.add(b);
        Swim s = new Swim();
        equiplogbook.add(s);
        Walk w = new Walk();
        equiplogbook.add(w);
    }//~public void createNewDat...

    /**
    Gets event.
    
    @param none
    
    @return Name of event.
    */
    public String getEvent()
    {
	    if (DEBUG)
	    {
		    d.print("getEvent");
	    }

    	return (statsEventGroup.getSelection().getActionCommand());
    }

    /**
    Facilitates showing Options Panel.
    
    @param none
    
    @return void
    */
    public void showOptions(Component parent)
    {
 	    if (DEBUG)
 	    {
	 	    d.print("showOptions");
 	    }

       	options = new UserConfig(optionslogbook);
       	
        if (options.getName().equals("optionsWindow") && !options.isOpen())
        {
	        optionsWindowOpened = true;
	        Point parentLoc = parent.getLocationOnScreen();
	        int x_loc = (parentLoc.x - 100);
	        int y_loc = (parentLoc.y - 50);
	        options.setLocation(x_loc,y_loc);
	        options.setVisible(true);
	        options.pack();
        }
        else
        {
            JOptionPane.showMessageDialog(
                this,
                "Please close any existing Options windows\n" +
                "before opening a new window.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    Facilitates showing graph of a log.
    
    @param event Name of event to be graphed
    @param leftAxis Name of left axis
    @param rightAxis Name of right axis
    
    @return void
    */
    public void showGraph(String event, Component parent, String leftAxis, String rightAxis)
    {
	    if (DEBUG)
	    {
		    d.print("showGraph");
	    }

       	graphLog = new Graph(logbook,event,parent,leftAxis,rightAxis);
        if (graphLog.getName().equals("graphWindow") && !graphLog.isOpen())
        {
	        Point parentLoc = parent.getLocationOnScreen();
	        int x_loc = (parentLoc.x - 150);
	        int y_loc = (parentLoc.y - 50);
	        graphLog.setLocation(x_loc,y_loc);
	        graphLog.setVisible(true);
	        graphLog.pack();
        }
        else
        {
            JOptionPane.showMessageDialog(
                this,
                "Please close any existing Graph windows\n" +
                "before opening a new window.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    Facilitates showing the Help dialog.
    
    @param none
    
    @return void
    */
    public void showHelp(Component parent)
    {
	    help = new Help();
	    Point parentLoc = parent.getLocationOnScreen();
        int x_loc = (parentLoc.x + 200);
        int y_loc = (parentLoc.y - 50);
        help.setLocation(x_loc,y_loc);
        help.setVisible(true);
    }

    /**
    Makes navigation buttons selectable or not.
    
    @param on True to turn on buttons or False to turn off buttons
    
    @return void
    */
    public void navButtonsOn(boolean on)
    {
	    if (DEBUG)
	    {
		    d.print("navButtonsOn");
	    }

        Component [] compOn =
        {
 	    	beginButton,
    	    prevButton,
        	nextButton,
        	endButton
        };//~Component [] compOn =...

	    if(on)
            componentsEnabled(compOn,on);
        else
        	componentsEnabled(compOn,on);
    }

    /**
    Makes action buttons selectable or not.
    
    @param on True to turn buttons on or False to turn them off
    
    @return void
    */
    public void actionButtonsOn(boolean on)
    {
	    if (DEBUG)
	    {
		    d.print("actionButtonsOn");
	    }

        Component [] compOn =
        {
        	findButton,
        	newButton,
        	deleteButton,
        	editButton,
        	editButton,
        	saveButton,
        	cancelButton,
        	clearButton,
        };//~Component [] compOn =...

        if(on)
	        componentsEnabled(compOn,on);
	    else
	    	componentsEnabled(compOn,on);
    }

    /**
    Resets current record to default values.
    
    @param none
    
    @return void
    */
    public void resetForm()
    {
	    if (DEBUG)
	    {
		    d.print("resetForm");
	    }

        resetFields();
        currentFileName = "";
        //enable
        Component [] compOn =
        {
            openItem,
            newItem,
            exitItem
        };

        componentsEnabled(compOn,true);
        //disable
        Component [] compOff =
        {
	        printItem,
            saveItem,
            saveAsItem,
            backupItem,
            closeItem,
            removeallItem,
            optionsItem,
            findButton,
            newButton,
            deleteButton,
            editButton,
            saveButton,
            cancelButton,
            clearButton,
            beginButton,
            prevButton,
            nextButton,
            endButton
        };//~Component [] compOff =...

        componentsEnabled(compOff,false);

        //make all textfields uneditable
        Component [] tfOff =
        {
            monthField,
            dayField,
            yearField,
            statusField,
            puField,
            suField
        };//~Component [] tfOff =...

        tempComboBox.setEnabled(false);
        weatherComboBox.setEnabled(false);
        weightComboBox.setEnabled(false);

        //enable all navigation and action buttons
        JButton [] nab =
        {
            beginButton,prevButton,
                nextButton,endButton
        };

        componentsEnabled(nab,false);

        componentsEditable(tfOff,false);

        //disable statistics, maintenance, equipment panels
        tabbedPane.setEnabledAt(2,false);
        tabbedPane.setEnabledAt(3,false);
        tabbedPane.setEnabledAt(4,false);

        //show aerobic panel
        tabbedPane.setSelectedComponent(mainPanel);

        //set currentFileName to null
        currentFileName = "";
		setTitle("T.E.W.L");

    }//~public void resetForm()...

    /**
    Saves current file to disk.
    
    @param parent Name of parent form
    
    @return True to indicate that save was successful.
    */
    public boolean saveCurrentFile(Component parent)
    {
	    if (DEBUG)
	    {
		    d.print("saveCurrentFile");
	    }

        boolean fileSaved = false;
        try
        {
            if(hasValidFirstRecord())
            {
                fileSaved = writeFile(currentFileName);
                if(!fileSaved)
                {
                    JOptionPane.showMessageDialog(
                        this,
                        "The file was not written to the hard drive.\n" +
                        "Please validate all fields before saving file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }

                else
                {
                    origlogbook = (Vector)logbook.clone();
                    origmntlogbook = (Vector)mntlogbook.clone();
                    origequiplogbook = (Vector)equiplogbook.clone();
                    origoptionslogbook = (Vector)optionslogbook.clone();
                }
            }//~if(hasValidFirstRecord()...

            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "The database has no valid records.\n" +
                    "Please create a new database and save\n" +
                    "a new record before saving the file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }//~else...
        }//~try...

        catch(Exception e)
        {
            fileSaved = false;
        }

        return fileSaved;
    }//~public boolean saveCurre...

    /**
    Saves file under a different name.
    
    @param parent Name of parent form.
    
    @return True indicating that the save was successful.
    */
    public boolean saveFile(Component parent)
    {
	    if (DEBUG)
	    {
		    d.print("saveFile");
	    }

        boolean fileSaved = false;
        try
        {
            if(!logbook.isEmpty() && hasValidFirstRecord())
            {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new DataFileFilter());
				int result = fc.showSaveDialog( this );
				if ( result == JFileChooser.APPROVE_OPTION )
				{
					String str1 = fc.getCurrentDirectory() +
						System.getProperty("file.separator") + fc.getSelectedFile().getName() ;
 					if (DEBUG)
 					{
	 					d.print("str1: " + str1);
 					}
					currentFileName = str1;
					File f = new File( str1 );
					fc.setSelectedFile(f);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					if ( f.exists() )
					{
// 						int answer = JOptionPane.showConfirmDialog
// 							(null,"Do you want to save the changes?",
// 							"Confirm Dialog",
// 							JOptionPane.YES_NO_OPTION, 
// 							JOptionPane.QUESTION_MESSAGE,null);

// 						if(answer == 1)
                        	fileSaved = writeFile(currentFileName);
                    }
                    fileSaved = writeFile(currentFileName);
				}
                if(!fileSaved)
                {
                    currentFileName = "";
                    fileSaved = false;
                    JOptionPane.showMessageDialog(
                        this,
                        "Please select a valid file name.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }//~else...
            }//~if(!logbook.isEmpty() &&...

            else
            {
                currentFileName = "";
                fileSaved = false;
                JOptionPane.showMessageDialog(
                    this,
                    "The database has no valid records.\n" +
                    "Please create a new database and save\n" +
                    "a new record before saving the file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }//~else...
        }//~try...

        catch(Exception e)
        {
            currentFileName = "";
            fileSaved = false;
        }

        return fileSaved;
    }//~public boolean saveFile(...

    /**
    Deletes any nonprinting characters before save file to disk.
    
    @param file Name of file
    
    @return void
    */
    public void deleteCRs(String file) throws Exception
    {
	    if (DEBUG)
	    {
		    d.print("deleteCRs");
	    }

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            FileWriter out = new FileWriter("tmp.dat");
            int i=0;
            while(i!=-1)
            {
                i=in.read();
                if('\r'==(char)i || '\n'==(char)i || '\f'==(char)i || '?' ==(char)i )
                {
                    continue;
                }

                out.write(i);
            }//~while(i!=-1)...

            in.close();
            out.close();
            int j=0;
            BufferedReader in2 =
            new BufferedReader(new FileReader("tmp.dat"));
            FileWriter out2 = new FileWriter(file);
            while(j!=-1)
            {
                j=in2.read();
                out2.write(j);
            }

            in2.close();
            out2.close();

        }//~try...

        catch(Exception e)
        {
            throw new Exception("Error in processing file.");
        }
    }//~public void deleteCRs(St...

    /**
    Determines whether or not two vectors are the same.
    
    @param origlist Original list
    @param newlist Modified list
    
    @return True if the vectors are the same or false if they are not
    */
    public boolean identical(Vector origlist, Vector newlist)
    {
	    if (DEBUG)
	    {
		    d.print("identical");
	    }
		return (origlist.equals(newlist));
    }//~public boolean identical...

/*
    public void printEquipment(Equipment eq)
    {

    }

    public void printWorkout(Workout w, int record)
    {
	    p = new PrintPanel(w,record);
        PrinterJob printJob = PrinterJob.getPrinterJob();
	    PageFormat pageFormat = printJob.defaultPage();
        printJob.setPrintable(p,pageFormat);
        if(printJob.printDialog())
        {
	        try
	        {
		        printJob.print();
	       	}
	       	catch(PrinterException exception)
	       	{
		       	JOptionPane.showMessageDialog(this,exception);
	       	}
       	}
    }

    public void printMaintenanceEntry(Maintenance m)
    {

    }

    public void printMaintenanceLog(Vector mlog)
    {

    }

    public void printWorkoutLog(Vector wlog)
    {

    }
*/

	/**
	Clears all logs
	
	@param none
	
	@return void
	*/
	public void clearAllLogs()
	{
		if(!origlogbook.isEmpty())
        	origlogbook.clear();

        if(!logbook.isEmpty())
        	logbook.clear();

        if(!origmntlogbook.isEmpty())
            origmntlogbook.clear();

        if(!mntlogbook.isEmpty())
        	mntlogbook.clear();

        if(!origequiplogbook.isEmpty())
        	origequiplogbook.clear();

        if(!equiplogbook.isEmpty())
            equiplogbook.clear();

        if(!origoptionslogbook.isEmpty())
        	origoptionslogbook.clear();

        if(!optionslogbook.isEmpty())
        	optionslogbook.clear();
	}

	/**
	Sort log vector
	
	@param v Name of vector to be sorted
	
	@return Sorted vector
	*/
 	public Vector sort(Vector v)
	{
		Collections.sort(v,
			new Comparator()
			{
				public int compare(Object a, Object b)
				{
					Workout w1 = (Workout)a;
					Workout w2 = (Workout)b;
					Calendar date1 = w1.getDate().getDate();
					Calendar date2 = w2.getDate().getDate();
					if (date1.before(date2))
						return -1;
					if (date1.equals(date2))
						return 0;
					return 1;
				}
			});
			return v;
	}

	/**
	Reads data file from hard disk and into their appropriate Vectors.
	
	@param parent Name of parent form
	
	@return True if the file was loaded successfully or False if there was a problem
	*/
    public boolean loadFile(Component parent) throws Exception
    {
	    if (DEBUG)
	    {
		    d.print("loadFile");
	    }

        boolean fileValid = false;
        try
        {
	        String token = "";
            JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("."));
            fc.setFileFilter(new DataFileFilter());
            int result = fc.showOpenDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION)
            {
				currentFileName = fc.getCurrentDirectory() +
					System.getProperty("file.separator") + fc.getSelectedFile().getName() ;
                if (DEBUG)
                {
					d.print("currentFileName: " + currentFileName);
				}
                repaint();
                File fin = new File(currentFileName);
                if (fin.exists())
                {
                    try
                    {
	                    //open file and remove all newlines
    	                deleteCRs(currentFileName);
        	            BufferedReader br = new BufferedReader
            	        (new FileReader(currentFileName));
                	    String s = br.readLine();
                    	StringTokenizer t = new StringTokenizer(s,"|");

                    	int logRecords = Integer.parseInt(t.nextToken());

    	                if(logRecords > 0)
        	            {
            	            ProgressMonitor progressBar = new ProgressMonitor(logRecords);
                    	    progressBar.setVisible(true);
                        	Vector log = new Vector(logRecords,5);
                        	
	                        for (int i=0; i<logRecords; i++)
    	                    {
        	                    progressBar.update(i+1);
            	                Workout in = new Workout();
                	            in.readData(t);
                	            WorkoutEvent [] e = in.getEvents();
                    	        log.add(in);
                        	}

	                        progressBar.setVisible(false);
    	                    int maintRecords = Integer.parseInt(t.nextToken());
        	                if(maintRecords > 0)
            	            {
                	            for (int i=0; i<maintRecords; i++)
                    	        {
                        	        Maintenance mentry = new Maintenance();
                            	    mentry.readData(t);
                                	mntlogbook.add(mentry);
	                            }
    	                    }

	                        for(int i=0; i<=EQUIPRECORDMAX - 1; i++)
    	                    {
        	                    token = t.nextToken();
            	                if ("equiprun".equals(token))
                	            {
                    	            Run rentry = new Run();
                        	        rentry.readData(t);
                                	equiplogbook.add(rentry);
	                                continue;
    	                        }

        	                    else if ("equipbike".equals(token))
            	                {
                	                Bike bentry = new Bike();
                    	            bentry.readData(t);
                            	    equiplogbook.add(bentry);
                                	continue;
	                            }

	                            else if ("equipswim".equals(token))
    	                        {
        	                        Swim sentry = new Swim();
            	                    sentry.readData(t);
                    	            equiplogbook.add(sentry);
                        	        continue;
                            	}

	                            else if ("equipwalk".equals(token))
    	                        {
        	                        Walk wentry = new Walk();
            	                    wentry.readData(t);
                    	            equiplogbook.add(wentry);
                        	        continue;
                            	}
	                        }//~for(int i=0; i<=EQUIPREC...

       	                    token = t.nextToken();
	                        if ("routeoptions".equals(token))
	                        {
		                        for (int i=0; i<ROUTEOPTIONSMAX; i++)
		                        {
			                        Route route = new Route();
			                        route.readData(t);
			                        optionslogbook.add(route);
		                        }
	                        }

       	                    token = t.nextToken();
	                        if("exerciseoptions".equals(token))
	                        {
		                        for (int i=0; i<EXERCISEOPTIONSMAX; i++)
		                        {
			                        Exercise exercise = new Exercise();
			                        exercise.readData(t);
			                        optionslogbook.add(exercise);
		                        }
	                        }

    	                    //sort main logbook
        	                logbook = (Vector)sort(log).clone();
        	                
        	                //clear list used to read in file.
        	                log.clear();

                    	    fileValid = true;

                        	//save original logs
	                        origlogbook = (Vector)logbook.clone();
    	                    origmntlogbook = (Vector)mntlogbook.clone();
        	                origequiplogbook = (Vector)equiplogbook.clone();
        	                origoptionslogbook = (Vector)optionslogbook.clone();
            	        }//~if(logRecords > 0)...
						setTitle("T.E.W.L - " + currentFileName);
                    	br.close();
					}

	                catch (Exception e)
					{
       	                fileValid = false;
           	            currentFileName = "";
						setTitle("T.E.W.L - " + currentFileName);
               	        throw new Exception("File contains invalid data.");
                   	}

				}//~if (fin.exists())...
        	    else if (result == JFileChooser.CANCEL_OPTION)
				{

            	}//~if (result == JFileChoos...
				else
				{
					fileValid = false;
					currentFileName = "";
					setTitle("T.E.W.L - " + currentFileName);
					throw new Exception("File does not exist.");
				}
			}
        }//~try...
        catch (Exception e)
        {
	        throw e;
        }

        return fileValid;
    }//~public boolean loadFile(...

    /**
    Writes each vector to one file.
    
    @param fileName Name of file
    
    @return True if save was successful or False if it failed
    */
    public boolean writeFile(String fileName) throws Exception
    {
	    if (DEBUG)
	    {
		    d.print("writeFile");
	    }

        boolean fileSaved = false;

        try
        {
            PrintStream fout = new PrintStream(new FileOutputStream(fileName));

            fout.print(logbook.size() + "|");

            Workout w = null;
            for(int i=0; i<logbook.size(); i++)
            {
                w = (Workout)logbook.elementAt(i);
                w.printData(fout);
            }

            if(mntlogbook.isEmpty())
            {
                fout.print("0|*|*|");
            }

            else
            {
                fout.print(mntlogbook.size() + "|");

                Maintenance m = null;
                for(int i=0; i<mntlogbook.size(); i++)
                {
                    m = (Maintenance)mntlogbook.elementAt(i);
                    m.printData(fout);
                }
            }//~else...

            if(equiplogbook.isEmpty())
            {
                fout.print("equiprun|*|*|");
                fout.print("equipbike|*|*|*|*|*|*|*|");
                fout.print("equipswim|*|");
                fout.print("equipwalk|*|*|");
            }
            else
            {
                Equipment e = new Equipment();
                for(int i=0; i<=3; i++)
                {
                    e = (Equipment)equiplogbook.elementAt(i);
                    if (e instanceof Run)
                    {
                        ((Run)e).print(fout);
                    }

                    else if (e instanceof Bike)
                    {
                        ((Bike)e).print(fout);
                    }

                    else if (e instanceof Swim)
                    {
                        ((Swim)e).print(fout);
                    }

                    else if (e instanceof Walk)
                    {
                        ((Walk)e).print(fout);
                    }
                }//~for(int i=0; i<=3; i++)...
            }//~else...

            if(optionslogbook.isEmpty())
            {
	            fout.print("routeoptions|*|*|*|*|*|*|*|*|*|*|");
	            fout.print("exerciseoptions|*|*|*|*|*|*|*|*|*|*|");
            }
            else
            {
	            fout.print("routeoptions|");
	            Options options = new Options();
	         	for(int i=0; i<ROUTEOPTIONSMAX; i++)
	         	{
		            options = (Options)optionslogbook.elementAt(i);
		            if(options instanceof Route)
		            {
			            ((Route)options).printData(fout);
		            }
	            }

				fout.print("exerciseoptions|");
				//start i at 10 because the exercise options range in index from
				// 10 to 19 in the optionslogbook
	            for (int i=10; i<EXERCISEOPTIONSMAX + 10; i++)
	            {
		            options = (Options)optionslogbook.elementAt(i);
		            if(options instanceof Exercise)
		            {
			            ((Exercise)options).printData(fout);
		            }
	            }
            }

            fout.close();
			setTitle("T.E.W.L - " + fileName);
            fileSaved = true;
        }//~try...

        catch(Exception e)
        {
            fileSaved = false;
        }

        return fileSaved;
    }//~public boolean writeFile...

    /**
    Performs actions selected by user.
    
    @param evt Name of action command triggered by the user
    
    @return void
    */
    public void actionPerformed(ActionEvent evt)
    {
		try
		{
		    String source = evt.getActionCommand();
		    
		    if (DEBUG)
		    {
			    d.print("logbook size = " + logbook.size());
				d.print("origlogbook = " + origlogbook.size());
				d.print("mntlogbook = " + mntlogbook.size());
				d.print("origmntlogbook = " + origmntlogbook.size());
				d.print("equiplogbook = " + equiplogbook.size());
				d.print("origequiplogbook = " + origequiplogbook.size());
				d.print("optionslogbook = " + optionslogbook.size());
				d.print("origoptionslogbook = " + origoptionslogbook.size());
			}

		    if(DEBUG)
		    {
			    d.print("Action = " + source);
		    }

	        if(logbook != null)
	        {

	            if (source.equals("find")&&
	    	        evt.getSource() instanceof JButton)
	    	    {
	        	    find();
        	    }
	            else if (source.equals("edit")&&
		            evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
		                editMaintRecord();
	                else if (equipPanel.isVisible())
	    	            editEquipPanel();
	                else
	        	        edit();
	            }

	            else if (source.equals("cancel")&&
	            	evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
		                cancelMaintRecord();
	                else if (equipPanel.isVisible())
	    	            cancelEquipPanel();
	                else
	        	        cancel();
	            }

	            else if (source.equals("new")&&
	            	evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
		                createMaintRecord();
	                else
	                {
	    	            create();
    	            }
	            }

	            else if (source.equals("save") &&
	        	    evt.getSource() instanceof JButton)
	            {
	                if(maintenancePanel.isVisible())
		                saveMaintRecord();
	                else if (equipPanel.isVisible())
	    	            saveEquipPanel();
	                else
	                	save();
	            }

	            else if (source.equals("delete")&&
	            	evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
		                deleteMaintRecord();
	                else
	    	            delete();
	            }

	            else if (source.equals("clear")&&
	        	    evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
	            	    clearMaintRecord();
	                else if (equipPanel.isVisible())
		                clearEquipPanel();
	                else
	    	            clear();
	            }

	            else if (source.equals("beginning")&&
	        	    evt.getSource() instanceof JButton)
	        	{
	            	getFirst();
        	    }
	            else if (source.equals("previous")&&
	            	evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
	                	getPrevMaintRecord();
	                else
	                	getPrev();
	            }

	            else if (source.equals("next")&&
		            evt.getSource() instanceof JButton)
	            {
	                if (maintenancePanel.isVisible())
	    	            getNextMaintRecord();
	                else
	        	        getNext();
	            }

	            else if (source.equals("end")&&
		            evt.getSource() instanceof JButton)
		        {
	    	        getLast();
    	        }

	            else if (source.equals("equiprun") &&
	            evt.getSource() instanceof JRadioButton)
	            {
	                equipSpecPanel.removeAll();
	                GridBagConstraints gbc = new GridBagConstraints();
	                gbc.anchor = GridBagConstraints.EAST;
	                gbc.insets = new Insets(2,2,2,2);
	                equipRunShoeSizeLabel = new JLabel("Shoe size: ");
	                equipRunShoeSizeLabel.setFont(f);
	                equipRunShoeModelLabel = new JLabel("Shoe model: ");
	                equipRunShoeModelLabel.setFont(f);
	                add(equipSpecPanel,equipRunShoeSizeLabel,gbc,1,1,1,1);
	                add(equipSpecPanel,equipRunShoeModelLabel,gbc,1,2,1,1);
	                gbc.anchor = GridBagConstraints.WEST;
	                equipRunShoeSizeField = new JTextField(15);
	                equipRunShoeModelField = new JTextField(15);
	                add(equipSpecPanel,equipRunShoeSizeField,gbc,2,1,1,1);
	                add(equipSpecPanel,equipRunShoeModelField,gbc,2,2,1,1);
	                repaint();

	                //make all textfields uneditable
	                Component [] comp =
	                {
	                    equipRunShoeSizeField,
	                        equipRunShoeModelField
	                };

	                componentsEditable(comp,false);

	                populateEquipWindow("equiprun");

	            }//~evt.getSource() instance...

	            else if (source.equals("equipbike") &&
		            evt.getSource() instanceof JRadioButton)
	            {
	                equipSpecPanel.removeAll();
	                GridBagConstraints gbc = new GridBagConstraints();
	                gbc.anchor = GridBagConstraints.EAST;
	                gbc.insets = new Insets(2,2,2,2);
	                equipBikeNameLabel = new JLabel("Name: ");
	                equipBikeNameLabel.setFont(f);
	                equipBikeModelLabel = new JLabel("Model: ");
	                equipBikeModelLabel.setFont(f);
	                equipBikeSNLabel = new JLabel("Serial #: ");
	                equipBikeSNLabel.setFont(f);
	                equipBikeFrameSizeLabel = new JLabel("Frame size: ");
	                equipBikeFrameSizeLabel.setFont(f);
	                equipBikeSeatStemDiameterLabel = new JLabel("Seat Post (mm): ");
	                equipBikeSeatStemDiameterLabel.setFont(f);
	                equipBikeTireSizeLabel = new JLabel("Tire size: ");
	                equipBikeTireSizeLabel.setFont(f);
	                equipBikeShoeSizeLabel = new JLabel("Shoe Size: ");
	                equipBikeShoeSizeLabel.setFont(f);
	                equipBikeShoeModelLabel = new JLabel("Shoe model: ");
	                equipBikeShoeModelLabel.setFont(f);

	                add(equipSpecPanel,equipBikeNameLabel,gbc,1,1,1,1);
	                add(equipSpecPanel,equipBikeModelLabel,gbc,1,2,1,1);
	                add(equipSpecPanel,equipBikeSNLabel,gbc,1,3,1,1);
	                add(equipSpecPanel,equipBikeFrameSizeLabel,gbc,1,4,1,1);
	                add(equipSpecPanel,equipBikeSeatStemDiameterLabel,gbc,1,5,1,1);
	                add(equipSpecPanel,equipBikeTireSizeLabel,gbc,1,6,1,1);
	                add(equipSpecPanel,equipBikeShoeSizeLabel,gbc,1,7,1,1);
	                add(equipSpecPanel,equipBikeShoeModelLabel,gbc,1,8,1,1);

	                equipBikeNameField = new JTextField(15);
	                equipBikeModelField = new JTextField(15);
	                equipBikeSNField = new JTextField(15);
	                equipBikeFrameSizeField = new JTextField(15);
	                equipBikeSeatPostDiameterField = new JTextField(15);
	                equipBikeTireSizeField = new JTextField(15);
	                equipBikeShoeSizeField = new JTextField(15);
	                equipBikeShoeModelField = new JTextField(20);

	                gbc.anchor = GridBagConstraints.WEST;
	                add(equipSpecPanel,equipBikeNameField,gbc,2,1,1,1);
	                add(equipSpecPanel,equipBikeModelField,gbc,2,2,1,1);
	                add(equipSpecPanel,equipBikeSNField,gbc,2,3,1,1);
	                add(equipSpecPanel,equipBikeFrameSizeField,gbc,2,4,1,1);
	                add(equipSpecPanel,equipBikeSeatPostDiameterField,gbc,2,5,1,1);
	                add(equipSpecPanel,equipBikeTireSizeField,gbc,2,6,1,1);
	                add(equipSpecPanel,equipBikeShoeSizeField,gbc,2,7,1,1);
	                add(equipSpecPanel,equipBikeShoeModelField,gbc,2,8,1,1);
	                repaint();

	                //make textfields uneditable
	                Component [] comp =
	                {
	                    equipBikeNameField,
	                        equipBikeModelField,
	                        equipBikeSNField,
	                        equipBikeFrameSizeField,
	                        equipBikeTireSizeField,
	                        equipBikeShoeSizeField,
	                        equipBikeShoeModelField,
	                        equipBikeSeatPostDiameterField
	                };//~Component [] comp =...

	                componentsEditable(comp,false);

	                populateEquipWindow("equipbike");

	            }//~evt.getSource() instance...

	            else if (source.equals("equipswim") &&
	    	        evt.getSource() instanceof JRadioButton)
	            {
	                equipSpecPanel.removeAll();
	                GridBagConstraints gbc = new GridBagConstraints();
	                gbc.anchor = GridBagConstraints.EAST;
	                gbc.insets = new Insets(2,2,2,2);
	                equipSwimGoggleSizeLabel = new JLabel("Goggle size: ");
	                equipSwimGoggleSizeLabel.setFont(f);
	                add(equipSpecPanel,equipSwimGoggleSizeLabel,gbc,1,1,1,1);
	                gbc.anchor = GridBagConstraints.WEST;
	                equipSwimGoggleSizeField = new JTextField(15);
	                add(equipSpecPanel,equipSwimGoggleSizeField,gbc,2,1,1,1);
	                repaint();

	                //make textfields uneditable
	                Component [] comp =
	                {
	                    equipSwimGoggleSizeField
	                };

	                componentsEditable(comp,false);

	                populateEquipWindow("equipswim");
	            }//~evt.getSource() instance...

	            else if (source.equals("equipwalk") &&
	        	    evt.getSource() instanceof JRadioButton)
	            {
	                equipSpecPanel.removeAll();
	                GridBagConstraints gbc = new GridBagConstraints();
	                gbc.anchor = GridBagConstraints.EAST;
	                gbc.insets = new Insets(2,2,2,2);
	                equipWalkShoeSizeLabel = new JLabel("Shoe size: ");
	                equipWalkShoeSizeLabel.setFont(f);
	                equipWalkShoeModelLabel = new JLabel("Shoe model: ");
	                equipWalkShoeModelLabel.setFont(f);
	                add(equipSpecPanel,equipWalkShoeSizeLabel,gbc,1,1,1,1);
	                add(equipSpecPanel,equipWalkShoeModelLabel,gbc,1,2,1,1);
	                gbc.anchor = GridBagConstraints.WEST;
	                equipWalkShoeSizeField = new JTextField(15);
	                equipWalkShoeModelField = new JTextField(15);
	                add(equipSpecPanel,equipWalkShoeSizeField,gbc,2,1,1,1);
	                add(equipSpecPanel,equipWalkShoeModelField,gbc,2,2,1,1);
	                repaint();

	                //make textfields uneditable
	                Component [] comp =
	                {
	                    equipWalkShoeSizeField,
	                        equipWalkShoeModelField
	                };

	                componentsEditable(comp,false);

	                populateEquipWindow("equipwalk");
	            }//~evt.getSource() instance...

	            else if(source.equals("showgraph") &&
	            	evt.getSource() instanceof JButton)
	            {
		            String leftAxisOption = "";
		            String rightAxisOption = "";
		            String event = statsEventGroup.getSelection().getActionCommand();

		            try
		            {
			            leftAxisOption = (String) statsLeftAxisOptionComboBox.getSelectedItem();
			            rightAxisOption = (String) statsRightAxisOptionComboBox.getSelectedItem();

			            showGraph(getEvent(),this, leftAxisOption, rightAxisOption);
		            }
		            catch(Exception e)
		            {
		                JOptionPane.showMessageDialog(
		                    this,
		                    "Please select an event before displaying the graph.",
		                    "Error",
		                    JOptionPane.ERROR_MESSAGE);
		            }
		        }
	        }//~if(logbook != null)...

	        if (source.equals("openlog") &&
		        evt.getSource() instanceof JButton)
	        {
	            if(!mntlogbook.isEmpty() && hasValidFirstMaintRecord())
	            {
	                Maintenance m = new Maintenance();
	                m = (Maintenance)mntlogbook.lastElement();
	                maintDateField.setText(m.getMaintDate());
	                maintWorkTextArea.setText(m.getMaintSummary());
                	maintOpenButton.setEnabled(false);
	            }
	        }//~evt.getSource() instance...

	        else if (source.equals("open")&&
	    	    evt.getSource() instanceof JMenuItem)
	        {
	            boolean fileExists = false;
	            try
	            {
	                fileExists = loadFile(this);
	                if(fileExists && hasValidFirstRecord())
	                {
	                    populateWindow((Workout)logbook.lastElement());

	                    //enable
	                    Component [] compOn =
	                    {
	                        printItem,
		                    saveItem,
	                            saveAsItem,
	                            exitItem,
	                            backupItem,
	                            closeItem,
	                            removeallItem,
	                            optionsItem,
	                            findButton,
	                            newButton,
	                            deleteButton,
	                            editButton,
	                            saveButton,
	                            cancelButton,
	                            clearButton,
	                            beginButton,
	                            prevButton,
	                            nextButton,
	                            endButton
	                    };//~Component [] compOn =...

	                    componentsEnabled(compOn,true);
	                    //disable
	                    Component [] compOff =
	                    {
	                        openItem,
	                            newItem
	                    };

	                    componentsEnabled(compOff,false);

	                    //enable statistics,maintenance,equipment panels
	                    tabbedPane.setEnabledAt(2,true);
	                    tabbedPane.setEnabledAt(3,true);
	                    tabbedPane.setEnabledAt(4,true);

	                    //enable all navigation and action buttons
	                }//~if(fileExists && hasVali...
	            }//~try...

	            catch(Exception e)
	            {
	                    JOptionPane.showMessageDialog(
	                        this,
	                        e.getMessage(),
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
	            }
	        }//~evt.getSource() instance...

	        else if (source.equals("new") &&
		        evt.getSource() instanceof JMenuItem)
	        {
	            repaint();
	            createNewDatabase();
	            resetForm();
	            //d.print("creating new file");
	            //enable
	            Component [] compOn =
	            {
	                saveAsItem,
                    closeItem,
                    exitItem,
                    newButton,
                    saveButton,
                    cancelButton,
                    clearButton
	            };//~Component [] compOn =...

	            componentsEnabled(compOn,true);
	            //disable
	            Component [] compOff =
	            {
	                openItem,
                    newItem,
                    printItem,
                    saveItem,
                    backupItem,
                    removeallItem,
                    optionsItem,
                    findButton,
                    deleteButton,
                    editButton,
                    beginButton,
                    prevButton,
                    nextButton,
                    endButton
	            };//~Component [] compOff =...

	            componentsEnabled(compOff,false);

	            //enable strength,maintenance,equipment tabs
	            tabbedPane.setEnabledAt(2,true);
	            tabbedPane.setEnabledAt(3,true);
	            tabbedPane.setEnabledAt(4,true);
	        }//~evt.getSource() instance...

	/*
	        else if (source.equals("print") &&
	        	evt.getSource()  instanceof JMenuItem)
	        {
		        repaint();
		        printWorkout((Workout)logbook.elementAt
		        	(visibleWorkoutRecord - 1),visibleWorkoutRecord);
	        }
	*/
	        else if (source.equals("save") &&
	        	evt.getSource() instanceof JMenuItem)
	        {
	            repaint();

	            if (optionsWindowOpened)
	            {
		            optionslogbook = options.getOptionsLog();
	            }

                boolean fileSaved = saveCurrentFile(this);

                if(fileSaved)
	            {
                    origlogbook = (Vector)logbook.clone();
                    origmntlogbook = (Vector)mntlogbook.clone();
                    origequiplogbook = (Vector)equiplogbook.clone();
                    origoptionslogbook = (Vector)optionslogbook.clone();

	                //enable
	                Component [] compOn =
	                {
		                printItem,
	                    saveItem,
                        saveAsItem,
                        exitItem,
                        backupItem,
                        closeItem,
                        removeallItem,
                        optionsItem
	                };

	                componentsEnabled(compOn,true);

	                //disable
	                Component [] compOff =
	                {
	                    openItem,
                        newItem
	                };

	                componentsEnabled(compOff,false);

	            }//~if(fileSaved)...
	        }//~evt.getSource() instance...

	        else if (source.equals("saveas")&&
		        evt.getSource() instanceof JMenuItem)
	        {
	            repaint();

	            if (optionsWindowOpened)
	            {
		            optionslogbook = options.getOptionsLog();
	            }

	            boolean fileSaved = saveFile(this);

	            if(fileSaved)
	            {
                    origlogbook = (Vector)logbook.clone();
                    origmntlogbook = (Vector)mntlogbook.clone();
                    origequiplogbook = (Vector)equiplogbook.clone();
                    origoptionslogbook = (Vector)optionslogbook.clone();

	                //enable
	                Component [] compOn =
	                {
	                    printItem,
		                saveItem,
                        saveAsItem,
                        exitItem,
                        backupItem,
                        closeItem,
                        removeallItem,
                        optionsItem
	                };

	                componentsEnabled(compOn,true);
	                //disable
	                Component [] compOff =
	                {
	                    openItem,
                        newItem
	                };

	                componentsEnabled(compOff,false);
	            }//~if(fileSaved)...
	        }//~evt.getSource() instance...

	        else if (source.equals("backup") &&
		        (evt.getSource() instanceof JMenuItem))
	        {
	            repaint();

	            try
	            {

		            if (optionsWindowOpened)
		            {
			            optionslogbook = options.getOptionsLog();
		            }
		            
		            Properties properties = System.getProperties();
		            String filePath = properties.getProperty("user.dir") 
		            						+ properties.getProperty("file.separator") 
		            						+ "backup.dat";
					currentFileName = filePath;
		            boolean fileSaved = writeFile(currentFileName);

	                if(fileSaved)
	                {
	                    origlogbook = (Vector)logbook.clone();
	                    origmntlogbook = (Vector)mntlogbook.clone();
	                    origequiplogbook = (Vector)equiplogbook.clone();
	                    origoptionslogbook = (Vector)optionslogbook.clone();

	                    Component [] compOn =
	                    {
		                    printItem,
	                        saveItem,
                            saveAsItem,
                            exitItem,
                            backupItem,
                            closeItem,
                            removeallItem,
                            optionsItem
	                    };

	                    componentsEnabled(compOn,true);
	                    //disable
	                    Component [] compOff =
	                    {
	                        openItem,
                            newItem
	                    };

	                    componentsEnabled(compOff,false);
	                    JOptionPane.showMessageDialog(
	                        this,
	                        "Backup written to " + filePath,
	                        "Information",
	                        JOptionPane.INFORMATION_MESSAGE);
	                }//~if(fileSaved)...
	            }//~try...

	            catch(Exception e)
	            {
	                JOptionPane.showMessageDialog(
	                    this,
	                    "Error while making backup. Reason unknown.",
	                    "Error",
	                    JOptionPane.ERROR_MESSAGE);
	            }
	        }//~(evt.getSource() instanc...

	        else if (source.equals("close") &&
	    	    (evt.getSource() instanceof JMenuItem))
	        {
	            repaint();

	            if (optionsWindowOpened)
	            {
		            optionslogbook = options.getOptionsLog();
            	}

	            if (!identical(origlogbook,logbook) ||
	            	!identical(origmntlogbook,mntlogbook) ||
	            	!identical(origequiplogbook,equiplogbook) ||
	            	!identical(origoptionslogbook,optionslogbook) &&
	            	!origlogbook.isEmpty() &&
	            	!logbook.isEmpty())
	            {
	                int option = JOptionPane.showConfirmDialog(
	                    this,
	                    "Do you want to save your latest changes?",
	                    "Save Latest Changes?",
	                    JOptionPane.YES_NO_OPTION);
	                if (option == JOptionPane.YES_OPTION)
	                {
	                    boolean dummy = saveCurrentFile(this);
	                }
	            }//~else...

                clearAllLogs();
	            optionsWindowOpened = false;
                resetForm();

	        }//~(evt.getSource() instanc...

	        else if (source.equals("exit") &&
	        (evt.getSource() instanceof JMenuItem))
	        {
	            repaint();

	            if (optionsWindowOpened)
	            {
		            optionslogbook = options.getOptionsLog();
            	}

	            if (!identical(origlogbook,logbook) ||
	            	!identical(origmntlogbook,mntlogbook) ||
	            	!identical(origequiplogbook,equiplogbook) ||
	            	!identical(origoptionslogbook,optionslogbook) &&
	            	!origlogbook.isEmpty() &&
	            	!logbook.isEmpty())
	            {
	                int option = JOptionPane.showConfirmDialog(
	                    this,
	                    "Do you want to save your latest changes?",
	                    "Save Latest Changes?",
	                    JOptionPane.YES_NO_OPTION);
	                if (option == JOptionPane.YES_OPTION)
	                {
	                    boolean dummy = saveCurrentFile(this);
	                }
	            }//~else...
	            System.exit(0);
	        }//~(evt.getSource() instanc...

	        else if (source.equals("remove all") &&
	        (evt.getSource() instanceof JMenuItem))
	        {
	            repaint();
	            int answer = JOptionPane.showConfirmDialog(
	                this,
	                "This action will delete all records!",
	                "Confirmation",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.WARNING_MESSAGE);
	            if(answer == JOptionPane.YES_OPTION)
	            {
	                logbook.removeAllElements();
	                resetFields();
	                edit();
	            }
	        }//~(evt.getSource() instanc...

	        else if (source.equals("options") &&
	        (evt.getSource() instanceof JMenuItem))
	        {
				showOptions(this);
	        }

            else if ((source.equals("run")) &&
        	    (evt.getSource() instanceof JRadioButton) &&
            	statsPanel.isVisible())
            {
                populateStatsTab("run");

                statsLeftAxisOptionComboBox.removeAllItems();
            	statsLeftAxisOptionComboBox.addItem("Pace (mph)");
            	statsLeftAxisOptionComboBox.addItem("Distance in Miles");
		        statsLeftAxisOptionComboBox.addItem("Resting Heart Rate");
		        statsLeftAxisOptionComboBox.addItem("Weight");
		        statsLeftAxisOptionComboBox.addItem("Temperature");

		        statsRightAxisOptionComboBox.removeAllItems();
            	statsRightAxisOptionComboBox.addItem("Pace (mph)");
            	statsRightAxisOptionComboBox.addItem("Distance in Miles");
		        statsRightAxisOptionComboBox.addItem("Resting Heart Rate");
		        statsRightAxisOptionComboBox.addItem("Weight");
		        statsRightAxisOptionComboBox.addItem("Temperature");
            }

            else if ((source.equals("bike")) &&
	            (evt.getSource() instanceof JRadioButton) &&
    	        statsPanel.isVisible())
            {
                populateStatsTab("bike");

                statsLeftAxisOptionComboBox.removeAllItems();
            	statsLeftAxisOptionComboBox.addItem("Pace (mph)");
            	statsLeftAxisOptionComboBox.addItem("Distance in Miles");
		        statsLeftAxisOptionComboBox.addItem("Resting Heart Rate");
		        statsLeftAxisOptionComboBox.addItem("Weight");
		        statsLeftAxisOptionComboBox.addItem("Temperature");

		        statsRightAxisOptionComboBox.removeAllItems();
            	statsRightAxisOptionComboBox.addItem("Pace (mph)");
            	statsRightAxisOptionComboBox.addItem("Distance in Miles");
		        statsRightAxisOptionComboBox.addItem("Resting Heart Rate");
		        statsRightAxisOptionComboBox.addItem("Weight");
		        statsRightAxisOptionComboBox.addItem("Temperature");
            }

            else if ((source.equals("swim")) &&
            	(evt.getSource() instanceof JRadioButton) &&
	            statsPanel.isVisible())
            {
                populateStatsTab("swim");

                statsLeftAxisOptionComboBox.removeAllItems();
            	statsLeftAxisOptionComboBox.addItem("Pace (mins/100 yds)");
            	statsLeftAxisOptionComboBox.addItem("Distance in Yards");
		        statsLeftAxisOptionComboBox.addItem("Resting Heart Rate");
		        statsLeftAxisOptionComboBox.addItem("Weight");
		        statsLeftAxisOptionComboBox.addItem("Temperature");

		        statsRightAxisOptionComboBox.removeAllItems();
            	statsRightAxisOptionComboBox.addItem("Pace (mins/100 yds)");
            	statsRightAxisOptionComboBox.addItem("Distance in Yards");
		        statsRightAxisOptionComboBox.addItem("Resting Heart Rate");
		        statsRightAxisOptionComboBox.addItem("Weight");
		        statsRightAxisOptionComboBox.addItem("Temperature");
            }

	        //miles or kilos checked
	        else if((source.equals("calcmilescheck") ||
	        source.equals("calckiloscheck")) &&
	        (evt.getSource() instanceof JCheckBox) &&
	        calcPanel.isVisible())
	        {
	            calcResetFields();
	            calcSelectionLabel.setEnabled(true);
	            calcResultLabel.setEnabled(true);
	            calcTimeRadio.setEnabled(true);
	            calcDistanceRadio.setEnabled(true);
	            calcPaceRadio.setEnabled(true);
	        }

	        //Miles checked
	        //time checked
	        else if(source.equals("calctimeradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcMilesCheck.isSelected())
	        {
	            calcResetFields();
	            calcDataLabel.setEnabled(true);
	            calcPaceMphLabel.setEnabled(true);
	            calcDistanceMilesLabel.setEnabled(true);
	            calcDistanceMilesField.setEnabled(true);
	            calcMinPerMileLabel.setEnabled(true);
	            calcPaceMphRadio.setEnabled(true);
	            calcPaceMinPerMileRadio.setEnabled(true);
	            calcMinPer100ydsLabel.setEnabled(true);
	            calcPaceMinsPer100ydsRadio.setEnabled(true);
	        }//~calcMilesCheck.isSelecte...

	        //miles checked
	        //distance checked
	        else if(source.equals("calcdistanceradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcMilesCheck.isSelected())
	        {
	            calcResetFields();
	            calcDataLabel.setEnabled(true);
	            calcPaceMphLabel.setEnabled(true);
	            calcTimeLabel.setEnabled(true);
	            calcTimeField.setEnabled(true);
				calcMinPerMileLabel.setEnabled(true);
	            calcPaceMphRadio.setEnabled(true);
	            calcPaceMinPerMileRadio.setEnabled(true);
	            calcMinPer100ydsLabel.setEnabled(true);
	            calcPaceMinsPer100ydsRadio.setEnabled(true);
	        }

	        //miles checked
	        //pace checked
	        else if(source.equals("calcpaceradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcMilesCheck.isSelected())
	        {
	            calcResetFields();
	            calcDataLabel.setEnabled(true);
	            calcTimeLabel.setEnabled(true);
	            calcTimeField.setEnabled(true);
	            calcDistanceMilesLabel.setEnabled(true);
	            calcDistanceMilesField.setEnabled(true);
	        }

	        //pace mph checked
	        else if (source.equals("calcpacemphradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcPaceMphRadio.isSelected())
	        {
	            calcPaceMphField.setEnabled(true);
		        calcPaceMinPerMileField.setEnabled(false);
		        calcPaceMinPer100ydsField.setEnabled(false);
	        }

	        //pace min per mile checked
	        else if (source.equals("calcpaceminpermileradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcPaceMinPerMileRadio.isSelected())
	        {
	            calcPaceMphField.setEnabled(false);
		        calcPaceMinPerMileField.setEnabled(true);
		        calcPaceMinPer100ydsField.setEnabled(false);
	        }

	        //pace kph
	        else if (source.equals("calcpacekphradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcPaceKphRadio.isSelected())
	        {
	            calcPaceKphField.setEnabled(true);
		        calcPaceMinPerKmField.setEnabled(false);
		        calcPaceMinPer100mField.setEnabled(false);
	        }

	        //pace min per km
	        else if (source.equals("calcpaceminperkmradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcPaceMinPerKmRadio.isSelected())
	        {
	            calcPaceKphField.setEnabled(false);
		        calcPaceMinPerKmField.setEnabled(true);
		        calcPaceMinPer100mField.setEnabled(false);
	        }

	        //Kilometers checked
	        //time checked
	        else if(source.equals("calctimeradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcKilosCheck.isSelected())
	        {
	            calcResetFields();
	            calcDataLabel.setEnabled(true);
	            calcPaceKphLabel.setEnabled(true);
	            calcMinPerKmLabel.setEnabled(true);
	            calcDistanceKiloLabel.setEnabled(true);
	            calcDistanceKiloField.setEnabled(true);
	            calcPaceKphRadio.setEnabled(true);
	            calcPaceMinPerKmRadio.setEnabled(true);
	            calcMinPer100mLabel.setEnabled(true);
	            calcPaceMinsPer100mRadio.setEnabled(true);
	        }

	        //kilometers checked
	        //distance checked
	        else if(source.equals("calcdistanceradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcKilosCheck.isSelected())
	        {
	            calcResetFields();
	            calcDataLabel.setEnabled(true);
	            calcPaceKphLabel.setEnabled(true);
	            calcMinPerKmLabel.setEnabled(true);
	            calcTimeLabel.setEnabled(true);
	            calcTimeField.setEnabled(true);
				calcMinPerKmLabel.setEnabled(true);
	            calcPaceKphRadio.setEnabled(true);
	            calcPaceMinPerKmRadio.setEnabled(true);
	            calcMinPer100mLabel.setEnabled(true);
	            calcPaceMinsPer100mRadio.setEnabled(true);
	        }

	        //Kilometers checked
	        //pace checked
	        else if(source.equals("calcpaceradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcKilosCheck.isSelected())
	        {
	            calcResetFields();
	            calcDataLabel.setEnabled(true);
	            calcTimeLabel.setEnabled(true);
	            calcTimeField.setEnabled(true);
	            calcDistanceKiloLabel.setEnabled(true);
	            calcDistanceKiloField.setEnabled(true);
	        }

	        //pace min per 100m
	        else if (source.equals("calcpaceminsper100mradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcPaceMinsPer100mRadio.isSelected())
	        {
	            calcPaceKphField.setEnabled(false);
		        calcPaceMinPerKmField.setEnabled(false);
		        calcPaceMinPer100mField.setEnabled(true);
	        }

	        //pace min per 100yds
	        else if (source.equals("calcpaceminsper100ydsradio") &&
	        (evt.getSource() instanceof JRadioButton) &&
	        calcPaceMinsPer100ydsRadio.isSelected())
	        {
	            calcPaceMphField.setEnabled(false);
		        calcPaceMinPerMileField.setEnabled(false);
		        calcPaceMinPer100ydsField.setEnabled(true);
	        }

	        else if(source.equals("calcbutton") &&
	        evt.getSource() instanceof JButton &&
	        calcPanel.isVisible())
	        {
	            calcFields();
	        }

	        else if(source.equals("calcresetbutton") &&
	        evt.getSource() instanceof JButton &&
	        calcPanel.isVisible())
	        {
	            calcResetFields();
	            calcSelectionLabel.setEnabled(true);
	            calcMilesCheck.setEnabled(true);
	            calcKilosCheck.setEnabled(true);
	        }

	        else if(source.equals("howto"))
	        {
		        showHelp(this);
	        }

	        else if(source.equals("about"))
	        {
	        	showAboutDialog();
        	}
        }
        catch(Exception e)
        {
	        d.print(e);
        }
    }//~public void actionPerfor...

    /**
    Listens to tabs being changed by user.
    
    @param evt Name of tab selected by the user
    
    @return void
    */
    public void stateChanged(ChangeEvent evt)
    {
        JTabbedPane pane = (JTabbedPane)evt.getSource();
        int n = pane.getSelectedIndex();
        //Aerobic tab
        if(n == 0 && logbook != null && logbooksize > 0)
        {
	        if (DEBUG)
	        {
		        d.print("Aerobic panel up");
	        }
            //enable
            Component [] compOn =
            {
                findButton,
                newButton,
                deleteButton,
                editButton,
                saveButton,
                cancelButton,
                clearButton,
                beginButton,
                prevButton,
                nextButton,
                endButton
            };//~Component [] compOn =...

            componentsEnabled(compOn,true);

            statusField.setText
            ("Workout Record " + visibleWorkoutRecord +
                " of " + logbooksize);
        }//~if(n == 0 && logbook != ...

        //Strength tab
        else if(n == 1 && logbook != null && logbooksize > 0)
        {
	        if (DEBUG)
	        {
		        d.print("Strength panel up");
	        }
            Component [] compOn =
            {
                findButton,
                newButton,
                deleteButton,
                editButton,
                saveButton,
                cancelButton,
                clearButton,
                beginButton,
                prevButton,
                nextButton,
                endButton
            };//~Component [] compOn =...

            componentsEnabled(compOn,true);

            statusField.setText
            ("Workout Record " + visibleWorkoutRecord +
                " of " + logbooksize);
        }//~else if(n == 1 && logboo...

        else if(n == 2) //Statistics tab
        {
	        if (DEBUG)
	        {
		        d.print("Stats panel up");
	        }

	        //Place focus on event last viewed.
	        String selectedEvent = statsEventGroup.getSelection().getActionCommand();
	        if(selectedEvent.equals("run"))
	        	statsRunRadio.requestFocus();
	        else if (selectedEvent.equals("bike"))
	        	statsBikeRadio.requestFocus();
	        else
	        	statsSwimRadio.requestFocus();

	        actionButtonsOn(false);
	        navButtonsOn(false);

	        tabbedPane.setEnabledAt(2,true);
	        statusField.setText("");
        }//~else if(n == 2) //Statis...

        //Maintenance tab
        else if(n == 3 && !mntlogbook.isEmpty())
        {
	        if (DEBUG)
	        {
		        d.print("Mnt panel up");
	        }
            //enable
            Component [] compOn =
            {
                newButton,
                deleteButton,
                editButton,
                saveButton,
                cancelButton,
                clearButton,
                prevButton,
                nextButton
            };//~Component [] compOn =...

            componentsEnabled(compOn,true);
            //disable
            Component [] compOff =
            {
                findButton,
                beginButton,
                endButton
            };

            componentsEnabled(compOff,false);

            maintDateField.setText("");
            maintWorkTextArea.setText("");
            maintDateField.setEditable(false);
            maintWorkTextArea.setEditable(false);
            statusField.setText("");
           	maintOpenButton.setEnabled(true);
        }//~else if(n == 3 && !mntlo...

        else if(n == 4) //Equipment Tab
        {
	        if (DEBUG)
	        {
		        d.print("Equipment panel up");
	        }
            //Place focus on event last viewed.
            String selectedEvent = statsEventGroup.getSelection().getActionCommand();
            if(selectedEvent.equals("equiprun"))
	            statsRunRadio.requestFocus();
            else if (selectedEvent.equals("equipbike"))
    	        statsBikeRadio.requestFocus();
            else
        	    statsSwimRadio.requestFocus();

            //enable
            Component [] compOn =
            {
                editButton,
                cancelButton,
                saveButton,
                clearButton
            };

            componentsEnabled(compOn,true);
            //disable
            Component [] compOff =
            {
                findButton,
                newButton,
                deleteButton,
                beginButton,
                prevButton,
                nextButton,
                endButton,
                };//~Component [] compOff =...

            componentsEnabled(compOff,false);

        }//~else if(n == 4) //Equipm...

        else if(n == 5) //Calculator tab
        {
	        if (DEBUG)
	        {
		        d.print("Calculator panel up");
	        }
            //disable
            actionButtonsOn(false);
            navButtonsOn(false);

            statusField.setText("");
        }//~else if(n == 5) //Calcul...
    }//~public void stateChanged...
    
    /**
    Main procedure
    
    @param args Arguments to start program
    @return void
    */
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel
            (UIManager.getSystemLookAndFeelClassName());
//             UIManager.setLookAndFeel
//             (UIManager.getLookAndFeel());
            //Sets common LAF if system not available
//             	UIManager.setLookAndFeel
//             		(UIManager.getCrossPlatformLookAndFeelClassName());
	        JFrame mainFrame = new Tewl();
	        mainFrame.setVisible(true);
	        mainFrame.pack();
	        Toolkit tk = Toolkit.getDefaultToolkit();
	        Dimension screen = tk.getScreenSize();
	        Dimension size = mainFrame.getSize();
	        int y_loc = (screen.height - size.height)/2;
	        int x_loc = (screen.width - size.width)/2;
	        mainFrame.setLocation(x_loc,y_loc);
        }

        catch(Exception e)
        {
//            System.err.println("Using common Look and Feel interface.");
			System.err.println("Program error.");
        }

    }//~public static void main(...

    //======================== variables =======================//
	private static Color tabColor;
    private JMenuBar menuBar;
    private JMenu databaseMenu,
        editMenu,
        optionsMenu,
        helpMenu;
    private JMenuItem openItem,
        newItem,
        printItem,
        saveItem,
        saveAsItem,
        exitItem,
        backupItem,
        closeItem,
        removeallItem,
        optionsItem,
        howToItem,
        aboutItem;
    private JButton findButton,
        editButton,
        cancelButton,
        newButton,
        saveButton,
        deleteButton,
        clearButton,
        beginButton,
        prevButton,
        nextButton,
        endButton,
        calcButton,
        calcResetButton,
        maintOpenButton,
        statsGraphButton;
    private JLabel dateLabel,
		runLabel,
		runPaceLabel,
		runDistanceMeasureLabel,
		bikeLabel,
		bikePaceLabel,
		bikeDistanceMeasureLabel,
		swimLabel,
		swimPaceLabel,
		swimDistanceMeasureLabel,
    	paceLabel,
        eventLabel,
        distanceLabel,
        timeLabel,
        avgSpeedLabel,
        routeLabel,
        weatherLabel,
        tempLabel,
        pulseLabel,
        summaryLabel,
        weightLabel,
        puLabel,
        suLabel,
        totalMilesToDateLabel,
        avgMilesLabel,
        maintDateLabel,
        maintWorkLabel,
        statsEventLabel,
        statsAvgSpeedLabel,
        statsAvgPaceLabel,
        statsLeftAxisOptionLabel,
        statsRightAxisOptionLabel,
        calcSelectionLabel,
        calcDistanceMilesLabel,
        calcDistanceKiloLabel,
        calcTimeLabel,
        calcTimeConvertedLabel,
        calcPaceMphLabel,
        calcPaceKphLabel,
        calcResultLabel,
        calcDataLabel,
        calcMinPerMileLabel,
        calcMinPerKmLabel,
        calcMinPer100mLabel,
        calcMinPer100ydsLabel,
        equipSelectOption,
        equipRunShoeSizeLabel,
        equipRunShoeModelLabel,
        equipBikeNameLabel,
        equipBikeModelLabel,
        equipBikeSNLabel,
        equipBikeFrameSizeLabel,
        equipBikeTireSizeLabel,
        equipBikeShoeSizeLabel,
        equipBikeShoeModelLabel,
        equipBikeSeatStemDiameterLabel,
        equipSwimGoggleSizeLabel,
        equipWalkShoeSizeLabel,
        equipWalkShoeModelLabel;
    private JTextField monthField,
        dayField,
        yearField,
        statusField,
        puField,
        suField,
        totalMilesToDateField,
        avgMilesField,
        statsAvgSpeedField,
        maintDateField,
        calcDistanceMilesField,
        calcDistanceKiloField,
        calcTimeField,
        calcTimeConvertedField,
        calcPaceMphField,
        calcPaceKphField,
        calcPaceMinPerMileField,
        calcPaceMinPerKmField,
        calcPaceMinPer100mField,
        calcPaceMinPer100ydsField,
        equipRunShoeSizeField,
        equipRunShoeModelField,
        equipBikeNameField,
        equipBikeModelField,
        equipBikeSNField,
        equipBikeFrameSizeField,
        equipBikeTireSizeField,
        equipBikeShoeSizeField,
        equipBikeShoeModelField,
        equipBikeSeatPostDiameterField,
        equipSwimGoggleSizeField,
        equipWalkShoeSizeField,
        equipWalkShoeModelField,
        todaysDateField;
    private JTextArea summaryTextArea,
        maintWorkTextArea;
    private ButtonGroup eventGroup,
        weatherGroup,
        statsEventGroup,
        calcResultGroup,
        calcSelectionGroup,
        equipEventGroup,
        calcPaceMileRadioGroup,
        calcPaceKmRadioGroup;
    private JRadioButton
        statsBikeRadio,
        statsRunRadio,
        statsSwimRadio,
        calcTimeRadio,
        calcDistanceRadio,
        calcPaceRadio,
        equipRunRadio,
        equipBikeRadio,
        equipSwimRadio,
        equipWalkRadio,
		calcPaceMphRadio,
        calcPaceMinPerMileRadio,
		calcPaceKphRadio,
        calcPaceMinPerKmRadio,
        calcPaceMinsPer100ydsRadio,
        calcPaceMinsPer100mRadio;
    private JCheckBox
    	kiloOnCheckBox,
    	calcMilesCheck,
        calcKilosCheck;
    private JComboBox tempComboBox,
    	weatherComboBox,
    	pulseComboBox,
		weightComboBox,
		statsLeftAxisOptionComboBox,
		statsRightAxisOptionComboBox;
    private JScrollPane scrollPane;
    private JPanel actionPanel,
        tabPanel,
        mainPanel,
		dateTempPanel,
		summaryPanel,
        stationaryPanel,
        statsPanel,
        statsFieldPanel,
        statsGraphPanel,
        eventPanel,
        weatherPanel,
        bottomPanel,
        navPanel,
        datePanel,
        statusPanel,
        maintenancePanel,
        maintButtonPanel,
        statsEventPanel,
        calcPanel,
        calcButtonPanel,
        equipPanel,
        equipEventPanel,
        equipSpecPanel,
        equipOuterEventPanel,
        equipOuterSpecPanel;
    private Box mainStatusPanel;
    private JTabbedPane tabbedPane;
    private Font f,
        m,
        l;
    private Calendar today;
    private Container contentPane;
    private QueryDialog findDialog;
    private static Graph graphLog;
    private static Vector logbook,
        origlogbook,
        mntlogbook,
        origmntlogbook,
        equiplogbook,
        origequiplogbook,
        optionslogbook,
        origoptionslogbook;
    private Workout w;
    private boolean newRecordValid = false;
    private int visibleMaintRecord = 0;
    private int visibleWorkoutRecord = 0;
    private int logbooksize = 0;    
    private int mntlogbooksize = 0;
    private int equiplogbooksize = 0;
    private final static int ROUTEOPTIONSMAX = 10;
    private final static int EXERCISEOPTIONSMAX = 10;
    private final static int OPTIONSMAX = 20;
    private final static int EQUIPRECORDMAX = 4;
    private final static double KM_TO_MILE = 0.624;
    private final static double MILE_TO_KM = 0.624;
    private static String currentFileName = "";
    private PrintPanel p;
    private Debug d;
    private boolean DEBUG;
    private Help help;
    private static UserConfig options;
    private boolean optionsWindowOpened = false;
    private EventTable eventTable;
}//~ChangeListener...

	
