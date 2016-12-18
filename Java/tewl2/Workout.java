import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Defines a Workout object.  Its attributes are date, temperature,
 * weather, and events.
 */
public class Workout{

	public Workout(){
		date = "";
		temperature = "";
		weather = "";
		events = new Event[3];
	}

	public Workout(String d, String t, String w, Event [] e){
		date = d;
		temperature = t;
		weather = w;
		events = e;
	}

	/**
	 * Getter methods.
	 */
	public String getDate(){
		return date;
	}

	public String getTemp(){
		return temperature;
	}

	public String getWeather(){
		return weather;
	}

	public Event [] getEvents(){
		return events;
	}

	/**
	 * Setter methods.
	 */
	public void setDate(String d){
		date = d;
	}

	public void setTemp(String t){
		temperature = t;
	}

	public void setWeather(String w){
		weather = w;
	}

	public void setEvents(Event [] e){
		events = e;
	}

	/**
	 * Reads from StringTokenizer
	 */
	public void readWorkout(StringTokenizer t){
		date = t.nextToken();
		temperature = t.nextToken();
		weather = t.nextToken();
		for(int i=0; i<=totalEvents; i++){
			events[i].readEvent(t);
		}
	}

	/**
	 * Writes to file
	 */
	public void writeWorkout(PrintWriter pw){
		pw.write(date);
		pw.write(']');
		pw.write(temperature);
		pw.write(']');
		pw.write(weather);
		pw.write(']');
		for(int i=0; i<totalEvents; i++){
			events[i].writeEvent(pw);
		}
	}

	private String date = "";
	private String temperature = "";
	private String weather = "";
	private Event [] events;
	private int totalEvents = 2;
}

	
