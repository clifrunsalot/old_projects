import java.io.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


public class DataFile
{
	public DataFile(boolean debugOn, String [] fileNames)
	{
		p = new MyPrint();
		
		//set debug status
		DEBUG = debugOn;
		
		String originalFile = fileNames[0];
		String masterFile = fileNames[1];
		String tempFile = fileNames[2];
		String processNamesFile = fileNames[3];
		
		//Create Vector list
		processList = new Vector();
				
		//load window
		try
		{
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
	    	p.print(e + ": Unable to get system look and feel; using defaults");   
        }
        
        processNames = createProcessNamesList(masterFile,processNamesFile);
        
		//read file and setup process attributes
		readFile(masterFile);
		
	}
	
	public String [] getProcessNames()
	{
		return processNames;
	}

	String getProcessName(String s)
	{
		if(DEBUG)
			p.print("inside getProcessName()");
			
		String name = "no process name";
			
		find_match:
		for (int i=0; i<processNames.length; i++)
		{
			if (s.indexOf(processNames[i]) > -1)
			{
				name = processNames[i];
				break find_match;
			}
		}
		return name;
	}
	
	MyProcess getProcess(String s)
	{
		MyProcess process = new MyProcess();
		findProcess:
		for(int i=0; i<processList.size(); i++)
		{
			process = (MyProcess)processList.elementAt(i);
			if(process.getName().equals(s))
			{
				break findProcess;
			}
		}
		return process;
	} 
	
	boolean isValidProcess(String s)
	{
		if(DEBUG)
			p.print("inside isValidProcess()");
			
		boolean isValid = false;
		
		find_match:
		for (int i=0; i<processNames.length; i++)
		{
			if (s.indexOf(processNames[i]) > -1)
			{
				isValid = true;
				break find_match;
			}
		}
		
		if (isValid)
		{
			if (!(s.indexOf("PROCESS")>-1))
				isValid = false;
		}
		
		return isValid;
	}
	
	StringBuffer changeHeartbeat(StringBuffer sb, boolean heartbeatOn, int startOfChange, int endOfChange)
	{
		if(DEBUG)
			p.print("inside changeHeartbeat()");
			
		if(heartbeatOn)
			sb = sb.replace(startOfChange,endOfChange,"True");
		else
			sb = sb.replace(startOfChange,endOfChange,"False");
		
		return sb;
	}
	
	public Vector getOriginalSettings(String masterFile)
	{
		readFile(masterFile);
		return processList;
	}
	
	public void updateFile(Vector windowList, String modifiedFile, String tempFile)
	{
		if(DEBUG)
			p.print("inside updateFile()");
			
		SingleProcessWindow singleWindow;
		MyProcess process;
		for(int i=0; i<windowList.size(); i++)
		{
			for(int j=0; j<processList.size(); j++)
			{
				singleWindow = (SingleProcessWindow)windowList.elementAt(i);
				process = (MyProcess)processList.elementAt(j);
				
				if(singleWindow.getName().equals(process.getName()))
				{
					process.setHeartbeatRequired(singleWindow.getHeartbeatRequiredCheckBox().isSelected());
					process.setCommentedOut(singleWindow.getCommentedOutCheckBox().isSelected());
				}
			}
		}
		writeHeartbeatsToFile(modifiedFile,tempFile);
		writeCommentedOutToFile(tempFile,modifiedFile);
	}
	
	String [] createProcessNamesList(String f1, String f2)
	{
		if(DEBUG)
			p.print("inside createProcessNamesList()");
			
		Vector processNamesList = new Vector();
		String [] processNamesArray;
		int len = 0;
			
		try
		{
			String line = " ";
			String process = " ";
			int colonPosition = 0;
			BufferedReader infile = new BufferedReader(new FileReader(new File(f1)));
			BufferedWriter outfile = new BufferedWriter(new FileWriter(new File(f2)));
						
			while ((line = infile.readLine()) != null)
			{
				if (line.toLowerCase().indexOf("= executables:") > -1)
				{
					if((colonPosition = line.toLowerCase().indexOf(":")) > -1)
					{
						process = line.toLowerCase().substring(colonPosition + 1,line.length());
						processNamesList.add(process);
						outfile.write(process + '\n');
					}
				}
			}
			
			infile.close();
			outfile.close();
		}
		catch (Exception e)
		{
			p.print(e + " in createProcessNamesList");
		}
		
		len = processNamesList.size();
		
		processNamesArray = new String[len];
		processNamesList.copyInto(processNamesArray);
		
		return processNamesArray;
	}
	
	void readFile(String fileName)
	{
		if(DEBUG)
			p.print("inside readFile()");
			
		try
		{
			String dashes = "--";
			String line = " ";
			String nameInArray;
			int atBeginningOfLine = 0;
			int startOfLine = 0;
			int startOfChange = 0;
			boolean processFound = false;
			MyProcess process = new MyProcess();
			BufferedReader infile = new BufferedReader(new FileReader(new File(fileName)));

			FindProcessBlock:
			while ((line = infile.readLine()) != null)
			{
				if (isValidProcess(line))
				{
					nameInArray = getProcessName(line);
 					process = new MyProcess(getProcessName(nameInArray));

					FindCommentOutProcessBlock:					
					if(line.startsWith(dashes,atBeginningOfLine))
					{
						process.setOrigCommentedOut(true);
					}
					else if(!line.startsWith(dashes,atBeginningOfLine))
					{
						process.setOrigCommentedOut(false);
					}
								
					FindCommentedOutProcessBlock:
					while((line = infile.readLine()) != null)
					{
						if(!line.trim().equals(""))
						{
							if(line.toLowerCase().indexOf("heartbeat_required") > -1)
							{
								if((line.toLowerCase().indexOf("true")) > -1) 
								{
									process.setOrigHeartbeatRequired(true);
								}
								else if((line.toLowerCase().indexOf("false")) > -1)
								{
									process.setOrigHeartbeatRequired(false);
								}
							}
						}
						else
						{
							break FindCommentedOutProcessBlock;
						}
					}
					
					processList.add(process);
				}
			}			
			infile.close();
		}
		catch (Exception e)
		{
			p.print(e + " in readFile()");
		}	
	}
	
	public void writeHeartbeatsToFile(String fin, String fout)
	{
		if(DEBUG)
		{
			p.print("inside writeHeartbeatsToFile()");
		}
			
		try
		{
			String line = " ";
			String nameInArray;
			StringBuffer buffer;
			int startOfLine = 0;
			int startOfChange = 0;
			int endOfChange = 0;
			boolean processFound = false;
			MyProcess process = new MyProcess();
			BufferedReader infile = new BufferedReader(new FileReader(new File(fin)));
			BufferedWriter outfile = new BufferedWriter(new FileWriter(new File(fout)));

			//This block will process each line and find lines that contain
			// the processes in the processNames array.
			findHeartbeatRequired:
			while ((line = infile.readLine()) != null)
			{
				buffer = new StringBuffer(line);

				if (isValidProcess(buffer.toString()))
				{
					nameInArray = getProcessName(buffer.toString());
					process = getProcess(nameInArray);
					
					//Adding the '\n' to line and "1" to its length is necessary so that
					// the buffer will print correctly inside the file.
					outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
					
					ChangeHeartbeatInProcessBlock:
					while((line = infile.readLine()) != null)
					{
						if(!line.trim().equals(""))
						{
							buffer = new StringBuffer(line);
							if(buffer.toString().toLowerCase().indexOf("heartbeat_required") > -1)
							{
								if((startOfChange = buffer.toString().toLowerCase().indexOf("true")) > -1 
									&& process.getHeartbeatRequired() == false)
								{
									endOfChange = startOfChange + "true".length();
									buffer = changeHeartbeat(buffer,false,startOfChange,endOfChange);

									//Adding the '\n' to line and "1" to its length is necessary so that
									// the buffer will print correctly inside the file.
									outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
								}
								else if((startOfChange = buffer.toString().toLowerCase().indexOf("false")) > -1 
									&& process.getHeartbeatRequired() == true)
								{
									endOfChange = startOfChange + "false".length();
									buffer = changeHeartbeat(buffer,true,startOfChange,endOfChange);

									//Adding the '\n' to line and "1" to its length is necessary so that
									// the buffer will print correctly inside the file.
									outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
								}
								else
								{
									//Adding the '\n' to line and "1" to its length is necessary so that
									// the buffer will print correctly inside the file.
									outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
								}
							}
							else
							{
								//Adding the '\n' to line and "1" to its length is necessary so that
								// the buffer will print correctly inside the file.
								outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
							}
						}
						else
						{
							outfile.write('\n');
							break ChangeHeartbeatInProcessBlock;
						}
					}
				}
				else
				{
					//Adding the '\n' to line and "1" to its length is necessary so that
					// the buffer will print correctly inside the file.
					outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
				}
			}
			
			infile.close();
			outfile.close();
		}
		catch (Exception e)
		{
			p.print(e + " in writeHeartbeatsToFile()");
		}	
	}

	public void writeCommentedOutToFile(String fin, String fout)
	{
		if(DEBUG)
		{
			p.print("inside writeCommentedOutToFile()");
		}
			
		try
		{
			boolean processFound = false;
			final String dashes = "--";
			String line = " ";
			String nameInArray;
			StringBuffer buffer;
			final int atBeginningOfLine = 0;
			MyProcess process = new MyProcess();
			BufferedReader infile = new BufferedReader(new FileReader(new File(fin)));
			BufferedWriter outfile = new BufferedWriter(new FileWriter(new File(fout)));

			//This block will process each line and find lines that contain
			// the processes in the processNames array.
			findCommentedOutProcess:
			while ((line = infile.readLine()) != null)
			{
				buffer = new StringBuffer(line);

				if (isValidProcess(buffer.toString()) && !processFound)
				{
					nameInArray = getProcessName(buffer.toString());
					process = getProcess(nameInArray);
					
					if(buffer.toString().startsWith(dashes,atBeginningOfLine) && (process.getCommentedOut() == false))
					{
						buffer = buffer.delete(0,2);
						//Adding the '\n' to line and "1" to its length is necessary so that
						// the buffer will print correctly inside the file.
						outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
						
						uncommentRestOfProcessBlock:
						while((line = infile.readLine()) != null)
						{
							if(!line.trim().equals(""))
							{
								buffer = new StringBuffer(line);
								buffer = buffer.delete(0,2);
								//Adding the '\n' to line and "1" to its length is necessary so that
								// the buffer will print correctly inside the file.
								outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
							}
							else
							{
								outfile.write('\n');
								break uncommentRestOfProcessBlock;
							}
						}
					}
					else if(!buffer.toString().startsWith(dashes,atBeginningOfLine) && (process.getCommentedOut() == true))
					{
						buffer = buffer.insert(0,"--");
						//Adding the '\n' to line and "1" to its length is necessary so that
						// the buffer will print correctly inside the file.
						outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
						
						commentRestOfProcessBlock:
						while((line = infile.readLine()) != null)
						{
							if(!line.trim().equals(""))
							{
								buffer = new StringBuffer(line);
								buffer = buffer.insert(0,"--");
								//Adding the '\n' to line and "1" to its length is necessary so that
								// the buffer will print correctly inside the file.
								outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
							}
							else
							{
								outfile.write('\n');
								break commentRestOfProcessBlock;
							}
						}
					}
					else
					{
						//Adding the '\n' to line and "1" to its length is necessary so that
						// the buffer will print correctly inside the file.
						outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
					}
					
				}
				else
				{
					//Adding the '\n' to line and "1" to its length is necessary so that
					// the buffer will print correctly inside the file.
					outfile.write(buffer.toString() + '\n',0,buffer.length() + 1);
				}
			}
			
			infile.close();
			outfile.close();
		}
		catch (Exception e)
		{
			p.print(e + " in writeCommentedOutToFile()");
		}	
	}
	
	private boolean DEBUG;
	private MyPrint p;
	private String [] processNames;
	private Vector processList;
}


