/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: DataFileFilter.java
*/

import java.io.File;
import javax.swing.filechooser.FileFilter;

class DataFileFilter extends FileFilter
{
	public boolean accept (File file)
	{
		return file.getName().toLowerCase().endsWith(".dat");
	}
	public String getDescription()
	{
		return "Data Files";
	}
}



