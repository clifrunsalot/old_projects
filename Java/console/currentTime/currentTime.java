import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class currentTime
{
	public static void main(String [] args)
	{
		
		Calendar midnight = GregorianCalendar.getInstance();
		midnight.set(GregorianCalendar.HOUR,0);
		midnight.set(GregorianCalendar.MINUTE,0);
		midnight.set(GregorianCalendar.SECOND,0);

		System.out.println("" + midnight.get(GregorianCalendar.YEAR) + "," + 
										midnight.get(GregorianCalendar.MONTH) + "," +
										midnight.get(GregorianCalendar.DATE) + ", " +
										midnight.get(GregorianCalendar.HOUR) + "," + 
										midnight.get(GregorianCalendar.MINUTE) + "," + 
										midnight.get(GregorianCalendar.SECOND));

		long currentMS = System.currentTimeMillis();
		long midnightMS = midnight.getTimeInMillis();

		System.out.println("Current MS: " + currentMS);
		System.out.println("Midnight MS: " + midnightMS);

		System.out.println("ms since midnight: " + (currentMS - midnightMS));

		System.out.println("" + GregorianCalendar.getInstance().get(GregorianCalendar.YEAR) + "," + 
										GregorianCalendar.getInstance().get(GregorianCalendar.MONTH) + "," +
										GregorianCalendar.getInstance().get(GregorianCalendar.DATE) + ", " +
										GregorianCalendar.getInstance().get(GregorianCalendar.HOUR) + "," + 
										GregorianCalendar.getInstance().get(GregorianCalendar.MINUTE) + "," + 
										GregorianCalendar.getInstance().get(GregorianCalendar.SECOND));
	}
}
		
//GregorianCalendar(int year, int month, int date, int hour, int minute, int second)
