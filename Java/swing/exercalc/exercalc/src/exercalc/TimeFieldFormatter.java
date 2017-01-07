package exercalc;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import java.text.*;

/**
 * <p>Title: exercalc</p>
 * <p>Description: This program offers conversion between the english and metric system distance measurements</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Clif Hudson
 * @version 1.0
 */

class TimeFieldFormatter extends DefaultFormatter{

  public TimeFieldFormatter() throws ParseException {
    setOverwriteMode(false);
  }

  public Object stringToValue(String s) throws ParseException {

    System.out.println(s);
    final int MAXALLOWABLE = 59;
    String[] str = s.split(":");
    int[] timeArray = new int[str.length];
    if (str.length == 3) {
      for (int i = 0; i <= str.length; i++) {
        if (Integer.parseInt(str[i]) <= MAXALLOWABLE &&
            Integer.parseInt(str[i]) > -1) {
          timeArray[i] = Integer.parseInt(str[i]);
        }
        else {
          throw new ParseException("Value is not in the format hh:mm:ss", 0);
        }
      }
    }
    else {
      throw new ParseException("Value is not in the format hh:mm:ss", 0);
    }
    return null;
  }

  public String valueToString(Object value) throws ParseException {

    if(value == null)
      return null;

    if(!(value instanceof int[]))
      throw new ParseException("Value is not in the format hh:mm:ss", 0);

    int [] timeArray = (int[])value;
    StringBuffer buff = new StringBuffer();

    for (int i=0; i<timeArray.length; i++){
      if(i<=1)
        buff.append(String.valueOf(timeArray[i]) + ":");
      else
        buff.append(String.valueOf(timeArray[i]));
    }

    return buff.toString();
  }

}

