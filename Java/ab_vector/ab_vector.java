import java.*;
import java.io.*; //FileReader,FileWriter,StreamInput,StreamOutput
import java.util.*; //StringTokenizer

/*
class: ab_vector is the main class that uses a vector to store
records.
*/
class ab_vector
{
	//Main
	public static void main (String [] args)
	{
		//Creates instance of ab_vector
		ab_vector AB = new ab_vector();

		//Creates vector of default size - 10
		Vector v = new Vector();

		//Creates three new records and adds them to
		//the vector v.
		v.add(new Person("Clif","Hudson",123456));
		v.add(new Person("Joann","Hudson",123457));
		v.add(new Person("Sirena","Hudson",123458));

                AB.printArray(v);
                AB.writeToFile(v,"ab.dat");
                AB.readFile("ab.dat");

        }

	//Method: Prints each element of the vector
        public void printArray(Vector v)
        {

		//Need to create the instance of Person
		//to be able to access components.
		Person p = new Person(" "," ",0);

		//Loop thru each element of v, cast it to a
		//Person object, use the Person instance to
		//access each element, and then print it.
		for (int j = 0; j < v.size(); j++)
		{
			p = (Person)(v.get(j));

			System.out.print
			(j + " " + p.getFname() + " "
				+ p.getLname() + " "
				+ p.getZip() + "\n");
		}
        }


	//Method: Writes contents of the vector v to file.
        public void writeToFile(Vector v, String f)
        {

		//Create instance of Person to access records in
		//the vector v.
		Person p = new Person(" "," ",0);
		 try
                {
                       //Prep file f to be written using a
			//character stream.
			PrintWriter dout = new PrintWriter
                                (new FileWriter (f));

                       //Print size of v, but first cast it to an
			//integer.
			dout.print((int)(v.size()));
                        dout.print("\n");

                        //Loop thru all elements of v, cast each to
			//an instance of Person, and the access and
			//print each record.
			for (int k = 0; k < v.size(); k++)
                        {
				p = (Person)(v.get(k));
                                dout.println
				(p.getFname() + ":"
					+ p.getLname()
					+ ":"+ p.getZip());
                        }

                        dout.close();
                }

                catch (IOException e)
                {
                        System.out.println("Error: " + e);
                        System.exit(0);
                }
        }

        //Method: Read file f and print contents.
	void readFile(String f)
        {
		String fn = " ";
		String ln = " ";
		int z = 0;
		int lines = 0;

                try
                {
       			//Prep file f for reading characters.
        		BufferedReader din = new BufferedReader
				(new FileReader (f));

			System.out.println();
			System.out.println
				("Contents of file: 'ab.dat'");

			//Read a line from file f.
			String line = din.readLine();

			//Use the StringTokenizer class to
			//parse the string read in above.
			StringTokenizer t = new StringTokenizer (line);

			//Get the first token, which is an integer
			//representing the number of records in the
			//file.  Since the value is not a string,
			//it must be casted to an integer for use later on.
			lines = Integer.parseInt(t.nextToken());
			System.out.println("Total: " + lines + " records");

			//Since the variable line obtained above reveals how
			//many records exist, then just loop the number of
			//times required to get all elements of the vector.
			for (int k = 0; k < lines; k++)
			{
				//As before, read in a whole line in the file.
				String data = din.readLine();

				//Then, use the StringTokenizer class to
				//parse each string in the first name,
				//last name, and sipcode.  Since each
				//item is separated by a ':', then it is
				// included as the second param in the
				//StringTokenizer instance u.
				StringTokenizer u =
					new StringTokenizer(data,":");
				fn = u.nextToken();
				ln = u.nextToken();
				z = Integer.parseInt(u.nextToken());

				System.out.println(fn + " " + ln + " " + z);
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