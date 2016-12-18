/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

package afatds.app.ppt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;




/**
*	This class creates the main window that represents the test driver 
*	used by the operator to test each of the protocols.
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/
class ProtocolTestDriver extends JFrame
						implements ActionListener
{
	/**
	*	This is a variable of the MyPrint class.  Use this for debugging purposes.  See the MyPrint class for usages.
	*/
	private MyPrint p;
	
	/**
	*	This is the container for the application.
	*/
	private Container	contentPane;
	
	/**
	*	Border design(s) in the application.
	*/
    private Border		etchedBorder,
    					loweredBevelBorder;
    					
    /**
    *	Panel(s) in the application.
    */
    private JPanel		rearMostPanel,
    					protocolLabelAndComboBoxPanel,
    					protocolAndIndicesPanel,
    					protocolAndResultsPanel,
    					indicesPanel,
    					resultsPanel,
    					scrollingResultsPanel;
    					
    /**
    *	This is the scrollpane for the indices.
    */
    private JScrollPane indicesScrollPane;
    /**
    *	This is the scrollpane for the results.
    */
    private JScrollPane resultsScrollPane;
    					
    /**
    *	This the main menuBar for the application.
    */
    private JMenuBar	menuBar;
    
    /**
    *	This is the file Menu.
    */
    private JMenu		fileMenu;
    
    /**
    *	This represents an item on the fileMenu.
    */
    private JMenuItem	exitMenuItem,
    					test220AMenuItem,
	    				test220CMenuItem;
	    				
	/**
	*	This is the comboBox for the protocol list.
	*/
	private JComboBox	protocolComboBox;
	/**
	*	This is the comboBox for the object list.
	*/
	private JComboBox	objectComboBox;
						
	/**
	*	This is a label used for the Protocol comboBox.
	*/
	private JLabel		protocolLabel;
	/**
	*	This is a label used for the Object comboBox.
	*/
	private JLabel		objectLabel;
						
	/**
	*	This the button that triggers retrieving the results.
	*/
	private JButton		retrieveButton;
	
	/**
	*	This list is used to hold the various attributes of a type.
	*/
	private Vector attributeList;
	
	
	/**
	*	This represents the file that contains the indices associated with the A220 protocol.  It should reside 
	*	in the same directory as the calling application.  The format for this file must be
	*	correct in order for it to be processed correctly.
	*/
	private String A220_INDICES_FILE = "a220_indices.txt";
	
	/**
	*	This represents the file that contains the indices associated with the C220 protocol. It should reside 
	*	in the same directory as the calling application.  The format for this file must be
	*	correct in order for it to be processed correctly.
	*/
	private String C220_INDICES_FILE = "c220_indices.txt";
	
	/**
	*	This represents the file that contains the types associated with the A220 protocol.  It should reside 
	*	in the same directory as the calling application.  The format for this file must be
	*	correct in order for it to be processed correctly.
	*/
	private String A220TYPESFILE = "a220_types.txt";
	
	/**
	*	This represents the file that contains the types associated with the C220 protocol.  It should reside 
	*	in the same directory as the calling application.  The format for this file must be
	*	correct in order for it to be processed correctly.
	*/
	private String C220TYPESFILE = "c220_types.txt";
	
	/**
	*	This represents the file that contains the indices selected by the operator.  It will reside in the 
	*	/h/AFATDS/data/scratch directory.  This file is generated by this class and then 
	*	processed by an Ada program; so, it is important to maintain its format.  Any changes
	*	to the format must be coordinated between this class and the Ada driver that reads this
	*	file.
	*/
	private String SELECTED_INDICES_FILE = "/h/AFATDS/data/scratch/selected_indices.txt";
	
	/**
	*	This represents the file that contains the results generated by the Ada program and which 
	*	are read in by this class and displayed in the resultsPanel.  It will reside in the 
	*	/h/AFATDS/data/scratch directory.  This file is generated by the Ada driver; so, it is
	*	important to maintain its format.  Any changes to the format must be coordinated 
	*	between Ada driver that creates this file and this class.
	*	
	*	IMPORTANT: The path to this file reference must be the same fullpath provided by the Ada driver!
	*/
	private String RESULTS_FILE = "/h/AFATDS/data/scratch/results.txt";

	/**
	*	This array contains the names of the protocols to be tested.  Each item in 
	*	the array should have its own set of indices and types files.
	*/	
	final private Vector protocolNames;
							
	/**
	*	This constructs an object of the class.
	*/
	public ProtocolTestDriver()
	{
		p = new MyPrint();

		protocolNames = new Vector();		
		protocolNames.add("A220");
		protocolNames.add("C220");
		
		etchedBorder = BorderFactory.createEtchedBorder();
		loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		
		setTitle("Protocol Test Driver");
		contentPane = getContentPane();
		
		rearMostPanel = new JPanel(new BorderLayout());
		
		setUpMenuBar();
		setUpProtocolAndResultsPanel();
		
		rearMostPanel.add(menuBar, BorderLayout.NORTH);
		rearMostPanel.add(protocolAndResultsPanel,BorderLayout.CENTER);
		
		rearMostPanel.setPreferredSize(new Dimension(575,500));
		rearMostPanel.setMinimumSize(new Dimension(575,550));
		
		contentPane.add(rearMostPanel);
		
	    addWindowListener(new WindowAdapter()
	    {
	        public void windowClosing(WindowEvent e)
	        {
	            System.exit(0);
	        }
	    });
	}
	
    /**
    *	Adds the component c to the container p.
    *	
    *	@param p is the parent container
    *	@param c is the component to add to p
    *	@param gbc is the GridBagConstraints to establish the layout
    *	@param x is the row in GridBagLayout
    *	@param y is the column in GridBagLayout
    *	@param w is the number of row cells to be filled by the component
    *	@param h is the number of column cells to be filled by component
    *	
    *	@return void
    */
    private void add(Container p, Component c, GridBagConstraints gbc,
        int x, int y, int w, int h)
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        p.add(c,gbc);
    }
    
	/**
	*	Creates a menu with the associated menuItems, actionCommands, and
	*	separators.
	*	
	*	@param menu is a JMenu object
	*	@param menuItems is an array of JMenuItems
	*	@param actionCommands is an array of action commands
	*	@param separators is an array of separators, each of which can either be "" or "separator"
	*	
	*	@return void
	*/
    private void setUpMenu(	JMenu menu,
    						JMenuItem [] menuItems, 
    						String [] actionCommands, 
    						String [] separators)
    {
	    for(int i=0; i<menuItems.length; i++)
	    {
		    if(separators[i].equals("separator"))
		    {
			    menu.addSeparator();
		    }
		    menuItems[i].addActionListener(this);
		    menuItems[i].setActionCommand(actionCommands[i]);
		    menu.add(menuItems[i]);
	    }
    }
    
    
    /**
    *	Sets up the File menu.
    *	
    *	@return void
    */
    private void setUpFileMenu()
    {
	    fileMenu = new JMenu("File");
	    fileMenu.setBackground(Color.lightGray);
    
	    //Create objects here to enable/disable later.
	    exitMenuItem = new JMenuItem("Exit");
	    
	    JMenuItem [] menuItems = {exitMenuItem};
	    							
	    String [] actionCommands = {"exit"};
	    								
	    String [] separators = {""};
	    								
	    setUpMenu(fileMenu, menuItems, actionCommands, separators);
    }
    
    
    /**
    *	Sets up the menu bar.
    *	
    *	@return void
    */
	private void setUpMenuBar()
	{
		menuBar = new JMenuBar();
	    menuBar.setBackground(Color.lightGray);
		setUpFileMenu();
		menuBar.add(fileMenu);
	}

	/**
	*	Adds a array of Strings to comboBox.
	*	
	*	@param comboBox is a JComboBox
	*	@param items is an array of strings to be added to the comboBox
	*	
	*	@return void
	*/	
	private void setUpComboBox(JComboBox comboBox, Vector items)
	{
		GenericComboBoxModel model = new GenericComboBoxModel(items);
		comboBox.setModel(model);
		comboBox.setSelectedIndex(0);
	}
	
	
	/**
	*	Sets up the panel that holds the protocol combobox, object combobox, and retrieve button
    *	and the panel that holds the protocol indices comboboxes.
	*	
	*	<pre>
	*		|------------------------protocolAndIndicesPanel-------------------|
	*		||--------------------protocolLabelAndComboBoxPanel---------------||
	*		||                                                                ||
	*		||    label:combobox   label:combobox      retrieve button        ||
	*		||                                                                ||
	*		||----------------------------------------------------------------||
	*		|                                                                  |
	*		||----------------------indicesScrollPane-------------------------||
	*		|||------------------------indicesPanel--------------------------|||
	*		|||                                                              |||
	*		|||                    label:combobox                            |||
	*		||| 	               label:combobox                            |||
	*		|||                    label:combobox                            |||
	*		|||                                                              |||
	*		|||--------------------------------------------------------------|||
	*		||----------------------------------------------------------------||
	*		|------------------------------------------------------------------|
	*	</pre>
	*		
	*	<br>
	*	
	*	@return void
	*/	
	private void setUpProtocolPanel()
	{
		/**
		*	protocolLabelAndComboBoxPanel
		*/
		protocolLabelAndComboBoxPanel = new JPanel(new GridBagLayout());
		protocolLabelAndComboBoxPanel.setBorder(etchedBorder);
		protocolLabelAndComboBoxPanel.setPreferredSize(new Dimension(550,50));
		protocolLabelAndComboBoxPanel.setMinimumSize(new Dimension(550,50));
		protocolLabelAndComboBoxPanel.setMaximumSize(new Dimension(550,50));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(1,5,1,5);
		gbc.fill = GridBagConstraints.NONE;
		
		protocolLabel = new JLabel("Protocol: ");
		
		protocolComboBox = new JComboBox();
		setUpComboBox(protocolComboBox,protocolNames);
		protocolComboBox.setActionCommand("protocolComboBox");
		protocolComboBox.addActionListener(this);
		
		objectLabel = new JLabel("Object: ");
		objectComboBox = new JComboBox();
		objectComboBox.setActionCommand("objectComboBox");
		objectComboBox.addActionListener(this);
		
		retrieveButton = new JButton("Retrieve Attributes");
       	retrieveButton.setEnabled(false);
		retrieveButton.addActionListener(this);
		retrieveButton.setActionCommand("retrieveButton");
		
		gbc.anchor = GridBagConstraints.WEST;
		add(protocolLabelAndComboBoxPanel,protocolLabel,gbc,1,1,1,1);
		gbc.anchor = GridBagConstraints.EAST;
		add(protocolLabelAndComboBoxPanel,protocolComboBox,gbc,2,1,1,1);
		
		gbc.anchor = GridBagConstraints.WEST;
		add(protocolLabelAndComboBoxPanel,objectLabel,gbc,3,1,1,1);
		gbc.anchor = GridBagConstraints.EAST;
 		add(protocolLabelAndComboBoxPanel,objectComboBox,gbc,4,1,1,1);
 		
 		add(protocolLabelAndComboBoxPanel,retrieveButton,gbc,5,1,1,1);
 		
		//
		//indicesPanel
		//
		indicesPanel = new JPanel(new GridBagLayout());
		
        indicesScrollPane = new JScrollPane(indicesPanel,
        							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		indicesScrollPane.setPreferredSize(new Dimension(550,200));
		indicesScrollPane.setMinimumSize(new Dimension(550,100));
		
		//
		//protocolAndIndicesPanel
		//
		protocolAndIndicesPanel = new JPanel(new BorderLayout());
		protocolAndIndicesPanel.setPreferredSize(new Dimension(550,300));
		protocolAndIndicesPanel.setMinimumSize(new Dimension(550,300));
		protocolAndIndicesPanel.setMaximumSize(new Dimension(550,300));
		
		protocolAndIndicesPanel.add(protocolLabelAndComboBoxPanel,BorderLayout.NORTH);
		protocolAndIndicesPanel.add(indicesScrollPane,BorderLayout.CENTER);
	}
	
	/**
	*	This panel displays the output of the Ada program, which is called by this class
	*	when the operator clicks on the Retrieve button.  The format is:
	*	
	*	<pre>
	*		attribute1: value1
	*		attribute2: value2
	*		attribute3: value3
	*	
	*		...
	*	</pre>
	*	
	*	@return void
	*/
	private void setUpResultsPanel()
	{
		resultsPanel = new JPanel(new BorderLayout(10,10));
		
		scrollingResultsPanel = new JPanel(new GridBagLayout());
		resultsScrollPane = new JScrollPane(scrollingResultsPanel,
        							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultsScrollPane.setPreferredSize(new Dimension(550,300));
		resultsScrollPane.setMinimumSize(new Dimension(550,100));
		
		resultsPanel.add(resultsScrollPane);
	}
	
	/**
	*	Sets up protocol and results panels.
	*	
	*	<pre>
	*		|---------------------protocolAndResultsPanel-------------------------------|
	*		|                                                                           |
	*		| |-------------------------splitPane-------------------------------------| |
	*		| |  |------------------------protocolAndIndicesPanel--------------------|| |
	*		| |  | |--------------------protocolLabelAndComboBoxPanel--------------| || |
	*		| |  | |                                                               | || |
	*		| |  | |    label:combobox   label:combobox      retrieve button       | || |
	*		| |  | |                                                               | || |
	*		| |  | |---------------------------------------------------------------| || |
	*		| |  |                                                                   || |
	*		| |  | |----------------------indicesScrollPane------------------------| || |
	*		| |  | | |------------------------indicesPanel-----------------------| | || |
	*		| |  | | |                                                           | | || |
	*		| |  | | |                    label:combobox                         | | || |
	*		| |  | | |                    label:combobox                         | | || |
	*		| |  | | |                    label:combobox                         | | || |
	*		| |  | | |                                                           | | || |
	*		| |  | | |-----------------------------------------------------------| | || |
	*		| |  | |---------------------------------------------------------------| || |
	*		| |  |-------------------------------------------------------------------|| |
	*		| |                                                                       | |
	*		| |====================== splitPane movable bar ==========================| |
	*		| |                                                                       | |
	*		| |                                                                       | |
	*		| |  |------------------------resultsPanel-----------------------------|  | |
	*		| |  |                                                                 |  | |
	*		| |  |                   attribute: value                              |  | |
	*		| |  |                   attribute: value                              |  | |
	*		| |  |                   attribute: value                              |  | |
	*		| |  |                                                                 |  | |
	*		| |  |                                                                 |  | |
	*		| |  |-----------------------------------------------------------------|  | |
	*		| |-----------------------------------------------------------------------| |
	*		|---------------------------------------------------------------------------|
	*	
	*	</pre>
	*	<br>
	*	@return void
	*/
	private void setUpProtocolAndResultsPanel()
	{
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		setUpProtocolPanel();
		setUpResultsPanel();
		splitPane.setTopComponent(protocolAndIndicesPanel);
		splitPane.setBottomComponent(resultsPanel);
		
		protocolAndResultsPanel = new JPanel(new BorderLayout());
		protocolAndResultsPanel.add(splitPane,BorderLayout.CENTER);
	}
	
	/**
	*	Repaints all panels.
	*	
	*	@return void
	*/
	private void repaintAllPanels()
	{
		rearMostPanel.repaint();
		protocolLabelAndComboBoxPanel.repaint();
		protocolAndIndicesPanel.repaint();
		protocolAndResultsPanel.repaint();
		indicesPanel.repaint();
		resultsPanel.repaint();
		scrollingResultsPanel.repaint();
		indicesScrollPane.repaint();
		resultsScrollPane.repaint();
	}
	
	/**
	*	Sets up objectComboBox based on protocolString.
	*	
	*	@param protocolString is a string that should be equal to one of the items in
	*	the protocolNames array
	*	
	*	@return void
	*/
	private void setUpObjectComboBox(String protocolString)
	{
		NetworkTypesFile file = new NetworkTypesFile();
		final String enum = "enumeration";
		if(protocolString.equals("A220"))
		{
			file.readFile(A220TYPESFILE);
		}
		else if(protocolString.equals("C220"))
		{
			file.readFile(C220TYPESFILE);
		}
		Protocol protocol = file.getProtocol();
		Vector objects = protocol.getObjects();
		
		/**
		*	There should be only one enumeration in this file.
		*/
		if(objects.size() == 1)
		{
			EnumerationType enumObject = (EnumerationType)objects.elementAt(0);
		
			if(enumObject.getName().toLowerCase().equals(protocolString.toLowerCase()))
			{
				/**
				*	Always remove listener before rebuilding the combobox; otherwise,
				*	the listener will unnecessarily trigger calls to the event
				*	handler everytime the combobox is modified in any way.
				*/
				JComboBox comboBox = enumObject.getComboBox();
				Vector list = new Vector();
				
				for(int i=0; i<comboBox.getItemCount(); i++)
				{
					list.add(comboBox.getItemAt(i));
				}
				
				GenericComboBoxModel model = new GenericComboBoxModel(list);
				objectComboBox.setModel(model);
				objectComboBox.setSelectedIndex(0);
			}
			else
			{
				JOptionPane.showMessageDialog
						(null, 
						"The enumeration read in does not match the\n" +
						"protocol selected.  Check the types file for\n" +
						"errors.",
						"Error", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog
					(null, 
					"The types file contains more than one set of enumerations.\n" +
					"Edit the file to limit the number of enumerations to 1.\n",
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	*	Clears the panel p.
	*	
	*	@param p is a JPanel
	*	
	*	@return void
	*/
	private void clear(JPanel p)
	{
		p.removeAll();
	}

	/**
	*	This method retrieves the indices from the indicesPanel.
	*	
	*	@return An array of strings
	*/	
	private String [] getIndices()
	{
		Component [] c = indicesPanel.getComponents();
		int size = c.length;
		String [] indicesArray = new String[size];
		
		for(int i=0; i<size; i++)
		{
			Component comp = c[i];
			if(comp instanceof JLabel)
			{
				indicesArray[i] = ((JLabel)comp).getText();
			}
			else if(comp instanceof JComboBox)
			{
				indicesArray[i] = "" + ((JComboBox)comp).getSelectedItem();
			}
		}
		return indicesArray;
	}
	

	/**
	*	This method populates the indicesPanel based on the protocolName and
	*	objectName.  The parameters are used to identify which file to read.  The
	*	file names are specified in the data member section of this class.
	*	
	*	@param protocolName is the name of protocol
	*	@param objectName is the name of protocol object
	*	
	*	@return void
	*/		
	private void addIndices(String protocolName, String objectName)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		NetworkTypesFile file = new NetworkTypesFile();
		if(protocolName.equals("A220"))
		{
			file.readFile(A220_INDICES_FILE);
		}
		else if(protocolName.equals("C220"))
		{
			file.readFile(C220_INDICES_FILE);
		}
		Protocol protocol = file.getProtocol();
		Vector objects = protocol.getObjects();
		
		if(protocol.getName().toLowerCase().equals(protocolName.toLowerCase()))
		{
			if(objects.size() > 0)
			{
				int numberOfObjects = objects.size();
				EnumerationType enumeration;
				
				FINDOBJECTBLOCK:
				for(int row=0; row<numberOfObjects; row++)
				{
					enumeration = (EnumerationType)objects.elementAt(row);
					gbc.anchor = GridBagConstraints.EAST;
					add(indicesPanel,enumeration.getLabel(),gbc,1,row,1,1);
					gbc.anchor = GridBagConstraints.WEST;
					add(indicesPanel,enumeration.getComboBox(),gbc,2,row,1,1);
				}
			}
			else
			{
				JOptionPane.showMessageDialog
						(null, 
						"There are no enumerations for this protocol in the indices file.\n" +
						"Ensure that there is at least one enumeration set in the file before\n" +
						"continuing.",
						"Error", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog
					(null, 
					"The enumerations read in do not apply to the protocol selected.\n" +
					"Verify that the indices file is correctly filled in and formatted.\n",
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	*	This method writes the indicesArray to a special file, which is eventually
	*	processed by an Ada program.  This file is specified in the data member section of this
	*	class.
	*	
	*	@param indicesArray is an array of Strings.
	*	
	*	@return void
	*/
	private void writeIndices(String protocol, String object, String [] indicesArray)
	{
		try
		{
			BufferedWriter outfile = new BufferedWriter(new FileWriter(new File(SELECTED_INDICES_FILE)));
			
			outfile.write("protocol " + protocol);
			outfile.newLine();
			outfile.write("object " + object);
			outfile.newLine();
			
			for(int i=0; i<indicesArray.length; i++)
			{
				outfile.write(indicesArray[i]);
				outfile.write(" ");
				if(Math.IEEEremainder(i,2) != 0)
				{
					outfile.newLine();
				}
			}
			outfile.close();
		}
		catch(Exception e)
		{
			p.print(e + " in writeIndices()");
		}
	}
	
	
	/**
	*	This method invokes an Ada program using the Runtime interface.  All standard 
	*	output generated from the Ada program will be displayed in a dialog.
	*	
	*	@return void
	*/
	private void invokeAdaReader()
	{
		try
		{
			int ch = ' ';
			Process process = Runtime.getRuntime().exec("test_device_and_protocol_parameter_tables");
			process.waitFor();
			if(process.exitValue() == 0)
			{
				JOptionPane.showMessageDialog
						(null, 
						"Tables accessed and read",
						"Information", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				InputStream good_stream = process.getInputStream();
				String good_string = "";
				if((ch = good_stream.read()) != -1)
				{
					p.print("Stream from application");
					good_string += ((char)ch);
					while((ch = good_stream.read()) != -1)
					{
						good_string += ((char)ch);
					}
					good_string = new String(good_string); 
					JOptionPane.showMessageDialog
							(null, 
							good_string,
							"Information", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				InputStream bad_stream = process.getErrorStream();
				String error_string = "";
				if((ch = bad_stream.read()) != -1)
				{
					p.print("Error stream from application");
					error_string += ((char)ch);
					while((ch = bad_stream.read()) != -1)
					{
						error_string += ((char)ch);
					}
					JOptionPane.showMessageDialog
							(null, 
							error_string,
							"Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			process.destroy();
		}
		catch(Exception e)
		{
			System.out.println(e);
			JOptionPane.showMessageDialog
					(null, 
					e.toString() + ":\n"
					+ "There was a problem invoking the external process.",
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	*	This procedure reads the file identified by the variable RESULTS_FILE, 
	*	which contains the results of the Ada process after it has read the 
	*	configuration tables and then displays that information onto the 
	*	resultsPanel.  RESULTS_FILE is specified in the data member
	*	section of this class.
	*	
	*	@return void
	*/
	private void readResultsFile()
	{
		try
		{
			scrollingResultsPanel.setVisible(false);
			scrollingResultsPanel.removeAll();
			scrollingResultsPanel.setVisible(true);
			BufferedReader infile = new BufferedReader(new FileReader(new File(RESULTS_FILE)));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5,5,5,5);
			int row = 1;
			String line;
			StringTokenizer st;
			boolean defaultValue = false;
			
			READRESULTSBLOCK:
			while((line = infile.readLine()) != null)
			{
				if(line.toLowerCase().indexOf("default") > -1)
				{
					defaultValue = true;
					String notFound = "<html>A matching set of parameters could<br>" +
									  "not be found for the selected indices.<br><br>" +
									  "The values displayed below are default<br>" +
									  "values for the object selected.<br></html>";
					JOptionPane.showMessageDialog
							(null, 
							notFound,
							"Error", 
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
				else
				{
					st = new StringTokenizer(line,",");
					while(st.hasMoreTokens())
					{
						String name = st.nextToken();
						JLabel nameLabel = new JLabel(name + ": ");
						if(defaultValue)
							nameLabel.setForeground(Color.red);
						else
							nameLabel.setForeground(Color.blue);
						String value = st.nextToken();
						JLabel valueLabel = new JLabel(value);
						if(defaultValue)
							valueLabel.setForeground(Color.red);
						else
							valueLabel.setForeground(Color.blue);
						gbc.anchor = GridBagConstraints.EAST;
						add(scrollingResultsPanel,nameLabel,gbc,1,row,1,1);
						gbc.anchor = GridBagConstraints.WEST;
						add(scrollingResultsPanel,valueLabel,gbc,2,row,1,1);
						row++;
					}
				}
			}
			
			//The following refreshes the panel because without it, at least
			//  on the NT platform, the panel doesn't refresh automatically.
			scrollingResultsPanel.setVisible(false);
			scrollingResultsPanel.setVisible(true);
			infile.close();
		}
		catch(Exception e)
		{
			p.print(e + " in readResultsFile()");
		}
	}
	
	/**
	*	This method is the event handler.
	*	
	*	@param evt is an ActionEvent
	*	
	*	@return void
	*/
	public void actionPerformed(ActionEvent evt)
	{
		String source = evt.getActionCommand();
		
		if(source.equals("exit"))
		{
			System.exit(0);
		}
		else if(source.equals("protocolComboBox"))
		{
        	retrieveButton.setEnabled(false);
        	String protocol = (String)protocolComboBox.getSelectedItem();
        	setUpObjectComboBox(protocol);
			clear(indicesPanel);
			clear(scrollingResultsPanel);
		}
		else if(source.equals("objectComboBox"))
		{
			String protocol = (String)protocolComboBox.getSelectedItem();
			String object = (String)objectComboBox.getSelectedItem();
			retrieveButton.setVisible(false);
        	retrieveButton.setEnabled(true);
			retrieveButton.setVisible(true);
        	addIndices(protocol,object);
        	repaintAllPanels();
		}
		else if(source.equals("retrieveButton"))
		{
			String protocol = (String)protocolComboBox.getSelectedItem();
			String object = (String)objectComboBox.getSelectedItem();
			writeIndices(protocol, object, getIndices());
			invokeAdaReader();
			readResultsFile();
		}
	}
	
	
}
