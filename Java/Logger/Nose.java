import java.util.logging.*;
import java.io.IOException;

public class Nose{
	
	private static Logger logger;
	private static FileHandler fh;
	
	public Nose(){
		logger = Logger.getLogger("com.wombat.nose");
		
		try{
			fh = new FileHandler("mylog.txt");
		}catch (IOException ioe){
			logger.log(Level.WARNING,"trouble sneezing",ioe);
		}
			
	}
	
	public static void main(String argv[]) {
		
		Nose nose = new Nose();
		
		// Send logger output to our FileHandler.
 		logger.addHandler(fh);
		// Request that every detail gets logged.
		logger.setLevel(Level.ALL);
		// Log a simple INFO message.
		logger.info("doing stuff");
		try {
			logger.log(Level.INFO,"inside the try");
			TestLog tL = new TestLog();
		} catch (Error ex) {
			logger.log(Level.WARNING, "trouble sneezing", ex);
		}
	}

	public static Logger getLogger(){
			  return Nose.logger;
	}
}

class TestLog{

		  private static Logger logger;

		  public TestLog(){

					 log("inside TestLog");
				
		  }

		  public static void log(String str){
				logger = Nose.getLogger();
				logger.log(Level.INFO, str);
		  }

}
