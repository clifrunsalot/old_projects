/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

package afatds.app.ppt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;


/**
*	This class handles reading and extracting information from specially formatted files.  Essentially, an
*	object of this class will read a configuration file, and extract the protocol and enumeration values
*	contained therein.  These values are then made available via the getProtocol method.  The use of the 
*	punctuation shown is mandatory in order for an object of this class to correctly read the 
*	configuration files.  Comment lines are allowed; however, do not mix comments with code.  Precede
*	all comment lines with "--".<br>
*	<br>
*	
*	There can only be one protocol listed and it must be listed first; create it in the
*	following manner.
*	<pre>
*	protocol:(name of protocol):
*	</pre>
*	
*	Depending on the kind of configuration file it is, there may be one or more enumeration listings; 
*	create them in the following manner.
*	<pre>
*	enumeration:
*	(name of enumeration_type_1),
*	(value_1),
*	(value_2),
*	(value_3),
*	(value_4),
*	(value_5),
*	...
*	end,
*	
*	enumeration:
*	(name of enumeration_type_2),
*	(value_1),
*	(value_2),
*	(value_3),
*	(value_4),
*	(value_5),
*	...
*	end,
*	
*	</pre>
*	
*	An example of a complete configuration file might be as follows without any other information.
*	<pre>
*	
*			"example_configuration_file.txt"
*		|-----------------------------------------|
*		|protocol:C220:                           |
*		|                                         |
*		|enumeration:                             |
*		|Device_Type,                             |
*		|Two_Wire_Net,                            |
*		|Four_Wire_Net,                           |
*		|Analog_Radio_Net,                        |
*		|Ky57_Sincgars_Net,                       |
*		|Sincgars_Icom_Net,                       |
*		|Sincgars_Sip_Net,                        |
*		|Hf_Config_Net,                           |
*		|Satcom_Net,                              |
*		|end,                                     |
*		|                                         |
*		|-----------------------------------------|
*		
*	</pre>
*	
*	Use the getProtocol method to gain access to the Protocol class object as it contains the name 
*	of the protocol, its enumeration types, and the values belonging to each enumeration type.<br>
*	<br>
*	
*	Now that it is understood how to create a configuration file, the types to be used in the program
*	will be addressed.  For every protocol, such as A220 or C220, there must be two kinds of
*	configuration files: a "types" and a "indices" file.<br>
*	<br>
*	
*	The "types" file must list only one enumeration set that identifies the values associated with
*	the protocol type.  For example, to create a types package for the a220 protocol, the file
*	might look like the one below.<br>
*	
*	<pre>
*	
*	                 "a220_types.txt"
*		|---------------------------------------|
*		|protocol:a220:                         |
*		|                                       |
*		|enumeration:                           |  \
*		|a220,                                  |   |
*		|Channel_Configuration_Type,            |   |
*		|Protocol_Configuration_Type,           |   |-- List of objects belonging to A220.
*		|Disable_Channel_Type,                  |   |   These are the objects to be retrieved
*		|Enable_Channel_Type,                   |   |   from the parameters tables.  There
*		|Netcon_Configuration_Type,             |   |   will be five choices that will appear
*		|end,                                   |  /    in the combobox.
*		|---------------------------------------|
*	
*	</pre>
*	
*	The "indices" file can list one or more enumerations that will serve as index values.  For example,
*	in order to find a record in the table, a combination of indices must be used to locate a certain
*	row in the table.  These indices are what should be placed in the "indices" file.  Here is an 
*	example of an "indices" file for the A220 protocol.
*	
*	<pre>
*	
*	               "a220_indices.txt"
*		|----------------------------------------|
*		|protocol:A220:                          |
*		|                                        |
*		|enumeration:                            | \
*		|Device_Type,                            |  |
*		|Two_Wire_Net,                           |  |
*		|Four_Wire_Net,                          |  |
*		|Analog_Radio_Net,                       |  |--- This is an index enumeration of the Device_type.
*		|Ky57_Sincgars_Net,                      |  |    It has seven choices that will appear in its
*		|Sincgars_Icom_Net,                      |  |    combobox.
*		|Sincgars_Sip_Net,                       |  |
*		|Hf_Config_Net,                          |  |
*		|end,                                    | /
*		|                                        |
*		|enumeration:                            |
*		|Modulation_Type,                        |
*		|Nrz,                                    |
*		|Cdp,                                    |
*		|Fsk_188C,                               |
*		|Stanag4202A,                            |
*		|end,                                    |
*		|                                        |
*		|enumeration:                            |
*		|Data_Rate_Type,                         |
*		|Rate_75_Baud,                           |
*		|Rate_150_Baud,                          |
*		|Rate_300_Baud,                          |
*		|Rate_600_Baud,                          |
*		|Rate_1200_Baud,                         |
*		|Rate_2400_Baud,                         |
*		|Rate_4800_Baud,                         |
*		|Rate_9600_Baud,                         |
*		|Rate_16000_Baud,                        |
*		|Rate_32000_Baud,                        |
*		|end,                                    |
*		|                                        |
*		| (... and so on)                        |
*		|----------------------------------------|
*	
*	</pre>
*	
*	Once the "types" and "indices" files have been created, be sure to identify their full pathnames in the
*	ProtocolTestDriver.java source file.
*	
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/
class NetworkTypesFile
{
	/**
	*	This is a variable of the MyPrint class.  This is for debugging purposes only.  See the MyPrint class
	*	for usages.
	*/
	private MyPrint p;
	
	/**
	*	This is a variable of the Protocol class.
	*/
	private Protocol protocol;

	/**
	*	This constructs an object of the class.
	*/
	public NetworkTypesFile()
	{
		p = new MyPrint();
	}
	
	/**
	*	Determines if the String passed in is a comment line.
	*	
	*	@param line is a String of text
	*	
	*	@return True if the line is a comment line.
	*/
	private boolean isComment(String line)
	{
		int startOfDashes = 0;
		boolean isCommentLine = false;
		String tempString = "";
		
		if((startOfDashes = line.indexOf("--")) >= 0)
		{
			if(startOfDashes == 0)
			{
				isCommentLine = true;
			}
			else
			{
				tempString = line.substring(0,startOfDashes-1);
				if(tempString.trim().equals(""))
				{
					isCommentLine = true;
				}
			}
		}
		return isCommentLine;
	}
	
	/**
	*	Determines if the String passed in contains the searchString.
	*	
	*	@param line is the String to be searched
	*	@param searchString is the String to find
	*	
	*	@return True if the line contains the searchString
	*/
	private boolean contains(String line, String searchString)
	{
		boolean contains = false;
		if(line.indexOf(searchString) > -1)
		{
			contains = true;
		}
		return contains;
	}
	
	/**
	*	This method reads a file and returns an EnumerationType object.
	*	
	*	@param infile is the name of file
	*	
	*	@return An EnumerationType object
	*/
	private EnumerationType extractEnumerationType(BufferedReader infile)
	{
		EnumerationType enum = null;
		try
		{
			String line = infile.readLine();
			StringTokenizer st = new StringTokenizer(line,",");
			String enumName = new String(st.nextToken());
			Vector values = new Vector();
			Object item;
			
			ExtractEnumerationBlock:
			while((line = infile.readLine()) != null)
			{
				st = new StringTokenizer(line,",");
				if(st.hasMoreTokens())
				{
					item = st.nextToken();
					if(item.equals("end"))
					{
						break ExtractEnumerationBlock;
					}
					else
					{
						values.add(item);
					}
				}
			}
			
			enum = new EnumerationType(enumName,values);
		}
		catch (Exception e)
		{
			p.print(e + " in extractEnumerationType()");
		}
		return enum;
	}
	
	/**
	*	Reads a file and then extracts the type information and saves it to a class-wide
	*	variable called protocol.
	*	
	*	@param filename is the name of file to be read.
	*	
	*	@return void
	*/
	public void readFile(String fileName)
	{
		try
		{
			final String colon = ":";
			boolean containsProtocol = false;
			String line;
			StringTokenizer st;
			
			//It is IMPORTANT to use the InputStreamReader class to encapsulate the file read in
			//  because this is the only format capable of working if the application that uses 
			//  this class were to call this method from within a jar file.
 			BufferedReader infile = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
			FindProcessBlock:
			while ((line = infile.readLine()) != null)
			{
				if(!isComment(line))
				{
					if(contains(line,"protocol:"))
					{
						st = new StringTokenizer(line,colon);
						st.nextToken();
						String protocolName = (String)st.nextToken();
						protocol = new Protocol(protocolName);
						containsProtocol = true;
					}
					else if(contains(line,"enumeration:") && containsProtocol)
					{
						EnumerationType enumObject = extractEnumerationType(infile);
						protocol.add(enumObject);
					}
				}
			}			
			infile.close();
		}
		catch (Exception e)
		{
			p.print(e + " in readFile()");
		}	
	}

	/**
	*	Uses the MyPrint class to print the protocol object.
	*	
	*	@return void
	*/	
	public void printProtocol()
	{
		protocol.print();
	}
	
	/**
	*	Returns protocol object.
	*	
	*	@return A Protocol class object
	*/
	public Protocol getProtocol()
	{
		return protocol;
	}
	
}