/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: Debug.java
*/

import java.io.*;

/**
Instantiates the Debug class.  Essentially, it offers a shorter way to call the print
command when the application is being debugged.
*/
public class Debug
{
	Debug()
	{
	}
	
	/**
	Prints a boolean value.
	
	@param s A boolean
	
	@return void
	*/
	public void print(boolean b)
	{
		System.out.println("-");
		if(b)
			System.out.print("true");
		else
			System.out.print("false");
	}

	/**
	Prints a string.
	
	@param s A string
	
	@return void
	*/
	public void print(String s)
	{
		System.out.println("-");
		System.out.println(s);
	}

	/**
	Prints a double.
	
	@param d A double
	
	@return void
	*/
	public void print(double d)
	{
		System.out.println("-");
		System.out.println(d);
	}
	
	/**
	Prints an integer.
	
	@param i An Integer
	
	@return void
	*/
	public void print(int i)
	{
		System.out.println("-");
		System.out.println(i);
	}
	
	/**
	Prints a Workout object.
	
	@param w A Workout object
	
	@return void
	*/
	public void print(Workout w)
	{
		System.out.println("-");
		w.printData(System.out);
	}
	
	/**
	Prints a WorkoutEvent object.
	
	@param e A WorkoutEvent
	
	@return void
	*/
	public void print(WorkoutEvent e)
	{
		System.out.println("-");
		e.printData(System.out);
	}
	
	/**
	Prints a Maintenance object.
	
	@param m A Maintenance object
	
	@return void
	*/
	public void print(Maintenance m)
	{
		System.out.println("-");
		m.printData(System.out);
	}
	
	/**
	Prints an Equipment object.
	
	@param e An Equipment object
	
	@return void
	*/
	public void print(Equipment e)
	{
		System.out.println("-");
		e.print(System.out);
	}
	
	/**
	Prints an Object name in String format.
	
	@param c An Object
	
	@return void
	*/
	public void print(Object c)
	{
		System.out.println("-");
		System.out.println(c.toString());
	}
	
	/**
	Prints an array of Objects.
	
	@param array An Array of Objects
	@param rows Number of rows to cycle through
	@param cols Number of columns per row to cycle through
	
	@return void
	*/
	public void print(Object [][] array, int rows, int cols)
	{
		System.out.println("-");
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				System.out.print(array[i][j] + "  ");
			}
			System.out.println();
		}
	}
				
	/**
	Prints a single array of objects
	
	@param array A single array of objects
	
	@return void
	*/
	public void print(Object [] array)
	{
		System.out.println("-");
		for(int i=0; i<array.length; i++)
		{
			System.out.println(array[i]);
		}
	}
	
}




