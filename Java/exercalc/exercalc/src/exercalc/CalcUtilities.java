package exercalc;

public class CalcUtilities {
  public CalcUtilities() {
  }

  //Validates strings as digits. Returns false if string
  //cannot be interpreted as a digit.
  public static boolean validDigit(String s, int maxlen) {
    ////System.out.println("validDigit");

    boolean isValid = false;
    char[] charArray = s.toCharArray();

    if ( (charArray.length > 0) && (charArray.length <= maxlen)) {
      for (int i = 0; i < charArray.length; i++) {
        if (Character.isDigit(charArray[i])) {
          isValid = true;
        }
        else {
          isValid = false;
          break;
        }
      } //~for(int i=0; i<charArray...
    } //~if ((charArray.length > ...

    return isValid;
  } //~public boolean validDigi...

  //Validates strings as doubles. Returns false if string cannot
  //be interpreted as a double.
  public static boolean validDouble(String s, int maxlen) {
    //System.out.println("validDouble");

    boolean isValid = false;
    char[] charArray = s.toCharArray();
    int numberOfDecimals = 0;

    if ( (charArray.length > 0) && (charArray.length <= maxlen)) {
      for (int i = 0; i < charArray.length; i++) {
        if ( (Character.isDigit(charArray[i])) ^ (charArray[i] == '.')) {
          if (charArray[i] == '.') {
            numberOfDecimals++;
            if (numberOfDecimals == 0 || numberOfDecimals == 1) {
              isValid = true;
            }
            else {
              isValid = false;
              break;
            }
          } //~if(charArray[i] == '.')...

          else {
            isValid = true;
          }
        } //~if((Character.isDigit(ch...

        else {
          isValid = false;
          break;
        }
      } //~for(int i=0; i<charArray...
    } //~if((charArray.length > 0...

    return isValid;
  } //~public boolean validDoub...

  public static double getAvgSpeed(String localEvent, double time,
                                   double distance) {
    double avgSpeed = 0.0;
    if (distance > 0 && time > 0) {
      if (localEvent.equals("swim")) {
        double minsInHour = 60;
        double _hr = 1;
        double _mile = 1;
        double ydsInMile = 1760;

        //The swim distance is saved in yds, so this must be
        // converted to miles before it is plotted on the graph.
        avgSpeed = (distance / time) * (minsInHour / _hr) * (_mile / ydsInMile);
      }
      else {
        avgSpeed = (distance / time) * 60;
      }
    }
    else {
      avgSpeed = 0.0;

    }
    return avgSpeed;
  }

  public static double getMinsPer100yds(double time, double distance) {
    double _100yds = 100;
    if (time > 0 && distance > 0) {
      return ( (time / distance) * _100yds);
    }
    else {
      return 0.0;
    }

  }

  public static double getMinsPerMile(double time, double distance) {
    double _60mins = 60;
    return ( (time / distance) * _60mins);
  }

}
