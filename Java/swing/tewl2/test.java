import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSpinner;
import java.util.Date;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.text.DateFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;


class ComboBoxTest extends JFrame{
	public ComboBoxTest(){
		super("Test");
		container = getContentPane();

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(200,200));

		eventComboBox = new JComboBox(new StringComboBoxModel(eventNames));
		eventComboBox.setSelectedIndex(0);

		locationComboBox = new JComboBox(new StringComboBoxModel(locationNames));
		locationComboBox.setSelectedIndex(0);
		
		doubleComboBox = new JComboBox(new DoubleComboBoxModel(0.0,500.0));
		doubleComboBox.setSelectedIndex(0);
		doubleTextField = new JTextField(doubleComboBox.getItemAt(0).toString(),10);

		integerComboBox = new JComboBox(new IntegerComboBoxModel(1,500));
		integerComboBox.setSelectedIndex(0);
		integerTextField = new JTextField(integerComboBox.getItemAt(0).toString(),10);

		timeComboBox = new JComboBox(new TimeComboBoxModel());
		timeComboBox.setSelectedIndex(0);
		timeTextField = new JTextField(timeComboBox.getItemAt(0).toString(),10);

		dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
		dateTextField = new JFormattedTextField(dateFormatter);
		dateTextField.setValue(new Date());
		
		doubleComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doubleTextField.setText(doubleComboBox.getSelectedItem().toString());
			}
		});
		
		integerComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				integerTextField.setText(integerComboBox.getSelectedItem().toString());
			}
		});
		
		timeComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				timeTextField.setText(timeComboBox.getSelectedItem().toString());
			}
		});

		dateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor de = new JSpinner.DateEditor(dateSpinner,"MM/dd/yy");
		dateSpinner.setEditor(de);

		dateSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				dateTextField.setValue(dateSpinner.getValue());
			}
		});

		Object [][] objs = {{eventComboBox.getSelectedItem(),
									doubleComboBox.getSelectedItem(),
									timeComboBox.getSelectedItem(),"200",
									locationComboBox.getSelectedItem()}};

		eventTable = new JTable(new EventTableModel(objs));

		eventColumn = eventTable.getColumnModel().getColumn(0);
		eventColumn.setCellEditor(new DefaultCellEditor(eventComboBox));

		distanceColumn = eventTable.getColumnModel().getColumn(1);
		distanceColumn.setCellEditor(new DefaultCellEditor(doubleComboBox));
		
		timeColumn = eventTable.getColumnModel().getColumn(2);
		timeColumn.setCellEditor(new DefaultCellEditor(timeComboBox));

		locationColumn = eventTable.getColumnModel().getColumn(4);
		locationColumn.setCellEditor(new DefaultCellEditor(locationComboBox));

		JScrollPane scrollPane = new JScrollPane(eventTable);
		eventTable.setPreferredScrollableViewportSize(new Dimension(500, 70));


		
		
		panel.add(doubleComboBox);
		panel.add(doubleTextField);
		panel.add(integerComboBox);
		panel.add(integerTextField);
		panel.add(timeComboBox);
		panel.add(timeTextField);
		panel.add(dateSpinner);
		panel.add(dateTextField);

		panel.add(scrollPane);

		container.add(panel);

	   addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	private Container container;
	private JPanel panel;
	private JComboBox eventComboBox;
	private JComboBox locationComboBox;
	private JComboBox doubleComboBox;
	private JComboBox integerComboBox;
	private JComboBox timeComboBox;
	private JTextField doubleTextField;
	private JTextField integerTextField;
	private JTextField timeTextField;
	private JFormattedTextField dateTextField;
	private JSpinner dateSpinner;
	private DateFormat dateFormatter;

	private JTable eventTable;
	private TableColumn eventColumn;
	private TableColumn distanceColumn;
	private TableColumn timeColumn;
	private TableColumn locationColumn;

	private JComboBox eventList;
	private String [] eventNames = {"Swim","Bike","Run"};
	private String [] locationNames = {"Route A","Route B","Route C"};
}

public class test{
	public static void main(String [] args){
		ComboBoxTest cbt = new ComboBoxTest();
		cbt.pack();
		cbt.setVisible(true);
	}
}

