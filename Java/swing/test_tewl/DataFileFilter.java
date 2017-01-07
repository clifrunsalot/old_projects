/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: DataFileFilter.java
*/

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
Instantiates the DataFileFilter class.  This class accepts all files or files ending
in ".dat".
*/
public class DataFileFilter extends FileFilter
{
	
	/**
	Returns True if the file object is either a directory or ends with ".dat".
	
	@param file File object
	
	@return True if the file object is either a directory or ends with ".dat".
	*/
	public boolean accept (File file)
	{
		boolean isValid = false;

		if (file.isDirectory()
		 ||	file.getName().toLowerCase().endsWith(".dat"))
		{
			isValid = true;
    	}
    	return isValid;
	}
	
	/**
	Returns the description for the file type to be displayed in the Open File Dialog."
	
	@param none
	
	@return File name in String format
	*/
	public String getDescription()
	{
		return "Data Files";
	}
}




