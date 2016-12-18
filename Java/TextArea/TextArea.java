import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

//****** 2 *******
//Create frame for window
class TextArea extends JFrame implements ActionListener, MenuListener 
{
	public TextArea()
	{
		setTitle("Test Menu");
		setSize(310,310);

		//Create window listener
		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		//****** 3 *******
		//Create menu panel
		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout());

		Container contentPane = getContentPane();
		contentPane.add(topPanel);
		
		//****** 8 ******
		//Add text area and a scrollpane
		textArea = new JTextArea("This a new text frame",8,40);
		JScrollPane scrollPane = new JScrollPane(textArea);
		contentPane.add(scrollPane,"Center");
		
		//****** 4 *******
		//Create menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//****** 5 *******
		//Create File menu and submenus
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		fileMenu.add(openItem);

		saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);

		closeItem = new JMenuItem("Close");
		fileMenu.add(closeItem);


		//****** 6 *******
		//Add file menu to menu bar
		menuBar.add(fileMenu);

		//****** 7 *******
		//Add listeners menu items
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		closeItem.addActionListener(this);




	}

	public void loadFile(String f)
	{
		try
		{
			FileReader fin = new FileReader(f);
			textArea.read(fin, f);
			fin.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		catch(IOException e)
		{
			System.out.println("Internal error: " + e);
		}
	}
	
	
	public void saveFile(String f)
	{
		try
		{
			FileWriter fout = new FileWriter(f);
			textArea.write(fout);
			fout.close();
		}
		catch(IOException e)
		{
			System.out.println("Internal error: " + e);
		}
	}

	//****** 9 ******
	//Create behavior to occur when menu items
	// are clicked
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JMenuItem)
		{
			String arg = event.getActionCommand();
			if (arg.equals("Close"))
				System.exit(0);
			else if (arg.equals("Open"))
			{
				JFileChooser fin = new JFileChooser();
				fin.setCurrentDirectory(new File("."));
				int result = fin.showOpenDialog(topPanel);

				if (result == JFileChooser.APPROVE_OPTION)
				{
					loadFile(fin.getSelectedFile().getName());
				}
			}
			else if (arg.equals("Save"))
			{
				JFileChooser fout = new JFileChooser();
				fout.setCurrentDirectory(new File("."));
				int result = fout.showSaveDialog(topPanel);

				if (result == JFileChooser.APPROVE_OPTION)
				{
					saveFile(fout.getSelectedFile().getName());
				}
			}
				
		}	
	}

	public void menuSelected(MenuEvent event)
	{
	}
	
	public void menuDeselected(MenuEvent event)
	{
	}

	public void menuCanceled(MenuEvent event)
	{
	}
	
	//***** 1 *******
	//Create class instance
	public static void main (String [] args)
	{
		TextArea myFrame = new TextArea();
		myFrame.show();
	}
	private JPanel topPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem closeItem;
	private JTextArea textArea;
}

