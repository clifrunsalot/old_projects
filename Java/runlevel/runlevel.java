import java.io.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.Timer;

		

class SystemLog extends JFrame implements ActionListener
{

	public SystemLog()
	{
		p = new MyPrint();
		DEBUG = false;
		
		setTitle("Run Level Monitor");
		timePassed = 0;
		minutes = 0;
		indicatorIndex = 0;
		
		contentPane = getContentPane();
		
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setPreferredSize(new Dimension(425,500));

		etchedBorder = BorderFactory.createEtchedBorder();
	 	mainPanel.setBorder(etchedBorder);
	 	
		gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        
		titlePanel = new JPanel(new GridBagLayout());
		runlevelLabel = new JLabel("Run Level Monitor");
		Font bigFont = new Font("Arial",Font.BOLD,14);
		runlevelLabel.setFont(bigFont);
		add(titlePanel,runlevelLabel,gbc,2,1,1,1);
		
		timePanel = new JPanel();
	 	indicatorPanel = new JPanel(new BorderLayout());
		indicatorLabel = new JLabel(indicatorArray[indicatorIndex] + "  ");
		indicatorPanel.setPreferredSize(new Dimension(30,20));
		indicatorPanel.add(indicatorLabel,BorderLayout.CENTER);
        add(timePanel,indicatorPanel,gbc,1,1,1,1);
		timeLabel = new JLabel(minutes + " minute(s) have passed");
		add(timePanel,timeLabel,gbc,2,1,1,1);
		
		textArea = new JTextArea(40,40);
		textArea.setLineWrap(false);
		textArea.setWrapStyleWord(false);
		
		scrollPane = new JScrollPane(textArea,
	        							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	        							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400,375));
		scrollPane.setMinimumSize(new Dimension(400,375));
		
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setActionCommand("startReadingLog");
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		stopButton.setActionCommand("stopReadingLog");
		
		exitButton = new JButton("Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(exitButton);

        gbc.insets = new Insets(0,0,0,0);
		add(mainPanel,titlePanel,gbc,2,1,1,1);
		add(mainPanel,timePanel,gbc,2,2,1,1);
		add(mainPanel,scrollPane,gbc,2,3,1,1);
		add(mainPanel,buttonPanel,gbc,2,4,1,1);
		
		contentPane.add(mainPanel);
		
		startReadingFile();
		startIndicator();

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
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
	
	void startTimers()
	{
		timer.start();
		indicatorTimer.start();
	}
	
	void stopTimers()
	{
		timer.stop();
		indicatorTimer.stop();
	}

	void updateIndicatorLabel()
	{
		if(indicatorIndex < indicatorArray.length)
		{
			indicatorLabel.setText(indicatorArray[indicatorIndex] + "  ");
		}
		else
		{
			indicatorIndex = 0;
			indicatorLabel.setText(indicatorArray[indicatorIndex] + "  ");
		}
		
		indicatorIndex++;
	}
	
	void startIndicator()
	{	
		indicatorTimer = new Timer(HALF_SECOND, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	updateIndicatorLabel();
		    }
		});
		indicatorTimer.start();
	}
	
	void updateTimeLabel()
	{
		timePassed += FIVE_SECONDS;
		if((timePassed/FIVE_SECONDS) == 12)
		{
			minutes++;
			timeLabel.setText(minutes + " minute(s) have passed");
			timePassed = 0;
		}
		
	}
	
	void startReadingFile()
	{	
		readFile();
		timer = new Timer(FIVE_SECONDS, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	readFile();
		    	updateTimeLabel();
		    }
		});
		timer.start();
	}
	
	
	StringBuffer extractRunLevel(StringBuffer b)
	{
		if(DEBUG)
			p.print("inside extractRunLevel()");
			
		//1905  1066056194 DEBUG SS.UNIT.Proc Man Changing the Run Level to:   1 because a configuration event occurred.
		
		int	colonPos = b.toString().indexOf(":");
		int posOfFirstWordAfterColon = 0;
		String numberSpace;
		String modifiedBuffer;
		
		if(b.toString().toLowerCase().indexOf("because") > -1)
		{
			//This string is longer than the rest; needs special handling.
			posOfFirstWordAfterColon = b.toString().indexOf("because");
			numberSpace = new String(b.toString().substring(colonPos+1,posOfFirstWordAfterColon-1));
		}
		else
		{
			//This is a normal run level string.
			numberSpace = new String(b.toString().substring(colonPos+1,b.length()));
		}
		
		modifiedBuffer = new String("  Run Level: " + numberSpace.trim());
		b = new StringBuffer(modifiedBuffer);
		
		return b;
		
	}
	
	StringBuffer extractProcessName(StringBuffer b)
	{
		if(DEBUG)
			p.print("inside extractProcessName()");
			
		//1905  1066056194 INFORMATION SS.UNIT.ProcOpns Starting process /h/AFATDS/bin/manage_tactical_subnet with PID of  2580
		
		int lastSlashPos = 0;
		int startOfWithPIDOf = b.toString().indexOf(" with PID of ");
		int endOfWithPIDOf = startOfWithPIDOf + " with PID of ".length();
		String pidSpace = b.substring(endOfWithPIDOf+1,b.length()).trim();
		String processName;
		
		for(int i=0; i<b.length(); i++)
		{
			if(b.charAt(i) == '/')
			{
				lastSlashPos = i;
			}
		}
		
		if(lastSlashPos > 0 && startOfWithPIDOf > 0 && endOfWithPIDOf > 0)
		{
			processName = new String(b.substring(lastSlashPos+1,startOfWithPIDOf).trim());
			b = new StringBuffer("               " + processName + " (" + pidSpace + ")");
		}
		
		return b;
	}
	
	void readFile()
	{
		if(DEBUG)
			p.print("inside readFile()");
			
		try
		{
//			File f = new File("/h/AFATDS/data/scratch/System_Log");
			File f = new File("System_Log");
			if(f.exists())
			{
				BufferedReader infile = new BufferedReader(new FileReader(f));
				String line = "";
				StringBuffer buffer;
				
				textArea.setText("");
				
				ReadingSystemLogBlock:
				{
					while ((line = infile.readLine()) != null)
					{
						buffer = new StringBuffer(line);
 						if(buffer.toString().toLowerCase().indexOf("changing the run level to:") > -1)
 						{
	 						buffer = extractRunLevel(buffer);
	 						textArea.append("\n" + buffer.toString() + "\n");
 						}
 						 if(buffer.toString().toLowerCase().indexOf("starting process") > -1)
 						{
	 						buffer = extractProcessName(buffer);
	 						textArea.append(buffer.toString() + "\n");
 						}
					}
				}
				infile.close();
				
			}
			else
			{
				p.print("Unable to find System_Log; closing program.");
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			p.print(e + " in readFile()");
		}
	}
	
    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();
	    if(DEBUG)
	    	p.print(source);
	    	
	    if(source.equals("exit"))
	    {
		    System.exit(0);
	    }
	    else if(source.equals("stopReadingLog"))
	    {
		    stopTimers();
	    }
	    else if(source.equals("startReadingLog"))
	    {
		    startTimers();
	    }
    }
	
    private GridBagConstraints gbc;
	private JPanel mainPanel,
					titlePanel,
					timePanel,
					indicatorPanel,
					buttonPanel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private Container contentPane;
	private JLabel runlevelLabel,
					timeLabel,
					indicatorLabel;
	private Border etchedBorder;
	private Timer timer,
					indicatorTimer;
	private boolean DEBUG;
	private MyPrint p;
	private JButton exitButton,
					startButton,
					stopButton;
	final private static int FIVE_SECONDS = 5000;
	final private static int HALF_SECOND = 500;
	final private static String [] indicatorArray = {" |","/","--","\\"};
	private int indicatorIndex = 0;
	private int timePassed;
	private int minutes;
}

public class runlevel
{

	public static void main (String [] args)
	{
		try
		{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SystemLog syslog = new SystemLog();
			syslog.setVisible(true);
			syslog.pack();
		}
	    catch(Exception e)
        {
			System.err.println("Program error.");
		}
	}
}

