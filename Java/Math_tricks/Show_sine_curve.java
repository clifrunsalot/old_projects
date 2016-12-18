/*
file - Show_sine_curve.java
purpose - demonstrates how to use characters to display a sine graph function
*/

import java.io.*;	//for input and output
import java.lang.Math;	//for sine function

class Show_sine_curve {

	static double num;	//must be static because it will use a value from aother method

static void main (String Args[])
{
	double w;		//to hold value of x after it is converted to a double
	int y;			//used to count spaces to right (y-axis)
	int vert;		//reps y-axis in single digit border
	char Star = '*';	//denotes point on graph
	char Blank = ' ';	//used to line up star with y-axis


	//loop: prints x-axis and points on graph
	//pre: x-axis values begin at 0
	//post: y equals last calculated value

	for (int x=0; x<=20; x++)
	{

		//selection: prints values ranging from 0 thru 20 as single digits as in
		// where 10 is represented by 0 and 11 thru 20 is represented by the second digit in the number

		if (x<10)
			System.out.print(x);
		else if (x %10 == 0)
			System.out.print(0);
		else if (x > 10)
		{
			vert = x - 10;
			System.out.print(vert);
		}

		//moves all plotted points 9 spaces left

		System.out.print("         ");	//9 spaces

		w = (double)x;		//converts x to float w

		y = ((int)((java.lang.Math.sin(w))*10)) + 10;	//calculated sine value of w, which is then added to 10
								// so that no negative value is plotted

		num = (java.lang.Math.sin(w) * 10) + 10;	//keeps decimal form of sine value of w


		//loop: moves the number spaces required to plot y on y-axis

		for (int col=0; col<=(y-1); col++)
		{
			System.out.print(Blank);
		}
		System.out.print(Star);		//represents point on graph
		System.out.print("   ");
		System.out.println(num);	//value of point in decimal form, 
						// after it has been multiplied by ten and then added to ten
	}

	int bottom = (int)num;	//holds final value of num in integer form

	System.out.println();

	System.out.print("          ");  //10 spaces

	//loop: prints y-axis border

	for (int col=0; col<=bottom+1; col++)
	{
		if (col < 10) 
		{
			System.out.print(col);
		}
		else if (col % 10 == 0)
			System.out.print(0);
		else if (col > 10)
		{
			vert = col - 10;
			System.out.print(vert);
		}
	}
}
}

