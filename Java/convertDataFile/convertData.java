import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.lang.Integer;
import java.lang.Character;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.io.*;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.print.*;
import java.awt.font.*;
import java.awt.geom.*;



class Convert extends JFrame
{
	public Convert()
	{
		try
		{
			boolean validFile = loadFile(this);
			if (validFile)
			{
				writeFile("result.dat");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}

	public Vector sort(Vector v)
	{
		Collections.sort(v,
			new Comparator()
			{
				public int compare(Object a, Object b)
				{
					Workout w1 = (Workout)a;
					Workout w2 = (Workout)b;
					Calendar date1 = w1.getDate().getDate();
					Calendar date2 = w2.getDate().getDate();
					if (date1.before(date2))
						return -1;
					if (date1.equals(date2))
						return 0;
					return 1;
				}
			});
			return v;
	}

    public void deleteCRs(String file) throws Exception
    {
	    //System.out.println("deleteCRs");

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            FileWriter out = new FileWriter("tmp.dat");
            int i=0;
            while(i!=-1)
            {
                i=in.read();
                if('\r'==(char)i || '\n'==(char)i || '\f'==(char)i || '?' ==(char)i )
                {
                    continue;
                }

                out.write(i);
            }//~while(i!=-1)...

            in.close();
            out.close();
            int j=0;
            BufferedReader in2 =
            new BufferedReader(new FileReader("tmp.dat"));
            FileWriter out2 = new FileWriter(file);
            while(j!=-1)
            {
                j=in2.read();
                out2.write(j);
            }

            in2.close();
            out2.close();

        }//~try...

        catch(Exception e)
        {
            throw new Exception("Error in processing file.");
        }
    }//~public void deleteCRs(St...

    public boolean loadFile(Component parent) throws Exception
    {
	    //System.out.println("loadFile");

        boolean fileValid = false;
        try
        {
	        String token = "";
            JFileChooser f = new JFileChooser();
            f.setCurrentDirectory(new File("."));
            f.setFileFilter(new DataFileFilter());
            int result = f.showOpenDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                currentFileName = f.getSelectedFile().getName();
                repaint();
                File fin = new File(currentFileName);
                if (fin.exists())
                {
                    try
                    {
	                    //open file and remove all newlines
    	                deleteCRs(currentFileName);
        	            BufferedReader br = new BufferedReader
            	        (new FileReader(currentFileName));
                	    String s = br.readLine();
                    	StringTokenizer t = new StringTokenizer(s,"|");

                    	int logRecords = Integer.parseInt(t.nextToken());

    	                if(logRecords > 0)
        	            {
            	            ProgressMonitor progressBar =
                	        new ProgressMonitor(logRecords);
                    	    progressBar.show();
                        	Vector log = new Vector(logRecords,5);
	                        Vector mntLog = new Vector(5);
                        	Vector equipLog = new Vector(EQUIPRECORDMAX);
	                        for (int i=0; i<logRecords; i++)
    	                    {
        	                    progressBar.update(i+1);
            	                Workout in = new Workout();
                	            in.readData(t);
                	            WorkoutEvent [] e = in.getEvents();
                    	        log.add(in);
                        	}

	                        progressBar.setVisible(false);

    	                    int maintRecords = Integer.parseInt(t.nextToken());
        	                if(maintRecords > 0)
            	            {
                	            for (int i=0; i<maintRecords; i++)
                    	        {
                        	        Maintenance mentry = new Maintenance();
                            	    mentry.readData(t);
                                	mntLog.add(mentry);
	                            }
    	                    }

	                        for(int i=0; i<=EQUIPRECORDMAX - 1; i++)
    	                    {
        	                    token = t.nextToken();
            	                if ("equiprun".equals(token))
                	            {
                    	            Run rentry = new Run();
                        	        rentry.readData(t);
                                	equipLog.add(rentry);
	                                continue;
    	                        }

        	                    else if ("equipbike".equals(token))
            	                {
                	                Bike bentry = new Bike();
                    	            bentry.readData(t);
                            	    equipLog.add(bentry);
                                	continue;
	                            }

	                            else if ("equipswim".equals(token))
    	                        {
        	                        Swim sentry = new Swim();
            	                    sentry.readData(t);
                    	            equipLog.add(sentry);
                        	        continue;
                            	}

	                            else if ("equipwalk".equals(token))
    	                        {
        	                        Walk wentry = new Walk();
            	                    wentry.readData(t);
                    	            equipLog.add(wentry);
                        	        continue;
                            	}
	                        }//~for(int i=0; i<=EQUIPREC...

    	                    //populate logbook and mntlogbook vectors
        	                logbook = sort(log);
            	            mntlogbook = mntLog;
                	        equiplogbook = equipLog;

                    	    fileValid = true;

            	        }//~if(logRecords > 0)...
						setTitle("T.E.W.L - " + currentFileName);
                    	br.close();
					}

	                catch (Exception e)
					{
       	                fileValid = false;
           	            currentFileName = "";
						setTitle("T.E.W.L - " + currentFileName);
               	        throw new Exception("File contains invalid data.");
                   	}

				}//~if (fin.exists())...
        	    else if (result == JFileChooser.CANCEL_OPTION)
				{

            	}//~if (result == JFileChoos...
				else
				{
					fileValid = false;
					currentFileName = "";
					setTitle("T.E.W.L - " + currentFileName);
					throw new Exception("File does not exist.");
				}
			}
        }//~try...
        catch (Exception e)
        {
	        throw e;
        }

        return fileValid;
    }//~public boolean loadFile(...

    //Writes vector to file
    public boolean writeFile(String fileName) throws Exception
    {
	    //System.out.println("writeFile");

        boolean fileSaved = false;

        try
        {
            PrintWriter fout = new PrintWriter(new FileWriter(fileName));

            fout.print(logbook.size() + "|");

            Workout w = null;
            for(int i=0; i<logbook.size(); i++)
            {
                w = (Workout)logbook.elementAt(i);
                w.printData(fout);
            }

            if(mntlogbook.isEmpty())
            {
                fout.print("0|*|*|");
            }

            else
            {
                fout.print(mntlogbook.size() + "|");

                Maintenance m = null;
                for(int i=0; i<mntlogbook.size(); i++)
                {
                    m = (Maintenance)mntlogbook.elementAt(i);
                    m.printData(fout);
                }
            }//~else...

            if(equiplogbook.isEmpty())
            {
                fout.print("equiprun|*|*|");
                fout.print("equipbike|*|*|*|*|*|*|*|");
                fout.print("equipswim|*|");
                fout.print("equipwalk|*|*|");
            }
            else
            {
                Equipment e = new Equipment();
                for(int i=0; i<=3; i++)
                {
                    e = (Equipment)equiplogbook.elementAt(i);
                    if (e instanceof Run)
                    {
                        ((Run)e).print(fout);
                    }

                    else if (e instanceof Bike)
                    {
                        ((Bike)e).print(fout);
                    }

                    else if (e instanceof Swim)
                    {
                        ((Swim)e).print(fout);
                    }

                    else if (e instanceof Walk)
                    {
                        ((Walk)e).print(fout);
                    }
                }//~for(int i=0; i<=3; i++)...
            }//~else...

            fout.print("routeoptions|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|");
            fout.print("exerciseoptions|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|");

            fout.close();
			setTitle("T.E.W.L - " + fileName);
            fileSaved = true;
        }//~try...

        catch(Exception e)
        {
            fileSaved = false;
        }

        return fileSaved;
    }//~public boolean writeFile...

    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();

    }

    String currentFileName = "";
    Vector log, logbook,
    		mntlog, mntlogbook,
    		equiplog, equiplogbook;
    private static int EQUIPRECORDMAX = 4;

}



public class convertData
{
	public static void main (String [] args)
	{
       try
        {
            UIManager.setLookAndFeel
            (UIManager.getSystemLookAndFeelClassName());
            // Sets common LAF if system not available
            //	UIManager.setLookAndFeel
            //		(UIManager.getCrossPlatformLookAndFeelClassName());
	        JFrame mainFrame = new Convert();
	        mainFrame.setVisible(true);
	        mainFrame.pack();
	        Toolkit tk = Toolkit.getDefaultToolkit();
	        Dimension screen = tk.getScreenSize();
	        Dimension size = mainFrame.getSize();
	        int y_loc = (screen.height - size.height)/2;
	        int x_loc = (screen.width - size.width)/2;
	        mainFrame.setLocation(x_loc,y_loc);
			System.exit(0);
        }

        catch(Exception e)
        {
//            System.err.println("Using common Look and Feel interface.");
			System.err.println("Program error.");
        }

	}
}