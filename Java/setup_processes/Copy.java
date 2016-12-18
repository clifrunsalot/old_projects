/*
file - Copy.java
usage - "java Copy <input_file> <output_file)"
purpose - Makes a copy of input_file, where input_file is a text file.
*/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

public class Copy
{
	public Copy(String original, String modified)
	{
		try
		{
			
			FileReader fr = new FileReader(new File(original));
			FileWriter fw = new FileWriter(new File(modified));

			int ch;

			//loop through input_file until the EOF, or -1, is encountered.
			while ((ch = fr.read()) != -1)
			{
				//write ch to output_file
				fw.write((char)ch);
			}

			//close both files
			fr.close();
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}	
