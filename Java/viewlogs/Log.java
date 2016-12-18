import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.Timer;

class MyTextPane extends JTextPane
{
	public MyTextPane(DefaultStyledDocument doc)
	{
		p = new MyPrint();
		localFileName = "";
		localSearchString = "";
		localFilter = false;
		localMatchCase = false;
		localAttributes = new Hashtable();
		matchesFound = false;
		timerIsOn = false;
		setStyledDocument(doc);
		setFont(new Font("Helvetica",Font.PLAIN,12));
		setBackground(Color.white);
		getCaret().setVisible(true);
		setEditable(false);
	}
	
	public DefaultStyledDocument getDoc()
	{
		return (DefaultStyledDocument)getStyledDocument();
	}
	
	void incrementIndex()
	{
		if(matchIndex < MAX_INDEX)
			matchIndex++;
		else
			matchIndex = lastMatchIndex;
	}
	
	void decrementIndex()
	{
		if(matchIndex > 0)
			matchIndex--;
		else
			matchIndex = 0;
	}
	
	public void nextMatch()
	{
		getCaret().setVisible(true);
		
		int cursorPosition = getCaret().getDot();
		
		if(cursorPosition >= matchingCaretPositions[lastMatchIndex])
		{
			matchIndex = lastMatchIndex;
		}
		else
		{
			if(cursorPosition < matchingCaretPositions[lastMatchIndex] && cursorPosition >= matchingCaretPositions[0])
			{
				FindClosestNextMatchIndexBlock:
				for(int i=0; i<lastMatchIndex; i++)
				{
					if (cursorPosition < matchingCaretPositions[i])
					{
						break FindClosestNextMatchIndexBlock;
					}
					matchIndex = i;
				}
				incrementIndex();
			}
			else
			{
				matchIndex = 0;
			}
		}
		
		getCaret().moveDot(matchingCaretPositions[matchIndex]);
	}
	
	public void previousMatch()
	{
		getCaret().setVisible(true);
		
		int cursorPosition = getCaret().getDot();
		
		if(cursorPosition <= matchingCaretPositions[0])
		{
			matchIndex = 0;
		}
		else
		{
			if(cursorPosition > matchingCaretPositions[0] && cursorPosition <= matchingCaretPositions[lastMatchIndex])
			{
				FindClosestPreviousMatchIndexBlock:
				for(int i=lastMatchIndex; i>0; i--)
				{
					if (cursorPosition > matchingCaretPositions[i])
					{
						break FindClosestPreviousMatchIndexBlock;
					}
					matchIndex = i;
				}
				decrementIndex();
			}
			else
			{
				matchIndex = lastMatchIndex;
			}
		}
		
		getCaret().moveDot(matchingCaretPositions[matchIndex]);
	}
	
    public void readFile(String fin, String searchString, boolean filter, boolean matchCase, Hashtable attributes)
	{
		try
		{
			localFileName = fin;
			localSearchString = searchString;
			localFilter = filter;
			localMatchCase = matchCase;
			localAttributes = attributes;
			
			//used LineNumberReader for future enhancements
			LineNumberReader infile = new LineNumberReader(new FileReader(fin));
			String line = "";
			StringBuffer buffer;
			Style highlightStyle = (Style)attributes.get("highlight");
			Style noStyle = (Style)attributes.get("none");
			int lineNumber = 0;
			int charPosition = 0;
			lastMatchIndex = 0;
			matchIndex = 0;
			int startOfMatch = 0;
			
			getDoc().remove(0,getDoc().getLength());
			
			//this while loop version places the cursor at the first position in the line
			// with the match		
			while((line=infile.readLine()) != null)
			{
				//use the StringBuffer.length() to measure because it is accurate versus the String.length();
				buffer = new StringBuffer(line);
				
				lineNumber = infile.getLineNumber();
				
				if(!filter)
				{
					if(searchString.equals("") || searchString.equals(null))
					{
						getDoc().insertString(getDoc().getLength(),buffer.toString()+"\n",noStyle);
						// add 1 for newline character
						charPosition += buffer.length() + 1;
					}
					else
					{
						if((startOfMatch = buffer.toString().toLowerCase().indexOf(searchString.toLowerCase())) > -1 
								&& !matchCase)
						{
							charPosition += startOfMatch;
							
							getDoc().insertString(getDoc().getLength(),buffer.toString()+"\n",highlightStyle);
							matchingCaretPositions[matchIndex] = charPosition;
							lastMatchIndex = matchIndex;
							incrementIndex();
							
							// add 1 for newline character
							charPosition += (buffer.length() - startOfMatch + 1);
						}
						else if((buffer.toString().indexOf(searchString)) > -1  
								&& matchCase)
						{
							charPosition += startOfMatch;
							
							getDoc().insertString(getDoc().getLength(),buffer.toString()+"\n",highlightStyle);
							matchingCaretPositions[matchIndex] = charPosition;
							lastMatchIndex = matchIndex;
							incrementIndex();
							
							// add 1 for newline character
							charPosition += (buffer.length() - startOfMatch + 1);
						}
						else
						{
							getDoc().insertString(getDoc().getLength(),buffer.toString()+"\n",noStyle);
							// add 1 for newline character
							charPosition += buffer.length() + 1;
						}
					}
				}
				else if(filter)
				{
					if((buffer.toString().toLowerCase().indexOf(searchString.toLowerCase())) > -1 
							&& !matchCase)
					{
						getDoc().insertString(getDoc().getLength(),buffer.toString()+"\n",highlightStyle);
					}
					else if((buffer.toString().indexOf(searchString)) > -1 
							&& matchCase)
					{
						getDoc().insertString(getDoc().getLength(),buffer.toString()+"\n",highlightStyle);
					}
				}
				
			}

			infile.close();
			
			getCaret().setVisible(true);
			matchIndex = 0;
			getCaret().moveDot(getDoc().getLength());
			
			if(matchingCaretPositions[lastMatchIndex] == 0)
				matchesFound = false;
			else
				matchesFound = true;
		}
		catch(Exception e)
		{
			p.print(e + " in readFile()");
		}
	}
	
	public boolean foundMatches()
	{
		return matchesFound;
	}
	
	public boolean isFirstMatch()
	{
		return (matchIndex == 0);
	}
	
	public boolean isLastMatch()
	{
		return (matchIndex == lastMatchIndex);
	}
	
    void startTailingLog()
    {
		timer = new Timer(FIVE_SECONDS, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
				readFile(	localFileName,
							localSearchString,
							localFilter,
							localMatchCase,
							localAttributes);
		    }
		});
		timer.start();
		timerIsOn = true;
    }
    
    void stopTailingLog()
    {
	    if(timerIsOn)
	    {
    		timer.stop();
	    	timerIsOn = false;
    	}
    }
    
    public boolean timerRunning()
    {
	    return (timerIsOn);
    }
    
    private Timer timer;
	private static int matchIndex;
	private static int lastMatchIndex;
	private final static int MAX_INDEX = 2000;
	private static int [] matchingCaretPositions = new int[MAX_INDEX];
	private static boolean matchesFound;
	private static int currentPosition;
	final private static int FIVE_SECONDS = 5000;
	private MyPrint p;
	private String localFileName;
	private String localSearchString;
	private boolean localFilter;
	private boolean localMatchCase;
	private Hashtable localAttributes;
	private boolean timerIsOn;
}

class MyTabPane 
				extends JPanel 
				implements ActionListener
{
	public MyTabPane(StyleContext sc)
	{
		setLayout(new GridBagLayout());
		p = new MyPrint();
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(1,1,1,1);
		tailCheckBox = new JCheckBox("Tail");
		tailCheckBox.addActionListener(this);
		tailCheckBox.setActionCommand("tail");
		tailCheckBox.setSelected(false);
		tailCheckBox.setEnabled(false);
		add(this,tailCheckBox,gbc,1,1,1,1);
 		doc = new DefaultStyledDocument(sc);
		textPane = new MyTextPane(doc);
		scrollPane = new JScrollPane(textPane,
									JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
									JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(945,270));
		scrollPane.setMinimumSize(new Dimension(945,270));
		add(this,scrollPane,gbc,1,2,1,1);
	}
	
    void add(Container p, Component c, GridBagConstraints gbc, int x, int y, int w, int h)
    {
		gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        p.add(c,gbc);
    }
    
	public MyTextPane getTextPane()
	{
		return textPane;
	}
	
	public void startTailingLog()
	{
		tailCheckBox.setSelected(true);
		getTextPane().startTailingLog();
	}
	
	public void stopTailingLog()
	{
		tailCheckBox.setSelected(false);
		getTextPane().stopTailingLog();
	}
		
	
	public void setTailCheckBoxEnabled(boolean b)
	{
		tailCheckBox.setEnabled(b);
	}
	
	public boolean tailCheckBoxSelected()
	{
		return tailCheckBox.isSelected();
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String source = evt.getActionCommand();
		if(source.equals("tail"))
		{
			if(tailCheckBox.isSelected())
				startTailingLog();
			else
				stopTailingLog();
		}
	}
	
	private MyPrint p;
	private JPanel panel;
	private DefaultStyledDocument doc;
	private MyTextPane textPane;
	private JScrollPane scrollPane;
	private JCheckBox tailCheckBox;
	private GridBagConstraints gbc;
}

public class Log 
				extends 	JFrame 
				implements 	ActionListener,
							DocumentListener,
							ChangeListener
{
	public Log()
	{
			DEBUG = true;
			p = new MyPrint();
			myFile = new MyFile();
			
			contentPane = getContentPane();
			
			setTitle("View System Log");
			
			titlePanel = new JPanel();
			titleLabel = new JLabel("View System Log");
			Font titleFont = new Font("Arial",Font.BOLD,14);
			titleLabel.setFont(titleFont);
			titlePanel.add(titleLabel);
			
			mainPanel = new JPanel(new BorderLayout());
			gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.NONE;
	        gbc.insets = new Insets(1,1,1,1);
	 		etchedBorder = BorderFactory.createEtchedBorder();
	 		mainPanel.setBorder(etchedBorder);
	       	mainPanel.setPreferredSize(new Dimension(990,460));
	       	mainPanel.setMinimumSize(new Dimension(990,460));
	 		
	 		buttonPanel = new JPanel(new GridBagLayout());
	 		buttonPanel.setBorder(etchedBorder);
	 		
	 		optionsPanel = new JPanel(new GridBagLayout());
	 		searchButton = new JButton("Search ==>");
	 		searchButton.addActionListener(this);
	 		searchButton.setActionCommand("search");
	 		
	 		searchTextField = new JTextField(30);
	 		searchTextField.setBackground(Color.white);
	 		searchTextField.getDocument().addDocumentListener(this);
	 		
	 		matchCaseCheckBox = new JCheckBox("Match Case");
	 		matchCaseCheckBox.addActionListener(this);
	 		matchCaseCheckBox.setActionCommand("matchCase");
	 		
	 		filterCheckBox = new JCheckBox("Filter");
	 		filterCheckBox.addActionListener(this);
	 		filterCheckBox.setActionCommand("filter");
	 		
	 		previousButton = new JButton("Previous");
	 		previousButton.addActionListener(this);
	 		previousButton.setActionCommand("previous");
		    previousButton.setEnabled(false);
	 		
	 		nextButton = new JButton("Next");
	 		nextButton.addActionListener(this);
	 		nextButton.setActionCommand("next");
		    nextButton.setEnabled(false);
		    
	 		add(optionsPanel,searchButton,gbc,1,1,1,1);
	 		add(optionsPanel,searchTextField,gbc,2,1,1,1);
	 		add(optionsPanel,matchCaseCheckBox,gbc,3,1,1,1);
	 		add(optionsPanel,filterCheckBox,gbc,4,1,1,1);
	 		add(optionsPanel,previousButton,gbc,5,1,1,1);
	 		add(optionsPanel,nextButton,gbc,6,1,1,1);
	 		
	 		sc = new StyleContext();
	 		createStyles(sc);
	 		
	 		tabs = new Hashtable();
			setUpTabbedPanel();
			
			add(buttonPanel,optionsPanel,gbc,1,1,1,1);
			add(buttonPanel,tabPanel,gbc,1,2,1,1);
			
	 		hideButton = new JButton("Exit");
	 		hideButton.addActionListener(this);
	 		hideButton.setActionCommand("exit");
	 		add(buttonPanel,hideButton,gbc,1,3,1,1);
			
	 		loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
	 		statusPanel = new JPanel(new BorderLayout());
			statusPanel.setPreferredSize(new Dimension(960,20));
			statusPanel.setMinimumSize(new Dimension(960,20));
	 		statusPanel.setBorder(loweredBevelBorder);
			statusLabel = new JLabel("");
	 		statusPanel.add(statusLabel,BorderLayout.WEST);
	 		
			mainPanel.add(titlePanel,BorderLayout.NORTH);
			mainPanel.add(buttonPanel,BorderLayout.CENTER);
			mainPanel.add(statusPanel,BorderLayout.SOUTH);
			
			contentPane.add(mainPanel);
			
			this.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
//			        setVisible(false);
					System.exit(0);
				}
			});
	}

    void add(Container p, Component c, GridBagConstraints gbc, int x, int y, int w, int h)
    {
		gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        p.add(c,gbc);
    }
    
    void createStyles(StyleContext sc)
    {
		Style myStyle;
		
		attributes = new Hashtable();
		
		//no style
		myStyle = sc.addStyle(null,null);
		StyleConstants.setBackground(myStyle,Color.white);
		attributes.put("none",myStyle);
		
		//highlighted
		myStyle = sc.addStyle(null,null);
		StyleConstants.setBackground(myStyle,Color.yellow);
		attributes.put("highlight",myStyle);
	}
	
    void setUpSystemLogTabPanel(StyleContext tsc)
	{
		sysLogTabPane = new MyTabPane(tsc);
		tabbedPane.addTab("System Log",sysLogTabPane);
		tabs.put("syslog",sysLogTabPane);
		myFile.makeCopy(SYSTEM_LOG,DUP_SYS_LOG);
	    sysLogTabPane.getTextPane().readFile(DUP_SYS_LOG,
	    							searchTextField.getText(),
	    							filterCheckBox.isSelected(),
	    							matchCaseCheckBox.isSelected(),
	    							attributes);
	    sysLogTabPane.setTailCheckBoxEnabled(true);
	}
	
	void setUpGUICLogTabPanel(StyleContext tsc)
	{
		guicLogTabPane = new MyTabPane(tsc);
		tabbedPane.addTab("GUIC Log",guicLogTabPane);
		tabs.put("guiclog",guicLogTabPane);
		myFile.makeCopy(GUIC_LOG,DUP_GUIC_LOG);
	}
	
	void setUpAASLogTabPanel(StyleContext tsc)
	{
		aasLogTabPane = new MyTabPane(tsc);
		tabbedPane.addTab("AAS Log",aasLogTabPane);
		tabs.put("aaslog",aasLogTabPane);
		myFile.makeCopy(AAS_LOG,DUP_AAS_LOG);
	}
    
    public void setUpTabbedPanel()
    {
        tabbedPane = new JTabbedPane();
        tabPanel = new JPanel();
 		setUpSystemLogTabPanel(sc);
		setUpGUICLogTabPanel(sc);
		setUpAASLogTabPanel(sc);
        tabPanel.add(tabbedPane);
		tabbedPane.setSelectedIndex(0);
        tabbedPane.addChangeListener(this);
	}
    
	public void changedUpdate(DocumentEvent e) 
	{
	    previousButton.setEnabled(false);
	    nextButton.setEnabled(false);
    }

	public void insertUpdate(DocumentEvent e) 
	{
	    previousButton.setEnabled(false);
	    nextButton.setEnabled(false);
    }

	public void removeUpdate(DocumentEvent e) 
	{
	    previousButton.setEnabled(false);
	    nextButton.setEnabled(false);
    }
    
    void stopTailingLogsExcept(String logName)
    {
	    Enumeration names;
	    for(names = tabs.keys(); names.hasMoreElements(); )
	    {
		    String otherLog = (String)names.nextElement();
		 	if(!otherLog.equals(logName))
		 	{
			 	MyTabPane tab = (MyTabPane)tabs.get(otherLog);
			 	tab.stopTailingLog();
		 	}
	    }
    }
    
    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();
	    
	    if(source.equals("search"))
	    {
	        int n = tabbedPane.getSelectedIndex();
	        if(n == 0)
	        {
				myFile.makeCopy(SYSTEM_LOG,DUP_SYS_LOG);
			    sysLogTabPane.getTextPane().readFile(DUP_SYS_LOG,
			    							searchTextField.getText(),
			    							filterCheckBox.isSelected(),
			    							matchCaseCheckBox.isSelected(),
			    							attributes);
			    sysLogTabPane.setTailCheckBoxEnabled(true);
				if(sysLogTabPane.getTextPane().foundMatches())
				    statusLabel.setText("Matches found");
				else
				    statusLabel.setText("No match found");
			}
	        else if(n == 1)
	        {
				myFile.makeCopy(GUIC_LOG,DUP_GUIC_LOG);
			    guicLogTabPane.getTextPane().readFile(DUP_GUIC_LOG,
			    							searchTextField.getText(),
			    							filterCheckBox.isSelected(),
			    							matchCaseCheckBox.isSelected(),
			    							attributes);
			    guicLogTabPane.setTailCheckBoxEnabled(true);
				if(guicLogTabPane.getTextPane().foundMatches())
				    statusLabel.setText("Matches found");
				else
				    statusLabel.setText("No match found");
			}
	        else if(n == 2)
	        {
				myFile.makeCopy(AAS_LOG,DUP_AAS_LOG);
			    aasLogTabPane.getTextPane().readFile(DUP_AAS_LOG,
			    							searchTextField.getText(),
			    							filterCheckBox.isSelected(),
			    							matchCaseCheckBox.isSelected(),
			    							attributes);
			    aasLogTabPane.setTailCheckBoxEnabled(true);
				if(aasLogTabPane.getTextPane().foundMatches())
				    statusLabel.setText("Matches found");
				else
				    statusLabel.setText("No match found");
			}
			
		    previousButton.setEnabled(true);
		    nextButton.setEnabled(true);
	    }
	    else if(source.equals("matchCase"))
	    {
		    previousButton.setEnabled(false);
		    nextButton.setEnabled(false);
	    }
	    else if(source.equals("filter"))
	    {
		    previousButton.setEnabled(false);
		    nextButton.setEnabled(false);
	    }
	    else if(source.equals("previous"))
	    {
	        int n = tabbedPane.getSelectedIndex();
	        if(n == 0)
	        {
				MyTabPane tab = (MyTabPane)tabs.get("syslog");
			    tab.getTextPane().previousMatch();
			    if(tab.getTextPane().isFirstMatch())
			    	statusLabel.setText("First match found");
			    else if(tab.getTextPane().isLastMatch())
			    	statusLabel.setText("Last match found");
			    else
			    	statusLabel.setText("");
			}
	        else if(n == 1)
	        {
				MyTabPane tab = (MyTabPane)tabs.get("guiclog");
			    tab.getTextPane().previousMatch();
			    if(tab.getTextPane().isFirstMatch())
			    	statusLabel.setText("First match found");
			    else if(tab.getTextPane().isLastMatch())
			    	statusLabel.setText("Last match found");
			    else
			    	statusLabel.setText("");
			}
	        if(n == 2)
	        {
				MyTabPane tab = (MyTabPane)tabs.get("aaslog");
			    tab.getTextPane().previousMatch();
			    if(tab.getTextPane().isFirstMatch())
			    	statusLabel.setText("First match found");
			    else if(tab.getTextPane().isLastMatch())
			    	statusLabel.setText("Last match found");
			    else
			    	statusLabel.setText("");
			}
	    }
	    else if(source.equals("next"))
	    {
	        int n = tabbedPane.getSelectedIndex();
	        if(n == 0)
	        {
				MyTabPane tab = (MyTabPane)tabs.get("syslog");
			    tab.getTextPane().nextMatch();
			    if(tab.getTextPane().isFirstMatch())
			    	statusLabel.setText("First match found");
			    else if(tab.getTextPane().isLastMatch())
			    	statusLabel.setText("Last match found");
			    else
			    	statusLabel.setText("");
			}
	        else if(n == 1)
	        {
				MyTabPane tab = (MyTabPane)tabs.get("guiclog");
			    tab.getTextPane().nextMatch();
			    if(tab.getTextPane().isFirstMatch())
			    	statusLabel.setText("First match found");
			    else if(tab.getTextPane().isLastMatch())
			    	statusLabel.setText("Last match found");
			    else
			    	statusLabel.setText("");
			}
	        else if(n == 2)
	        {
				MyTabPane tab = (MyTabPane)tabs.get("aaslog");
			    tab.getTextPane().nextMatch();
			    if(tab.getTextPane().isFirstMatch())
			    	statusLabel.setText("First match found");
			    else if(tab.getTextPane().isLastMatch())
			    	statusLabel.setText("Last match found");
			    else
			    	statusLabel.setText("");
			}
	    }
	    else if(source.equals("searchChanged"))
	    {
		    previousButton.setEnabled(false);
		    nextButton.setEnabled(false);
	    }
	    else if(source.equals("exit"))
	    {
// 	        setVisible(false);
			System.exit(0);
    	}
	}
	
    public void stateChanged(ChangeEvent evt)
    {
        JTabbedPane pane = (JTabbedPane)evt.getSource();
        int n = tabbedPane.getSelectedIndex();
        
        if(n == 0)
        {
			stopTailingLogsExcept("syslog");
        }
        else if(n == 1)
        {
			stopTailingLogsExcept("guiclog");
        }
        else if(n == 2)
        {
			stopTailingLogsExcept("aaslog");
        }
    }
	
	private MyPrint p;
	private MyFile myFile;
	private Container contentPane;
	private boolean DEBUG;
	private GridBagConstraints gbc;
	private Border etchedBorder,
					loweredBevelBorder;
	private JPanel mainPanel,
					titlePanel,
					optionsPanel,
					logPanel,
					statusPanel,
					tabPanel,
					buttonPanel;
	private JScrollPane systemScrollPane,
						guicScrollPane,
						aasScrollPane;
	private JTabbedPane tabbedPane;
	private MyTabPane sysLogTabPane,
						guicLogTabPane,
						aasLogTabPane;
	private JLabel titleLabel,
					statusLabel;
	private JButton hideButton,
					searchButton,
					previousButton,
					nextButton;
	private JCheckBox matchCaseCheckBox,
						filterCheckBox,
						sysLogCheckBox,
						guicLogCheckBox,
						aasLogCheckBox;
	private JTextField searchTextField;
	private StyleContext sc;
	private String [] stringArray;
	private Hashtable attributes,
						tabs;
	final static private String SYSTEM_LOG = "System_Log";
	final static private String GUIC_LOG = "run_guic.log";
	final static private String AAS_LOG = "run_aas.log";
	final static private String DUP_SYS_LOG = "DUP_System_Log";
	final static private String DUP_GUIC_LOG = "DUP_GUIC_Log";
	final static private String DUP_AAS_LOG = "DUP_AAS_Log";
// 	final static private String SYSTEM_LOG = 	"/h/AFATDS/data/scratch/System_Log";
// 	final static private String DUP_SYS_LOG = 	"/h/AFATDS/data/scratch/DUP_System_Log";
// 	final static private String GUIC_LOG = 		"/h/AFATDS/data/scratch/run_guic.log";
// 	final static private String DUP_GUIC_LOG = 	"/h/AFATDS/data/scratch/DUP_GUIC_Log";
// 	final static private String AAS_LOG = 		"/h/AFATDS/data/scratch/run_aas.log";
// 	final static private String DUP_AAS_LOG = 	"/h/AFATDS/data/scratch/DUP_AAS_Log";
}