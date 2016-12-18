import java.*;
import java.io.*;

class Ab
{

	public static void main (String [] args)
	{
		Ab AB = new Ab();
		Person [] Record = new Person [3];
		File f = new File("ab.dat");

		Record[0] = new Person("Clif","Hudson",123456);
		Record[1] = new Person("Joann","Hudson",123457);
		Record[2] = new Person("Sirena","Hudson",123458);

		AB.printArray(Record);
		AB.writeFile(Record, f);
		AB.readFile(f);

	}

	public void printArray(Person [] P)
	{
		for (int j = 0; j < P.length; j++)
		{
			System.out.print(j + " ");
			System.out.print(P[j].getFname() + " ");
			System.out.print(P[j].getLname() + " ");
			System.out.println(P[j].getZip());
		}
	}

	public void writeFile(Person [] P, File f)
	{

		try
		{
		
			DataOutputStream dout = new DataOutputStream
				(new BufferedOutputStream
				(new FileOutputStream (f)));

			dout.writeInt(P.length);

			dout.writeChars("\r\n");

			for (int i = 0; i < P.length; i++)
			{

				dout.writeChars(P[i].getFname() + " " +
					P[i].getLname() + " ");
				dout.writeInt(P[i].getZip());
				dout.writeChars("\r\n");

			}
			dout.close();
		}

		catch (IOException e)
		{
			System.out.println("Error: " + e);
			System.exit(0);
		}
	}

	void readFile (File f)
	{

		char c = ' ';
		
		int z = 0;
		int lines = 0;

		try
		{
		
			DataInputStream din = new DataInputStream
				(new BufferedInputStream
				(new FileInputStream (f)));

			lines = din.readInt();

			for (int k = 0; k < lines; k++)
			{

				while( (c = din.readChar()) != ' ')
				{
					c = din.readChar();
					System.out.print(c);
				}

				z = din.readInt();
			       
					
				System.out.print(z);
			}

			din.close();
		}

		catch (IOException e)
		{
			System.out.println("Error: " + e);
			System.exit(0);
		}
	}

}
