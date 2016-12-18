import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Insets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.lang.Math;
import java.lang.Double;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.JFormattedTextField.*;

class GenericComboBoxModel extends DefaultComboBoxModel
{

 	private Object item;
 	private Vector list;
 	
	public GenericComboBoxModel(Vector v)
	{
		list = new Vector(v);
	}
	
	public void addElement(Object anObject)
	{
		list.add(anObject);
	    int length = getSize();
	    fireIntervalAdded(this, length-1, length-1);
	}
 
	public Object getElementAt(int index)
	{
		return (Object)list.elementAt(index);
	}
	
	public int getIndexOf(Object anObject)
	{
		return list.indexOf(anObject);
	}
	
 	public Object getSelectedItem()
	{
	 	return item;
 	}
 	
 	public int getSize()
	{
	 	return list.size();
 	}
 	
	public void insertElementAt(Object anObject, int index)
	{
		list.insertElementAt(anObject,index);
	    fireIntervalAdded(this, index, index);
	}
	
	public void removeAllElements()
	{
		list.removeAllElements();
		int length = list.size();
		fireIntervalRemoved(this, length, length);
	}
	
	public void removeElement(Object anObject)
	{
		int index = list.indexOf(anObject);
		list.removeElement(anObject);
		fireIntervalRemoved(this, index, index);
	}
	
	public void removeElementAt(int index)
	{
		list.removeElementAt(index);
		fireIntervalRemoved(this, index, index);
	}
	
 	public void setSelectedItem(Object anObject)
	{
	 	item = anObject;
 	}

}

class IntegerList
{
	public IntegerList(int start, int end)
	{
		list = new Vector();
		createList(start,end);
	}

	void createList(int start, int end)
	{
		for(int i=start; i<=end; i++)
		{
			String n = "" + i;
			list.addElement(n);
		}
	}

	public Vector getList()
	{
		return list;
	}

	private Vector list;
}

class DoubleList
{
	public DoubleList(double start, double end)
	{
		list = new Vector();
		createList(start,end);
	}

	void createList(double start, double end)
	{
		for(double i=start; i<=end; i+=0.01)
		{
	      DecimalFormat degFormat = new DecimalFormat("###.##");
			list.addElement(degFormat.format(i));
		}
	}

	public Vector getList()
	{
		return list;
	}

	private Vector list;
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
				else
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
}

class GenericTextField extends JFormattedTextField
{
		public GenericTextField(NumberFormat format, 
										Double start, 
										Double end)
		{
			super(format);
			setColumns(6);
			setInputVerifier(new RangeVerifier(start,end));
		}

		public GenericTextField(NumberFormat format, 
										Integer start, 
										Integer end)
		{
			super(format);
			setColumns(6);
			setInputVerifier(new RangeVerifier(start,end));
		}
}

class Point
{
	public Point()
	{

	}

	private double longitude;
	private double latitude;
	private double altitude;
	private double speed;
}

class FlightPath
{
	public FlightPath(double oLong, 
							double oLat,
							double dLong,
							double dLat,
							double alt,
							double spd,
							double incr)
	{
		path      = new Vector();
		origLong  = Math.toRadians(oLong);
		origLat   = Math.toRadians(oLat);
		destLong  = Math.toRadians(dLong);
		destLat   = Math.toRadians(dLat);
		altitude  = Math.toRadians(alt);
		speed     = spd;
		increment = incr;
	}

	void calcDistance()
	{
		distance = Math.acos(Math.sin(origLat) 
									* Math.sin(destLat) 
									+ Math.cos(origLat) 
									* Math.cos(destLat)
									* Math.cos(origLong - destLong));
	}

	public double getDistanceRadians()
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
		double radiusEarthMiles = 3956.09;
		return ((Math.toDegrees(distance)/180) * Math.PI * radiusEarthMiles);
	}

	public double getDistanceNms()
	{
		double radiusEarthNms = 3435.43;
		return ((Math.toDegrees(distance)/180) * Math.PI * radiusEarthNms);
	}
	
	private double origLong,
						origLat,
						destLong,
						destLat,
						altitude,
						speed,
						distance,
						increment;
	private Point point;
	private Vector path;
}


class FlightPathGui extends JFrame
{
	public FlightPathGui()
	{
		setTitle("Flight Path Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500,250));
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		panel.setBorder(border);

		add(panel,new JLabel("Longitude"),2,1,1,1);
		add(panel,new JLabel("Latitude"),4,1,1,1);

		NumberFormat degFormat = NumberFormat.getNumberInstance();
		degFormat.setMaximumIntegerDigits(3);
		degFormat.setMinimumFractionDigits(2);
		degFormat.setMaximumFractionDigits(2);
		
		origLongTF = new GenericTextField(degFormat,new Double(-179.99),new Double(180.00));
		origLatTF  = new GenericTextField(degFormat,new Double(-179.99),new Double(180.00));
		destLongTF = new GenericTextField(degFormat,new Double(-179.99),new Double(180.00));
		destLatTF  = new GenericTextField(degFormat,new Double(-179.99),new Double(180.00));

		origLongTF.setValue(new Double(0.00));
		origLatTF.setValue(new Double(0.00));
		destLongTF.setValue(new Double(0.00));
		destLatTF.setValue(new Double(0.00));

		add(panel,new JLabel("Origin"),1,2,1,1);
		add(panel,origLongTF,2,2,1,1);
		add(panel,Box.createRigidArea(new Dimension(20,20)),3,2,1,1);
		add(panel,origLatTF,4,2,1,1);
	
		add(panel,new JLabel("Destination"),1,3,1,1);
		add(panel,destLongTF,2,3,1,1);
		add(panel,Box.createRigidArea(new Dimension(20,20)),3,3,1,1);
		add(panel,destLatTF,4,3,1,1);

		NumberFormat intFormat = NumberFormat.getNumberInstance();
		intFormat.setMaximumIntegerDigits(5);
		intFormat.setMinimumFractionDigits(0);
		intFormat.setMaximumFractionDigits(0);

		altitudeTF = new GenericTextField(intFormat,new Integer(-1),new Integer(99999));
		altitudeTF.setValue(new Integer(0));
		
		middlePanel = new JPanel();
		middlePanel.setBorder(border);
		add(middlePanel,new JLabel("Altitude (m)"),1,1,1,1);
		add(middlePanel,altitudeTF,1,2,1,1);
		
		add(middlePanel,Box.createRigidArea(new Dimension(20,20)),1,3,1,1);
		
		speedTF = new GenericTextField(intFormat,new Integer(-1),new Integer(99999));
		speedTF.setValue(new Integer(0));
		add(middlePanel,new JLabel("Speed (kph)"),1,4,1,1);
		add(middlePanel,speedTF,1,5,1,1);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		calculateBtn = new JButton("Calculate");
		exitBtn = new JButton("Exit");
		bottomPanel = new JPanel();
		bottomPanel.setBorder(border);
		bottomPanel.add(calculateBtn);
		bottomPanel.add(exitBtn);
		mainPanel.add(panel);
		mainPanel.add(middlePanel);
		mainPanel.add(bottomPanel);

		getContentPane().add(mainPanel);
		pack();

		calculateBtn.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Double olong = new Double(origLongTF.getValue().toString());
					Double olat  = new Double(origLatTF.getValue().toString());
					Double dlong = new Double(destLongTF.getValue().toString());
					Double dlat  = new Double(destLatTF.getValue().toString());
			  		Double alt   = new Double(altitudeTF.getValue().toString());
			  		Double spd   = new Double(speedTF.getValue().toString());

					FlightPath p = new FlightPath(olong.doubleValue(), 
														olat.doubleValue(), 
														dlong.doubleValue(), 
														dlat.doubleValue(), 
														alt.doubleValue(), 
														spd.doubleValue(),
														0);
					p.calcDistance();

					System.out.println("Distance (radians): " + p.getDistanceRadians());
					System.out.println("Distance (km)     : " + p.getDistanceKm());
					System.out.println("Distance (miles)  : " + p.getDistanceMiles());
					System.out.println("Distance (nm)     : " + p.getDistanceNms());
				
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
	}

	void add(Container c, Component comp, int x, int y, int w, int h)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx              = x;
		gbc.gridy              = y;
		gbc.gridwidth          = w;
		gbc.gridheight         = h;
		gbc.insets             = new Insets(3,3,3,3);
		c.add(comp,gbc);
	}		

	private JPanel mainPanel,
	               panel,
		            middlePanel,
		            bottomPanel;

	private JButton calculateBtn,
	                exitBtn;

	private GenericTextField origLongTF,
							origLatTF,
							destLongTF,
							destLatTF,
							altitudeTF,
							speedTF;

}
		


public class createFlightPath
{
	public static void main(String [] args)
	{
		FlightPathGui path = new FlightPathGui();
		path.setVisible(true);
	}
}
