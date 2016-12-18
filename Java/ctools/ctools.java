import java.io.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

class ToolWindow 	extends  	JFrame
					implements	ActionListener
{
	public ToolWindow()
	{
		p = new MyPrint();
		
		contentPane = getContentPane();
		setTitle("ctools");
		
// 		toolWindowPanel = new JPanel(new GridLayout(4,1));
		toolWindowPanel = new JPanel(new GridLayout(3,1));
		
		setupProcessesButton = new JButton("Setup Processes");
		setupProcessesButton.addActionListener(this);
		setupProcessesButton.setActionCommand("setupProcessesButton");
		
		runLevelButton = new JButton("Run Levels");
		runLevelButton.addActionListener(this);
		runLevelButton.setActionCommand("runLevelButton");
		
// 		viewSysLogButton = new JButton("View System Log");
// 		viewSysLogButton.addActionListener(this);
// 		viewSysLogButton.setActionCommand("viewSysLogButton");
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exitButton");
		
		toolWindowPanel.add(setupProcessesButton);
		toolWindowPanel.add(runLevelButton);
// 		toolWindowPanel.add(viewSysLogButton);
		toolWindowPanel.add(exitButton);
		
		contentPane.add(toolWindowPanel);
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String source = evt.getActionCommand();
//		p.print(source);
		
		if(source.equals("setupProcessesButton"))
		{
			if(!processWindowOpened)
			{
				setupProcesses = new ProcessWindow();
				setupProcesses.setVisible(true);
				setupProcesses.pack();
				processWindowOpened = true;
				Location loc = new Location(setupProcesses);
				loc.setLocation("nw");
			}
			if(!setupProcesses.isVisible())
				setupProcesses.setVisible(true);
		}
		else if(source.equals("runLevelButton"))
		{
			if(!runLevelWindowOpened)
			{
				runLevel = new RunLevel();
				runLevel.setVisible(true);
				runLevel.pack();
				runLevelWindowOpened = true;
				Location loc = new Location(runLevel);
				loc.setLocation("nw");
			}
			if(!runLevel.isVisible())
				runLevel.setVisible(true);
		}
// 		else if(source.equals("viewSysLogButton"))
// 		{
// 			if(!logWindowOpened)
// 			{
// 				log = new Log();
// 				log.setVisible(true);
// 				log.pack();
// 				logWindowOpened = true;
// 				Location loc = new Location(log);
// 				loc.setLocation("nw");
// 			}
// 			if(!log.isVisible())
// 			{
// 				log.setVisible(true);
// 			}
// 		}
		else if(source.equals("exitButton"))
		{
			System.exit(0);
		}
		
	}
	

	private MyPrint p;
	private Container contentPane;
	private JPanel toolWindowPanel;
	private JButton	exitButton,
					setupProcessesButton,
					runLevelButton,
					viewSysLogButton;
	static private ProcessWindow setupProcesses;
	static private RunLevel runLevel;
// 	static private Log log;
	static private boolean processWindowOpened = false;
	static private boolean runLevelWindowOpened = false;
	static private boolean logWindowOpened = false;
}


public class ctools
{
	public static void main(String [] args)
	{
		try
		{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ToolWindow toolWindow = new ToolWindow();
			toolWindow.setVisible(true);
			toolWindow.pack();
			Location loc = new Location(toolWindow);
			loc.setLocation("");
//			loc.setLocation("n");
//			loc.setLocation("s");
//			loc.setLocation("e");
// 			loc.setLocation("w");
// 			loc.setLocation("ne");
// 			loc.setLocation("nw");
// 			loc.setLocation("se");
// 			loc.setLocation("sw");
		}
	    catch(Exception e)
        {
			System.err.println("Program error.");
		}
	}
}	