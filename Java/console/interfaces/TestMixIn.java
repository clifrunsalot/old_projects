import java.io.*;

public class TestMixIn extends AbstractMixIn_T
{
		  private static TestMixIn instance = new TestMixIn();
		  private final int INITIALIZING = 0;
		  private final int OPERATING = 1;
		  private final int TERMINATING = 2;
		  private final int BAD = -1;
		  private int healthStatus = BAD;

		  private TestMixIn()
		  {
		  }

		  public static TestMixIn getInstance() throws NullSingletonException
		  {
				 if (instance == null)
				 {
						throw new NullSingletonException();
				 }
				 return instance;
		  }

		  public void processStart()
		  {
				healthStatus = INITIALIZING;
		  }

		  public void processTerminate()
		  {
				healthStatus = TERMINATING;
		  }

		  public int processCheckHealth()
		  {
				return healthStatus;
		  }
}
