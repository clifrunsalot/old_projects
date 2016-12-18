import java.io.*;


public class MyPrint
{
	public MyPrint()
	{
	}
	
	void print(boolean b)
	{
		System.out.println(String.valueOf(b));
	}

	void print(char c)
	{
		System.out.println(c);
	}

	void print(String s)
	{
		System.out.println(s);
	}

	void print(int i)
	{
		System.out.println(i);
	}
	
	void print(double d)
	{
		System.out.println(d);
	}
		
	void print(float f)
	{
		System.out.println(f);
	}
	
	void print(long l)
	{
		System.out.println(l);
	}
	
	void print(short s)
	{
		System.out.println(s);
	}
	
	void print(StringBuffer s)
	{
		System.out.println(s.toString());
	}
	
	void print(MyProcess p)
	{
		print("   name - " + p.getName());
		print("Orig HB - " + p.getOrigHeartbeatRequired());
		print("     HB - " + p.getHeartbeatRequired());
		print("Orig CO - " + p.getOrigCommentedOut());
		print("     CO - " + p.getCommentedOut());
	}
}
