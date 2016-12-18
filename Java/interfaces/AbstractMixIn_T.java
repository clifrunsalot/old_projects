import java.io.*;

public abstract class AbstractMixIn_T implements StartListener_T,
		  										TerminateListener_T,
												CheckHealthListener_T
{
		  protected String name;

		  protected void setName(String n)
		  {
					 name = n;
		  }

		  protected String getName()
		  {
					 return name;
		  }
		  
		  protected String getClassName()
		  {
					 return this.getClass().getName();
		  }

}					 
