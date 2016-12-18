/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */
 
package afatds.app.ppt;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
*	This class provides a simpler way to position the application on the monitor screen.  Passing
*	one of the specified strings to the setLocation method will place the top-left corner of the frame
*	of the application in one of the locations indicated below.
*
*	<pre>
*
*                 (Monitor Screen)
*	|----------------------------------------|
*	|              |             |           |
*	|    'nw'      |      'n'    |    'ne'   |
*	|              |             |           |
*	|----------------------------------------|
*	|              |             |           |
*	|     'w'      |             |    'e'    |
*	|              |             |           |
*	|----------------------------------------|
*	|              |             |           |
*	|    'sw'      |      's'    |    'se'   |
*	|              |             |           |
*	|----------------------------------------|
*
*	</pre>
*
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/

public class Location
{
    /**
    *	This is a variable of the MyPrint class.  Use this for debugging purposes only.  See the 
    *	MyPrint class for usage.
    */
    private MyPrint p;
    
    /**
    *	This is the container of the application.
    */
    private Container localC;
    
    /**
    *	This is a variable of the Toolkit class that gives the program access to the screen dimensions.
    */
    private Toolkit tk;
    
    /**
    *	This is the variable that describes the screen object.
    */
    private Dimension screen;
    
    /**
    *	This is the width of the screen.
    */
    private double screenWidth;
    
    /**
    *	This is the height of the screen.
    */
    private double screenHeight;
    
    /**
    *	This is half of the screen width.
    */
    private double cMidWidth;
    
    /**
    *	This is half of the screen height.
    */
    private double cMidHeight;
    
    /**
    *	This is a point on the north-south axis of the screen.
    */
    private int x_loc = 0;
    
    /**
    *	This is a point on the west-east axis of the screen.
    */
    private int y_loc = 0;
    
	/**
	*	This constructs an object of the class.
	*	
	*	@param c is the container belonging to the application.
	*/
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

	/**
	*	Sets the location of the application container on the monitor screen.  The direction can
	*	be one of the following values.  Any other value will place the top-left corner of the 
	*	application in the center of the screen.
	*	
	*	<ul>
	*	<li>ne - Northeast corner of screen
	*	<li>n - Above center of screen
	*	<li>nw - Northwest corner of screen
	*	<li>w - West of center of screen
	*	<li>e - East of center of screen
	*	<li>se - Southeast corner of screen
	*	<li>s - Below center of screen
	*	<li>sw - Southwest corner of screen
	*	</ul>
	*	
	*	@param direction is one of the strings listed above
	*/
	public void setLocation(String direction)
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
    

}