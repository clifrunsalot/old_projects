import java.*;

class Person
{
	public Person(String fn, String ln, int zip)
	{
		fname = fn;
		lname = ln;
		zipcode = zip;
	}

	public String getFname()
	{
		return fname;
	}

	public String getLname()
	{
		return lname;
	}

	public int getZip()
	{
		return zipcode;
	}

	private String fname;
	private String lname;
	private int zipcode;
}

