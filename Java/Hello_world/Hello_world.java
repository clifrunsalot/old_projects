import java.*;
import java.io.*;
import java.util.*;

class Hello_world {

	public void Say_Hello() throws Exception
	{
		System.out.print ("Hello world.");
		BufferedReader infile = new BufferedReader(new FileReader(new File("bad_file.txt")));
	}

	static void main (String args[])
	{
		try
		{
			Hello_world Greeting = new Hello_world();
			Greeting.Say_Hello();
			System.exit(0);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
