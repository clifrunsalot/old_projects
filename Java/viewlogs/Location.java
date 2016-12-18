import java.io.*;
import java.awt.*;

public class Location
{
	public Location(Container c)
	{
		p = new MyPrint();
		localC = c;
        tk = Toolkit.getDefaultToolkit();
        screen = tk.getScreenSize();
        
        screenWidth = screen.width;
        screenHeight = screen.height;
        
        cMidWidth = localC.getWidth()/2;
        cMidHeight = localC.getHeight()/2;
	}

	void setLocation(String direction)
	{
		double pre_x = 0.0;
		double pre_y = 0.0;
        x_loc = 0;
        y_loc = 0;

        if(direction.equals("n"))
        {
	        pre_x = (screenWidth*(.5));// - cMidWidth;
	        pre_y = (screenHeight*(.25));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
        }
        else if(direction.equals("s"))
        {
	        pre_x = (screenWidth*(.5));// - cMidWidth;
	        pre_y = (screenHeight*(.75));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
        else if(direction.equals("e"))
        {
	        pre_x = (screenWidth*(.75));// - cMidWidth;
	        pre_y = (screenHeight*(.5));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
        else if(direction.equals("w"))
        {
	        pre_x = (screenWidth*(.25));// - cMidWidth;
	        pre_y = (screenHeight*(.5));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
        else if(direction.equals("ne"))
        {
	        pre_x = (screenWidth*(.75));// - cMidWidth;
	        pre_y = (screenHeight*(.25));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
        else if(direction.equals("nw"))
        {
	        pre_x = (screenWidth*(.25));// - cMidWidth;
	        pre_y = (screenHeight*(.25));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
        else if(direction.equals("se"))
        {
	        pre_x = (screenWidth*(.75));// - cMidWidth;
	        pre_y = (screenHeight*(.75));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
        else if(direction.equals("sw"))
        {
	        pre_x = (screenWidth*(.25));// - cMidWidth;
	        pre_y = (screenHeight*(.75));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
      	}
      	else
      	{
	        pre_x = (screenWidth*(.5));// - cMidWidth;
	        pre_y = (screenHeight*(.5));// - cMidWidth;
	        
	        x_loc = (int)pre_x;
	        y_loc = (int)pre_y;
	        
        	localC.setLocation(x_loc,y_loc);
        }
    }
    
    MyPrint p;
	Container localC;
    Toolkit tk;
    Dimension screen;
    double screenWidth;
    double screenHeight;
    double cMidWidth;
    double cMidHeight;
    int x_loc = 0;
    int y_loc = 0;

}