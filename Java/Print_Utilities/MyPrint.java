/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

package Print_Utilities;

/**
*	This class offers a simpler way to print to standard out.
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/

public class MyPrint
{
	/**
	*	This constructs an object of the class.
	*/
	public MyPrint()
	{
	}
	
	/**
	*	This prints a boolean value.
	*
	*	@param b is the boolean value
	*	@return void
	*/
	public void print(boolean b)
	{
		System.out.println(String.valueOf(b));
	}

	/**
	*	This prints a char value.
	*
	*	@param c is the char value
	*	@return void
	*/
	public void print(char c)
	{
		System.out.println(c);
	}

	/**
	*	This prints a String value.
	*	
	*	@param s is the String value
	*	@return void
	*/
	public void print(String s)
	{
		System.out.println(s);
	}
	
	/**
	*	This prints a StringBuffer value.
	*
	*	@param s is the StringBuffer value
	*	@return void
	*/
	public void print(StringBuffer s)
	{
		System.out.println(s.toString());
	}

	/**
	*	This prints an integer value.
	*
	*	@param i is the integer value
	*	@return void
	*/
	public void print(int i)
	{
		System.out.println(i);
	}
	
	/**
	*	This prints a value of the Integer class.
	*
	*	@param i is the Integer Class value
	*	@return void
	*/
	public void print(Integer i)
	{
		System.out.println(i.toString());
	}
	
	/**
	*	This prints a double value.
	*
	*	@param d is the double value
	*	@return void
	*/
	public void print(double d)
	{
		System.out.println(d);
	}
		
	/**
	*	This prints a value of the Double class.
	*
	*	@param d is the Double class value
	*	@return void
	*/
	public void print(Double d)
	{
		System.out.println(d.toString());
	}
	
	/**
	*	This prints a float value.
	*
	*	@param f is the float value
	*	@return void
	*/
	public void print(float f)
	{
		System.out.println(f);
	}
	
	/**
	*	This prints a long value.
	*
	*	@param l is the long value
	*	@return void
	*/
	public void print(long l)
	{
		System.out.println(l);
	}
	
	/**
	*	This prints a short value.
	*
	*	@param s is the short value
	*	@return void
	*/
	public void print(short s)
	{
		System.out.println(s);
	}

	/**
	*	This prints the table object passed in.  It automatically discovers the table model used
	*	and proceeds to display the values in each cell of the table.
	*
	*	@param table is the Table object
	*/	
    public void print(javax.swing.JTable table) 
    {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println();
        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) 
        {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) 
            {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
    
}
