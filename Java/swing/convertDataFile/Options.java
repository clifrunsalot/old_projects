import java.io.*;
import java.util.StringTokenizer;


class Route extends Options
{
	public Route()
	{
		super();
	}

}


class Exercise extends Options
{
	public Exercise()
	{
		super();
	}
}


class Options
{
	public Options()
	{
	}

	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setCode(String c)
	{
		code = c;
	}

	public void setDescription(String d)
	{
		description = d;
	}

	//Reads one record from input stream
	public void readData(StringTokenizer t)
	throws    IOException
	{
		//System.out.println("Workout.readData");
		setCode(t.nextToken());
		System.out.println(code);
		setDescription(t.nextToken());
		System.out.println(description);
	}//~IOException...

	public void printData (PrintWriter fout)
	{
		//System.out.println("Workout.print");
		fout.print(getCode() + "|");
		System.out.println(getCode());
		fout.print(getDescription() + "|");
		System.out.println(getDescription());

	}//~public void print (Print...

	String code = "";
	String description = "";
}
