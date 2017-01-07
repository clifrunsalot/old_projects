import java.io.*;

public class TestInterfacesAndAbstractions
{
		  public static void main (String [] args)
		  {
					 try
					 {
						 TestMixIn mixIn = TestMixIn.getInstance();
	
						 System.out.println(mixIn.getInstance().toString());
	
						 mixIn.getInstance().setName("Clif's abstract");
						 System.out.print("Name: ");
						 System.out.println(mixIn.getInstance().getName());

						 System.out.print("Class name: ");
						 System.out.println(mixIn.getInstance().getClassName());

						 mixIn.getInstance().processStart();
						 System.out.print("Health: ");
						 System.out.println(mixIn.getInstance().processCheckHealth());

						 mixIn.getInstance().processTerminate();
						 System.out.print("Health: ");
						 System.out.println(mixIn.getInstance().processCheckHealth());
					 }
					 catch (NullSingletonException nse)
					 {
								System.out.println("Singleton not instantiated");
					 }
		  }
}
