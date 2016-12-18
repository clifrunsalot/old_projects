import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


class MySurvey extends JFrame implements ActionListener
{
	MySurvey()
	{
		contentPane = getContentPane();
		setTitle("Survey");
		setSize(300,150);
		
		panel = new JPanel(new GridLayout(7,1));
		
		label1 = new JLabel("Rate Nastasha Henstridge's appeal:");
		
		one_radio = new JRadioButton("1");
		one_radio.setActionCommand("1");
		two_radio = new JRadioButton("2");
		two_radio.setActionCommand("2");
		three_radio = new JRadioButton("3");
		three_radio.setActionCommand("3");
		four_radio = new JRadioButton("No Comment",true);
		four_radio.setActionCommand("4");
		
		group = new ButtonGroup();
		group.add(one_radio);
		group.add(two_radio);
		group.add(three_radio);
		group.add(four_radio);
		
		button = new JButton("Survey Results");
		button.setActionCommand("retrieve");
 		button.addActionListener(this);
		
		panel.add(label1);
		panel.add(one_radio);
		panel.add(two_radio);
		panel.add(three_radio);
		panel.add(four_radio);
		panel.add(button);
		
		contentPane.add(panel);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	public void invokeCprogram()
	{
		try
		{
			int ch = ' ';
			Process process = Runtime.getRuntime().exec("survey.exe");
			process.waitFor();
			InputStream good_stream = process.getInputStream();
			String good_string = "";
			if((ch = good_stream.read()) != -1)
			{
				good_string += ((char)ch);
				while((ch = good_stream.read()) != -1)
				{
					good_string += ((char)ch);
				}
				good_string = new String(good_string); 
				JOptionPane.showMessageDialog
						(null, 
						good_string,
						"Information", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			JOptionPane.showMessageDialog
					(null, 
					e.toString() + ":\n"
					+ "There was a problem invoking the external process.",
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void writeAnswer(String option)
	{
		try
		{
			BufferedWriter of = new BufferedWriter(new FileWriter(new File("question.txt")));
			of.write(option);
			of.close();
		}
		catch(Exception e)
		{
				JOptionPane.showMessageDialog
						(null, 
						"Could not read input",
						"Error", 
						JOptionPane.ERROR_MESSAGE);
		}
	}

    public void actionPerformed(ActionEvent evt)
    {
	    String source = evt.getActionCommand();
	    String option = group.getSelection().getActionCommand();
	    System.out.println(option);
	    if(source.equals("retrieve"))
	    {
		    writeAnswer(option);
		    invokeCprogram();
	    }
    }
	    
	private Container contentPane;
	private JPanel panel;
	private JLabel label1;
	private JRadioButton one_radio,
						two_radio,
						three_radio,
						four_radio;
	private ButtonGroup group;
	private JButton button;
}

public class survey
{
	public static void main(String [] args)
	{
		JFrame svy = new MySurvey();
		svy.setVisible(true);
	}
}
