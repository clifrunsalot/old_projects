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
		
		myFile = new MyFile();
		
		String [] fileNames = getFileNames();
		originalFile = fileNames[0];
		masterFile = fileNames[1];
		tempFile = fileNames[2];

		saveOriginalFile(originalFile,masterFile);		
		
		if(isPresent(masterFile))
		{
			df = new DataFile(DEBUG,fileNames);
			
			setTitle("Setup Processes");
			
			windowProcessList = new Vector();
			contentPane = getContentPane();
			
			//create mainPanel on which all other panels will reside
	        mainPanel = new JPanel(new BorderLayout());
	       	mainPanel.setPreferredSize(new Dimension(490,470));
	       	mainPanel.setMinimumSize(new Dimension(490,470));
	       	
			gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.NONE;
	 		etchedBorder = BorderFactory.createEtchedBorder();
	 		mainPanel.setBorder(etchedBorder);
	 		
	 		titlePanel = new JPanel();
		    titleLabel = new JLabel("Setup Processes");
		    Font bigFont = new Font("Arial",Font.BOLD,14);
		    titleLabel.setFont(bigFont);
		    titlePanel.add(titleLabel);
		    
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
	        
	        statusPanel = new JPanel(new BorderLayout());
	        //setting the size ensures that the panel does not vanish when there
	        // is nothing in it.
			statusPanel.setPreferredSize(new Dimension(480,20));
			statusPanel.setMinimumSize(new Dimension(480,20));
	 		loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
	 		statusPanel.setBorder(loweredBevelBorder);
	        statusLabel = new JLabel("Original Settings");
	        
	        statusPanel.add(statusLabel,BorderLayout.WEST);
	        
	        mainPanel.add(titlePanel,BorderLayout.NORTH);
	        mainPanel.add(internalProcessMainPanel,BorderLayout.CENTER);
	        mainPanel.add(statusPanel,BorderLayout.SOUTH);
	        
	        contentPane.add(mainPanel);

	        //fill in process panel with original settings
	        resetAllProcessesToOriginalSettings(df.getOriginalSettings(masterFile));
        }
        else
        {
	    	p.print("initial_configuration.m4 file is missing.  Shutdown and ensure file is present before continuiing");
	        this.setVisible(false);
        }
        
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
		        setVisible(false);
			}
		});
	}
	
	String [] getFileNames()
	{
		if(DEBUG)
			p.print("inside getFileNames()");
			
		String [] names = new String[3];
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
		
		resetAllButton = new JButton("Reset all to original state");
		resetAllButton.setActionCommand("resetAll");
		resetAllButton.addActionListener(this);
		
		setupButton = new JButton("Click to setup processes");
		setupButton.setActionCommand("setup");
		setupButton.addActionListener(this);
		
		hideButton = new JButton("Hide");
		hideButton.setActionCommand("exit");
		hideButton.addActionListener(this);
		
        buttonPanel = new JPanel();
        buttonPanel.add(resetAllButton);
        buttonPanel.add(setupButton);
        buttonPanel.add(hideButton);
        
        gbc.insets = new Insets(1,1,1,1);
        add(singleProcessMainPanel,buttonPanel,gbc,1,1,1,1);
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
    		myFile.makeCopy(origFile,mastFile);
    	}
    }
    
    public void applyChangesToFile(Vector v, String modFile, String tmpFile)
    {
	    df.updateFile(v,modFile,tmpFile);
    }
    
    public void changeTitle(String s)
    {
	    statusLabel.setText(s);
    }
		    
    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();
	    if(DEBUG)
	    	p.print(source);
	    	
	    if (source.equals("selectAllCmt"))
	    {
			selectAllProcesses("selectAllCmt",true);
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("unselectAllCmt"))
	    {
			selectAllProcesses("unselectAllCmt",false);
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("selectAllBeat"))
	    {
			selectAllProcesses("selectAllBeat",true);
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("unselectAllBeat"))
	    {
			selectAllProcesses("unselectAllBeat",false);
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("comment"))
	    {
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("heartbeats"))
	    {
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("reset"))
	    {
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("setup"))
	    {
		    saveOriginalFile(originalFile,masterFile);
		    applyChangesToFile(windowProcessList,originalFile,tempFile);
		    changeTitle("Settings Saved");
	    }
	    else if (source.equals("resetAll"))
	    {
		    resetAllProcessesToOriginalSettings(df.getOriginalSettings(masterFile));
       		myFile.makeCopy(masterFile,originalFile);
		    changeTitle("*** Settings Unsaved *** ");
	    }
	    else if (source.equals("exit"))
	    {
		    this.setVisible(false);
	    }
    }

    private String [] processArray;
										
	private GridBagConstraints gbc;
	private Container contentPane;
	private JPanel 	mainPanel,
					titlePanel,
					internalProcessMainPanel,
					headerForProcessPanel,
					buttonPanel,
					singleProcessMainPanel, 
					processPanel,
					statusPanel,
					mainScrollPane;
	private JScrollPane scrollPane;
	private Border etchedBorder,
					loweredBevelBorder;
	private JLabel titleLabel,
					processLabel,
					commentLabel,
					checkinsLabel,
					statusLabel;
	private JButton resetAllButton,
					hideButton,
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
	private MyFile myFile;
	private String masterFile;
	private String originalFile;
	private String tempFile;
}


