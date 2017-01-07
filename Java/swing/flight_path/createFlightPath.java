import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.PrintStream;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import java.lang.Double;
import java.lang.Long;
import java.lang.Math;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.*;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;


class MyPrint extends JFrame 
{
	public MyPrint(HashMap pathPrintOpts, FlightPath p)
	{
		setTitle("Select print options");
		optPanel = new JPanel(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		path = p;

		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		//Print options
		indexChkBox         = new JCheckBox("Index");
		trackChkBox         = new JCheckBox("Track");
		
		latRadChkBox        = new JCheckBox("Latitude (rad)");
		latDegChkBox        = new JCheckBox("Latitude (deg)");
		latCodeChkBox       = new JCheckBox("Latitude (code)");

		longRadChkBox       = new JCheckBox("Longitude (rad)");
		longDegChkBox       = new JCheckBox("Longitude (deg)");
		longCodeChkBox      = new JCheckBox("Longitude (code)");

		courseRadChkBox     = new JCheckBox("Course (rad)");
		courseDegChkBox     = new JCheckBox("Course (deg)");
		courseCodeChkBox    = new JCheckBox("Course (code)");
		
		offsetDistRadChkBox = new JCheckBox("Offset Distance (rad)");
		offsetDistKmChkBox  = new JCheckBox("Offset Distance (km)");
		offsetDistMiChkBox  = new JCheckBox("Offset Distance (mi)");
		offsetDistNMChkBox  = new JCheckBox("Offset Distance (nm)");
		offsetDistDMChkBox  = new JCheckBox("Offset Distance (dm)");

		altMetChkBox        = new JCheckBox("Altitude (m)");
		altFtChkBox         = new JCheckBox("Altitude (ft)");
		altCodeChkBox       = new JCheckBox("Altitude (code)");

		speedKPHChkBox      = new JCheckBox("Speed (kph)");
		speedMPHChkBox      = new JCheckBox("Speed (mph)");
		speedNMPHChkBox     = new JCheckBox("Speed (nmph)");
		speedDMPHChkBox     = new JCheckBox("Speed (dmph)");
		speedCodeChkBox     = new JCheckBox("Speed (code)");
		
		offsetTimeMSChkBox  = new JCheckBox("Offset Time (ms)");
		offsetBinMSChkBox   = new JCheckBox("Offset Time (bin ms)");

		xCoordChkBox        = new JCheckBox("x-Coord");
		yCoordChkBox        = new JCheckBox("y-Coord");
		zCoordChkBox        = new JCheckBox("z-Coord");

		selectAllChkBox     = new JCheckBox("Select All");

		//Action Command for use later when trying to determine the
		// selected options
		indexChkBox.setActionCommand("0");
		trackChkBox.setActionCommand("1");
		
		latRadChkBox.setActionCommand("2");
		latDegChkBox.setActionCommand("3");
		latCodeChkBox.setActionCommand("4");
		
		longRadChkBox.setActionCommand("5");
		longDegChkBox.setActionCommand("6");
		longCodeChkBox.setActionCommand("7");
		
		courseRadChkBox.setActionCommand("8");
		courseDegChkBox.setActionCommand("9");
		courseCodeChkBox.setActionCommand("10");

		offsetDistRadChkBox.setActionCommand("11");
		offsetDistKmChkBox.setActionCommand("12");
		offsetDistMiChkBox.setActionCommand("13");
		offsetDistNMChkBox.setActionCommand("14");
		offsetDistDMChkBox.setActionCommand("15");

		altMetChkBox.setActionCommand("16");
		altFtChkBox.setActionCommand("17");
		altCodeChkBox.setActionCommand("18");

		speedKPHChkBox.setActionCommand("19");
		speedMPHChkBox.setActionCommand("20");
		speedNMPHChkBox.setActionCommand("21");
		speedDMPHChkBox.setActionCommand("22");
		speedCodeChkBox.setActionCommand("23");
		
		offsetTimeMSChkBox.setActionCommand("24");
		offsetBinMSChkBox.setActionCommand("25");

		xCoordChkBox.setActionCommand("26");
		yCoordChkBox.setActionCommand("27");
		zCoordChkBox.setActionCommand("28");

		selectAllChkBox.setActionCommand("29");

		//An array for all of the print options checkboxes
		printOptArray = new JComponent [] {indexChkBox,
													trackChkBox,
													latRadChkBox,
													latDegChkBox,
													latCodeChkBox,
													longRadChkBox,
													longDegChkBox,
													longCodeChkBox,
													courseRadChkBox,
													courseDegChkBox,
													courseCodeChkBox,
													offsetDistRadChkBox,
													offsetDistKmChkBox,
													offsetDistMiChkBox,
													offsetDistNMChkBox,
													offsetDistDMChkBox,
													altMetChkBox,
													altFtChkBox,
													altCodeChkBox,
													speedKPHChkBox,
													speedMPHChkBox,
													speedNMPHChkBox,
													speedDMPHChkBox,
													speedCodeChkBox,
													offsetTimeMSChkBox,
													offsetBinMSChkBox,
													xCoordChkBox,
													yCoordChkBox,
													zCoordChkBox,
													selectAllChkBox};

		//Adding components to panel
		add(optPanel,indexChkBox,1,1,1,1,"W");
		add(optPanel,trackChkBox,2,1,1,1,"W");
		
		add(optPanel,latRadChkBox,1,2,1,1,"W");
		add(optPanel,latDegChkBox,2,2,1,1,"W");
		add(optPanel,latCodeChkBox,3,2,1,1,"W");
		
		add(optPanel,longRadChkBox,1,3,1,1,"W");
		add(optPanel,longDegChkBox,2,3,1,1,"W");
		add(optPanel,longCodeChkBox,3,3,1,1,"W");
		
		add(optPanel,courseRadChkBox,1,4,1,1,"W");
		add(optPanel,courseDegChkBox,2,4,1,1,"W");
		add(optPanel,courseCodeChkBox,3,4,1,1,"W");
	
		add(optPanel,offsetDistRadChkBox,1,5,1,1,"W");
		add(optPanel,offsetDistKmChkBox,2,5,1,1,"W");
		add(optPanel,offsetDistMiChkBox,3,5,1,1,"W");
		add(optPanel,offsetDistNMChkBox,4,5,1,1,"W");
		add(optPanel,offsetDistDMChkBox,5,5,1,1,"W");
		
		add(optPanel,altMetChkBox,1,6,1,1,"W");
		add(optPanel,altFtChkBox,2,6,1,1,"W");
		add(optPanel,altCodeChkBox,3,6,1,1,"W");
		
		add(optPanel,speedKPHChkBox,1,7,1,1,"W");
		add(optPanel,speedMPHChkBox,2,7,1,1,"W");
		add(optPanel,speedNMPHChkBox,3,7,1,1,"W");
		add(optPanel,speedDMPHChkBox,4,7,1,1,"W");
		add(optPanel,speedCodeChkBox,5,7,1,1,"W");
		
		add(optPanel,offsetTimeMSChkBox,1,8,1,1,"W");
		add(optPanel,offsetBinMSChkBox,2,8,1,1,"W");

		add(optPanel,xCoordChkBox,1,9,1,1,"W");
		add(optPanel,yCoordChkBox,2,9,1,1,"W");
		add(optPanel,zCoordChkBox,3,9,1,1,"W");

		add(optPanel,selectAllChkBox,3,10,2,1,"W");

		fileNamePanel = new JPanel(new GridBagLayout());
		fileNameTF    = new JTextField(20);
		add(fileNamePanel,new JLabel("Filename: "),1,1,1,1,"E");
		add(fileNamePanel,fileNameTF,2,1,1,1,"W");
		
		btnPanel  = new JPanel(new GridBagLayout());
		printBtn = new JButton("Print");
		cancelBtn = new JButton("Cancel");
		add(btnPanel,printBtn,1,1,1,1,"C");
		add(btnPanel,cancelBtn,3,1,1,1,"C");
	
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		mainPanel.add(optPanel);
		mainPanel.add(fileNamePanel);
		mainPanel.add(btnPanel);
		getContentPane().add(mainPanel);

		optPanel.setOpaque(true);
		fileNamePanel.setOpaque(true);
		btnPanel.setOpaque(true);

		optPanel.setBorder(border);
		fileNamePanel.setBorder(border);
		btnPanel.setBorder(border);

		pack();
		
		// Position GUI on Monitor
		//
		int heightOfFrame = getContentPane().getHeight();
		int widthOfFrame  = getContentPane().getWidth();
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension screen = tk.getScreenSize();
      int y_loc = (screen.height/3) - (heightOfFrame/3);
      int x_loc = (screen.width/3) - (widthOfFrame/3);
      setLocation(x_loc,y_loc);
		setVisible(true);

		setEnabled(pathPrintOpts);

		selectAllChkBox.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					//If SelectAll is not selected, then
					// unselect all print options.
					if(!selectAllChkBox.isSelected())
					{
						for(int i=0; i<printOptArray.length; i++)
						{
							if(printOptArray[i] instanceof JCheckBox)
							{
								((JCheckBox)printOptArray[i]).setSelected(false);
							}
						}
					}
					else
					{	
						//Else, select all.
						for(int i=0; i<printOptArray.length; i++)
						{
							if(printOptArray[i] instanceof JCheckBox)
							{
								if(((JCheckBox)printOptArray[i]).isEnabled())
									((JCheckBox)printOptArray[i]).setSelected(true);
							}
						}
					}
				}
			}
		);

		printBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(anySelected() && !fileNameTF.getText().trim().equals("") && fileNameTF.getText() != null)
					{
						try
						{
							PrintWriter stream = new PrintWriter(new FileWriter(new File(fileNameTF.getText())));
							if(path instanceof IndexPath)
							{
								MyProgressMonitor monitor = new MyProgressMonitor("Saving results ... ", 
																								((IndexPath)path).getPath().size()-1);
								((IndexPath)path).printPath(stream,monitor,getSelectedOptions());
								stream.close();
								setVisible(false);
							}
							else if(path instanceof TimePath)
							{
								MyProgressMonitor monitor = new MyProgressMonitor("Saving results ... ", 
																								((TimePath)path).getPath().size()-1);
								((TimePath)path).printPath(stream,monitor,getSelectedOptions());
								stream.close();
								setVisible(false);
							}
							else if(path instanceof MultiTrackPath)
							{
								MyProgressMonitor monitor = new MyProgressMonitor("Saving results ... ", 
																								((MultiTrackPath)path).getPath().size()-1);
								((MultiTrackPath)path).printPath(stream,monitor,getSelectedOptions());
								stream.close();
								setVisible(false);
							}
						}
							catch(IOException ie)
						{
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, 
														"Please select options to print \n" + 
														"and a filename for the results.", 
														"Stop!", 
														JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);

		cancelBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					setVisible(false);
				}
			}
		);

	}

	void setEnabled(HashMap opts)
	{
		Boolean item = new Boolean(false);
		for(int i=0; i<printOptArray.length; i++)
		{
			item = (Boolean)(opts.get("" + i));
				printOptArray[i].setEnabled(item.booleanValue());
		}
	}

	boolean anySelected()
	{
		for(int i=0; i<printOptArray.length; i++)
		{
			if(printOptArray[i] instanceof JCheckBox)
			{
				if(((JCheckBox)printOptArray[i]).isSelected())
					return true;
			}
		}
		return false;
	}

	public HashMap getSelectedOptions()
	{
		HashMap optSelectedMap = new HashMap();

		for(int i=0; i<printOptArray.length; i++)
		{
			if(printOptArray[i] instanceof JCheckBox)
			{
				optSelectedMap.put("" + i,new Boolean(((JCheckBox)printOptArray[i]).isSelected()));
			}
		}
		return optSelectedMap;
	}

	public String getFileName()
	{
		return fileNameTF.getText();
	}

	//Easy way to add components to a GridBagLayout.
	void add(Container c, Component comp, int x, int y, int w, int h, String anchor)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx              = x;
		gbc.gridy              = y;
		gbc.gridwidth          = w;
		gbc.gridheight         = h;
		gbc.insets             = new Insets(3,3,3,3);

		if      (anchor.equals("C")){gbc.anchor = GridBagConstraints.CENTER;}
		else if (anchor.equals("E")){gbc.anchor = GridBagConstraints.EAST;}
		else if (anchor.equals("W")){gbc.anchor = GridBagConstraints.WEST;}

		c.add(comp,gbc);
	}		
	
	private JPanel optPanel;
	private JPanel fileNamePanel;
	private JPanel btnPanel;

	private JComponent [] printOptArray;

	private JCheckBox indexChkBox;
	private JCheckBox trackChkBox;
	
	private JCheckBox latRadChkBox;
	private JCheckBox latDegChkBox;
	private JCheckBox latCodeChkBox;
	
	private JCheckBox longRadChkBox;
	private JCheckBox longDegChkBox;
	private JCheckBox longCodeChkBox;
	
	private JCheckBox courseRadChkBox;
	private JCheckBox courseDegChkBox;
	private JCheckBox courseCodeChkBox;
	
	private JCheckBox offsetDistRadChkBox;
	private JCheckBox offsetDistKmChkBox;
	private JCheckBox offsetDistMiChkBox;
	private JCheckBox offsetDistNMChkBox;
	private JCheckBox offsetDistDMChkBox;
	
	private JCheckBox altMetChkBox;
	private JCheckBox altFtChkBox;
	private JCheckBox altCodeChkBox;
	
	private JCheckBox speedKPHChkBox;
	private JCheckBox speedMPHChkBox;
	private JCheckBox speedNMPHChkBox;
	private JCheckBox speedDMPHChkBox;
	private JCheckBox speedCodeChkBox;
	
	private JCheckBox offsetTimeMSChkBox;
	private JCheckBox offsetBinMSChkBox;

	private JCheckBox xCoordChkBox;
	private JCheckBox yCoordChkBox;
	private JCheckBox zCoordChkBox;

	private JCheckBox selectAllChkBox;

	private JButton printBtn;
	private JButton cancelBtn;

	private JTextField fileNameTF;

	private FlightPath path;
}

//Generic Class for progress bar.
// Just pass in max value and string to display in title bar.
// Use the update method to update the bar.
class MyProgressMonitor extends JDialog
{
    public MyProgressMonitor(String s,int records)
    {
        int baseofframe = 310;
        int heightofframe = 80;
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        int y_loc = (screen.height/2) - (heightofframe/2);
        int x_loc = (screen.width/2) - (baseofframe/2);
        setLocation(x_loc,y_loc);
        setTitle(s);
        setSize(baseofframe,heightofframe);
        totalRecords = records;
        p = new JPanel();
			p.setOpaque(true);
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(280,20));
        progress.setMinimum(0);
        progress.setMaximum(totalRecords);
        progress.setValue(0);
        progress.setBounds(20,35,260,20);
        p.add(progress);
		container = getContentPane();
        container.add(p);
		setVisible(true);
    }//~public ProgressMonitor(i...

    public void update(int i)
    {
		if((totalRecords) <= i)
		{
			setVisible(false);
		}

        progress.setValue(i);
        Rectangle progressRect = p.getBounds();
        progressRect.x = 0;
        progressRect.y = 0;
        p.paintImmediately(progressRect);
    }//~public void update(int i...

    private int totalRecords;
    private JPanel p;
    private JProgressBar progress;
	private Container container;
}

class RangeVerifier extends InputVerifier 
{
	public RangeVerifier(Double s, Double e)
	{
		dStart = s;
		dEnd   = e;
		type = "double";
	}

	public RangeVerifier(Integer s, Integer e)
	{
		iStart = s;
		iEnd   = e;
		type = "integer";
	}

	public RangeVerifier(Long s, Long e)
	{
		lStart = s;
		lEnd   = e;
		type = "long";
	}
	
	public boolean verify(JComponent input) 
	{
		if (input instanceof JFormattedTextField) 
		{
			GenericTextField gtf = (GenericTextField)input;
			String text = gtf.getText();
			
			try
			{
				NumberFormat nf = NumberFormat.getNumberInstance();
				nf.setParseIntegerOnly(false);

				Number value = nf.parse(text);

				if (type.equals("double"))
				{
					if ((value.doubleValue() > (dStart.doubleValue() - 0.01)) 
							&& (value.doubleValue() <= dEnd.doubleValue()))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else if(type.equals("integer"))
				{
					if ((value.intValue() > iStart.intValue()) 
							&& (value.intValue() <= iEnd.intValue()))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else if(type.equals("long"))
				{
					if ((value.longValue() > lStart.longValue()) 
							&& (value.longValue() <= lEnd.longValue()))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			catch (ParseException pe)
			{
				return false;
			}
		}
		return false; 
	}

	public boolean shouldYieldFocus(JComponent input)
	{
		return verify(input);
	}

	private String type;
	private Double dStart;
	private Double dEnd;
	private Integer iStart;
	private Integer iEnd;
	private Long lStart;
	private Long lEnd;
}

class GenericTextField extends JFormattedTextField
{
		public GenericTextField(NumberFormat format,
										int width,
										Double start, 
										Double end)
		{
			super(format);
			setHorizontalAlignment(JTextField.RIGHT);
			setColumns(width);
			setInputVerifier(new RangeVerifier(start,end));
		}

		public GenericTextField(NumberFormat format, 
										int width,
										Integer start, 
										Integer end)
		{
			super(format);
			setHorizontalAlignment(JTextField.RIGHT);
			setColumns(width);
			setInputVerifier(new RangeVerifier(start,end));
		}

		public GenericTextField(NumberFormat format,
										int width,
										Long start,
										Long end)
		{
			super(format);
			setHorizontalAlignment(JTextField.RIGHT);
			setColumns(width);
			setInputVerifier(new RangeVerifier(start,end));
		}

		public GenericTextField(NumberFormat format,
										int width)
		{
			super(format);
			setHorizontalAlignment(JTextField.RIGHT);
			setColumns(width);
			setEditable(false);
		}
}

class DoubleRenderer extends DefaultTableCellRenderer
{
    public DoubleRenderer()
	{
		super(); 
		setHorizontalAlignment(SwingConstants.RIGHT);
		format = NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(4);
		format.setMaximumFractionDigits(4);
	}

    public void setValue(Object value)
	{
       	setText(format.format(value));
    }

    private NumberFormat format;
}

class DistanceRenderer extends DefaultTableCellRenderer
{
    public DistanceRenderer()
	{
		super(); 
		setHorizontalAlignment(SwingConstants.RIGHT);
		format = NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
	}

    public void setValue(Object value)
	{
       	setText(format.format(value));
    }

    private NumberFormat format;
}

class Distance extends Number
{
	public Distance(double d)
	{
		value = new Double(d);
	}

	public double doubleValue()
	{
		return value.doubleValue();
	}

	public float floatValue()
	{
		return (float)value.doubleValue();
	}

	public int intValue()
	{
		return (int)value.doubleValue();
	}

	public long longValue()
	{
		return (long)value.doubleValue();
	}

	private Double value;
}


class IndexTableModel extends DefaultTableModel
{
	public IndexTableModel(Vector p)
	{
		path = p;
		cols = 5;
		rows = p.size();
	}

	public int getRowCount()
	{
		return rows;
	}

	public int getColumnCount()
	{
		return cols;
	}

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public Object getValueAt(int r, int c)
	{
		Point p = (Point)path.elementAt(r);
		Object item = "";
		if (c == 0)
		{
			item = new Integer(r);
		}
		else if (c == 1)
		{
			item = new Integer(p.getTrackID());
		}
		else if (c == 2)
		{
			item = new Double(p.getLatDeg());
		}
		else if (c == 3)
		{
			item = new Double(p.getLongDeg());
		}
		else if (c == 4)
		{
			item = new Distance(p.getDistanceKm());
		}
		return item;
	}

	public String getColumnName(int c)
	{
		String item = "";
		if (c == 0)
		{
			item = new String("Index");
		}
		else if (c == 1)
		{
			item = new String("Track #");
		}
		else if (c == 2)
		{
			item = new String("Latitude");
		}
		else if (c == 3)
		{
			item = new String("Longitude");
		}
		else if (c == 4)
		{
			item = new String("Km (offset)");
		}
		return item;
	}

	public boolean isCellEditable(int r, int c)
	{
		return false;
	}

	private int rows;
	private int cols;
	private Vector path;
}

class TimeTableModel extends DefaultTableModel
{
	public TimeTableModel(Vector p)
	{
		path = p;
		cols = 6;
		rows = p.size();
	}

	public int getRowCount()
	{
		return rows;
	}

	public int getColumnCount()
	{
		return cols;
	}

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public Object getValueAt(int r, int c)
	{
		Point p = (Point)path.elementAt(r);
		Object item = "";
		if (c == 0)
		{
			item = new Integer(r);
		}
		else if (c == 1)
		{
			item = new Integer(p.getTrackID());
		}
		else if (c == 2)
		{
			item = new Long(p.getTimeOffset());
		}
		else if (c == 3)
		{
			item = new Double(p.getLatDeg());
		}
		else if (c == 4)
		{
			item = new Double(p.getLongDeg());
		}
		else if (c == 5)
		{
			item = new Distance(p.getDistanceKm());
		}
		return item;
	}

	public String getColumnName(int c)
	{
		String item = "";
		if (c == 0)
		{
			item = new String("Index");
		}
		else if (c == 1)
		{
			item = new String("Track #");
		}
		else if (c == 2)
		{
			item = new String(" Milliseconds (offset) ");
		}
		else if (c == 3)
		{
			item = new String("Latitude");
		}
		else if (c == 4)
		{
			item = new String("Longitude");
		}
		else if (c == 5)
		{
			item = new String("Km (offset)");
		}
		return item;
	}

	public boolean isCellEditable(int r, int c)
	{
		return false;
	}

	private int rows;
	private int cols;
	private Vector path;
}

class Link16TableModel extends DefaultTableModel
{
	public Link16TableModel(Vector p)
	{
		path = p;
		cols = 7;
		rows = p.size();
	}

	public int getRowCount()
	{
		return rows;
	}

	public int getColumnCount()
	{
		return cols;
	}

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public Object getValueAt(int r, int c)
	{
		Point p = (Point)path.elementAt(r);
		Object item = "";
		if (c == 0)
		{
			item = new Integer(r);
		}
		else if (c == 1)
		{
			item = new Integer(p.getTrackID());
		}
		else if (c == 2)
		{
			item = new Long(p.getTimeOffset());
		}
		else if (c == 3)
		{
			item = new Long(p.getTimeOffsetBinaryMSSinceMidnight());
		}
		else if (c == 4)
		{
			item = new Double(p.getLatDeg());
		}
		else if (c == 5)
		{
			item = new Double(p.getLongDeg());
		}
		else if (c == 6)
		{
			item = new Distance(p.getDistanceKm());
		}
		return item;
	}

	public String getColumnName(int c)
	{
		String item = "";
		if (c == 0)
		{
			item = new String("Index");
		}
		else if (c == 1)
		{
			item = new String("Track #");
		}
		else if (c == 2)
		{
			item = new String(" Milliseconds (offset) ");
		}
		else if (c == 3)
		{
			item = new String(" BinMS Since Midnight (offset) ");
		}
		else if (c == 4)
		{
			item = new String("Latitude");
		}
		else if (c == 5)
		{
			item = new String("Longitude");
		}
		else if (c == 6)
		{
			item = new String("Km (offset)");
		}
		return item;
	}

	public boolean isCellEditable(int r, int c)
	{
		return false;
	}

	private int rows;
	private int cols;
	private Vector path;
}

class CECTableModel extends DefaultTableModel
{
	public CECTableModel(Vector p)
	{
		path = p;
		cols = 7;
		rows = p.size();
	}

	public int getRowCount()
	{
		return rows;
	}

	public int getColumnCount()
	{
		return cols;
	}

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public Object getValueAt(int r, int c)
	{
		Point p = (Point)path.elementAt(r);
		Object item = "";
		if (c == 0)
		{
			item = new Integer(r);
		}
		else if (c == 1)
		{
			item = new Integer(p.getTrackID());
		}
		else if (c == 2)
		{
			item = new Long(p.getTimeOffset());
		}
		else if (c == 3)
		{
			item = new Distance(p.getX());
		}
		else if (c == 4)
		{
			item = new Distance(p.getY());
		}
		else if (c == 5)
		{
			item = new Distance(p.getZ());
		}
		else if (c == 6)
		{
			item = new Distance(p.getDistanceKm());
		}
		return item;
	}

	public String getColumnName(int c)
	{
		String item = "";
		if (c == 0)
		{
			item = new String("Index");
		}
		else if (c == 1)
		{
			item = new String("Track #");
		}
		else if (c == 2)
		{
			item = new String(" Milliseconds (offset) ");
		}
		else if (c == 3)
		{
			item = new String(" X ");
		}
		else if (c == 4)
		{
			item = new String(" Y ");
		}
		else if (c == 5)
		{
			item = new String(" Z ");
		}
		else if (c == 6)
		{
			item = new String("Km (offset)");
		}
		return item;
	}

	public boolean isCellEditable(int r, int c)
	{
		return false;
	}

	private int rows;
	private int cols;
	private Vector path;
}

strictfp class Point
{
	public Point(int trkID, double lt, double lg, double cs, double dist, double alt)
	{
		trackID    = trkID;
		latitude   = lt;
		longitude  = lg;
		course     = cs;
		distance   = dist;
		altitude   = alt;
	}

	public Point(int trkID, double lt, double lg, double cs, double dist, double alt, double spd, long tm)
	{
		trackID    = trkID;
		latitude   = lt;
		longitude  = lg;
		course     = cs;
		distance   = dist;
		altitude   = alt;
		speed      = spd;
		timeOffset = tm;
	}

	public Point(int trkID, double x, double y, double z, double dist, double spd, long tm)
	{
		trackID    = trkID;
		xCoord     = x;
		yCoord     = y;
		zCoord     = z;
		distance   = dist;
		speed      = spd;
		timeOffset = tm;
	}

	public int getTrackID()
	{
		return trackID;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public double getLatDeg()
	{
		return ((latitude * 180)/Math.PI);
	}

	public int getLatCode()
	{
		double A      = 90;
		double B      = 1048575;
		double FACTOR = A/B;
		double result = 0;
		double lat    = getLatDeg();
		
		if(lat == 0)
		{
			result = 0;
		}
		else if(lat >= FACTOR && lat < 90)
		{
			result = lat * 1/FACTOR;
		}
		else if(lat == 90)
		{
			result = lat * 1/FACTOR;
		}
		else if(lat <= -FACTOR && lat > -90)
		{
			result = (B + 2) + ((Math.abs(-90) - Math.abs(lat)) * (B/A));
		}
		else if(lat == -90)
		{
			result = B + 2;
		}
		return (int)result;
	}

	// Need lat/long
	public double getXCoord()
	{
		// Axes are in Meters
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double eSq = (2 * f) - (f * f);
		double radiusCurvature = a / Math.sqrt(1 - eSq * Math.sin(latitude) * Math.sin(latitude));
		double htFromSurface = 0;

		return ((radiusCurvature + htFromSurface) * Math.cos(latitude) * Math.cos(longitude));
	}

	// Need lat/long
	public double getYCoord()
	{
		// Axes are in Meters
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double eSq = (2 * f) - (f * f);
		double radiusCurvature = a / Math.sqrt(1 - eSq * Math.sin(latitude) * Math.sin(latitude));
		double htFromSurface = 0;
		
		return ((radiusCurvature + htFromSurface) * Math.cos(latitude) * Math.sin(longitude));
	}

	// Need lat/long
	public double getZCoord()
	{
		// Axes are in Meters
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double eSq = (2 * f) - (f * f);
		double radiusCurvature = a / Math.sqrt(1 - eSq * Math.sin(latitude) * Math.sin(latitude));
		double htFromSurface = 0;
		
		return ((radiusCurvature * (1 - eSq) + htFromSurface) * Math.sin(latitude));
	}

	public double getX()
	{
		return xCoord;
	}

	public double getY()
	{
		return yCoord;
	}

	public double getZ()
	{
		return zCoord;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public double getLongDeg()
	{
		return ((longitude * 180)/Math.PI);
	}

	public int getLongCode()
	{
		double A      = 180;
		double B      = 2097151;
		double FACTOR = A/B;
		double result = 0;
		double lg     = getLongDeg();
		
		if(lg == 0)
		{
			result = 0;
		}
		else if(lg >= FACTOR && lg < 180)
		{
			result = lg * 1/FACTOR;
		}
		else if(lg == 180)
		{
			result = lg * 1/FACTOR;
		}
		else if(lg <= -FACTOR && lg > -180)
		{
			result = (B + 2) + ((Math.abs(-180) - Math.abs(lg)) * (B / 180));
		}
		else if(lg == -180)
		{
			result = B + 2;
		}
		return (int)result;
	}

	public double getCourse()
	{
		return course;
	}

	public double getCourseDeg()
	{
		return course * 180 / Math.PI;
	}

	public int getCourseCode()
	{
		return (int)getCourseDeg();
	}

	public double getDistance()
	{
		return distance;
	}

	public double getDistanceKm()
	{
		double radiusEarthKms = 6366.71;
		return ((Math.toDegrees(distance)/180) * Math.PI * radiusEarthKms);
	}

	public double getDistanceMiles()
	{
		double radiusEarthMiles = 1.15077946 * 180 * 60 / Math.PI;
		return ((Math.toDegrees(distance)/180) * Math.PI * radiusEarthMiles);
	}

	public double getDistanceNms()
	{
		double radiusEarthNms = 3435.43;
		return ((180 * 60) / Math.PI) * distance;
	}

	public double getDistanceDms()
	{
		double DM_PER_NAUT_MILE = 1.012685914;
		return getDistanceNms() * DM_PER_NAUT_MILE;
	}

	public double getAltMeter()
	{
		return altitude;
	}

	public double getAltFeet()
	{
		double FEET_PER_METER = 3.2808399;
		return altitude * FEET_PER_METER;
	}

	public int getAltCode()
	{
		double MAX_CODE_RG  = 8190;
		double INCREMENT    = 25;
		double MAX_ALT      = INCREMENT * MAX_CODE_RG;
		return (int)(MAX_CODE_RG * getAltFeet() / MAX_ALT);
	}

	public double getSpeedKPH()
	{
		return speed;
	}

	public double getSpeedMPH()
	{
		double MILES_PER_KM = 0.6213712;
		return speed * MILES_PER_KM;
	}

	public double getSpeedNMPH()
	{
		double NM_PER_KM = 0.5395926;
		return speed * NM_PER_KM;
	}

	public double getSpeedDMPH()
	{
		double DM_PER_NM = 1.012685914;
		return getSpeedNMPH() * DM_PER_NM;
	}

	public int getSpeedCode()
	{
		double MAX_DM_PER_HOUR = 4092;
		double MAX_CODE_RG     = 2046;
		return (int)(MAX_CODE_RG * getSpeedDMPH() / MAX_DM_PER_HOUR);
	}

	public long getTimeOffset()
	{
		return timeOffset;
	}

	public long getTimeOffsetBinaryMSSinceMidnight()
	{
		// Get current instance of time for today.
		Calendar instance = GregorianCalendar.getInstance();

		// Create a new instance of time called midnight and set it midnight
		Calendar midnight = new GregorianCalendar(instance.get(GregorianCalendar.YEAR),
																instance.get(GregorianCalendar.MONTH),
																instance.get(GregorianCalendar.DATE));
		midnight.set(GregorianCalendar.HOUR,0);
		midnight.set(GregorianCalendar.MINUTE,0);
		midnight.set(GregorianCalendar.SECOND,0);

		// Get midnight in milliseconds from EPOCH.
		long midnightMS = midnight.getTimeInMillis();

		// Get the diff between timeOffset and midnight. 
		long diff = timeOffset - midnightMS;

		// Convert to corresponding binary milliseconds, which is 1024 per second.
		long MS_PER_SEC = 1000;
		long BIN_MS_PER_SEC = 1024;

//		System.out.println("offset  : " + timeOffset);
//		System.out.println("midnight: " + midnightMS);
//		System.out.println("diff    : " + diff);
//		System.out.println("seconds : " + (diff/MS_PER_SEC));
//		System.out.println("bin_ms  : " + (diff/MS_PER_SEC * BIN_MS_PER_SEC));
//		System.out.println();
		
		return diff/MS_PER_SEC * BIN_MS_PER_SEC;
	}

	public static double toSeconds(double timeMs)
	{
		return (timeMs / 1000);
	}

	public static double toMinutes(double timeMs)
	{
		return (timeMs / (1000 * 60));
	}

	public static double toHours(double timeMs)
	{
		return (timeMs / (1000 * 60 * 60));
	}

	public static double toKilometers(double distRadians)
	{
		double radiusEarthKms = 1.852 * 180 * 60 / Math.PI;
		return ((Math.toDegrees(distRadians)/180) * Math.PI * radiusEarthKms);
	}

	public static double toMiles(double distRadians)
	{
		double radiusEarthMiles = 1.15077946 * 180 * 60 / Math.PI;
		return ((Math.toDegrees(distRadians)/180) * Math.PI * radiusEarthMiles);
	}

	public static double toNauticalMiles(double distRadians)
	{
		double radiusEarthNms = 3435.43;
		return ((180 * 60) / Math.PI) * distRadians;
	}

	public static double toDataMiles(double distRadians)
	{
		double DM_PER_NM = 1.012685914;
		return ((180 * 60) / Math.PI) * distRadians * DM_PER_NM;
	}

	public static double fromDegToRadians(double degree)
	{
		return degree * Math.PI / 180;
	}

	public static double fromRadToDegrees(double radians)
	{
		return (180 / Math.PI) * radians;
	}

	public static double toXCoord(double lat, double lg)
	{
		// Axes are in Meters
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double eSq = (2 * f) - (f * f);
		double radiusCurvature = a / Math.sqrt(1 - eSq * Math.sin(lat) * Math.sin(lat));
		double htFromSurface = 0;

		return ((radiusCurvature + htFromSurface) * Math.cos(lat) * Math.cos(lg));
	}
		
	public static double toYCoord(double lat, double lg)
	{
		// Axes are in Meters
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double eSq = (2 * f) - (f * f);
		double radiusCurvature = a / Math.sqrt(1 - eSq * Math.sin(lat) * Math.sin(lat));
		double htFromSurface = 0;
		
		return ((radiusCurvature + htFromSurface) * Math.cos(lat) * Math.sin(lg));
	}
		
	public static double toZCoord(double lat)
	{
		// Axes are in Meters
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double eSq = (2 * f) - (f * f);
		double radiusCurvature = a / Math.sqrt(1 - eSq * Math.sin(lat) * Math.sin(lat));
		double htFromSurface = 0;
		
		return ((radiusCurvature * (1 - eSq) + htFromSurface) * Math.sin(lat));
	}
		

	private int trackID;
	private double latitude;
	private double longitude;
	private double course;
	private double distance;
	private double altitude;
	private double speed;
	private long timeOffset;
	private double xCoord;
	private double yCoord;
	private double zCoord;
}

abstract strictfp class FlightPath
{
	abstract void calculatePath();

	abstract HashMap getPrintOptions();

	abstract void printPath(PrintWriter stream, MyProgressMonitor monitor, HashMap opts);

	double getDestLat(double oLat, double course, double dist)
	{
		return (Math.asin(Math.sin(oLat) * Math.cos(dist) + Math.cos(oLat) * Math.sin(dist) * Math.cos(course)));
	}

	double getDestLong(double oLong, double oLat, double dLat, double course, double dist)
	{
		double dLong = 0;

		if(Math.cos(dLat) == 0)
		{
			dLong = oLong;
		}
		else
		{
			// For all distances
			double y = Math.sin(course) * Math.sin(dist) * Math.cos(oLat);
			double x = Math.cos(dist) - Math.sin(oLat) * Math.sin(dLat);
			double z = Math.atan2(y,x);
			
			//For negative west longitude, use:
			// (oLong + z + Math.PI, 2 * Math.PI)
			//
			// For negative east longitude use:
			//  (oLong - z + Math.PI, 2 * Math.PI)
			//
			double remainder = Math.IEEEremainder(oLong + z + Math.PI, 2 * Math.PI);

			double result = remainder - Math.PI;

			if(result <= -Math.PI)
			{
				dLong = result + 2 * Math.PI;
			}
			else
			{
				dLong = result;
			}
		}

		return dLong;
	}

	
	double getCourse(double oLat, double oLong, double dLat, double dLong, double dist)
	{
		double course = 0;

		//For negative west longitude, use:
		// (Math.sin(dLong - oLong) > 0)
		// For negative east longitude, use:
		//  (Math.sin(dLong - oLong < 0)
		//
		if (Math.sin(dLong - oLong) > 0)
		{
			course = Math.acos((Math.sin(dLat) - Math.sin(oLat) * Math.cos(dist)) /
					(Math.sin(dist) * Math.cos(oLat)));
		}
		else
		{
			course = 2 * Math.PI - Math.acos((Math.sin(dLat) - Math.sin(oLat) * Math.cos(dist)) /
					(Math.sin(dist) * Math.cos(oLat)));
		}

		return course;
	}

	public double getDistance(double timeMS, double spdKPH)
	{
		double dist = 0;
		if((timeMS > 0) && (spdKPH > 0))
		{
			double radiusEarthKms = 6366.71;
			dist = (timeMS * spdKPH) / (1000 * 60 * 60 * radiusEarthKms);
		}
		return dist;
	}

//	public double getDistance(double oLat, double oLong, double dLat, double dLong)
//
//	{
//		return Math.acos(Math.sin(oLat) 
//									* Math.sin(dLat) 
//									+ Math.cos(oLat) 
//									* Math.cos(dLat)
//									* Math.cos(oLong - dLong));
//	}

	public double getDistance(double oLat, double oLong, double dLat, double dLong)
	{
		double a = 6378137;
		double b = 6356752.3142;
		double f = 1/298.257223563;
		double eSq = (2 * f) - (f * f);
		
		double U1 = Math.atan((1 - f) * Math.tan(oLat));
		double U2 = Math.atan((1 - f) * Math.tan(dLat));

		double L = dLong - oLong;
		double lambda = L;
		double lambdaP = 2 * Math.PI;

		double sinU1 = Math.sin(U1);
		double sinU2 = Math.sin(U2);
		double cosU1 = Math.cos(U1);
		double cosU2 = Math.cos(U2);

		double sinLambda = 0;
		double cosLambda = 0;

		double sinSigma = 0;
		double cosSigma = 0;
		double sigma = 0;
		double alpha = 0;
		double cos2Sigma = 0;
		double C = 0;
		double uSq = 0;
		double A = 0;
		double B = 0;
		double deltaSigma = 0;
		double distance = 0;
		int iter = 0;
		double cosSqAlpha = 0;

		while ((Math.abs(lambda - lambdaP) > Math.pow(10,-12)) && iter++ < 40)
		{
			sinLambda = Math.sin(lambda);
			cosLambda = Math.cos(lambda);

			sinSigma = Math.sqrt(Math.pow(cosU2 * sinLambda,2) +
									Math.pow(cosU1 * sinU2 - sinU1 * cosU2 * cosLambda,2));

			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;

			sigma = Math.atan2(sinSigma,cosSigma);

			alpha = Math.asin(cosU1 * cosU2 * sinLambda / sinSigma);

			cosSqAlpha = Math.cos(alpha) * Math.cos(alpha);

			cos2Sigma = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;

			C = (f / 16) * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));

			lambdaP = lambda;

			lambda = L + (1 - C) * f * Math.sin(alpha) *
					(sigma + C * sinSigma * (cos2Sigma + C * cosSigma * (-1 + 2 * cos2Sigma * cos2Sigma)));

		}

		uSq = cosSqAlpha * (a * a - b * b) / (b * b);

		A = 1 + (uSq/16384) * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		
		B = (uSq/1024) * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		
		deltaSigma = B * sinSigma * (cos2Sigma + (B/4) * (cosSigma * (-1 + 2 * cos2Sigma * cos2Sigma) - (B/6) * cos2Sigma * ( -3 + 4 * sinSigma * sinSigma) * ( -3 + 4 * cos2Sigma * cos2Sigma)));

		distance = b * A * (sigma - deltaSigma);

		return (distance / 1852) * (Math.PI / (180 * 60)) ; 
	}

	public double getTimeMilliSecondsBasedOnKm(double dist, double spd)
	{
		double time = 0;
		if((dist > 0) && (spd > 0))
		{
			double radiusEarthKms = 6366.71;
			time = dist * radiusEarthKms * 1000 * 60 * 60 / spd;
		}
		return time;
	}

	//Returns radius of curve at the latitude passed in.
	// Based on WGS84 Datum where:
	// Radius at Equator is 6,378,137 meters.
	// Radius at Pole is 6,356,752.3142 meters.
	double getRadiusOfCurvature(double lat)
	{
		double a   = 6378137;
		double b   = 6356752.3142;
		double f   = 1/298.257223563;
		double eSq = (2 * f) - (f * f);
		double sinSqTheta = Math.sin(lat) * Math.sin(lat);

		return a / Math.sqrt(1 - eSq * sinSqTheta); 
	}

	public void printPath(Vector path, PrintWriter stream, MyProgressMonitor monitor, HashMap opts)
	{
		NumberFormat timeFormat = NumberFormat.getNumberInstance();
		timeFormat.setMaximumFractionDigits(0);

		NumberFormat degFormat = NumberFormat.getNumberInstance();
		degFormat.setMinimumFractionDigits(4);
		degFormat.setMaximumFractionDigits(4);
		
		NumberFormat radFormat = NumberFormat.getNumberInstance();
		radFormat.setMinimumFractionDigits(6);
		radFormat.setMaximumFractionDigits(6);
		
		NumberFormat distFormat = NumberFormat.getNumberInstance();
		distFormat.setMinimumFractionDigits(4);
		distFormat.setMaximumFractionDigits(4);

		NumberFormat speedFormat = NumberFormat.getNumberInstance();
		speedFormat.setMinimumFractionDigits(4);
		speedFormat.setMaximumFractionDigits(4);

		NumberFormat altFormat = NumberFormat.getNumberInstance();
		altFormat.setMinimumFractionDigits(2);
		altFormat.setMaximumFractionDigits(2);
		
		NumberFormat codeFormat = NumberFormat.getNumberInstance();
		codeFormat.setMaximumFractionDigits(0);
		
		int size = path.size();

		for(int i=0; i<size; i++)
		{
			Point p = (Point)path.elementAt(i);

			for (int j=0; j<opts.size(); j++)
			{
				Boolean item = (Boolean)(opts.get("" + j));

				if(item.booleanValue() == true)
				{
					switch(j)
					{
						case 0:
							stream.println("Index                 = " + i); break;
						case 1:
							stream.println("Track ID              = " + p.getTrackID()); break;
						case 2:
							stream.println("Latitude (rad)        = " + radFormat.format(p.getLatitude())); break;
						case 3:
							stream.println("Latitude (deg)        = " + degFormat.format(p.getLatDeg())); break;
						case 4:
							stream.println("Latitude (code)       = " + codeFormat.format(p.getLatCode())); break;
						case 5:
							stream.println("Longitude (rad)       = " + radFormat.format(p.getLongitude())); break;
						case 6:
							stream.println("Longitude (deg)       = " + degFormat.format(p.getLongDeg())); break;
						case 7:
							stream.println("Longitude (code)      = " + codeFormat.format(p.getLongCode())); break;
						case 8:
							stream.println("Course (rad)          = " + radFormat.format(p.getCourse())); break;
						case 9:
							stream.println("Course (deg)          = " + degFormat.format(p.getCourseDeg())); break;
						case 10:
							stream.println("Course (code)         = " + codeFormat.format(p.getCourseCode())); break;
						case 11:
							stream.println("Offset Distance (rad) = " + radFormat.format(p.getDistance())); break;
						case 12:
							stream.println("Offset Distance (km)  = " + distFormat.format(p.getDistanceKm())); break;
						case 13:
							stream.println("Offset Distance (mi)  = " + distFormat.format(p.getDistanceMiles())); break;
						case 14:
							stream.println("OfFfset Distance (nm) = " + distFormat.format(p.getDistanceNms())); break;
						case 15:
							stream.println("Offset Distance (dm)  = " + distFormat.format(p.getDistanceDms())); break;
						case 16:
							stream.println("Altitude (m)          = " + altFormat.format(p.getAltMeter())); break;
						case 17:
							stream.println("Altitude (ft)         = " + altFormat.format(p.getAltFeet())); break;
						case 18:
							stream.println("Altitude (code)       = " + codeFormat.format(p.getAltCode())); break;
						case 19:
							stream.println("Speed (kph)           = " + speedFormat.format(p.getSpeedKPH())); break;
						case 20:
							stream.println("Speed (mph)           = " + speedFormat.format(p.getSpeedMPH())); break;
						case 21:
							stream.println("Speed (nmph)          = " + speedFormat.format(p.getSpeedNMPH())); break;
						case 22:
							stream.println("Speed (dmph)          = " + speedFormat.format(p.getSpeedDMPH())); break;
						case 23:
							stream.println("Speed (code)          = " + codeFormat.format(p.getSpeedCode())); break;
						case 24:
							stream.println("Offset Time (ms)      = " + timeFormat.format(p.getTimeOffset())); break;
						case 25:
							stream.println("Offset Time (bin ms)  = " + timeFormat.format(p.getTimeOffsetBinaryMSSinceMidnight())); break;
						case 26:
							stream.println("x-Coord               = " + codeFormat.format(p.getX())); break;
						case 27:
							stream.println("y-Coord               = " + codeFormat.format(p.getY())); break;
						case 28:
							stream.println("z-Coord               = " + codeFormat.format(p.getZ())); break;
						default:
							break;
					}
				}
			}

			stream.println();
			if(monitor != null)
			{
				monitor.update(i);
			}
		}
	}

}

strictfp class IndexPath extends FlightPath
{
	public IndexPath(int trkID,
						double oLat, 
						double oLong,
						double dLat,
						double dLong,
						double alt,
						double incr)
	{
		path     = new Vector();

		trackID       = trkID;
		origLat       = Point.fromDegToRadians(oLat);
		origLong      = Point.fromDegToRadians(oLong);
		destLat       = Point.fromDegToRadians(dLat);
		destLong      = Point.fromDegToRadians(dLong);
		altitude      = alt;
		increment     = incr;

		totalDistance = getDistance(origLat, origLong, destLat, destLong);
		course        = getCourse(origLat, origLong, destLat, destLong, totalDistance);
		calculatePath();
	}

	void calculatePath()
	{
		if(increment > 0)
		{
			Point p;
			double A     = 0;
			double B     = 0;
			double x     = 0;
			double y     = 0;
			double z     = 0;
			double iLat  = 0;
			double iLong = 0;
			double iAlt  = 0;
			double iDist = 0;

			for(int i=0; i<=increment; i++)
			{
					A     = Math.sin((1 - (i/increment)) * totalDistance) / Math.sin(totalDistance);
					B     = Math.sin((i/increment) * totalDistance) / Math.sin(totalDistance);
					x     = A * Math.cos(origLat) * Math.cos(origLong) + B * Math.cos(destLat) * Math.cos(destLong);
					y     = A * Math.cos(origLat) * Math.sin(origLong) + B * Math.cos(destLat) * Math.sin(destLong);
					z     = A * Math.sin(origLat)                      + B * Math.sin(destLat);
					iLat  = Math.atan2(z, Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
					iLong = Math.atan2(y, x);
					iAlt  = 0;
					if(i == 0)
					{
						iDist = 0;
					}
					else
					{
						iDist = getDistance(origLat,origLong,iLat,iLong);
					}
						
					p = new Point(trackID, iLat, iLong, course, iDist, altitude);
					path.addElement(p);
			}
		}
	}

	public Vector getPath()
	{
		return path;
	}

	public double getDistance()
	{
		return totalDistance;
	}

	public double getCourse()
	{
		return course;
	}

	public int getTrackID()
	{
		return trackID;
	}

	public HashMap getPrintOptions()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(true));		// Latitude (Rad)
		optMap.put("3",new Boolean(true));		// Latitude (Deg)
		optMap.put("4",new Boolean(true));		// Latitude (code)
		optMap.put("5",new Boolean(true));		// Longitude (Rad)
		optMap.put("6",new Boolean(true));		// Longitude (Deg)
		optMap.put("7",new Boolean(true));		// Longitude (code)
		optMap.put("8",new Boolean(true));		// Course (Rad)
		optMap.put("9",new Boolean(true));		// Course (Deg)
		optMap.put("10",new Boolean(true));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(false));		// Speed (kph)
		optMap.put("20",new Boolean(false));		// Speed (mph)
		optMap.put("21",new Boolean(false));		// Speed (nmph)
		optMap.put("22",new Boolean(false));		// Speed (dmph)
		optMap.put("23",new Boolean(false));		// Speed (code)
		optMap.put("24",new Boolean(false));		// Offset Time (ms) 
		optMap.put("25",new Boolean(false));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(false));		// x-coord
		optMap.put("27",new Boolean(false));		// y-coord
		optMap.put("28",new Boolean(false));		// z-coord
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}
	
	public void printPath(PrintWriter stream, MyProgressMonitor monitor, HashMap opts)
	{
		super.printPath(path,stream,monitor,opts);
	}

	private int trackID;
	private double origLong,
						origLat,
						destLong,
						destLat,
						altitude,
						totalDistance,
						course,
						increment;
	private Point point;
	private Vector path;
}

strictfp class TimePath extends FlightPath
{
	public TimePath(long baseTm,
						int trkID,
						double oLat, 
						double oLong,
						double dLat,
						double dLong,
						double alt,
						double spd,
						long time,
						double obsLat,
						double obsLong,
						double obsHt)
	{
		path    = new Vector();

		baseTime       = baseTm;
		trackID        = trkID;
		origLat        = Point.fromDegToRadians(oLat);
		origLong       = Point.fromDegToRadians(oLong);
		destLat        = Point.fromDegToRadians(dLat);
		destLong       = Point.fromDegToRadians(dLong);
		altitude       = alt;
		speed          = spd;
		timeInterval   = time;
		cecObserverLat = obsLat;
		cecObserverLong = obsLong;
		observerLat    = Point.fromDegToRadians(obsLat);
		observerLong   = Point.fromDegToRadians(obsLong);
		observerHt     = obsHt;

		totalDistance  = getDistance(origLat, origLong, destLat, destLong);
		totalTimeMS    = getTimeMilliSecondsBasedOnKm(totalDistance,speed);
		course         = getCourse(origLat, origLong, destLat, destLong, totalDistance);

		calculateCECPath();
	}
	
	public TimePath(long baseTm,
						int trkID,
						double oLat, 
						double oLong,
						double dLat,
						double dLong,
						double alt,
						double spd,
						long time)
	{
		path    = new Vector();

		baseTime       = baseTm;
		trackID        = trkID;
		origLat        = Point.fromDegToRadians(oLat);
		origLong       = Point.fromDegToRadians(oLong);
		destLat        = Point.fromDegToRadians(dLat);
		destLong       = Point.fromDegToRadians(dLong);
		altitude       = alt;
		speed          = spd;
		timeInterval   = time;

		totalDistance  = getDistance(origLat, origLong, destLat, destLong);
		totalTimeMS    = getTimeMilliSecondsBasedOnKm(totalDistance,speed);
		course         = getCourse(origLat, origLong, destLat, destLong, totalDistance);

		calculateLink16Path();
	}

	void calculatePath(){};
	
	void calculateCECPath()
	{
		if ((timeInterval > 0) && (speed > 0)) 
		{
			long start       = timeInterval;
			double iDistance = 0;
			double iLat      = 0;
			double iLong     = 0;
			long offset      = 0;
			long lastOffset  = 0;

			double obsXCoordECEF = Point.toXCoord(observerLat,observerLong);
			double obsYCoordECEF = Point.toYCoord(observerLat,observerLong);
			double obsZCoordECEF = Point.toZCoord(observerLat);

//			System.out.println("obsXCoordECEF : " + obsXCoordECEF);
//			System.out.println("obsYCoordECEF : " + obsYCoordECEF);
//			System.out.println("obsZCoordECEF : " + obsZCoordECEF);

			// Coords in ECEF
			double iXCoordECEF = 0;
			double iYCoordECEF = 0;
			double iZCoordECEF = 6366.71 * 1000;

			// Coords translated to OCOF
			double iXCoordOCOF = 0;
			double iYCoordOCOF = 0;
			double iZCoordOCOF = 0;
			
			Point p;

			// Get initial course based on orig/dest coordinates and total distance
			// 
			double course = getCourse(origLat, origLong, destLat, destLong, totalDistance);

			for(offset=start; offset<totalTimeMS; offset += timeInterval)
			{
				iDistance = getDistance(offset, speed);

				System.out.println("" + offset + " - " + "iDistance:    " + Point.toKilometers(iDistance));

				iLat      = getDestLat(origLat, course, iDistance);
				iLong     = getDestLong(origLong, origLat, iLat, course, iDistance);

				System.out.println("lat: " + Point.fromRadToDegrees(iLat));
				System.out.println("lg: " + Point.fromRadToDegrees(iLong));


				iXCoordECEF = Point.toXCoord(iLat,iLong);
				iYCoordECEF = Point.toYCoord(iLat,iLong);
				iZCoordECEF = Point.toZCoord(iLat);

				System.out.println("iXCoordECEF : " + iXCoordECEF);
				System.out.println("iYCoordECEF : " + iYCoordECEF);
				System.out.println("iZCoordECEF : " + iZCoordECEF);

				iXCoordOCOF = iXCoordECEF - obsXCoordECEF;
				iYCoordOCOF = iYCoordECEF - obsYCoordECEF;
				iZCoordOCOF = iZCoordECEF - obsZCoordECEF;
				
//				System.out.println("iXCoordOCOF : " + iXCoordOCOF);
//				System.out.println("iYCoordOCOF : " + iYCoordOCOF);
//				System.out.println("iZCoordOCOF : " + iZCoordOCOF);

				System.out.println();
				
				p         = new Point(trackID, 
											iXCoordOCOF, 
											iYCoordOCOF, 
											iZCoordOCOF, 
											iDistance, 
											altitude, 
											offset + baseTime);

				path.addElement(p);
				lastOffset = offset;
				System.out.println("offset:     " + offset);
				System.out.println("lastOffset: " + lastOffset);
			}

			System.out.println("lastOffset: " + lastOffset);

			if(lastOffset < totalTimeMS)
			{
				long diff = (long)totalTimeMS - lastOffset;
				iDistance = iDistance + getDistance(diff, speed);

				System.out.println("diff:      " + diff);
				System.out.println("iDistance: " + Point.toKilometers(iDistance));

				iLat      = getDestLat(origLat, course, iDistance);
				iLong     = getDestLong(origLong, origLat, iLat, course, iDistance);

				System.out.println("lat: " + Point.fromRadToDegrees(iLat));
				System.out.println("lg: " + Point.fromRadToDegrees(iLong));

				
				iXCoordECEF = Point.toXCoord(iLat,iLong);
				iYCoordECEF = Point.toYCoord(iLat,iLong);
				iZCoordECEF = Point.toZCoord(iLat);

				iXCoordOCOF = iXCoordECEF - obsXCoordECEF;
				iYCoordOCOF = iYCoordECEF - obsYCoordECEF;
				iZCoordOCOF = iZCoordECEF - obsZCoordECEF;
				
				p         = new Point(trackID, 
											iXCoordOCOF, 
											iYCoordOCOF, 
											iZCoordOCOF, 
											iDistance, 
											speed, 
											(lastOffset + diff) + baseTime);

				path.addElement(p);
			}
			
		}
	}

	void calculateLink16Path()
	{
		if ((timeInterval > 0) && (speed > 0)) 
		{
			long start       = timeInterval;
			double iDistance = 0;
			double iLat      = 0;
			double iLong     = 0;
			long offset      = 0;
			long lastOffset  = 0;
			Point p;

			// Get initial course based on orig/dest coordinates and total distance
			// 
			double course = getCourse(origLat, origLong, destLat, destLong, totalDistance);

			for(offset=start; offset<totalTimeMS; offset += timeInterval)
			{
				iDistance = getDistance(offset, speed);
				iLat      = getDestLat(origLat, course, iDistance);
				iLong     = getDestLong(origLong, origLat, iLat, course, iDistance);
				p         = new Point(trackID, 
											iLat, 
											iLong, 
											course, 
											iDistance, 
											altitude, 
											speed, 
											offset + baseTime);

				path.addElement(p);
				lastOffset = offset;
			}

			if(lastOffset < totalTimeMS)
			{
				long diff = (long)totalTimeMS - lastOffset;
				iDistance = iDistance + getDistance(diff, speed);
				iLat      = getDestLat(origLat, course, iDistance);
				iLong     = getDestLong(origLong, origLat, iLat, course, iDistance);
				p         = new Point(trackID, iLat, iLong, course, iDistance, altitude, speed, (lastOffset + diff) + baseTime);
				path.addElement(p);
			}
		}
	}

	public Vector getPath()
	{
		return path;
	}

	public double getDistance()
	{
		return totalDistance;
	}

	public double getCourse()
	{
		return course;
	}

	public double getTimeMS()
	{
		return totalTimeMS;
	}

	public int getTrackID()
	{
		return trackID;
	}

	public HashMap getPrintOptions()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(true));		// Latitude (Rad)
		optMap.put("3",new Boolean(true));		// Latitude (Deg)
		optMap.put("4",new Boolean(true));		// Latitude (code)
		optMap.put("5",new Boolean(true));		// Longitude (Rad)
		optMap.put("6",new Boolean(true));		// Longitude (Deg)
		optMap.put("7",new Boolean(true));		// Longitude (code)
		optMap.put("8",new Boolean(true));		// Course (Rad)
		optMap.put("9",new Boolean(true));		// Course (Deg)
		optMap.put("10",new Boolean(true));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(true));		// Speed (kph)
		optMap.put("20",new Boolean(true));		// Speed (mph)
		optMap.put("21",new Boolean(true));		// Speed (nmph)
		optMap.put("22",new Boolean(true));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms) 
		optMap.put("25",new Boolean(false));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(false));		// x-coord (code)
		optMap.put("27",new Boolean(false));		// y-coord (code)
		optMap.put("28",new Boolean(false));		// z-coord (code)
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}

	public HashMap getLink16Options()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(true));		// Latitude (Rad)
		optMap.put("3",new Boolean(true));		// Latitude (Deg)
		optMap.put("4",new Boolean(true));		// Latitude (code)
		optMap.put("5",new Boolean(true));		// Longitude (Rad)
		optMap.put("6",new Boolean(true));		// Longitude (Deg)
		optMap.put("7",new Boolean(true));		// Longitude (code)
		optMap.put("8",new Boolean(true));		// Course (Rad)
		optMap.put("9",new Boolean(true));		// Course (Deg)
		optMap.put("10",new Boolean(true));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(true));		// Speed (kph)
		optMap.put("20",new Boolean(true));		// Speed (mph)
		optMap.put("21",new Boolean(true));		// Speed (nmph)
		optMap.put("22",new Boolean(true));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms) 
		optMap.put("25",new Boolean(true));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(false));		// x-coord
		optMap.put("27",new Boolean(false));		// y-coord
		optMap.put("28",new Boolean(false));		// z-coord
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}

	public HashMap getCECOptions()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(false));		// Latitude (Rad)
		optMap.put("3",new Boolean(false));		// Latitude (Deg)
		optMap.put("4",new Boolean(false));		// Latitude (code)
		optMap.put("5",new Boolean(false));		// Longitude (Rad)
		optMap.put("6",new Boolean(false));		// Longitude (Deg)
		optMap.put("7",new Boolean(false));		// Longitude (code)
		optMap.put("8",new Boolean(false));		// Course (Rad)
		optMap.put("9",new Boolean(false));		// Course (Deg)
		optMap.put("10",new Boolean(false));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(true));		// Speed (kph)
		optMap.put("20",new Boolean(true));		// Speed (mph)
		optMap.put("21",new Boolean(true));		// Speed (nmph)
		optMap.put("22",new Boolean(true));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms) 
		optMap.put("25",new Boolean(false));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(true));		// x-coord
		optMap.put("27",new Boolean(true));		// y-coord
		optMap.put("28",new Boolean(true));		// z-coord
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}

	public void printPath(PrintWriter stream, MyProgressMonitor monitor, HashMap opts)
	{
		super.printPath(path,stream,monitor,opts);
	}

	private int trackID;
	private double origLong,
						origLat,
						destLong,
						destLat,
						observerLat,
						observerLong,
						observerHt,
						altitude,
						speed,
						totalDistance,
						totalTimeMS,
						course,
						cecObserverLat,
						cecObserverLong;
	private long timeInterval,
					baseTime;
	private Point point;
	private Vector path;
}

strictfp class MultiTrackPath extends FlightPath
{
	public MultiTrackPath(int numTrks,
										double offst,
										double oLat, 
										double oLong,
										double dLat,
										double dLong,
										double alt,
										double spd,
										long time,
										double obsLat,
										double obsLong,
										double obsHt)
	{
		path                = new Vector();

		// This number is zero-based, so subtract one.
		trackCount          = numTrks - 1;
		offsetBetweenTracks = offst;
		origLat             = oLat;
		origLong            = oLong;
		destLat             = dLat;
		destLong            = dLong;
		altitude            = alt;
		speed               = spd;
		timeInterval        = time;
		observerLat         = obsLat;
		observerLong        = obsLong;
		observerHt          = obsHt;

		totalDistance       = getDistance(Point.fromDegToRadians(origLat),
														Point.fromDegToRadians(origLong),
														Point.fromDegToRadians(destLat),
														Point.fromDegToRadians(destLong));
		totalTimeMS         = getTimeMilliSecondsBasedOnKm(totalDistance,speed);
		course              = getCourse(Point.fromDegToRadians(origLat), 
													Point.fromDegToRadians(origLong), 
													Point.fromDegToRadians(destLat), 
													Point.fromDegToRadians(destLong),
													totalDistance);

		mergeCECPaths();
		sortPath();
	}

	public MultiTrackPath(int numTrks,
										double offst,
										double oLat, 
										double oLong,
										double dLat,
										double dLong,
										double alt,
										double spd,
										long time)
	{
		path                = new Vector();

		// This number is zero-based, so subtract one.
		trackCount          = numTrks - 1;
		offsetBetweenTracks = offst;
		origLat             = oLat;
		origLong            = oLong;
		destLat             = dLat;
		destLong            = dLong;
		altitude            = alt;
		speed               = spd;
		timeInterval        = time;

		totalDistance       = getDistance(Point.fromDegToRadians(origLat),
														Point.fromDegToRadians(origLong),
														Point.fromDegToRadians(destLat),
														Point.fromDegToRadians(destLong));
		totalTimeMS         = getTimeMilliSecondsBasedOnKm(totalDistance,speed);
		course              = getCourse(Point.fromDegToRadians(origLat), 
													Point.fromDegToRadians(origLong), 
													Point.fromDegToRadians(destLat), 
													Point.fromDegToRadians(destLong),
													totalDistance);

		mergeLink16Paths();
		sortPath();
	}
	
	void calculatePath(){}

	void mergeCECPaths()
	{
		long baseTime = System.currentTimeMillis();

		for(int track = 0; track <= trackCount; track++)
		{
			TimePath p = new TimePath(baseTime,
									track,
									origLat,
									origLong,
									destLat,
									destLong,
									altitude,
									speed,
									timeInterval,
									observerLat,
									observerLong,
									observerHt);

			path.addAll(p.getPath());
			baseTime += offsetBetweenTracks;
		}
	}

	void mergeLink16Paths()
	{
		long baseTime = System.currentTimeMillis();

		for(int track = 0; track <= trackCount; track++)
		{
			TimePath p = new TimePath(baseTime,
									track,
									origLat,
									origLong,
									destLat,
									destLong,
									altitude,
									speed,
									timeInterval);

			path.addAll(p.getPath());
			baseTime += offsetBetweenTracks;
		}
	}
	
	void sortPath()
	{
		Collections.sort
		(path, new Comparator()
			{
				public int compare(Object a, Object b)
				{
					long t1 = (long)((Point)a).getTimeOffset();
					long t2 = (long)((Point)b).getTimeOffset();
					
					if(t1 < t2) return -1;
					if(t1 > t2) return 1;
					return 0;
				}
			}
		);
	}

	Vector getPath()
	{
		return path;
	}

	double getDistance()
	{
		return totalDistance;
	}

	double getTimeMS()
	{
		return totalTimeMS;
	}

	double getCourse()
	{
		return course;
	}
							
	public HashMap getPrintOptions()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(true));		// Latitude (Rad)
		optMap.put("3",new Boolean(true));		// Latitude (Deg)
		optMap.put("4",new Boolean(true));		// Latitude (code)
		optMap.put("5",new Boolean(true));		// Longitude (Rad)
		optMap.put("6",new Boolean(true));		// Longitude (Deg)
		optMap.put("7",new Boolean(true));		// Longitude (code)
		optMap.put("8",new Boolean(true));		// Course (Rad)
		optMap.put("9",new Boolean(true));		// Course (Deg)
		optMap.put("10",new Boolean(true));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(true));		// Speed (kph)
		optMap.put("20",new Boolean(true));		// Speed (mph)
		optMap.put("21",new Boolean(true));		// Speed (nmph)
		optMap.put("22",new Boolean(true));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms) 
		optMap.put("25",new Boolean(false));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(false));		// x-coord (code)
		optMap.put("27",new Boolean(false));		// y-coord (code)
		optMap.put("28",new Boolean(false));		// z-coord (code)
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}
	
	// This is for non-TDIS related calls.
	public HashMap getLink16Options()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(true));		// Latitude (Rad)
		optMap.put("3",new Boolean(true));		// Latitude (Deg)
		optMap.put("4",new Boolean(true));		// Latitude (code)
		optMap.put("5",new Boolean(true));		// Longitude (Rad)
		optMap.put("6",new Boolean(true));		// Longitude (Deg)
		optMap.put("7",new Boolean(true));		// Longitude (code)
		optMap.put("8",new Boolean(true));		// Course (Rad)
		optMap.put("9",new Boolean(true));		// Course (Deg)
		optMap.put("10",new Boolean(true));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(true));		// Speed (kph)
		optMap.put("20",new Boolean(true));		// Speed (mph)
		optMap.put("21",new Boolean(true));		// Speed (nmph)
		optMap.put("22",new Boolean(true));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms) 
		optMap.put("25",new Boolean(true));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(false));		// x-coord (code)
		optMap.put("27",new Boolean(false));		// y-coord (code)
		optMap.put("28",new Boolean(false));		// z-coord (code)
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}

	// This is for TDIS related calls.  Select only those options
	// required by the TDIS ssf.
	public HashMap getTDISLink16Options()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(false));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(false));		// Latitude (Rad)
		optMap.put("3",new Boolean(false));		// Latitude (Deg)
		optMap.put("4",new Boolean(true));		// Latitude (code)
		optMap.put("5",new Boolean(false));		// Longitude (Rad)
		optMap.put("6",new Boolean(false));		// Longitude (Deg)
		optMap.put("7",new Boolean(true));		// Longitude (code)
		optMap.put("8",new Boolean(false));		// Course (Rad)
		optMap.put("9",new Boolean(false));		// Course (Deg)
		optMap.put("10",new Boolean(true));		// Course (code)
		optMap.put("11",new Boolean(false));		// Offset Dist (rad)
		optMap.put("12",new Boolean(false));		// Offset Dist (km)
		optMap.put("13",new Boolean(false));		// Offset Dist (mi)
		optMap.put("14",new Boolean(false));		// Offset Dist (nm)
		optMap.put("15",new Boolean(false));		// Offset Dist (dm)
		optMap.put("16",new Boolean(false));		// Altitude (m)
		optMap.put("17",new Boolean(false));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(false));		// Speed (kph)
		optMap.put("20",new Boolean(false));		// Speed (mph)
		optMap.put("21",new Boolean(false));		// Speed (nmph)
		optMap.put("22",new Boolean(false));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms) 
		optMap.put("25",new Boolean(true));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(false));		// x-coord (code)
		optMap.put("27",new Boolean(false));		// y-coord (code)
		optMap.put("28",new Boolean(false));		// z-coord (code)
		optMap.put("29",new Boolean(false));		// SelectAll 

		return optMap;
	}

	// This is for TDIS non-related calls
	public HashMap getCECOptions()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(true));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(false));		// Latitude (Rad)
		optMap.put("3",new Boolean(false));		// Latitude (Deg)
		optMap.put("4",new Boolean(false));		// Latitude (code)
		optMap.put("5",new Boolean(false));		// Longitude (Rad)
		optMap.put("6",new Boolean(false));		// Longitude (Deg)
		optMap.put("7",new Boolean(false));		// Longitude (code)
		optMap.put("8",new Boolean(false));		// Course (Rad)
		optMap.put("9",new Boolean(false));		// Course (Deg)
		optMap.put("10",new Boolean(false));		// Course (code)
		optMap.put("11",new Boolean(true));		// Offset Dist (rad)
		optMap.put("12",new Boolean(true));		// Offset Dist (km)
		optMap.put("13",new Boolean(true));		// Offset Dist (mi)
		optMap.put("14",new Boolean(true));		// Offset Dist (nm)
		optMap.put("15",new Boolean(true));		// Offset Dist (dm)
		optMap.put("16",new Boolean(true));		// Altitude (m)
		optMap.put("17",new Boolean(true));		// Altitude (ft)
		optMap.put("18",new Boolean(true));		// Altitude (code)
		optMap.put("19",new Boolean(true));		// Speed (kph)
		optMap.put("20",new Boolean(true));		// Speed (mph)
		optMap.put("21",new Boolean(true));		// Speed (nmph)
		optMap.put("22",new Boolean(true));		// Speed (dmph)
		optMap.put("23",new Boolean(true));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms)
		optMap.put("25",new Boolean(false));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(true));		// x-coord (code)
		optMap.put("27",new Boolean(true));		// y-coord (code)
		optMap.put("28",new Boolean(true));		// z-coord (code)
		optMap.put("29",new Boolean(true));		// SelectAll 

		return optMap;
	}
	
	// This is for TDIS related calls.  Select only those options
	// required by the TDIS ssf.
	public HashMap getTDISCECOptions()
	{
		HashMap optMap = new HashMap();

		optMap.put("0",new Boolean(false));		// Index
		optMap.put("1",new Boolean(true));		// Track
		optMap.put("2",new Boolean(false));		// Latitude (Rad)
		optMap.put("3",new Boolean(false));		// Latitude (Deg)
		optMap.put("4",new Boolean(false));		// Latitude (code)
		optMap.put("5",new Boolean(false));		// Longitude (Rad)
		optMap.put("6",new Boolean(false));		// Longitude (Deg)
		optMap.put("7",new Boolean(false));		// Longitude (code)
		optMap.put("8",new Boolean(false));		// Course (Rad)
		optMap.put("9",new Boolean(false));		// Course (Deg)
		optMap.put("10",new Boolean(false));		// Course (code)
		optMap.put("11",new Boolean(false));		// Offset Dist (rad)
		optMap.put("12",new Boolean(false));		// Offset Dist (km)
		optMap.put("13",new Boolean(false));		// Offset Dist (mi)
		optMap.put("14",new Boolean(false));		// Offset Dist (nm)
		optMap.put("15",new Boolean(false));		// Offset Dist (dm)
		optMap.put("16",new Boolean(false));		// Altitude (m)
		optMap.put("17",new Boolean(false));		// Altitude (ft)
		optMap.put("18",new Boolean(false));		// Altitude (code)
		optMap.put("19",new Boolean(false));		// Speed (kph)
		optMap.put("20",new Boolean(false));		// Speed (mph)
		optMap.put("21",new Boolean(false));		// Speed (nmph)
		optMap.put("22",new Boolean(false));		// Speed (dmph)
		optMap.put("23",new Boolean(false));		// Speed (code)
		optMap.put("24",new Boolean(true));		// Offset Time (ms)
		optMap.put("25",new Boolean(false));		// Offset Time (bin ms) 
		optMap.put("26",new Boolean(true));		// x-coord (code)
		optMap.put("27",new Boolean(true));		// y-coord (code)
		optMap.put("28",new Boolean(true));		// z-coord (code)
		optMap.put("29",new Boolean(false));		// SelectAll 

		return optMap;
	}
	
	public void printPath(PrintWriter stream, MyProgressMonitor monitor, HashMap opts)
	{
		super.printPath(path,stream,monitor,opts);
	}

	private Vector path;
	private double trackCount;
	private double offsetBetweenTracks;
	private double origLat;
	private double origLong;
	private double destLat;
	private double destLong;
	private double observerLat;
	private double observerLong;
	private double observerHt;
	private double altitude;
	private double speed;
	private long timeInterval;
	private double totalDistance;
	private double totalTimeMS;
	private double course;
}

class FlightPathGui extends JFrame
{
	public FlightPathGui()
	{
		setTitle("Flight Path Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setOpaque(true);
		panel.setLayout(new GridBagLayout());
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		add(panel,new JLabel("Latitude"),2,1,1,1,"C");
		add(panel,new JLabel("Longitude"),3,1,1,1,"C");

		NumberFormat degFormat = NumberFormat.getNumberInstance();
		degFormat.setMaximumIntegerDigits(3);
		degFormat.setMinimumFractionDigits(8);
		degFormat.setMaximumFractionDigits(8);
		
		origLatTF  = new GenericTextField(degFormat,10,new Double(-90.00000000),new Double(89.99999999));
		origLongTF = new GenericTextField(degFormat,10,new Double(-180.00000000),new Double(179.99999999));
		destLatTF  = new GenericTextField(degFormat,10,new Double(-90.00000000),new Double(89.99999999));
		destLongTF = new GenericTextField(degFormat,10,new Double(-180.00000000),new Double(179.99999999));

		origLatTF.setToolTipText("-89.99999999 to 89.99999999");
		origLongTF.setToolTipText("-179.99999999 to 179.99999999");
		destLatTF.setToolTipText("-89.99999999 to 89.99999999");
		destLongTF.setToolTipText("-179.99999999 to 179.99999999");

		origLatTF.setValue(new Double(0.00000000));
		origLongTF.setValue(new Double(0.00000000));
		destLatTF.setValue(new Double(0.00000000));
		destLongTF.setValue(new Double(0.00000000));

		add(panel,new JLabel("Origin"),1,2,1,1,"E");
		add(panel,origLatTF,2,2,1,1,"E");
		add(panel,origLongTF,3,2,1,1,"E");
	
		add(panel,new JLabel("Destination"),1,3,1,1,"E");
		add(panel,destLatTF,2,3,1,1,"E");
		add(panel,destLongTF,3,3,1,1,"E");

		add(panel,Box.createRigidArea(new Dimension(20,20)),4,2,1,2,"C");
		reverseBtn = new JButton("Reverse Direction");
		add(panel,reverseBtn,5,2,1,2,"C");

		reverseBtn.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String oLat  = origLatTF.getValue().toString();
					String oLong = origLongTF.getValue().toString();
					String dLat  = destLatTF.getValue().toString();
					String dLong = destLongTF.getValue().toString();

					origLatTF.setValue(new Double(dLat));
					origLongTF.setValue(new Double(dLong));
					destLatTF.setValue(new Double(oLat));
					destLongTF.setValue(new Double(oLong));
				}
			}
		);

		JLabel warning = new JLabel("Prefix South Latitude and West Longitude with a '-'");
		warning.setFont(new Font("Courier", Font.PLAIN, 12));
		warning.setForeground(new Color(0xff0000));
		warningPanel = new JPanel();
		warningPanel.setOpaque(true);
		warningPanel.add(warning);

		topPanel = new JPanel();
		topPanel.setOpaque(true);
		topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
		topPanel.setBorder(border);
		topPanel.add(panel);
		topPanel.add(warningPanel);

		NumberFormat intFormat = NumberFormat.getNumberInstance();
		intFormat.setMaximumIntegerDigits(10);
		intFormat.setMinimumFractionDigits(0);
		intFormat.setMaximumFractionDigits(0);

		middleLeftPanel = new JPanel(new GridBagLayout());
		middleLeftPanel.setOpaque(true);
		middleLeftPanel.setBorder(border);

		altitudeTF = new GenericTextField(intFormat,6,new Integer(-1),new Integer(62415));
		altitudeTF.setToolTipText("0 to 62415");
		altitudeTF.setValue(new Integer(0));
		add(middleLeftPanel,new JLabel("Altitude (m)"),1,1,1,1,"E");
		add(middleLeftPanel,altitudeTF,2,1,1,1,"W");
		
		speedTF = new GenericTextField(intFormat,8,new Integer(-1),new Integer(7488));
		speedTF.setToolTipText("0 to 7488");
		speedTF.setValue(new Integer(0));
		add(middleLeftPanel,new JLabel("Speed (kph)"),3,1,1,1,"E");
		add(middleLeftPanel,speedTF,4,1,1,1,"W");

		intermediatePtTF = new GenericTextField(intFormat,6,new Integer(-1), new Integer(100000));
		intermediatePtTF.setToolTipText("0 to 100000");
		intermediatePtTF.setValue(new Integer(0));
		add(middleLeftPanel,new JLabel("Index Points"),1,2,1,1,"E");
		add(middleLeftPanel,intermediatePtTF,2,2,1,1,"W");

		timeIntervalTF = new GenericTextField(intFormat,8,new Integer(-1), new Integer(1000000000));
		timeIntervalTF.setToolTipText(" >= 0 ");
		timeIntervalTF.setValue(new Integer(0));
		add(middleLeftPanel,new JLabel("Time Interval (ms)"),3,2,1,1,"E");
		add(middleLeftPanel,timeIntervalTF,4,2,1,1,"W");

		add(middleLeftPanel,new JLabel("Display Type: "),1,6,1,1,"E");

		indexBasedRadioBtn = new JRadioButton("Index");
		indexBasedRadioBtn.setSelected(true);

		timeBasedRadioBtn = new JRadioButton("Time");
		timeBasedRadioBtn.setSelected(false);

		ButtonGroup group = new ButtonGroup();
		group.add(indexBasedRadioBtn);
		group.add(timeBasedRadioBtn);

		add(middleLeftPanel,indexBasedRadioBtn,2,6,1,1,"E");
		add(middleLeftPanel,timeBasedRadioBtn,3,6,1,1,"E");

		middleRightPanel = new JPanel(new GridBagLayout());
		middleRightPanel.setOpaque(true);
		middleRightPanel.setBorder(border);

		singleTrackBtn = new JRadioButton("Single");
		singleTrackBtn.setSelected(true);
				
		multiTrackBtn = new JRadioButton("Multiple");
		multiTrackBtn.setSelected(false);

		ButtonGroup trackGrp = new ButtonGroup();
		trackGrp.add(singleTrackBtn);
		trackGrp.add(multiTrackBtn);

		multiTrackTF = new GenericTextField(intFormat,6,new Integer(-1),new Integer(10000));
		multiTrackTF.setToolTipText(" >= 0 ");
		multiTrackTF.setValue(new Integer(0));
		multiTrackTF.setEnabled(false);
		offsetBetweenTracksTF = new GenericTextField(intFormat,6,new Integer(-1),new Integer(100000));
		offsetBetweenTracksTF.setToolTipText(" >= 0 ");
		offsetBetweenTracksTF.setValue(new Integer(0));
		offsetBetweenTracksTF.setEnabled(false);
		
		singleTrackBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					indexBasedRadioBtn.setEnabled(true);
					timeBasedRadioBtn.setEnabled(true);
					multiTrackTF.setEnabled(false);
					offsetBetweenTracksTF.setEnabled(false);
				}
			}
		);

		multiTrackBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					indexBasedRadioBtn.setEnabled(false);
					timeBasedRadioBtn.setEnabled(false);
					multiTrackTF.setEnabled(true);
					offsetBetweenTracksTF.setEnabled(true);
				}
			}
		);

		add(middleRightPanel,new JLabel("Tracks"),1,1,2,1,"C");
		add(middleRightPanel,singleTrackBtn,1,2,1,1,"W");
		add(middleRightPanel,multiTrackBtn,1,3,1,1,"W");
		add(middleRightPanel,multiTrackTF,2,3,1,1,"W");
		add(middleRightPanel,new JLabel("Offset (ms): "),1,4,1,1,"E");
		add(middleRightPanel,offsetBetweenTracksTF,2,4,1,1,"W");

		middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.X_AXIS));
		middlePanel.add(middleLeftPanel);
		middlePanel.add(middleRightPanel);

		NumberFormat radiansFormat = NumberFormat.getNumberInstance();
		radiansFormat.setMaximumFractionDigits(6);
		
		NumberFormat distanceTimeFormat = NumberFormat.getNumberInstance();
		distanceTimeFormat.setMaximumFractionDigits(2);

		radiansTF       = new GenericTextField(radiansFormat,10);
		kilometersTF    = new GenericTextField(distanceTimeFormat,10);
		milesTF         = new GenericTextField(distanceTimeFormat,10);
		nauticalMilesTF = new GenericTextField(distanceTimeFormat,10);
		dataMilesTF     = new GenericTextField(distanceTimeFormat,10);

		millisecondsTF  = new GenericTextField(distanceTimeFormat,10);
		secondsTF       = new GenericTextField(distanceTimeFormat,10);
		minutesTF       = new GenericTextField(distanceTimeFormat,10);
		hoursTF         = new GenericTextField(distanceTimeFormat,10);

		headingRadTF    = new GenericTextField(radiansFormat,10);
		headingDegTF    = new GenericTextField(degFormat,10);
		
		distanceTimePanel = new JPanel(new GridBagLayout());
		distanceTimePanel.setOpaque(true);
		distanceTimePanel.setBorder(border);

		add(distanceTimePanel,new JLabel("Distance"),1,1,2,1,"C");

		add(distanceTimePanel,new JLabel("Radians"),1,2,1,1,"E");
		add(distanceTimePanel,radiansTF,2,2,1,1,"E");

		add(distanceTimePanel,new JLabel("Kilometers"),1,3,1,1,"E");
		add(distanceTimePanel,kilometersTF,2,3,1,1,"E");

		add(distanceTimePanel,new JLabel("Miles"),1,4,1,1,"E");
		add(distanceTimePanel,milesTF,2,4,1,1,"E");

		add(distanceTimePanel,new JLabel("Nautical Miles"),1,5,1,1,"E");
		add(distanceTimePanel,nauticalMilesTF,2,5,1,1,"E");

		add(distanceTimePanel,new JLabel("Data Miles"),1,6,1,1,"E");
		add(distanceTimePanel,dataMilesTF,2,6,1,1,"E");

		add(distanceTimePanel,new JLabel("Travel Time"),3,1,2,1,"C");
		
		add(distanceTimePanel,new JLabel("Milliseconds"),3,2,1,1,"E");
		add(distanceTimePanel,millisecondsTF,4,2,1,1,"E");

		add(distanceTimePanel,new JLabel("Seconds"),3,3,1,1,"E");
		add(distanceTimePanel,secondsTF,4,3,1,1,"E");

		add(distanceTimePanel,new JLabel("Minutes"),3,4,1,1,"E");
		add(distanceTimePanel,minutesTF,4,4,1,1,"E");

		add(distanceTimePanel,new JLabel("Hours"),3,5,1,1,"E");
		add(distanceTimePanel,hoursTF,4,5,1,1,"E");

		add(distanceTimePanel,new JLabel("Heading"),5,1,2,1,"C");

		add(distanceTimePanel,new JLabel("Radians"),5,2,1,1,"E");
		add(distanceTimePanel,headingRadTF,6,2,1,1,"E");

		add(distanceTimePanel,new JLabel("Degrees"),5,3,1,1,"E");
		add(distanceTimePanel,headingDegTF,6,3,1,1,"E");

		easyFmtRadioBtn   = new JRadioButton("Friendly");
		easyFmtRadioBtn.setSelected(true);
		link16FmtRadioBtn = new JRadioButton("link16");
		cecFmtRadioBtn    = new JRadioButton("cec");

		observerLatTF  = new GenericTextField(degFormat,10,new Double(-90.00000000),new Double(89.99999999));
		observerLatTF.setEnabled(false);
		observerLatTF.setValue(new Double(0.00000000));
		observerLatTF.setToolTipText("-89.99999999 to 89.99999999");
		observerLongTF = new GenericTextField(degFormat,10,new Double(-180.00000000),new Double(179.99999999));
		observerLongTF.setEnabled(false);
		observerLongTF.setValue(new Double(0.00000000));
		observerLongTF.setToolTipText("-179.99999999 to 179.99999999");
		observerHtTF   = new GenericTextField(degFormat,6,new Double(-1),new Double(62415));
		observerHtTF.setEnabled(false);
		observerHtTF.setValue(new Double(0));
		observerHtTF.setToolTipText("0 to 62415");

		ButtonGroup fmtGroup = new ButtonGroup();
		fmtGroup.add(easyFmtRadioBtn);
		fmtGroup.add(link16FmtRadioBtn);
		fmtGroup.add(cecFmtRadioBtn);
		
		tableFmtPanel = new JPanel(new GridBagLayout());
		tableFmtPanel.setOpaque(true);
		add(tableFmtPanel,easyFmtRadioBtn,1,1,1,1,"W");
		add(tableFmtPanel,link16FmtRadioBtn,2,1,1,1,"W");
		add(tableFmtPanel,cecFmtRadioBtn,3,1,1,1,"W");
		add(tableFmtPanel,new JLabel("Observer Lat: "), 4,1,1,1,"E");
		add(tableFmtPanel,observerLatTF,5,1,1,1,"W");
		add(tableFmtPanel,new JLabel("Long: "),6,1,1,1,"E");
		add(tableFmtPanel,observerLongTF,7,1,1,1,"W");
		add(tableFmtPanel,new JLabel("Alt: "),8,1,1,1,"E");
		add(tableFmtPanel,observerHtTF,9,1,1,1,"W");

		easyFmtRadioBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					observerLatTF.setValue(new Double(0.0000));
					observerLongTF.setValue(new Double(0.0000));
					observerHtTF.setValue(new Double(0.0000));
					observerLatTF.setEnabled(false);
					observerLongTF.setEnabled(false);
					observerHtTF.setEnabled(false);
				}
			}
		);
		
		link16FmtRadioBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					observerLatTF.setValue(new Double(0.0000));
					observerLongTF.setValue(new Double(0.0000));
					observerHtTF.setValue(new Double(0.0000));
					observerLatTF.setEnabled(false);
					observerLongTF.setEnabled(false);
					observerHtTF.setEnabled(false);
				}
			}
		);

		cecFmtRadioBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					observerLatTF.setValue(new Double(0.0000));
					observerLongTF.setValue(new Double(0.0000));
					observerHtTF.setValue(new Double(0.0000));
					observerLatTF.setEnabled(true);
					observerLongTF.setEnabled(true);
					observerHtTF.setEnabled(true);
				}
			}
		);



		calculateBtn  = new JButton("Calculate");
		exitBtn       = new JButton("Exit");
		printBtn      = new JButton("Print");
		sendToTDISBtn = new JButton("Send to TDIS");

		bottomPanel = new JPanel();
		bottomPanel.setOpaque(true);
		bottomPanel.setBorder(border);
		bottomPanel.add(calculateBtn);
		bottomPanel.add(printBtn);
		bottomPanel.add(sendToTDISBtn);
		bottomPanel.add(exitBtn);

		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		JScrollPane scrollPane = new JScrollPane(table,
									JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
									JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setPreferredScrollableViewportSize(new Dimension(300,300));
		table.setDefaultRenderer(Double.class,new DoubleRenderer());
		table.setDefaultRenderer(Distance.class,new DistanceRenderer());

		mainPanel = new JPanel();
		mainPanel.setOpaque(true);
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		mainPanel.add(middlePanel);
		mainPanel.add(distanceTimePanel);
		mainPanel.add(tableFmtPanel);
		mainPanel.add(scrollPane);
		mainPanel.add(bottomPanel);

		getContentPane().add(mainPanel);
		pack();

		// Position GUI on Monitor
		//
		int heightOfFrame = getContentPane().getHeight();
		int widthOfFrame  = getContentPane().getWidth();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        int y_loc = (screen.height/4) - (heightOfFrame/4);
        int x_loc = (screen.width/4) - (widthOfFrame/4);
        setLocation(x_loc,y_loc);
		
		calculateBtn.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Retrieve entered values
					Double olat  = new Double(origLatTF.getValue().toString());
					Double olong = new Double(origLongTF.getValue().toString());
					Double dlat  = new Double(destLatTF.getValue().toString());
					Double dlong = new Double(destLongTF.getValue().toString());
			  		Double alt   = new Double(altitudeTF.getValue().toString());
			  		Double spd   = new Double(speedTF.getValue().toString());
					Double incre = new Double(intermediatePtTF.getValue().toString());
					Double time  = new Double(timeIntervalTF.getValue().toString());

					// Make sure that the coords are valid.
					if(!antipodal(olat,olong,dlat,dlong) && !sameCoordinates(olat,olong,dlat,dlong))
					{

						// For single tracks, the possible calculations are index-based and time-based.
						if(singleTrackBtn.isSelected())
						{
							if(indexBasedRadioBtn.isSelected() && easyFmtRadioBtn.isSelected())
							{
									
								indexPath = new IndexPath( 0,
															olat.doubleValue(), 
															olong.doubleValue(), 
															dlat.doubleValue(), 
															dlong.doubleValue(), 
															alt.doubleValue(), 
															incre.doubleValue());

								radiansTF.setValue(new Double(indexPath.getDistance()));
								kilometersTF.setValue(new Double(Point.toKilometers(indexPath.getDistance())));
								milesTF.setValue(new Double(Point.toMiles(indexPath.getDistance())));
								nauticalMilesTF.setValue(new Double(Point.toNauticalMiles(indexPath.getDistance())));
								dataMilesTF.setValue(new Double(Point.toDataMiles(indexPath.getDistance())));
								headingRadTF.setValue(new Double(indexPath.getCourse()));
								headingDegTF.setValue(new Double(Point.fromRadToDegrees(indexPath.getCourse())));
								millisecondsTF.setValue(new Double(0));
								secondsTF.setValue(new Double(0));
								minutesTF.setValue(new Double(0));
								hoursTF.setValue(new Double(0));

								IndexTableModel indexModel = new IndexTableModel(indexPath.getPath());
								table.setVisible(false);
								table.setModel(indexModel);
								table.setVisible(true);
							}
							// time-based calculations can be either in link16 or cec format.
							else if(timeBasedRadioBtn.isSelected())
							{
								if(easyFmtRadioBtn.isSelected() || link16FmtRadioBtn.isSelected())
								{
									timePath = new TimePath(System.currentTimeMillis(),
																	0,
																	olat.doubleValue(), 
																	olong.doubleValue(), 
																	dlat.doubleValue(), 
																	dlong.doubleValue(), 
																	alt.doubleValue(), 
																	spd.doubleValue(),
																	time.longValue());

									if(easyFmtRadioBtn.isSelected())
									{
										TimeTableModel timeModel = new TimeTableModel(timePath.getPath());
										table.setVisible(false);
										table.setModel(timeModel);
										table.getColumnModel().getColumn(2).setPreferredWidth(150);
										table.setVisible(true);
									}
									else if(link16FmtRadioBtn.isSelected())
									{
										Link16TableModel link16Model = new Link16TableModel(timePath.getPath());
										table.setVisible(false);
										table.setModel(link16Model);
										table.getColumnModel().getColumn(2).setPreferredWidth(150);
										table.getColumnModel().getColumn(3).setPreferredWidth(125);
										table.setVisible(true);
									}
								}
								else if(cecFmtRadioBtn.isSelected())
								{
									Double obsLat    = new Double(observerLatTF.getValue().toString());
									Double obsLong   = new Double(observerLongTF.getValue().toString());
									Double obsHeight = new Double(observerHtTF.getValue().toString());

									timePath = new TimePath(System.currentTimeMillis(),
																	0,
																	olat.doubleValue(), 
																	olong.doubleValue(), 
																	dlat.doubleValue(), 
																	dlong.doubleValue(), 
																	alt.doubleValue(), 
																	spd.doubleValue(),
																	time.longValue(),
																	obsLat.doubleValue(),
																	obsLong.doubleValue(),
																	obsHeight.doubleValue());

									CECTableModel cecModel = new CECTableModel(timePath.getPath());
									table.setVisible(false);
									table.setModel(cecModel);
									table.getColumnModel().getColumn(2).setPreferredWidth(150);
									table.getColumnModel().getColumn(3).setPreferredWidth(125);
									table.getColumnModel().getColumn(4).setPreferredWidth(125);
									table.getColumnModel().getColumn(5).setPreferredWidth(125);
									table.setVisible(true);
								}

								// Populate common fields
								radiansTF.setValue(new Double(timePath.getDistance()));
								kilometersTF.setValue(new Double(Point.toKilometers(timePath.getDistance())));
								milesTF.setValue(new Double(Point.toMiles(timePath.getDistance())));
								nauticalMilesTF.setValue(new Double(Point.toNauticalMiles(timePath.getDistance())));
								dataMilesTF.setValue(new Double(Point.toDataMiles(timePath.getDistance())));
								headingRadTF.setValue(new Double(timePath.getCourse()));
								millisecondsTF.setValue(new Double(timePath.getTimeMS()));
								secondsTF.setValue(new Double(Point.toSeconds(timePath.getTimeMS())));
								minutesTF.setValue(new Double(Point.toMinutes(timePath.getTimeMS())));
								hoursTF.setValue(new Double(Point.toHours(timePath.getTimeMS())));
								headingDegTF.setValue(new Double(Point.fromRadToDegrees(timePath.getCourse())));
							}
							else
							{
								indexPath = new IndexPath( 0,
															olat.doubleValue(), 
															olong.doubleValue(), 
															dlat.doubleValue(), 
															dlong.doubleValue(), 
															alt.doubleValue(), 
															0);
								
								IndexTableModel indexModel = new IndexTableModel(indexPath.getPath());
								table.setVisible(false);
								table.setModel(indexModel);
								table.setVisible(true);
							}
								
						}
						else if(multiTrackBtn.isSelected())
						{
							Integer trackCount = new Integer(multiTrackTF.getValue().toString());
							Integer offset     = new Integer(offsetBetweenTracksTF.getValue().toString());
							
							if(easyFmtRadioBtn.isSelected() || link16FmtRadioBtn.isSelected())
							{
								multiTrackPath = new MultiTrackPath(trackCount.intValue(),
																	offset.doubleValue(),
																	olat.doubleValue(), 
																	olong.doubleValue(), 
																	dlat.doubleValue(), 
																	dlong.doubleValue(), 
																	alt.doubleValue(), 
																	spd.doubleValue(),
																	time.longValue());

								if(easyFmtRadioBtn.isSelected())
								{
										TimeTableModel timeModel = new TimeTableModel(multiTrackPath.getPath());
										table.setVisible(false);
										table.setModel(timeModel);
										table.getColumnModel().getColumn(2).setPreferredWidth(150);
										table.setVisible(true);
								}
								else if(link16FmtRadioBtn.isSelected())
								{
										Link16TableModel link16Model = new Link16TableModel(multiTrackPath.getPath());
										table.setVisible(false);
										table.setModel(link16Model);
										table.getColumnModel().getColumn(2).setPreferredWidth(150);
										table.getColumnModel().getColumn(3).setPreferredWidth(125);
										table.setVisible(true);
								}
							}
							else if(cecFmtRadioBtn.isSelected())
							{
									Double obsLat    = new Double(observerLatTF.getValue().toString());
									Double obsLong   = new Double(observerLongTF.getValue().toString());
									Double obsHeight = new Double(observerHtTF.getValue().toString());

									multiTrackPath = new MultiTrackPath(trackCount.intValue(),
																			offset.doubleValue(),
																			olat.doubleValue(), 
																			olong.doubleValue(), 
																			dlat.doubleValue(), 
																			dlong.doubleValue(), 
																			alt.doubleValue(), 
																			spd.doubleValue(),
																			time.longValue(),
																			obsLat.doubleValue(),
																			obsLong.doubleValue(),
																			obsHeight.doubleValue());

									CECTableModel cecModel = new CECTableModel(multiTrackPath.getPath());
									table.setVisible(false);
									table.setModel(cecModel);
									table.getColumnModel().getColumn(2).setPreferredWidth(150);
									table.getColumnModel().getColumn(3).setPreferredWidth(125);
									table.getColumnModel().getColumn(4).setPreferredWidth(125);
									table.getColumnModel().getColumn(5).setPreferredWidth(125);
									table.setVisible(true);
							}

							radiansTF.setValue(new Double(multiTrackPath.getDistance()));
							kilometersTF.setValue(new Double(Point.toKilometers(multiTrackPath.getDistance())));
							milesTF.setValue(new Double(Point.toMiles(multiTrackPath.getDistance())));
							nauticalMilesTF.setValue(new Double(Point.toNauticalMiles(multiTrackPath.getDistance())));
							dataMilesTF.setValue(new Double(Point.toDataMiles(multiTrackPath.getDistance())));
							headingRadTF.setValue(new Double(multiTrackPath.getCourse()));
							millisecondsTF.setValue(new Double(multiTrackPath.getTimeMS()));
							secondsTF.setValue(new Double(Point.toSeconds(multiTrackPath.getTimeMS())));
							minutesTF.setValue(new Double(Point.toMinutes(multiTrackPath.getTimeMS())));
							hoursTF.setValue(new Double(Point.toHours(multiTrackPath.getTimeMS())));
							headingDegTF.setValue(new Double(Point.fromRadToDegrees(multiTrackPath.getCourse())));

						}
					}
					else
					{
								JOptionPane.showMessageDialog(null, 
															"Please make sure that\n\n" +
															"(1) Latitude_1 + Latitude_2 != 0 and \n" +
															"    abs(Longitude_1 - Longitude_2) != PI.\n\n" +
															"(2) Latitude_1 != Latitude_2 and \n" +
															"    Longitude_1 != Longitude_2",
															"Stop!", 
															JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);

		exitBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			}
		);

		printBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(singleTrackBtn.isSelected())
					{
						if(indexBasedRadioBtn.isSelected() && indexPath != null && !indexPath.getPath().isEmpty())
						{
							printIt = new MyPrint(indexPath.getPrintOptions(),indexPath);
						}
						else if(timeBasedRadioBtn.isSelected() && timePath != null && !timePath.getPath().isEmpty())
						{
							if(easyFmtRadioBtn.isSelected())
							{
								printIt = new MyPrint(timePath.getPrintOptions(),timePath);
							}
							else if(link16FmtRadioBtn.isSelected())
							{
								printIt = new MyPrint(timePath.getLink16Options(),timePath);
							}
							else if(cecFmtRadioBtn.isSelected())
							{
								printIt = new MyPrint(timePath.getCECOptions(),timePath);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, 
																	"Please calculate a flight path first.", 
																	"Stop!", 
																	JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						if(multiTrackPath != null && !multiTrackPath.getPath().isEmpty())
						{
							if(link16FmtRadioBtn.isSelected())
							{
								printIt = new MyPrint(multiTrackPath.getLink16Options(),multiTrackPath);
							}
							else if(cecFmtRadioBtn.isSelected())
							{
								printIt = new MyPrint(multiTrackPath.getCECOptions(),multiTrackPath);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, 
																	"Please calculate a flight path first.", 
																	"Stop!", 
																	JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		);

		sendToTDISBtn.addActionListener
		(
		 	new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Double olat        = new Double(origLatTF.getValue().toString());
					Double olong       = new Double(origLongTF.getValue().toString());
					Double dlat        = new Double(destLatTF.getValue().toString());
					Double dlong       = new Double(destLongTF.getValue().toString());
					Double alt         = new Double(altitudeTF.getValue().toString());
					Double spd         = new Double(speedTF.getValue().toString());
					Double incre       = new Double(intermediatePtTF.getValue().toString());
					Double time        = new Double(timeIntervalTF.getValue().toString());
					Integer trackCount = new Integer(multiTrackTF.getValue().toString());
					Integer offset     = new Integer(offsetBetweenTracksTF.getValue().toString());
						
					if(!antipodal(olat,olong,dlat,dlong) && !sameCoordinates(olat,olong,dlat,dlong))
					{
						if(multiTrackBtn.isSelected())
						{
							Object[] possibleValues = {"Link16", "CEC"};

							String selectedValue = (String)(JOptionPane.showInputDialog(null, 
																					"Select the type of message to create. ", 
																					"Message Type",
																					JOptionPane.INFORMATION_MESSAGE, 
																					null,
																					possibleValues, 
																					possibleValues[0]));

							if(selectedValue != null)
							{
								String fileName = JOptionPane.showInputDialog("Please enter a filename for the results. ");

								if(fileName != null && !fileName.trim().equals(""))
								{
									try
									{
											PrintWriter stream = new PrintWriter(new FileWriter(new File(fileName)));
		
											if(selectedValue.equalsIgnoreCase("link16"))
											{
												tdisPath = new MultiTrackPath(trackCount.intValue(),
																								offset.doubleValue(),
																								olat.doubleValue(), 
																								olong.doubleValue(), 
																								dlat.doubleValue(), 
																								dlong.doubleValue(), 
																								alt.doubleValue(), 
																								spd.doubleValue(),
																								time.longValue());

												tdisPath.printPath(stream,null,tdisPath.getTDISLink16Options());
											}
											else if (selectedValue.equalsIgnoreCase("cec"))
											{
												Double obsLat    = new Double(observerLatTF.getValue().toString());
												Double obsLong   = new Double(observerLongTF.getValue().toString());
												Double obsHeight = new Double(observerHtTF.getValue().toString());

												tdisPath = new MultiTrackPath(trackCount.intValue(),
																								offset.doubleValue(),
																								olat.doubleValue(), 
																								olong.doubleValue(), 
																								dlat.doubleValue(), 
																								dlong.doubleValue(), 
																								alt.doubleValue(), 
																								spd.doubleValue(),
																								time.longValue(),
																								obsLat.doubleValue(),
																								obsLong.doubleValue(),
																								obsHeight.doubleValue());

												tdisPath.printPath(stream,null,tdisPath.getTDISCECOptions());
											}

											stream.close();

											//Call to Create_ICE_Tool
											createFlightPath.runExternalProcess("gvim","Invoking GVIM ... ");
											
											//Call to Command-line TDIS
											createFlightPath.runExternalProcess("gvim","Invoking GVIM ... ");
								
											// Populate common fields
											radiansTF.setValue(new Double(tdisPath.getDistance()));
											kilometersTF.setValue(new Double(Point.toKilometers(tdisPath.getDistance())));
											milesTF.setValue(new Double(Point.toMiles(tdisPath.getDistance())));
											nauticalMilesTF.setValue(new Double(Point.toNauticalMiles(tdisPath.getDistance())));
											dataMilesTF.setValue(new Double(Point.toDataMiles(tdisPath.getDistance())));
											headingRadTF.setValue(new Double(tdisPath.getCourse()));
											millisecondsTF.setValue(new Double(tdisPath.getTimeMS()));
											secondsTF.setValue(new Double(Point.toSeconds(tdisPath.getTimeMS())));
											minutesTF.setValue(new Double(Point.toMinutes(tdisPath.getTimeMS())));
											hoursTF.setValue(new Double(Point.toHours(tdisPath.getTimeMS())));
											headingDegTF.setValue(new Double(Point.fromRadToDegrees(tdisPath.getCourse())));
							
											// Populate table
											if(selectedValue.equalsIgnoreCase("link16"))
											{
												Link16TableModel multiTrackModel = new Link16TableModel(tdisPath.getPath());
												table.setVisible(false);
												table.setModel(multiTrackModel);
												table.getColumnModel().getColumn(2).setPreferredWidth(150);
												table.getColumnModel().getColumn(3).setPreferredWidth(125);
												table.setVisible(true);
											
												JOptionPane.showMessageDialog(null, 
																					"TDIS call completed.", 
																					"Attention!", 
																					JOptionPane.INFORMATION_MESSAGE);
											}
											else if(selectedValue.equalsIgnoreCase("cec"))
											{
												CECTableModel multiTrackModel = new CECTableModel(tdisPath.getPath());
												table.setVisible(false);
												table.setModel(multiTrackModel);
												table.getColumnModel().getColumn(2).setPreferredWidth(150);
												table.getColumnModel().getColumn(3).setPreferredWidth(125);
												table.setVisible(true);
											
												JOptionPane.showMessageDialog(null, 
																					"TDIS call completed.", 
																					"Attention!", 
																					JOptionPane.INFORMATION_MESSAGE);
											}
									}
									catch(IOException ie)
									{
										JOptionPane.showMessageDialog(null, 
																				ie.toString(), 
																				"Error!", 
																				JOptionPane.ERROR_MESSAGE);
									}
									catch(InterruptedException ie)
									{
										JOptionPane.showMessageDialog(null,
																				ie.toString(),
																				"Error!",
																				JOptionPane.ERROR_MESSAGE);
									}
								}
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, 
																	"Please select a Multi-track Flight Path first.", 
																	"Stop!", 
																	JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, 
														"Please make sure that\n\n" +
														"(1) Latitude_1 + Latitude_2 != 0 and \n" +
														"    abs(Longitude_1 - Longitude_2) != PI.\n\n" +
														"(2) Latitude_1 != Latitude_2 and \n" +
														"    Longitude_1 != Longitude_2",
														"Stop!", 
														JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		);
	
	}

	boolean sameCoordinates(Double oLat, Double oLong, Double dLat, Double dLong)
	{
		if(oLat.doubleValue() == dLat.doubleValue() && oLong.doubleValue() == dLong.doubleValue())
		{
			return true;
		}
		return false;
	}

	boolean antipodal(Double oLat, Double oLong, Double dLat, Double dLong)
	{
		if((oLat.doubleValue() + dLat.doubleValue() == 0) && 
			(Math.abs(oLong.doubleValue() - dLong.doubleValue()) == Math.PI))
		{
			return true;
		}
		return false;
	}

	//Easy way to add components to a GridBagLayout panel.
	void add(Container c, Component comp, int x, int y, int w, int h, String anchor)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx              = x;
		gbc.gridy              = y;
		gbc.gridwidth          = w;
		gbc.gridheight         = h;
		gbc.insets             = new Insets(3,3,3,3);

		if      (anchor.equals("C")){gbc.anchor = GridBagConstraints.CENTER;}
		else if (anchor.equals("E")){gbc.anchor = GridBagConstraints.EAST;}
		else if (anchor.equals("W")){gbc.anchor = GridBagConstraints.WEST;}

		c.add(comp,gbc);
	}		

	private JPanel mainPanel,
					panel,
					warningPanel,
					topPanel,
					middleLeftPanel,
					middleRightPanel,
					middlePanel,
					distanceTimePanel,
					tableFmtPanel,
					bottomPanel;

	private JButton calculateBtn,
							exitBtn,
							reverseBtn,
							printBtn,
							sendToTDISBtn;

	private JRadioButton indexBasedRadioBtn,
								timeBasedRadioBtn,
								singleTrackBtn,
								multiTrackBtn,
								easyFmtRadioBtn,
								link16FmtRadioBtn,
								cecFmtRadioBtn;

	private GenericTextField origLongTF,
							origLatTF,
							destLongTF,
							destLatTF,
							altitudeTF,
							speedTF,
							intermediatePtTF,
							timeIntervalTF,
							radiansTF,
							kilometersTF,
							milesTF,
							nauticalMilesTF,
							dataMilesTF,
							millisecondsTF,
							secondsTF,
							minutesTF,
							hoursTF,
							headingRadTF,
							headingDegTF,
							multiTrackTF,
							offsetBetweenTracksTF,
							observerLatTF,
							observerLongTF,
							observerHtTF;

	private JTable table;

	private IndexPath indexPath;
	private TimePath timePath;
	private MultiTrackPath multiTrackPath;
	private MultiTrackPath tdisPath;

	private String printFilename;
	private MyPrint printIt;

}

public class createFlightPath
{

	static void displayUsage()
	{
		System.out.println("\nUsage: createFlightPath   <[ link16 | cec ]>\n" +
								"                          <Lat_1         ( -89.9999 to 89.9999 ) >\n" +
							   "                          <Long_1        (-179.9999 to 179.9999) >\n" +
							   "                          <Lat_2         ( -89.9999 to 89.9999 ) >\n" +
							   "                          <Long_2        (-179.9999 to 179.9999) >\n" +
							   "                          <speed         (    0 to 7488 kph    ) >\n" +
							   "                          <interval      (         >= 0        ) >\n" +
							   "                          <# tracks      (         >= 0        ) >\n" +
							   "                          <offset        (         >= 0        ) >\n" +
							   "                          <altitude      (   0 to 62415 meters ) >\n" +
								"                          <observer Lat  ( -89.9999 to 89.9999 ) >\n" +
								"                          <observer Long (-179.9999 to 179.9999) >\n" +
								"                          <observer Alt  (   0 to 62415 meters ) >\n" +
							   "                          <filename      (     to be created   ) >\n" +
								"\n" +
								"<> Required\n" +
								"[] Optional\n");

	}

	static double isDouble(String item) throws NumberFormatException
	{
		return (new Double(item)).doubleValue();
	}

	static int isInteger(String item) throws NumberFormatException
	{
		return (new Integer(item)).intValue();
	}
	
	static boolean isValidValue(double value, double min, double max)
	{
		return (value >= min && value < max) || (value == max);
	}

	static boolean isValidValue(int value, int min, int max)
	{
		return (value >= min && value < max) || (value == max);
	}

	static void runExternalProcess(String cmd, String notice) throws IOException,
																			InterruptedException
	{
		System.out.println(notice);

		Process process = Runtime.getRuntime().exec(cmd);
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		process.waitFor();
		
		int status = process.exitValue();
		int ch = ' ';
		String good = "";
		String bad  = "";

		if(status == 0)
		{
			System.out.println("External call to " + cmd + " was successful.\n");
		}
		else
		{
			while((ch = input.read()) != -1)
			{
				good += (char)ch;
				System.out.println("Non-error output: \n" + good + "\n");
			}
			while((ch = error.read()) != -1)
			{
				bad += (char)ch;
				System.out.println("Error output: \n" + bad + "\n");
			}
		}
		input.close();
		error.close();
		process.destroy();
	}

	public static void main(String [] args)
	{
		if(args.length == 0)
		{
			FlightPathGui path = new FlightPathGui();
			path.setVisible(true);
		}
		else
		{
			if(args.length == 14)
			{
				System.out.println();
				System.out.println("Message  : " + args[0]);
				System.out.println("Lat_1    : " + args[1]);
				System.out.println("Long_1   : " + args[2]);
				System.out.println("Lat_2    : " + args[3]);
				System.out.println("Long_2   : " + args[4]);
				System.out.println("Speed    : " + args[5]);
				System.out.println("Interval : " + args[6]);
				System.out.println("# Tracks : " + args[7]);
				System.out.println("Offset   : " + args[8]);
				System.out.println("Altitude : " + args[9]);
				System.out.println("Obs Lat  : " + args[10]);
				System.out.println("Obs Long : " + args[11]);
				System.out.println("Obs Alt  : " + args[12]);
				System.out.println("Filename : " + args[13]);
				System.out.println();

				try
				{
					if((args[0].equalsIgnoreCase("link16") || args[0].equalsIgnoreCase("cec")) &&
							isValidValue(isDouble(args[1]),-89.9999,89.9999)
							&& isValidValue(isDouble(args[2]),-179.9999,179.9999)
							&& isValidValue(isDouble(args[3]),-89.9999,89.9999)
							&& isValidValue(isDouble(args[4]),-179.9999,179.9999)
							&& isValidValue(isInteger(args[5]),0,7488)
							&& (isInteger(args[6]) >=0)
							&& (isInteger(args[7]) >=0)
							&& (isInteger(args[8]) >=0)
							&& isValidValue(isInteger(args[9]),0,62415)
							&& isValidValue(isDouble(args[10]),-89.9999,89.9999)
							&& isValidValue(isDouble(args[11]),-179.9999,179.9999)
							&& isValidValue(isInteger(args[12]),0,62415))
					{
						PrintWriter stream = new PrintWriter(new FileWriter(new File(args[13])));

						if(args[0].equalsIgnoreCase("link16"))
						{
							MultiTrackPath multiPath = new MultiTrackPath(
																	(new Integer(args[7])).intValue(),		// #trks
																	(new Double(args[8])).doubleValue(),	// offset
																	(new Double(args[1])).doubleValue(),	// Lat_1
																	(new Double(args[2])).doubleValue(),	// Long_1
																	(new Double(args[3])).doubleValue(),	// Lat_2
																	(new Double(args[4])).doubleValue(),	// Long_2
																	(new Double(args[9])).doubleValue(),	// altitude
																	(new Double(args[5])).doubleValue(),	// speed
																	(new Long(args[6])).longValue());		// interval

							multiPath.printPath(stream,null,multiPath.getTDISLink16Options());
						}
						else if(args[0].equalsIgnoreCase("cec"))
						{
							MultiTrackPath multiPath = new MultiTrackPath(
																	(new Integer(args[7])).intValue(),		// #trks
																	(new Double(args[8])).doubleValue(),	// offset
																	(new Double(args[1])).doubleValue(),	// Lat_1
																	(new Double(args[2])).doubleValue(),	// Long_1
																	(new Double(args[3])).doubleValue(),	// Lat_2
																	(new Double(args[4])).doubleValue(),	// Long_2
																	(new Double(args[9])).doubleValue(),	// altitude
																	(new Double(args[5])).doubleValue(),	// speed
																	(new Long(args[6])).longValue(),			// interval
																	(new Double(args[10])).doubleValue(),	// Obs Lat
																	(new Double(args[11])).doubleValue(),	// Obs Long
																	(new Double(args[12])).doubleValue());	// Obs Alt

							multiPath.printPath(stream,null,multiPath.getTDISCECOptions());
						}

						stream.close();
						System.out.println("Flight Path Created ... \n");

						runExternalProcess("gvim","Invoking GVIM ... ");
						runExternalProcess("gvim","Invoking GVIM ... ");

						System.out.println("\nDone\n");
					}
					else
					{
						System.out.println();
						System.out.println("One of the arguments in outside the valid range!");
						displayUsage();
					}
				}
				catch(InterruptedException ie)
				{
					System.out.println(ie.toString());
				}
				catch (NumberFormatException n)
				{
					displayUsage();
				}
				catch(IOException ie)
				{
					System.out.println(ie.toString());
				}
			}
			else
			{
				System.out.println();
				System.out.println("Invalid number of arguments!");
						
				displayUsage();
			}
		}
	}
}
