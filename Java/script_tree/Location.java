/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

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

public class Location{
    
    /**
    *	This is the container of the application.
    */
    private Container container;
    
    /**
    *	This is the width of the container.
    */
    private double containerWidth;
    
    /**
    *	This is the height of the container.
    */
    private double containerHeight;
    
    /**
    *	This is half of the container width.
    */
    private double containerMidX;
    
    /**
    *	This is half of the container height.
    */
    private double containerMidY;
    
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
    private double screenMidX;
    
    /**
    *	This is half of the screen height.
    */
    private double screenMidY;
    
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
	public Location(Container c){
		
		container = c;
		tk = Toolkit.getDefaultToolkit();
		screen = tk.getScreenSize();

		/**
		 * Get screen dimensions
		 */		
		screenWidth = screen.width;
		screenHeight = screen.height;
		
		screenMidX = screenWidth/2;
		screenMidY = screenHeight/2;

		/**
		 * Get container dimensions
		 */		
		containerWidth = container.getWidth();
		containerHeight = container.getHeight();
		
		containerMidX = containerWidth/2;
		containerMidY = containerHeight/2;
		
	}
	
	private double convertTo(double d, double percent){
		return (percent * d);
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
	public void setLocation(String direction){

		double percent_x = 0.0;
		double percent_y = 0.0;
		int directionConverted = -1;
		int i = 0;
		x_loc = 0;
		y_loc = 0;
		
		String [] directionArray = {"n",
														"s",
														"e",
														"w",
														"ne",
														"nw",
														"se",
														"sw"};
		
		for(i=0; i<directionArray.length; i++){
			if(direction.equals(directionArray[i])){
				directionConverted = i;
				break;
			}
		}
														
		switch (directionConverted){
			case 0 :
				percent_x = 0.5;
				percent_y = 0.25;
				break;
			case 1 :
				percent_x = 0.5;
				percent_y = 0.75;
				break;
			case 2 :
				percent_x = 0.75;
				percent_y = 0.5;
				break;
			case 3 :
				percent_x = 0.25;
				percent_y = 0.5;
				break;
			case 4 :
				percent_x = 0.75;
				percent_y = 0.25;
				break;
			case 5 :
				percent_x = 0.25;
				percent_y = 0.25;
				break;
			case 6 :
				percent_x = 0.75;
				percent_y = 0.75;
				break;
			case 7 :
				percent_x = 0.25;
				percent_y = 0.75;
				break;
			default :
				percent_x = 0.0;
				percent_y = 0.0;
				break;
		}
		
		if(directionConverted != -1){
			x_loc = (int)(convertTo(screenWidth,percent_x) - convertTo(containerWidth,percent_x));
			y_loc = (int)(convertTo(screenWidth,percent_y) - convertTo(containerWidth,percent_y));
		}
		else{
			/**
			 * Locate upper left corner of container and position
			 * it in the center of the screen.
			 */		
			x_loc = (int)(screenMidX - containerMidX);
			y_loc = (int)(screenMidY - containerMidY);
		}
		
		container.setLocation(x_loc, y_loc);
	}
	
	public void setLocation(double percent_x, double percent_y){
		x_loc = (int)(convertTo(screenWidth,percent_x) - convertTo(containerWidth,percent_x));
		y_loc = (int)(convertTo(screenWidth,percent_y) - convertTo(containerWidth,percent_y));
		container.setLocation(x_loc,y_loc);
	}
}