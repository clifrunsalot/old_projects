import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//******* 4 *********
//Create a button panel and set attributes
class ButtonPanel extends JPanel
	implements ActionListener
{
	public ButtonPanel()
	{
		//Create instances of three buttons
		metalButton = new JButton("Metal");
		motifButton = new JButton("Motif");
		windowButton = new JButton("Window");

		//Add each button to the panel
		add(metalButton);
		add(motifButton);
		add(windowButton);

		//register a listener to each button
		metalButton.addActionListener(this);
		motifButton.addActionListener(this);
		windowButton.addActionListener(this);
	}
	
	//******* 5 ********
	//Create methods to handle actions from
	// buttons
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		String plaf = "";
		
		if (source == metalButton)
			plaf = "javax.swing.plaf.metal.MetalLookAndFeel";
		else if (source == motifButton)
			plaf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		else if (source == windowButton)
			plaf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
		try
		{
			UIManager.setLookAndFeel(plaf);
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e){}
	}

	private JButton metalButton;
	private JButton motifButton;
	private JButton windowButton;
}

//******* 2 *********
//Set attributes and behavior of class
// instance
class FrameTest extends JFrame
{
	public FrameTest()
	{
		//Set title and size
		setTitle("PLAF Frame");
		setSize(400,100);
		
		//Create a listener that listens for
		// actions to close window
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		//******* 3 ********
		//Create a panel on which to place some buttons
		Container contentPane = getContentPane();
		contentPane.add(new ButtonPanel());
	}
}

//********** 1 ************
//Create class instance and show it
public class PlafTest 
{
	public static void main (String [] args)
	{
		JFrame jframe = new FrameTest();
		jframe.show();
	}
}

