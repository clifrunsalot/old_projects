package exercalc;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.Dimension;
import java.awt.Container;
import javax.swing.JPanel;
import java.awt.event.WindowEvent;
import java.text.*;
import javax.swing.JFormattedTextField;

/**
 * <p>Title: exercalc</p>
 * <p>Description: This program offers conversion between the english and metric system distance measurements</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Clif Hudson
 * @version 1.0
 */

public class CalcFrame
    extends JFrame {

  private Container contentPane;
  private JPanel mainPanel;
  private JFormattedTextField timeTextField;
  private int [] initialValue = {00,00,00};

  public CalcFrame() throws ParseException {
    setTitle("Time/Distance/Pace Calculator");
    contentPane = getContentPane();

    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(300, 200));

    timeTextField = new JFormattedTextField(new
                                            TimeFieldFormatter());
    timeTextField.setValue(initialValue);

    mainPanel.add(timeTextField);

    contentPane.add(mainPanel);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
