import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.FlowLayout;
import java.util.StringTokenizer;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.print.*;


public class UserConfig extends JFrame implements ActionListener,
											ChangeListener,
											WindowListener
{
	public UserConfig(Vector inOptionsLog)
	{
		d = new Debug();
// 		d.print("UserConfig()");
		this.setName("optionsWindow");
		
		addWindowListener(this);
        m = new Font("SansSerif",Font.PLAIN,11);
        gbc = new GridBagConstraints();
        
        //All components will not grow/shrink with window
        gbc.fill = GridBagConstraints.NONE;
		setSize(300,300);
		setTitle("Options");

 		origOptionsLog = inOptionsLog;
		tempOptionsLog = new Vector();
		initializeVector(tempOptionsLog);
		
		origRouteArray = extractOptionsArray(origOptionsLog,"route");
		origExerciseArray = extractOptionsArray(origOptionsLog,"exercise");

		localRouteArray = extractOptionsArray(origOptionsLog,"route");
		localExerciseArray = extractOptionsArray(origOptionsLog,"exercise");

		routePanel = new JPanel();
		routeTablePanel = new UserConfigTable(new String[MAXROWS][MAXCOLUMNS]);
		makePanel(routePanel,routeTablePanel,localRouteArray,"routePanel");
		
		exercisePanel = new JPanel();
		exerciseTablePanel = new UserConfigTable(new String[MAXROWS][MAXCOLUMNS]);
 		makePanel(exercisePanel,exerciseTablePanel,localExerciseArray,"exercisePanel");
 		
		makeTabbedPanel();
		makeButtonPanel(gbc);

		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(tabPanel,BorderLayout.CENTER);
		contentPane.add(buttonPanel,BorderLayout.SOUTH);
	}

	//================= Add ========================//
    //Simplifies adding components to panel by
    //passing in constraints
    public void add(JPanel p, Component c, GridBagConstraints gbc,
        int x, int y, int w, int h)
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        p.add(c,gbc);
    }

    //======================= Route Panel ========================//
    //constructs route panel
    public void makePanel(JPanel panel, 
    						UserConfigTable tablePanel, 
    						String [][] optionsArray, 
    						String panelName)
	{
// 		d.print("inside makePanel()");
		
		panel.setName(panelName);
 		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 		populateTab(tablePanel,optionsArray);
		panel.add(tablePanel);
	}

	//===================== populateTab ===========================//
	//populates the tab panel passed in with the array
	public void populateTab(UserConfigTable p, String [][] array)
	{
// 		d.print("inside populateTab()");
		p.refreshTable(array);
	}

	//============== Tabbed Panel =============//
	//creates the tabbed panel
    public void makeTabbedPanel()
    {
// 	    d.print("inside makeTabbedPanel()");
        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
        tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());

        tabbedPane.setFont(m);
        tabbedPane.addTab("Routes",routePanel);
        tabbedPane.addTab("Exercises",exercisePanel);

        tabbedPane.setEnabledAt(0,true);
        tabbedPane.setEnabledAt(1,true);

        tabPanel.add(tabbedPane,BorderLayout.CENTER);
    }//~public void makeTabbedPa...

	//============== Button Panel =============//
    //constructs button panel
	public void makeButtonPanel(GridBagConstraints gbc)
	{
// 		d.print("inside makeButtonPanel()");
		buttonPanel = new JPanel(new GridBagLayout());

		saveButton = new JButton("Save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);

		clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(this);
		buttonPanel.add(clearButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

		closeButton = new JButton("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);

        gbc.insets = new Insets(5,10,2,2);
        gbc.anchor = GridBagConstraints.WEST;

        buttonPanel.setPreferredSize(new Dimension(400,75));
        buttonPanel.setMinimumSize(new Dimension(400,75));

        Border border = BorderFactory.createEtchedBorder();
        buttonPanel.setBorder(border);
	}

	//============== Tabbed Panel =============//
	//returns a deep copy of the vector passed in
	public Vector copyLog(Vector inLog)
	{
// 		d.print("inside copyLog()");
		Vector outLog = new Vector(20);
		Options option = new Options();
		for(int i=0; i<MAXVECTORSIZE; i++)
		{
			option = (Options)inLog.elementAt(i);
			if (option instanceof Route)
			{
				outLog.add((Route)option);
			}
			else if (option instanceof Exercise)
			{
				outLog.add((Exercise)option);
			}
		}
		return outLog;
	}

	//================ initializeVector ========================//
	//creates a vector of default options.
	public void initializeVector(Vector v)
	{
// 		d.print("initializeVector())");

		for(int i=0; i<MAXVECTORSIZE; i++)
		{
			Options option = new Options();
			v.add(option);
		}
	}

	//================== initializeArray ========================//
	//initializes route array to empty strings
	public void initializeArray(String [][] array)
	{
// 		d.print("initializeRouteArray()");

		array = new String[MAXROWS][MAXCOLUMNS];

		for (int rowIndex=0; rowIndex<MAXROWS; rowIndex++)
		{
			for(int colIndex=0; colIndex<MAXCOLUMNS; colIndex++)
			{
				array[rowIndex][colIndex] = "";
			}
		}
	}

	//===================== extractOptionsArray ==================//
	//extracts an array of strings from the vector based on the
	// option identified by the optionName passed in.
	public String[][] extractOptionsArray(Vector v, String optionName)
	{
// 		d.print("inside extractOptionsArray()");
		Options option;
		//represents positions 0 and 1 in each element of the array
		int elementIndex = 0;
		//represents the position in the array.
		int arrayIndex = 0;
		String [][] optionsArray = new String[MAXROWS][MAXCOLUMNS];

		//cycle thru the vector.
		for(int i=0; i<v.size(); i++)
		{
			option = (Options)v.elementAt(i);
			//extract the element if it is a Route object
			if ((optionName.equals("route")) && (option instanceof Route))
			{
				//start elementIndex at 0
				elementIndex = 0;
				//fill first element in route pair - [code][""]
				optionsArray[arrayIndex][elementIndex] = option.getCode();
				//increment elementIndex
				elementIndex++;
				//fill second element in route pair - [code][description]
				optionsArray[arrayIndex][elementIndex] = option.getDescription();
				//increment the arrayIndex
				arrayIndex++;
			}

			// or extract the element if it is an Exercise object
			else if ((optionName.equals("exercise")) && (option instanceof Exercise))
			{
				//start elementIndex at 0
				elementIndex = 0;
				//fill first element in route pair - [code][""]
				optionsArray[arrayIndex][elementIndex] = option.getCode();
				//increment elementIndex
				elementIndex++;
				//fill second element in route pair - [code][description]
				optionsArray[arrayIndex][elementIndex] = option.getDescription();
				//increment the arrayIndex
				arrayIndex++;
			}
		}
		
		return optionsArray;
	}

	//====================== getOptionsArray =======================//
	//gets array of strings from either the route or exercise panel.
	// IMPORTANT - this procedure assumes that the panel has
	// ten consecutive pairs of strings.
	public String [][] getOptionsArray(JPanel p)
	{
// 		d.print("inside getOptionsArray()");
		String [][] array = new String[MAXROWS][MAXCOLUMNS];
		if (p.getName().equals("routePanel"))
		{
			array = routeTablePanel.extractOptions();
		}
		else if (p.getName().equals("exercisePanel"))
		{
			array = exerciseTablePanel.extractOptions();
		}
        return array;
	}

	//====================== isEqual =====================//
	//verifies that both arrays are equal
	public boolean isEqual(String [][] a1, String [][] a2)
	{
		boolean notEqual = false;
		for(int rowIndex=0; rowIndex<a1.length; rowIndex++)
		{
			for(int colIndex=0; colIndex<MAXCOLUMNS; colIndex++)
			{
				if(!(a1[rowIndex][colIndex]).equals(a2[rowIndex][colIndex]))
				{
					notEqual = true;
					break;
				}
			}
			if (notEqual)
				break;
		}
		return (!notEqual);
	}

	//======================= saveOptionsArray ========================//
	//saves the array to the local copy of the array passed in
	public void saveOptionsArray(String [][] newArray, String optionName)
	{
// 		d.print("inside saveOptionsArray()");
		for(int rowIndex=0; rowIndex<MAXROWS; rowIndex++)
		{
			for(int colIndex=0; colIndex<MAXCOLUMNS; colIndex++)
			{
				if(optionName.equals("routePanel"))
				{
					localRouteArray[rowIndex][colIndex] = newArray[rowIndex][colIndex];
				}
				else if(optionName.equals("exercisePanel"))
				{
					localExerciseArray[rowIndex][colIndex] = newArray[rowIndex][colIndex];
				}
			}
		}
	}

	//========================== save ========================//
	//facilitates save the updated array of options
	public void save(JPanel p)
	{
// 		d.print("inside save()");
		String [][] newArray = getOptionsArray(p);
		String [][] origArray = new String[MAXROWS][MAXCOLUMNS];
		if (p.getName().equals("routePanel"))
		{
			origArray = origRouteArray;
		}
		else if (p.getName().equals("exercisePanel"))
		{
			origArray = origExerciseArray;
		}

		if (!isEqual(newArray,origArray))
		{
            int option = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save your latest changes?",
                "Save Latest Changes?",
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
            {
				saveOptionsArray(newArray,p.getName());
				if (p.getName().equals("routePanel"))
				{
					localRouteArray = newArray;
				}
				else if(p.getName().equals("exercisePanel"))
				{
					localExerciseArray = newArray;
				}
            }
		}
	}

	//========================== isIdentical ======================//
	//performs a element by element comparison to ensure that the
	// two vectors passed in are identical.
	public boolean isIdentical(Vector origLog, Vector updatedLog)
	{
// 		d.print("inside isIdentical()");
		boolean notSame = false;
		for(int i=0; i<origLog.size(); i++)
		{
			Options origOpt = (Options)origLog.elementAt(i);
			Options updatedOpt = (Options)updatedLog.elementAt(i);

			if(origOpt instanceof Route && updatedOpt instanceof Route)
			{
				if(!origOpt.getCode().equals(updatedOpt.getCode()) ||
					!origOpt.getDescription().equals(updatedOpt.getDescription()))
					{
						notSame = true;
						break;
					}
			}
			else if(origOpt instanceof Exercise && updatedOpt instanceof Exercise)
			{
				if(!origOpt.getCode().equals(updatedOpt.getCode()) ||
					!origOpt.getDescription().equals(updatedOpt.getDescription()))
					{
						notSame = true;
						break;
					}
			}
		}
		return (!notSame);
	}

	//=========================== getOptionsLog ======================//
	//returns the reference to the original options log
	public Vector getOptionsLog()
	{
// 		d.print("inside getOptionsLog()");
		return origOptionsLog;
	}
	
	public boolean isOpen()
	{
		return windowOpened;
	}

	//====================== saveOptionsArrayToLocalLog ======================//
	// saves the array to tempOptionsLog
	public void saveOptionsArrayToLocalLog(String [][] array, String optionName)
	{
// 		d.print("inside saveOptionsArrayToLocalLog()");
		int colIndex = 0;
		if (optionName.equals("route"))
		{
			for(int rowIndex=0, arrayIndex=0; arrayIndex<MAXROWS; rowIndex++, arrayIndex++)
			{
				Route route = new Route();
				colIndex = 0;
				route.setCode(array[arrayIndex][colIndex]);
				colIndex++;
				route.setDescription(array[arrayIndex][colIndex]);
				tempOptionsLog.setElementAt(route,rowIndex);
			}
		}
		if (optionName.equals("exercise"))
		{
			for(int rowIndex=10, arrayIndex=0; arrayIndex<MAXROWS; rowIndex++, arrayIndex++)
			{
				Exercise exercise = new Exercise();
				colIndex = 0;
				exercise.setCode(array[arrayIndex][colIndex]);
				colIndex++;
				exercise.setDescription(array[arrayIndex][colIndex]);
				tempOptionsLog.setElementAt(exercise,rowIndex);
			}
		}
	}

    public void windowClosing(WindowEvent we)
    {
	    windowOpened = false;
    }

    public void windowOpened(WindowEvent we)
    {
	    windowOpened = true;
	}

    public void windowIconified(WindowEvent we)
    {
    }

    public void windowDeiconified(WindowEvent we)
    {
	}

    public void windowClosed(WindowEvent we)
    {
	    windowOpened = false;
	}

    public void windowActivated(WindowEvent we)
    {
	}

    public void windowDeactivated(WindowEvent we)
    {
	}

	public void actionPerformed(ActionEvent evt)
	{
		String source = evt.getActionCommand();
		if (source.equals("save"))
		{
// 			System.out.println("Save selected");

			if(routePanel.isVisible())
			{
				save(routePanel);
		        populateTab(routeTablePanel,localRouteArray);
			}
			else if (exercisePanel.isVisible())
			{
				save(exercisePanel);
		        populateTab(exerciseTablePanel,localExerciseArray);
			}

		}
		else if (source.equals("clear"))
		{
// 			System.out.println("Clear selected");

			String [][] clearedArray = new String [MAXROWS][MAXCOLUMNS];
			if(routePanel.isVisible())
			{
				routeTablePanel.refreshTable(clearedArray);
			}
			else if(exercisePanel.isVisible())
			{
				exerciseTablePanel.refreshTable(clearedArray);
			}
		}
		else if (source.equals("cancel"))
		{
// 			System.out.println("Cancel selected");

			if (routePanel.isVisible())
			{
		        populateTab(routeTablePanel,localRouteArray);
			}
			else if (exercisePanel.isVisible())
			{
		        populateTab(exerciseTablePanel,localExerciseArray);
			}
		}
		else if (source.equals("close"))
		{
// 			System.out.println("close selected");
			localRouteArray = routeTablePanel.extractOptions();
			localExerciseArray = exerciseTablePanel.extractOptions();

			if (!isEqual(origRouteArray,localRouteArray) ||
				!isEqual(origExerciseArray,localExerciseArray))
			{
	            int option = JOptionPane.showConfirmDialog(
	                this,
	                "Do you want to save your latest changes?",
	                "Save Latest Changes?",
	                JOptionPane.YES_NO_OPTION);
	            if (option == JOptionPane.YES_OPTION)
	            {
		            //save both arrays to tempOptionsLog
		            saveOptionsArrayToLocalLog(localRouteArray,"route");
		            saveOptionsArrayToLocalLog(localExerciseArray,"exercise");

					//accept updated options log
					origOptionsLog = copyLog(tempOptionsLog);

					JOptionPane.showMessageDialog(
			            this,
			            "Any changes made to your options will appear\n" +
			            "the next time Tewl is opened.",
			            "Information",
		            	JOptionPane.INFORMATION_MESSAGE);
	            }
	            else
	            {	//revert to orig options log
		            tempOptionsLog = copyLog(origOptionsLog);
	            }
			}
			this.setVisible(false);
			windowOpened = false;
		}
	}

    public void stateChanged(ChangeEvent evt)
    {
        JTabbedPane pane = (JTabbedPane)evt.getSource();
        int n = pane.getSelectedIndex();
        //Route tab
        if (n == 0)
        {
      	}
      	else if (n == 1)
      	{
      	}
    }

	private Font m;
   	private GridBagConstraints gbc;
	private Container contentPane;
	private JPanel routePanel,
					exercisePanel,
					tabPanel,
					buttonPanel;
	private UserConfigTable routeTablePanel,
					exerciseTablePanel;
	private JTabbedPane tabbedPane;
	private JButton saveButton,
					clearButton,
					cancelButton,
					closeButton;
	private JLabel routeCodeLabel,
					routeDescLabel,
					exerciseCodeLabel,
					exerciseDescLabel;
	private final int MAXCOLUMNS = 2,
					MAXROWS = 10,
					MAXVECTORSIZE = 20;
    private Vector origOptionsLog,
    				tempOptionsLog;
    private String [][] origRouteArray = new String [MAXROWS][MAXCOLUMNS];
	private String [][] origExerciseArray = new String [MAXROWS][MAXCOLUMNS];
    private String [][] localRouteArray = new String [MAXROWS][MAXCOLUMNS];
	private String [][] localExerciseArray = new String [MAXROWS][MAXCOLUMNS];
	private Debug d;
	private static boolean windowOpened = false;
}
