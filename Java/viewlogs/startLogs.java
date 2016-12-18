import java.io.*;
import javax.swing.*;

public class startLogs
{
	public static void main(String [] args)
	{
		try
		{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Log log = new Log();
			log.setVisible(true);
			log.pack();
			Location loc = new Location(log);
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