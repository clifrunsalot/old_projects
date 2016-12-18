/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

package afatds.app.ppt;

import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;


/**
*	This class is the logical representation of an enumeration type.  Use this class
*	to create a single enumeration type.  To create a combobox with an assigned name
*	and list of values, simply call the constructor and pass the name and a Vector 
*	of values.
*	
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/
class EnumerationType
{
	/**
	*	This is a variable of the MyPrint class.  Refer to the MyPrint class for usage.
	*/
	private MyPrint p;
	
	/**
	*	This is the name of the combobox.
	*/
	private String name;
	
	/**
	*	This represents the combobox control.
	*/
	private JComboBox comboBox;
	
	/**
	*	This is the list of values to be displayed by the combobox.
	*/
	private Vector itemList;
	
	/**
	*	This is the label belonging to the combobox.  Be sure to refer to this when labeling 
	*	the combobox on the form.
	*/
	private JLabel label;
	
	
	/**
	*	This constructs a combobox assigned the name "nm" and filled with the list of
	*	'items.'
	*
	*	@param nm is the name of the object
	*	@param items is the list of items to be displayed by the combobox
	*/
	public EnumerationType(String nm, Vector items)
	{
		p = new MyPrint();
		name = new String(nm);
		label = new JLabel(name);
		comboBox = new JComboBox();
		itemList = new Vector(items.size());
		setUpComboBox(items);
	}

	/**
	*	This constructs a simple combobox assigned the name 'nm'; it will not have 
	*	any items.
	*	
	*	@param nm is the name of the object
	*/
	public EnumerationType(String nm)
	{
		p = new MyPrint();
		name = new String(nm);
		label = new JLabel(name);
		comboBox = new JComboBox();
	}

	/**
	*	Returns the name of the enumeration object.
	*	
	*	@return The name of the object
	*/
	public String getName()
	{
		return name;
	}
	
	/**
	*	Adds array of 'items' to the comboBox.
	*	
	*	@param items is a Vector of values
	*	
	*	@return void
	*/
	public void setUpComboBox(Vector items)
	{
		GenericComboBoxModel model;;
		/**	
		*	The order of this approach is IMPORTANT.  If the first value read is not an
		*	integer, then an exception is thrown.  At which point, the combobox is 
		*	built with strings.
		*	If the first item is actually an integer, then the combobox is
		*	built with integers.
		*/
		try
		{
			int start = (new Integer((String)items.firstElement())).intValue();
			int end = (new Integer((String)items.lastElement())).intValue();
			Vector numberList = new Vector();
			
			for(int i=start; i<=end; i++)
			{
				numberList.add(new Integer(i));
			}
			
			model = new GenericComboBoxModel(numberList);
			comboBox.setModel(model);
			comboBox.setSelectedIndex(0);
		}
		catch(Exception e)
		{
			model = new GenericComboBoxModel(items);
			comboBox.setModel(model);
			comboBox.setSelectedIndex(0);
		}
	}
	
	/**
	*	Adds a String item to the combobox.
	*	@param 'item' is the String to add.
	*	@return void
	*/
	public void addItem(String item)
	{
		comboBox.addItem(item);
	}
	
	/**
	*	Adds an integer to the combobox.
	*	
	*	@param item is the integer to add
	*	
	*	@return void
	*/
	public void addItem(int item)
	{
		comboBox.addItem(new Integer(item));
	}
	
	/**
	*	Returns the JComboBox object associated with this enumeration.
	*	
	*	@return The JComboBox attribute of this object
	*/
	public JComboBox getComboBox()
	{
		return comboBox;
	}
	
	/**
	*	Sets the label assigned to this object.
	*	
	*	@param s is the text of the label
	*	
	*	@return void
	*/
	public void setLabel(String s)
	{
		label.setText(s);
	}
	
	/**
	*	Returns the label of this object.
	*	
	*	@return The JLabel attribute of this object
	*/
	public JLabel getLabel()
	{
		return label;
	}
	
	/**
	*	For debugging purposes only.  This prints the items in the combobox.
	*	
	*	@return void
	*/
	public void print()
	{
		/**
		*	The order of this approach is IMPORTANT.  If the first value read is not an
		*	integer, then an exception is thrown.  At which point, the combobox is 
		*	assumed to have been built with strings.
		*	If the first item is actually an integer, then the combobox is
		*	assumed to have been built with integers.
		*/
		p.print("enumeration: " + name);
		try
		{
			int start = Integer.parseInt((String)itemList.firstElement());
			int end  = Integer.parseInt((String)itemList.lastElement());
			for(int i=start; i<end; i++)
			{
				p.print("" + itemList.elementAt(i));
			}
		}
		catch(Exception e)
		{
			for(int i=0; i<itemList.size(); i++)
			{
				String value = "" + itemList.elementAt(i);
				p.print(value);
			}
		}
	}
	
}