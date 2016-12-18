/*
 * @(#)Log.java 1.0 01/05/30
 *
 *
 */
 
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.lang.Integer;
import java.lang.Character;
import java.text.NumberFormat;
import java.io.*;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/*
WorkoutTime class: encapsulates the time object used to create 
and maintain the event time in minutes and seconds.
*/
class WorkoutTime
{
	//***************************************************
	//Constructor: uses set methods to build time object
	WorkoutTime(int m,int s)
	{
		try
		{
			System.out.println("inside WorkoutTime()");
			setMinutes(m);
			setSeconds(s);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}

	//***************************************************
	//Accessors: gets minutes and seconds from time object
	public int getMinutes()
	{
		return minutes;
	}
	
	public int getSeconds()
	{
		return seconds;
	}
	
	//***************************************************
	//Mutators: validates and saves minutes and seconds 
	//into time object.
	public void setMinutes(int m)
	{
		try
		{
			if (m > 0)
			{
				minutes = m;
			}
			else
				throw new IllegalArgumentException
				("Minutes must be >= 0 & <= 999");
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	public void setSeconds(int s)
	{
		try
		{
			if ((s >= 0)&&(s <= 59))
			{
				seconds = s;
			}
			else
				throw new IllegalArgumentException
				("Seconds must be >= 0 or <= 59");
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	//***************************************************
	//Private data types for minutes and seconds
	private int minutes;
	private int seconds;
}

/*
Workout class: used to managed the creation of dates.
*/
class WorkoutDate
{
	WorkoutDate()
	{}
	
	WorkoutDate(int m,int d,int y)
	{
		try
		{
			System.out.println("inside WorkoutDate()");
			if(validDate(m,d,y))
				setDate(m,d,y);
			else
				throw new IllegalArgumentException ("Invalid date");
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	//Validates month and day.  Year passed in for future
	//purposes.
	public boolean validDate(int m,int d,int y)
	{
		System.out.println("inside validDate()(WorkoutDate)");
		boolean monthAndDayOk = false;
		boolean result = false;
		Calendar today = new GregorianCalendar();
		
		//month(1..12)
		//day(1..31)
		if ((m >= 1)&&(m <= 12)&&
			(d >= 1)&&(d <= 31))
		{
			//January
			if ((m == 1)&&(d <= 31))
				monthAndDayOk = true;
			//February
			else if ((m == 2)&&(d <= 28))
				monthAndDayOk = true;
			//March
			else if ((m == 3)&&(d <= 31))
				monthAndDayOk = true;
			//April
			else if ((m == 4)&&(d <= 30))
				monthAndDayOk = true;
			//May
			else if ((m == 5)&&(d <= 31))
				monthAndDayOk = true;
			//June
			else if ((m == 6)&&(d <= 30))
				monthAndDayOk = true;
			//July
			else if ((m == 7)&&(d <= 31))
				monthAndDayOk = true;
			//August
			else if ((m == 8)&&(d <= 31))
				monthAndDayOk = true;
			//September
			else if ((m == 9)&&(d <= 30))
				monthAndDayOk = true;
			//October
			else if ((m == 10)&&(d <= 31))
				monthAndDayOk = true;
			//November
			else if ((m == 11)&&(d <= 30))
				monthAndDayOk = true;
			//Must be December
			else if ((m == 12)&&(d <= 31))
				monthAndDayOk = true;
			else;
		}
		
		if (monthAndDayOk)
			result = true;
			
		System.out.println("result = " + result);
		return result;
	}
	
	/**************** Accessors *********************/
	public Calendar getDate()
	{
		return date;
	}
	
	public int getMonth()
	{
		return (month + 1);
	}
	
	public int getDay()
	{
		return day;
	}

	public int getYear()
	{
		return year;
	}

	/**************** Mutators *********************/
	public void setDate(int m,int d,int y)
	{
		
		//Since dates are zero-based, meaning that January
		//thru December range from 0 .. 11,
		//the month passed in is converted to the
		//appropriate position number before being
		//used to create a calendar date.
		int mon = m-1;
		date = new GregorianCalendar(y,mon,d);
		setMonth();
		setDay();
		setYear();
	}
	
	public void setMonth()
	{
		month = date.get(Calendar.MONTH);
	}
	
	public void setDay()
	{
		day = date.get(Calendar.DAY_OF_MONTH);
	}

	public void setYear()
	{
		year = date.get(Calendar.YEAR);
	}

	//Private data types
	private Calendar date;
	private int month;
	private int day;
	private int year;
}


//Workout class: this class builds the object that contains all of
//the info that is maintained in a list.
class Workout
{
	//******************** Constructor *****************
	//Empty constructor that sets all data types to default values
	Workout()
	{
		System.out.println("inside Workout()");
	}
	
	//********************* Accessors *******************
	public WorkoutDate getDate()
	{
		return date;
	}
	
	public WorkoutTime getTime()
	{
		return time;
	}
	
	public String getEvent()
	{
		return event;
	}
	
	public String getWeather()
	{
		return weather;
	}
	
	public int getTemp()
	{
		return temp;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	//******************** Mutators ****************
	
	public void setDate(int m,int d,int y)
	{
		try
		{
			date = new WorkoutDate(m,d,y);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	public void setTime(int m,int s)
	{
		try
		{
			time = new WorkoutTime(m,s);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
	}
	
	public void setEvent(String evt)
	{
		event = evt;
	}
	
	public void setWeather(String w)
	{
		weather = w;
	}
	
	public void setTemp(int tmp)
	{
		temp = tmp;
	}
	
	public void setDistance(double dis)
	{
		distance = dis;
	}
	
	public void setSummary(String s)
	{
		summary = s;
	}
	
	//Reads one record from input stream
	public void readData(StringTokenizer t) throws
		IOException
	{
		System.out.println("inside readData()");
		int month = Integer.parseInt(t.nextToken());
		int day = Integer.parseInt(t.nextToken());
		int year = Integer.parseInt(t.nextToken());
		setDate(month,day,year);
		setDistance
			(Double.parseDouble(t.nextToken()));
		setEvent(t.nextToken());
		int mins = Integer.parseInt(t.nextToken());
		int secs = Integer.parseInt(t.nextToken());
		setTime(mins,secs);
		setWeather(t.nextToken());
		setTemp(Integer.parseInt(t.nextToken()));
		setSummary(t.nextToken());
	}
		
	//Private types
	private WorkoutDate date;
	private WorkoutTime time;
	private String event;
	private double distance;
	private String weather;
	private int temp;
	private String summary;

}

/*
QueryDialog class: used to get user input on search date
*/
class QueryDialog extends JDialog implements ActionListener
{
	public QueryDialog()
	{
		System.out.println("inside QueryDialog()");
		setModal(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		setTitle("Search");
		
		//Data needed to position dialog in the middle of the window.
		int baseofframe = 200;
		int heightofframe = 150;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int y_loc = ((screen.height/2) - (heightofframe/2)) + 25;
		int x_loc = (screen.width/2) - (baseofframe/2);
		setLocation(x_loc,y_loc);
		setSize(baseofframe,heightofframe);
		
		//Panel to hold three textfields
		datePanel = new JPanel();
		
		//Month,Day,Year textfields
		monthField = new JTextField(2);
		monthField.setHorizontalAlignment
			(JTextField.RIGHT);
		dayField = new JTextField(2);
		dayField.setHorizontalAlignment
			(JTextField.RIGHT);
		yearField = new JTextField(4);
		yearField.setHorizontalAlignment
			(JTextField.RIGHT);
		
		//Add textfields to datePanel
		datePanel.add(monthField);
		datePanel.add(dayField);
		datePanel.add(yearField);
		
		//Panel to hold buttons
		buttonPanel = new JPanel();
		
		//Find,close buttons
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		closeButton = new JButton("Cancel");
		closeButton.addActionListener(this);

		//Add buttons to buttonPanel
		buttonPanel.add(findButton);
		buttonPanel.add(closeButton);
		
		//Main panel on which the datePanel and buttonPanel
		//will sit.  This is necessary because this
		//dialog frame uses the flowlayout, which forces
		//no component position control when the dialog is resized
		//So, setting building each panel and then adding each
		//mainPanel will ensure a certain appearance everytime
		//the dialog is invoked.
		mainPanel = new JPanel();
		mainPanel.add(datePanel);
		mainPanel.add(buttonPanel);
		getContentPane().add(mainPanel);
	}
	
	//Retrieves the three date strings from dialog
	//and return a string array.
	public String [] getDateStrings()
	{
		boolean badvalue = false;
		String m = monthField.getText();
		String d = dayField.getText();
		String y = yearField.getText();
		String [] stringArray = {m,d,y};
		
		return stringArray;
	}
	
	//Accesses class field used as flag for Log class
	public boolean isValidRequest()
	{
		return validRequest;
	}
	
	//Validates strings as digits and 
	//returns false if string cannot be 
	//interpreted as a digit.
	public boolean validDigit(String s)
	{
		System.out.println("inside validDigit()");
		boolean isValid = true;
		char [] charArray = s.toCharArray();
		for(int i=0; i<charArray.length; i++)
		{
			if(!Character.isDigit(charArray[i]))
			{
				isValid = false;
				break;
			}
		}
		return isValid;
	}
	
	//Validates each string in the array and returns
	//true if each string is convertible to a digit
	//and contains valid month and day values.
	public boolean validDate(String [] da)
	{
		System.out.println("inside validDate()");

		/*
		da[0] is the month
		da[1] is the day
		da[2] is the year
		*/
		
		boolean valid = false;
		validRequest = false;
		WorkoutDate dateStr = new WorkoutDate();

		if(validDigit(da[0]))
			if(validDigit(da[1]))
				if(validDigit(da[2]))
				{
					int m = Integer.parseInt(da[0]);
					int d = Integer.parseInt(da[1]);
					int y = Integer.parseInt(da[2]);
					
					if(dateStr.validDate(m,d,y))
					{
						valid = true;
						validRequest = true;
					}
				}

		System.out.println("valid = " + valid);
		return valid;
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		System.out.println(evt.getActionCommand() + 
				" pressed (dialog)");
		
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
				}
				else
					JOptionPane.showMessageDialog(
						this,
						"Invalid date",
						"Error",
						JOptionPane.ERROR_MESSAGE);
		}
		else if (source == closeButton)
		{
			monthField.setText("");
			dayField.setText("");
			yearField.setText("");
			setVisible(false);
		}
	}
		
	//Private types
	private JFrame dialogFrame;
	private JPanel mainPanel,
					datePanel,
					buttonPanel;
	private JTextField monthField,
						dayField,
						yearField;
	private JButton findButton, 
					closeButton;

	//Used as a flag by the Log class to trigger a search 
	//for the record in the db.
	private boolean validRequest;
}

/*
Log Class: encapsulates the log frame from which all
workout data will be entered, viewed, and edited.
*/
class Log extends JFrame implements ActionListener
{
	public Log(Vector v)
	{
		System.out.println("inside Log()");
		
		getContentPane().setLayout(new BorderLayout());

		//************** Menu bar *************/
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		backupItem = new JMenuItem("Backup");
		backupItem.addActionListener(this);
		backupItem.setActionCommand("backup");
		recoverItem = new JMenuItem("Recover");
		recoverItem.addActionListener(this);
		recoverItem.setActionCommand("recover");
		exitItem = new JMenuItem("Exit");
		exitItem.setActionCommand("exit");
		exitItem.addActionListener(this);
		fileMenu.add(recoverItem);
		fileMenu.add(backupItem);
		fileMenu.add(exitItem);
		
		editMenu = new JMenu("Edit");
		removeallItem = new JMenuItem("Remove All Records");
		removeallItem.setActionCommand("remove all");
		removeallItem.addActionListener(this);
		editMenu.add(removeallItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		setJMenuBar(menuBar);
		
		//******** Action Panels *********//
		//********** Buttons ***********//

		actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(2,1));

		actionPanel1 = new JPanel();
		actionPanel1.setLayout(new GridLayout(1,3));
			
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		findButton.setActionCommand("find");
		actionPanel1.add(findButton);

		newButton = new JButton("New");
		newButton.addActionListener(this);
		newButton.setActionCommand("new");
		actionPanel1.add(newButton);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("delete");
		actionPanel1.add(deleteButton);

		actionPanel2 = new JPanel();
		actionPanel2.setLayout(new GridLayout(1,4));

		editButton = new JButton("Edit");
		editButton.addActionListener(this);
		editButton.setActionCommand("edit");
		actionPanel2.add(editButton);

		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");
		actionPanel2.add(saveButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		actionPanel2.add(cancelButton);

		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		clearButton.setActionCommand("clear");
		actionPanel2.add(clearButton);
		
		actionPanel.add(actionPanel1);
		actionPanel.add(actionPanel2);

		//********* Main Panel ************//
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.lightGray);
		mainPanel.setForeground(Color.lightGray);
		Border mainBorder = BorderFactory.createEtchedBorder();
		mainPanel.setBorder(mainBorder);
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		//All components will not grow/shrink with window
		gbc.fill = GridBagConstraints.NONE;
		
		//************ Date ************//

		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2,2,2,2);
		dateLabel = new JLabel("Date: ");
		add(dateLabel,gbc,1,2,1,1);

		gbc.insets = new Insets(2,5,2,2);
		gbc.anchor = GridBagConstraints.WEST;
		monthField = new JTextField(2);
		monthField.setHorizontalAlignment
			(JTextField.RIGHT);
		monthField.setText(month);
		monthField.setToolTipText("Month - MM");
		add(monthField,gbc,2,2,1,1);

		gbc.insets = new Insets(2,2,2,5);
		gbc.anchor = GridBagConstraints.EAST;
		dayField = new JTextField(2);
		dayField.setHorizontalAlignment
			(JTextField.RIGHT);
		dayField.setText(day);
		dayField.setToolTipText("Day - DD");
		add(dayField,gbc,2,2,1,1);
		
		gbc.anchor = GridBagConstraints.WEST;
		yearField = new JTextField(4);
		yearField.setHorizontalAlignment
			(JTextField.RIGHT);
		yearField.setText(year);
		yearField.setToolTipText("Year - YYYY");
		add(yearField,gbc,3,2,1,1);
		
		//********** Event *************//
		//Set ActionCommands for radio buttons to
		//lowercase so that they can used to match
		//event option retrieved from list and then
		//accordingly activated when record is
		//viewed.
		
		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.EAST;
		eventLabel = new JLabel("Event: ");
		add(eventLabel,gbc,1,3,1,1);
		
		eventPanel = new JPanel();
		gbc.anchor = GridBagConstraints.WEST;
		eventGroup = new ButtonGroup();

		runRadio = new JRadioButton("Run");
		runRadio.setActionCommand("run");
		eventGroup.add(runRadio);
		eventPanel.add(runRadio);

		bikeRadio = new JRadioButton("Bike");
		bikeRadio.setActionCommand("bike");
		eventGroup.add(bikeRadio);
		eventPanel.add(bikeRadio);

		swimRadio = new JRadioButton("Swim");
		swimRadio.setActionCommand("swim");
		eventGroup.add(swimRadio);
		eventPanel.add(swimRadio);

		walkRadio = new JRadioButton("Walk");
		walkRadio.setActionCommand("walk");
		eventGroup.add(walkRadio);
		eventPanel.add(walkRadio);

		add(eventPanel,gbc,2,3,4,1);
		
		//********* Distance **********//
		
		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.EAST;
		distanceLabel = new JLabel("Distance(mi): ");
		add(distanceLabel,gbc,1,4,1,1);
		
		gbc.anchor = GridBagConstraints.WEST;
		distanceField = new JTextField(5);
		distanceField.setHorizontalAlignment
			(JTextField.RIGHT);
		distanceField.setText(distance);
		distanceField.setToolTipText("Distance - NNN.N");
		add(distanceField,gbc,2,4,1,1);
		
		//********** Time ************//
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2,2,2,2);
		timeLabel = new JLabel("Time (mins:secs): ");
		add(timeLabel,gbc,4,4,1,1);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2,2,2,5);
		minutesField = new JTextField(3);
		minutesField.setHorizontalAlignment
			(JTextField.RIGHT);
		minutesField.setText(minutes);
		minutesField.setToolTipText("Minutes - MMM");
		add(minutesField,gbc,5,4,1,1);

		gbc.insets = new Insets(2,5,2,2);
		gbc.anchor = GridBagConstraints.CENTER;
		secondsField = new JTextField(2);
		secondsField.setHorizontalAlignment
			(JTextField.RIGHT);
		secondsField.setText(seconds);
		secondsField.setToolTipText("Seconds - SS");
		add(secondsField,gbc,5,4,1,1);
		
		//********* Weather **********//
		//Set ActionCommands for radio buttons to
		//lowercase so that they can used to match
		//event option retrieved from list and then
		//accordingly activated when record is
		//viewed.
		
		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.EAST;
		weatherLabel = new JLabel("Weather: ");
		add(weatherLabel,gbc,1,5,1,1);
		
		gbc.anchor = GridBagConstraints.WEST;
		weatherPanel = new JPanel();
		weatherGroup = new ButtonGroup();
		
		hotRadio = new JRadioButton("Hot");
		hotRadio.setActionCommand("hot");
		weatherGroup.add(hotRadio);
		weatherPanel.add(hotRadio);

		coldRadio = new JRadioButton("Cold");
		coldRadio.setActionCommand("cold");
		weatherGroup.add(coldRadio);
		weatherPanel.add(coldRadio);

		mildRadio = new JRadioButton("Mild");
		mildRadio.setActionCommand("mild");
		weatherGroup.add(mildRadio);
		weatherPanel.add(mildRadio);

		rainyRadio = new JRadioButton("Rainy");
		rainyRadio.setActionCommand("rainy");
		weatherGroup.add(rainyRadio);
		weatherPanel.add(rainyRadio);

		rainyhotRadio = new JRadioButton("Rainy-Hot");
		rainyhotRadio.setActionCommand("rainyhot");
		weatherGroup.add(rainyhotRadio);
		weatherPanel.add(rainyhotRadio);

		add(weatherPanel,gbc,2,5,4,1);
		
		//********** Temperature ***********//

		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.EAST;
		tempLabel = new JLabel("Temperature: ");
		add(tempLabel,gbc,1,6,1,1);
		
		gbc.anchor = GridBagConstraints.WEST;
		tempField = new JTextField(3);
		tempField.setHorizontalAlignment
			(JTextField.RIGHT);
		tempField.setText(temp);
		tempField.setToolTipText("Temperature - NNN");
		add(tempField,gbc,2,6,1,1);
		
		//********** Summary **************//
		
		gbc.insets = new Insets(2,2,2,5);
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		summaryLabel = new JLabel("Summary: ");
		add(summaryLabel,gbc,1,7,1,1);
		
		gbc.insets = new Insets(10,2,10,2);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		summaryTextArea = new JTextArea(4,20);
		summaryTextArea.setLineWrap(true);
		summaryTextArea.setWrapStyleWord(true);
		scrollPane = new JScrollPane
			(summaryTextArea,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		summaryTextArea.insert(summary,0);
		add(scrollPane,gbc,2,7,5,2);
		
		//********* Navigation Buttons *********//

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

		//********* Status Panel ************//
		
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusField = new JTextField();
		statusField.setEditable(false);
		statusField.setHorizontalAlignment
			(JTextField.CENTER);
		statusPanel.add(statusField);
		Border etchedBorder = BorderFactory.createEtchedBorder();
		statusPanel.setBorder(etchedBorder);

		//******** Bottom Panel ***********//
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBorder(etchedBorder);
		bottomPanel.add(navPanel,BorderLayout.CENTER);
		bottomPanel.add(statusPanel,BorderLayout.SOUTH);
		
		//********** Frame ***********//

		getContentPane().add(actionPanel,BorderLayout.NORTH);
		getContentPane().add(mainPanel,BorderLayout.CENTER);
		getContentPane().add(bottomPanel,BorderLayout.SOUTH);
		
		Container contentPane = getContentPane();

		//Data needed to position dialog in the middle of the window.
		int baseofframe = 550;
		int heightofframe = 450;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int y_loc = (screen.height/2) - (heightofframe/2);
		int x_loc = (screen.width/2) - (baseofframe/2);
		setLocation(x_loc,y_loc);

		setSize(baseofframe,heightofframe);
		setResizable(false);
		setTitle("Exercise Log");
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.exit(0);
			}
		});
		
		//Initialize strings for use later on.
		m = " ";
		d = " ";
		y = " ";
		dist = " ";
		min = " ";
		sec = " ";
		tmp = " ";
		logbook = v;
		if(v.size() > 0)
			populateWindow((Workout)v.lastElement());
	}
	
	//Simplifies adding components to panel by
	//passing in constraints
	public void add(Component c, GridBagConstraints gbc,
					int x, int y, int w, int h)
	{
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		mainPanel.add(c,gbc);
	}
	
	//Validates strings as digits. Returns false if string 
	//cannot be interpreted as a digit.
	public boolean validDigit(String s, int maxlen)
	{
		System.out.println("inside validDigit()");
		boolean isValid = true;
		char [] charArray = s.toCharArray();
		
		if ((charArray.length <= maxlen))
		{
			for(int i=0; i<charArray.length; i++)
			{
				if(!Character.isDigit(charArray[i]))
				{
					isValid = false;
					break;
				}
			}
		}
		return isValid;
	}
				
	//Validates strings as doubles. Returns false if string cannot 
	//be interpreted as a double.
	public boolean validDouble(String s,int maxlen)
	{
		System.out.println("inside validDouble()");
		boolean isValid = false;
		char [] charArray = s.toCharArray();
		
		if(charArray.length <= maxlen)
		{
			for(int i=0; i<charArray.length; i++)
			{
				if((Character.isDigit(charArray[i])) ^ (charArray[i]=='.'))
					isValid = true;
				else
				{
					isValid = false;
					break;
				}			
			}
		}
		return isValid;
	}

	//Compares date from query to each one in list and, if
	//applicable, returns the index of the matching
	//record.  Otherwise, it returns -1.
	public int recordFound(String [] s)
	{
		System.out.println("inside recordFound()");
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
				System.out.println("record found");
				System.out.println("w = " + w.getDate().getMonth() + "/" +
											w.getDate().getDay() + "/" +
											w.getDate().getYear());
				break;
			}
			else
				currentIndex = -1;
		}			
		return currentIndex;
	}
	
	//Resets all textfields and textareas to blank
	//and radio buttons to bike (event) and mild (weather).
	public void resetFields()
	{
		System.out.println("inside reset()");
		monthField.setText("");
		dayField.setText("");
		yearField.setText("");
		bikeRadio.setSelected(true);
		distanceField.setText("");
		minutesField.setText("");
		secondsField.setText("");
		mildRadio.setSelected(true);
		tempField.setText("");
		summaryTextArea.setText("");
		
		statusField.setText("");
	}		
	
	//Compares date from window to matching date
	//in list and returns index
	public int getCurrentWindowIndex()
	{
		System.out.println("inside getCurrentWindowIndex()");

		Workout current = new Workout();
		int cindex = -1;
		
		//Get data from window
		int cmonth = Integer.parseInt(monthField.getText());	
		int cday = Integer.parseInt(dayField.getText());
		int cyear = Integer.parseInt(yearField.getText());

		System.out.println("******** record inside vector ********");
		
		ListIterator iter = logbook.listIterator();
			
		//Iterate thru list to find date match between window date
		// one of the dates in the vector.
		while(iter.hasNext())
		{
			int iterindex = iter.nextIndex();
			Workout w = (Workout)iter.next();
			
			if ((w.getDate().getMonth() == cmonth) &&
				(w.getDate().getDay() == cday) &&
				(w.getDate().getYear() == cyear))
			{
				//If a match is found copy iterindex into cindex
				cindex = iterindex;
				System.out.println("match found, iterindex = " + iterindex + 
									" cindex = " + cindex);
				break;
			}
			else
				cindex = -1;
		}

		return cindex;
	}
	
	//Validates all field info, and if all correct, returns filled
	//Workout object.
	public Workout getFields()
	{
		Workout recordToSave = new Workout();
		boolean validMonth = false,
				validDay = false,
				validYear = false,
				validDistance = false,
				validEvent = false,
				validMinutes = false,
				validSeconds = false,
				validWeather = false,
				validTemp = false;
				newRecordValid = false;
		
		String smonth = monthField.getText();	
		String sday = dayField.getText();
		String syear = yearField.getText();
		String sdistance = distanceField.getText();
		String sevent = eventGroup.getSelection().getActionCommand();
		String sminutes = minutesField.getText();
		String sseconds = secondsField.getText();
		String sweather = weatherGroup.getSelection().getActionCommand();
		String stemp = tempField.getText();
		String ssummary = summaryTextArea.getText();
		
		//If text area is blank, then plug in '*'. This value will be
		//written to the file when saved, which in turn will allow
		//the StringTokenizer to safely read the record into
		//the list.  If left blank, the file will become unreadable
		//by the StringTokenizer because it can't handle
		//two consecutive pipes, as in "||", when this field
		//is written to the file.
		if(ssummary.length() == 0)
			ssummary = "*";

		if(validDigit(smonth,2) && smonth.length()>0)
			validMonth = true;
		if(validDigit(sday,2) && sday.length()>0)
			validDay = true;
		if(validDigit(syear,4) && syear.length()>0)
			validYear = true;
		if(validDouble(sdistance,5) && sdistance.length()>0)
			validDistance = true;
		if(validDigit(sminutes,3) && sminutes.length()>0)
			validMinutes = true;
		if(validDigit(sseconds,2) && sseconds.length()>0)
			validSeconds = true;
		if(validDigit(stemp,3) && stemp.length()>0)
			validTemp = true;
		if(!((eventGroup.getSelection()).getActionCommand()).equals(""))
			validEvent = true;			
		if(!((weatherGroup.getSelection()).getActionCommand()).equals(""))
			validWeather = true;

		if(validMonth&&validDay&&validYear&&validDistance&&validMinutes&&
			validSeconds&&validTemp&&validEvent&&validWeather)
		{
			//Get data from window
			int nmonth = Integer.parseInt(smonth);	
			int nday = Integer.parseInt(sday);
			int nyear = Integer.parseInt(syear);
			double ndistance = new Double
				(distanceField.getText().trim()).doubleValue();
			String nevent = eventGroup.getSelection().getActionCommand();
			int nminutes = Integer.parseInt(sminutes);
			int nseconds = Integer.parseInt(sseconds);
			String nweather = weatherGroup.getSelection().getActionCommand();
			int ntemp = Integer.parseInt(stemp);
			String nsummary = ssummary;
		
			//Put data in a Workout object
			recordToSave.setDate(nmonth,nday,nyear);
			recordToSave.setDistance(ndistance);
			recordToSave.setEvent(nevent);
			recordToSave.setTime(nminutes,nseconds);
			recordToSave.setWeather(nweather);
			recordToSave.setTemp(ntemp);
			recordToSave.setSummary(nsummary);
			
			newRecordValid = true;
		}
		else
			JOptionPane.showMessageDialog(
				this,
				"Invalid entries",
				"Error",
				JOptionPane.ERROR_MESSAGE);
		return recordToSave;	
	}
	
	//Populates window with workout object passed in and makes
	//the record uneditable.
	public void populateWindow(Workout w)
	{
		System.out.println("inside populateWindow()");
		System.out.println("w = " + w.getDate().getMonth() + "/" +
									w.getDate().getDay() + "/" +
									w.getDate().getYear());
		
		resetFields();
		
		//Copy values from w into individual strings
		month = m.valueOf(w.getDate().getMonth());
		day = d.valueOf(w.getDate().getDay());
		year = y.valueOf(w.getDate().getYear());
		distance = dist.valueOf(w.getDistance());
		event = w.getEvent();
		minutes = min.valueOf(w.getTime().getMinutes());
		seconds = sec.valueOf(w.getTime().getSeconds());
		temp = tmp.valueOf(w.getTemp());
		weather = w.getWeather();
		summary = w.getSummary();
		
		//Set each field with appropriate strings
		monthField.setText(month);
		monthField.setEditable(false);
		dayField.setText(day);
		dayField.setEditable(false);
		yearField.setText(year);
		yearField.setEditable(false);
		distanceField.setText(distance);
		distanceField.setEditable(false);
		minutesField.setText(minutes);
		minutesField.setEditable(false);
		secondsField.setText(seconds);
		secondsField.setEditable(false);
		tempField.setText(temp);
		tempField.setEditable(false);
		summaryTextArea.insert(summary,0);
		summaryTextArea.setEditable(false);
		
		//Set the appropriate event and weather radio
		//buttons to true
		if (event.equals("run"))
		{
			runRadio.setSelected(true);
		}
		else if (event.equals("bike"))
		{
			bikeRadio.setSelected(true);
		}
		else if (event.equals("swim"))
		{
			swimRadio.setSelected(true);
		}
		else
		{
			walkRadio.setSelected(true);
		}

		runRadio.setEnabled(false);
		bikeRadio.setEnabled(false);
		swimRadio.setEnabled(false);
		walkRadio.setEnabled(false);
			
		if (weather.equals("hot"))
		{
			hotRadio.setSelected(true);
		}
		else if (weather.equals("cold"))
		{
			coldRadio.setSelected(true);
		}
		else if (weather.equals("mild"))
		{
			mildRadio.setSelected(true);
		}
		else if (weather.equals("rainy"))
		{
			rainyRadio.setSelected(true);
		}
		else
		{
			rainyhotRadio.setSelected(true);
		}

		hotRadio.setEnabled(false);
		coldRadio.setEnabled(false);
		mildRadio.setEnabled(false);
		rainyRadio.setEnabled(false);
		rainyhotRadio.setEnabled(false);
		
		statusField.setText("Record " + (logbook.indexOf(w) + 1) + " of " + logbook.size());

		JButton [] ba3 = {findButton,editButton,cancelButton,
			newButton,saveButton,deleteButton,
			clearButton,beginButton,prevButton,
			nextButton,endButton};
		set(ba3,true);
	}

	//Uses user input from queryDialog to locate matching 
	//record in db
	public void find()
	{
		System.out.println("inside find()");
		int recordIndex = 0;
		findDialog = new QueryDialog();
		findDialog.setVisible(true);
		if(findDialog.isValidRequest())
		{
			String [] mdy = findDialog.getDateStrings();
			recordIndex = recordFound(mdy);
			System.out.println("recordIndex = " + recordIndex);
			if(recordIndex >= 0)
			{
				System.out.println("after call to recordFound()");
				populateWindow((Workout)logbook.elementAt(recordIndex));
			}
			else
			{
				JOptionPane errorDialog = new JOptionPane();
				errorDialog.showMessageDialog(
					this,
					"No record found",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Makes all text components editable
	public void edit()
	{
		System.out.println("inside edit()");
		monthField.setEditable(false);
		dayField.setEditable(false);
		yearField.setEditable(false);
		distanceField.setEditable(true);
		runRadio.setEnabled(true);
		bikeRadio.setEnabled(true);
		swimRadio.setEnabled(true);
		walkRadio.setEnabled(true);
		minutesField.setEditable(true);
		secondsField.setEditable(true);
		hotRadio.setEnabled(true);
		coldRadio.setEnabled(true);
		mildRadio.setEnabled(true);
		rainyRadio.setEnabled(true);
		rainyhotRadio.setEnabled(true);
		tempField.setEditable(true);
		summaryTextArea.setEditable(true);

		//Place focus in Month textfield
		monthField.requestFocus();

		//Disable all buttons except cancel, save,
		//and clear.
		JButton [] ba = {findButton,editButton,
			newButton,beginButton,prevButton,
			nextButton,endButton};
		set(ba,false);
	}
	
	//Cancels edit mode
	public void cancel()
	{
		if(!distanceField.isEditable())
			JOptionPane.showMessageDialog
				(this,
				"This record must be in the Edit mode first",
				"Error",
				JOptionPane.ERROR_MESSAGE);
		else
		{
			//Load last record
			populateWindow((Workout)logbook.lastElement());
			
			//Enable all button in case they were
			//disabled previously
			JButton [] ba1 = {findButton,editButton,cancelButton,
					newButton,saveButton,deleteButton,
					clearButton,beginButton,prevButton,
					nextButton,endButton};
			set(ba1,true);
		}
	}			
	
	//Resets all fields and makes the blank record
	//editable.
	public void create()
	{
		System.out.println("inside create()");
		resetFields();
		edit();
		
		//Make date fields editable
		monthField.setEditable(true);
		dayField.setEditable(true);
		yearField.setEditable(true);
	}
	
	//Saves the current record to list
	public void save()
	{
		System.out.println("inside save()");
		Workout w = new Workout();
		int index = 0;
		if(distanceField.isEditable())
		{
			w = getFields();
			if(newRecordValid)
			{
				index = getCurrentWindowIndex();
				System.out.println("index = " + index);
				if((index >= 0) && (logbook.size()>0))
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
				}
				else
				{
					//Add new record to end of list
					logbook.addElement(w);
					//Reload record and make it uneditable
					populateWindow(w);
				}
			}
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
	
	//Deletes the current record from list
	public void delete()
	{
		System.out.println("inside delete()");
		if(distanceField.isEditable())
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
				logbook.removeElementAt(deleteIndex);
				resetFields();
				populateWindow((Workout)logbook.lastElement());
			}
		}
		else
			JOptionPane.showMessageDialog(
				this,
				"This record must be in the Edit mode first.",
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	//Clears current record.  Doesn't delete from list.
	public void clear()
	{
		if (distanceField.isEditable())
		{
			resetFields();
			JButton [] ba5 = {findButton,editButton,deleteButton,
					newButton,beginButton,prevButton,
					nextButton,endButton};
			set(ba5,false);
		}
		else
			JOptionPane.showMessageDialog(
				this,
				"This record must be in the Edit mode first.",
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}

	//Populates window with first record in list
	public void getFirst()
	{
		System.out.println("inside getFirst()");
		if(logbook.size() > 0)
			populateWindow((Workout)logbook.firstElement());
		else
			JOptionPane.showMessageDialog(
				this,
				"There are no records in the log.",
				"Error",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	//Populates window with previous record in list
	public void getPrev()
	{
		System.out.println("inside getPrev()");
		if(logbook.size() > 0)
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
		}
		else
			JOptionPane.showMessageDialog(
				this,
				"There are no records in the log.",
				"Error",
				JOptionPane.ERROR_MESSAGE);			
		
	}
	
	//Populates window with next record in list
	public void getNext()
	{
		System.out.println("inside getNext()");
		if(logbook.size() > 0)
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
			}
			else
				JOptionPane.showMessageDialog
					(this,
					"There are no more records to view",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(
				this,
				"There are no records in the log.",
				"Error",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	//Populates window with last record in list
	public void getLast()
	{
		System.out.println("inside getLast()");
		if(logbook.size() > 0)
			populateWindow((Workout)(logbook.lastElement()));
		else
			JOptionPane.showMessageDialog(
				this,
				"There are no records in the log.",
				"Error",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	//Provides simple way to enable or disable buttons
	//during an edit.
	public void set(JButton [] btns, boolean b)
	{
		for(int i=0; i<btns.length; i++)
		{
			btns[i].setEnabled(b);
		}
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String source = evt.getActionCommand();
		System.out.println(evt.getActionCommand() + " pressed (main)");
		
		if (source.equals("find"))
			find();
		else if (source.equals("edit"))
			edit();
		else if (source.equals("cancel"))
			cancel();
		else if (source.equals("new"))
			create();
		else if (source.equals("save"))
			save();
		else if (source.equals("delete"))
		{
			delete();
		}
		else if (source.equals("clear"))
		{
			clear();
		}
		else if (source.equals("beginning"))
			getFirst();
		else if (source.equals("previous"))
			getPrev();
		else if (source.equals("next"))
			getNext();
		else if (source.equals("end"))
			getLast();
		else if ((source.equals("recover")) && 
			(evt.getSource() instanceof JMenuItem))
		{
			try
			{
				System.out.println("recovering backup");
				recover();
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
		}
		else if ((source.equals("backup")) && 
			(evt.getSource() instanceof JMenuItem))
		{
			try
			{
				System.out.println("making backup");
				writeFile("backup.dat");
				JOptionPane.showMessageDialog(
					this,
					"Backup complete",
					"Information",
					JOptionPane.INFORMATION_MESSAGE);			
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
		}
		else if ((source.equals("exit")) && 
			(evt.getSource() instanceof JMenuItem))
		{
			try
			{
				System.out.println("closing");
				writeFile("workout.dat");
				System.exit(0);
			}
			catch(IOException e)
			{
				System.out.println(e);
				System.exit(0);
			}
		}
		else if ((source.equals("remove all")) &&
			(evt.getSource() instanceof JMenuItem))
		{
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
		}
	}
	
	//Writes vector to file
	public void writeFile(String fileName) throws IOException
	{
		System.out.println("inside writeFile()");
		PrintWriter fout = new PrintWriter(new FileWriter(fileName));

		fout.print(logbook.size() + "|");
		
		for(int i=0; i<logbook.size(); i++)
		{
			Workout w = (Workout)logbook.elementAt(i);
			fout.print(w.getDate().getMonth() + "|" +
						w.getDate().getDay() + "|" +
						w.getDate().getYear() + "|" +
						w.getDistance() + "|" +
						w.getEvent() + "|" +
						w.getTime().getMinutes() + "|" +
						w.getTime().getSeconds() + "|" +
						w.getWeather() + "|" +
						w.getTemp() + "|" +
						w.getSummary() + "|");
		}
		fout.close();
	}
	
	public void recover() throws IOException
	{
		System.out.println("inside recover()");
		
		int answer = JOptionPane.showConfirmDialog(
			this,
			"This action will recover your exercise log. Do you want to continue?",
			"Confirmation",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.WARNING_MESSAGE);

		if(answer == JOptionPane.YES_OPTION)
		{
			File fin = new File("backup.dat");
			if(fin.exists())
			{
				PrintWriter to = new PrintWriter(new FileWriter("workout.dat"));
				BufferedReader from = new BufferedReader(new FileReader(fin));
				int ch = 0;
				while((ch = from.read()) != -1)
				{
					to.write((char)ch);
					System.out.print((char)ch);
				}
				to.flush();
				to.close();
				from.close();
				JOptionPane.showMessageDialog(
					this,
					"Recovery Complete. Exit and restart this program.",
					"Infomation",
					JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(
					this,
					"\'recover.dat\' file missing. Can\'t perform recovery",
					"Information",
					JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public static void main(String args[])
	{
		try
		{
			System.out.println("Starting Log...");
			File fin = new File("workout.dat");
			if (fin.exists())
			{	
				System.out.println("opening file");
				BufferedReader br = new BufferedReader(new FileReader(fin));
				String s = br.readLine();
				StringTokenizer t = new StringTokenizer(s,"|");
				int records = Integer.parseInt(t.nextToken());
				System.out.println("\n\nrecords = " + records);
				if(records > 0)
				{
					Vector log = new Vector(records,5);
					System.out.println("vector logbook = " + log.size());
					for (int i=0; i<records; i++)
					{
						System.out.println("Adding record = " + i);
						Workout in = new Workout();
						in.readData(t);
						log.add(in);
					}
					JFrame mainFrame = new Log(log);
					mainFrame.setVisible(true);

					String m = " ";
					String d = " ";
					String y = " ";
					String dist = " ";
					String min = " ";
					String sec = " ";
					String tmp = " ";
					
					System.out.println("******** record inside vector ********");
					ListIterator iter = log.listIterator();
					while(iter.hasNext())
					{
						Workout out = new Workout();
						out = (Workout)(iter.next());
						String omonth = m.valueOf(out.getDate().getMonth());
						String oday = d.valueOf(out.getDate().getDay());
						String oyear = y.valueOf(out.getDate().getYear());
						String odistance = dist.valueOf(out.getDistance());
						String oevent = out.getEvent();
						String ominutes = min.valueOf(out.getTime().getMinutes());
						String oseconds = sec.valueOf(out.getTime().getSeconds());
						String otemp = tmp.valueOf(out.getTemp());
						String oweather = out.getWeather();
						String osummary = out.getSummary();
						System.out.println(omonth + "|" + oday + "|" + oyear + "|" +
						odistance + "|" + oevent + "|" + ominutes + "|" +
						oseconds + "|" + oweather + "|" + otemp + "|" + osummary);
					}

				}
				else
				{
					System.out.println("There are no records in the file.");
				}				
				br.close();
			}
			else
			{
				System.out.println("workout.dat does not exist.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	private JMenuBar menuBar;
	private JMenu fileMenu,
					editMenu;
	private JMenuItem recoverItem,
					exitItem,
					backupItem,
					removeallItem;
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
					endButton;
	private JLabel dateLabel,
					eventLabel,
					distanceLabel,
					timeLabel,
					weatherLabel,
					tempLabel,
					summaryLabel;
	private JTextField monthField,
						dayField,
						yearField,
						distanceField,
						tempField,
						minutesField,
						secondsField,
						statusField;
	private JTextArea summaryTextArea;
	private ButtonGroup eventGroup,
						weatherGroup;
	private JRadioButton runRadio,
							bikeRadio,
							swimRadio,
							walkRadio,
							mildRadio,
							hotRadio,
							coldRadio,
							rainyRadio,
							rainyhotRadio;
	private JScrollPane scrollPane;
	private JPanel actionPanel,
					actionPanel1,
					actionPanel2,
					mainPanel,
					eventPanel,
					weatherPanel,
					bottomPanel,
					navPanel,
					statusPanel;
	private QueryDialog findDialog;
	private Vector logbook;
	private Workout w;
	private String m;
	private String month;
	private String d;
	private String day;
	private	String y;
	private String year;
	private String dist;
	private String distance;
	private String event;
	private String min;
	private String minutes;
	private String sec;
	private String seconds;
	private String tmp;
	private String temp;
	private String weather;
	private String summary;
	private boolean newRecordValid;

}

