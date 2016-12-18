/*
file - Copy.java
usage - "java Copy <input_file> <output_file)"
purpose - Makes a copy of input_file, where input_file is a text file.
*/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

public class MyFile
{
	public MyFile()
	{
		p = new MyPrint();
	}
	
	void makeCopy(String original, String modified)
	{
		try
		{
			
			FileReader fr = new FileReader(new File(original));
			FileWriter fw = new FileWriter(new File(modified));

			int ch;

			//loop through input_file until the EOF, or -1, is encountered.
			while ((ch = fr.read()) != -1)
			{
// 				if(original.equals("run_guic.log"))
// 				{
// 					p.print("run_guic.log: " + ch);
// 				}
// 				else if (original.equals("System_Log"))
// 				{
// 					p.print("System_Log: " + ch);
// 				}
// 				else if (original.equals("run_aas.log"))
// 				{
// 					p.print("run_aas.log: " + ch);
// 				}

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
	MyPrint p;
}	
