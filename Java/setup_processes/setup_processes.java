import javax.swing.*;

public class setup_processes
{
	public static void main (String args [])
	{
		try
		{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ProcessWindow mainFrame = new ProcessWindow();
			mainFrame.setVisible(true);
			mainFrame.pack();
		}
	    catch(Exception e)
        {
			System.err.println("Program error.");
		}
	}
}