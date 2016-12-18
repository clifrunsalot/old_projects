import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * SCAFrame.  This is the class that produces and manages the user interface.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class SCAFrame extends JFrame implements 	MouseListener,
														ActionListener{
												
	private CheckBoxNode root;
	private CustomEditor descriptionTextArea;
	private CustomEditor logTextArea;
	private DefaultTreeModel model;
	private Font arialFont;
	private JLabel descriptionLabel;
	private JLabel resultsLabel;
	private JLabel statusLabel;
	private JLabel treeLabel;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuBar menuBar;
	private JMenuItem exitMenuItem;
	private JMenuItem layoutMenuItem;
	private JMenuItem saveResultsAs;
	private JMenuItem setupMenuItem;
	private JPanel descriptionTextAreaPanel;
	private JPanel logTextAreaPanel;
	private JPanel panel;
	private JPanel statusPanel;
	private JPanel treePanel;
	private JScrollPane descriptionTextAreaScrollPane;
	private JScrollPane logTextAreaScrollPane;
	private JScrollPane treeScrollPane;
	private JSplitPane horizontalSplitPane;
	private JSplitPane verticalSplitPane;
	private JToolBar toolBar;
	private JTree tree;

	/**
	 * Constructor
	 *
	 */
	public SCAFrame(){
		
		setTitle("Platform Test GUI Prototype");
		arialFont = new Font("Arial",Font.PLAIN,12);
		
		/**
		 * Setup the menubar
		 */
		menuBar = new JMenuBar();
		setUpMenuBar(menuBar);
		setJMenuBar(menuBar);
		
		/**
		 * Setup the toolbar
		 */
		toolBar = new JToolBar("Action Bar",JToolBar.HORIZONTAL);
		setUpToolBar(toolBar);
		
		/**
		 * Setup the textarea that will display information about the
		 * currently selected node.
		 */
		descriptionTextArea = new CustomEditor();
		descriptionTextArea.setEditable(false);
		descriptionTextArea.addMouseListener(this);
		descriptionTextArea.setName("Test Node Description");
		descriptionTextAreaScrollPane = new JScrollPane(descriptionTextArea);
 		descriptionTextAreaScrollPane.setMinimumSize(new Dimension(200,100));
		descriptionLabel = new JLabel("Test Node Description");
		descriptionLabel.setFont(arialFont);
		descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		descriptionTextAreaPanel = new JPanel();
		descriptionTextAreaPanel.setLayout(new BoxLayout(descriptionTextAreaPanel,BoxLayout.Y_AXIS));
		descriptionTextAreaPanel.add(descriptionLabel);
		descriptionTextAreaPanel.add(descriptionTextAreaScrollPane);
		
		/**
		 * Setup the textarea that will display a log of the test
		 * results for the selected node(s).
		 */
		logTextArea = new CustomEditor();
		logTextArea.setEditable(false);
		logTextArea.addMouseListener(this);
		logTextArea.setName("Test Results Log");
		logTextAreaScrollPane = new JScrollPane(logTextArea);
 		logTextAreaScrollPane.setMinimumSize(new Dimension(200,100));
		resultsLabel = new JLabel("Test Results Log");
		resultsLabel.setFont(arialFont);
		resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		logTextAreaPanel = new JPanel();
		logTextAreaPanel.setLayout(new BoxLayout(logTextAreaPanel,BoxLayout.Y_AXIS));
		logTextAreaPanel.add(resultsLabel);
		logTextAreaPanel.add(logTextAreaScrollPane);
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,descriptionTextAreaPanel,logTextAreaPanel);
				
		/**
		 * Setup and populate the treemodel
		 */
		setUpTreeModel();
		setUpTree(model);
		
		/**
		 * Setup the panel that will contain the JTree.
		 */
		treePanel = new JPanel();
		treeLabel = new JLabel("Test");
		treeLabel.setFont(arialFont);
		treePanel.setLayout(new BoxLayout(treePanel,BoxLayout.Y_AXIS));
		treeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		treeScrollPane = new JScrollPane(tree);
		treeScrollPane.setMinimumSize(new Dimension(200,200));
		treePanel.add(treeLabel);
		treePanel.add(treeScrollPane);
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treePanel,verticalSplitPane);
		horizontalSplitPane.setPreferredSize(new Dimension(600,400));

		/**
		 * Setup the status panel.
		 */
		Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.X_AXIS));
		statusPanel.setBorder(loweredBevelBorder);
		statusLabel = new JLabel("Application Started",SwingConstants.LEFT);
		statusLabel.setFont(arialFont);
		statusPanel.add(statusLabel);
		
		/**
		 * Add all of the panels created above to the backmost panel 
		 * add that one to Frame
		 */
		panel =  new JPanel(new BorderLayout());
		panel.add(toolBar,BorderLayout.NORTH);
		panel.add(horizontalSplitPane,BorderLayout.CENTER);
		panel.add(statusPanel,BorderLayout.SOUTH);

		getContentPane().add(panel);
				
		/**
		 * Add a listener to gracefully exit the GUI whenever the user
		 * clicks on the "x" icon in the upper right corner of the
		 * window.
		 */
	   addWindowListener(new WindowAdapter(){
	       public void windowClosing(WindowEvent e){
	           System.exit(0);
	       }
	   });	
	}

	/**
	 * Adds an item to a menu.
	 */
	public void addMenuItem(JMenu menu, String label){
		
		JMenuItem item;
		item = new JMenuItem(label);
		item.setFont(arialFont);
		item.setActionCommand(label + "MenuItem");
		item.addActionListener(this);
		item.addMouseListener(this);
		menu.add(item);
	}
	
	public void addMenu(JMenuBar menuBar, JMenu menu, String [] items){
		
		menu.setFont(arialFont);
		menu.addMouseListener(this);
		
		for(int i=0; i<items.length; i++){
			addMenuItem(menu,items[i]);
			menu.addSeparator();
		}
		
		menuBar.add(menu);
	}
	
	/**
	 * Sets up the menubar.
	 */
	public void setUpMenuBar(JMenuBar menuBar){
		
		fileMenu = new JMenu("File");
		String [] fileMenuItems = {"Save Results As","Setup","Exit"};
		addMenu(menuBar,fileMenu,fileMenuItems);

		helpMenu = new JMenu("Help");
		String [] helpMenuItems = {"About"};
		addMenu(menuBar,helpMenu,helpMenuItems);
	}
	
	/**
	 * Add an item to the toolbar
	 */
	public void addToolBarButton(JToolBar bar,
									boolean useImage,
									String label,
									String help){
										
		JButton b;
		
		if(useImage){
			b = new JButton(new ImageIcon(label + ".gif"));
		}
		else{
			b = new JButton(label);
		}
		
		b.setFont(arialFont);
		bar.add(b);
		
		if(help != null){
			b.setToolTipText(help);
		}
		
		b.setActionCommand(label +"ToolBarButton");
		b.addActionListener(this);
		b.addMouseListener(this);
	}
	
	/**
	 * Sets up the toolbar.
	 */
	public void setUpToolBar(JToolBar toolBar){
		toolBar.setFloatable(false);
		addToolBarButton(toolBar,false,"Select All",null);
		toolBar.addSeparator();
		addToolBarButton(toolBar,false,"Unselect All",null);
		toolBar.addSeparator();
		addToolBarButton(toolBar,false,"Start",null);
		toolBar.addSeparator();
		addToolBarButton(toolBar,false,"Stop",null);
		toolBar.addSeparator();
	}
	
	/**
	 * Adds an array of child nodes to a specified parent node.
	 */
	public void addNodeToParent(CheckBoxNode parent, String searchString, String [] nodeName, String [] description){
	
		Enumeration_loop:
		for(Enumeration children = parent.children();children.hasMoreElements();){
			
			CheckBoxNode node = (CheckBoxNode)children.nextElement();
			TestObject obj = (TestObject)(node.getUserObject());
			
			if(obj.getTitle().equals(searchString)){
				
				for(int i=0; i<nodeName.length; i++){
					addNodeToParent(node,nodeName[i],description[i]);
				}
				
				break Enumeration_loop;
			}
		}
	}
	
	/**
	 * Adds a child node to a parent.
	 */
	public void addNodeToParent(CheckBoxNode parent, String title, String description){
		CheckBoxNode node = new CheckBoxNode(new TestObject(title,description));
		parent.add(node);
	}
	
	/**
	 * Sets up the tree model.
	 */
	public void setUpTreeModel(){
		
		String [] categories = {"Audio","FPGA","Modem","Red and Black","Red and Black GPP","Serial","Test APs","Transceiver"};
		String [] audioTests = {"Audio ACR","Audio Thales"};
		String [] audioTestDescriptions = {"Audio Test1", "Audio Test2"};
		String [] modemTests = {"ISWAPI","MI RTOS","Resource Adapter"};
		String [] modemTestDescriptions = {"Modem Test1", "Modem Test2", "Modem Test3"};
		String [] testAPsTests = {"Black GPP","DSP","Red GPP"};
		String [] testAPsTestsDescriptions = {"Test1", "Test2", "Test3"};
		
		root = new CheckBoxNode(new TestObject("Device Manager",null));
		
		for(int i=0; i<categories.length; i++){
			addNodeToParent(root,categories[i],null);
		}
		
		addNodeToParent(root,categories[0],audioTests,audioTestDescriptions);
		addNodeToParent(root,categories[2],modemTests,modemTestDescriptions);
		addNodeToParent(root,categories[6],testAPsTests,testAPsTestsDescriptions);
		
		model = new DefaultTreeModel(root);
	}
	
	/**
	 * Sets up the tree object with the given tree model.
	 */
	public void setUpTree(DefaultTreeModel model){
		
		tree = new JTree(model);
		tree.setShowsRootHandles(true);
		tree.setCellRenderer(new CheckRenderer(tree,new DefaultTreeCellRenderer()));
		tree.setCellEditor(new CheckEditor(tree,new CheckRenderer(tree,new DefaultTreeCellRenderer())));
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		tree.addMouseListener(this);
	}
	
	/**
	 * Invokes the testing of the selected nodes on the tree.  This is a 
	 * recursive method because a node may or may not be a parent node.
	 */
	public void startTesting(CheckBoxNode parent){
		
		Enumeration_loop:
		for(Enumeration children = parent.children();children.hasMoreElements();){
			
			CheckBoxNode node = (CheckBoxNode)children.nextElement();
			TestObject obj = (TestObject)(node.getUserObject());
			System.out.println(obj.getTitle() + " - " + obj.getTestThis());
			invokeTest(node);
			startTesting(node);
		}
	}
	
	/**
	 * Invokes a test on the node.
	 */
	public void invokeTest(CheckBoxNode node){
		
		
		
	}
	
	/**
	 * Stops testing of the selected node(s).
	 */
	public void stopTesting(){
		
		
	}
	
	/**
	 * Writes the contents of the logTextArea to a file.
	 */
	public void saveResults(){
		
		/**
		 * CustomFilter.  This is an inner class that manages the kinds of 
		 * files that will be displayed.  Currently, only "txt" files will be
		 * displayed.  Adjust the displayableFileTypes and description as
		 * necessary.
		 */
		class CustomFilter extends FileFilter{
			final String [] displayableFileTypes = {"txt"};
			final String description = "*.txt (Text Files)";
			
			public CustomFilter(){
				super();
			}
			
			public boolean accept(File f){
				if(f.isDirectory()){
					return true;
				}
				else{
				
					String fileName = f.getName();
					int len = fileName.length();
					int dot = 0;
					if((dot = fileName.lastIndexOf(".")) > 0 && dot < len){
						String extension = fileName.substring(dot+1);
						for(int i=0; i<displayableFileTypes.length; i++){
							if(extension.toLowerCase().equals(displayableFileTypes[i])){
								return true;
							}
						}
					}
				}
				return false;
			}
			
			public String getDescription(){
					return description;
			}
		}
		
		CustomFilter filter = new CustomFilter();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		int option = chooser.showSaveDialog(this);
		boolean extensionAdded = false;
		if(option == JFileChooser.APPROVE_OPTION){
			
			try{
				String path = chooser.getSelectedFile().getPath();
				int len = path.length();
				int dot = 0;
				
				/**
				 * Check if the user entered a ".txt" extension.  If not,
				 * add it for the user.
				 */
				if(((dot = path.lastIndexOf(".txt")) > 0) && (dot < len)){
					if(path.substring(dot+1,len).toLowerCase().equals("txt")){
						extensionAdded = true;
					}
				}
				else{
					path += ".txt";
					extensionAdded = true;
				}
				
				if(extensionAdded){
					BufferedWriter fout = new BufferedWriter(new FileWriter(new File(path)));
					logTextArea.write(fout);
					fout.close();
				}
				statusLabel.setText("File saved: " + ((chooser.getSelectedFile() != null)?
									path:"nothing"));
			} 
			catch(IOException e){
				JOptionPane.showMessageDialog(this,
											"Unable to save file.  Be sure to select a valid file name\n" +
											"and directory in which to save the results.",
											"Error", 
											JOptionPane.ERROR_MESSAGE,
											null);
			}
		}
		else{
			statusLabel.setText("File save operation cancelled");
		}
		
	}
	
	
	/********************
	 *
	 * Listeners
	 *
	 *
	 *******************/

	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	/**
	 * Depending on where the mouse cursor is located,
	 * show some descriptive text in the status panel.
	 */	
	public void mouseEntered(MouseEvent event){
		Component c = event.getComponent();
		String text = " ";
		if(c instanceof JMenu)
			text = ((JMenu)c).getText();
		else if(c instanceof JMenuItem)
			text = ((JMenuItem)c).getText();
		else if(c instanceof JButton)
			text = ((JButton)c).getText();
		else if(c instanceof CustomEditor)
			text = ((CustomEditor)c).getName();
		else if(c instanceof JPanel)
			text = ((JPanel)c).getName();
			
		if(text != null)
			statusLabel.setText(text);
			
	}

	/**
	 * As the mouse cursor leaves a component, reset
	 * the status panel.
	 */	
	public void mouseExited(MouseEvent event){
		Component c = event.getComponent();
		if(c != null)
			statusLabel.setText(" ");
	}
	
	/**
	 * This listener handles any tree-related activity
	 */	
	public void mousePressed(MouseEvent e){
		if(e.getComponent() instanceof JTree){
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			System.out.println("path - " + selPath);
			if(selPath != null) {
				tree.setSelectionPath(selPath);
				CheckBoxNode node = (CheckBoxNode)tree.getLastSelectedPathComponent();
				TestObject obj = (TestObject)node.getUserObject();
				
				if (node == null) return;
				
				boolean isSelected = !node.isSelected();
				node.setSelected(isSelected);
				obj = (TestObject)node.getUserObject();
				descriptionTextArea.setText(obj.getDescription());
	
				/**
				 * This block of code unselects all ancester nodes since
				 * selecting an ancestor node means that all decendents
				 * will be selected.
				 *
				 */			
				if(!node.isSelected()){
					node.unselectAncesters();
				}
				
				tree.revalidate();
				repaint();
			}
		}
	}
	
    public void actionPerformed(ActionEvent event){
		String source = event.getActionCommand();
		
		if(source.equals("ExitMenuItem")){
			System.exit(0);
		}
		else if(source.equals("Save Results AsMenuItem")){
			saveResults();
			
		}
		else if(source.equals("SetupMenuItem")){
			try{
				JOptionPane.showMessageDialog(this,
											"This feature is not implemented yet", 
											"About", 
											JOptionPane.INFORMATION_MESSAGE,
											null);
			}
			catch(HeadlessException e){
				System.out.println("Unable to process menu request");
			}
		}
		else if(source.equals("AboutMenuItem")){
			
			JLabel messageLabel = new JLabel("<HTML>Raytheon Network Centric Systems<br>" +
											"Platform Test Tool<br>" +
											"Copyright 2004</HTML>");
			messageLabel.setFont(arialFont);
											
			try{
				JOptionPane.showMessageDialog(this,
											messageLabel, 
											"About", 
											JOptionPane.INFORMATION_MESSAGE,
											null);
			}
			catch(HeadlessException e){
				System.out.println("Unable to process menu request");
			}
		}
		else if(source.equals("Select AllToolBarButton")){
			root.setSelected(true);
		}
		else if(source.equals("Unselect AllToolBarButton")){
			root.setSelected(false);
		}
		else if(source.equals("StartToolBarButton")){
			
			System.out.println("---------------");
			TestObject topNode = (TestObject)root.getUserObject();
			System.out.println(topNode.getTitle() + " - " + topNode.getTestThis());
			startTesting(root);
			
			try{
				JOptionPane.showMessageDialog(this,
											"This feature is not implemented yet", 
											"About", 
											JOptionPane.INFORMATION_MESSAGE,
											null);
			}
			catch(HeadlessException e){
				System.out.println("Unable to process menu request");
			}
		}
		else if(source.equals("StopToolBarButton")){
			stopTesting();
			try{
				JOptionPane.showMessageDialog(this,
											"This feature is not implemented yet", 
											"About", 
											JOptionPane.INFORMATION_MESSAGE,
											null);
			}
			catch(HeadlessException e){
				System.out.println("Unable to process menu request");
			}
		}
	}
}

/**
 * CustomEditor.  This class creates a standard JEditorPane with default 
 * characteristics.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class CustomEditor extends JEditorPane{
	
	public CustomEditor(){
		super();
		setFont(new Font("Arial",Font.PLAIN,12));
 		setContentType("text/plain");
	}
	
	/**
	 * Use this method to format the logTextArea as necessary
	 */
	public void setText(String s){
		super.setText(s);
	}
}


/**
 * CheckRenderer.  This class defines how each node on the tree will be 
 * displayed.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class CheckRenderer implements TreeCellRenderer{
	
	private Font arialFont = new Font("Arial",Font.PLAIN,12);
	private ImageIcon clearBall = new ImageIcon("clear_ball.gif");
	private ImageIcon yellowBall = new ImageIcon("yellow_ball.gif");
	private ImageIcon redBall = new ImageIcon("red_ball.gif");
	private ImageIcon greenBall = new ImageIcon("green_ball.gif");
	private JPanel panel;
	private JLabel label;
	protected CheckBoxNode node;
	protected TreeCellRenderer renderer;
	protected JCheckBox checkBox;
	
	public CheckRenderer(JTree tree){
		this(tree, new DefaultTreeCellRenderer());
	}

	public CheckRenderer(JTree tree, TreeCellRenderer renderer){
		super();
		this.renderer = renderer;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		checkBox = new JCheckBox();
		checkBox.setFont(arialFont);
		checkBox.setBorderPaintedFlat(true);
 		checkBox.setOpaque(false);
		label = new JLabel();
		panel.add(label);
		panel.add(checkBox);
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(175,20));
	}
	
	public Component getTreeCellRendererComponent(JTree tree,
													Object value,
													boolean isSelected,
													boolean expanded,
													boolean leaf,
													int row,
													boolean hasFocus){
														
		if(value instanceof CheckBoxNode){
			node = (CheckBoxNode)value;
			checkBox.setText(node.toString());
			checkBox.setSelected(node.isSelected());
			value = node.getUserObject();
			TestObject obj = (TestObject)value;
			
			if(obj.getTestThis()){
				if(obj.isRunning()){
					label.setIcon(yellowBall);
				}
				else{
					if(obj.isSuccessful()){
						label.setIcon(greenBall);
					}
					else{
						label.setIcon(redBall);
					}
				}
			}
			else{
				label.setIcon(clearBall);
			}		
		}
			
		renderer.getTreeCellRendererComponent(tree, 
												value, 
												isSelected, 
												expanded, 
												leaf, 
												row, 
												hasFocus);
		return panel;
	}
}


/**
 * CheckEditor.  This class defines how the JCheckBox at the node will behave
 * when the node is selected.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class CheckEditor extends CheckRenderer implements TreeCellEditor, ActionListener{
	
 	private Vector listeners = new Vector();
 	
	public CheckEditor(JTree tree){
		this(tree, new CheckRenderer(tree, new DefaultTreeCellRenderer()));
	}

	public CheckEditor(JTree tree, CheckRenderer renderer){
		super(tree, renderer);
		checkBox.addActionListener(this);
	}
	
	public Component getTreeCellEditorComponent(JTree tree,
													Object value,
													boolean isSelected,
													boolean expanded,
													boolean leaf,
													int row){
														
		return getTreeCellRendererComponent(tree, 
											value, 
											true, 
											expanded, 
											leaf, 
											row, 
											true);
	}
	
	public void cancelCellEditing(){
 		fireEditingCanceled();
	}
 
	public Object getCellEditorValue(){
	    return node;
	}
 
	public boolean isCellEditable(EventObject anEvent){
		return true;
	}
	
	public void addCellEditorListener(CellEditorListener l){
		listeners.addElement(l);
	}
		
	public void removeCellEditorListener(CellEditorListener l){
		listeners.removeElement(l);
	}
 
	public boolean shouldSelectCell(EventObject anEvent){
		return true;
	}

 	public boolean stopCellEditing(){
	 	return true;
	}
	
	protected void fireEditingStopped(){
		if (listeners != null){
			for(int i=0; i<listeners.size(); i++){
				CellEditorListener element = (CellEditorListener)listeners.elementAt(i);
				element.editingStopped(new ChangeEvent(this));
			}
		}
	}

	protected void fireEditingCanceled(){
		if (listeners != null){
			for(int i=0; i<listeners.size(); i++){
				CellEditorListener element = (CellEditorListener)listeners.elementAt(i);
			element.editingCanceled(new ChangeEvent(this));
			}
		}
	}

	public void actionPerformed(ActionEvent event){
		fireEditingStopped();
	}
}

/**
 * CheckBoxNode.  This class defines the object that will represent a node
 * on the JTree.  It manages the actual test object.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class CheckBoxNode extends DefaultMutableTreeNode{
	
	protected boolean isSelected;
	protected boolean propagate;
	
	public CheckBoxNode(Object userObject){
		this(userObject, true, false);
	}
	
	public CheckBoxNode(Object userObject, boolean allowsChildren, boolean isSelected){
		super(userObject, allowsChildren);
		this.isSelected = isSelected;
		this.propagate = isSelected;
	}
	
	public void setSelected(boolean selected){
		this.isSelected = selected;
		TestObject obj = (TestObject)getUserObject();
		obj.testThis(selected);
		selectChildren(selected);
	}
	
	public void selectChildren(boolean b){
		if(children != null){
			Enumeration enm = children.elements();
			while(enm.hasMoreElements()){
				CheckBoxNode node = (CheckBoxNode)enm.nextElement();
				TestObject obj = (TestObject)getUserObject();
				obj.testThis(b);
				node.setSelected(b);
			}
		}
	}
		
	public boolean isSelected(){
		return isSelected;
	}
	
	public void unselectAncesters(){
		if(!isSelected){
			CheckBoxNode node = (CheckBoxNode)getParent();
			if(node != null){
				node.isSelected = false;
				TestObject obj = (TestObject)node.getUserObject();
				obj.testThis(false);
				node.unselectAncesters();
			}
		}
	}
	
	public void setUserObject(Object obj){
		if(obj == this){
			return;
		}
		else{
			super.setUserObject(obj);
		}
	}
	
	public Object getUserObject(){
		return this.userObject;
	}
}

/**
 * TestObject.  This class represents the test object.  Each node on the 
 * JTree will encapsulate an object of the CheckBoxNode class, which in
 * turn holds an object of this class.  This class represents all of
 * the traits of a test object.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class TestObject{
	protected String title;
	protected String description;
	protected String path;
	protected String output;
	protected boolean testThis;
	protected boolean successful;
	protected boolean isRunning;
	
	public TestObject(String t, String d){
		title = t;
		description = d;
		path = "";
		output = "";
		testThis = false;
		isRunning = false;
		successful = false;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getPath(){
		return path;
	}
	
	public String getOutput(){
		return output;
	}
	
	public boolean getTestThis(){
		return testThis;
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	public boolean isSuccessful(){
		return successful;
	}
	
	public void testThis(boolean b){
		testThis = b;
	}
	
	public void setOutput(String o){
		output = o;
	}
	
	public void setIsRunning(boolean r){
		isRunning = r;
	}
	
	public void setSuccessful(boolean b){
		successful = b;
	}
	
	public String toString(){
		return title;
	}
}

/**
 * AncesterNode.  TBD
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class AncesterNode extends TestObject{
	
	public AncesterNode(String t, String d){
		super(t,d);
	}
	
}
		
		
/**
 * DescendentNode.  TBD
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
class DescendentNode extends TestObject{
	public DescendentNode(String t, String d){
		super(t,d);
	}
}
		
		
		
/**
 * script_test.  This is the class that invokes the GUI.
 *
 * @author Clifton Hudson
 * @version 1.0 11 Mar 2004  Initial Release
 */
public class script_test{
	public static void main(String [] args){
		SCAFrame frame = new SCAFrame();
		frame.pack();
		Location loc = new Location(frame);
		loc.setLocation("nw");
		frame.setVisible(true);
	}
}

