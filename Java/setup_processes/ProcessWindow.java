import java.io.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


public class ProcessWindow extends JFrame implements ActionListener
{
	public ProcessWindow()
	{
		DEBUG = false;
		
		p = new MyPrint();
		
		String [] fileNames = getFileNames();
		originalFile = fileNames[0];
		masterFile = fileNames[1];
		tempFile = fileNames[2];
		processNamesFile = fileNames[3];

		saveOriginalFile(originalFile,masterFile);		
		
		if(isPresent(masterFile))
		{
			df = new DataFile(DEBUG,fileNames);
			
			setTitle("Setup Processes");
			
			windowProcessList = new Vector();
			contentPane = getContentPane();
			
			//create mainPanel on which all other panels will reside
	        mainPanel = new JPanel(new BorderLayout());
	       	mainPanel.setPreferredSize(new Dimension(490,425));
	       	mainPanel.setMinimumSize(new Dimension(490,425));
	       	
			gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.NONE;
	 		etchedBorder = BorderFactory.createEtchedBorder();
	 		mainPanel.setBorder(etchedBorder);
	 		
	 		titlePanel = new JPanel(new GridBagLayout());
		    titleLabel = new JLabel("Setup Processes - Original Settings");
		    Font bigFont = new Font("Arial",Font.BOLD,14);
		    titleLabel.setFont(bigFont);
		    add(titlePanel,titleLabel,gbc,1,1,1,1);
		    
	 		internalProcessMainPanel = new JPanel(new GridBagLayout());
	 		internalProcessMainPanel.setBorder(etchedBorder);
	 		
	 		//create panel to display column headers for processes
	 		headerForProcessPanel = new JPanel(new GridBagLayout());
	 		setUpHeaderForProcessPanel();
	        add(internalProcessMainPanel,headerForProcessPanel,gbc,1,1,1,1);

	        //setup Single Processes on an internal panel		
	 		processPanel = new JPanel(new GridBagLayout());
			processArray = df.getProcessNames();
			processPanel.setBorder(etchedBorder);
			
	        setUpSingleProcessesPanel(processArray);
			
	        scrollPane = new JScrollPane(processPanel,
	        							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	        							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	       	scrollPane.setPreferredSize(new Dimension(480,325));
	       	scrollPane.setMinimumSize(new Dimension(480,325));
	        gbc.insets = new Insets(1,1,1,1);
	        add(internalProcessMainPanel,scrollPane,gbc,1,2,1,1);
	        
			//set up main panel for single processes		
			singleProcessMainPanel = new JPanel(new GridBagLayout());
			setUpSingleProcessMainPanel();
	        gbc.insets = new Insets(1,1,1,1);
	        //set singleProcessMainPanel on fifth row of window
	        add(internalProcessMainPanel,singleProcessMainPanel,gbc,1,5,1,1);
	        
	        mainPanel.add(titlePanel,BorderLayout.NORTH);
	        mainPanel.add(internalProcessMainPanel,BorderLayout.CENTER);
	        
	        contentPane.add(mainPanel);

	        //fill in process panel with original settings
	        resetAllProcessesToOriginalSettings(df.getOriginalSettings(masterFile));
        }
        else
        {
	    	p.print("initial_configuration.m4 file is missing.  Shutdown and ensure file is present before continuiing");
	        System.exit(0);
        }
        
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	String [] getFileNames()
	{
		if(DEBUG)
			p.print("inside getFileNames()");
			
		String [] names = new String[4];
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("filenames.dat")));
			for(int i=0; i<names.length; i++)
			{
				names[i] = br.readLine();
			}
		}
		catch (Exception e)
		{
			p.print(e + " in getFileNames()");
		}	
		return names;
	}
	
    void add(Container p, Component c, GridBagConstraints gbc, int x, int y, int w, int h)
    {
		gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        p.add(c,gbc);
    }
    
    public void setUpHeaderForProcessPanel()
    {
	    
        gbc.insets = new Insets(5,50,5,70);
        processLabel = new JLabel("Process Name");
		add(headerForProcessPanel,processLabel,gbc,1,2,1,1);
		
        gbc.insets = new Insets(5,40,5,35);
		commentLabel = new JLabel("Commented");
		add(headerForProcessPanel,commentLabel,gbc,2,2,1,1);
		
        gbc.insets = new Insets(5,5,5,5);
		checkinsLabel = new JLabel("Beats On");
		add(headerForProcessPanel,checkinsLabel,gbc,3,2,1,1);
	}
	
	void selectAllProcesses(String attribute, boolean b)
	{
		for(int i=0; i<windowProcessList.size(); i++)
		{
			SingleProcessWindow singleProcess 
				= (SingleProcessWindow)windowProcessList.elementAt(i);
				
			if(attribute.equals("selectAllCmt"))
			{
				singleProcess.setCommentedOutCheckBox(b);
			}
			else if(attribute.equals("unselectAllCmt"))
			{
				singleProcess.setCommentedOutCheckBox(b);
			}
			else if (attribute.equals("selectAllBeat"))
			{
				singleProcess.setHeartbeatRequiredCheckBox(b);
			}
			else if (attribute.equals("unselectAllBeat"))
			{
				singleProcess.setHeartbeatRequiredCheckBox(b);
			}
		}
	}
	
	public void setUpSingleProcessesPanel(String [] processArray)
	{
		if(DEBUG)
			p.print("inside setUpSingleProcessesPanel");
			
		int row = 0;
		int name = 0;
		
		for (name=0, row=1; name<processArray.length; name++, row++)
		{
	        //All components will not grow/shrink with window
	        gbc.fill = GridBagConstraints.NONE;
	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.insets = new Insets(5,10,5,10);

			process = new SingleProcessWindow(processArray[name]);
			add(processPanel,process.getLabel(),gbc,1,row,1,1);
			add(processPanel,process.getCommentedOutCheckBox(),gbc,2,row,1,1);
	        gbc.insets = new Insets(5,10,5,5);
			add(processPanel,process.getHeartbeatRequiredCheckBox(),gbc,3,row,1,1);
//			add(processPanel,process.getResetButton(),gbc,4,row,1,1);
			
			windowProcessList.add(process);
		}
		
		selectAllCmtButton = new JButton("Select All");
		selectAllCmtButton.setActionCommand("selectAllCmt");
		selectAllCmtButton.addActionListener(this);
		
		unselectAllCmtButton = new JButton("Unselect All");
		unselectAllCmtButton.setActionCommand("unselectAllCmt");
		unselectAllCmtButton.addActionListener(this);
		
		selectAllBeatButton = new JButton("Select All");
		selectAllBeatButton.setActionCommand("selectAllBeat");
		selectAllBeatButton.addActionListener(this);
		
		unselectAllBeatButton = new JButton("Unselect All");
		unselectAllBeatButton.setActionCommand("unselectAllBeat");
		unselectAllBeatButton.addActionListener(this);
			
        gbc.insets = new Insets(5,5,5,5);
		add(processPanel,selectAllCmtButton,gbc,2,row,1,1);
		add(processPanel,selectAllBeatButton,gbc,3,row,1,1);
		
		row++;
		
		add(processPanel,unselectAllCmtButton,gbc,2,row,1,1);
		add(processPanel,unselectAllBeatButton,gbc,3,row,1,1);
	}
	
	public void setUpSingleProcessMainPanel()
	{
		if(DEBUG)
			p.print("inside setUpSingleProcessessingleProcessMainPanel");
		
		//All components will not grow/shrink with window
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5,10,5,10);
        
		exitButton = new JButton("Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		//set exitButton in 1st column of singleProcessMainPanel
		add(singleProcessMainPanel,exitButton,gbc,1,1,1,1);
		
		resetAllButton = new JButton("Reset all to original state");
		resetAllButton.setActionCommand("resetAll");
		resetAllButton.addActionListener(this);
		//set resetAllButton in 3rd column of singleProcessMainPanel
		add(singleProcessMainPanel,resetAllButton,gbc,3,1,1,1);
		
		setupButton = new JButton("Click to setup processes");
		setupButton.setActionCommand("setup");
		setupButton.addActionListener(this);
		//set setupButton in 4th column of singleProcessMainPanel
		add(singleProcessMainPanel,setupButton,gbc,4,1,1,1);
		
	}
		
	void resetAllProcessesToOriginalSettings(Vector df)
	{
		if(DEBUG)
			p.print("inside displayAllOriginalSettings()");
		
		MyProcess process;
		SingleProcessWindow singleProcessWindow;
		
		int numberOfWindowProcesses = windowProcessList.size();
		int numberOfMyProcesses = df.size();
		
		String windowName = " ";
		String processName = " ";
		
		MyProcessLoop:
		for(int i=0; i<numberOfMyProcesses; i++)
		{
			SingleProcessWindowLoop:
			for(int j=0; j<numberOfWindowProcesses; j++)
			{
				processName = ((MyProcess)df.elementAt(i)).getName();
				windowName = ((SingleProcessWindow)windowProcessList.elementAt(j)).getName();
				
				if(processName.equals(windowName))
				{
					process = (MyProcess)df.elementAt(i);
					singleProcessWindow = (SingleProcessWindow)windowProcessList.elementAt(j);
					
					singleProcessWindow.setHeartbeatRequiredCheckBox(process.getOrigHeartbeatRequired());
					singleProcessWindow.setCommentedOutCheckBox(process.getOrigCommentedOut());
				}
			}
		}
	}
	
	boolean isPresent(String f)
	{
		File thisFile = new File(f);
		boolean exists = false;
		
		if (thisFile.exists())
			exists = true;
			
		return exists;
	}
	
    public void saveOriginalFile(String origFile, String mastFile)
    {
	    if(isPresent(origFile) && !isPresent(mastFile))
	    {
    		Copy copy = new Copy(origFile,mastFile);
    	}
    }
    
    public void applyChangesToFile(Vector v, String modFile, String tmpFile)
    {
	    df.updateFile(v,modFile,tmpFile);
    }
    
    public void changeTitle(String s)
    {
	    titleLabel.setText(s);
    }
		    
    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();
	    if(DEBUG)
	    	p.print(source);
	    	
	    if (source.equals("selectAllCmt"))
	    {
			selectAllProcesses("selectAllCmt",true);
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("unselectAllCmt"))
	    {
			selectAllProcesses("unselectAllCmt",false);
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("selectAllBeat"))
	    {
			selectAllProcesses("selectAllBeat",true);
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("unselectAllBeat"))
	    {
			selectAllProcesses("unselectAllBeat",false);
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("comment"))
	    {
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("heartbeats"))
	    {
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("reset"))
	    {
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("setup"))
	    {
		    saveOriginalFile(originalFile,masterFile);
		    applyChangesToFile(windowProcessList,originalFile,tempFile);
		    changeTitle("Setup Processes - Settings Saved");
	    }
	    else if (source.equals("resetAll"))
	    {
		    resetAllProcessesToOriginalSettings(df.getOriginalSettings(masterFile));
       		Copy copy = new Copy(masterFile,originalFile);
		    changeTitle("Setup Processes - *** Settings Unsaved *** ");
	    }
	    else if (source.equals("exit"))
	    {
		    System.exit(0);
	    }
    }

    private String [] processArray;
										
	private GridBagConstraints gbc;
	private Container contentPane;
	private JPanel 	mainPanel,
					titlePanel,
					internalProcessMainPanel,
					headerForProcessPanel, 
					singleProcessMainPanel, 
					processPanel,
					mainScrollPane;
	private JScrollPane scrollPane;
	private Border etchedBorder;
	private JLabel titleLabel,
					processLabel,
					commentLabel;
	private JLabel checkinsLabel;
	private JButton resetAllButton,
					exitButton,
					setupButton,
					selectAllCmtButton,
					selectAllBeatButton,
					unselectAllCmtButton,
					unselectAllBeatButton;
	private static Vector windowProcessList;
	private SingleProcessWindow process;
	private MyPrint p;
	private boolean DEBUG;
	private DataFile df;
	private String masterFile;
	private String originalFile;
	private String tempFile;
	private String processNamesFile;
}


