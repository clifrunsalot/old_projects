import java.io.*;


public class EventTime {
	
	public EventTime(String t){
		String [] parsedTime = t.split(":");
		if (isValid(parsedTime[0]) && isValid(parsedTime[1]) && isValid(parsedTime[2])){
			hours = parsedTime[0];
			minutes = parsedTime[1];
			seconds = parsedTime[2];
		}
	}

	public String getTime(){
		return hours + ":" + minutes + ":" + seconds;
	}

	public void setTime(String t){
		String [] parsedTime = t.split(":");
		if (isValid(parsedTime[0]) && isValid(parsedTime[1]) && isValid(parsedTime[2])){
			hours = parsedTime[0];
			minutes = parsedTime[1];
			seconds = parsedTime[2];
		}
	}

	boolean isValid(String t){
		boolean valid = false;
		if ((Integer.parseInt(t) > 0) && (Integer.parseInt(t) < 60)){
			valid = true;
		}
		return valid;
	}
		
	private String hours = "00";
	private String minutes = "00";
	private String seconds = "00";
}
