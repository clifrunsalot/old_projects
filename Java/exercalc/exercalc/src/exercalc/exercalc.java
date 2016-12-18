package exercalc;

import java.text.*;

/**
 * <p>Title: exercalc</p>
 * <p>Description: This program offers conversion between the english and metric system distance measurements</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Clif Hudson
 * @version 1.0
 */

public class exercalc {
  public static void main(String[] args) {
    CalcFrame mainFrame = null;
    try {
      mainFrame = new CalcFrame();
    }
    catch (ParseException ex) {
    }
    mainFrame.pack();
    mainFrame.setVisible(true);
  }
}
