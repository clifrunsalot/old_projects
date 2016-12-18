/** T.E.W.L - Triathlon Electronic Workout Log
* Copyright 2002 by Clifton B. Hudson
* Purpose: Electronic exercise log with a file database *
* File: TewlUtilities.java
*/

import java.util.*;


public class TewlUtilities
{
	public TewlUtilities()
	{
		d = new Debug();
	}

   	//Validates strings as digits. Returns false if string
    //cannot be interpreted as a digit.
    public boolean validDigit(String s, int maxlen)
    {
	    ////System.out.println("validDigit");

        boolean isValid = false;
        char [] charArray = s.toCharArray();

        if ((charArray.length > 0) && (charArray.length <= maxlen))
        {
            for(int i=0; i<charArray.length; i++)
            {
                if(Character.isDigit(charArray[i]))
                isValid = true;
                else
                {
                    isValid = false;
                    break;
                }
            }//~for(int i=0; i<charArray...
        }//~if ((charArray.length > ...

        return isValid;
    }//~public boolean validDigi...

    //Validates strings as doubles. Returns false if string cannot
    //be interpreted as a double.
    public boolean validDouble(String s,int maxlen)
    {
	    //System.out.println("validDouble");

        boolean isValid = false;
        char [] charArray = s.toCharArray();
        int numberOfDecimals = 0;

        if((charArray.length > 0) && (charArray.length <= maxlen))
        {
            for(int i=0; i<charArray.length; i++)
            {
                if((Character.isDigit(charArray[i])) ^ (charArray[i]=='.'))
                {
                    if(charArray[i] == '.')
                    {
                        numberOfDecimals++;
                        if(numberOfDecimals == 0 || numberOfDecimals == 1)
            	            isValid = true;
                        else
                        {
                            isValid = false;
                            break;
                        }
                    }//~if(charArray[i] == '.')...

                    else
                	    isValid = true;
                }//~if((Character.isDigit(ch...

                else
                {
                    isValid = false;
                    break;
                }
            }//~for(int i=0; i<charArray...
        }//~if((charArray.length > 0...

        return isValid;
    }//~public boolean validDoub...

    public double getAvgSpeed (String localEvent, double time, double distance)
    {
        double avgSpeed = 0.0;
        if(distance > 0 && time > 0)
        {
	        if (localEvent.equals("swim"))
	        {
			    double minsInHour = 60;
			    double _hr = 1;
			    double _mile = 1;
			    double ydsInMile = 1760;

		        //The swim distance is saved in yds, so this must be
		        // converted to miles before it is plotted on the graph.
				avgSpeed = (distance/time)*(minsInHour/_hr)*(_mile/ydsInMile);
	        }
	        else
	        {
		        avgSpeed = (distance/time)*60;
	        }
	    }
        else
    	    avgSpeed = 0.0;

        return avgSpeed;
    }

    public double getMinsPer100yds(double time, double distance)
    {
        double _100yds = 100;
	    if (time > 0 && distance > 0)
	    	return ((time/distance) * _100yds);
	    else
	    	return 0.0;

    }

    public double getMinsPerMile(double time, double distance)
    {
	    double _60mins = 60;
		return((time/distance)*_60mins);
   	}

    public double getTotal(String event, Vector logbook)
    {
	    //System.out.println("getTotal");

        double total = 0.0;
        double subTotal = 0.0;
        Workout w = new Workout();
        WorkoutEvent [] events;
        ListIterator iter = logbook.listIterator();

        while(iter.hasNext())
        {
            w = (Workout)iter.next();
            events = w.getEvents();
            if(event.equals("pushups"))
	            subTotal = w.getPu();
            else if(event.equals("situps"))
            	subTotal = w.getSu();
            else
            {
            	for (int i=0; i<events.length; i++)
            	{
		            if (event.equals(events[i].getName()))
		            	subTotal = events[i].getDistance();
            	}
            }
            total = total + subTotal;
        }//~while(iter.hasNext())...

        return total;
    }//~public double getTotal(S...


    public double getAvgPerDay(String event, Vector logbook)
    {
	    //System.out.println("getAvgPerDay");

        double avg = 0.0;
        if((getTotal(event,logbook)>0) && (getTotalDays(event,logbook)>0))
        	avg = (getTotal(event,logbook))/((double)getTotalDays(event,logbook));
        else
        	avg = 0;
        return avg;
    }

    public int getTotalDays(String event, Vector logbook)
    {
	    //System.out.println("getTotalDays");

        int eventDayCount = 0;
        Workout w = new Workout();
        WorkoutEvent [] events;
        ListIterator iter = logbook.listIterator();

        while(iter.hasNext())
        {
            w = (Workout)iter.next();
            events = w.getEvents();
            for (int i=0; i<events.length; i++)
            {
	            if((events[i].getName()).equals(event) &&
	            events[i].getDistance() > 0.0)
	            	eventDayCount++;
            }
        }

        return eventDayCount;
    }//~public int getTotalDays(...

    public double getAvgSpeedForAllWorkouts(String event, Vector logbook)
    {
	    //System.out.println("getAvgSpeedForAllWorkouts");

        double avgSpeed = 0.0;
        double accumulateDailySpeed = 0.0;
        int eventDayCount = 0;
        double highestDailyAvg = 0.0;
        double lowestDailyAvg = 0.0;
        double dailyAvgSpeed = 0.0;
        Workout w = new Workout();
        WorkoutEvent [] events;
        ListIterator iter = logbook.listIterator();

        eventDayCount = getTotalDays(event,logbook);
//        System.out.println("eventDayCount = " + eventDayCount);

        while(iter.hasNext())
        {
            w = (Workout)iter.next();
            events = w.getEvents();
            for (int i=0; i<events.length; i++)
            {
	            if((events[i].getName()).equals(event) && events[i].getDistance() > 0.0)
	            {
		            if (events[i].getName().equals("swim"))
		            {
		                accumulateDailySpeed = accumulateDailySpeed +
	                    ((100*60)/((getMinsPer100yds(events[i].getTime().getConvertedTime(),
	                    		(double)events[i].getDistance()))*1760));
	                    		
//	                    System.out.println("getMinsPer100yds = " +
//	                    ((100*60)/((getMinsPer100yds(events[i].getTime().getConvertedTime(),
//	                    		(double)events[i].getDistance()))*1760)));
	                }
	                else if (events[i].getName().equals("run"))
	                {
		                accumulateDailySpeed = accumulateDailySpeed +
	                    	getMinsPerMile(events[i].getTime().getConvertedTime(),
	                    	(double)events[i].getDistance());
	                }
	                else
	                {
		                accumulateDailySpeed = accumulateDailySpeed +
	                    	getAvgSpeed(events[i].getName(),events[i].getTime().getConvertedTime(),
	                    	(double)events[i].getDistance());
	                }
                }
            }
        }//~while(iter.hasNext())...

        if((accumulateDailySpeed > 0) && (eventDayCount > 0))
        {
	        if (event.equals("swim"))
	        {
		        avgSpeed = (60 * 100)/((accumulateDailySpeed/eventDayCount) * 1760);
	        }
	        else
	        	avgSpeed = accumulateDailySpeed/eventDayCount;
        }
        else
        	avgSpeed = 0.0;

//        System.out.println("accumulateDailySpeed = " + accumulateDailySpeed);
//        System.out.println("eventDayCount = " + eventDayCount);

        return avgSpeed;
    }//~public double getAvgSpee...
    
    private Debug d;

}


