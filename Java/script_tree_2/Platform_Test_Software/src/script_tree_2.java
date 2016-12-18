import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.HeadlessException;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


class SCAFrame extends JFrame implements 	MouseListener,
                                                                                        ActionListener{

        private JMenuBar menuBar;
        private JMenu fileMenu;
        private JMenu helpMenu;
        private JMenuItem exitMenuItem;
        private JMenuItem saveResultsAs;
        private JMenuItem setupMenuItem;
        private JMenuItem layoutMenuItem;
        private DefaultTreeModel model;
        private JTree tree;
        private CheckBoxNode root;
        private JScrollPane treeScrollPane;
        private JScrollPane descriptionTextAreaScrollPane;
        private JScrollPane logTextAreaScrollPane;
        private JSplitPane horizontalSplitPane;
        private JSplitPane verticalSplitPane;
        private CustomEditor descriptionTextArea;
        private CustomEditor logTextArea;
        private JPanel panel;
        private JPanel statusPanel;
        private JPanel treePanel;
        private JPanel descriptionTextAreaPanel;
        private JPanel logTextAreaPanel;
        private JToolBar toolBar;
        private JLabel treeLabel;
        private JLabel descriptionLabel;
        private JLabel resultsLabel;
        private JLabel statusLabel;
        private Font arialFont;

        public SCAFrame(){

                setTitle("Test Platform GUI Prototype");
                arialFont = new Font("Arial",Font.PLAIN,12);

                menuBar = new JMenuBar();
                setUpMenuBar(menuBar);
                setJMenuBar(menuBar);

                toolBar = new JToolBar("Action Bar",JToolBar.HORIZONTAL);
                setUpToolBar(toolBar);

                descriptionTextArea = new CustomEditor();
                descriptionTextArea.setEditable(false);
                descriptionTextAreaScrollPane = new JScrollPane(descriptionTextArea);
                 descriptionTextAreaScrollPane.setMinimumSize(new Dimension(200,100));
                descriptionLabel = new JLabel("Test Node Description");
                descriptionLabel.setFont(arialFont);
                descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                descriptionTextAreaPanel = new JPanel();
                descriptionTextAreaPanel.setLayout(new BoxLayout(descriptionTextAreaPanel,BoxLayout.Y_AXIS));
                descriptionTextAreaPanel.add(descriptionLabel);
                descriptionTextAreaPanel.add(descriptionTextAreaScrollPane);

                logTextArea = new CustomEditor();
                logTextArea.setEditable(false);
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

                setUpTreeModel();
                setUpTree(model);

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

                Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
                statusPanel = new JPanel();
                statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.X_AXIS));
                statusPanel.setBorder(loweredBevelBorder);
                statusLabel = new JLabel("Application Started",SwingConstants.LEFT);
                statusLabel.setFont(arialFont);
                statusPanel.add(statusLabel);

                panel =  new JPanel(new BorderLayout());
                panel.add(toolBar,BorderLayout.NORTH);
                panel.add(horizontalSplitPane,BorderLayout.CENTER);
                panel.add(statusPanel,BorderLayout.SOUTH);

                getContentPane().add(panel);

            addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });

            addMouseListener(new MouseAdapter(){
                    public void mouseEntered(MouseEvent event){
                            statusLabel.setText("over a component");
                    }
            });
        }

        public void addMenuItem(JMenu menu, String label){

                JMenuItem item;
                item = new JMenuItem(label);
                item.setFont(arialFont);
                item.setActionCommand(label + "MenuItem");
                item.addActionListener(this);
                menu.add(item);
        }

        public void setUpMenuBar(JMenuBar menuBar){

                fileMenu = new JMenu("File");
                fileMenu.setFont(arialFont);
                addMenuItem(fileMenu,"Save Results As");
                fileMenu.addSeparator();
                addMenuItem(fileMenu,"Setup");
                fileMenu.addSeparator();
                addMenuItem(fileMenu,"Exit");

                helpMenu = new JMenu("Help");
                helpMenu.setFont(arialFont);
                helpMenu.addSeparator();
                addMenuItem(helpMenu,"About");

                menuBar.add(fileMenu);
                menuBar.add(helpMenu);
        }

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
        }

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

        public void addNodeToParent(CheckBoxNode parent, String title, String description){
                CheckBoxNode node = new CheckBoxNode(new TestObject(title,description));
                parent.add(node);
        }

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

        public void setUpTree(DefaultTreeModel model){

                tree = new JTree(model);
                tree.setShowsRootHandles(true);
                tree.setCellRenderer(new CheckRenderer(tree,new DefaultTreeCellRenderer()));
                tree.setCellEditor(new CheckEditor(tree,new CheckRenderer(tree,new DefaultTreeCellRenderer())));
                tree.setEditable(true);
                tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
                tree.addMouseListener(this);
        }

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

        public void invokeTest(CheckBoxNode node){



        }

        public void stopTesting(){


        }

        public void saveResults(){

                JFileChooser chooser = new JFileChooser();
                int option = chooser.showSaveDialog(this);
                if(option == JFileChooser.APPROVE_OPTION){
                        statusLabel.setText("File saved: " + ((chooser.getSelectedFile() != null)?
                                                                chooser.getSelectedFile().getName():"nothing"));
                }
                else{
                        statusLabel.setText("File save operation cancelled");
                }
        }


        /************************************
        **
        **     Listeners
        **
        **
        *************************************/

        public void mouseClicked(MouseEvent e){
        }

        public void mouseEntered(MouseEvent e){
        }

        public void mouseExited(MouseEvent e){
        }

        public void mouseReleased(MouseEvent e){
        }

        public void mousePressed(MouseEvent e){
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

                        if (obj.getDescription() != null){
                                descriptionTextArea.setText(obj.getDescription());
                        }
                        else{
                                descriptionTextArea.setText(null);
                        }

                        if(!node.isSelected()){
                                System.out.println("node - " + node.isSelected);
                                node.unselectAncesters();
                        }

                        tree.revalidate();
                        repaint();
                }
        }

    public void actionPerformed(ActionEvent event){
                String source = event.getActionCommand();
                System.out.println("pressed - " + source);
                tree.revalidate();
                repaint();

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
                                                                                        "Test Platform Tool<br>" +
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
                        tree.revalidate();
                        repaint();
                }
                else if(source.equals("Unselect AllToolBarButton")){
                        root.setSelected(false);
                        tree.revalidate();
                        repaint();
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

class CustomEditor extends JEditorPane{

        public CustomEditor(){
                super();
                setFont(new Font("Arial",Font.PLAIN,12));
                 setContentType("text/html");
        }

        public void setText(String s){
                super.setText(s);
        }
}


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
                panel.setPreferredSize(new Dimension(175,25));
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

class AncesterNode extends TestObject{

        public AncesterNode(String t, String d){
                super(t,d);
        }

}


class DescendentNode extends TestObject{
        public DescendentNode(String t, String d){
                super(t,d);
        }
}




public class script_tree_2{
        public static void main(String [] args){
                SCAFrame frame = new SCAFrame();
                frame.pack();
                Location loc = new Location(frame);
                loc.setLocation("nw");
                frame.setVisible(true);
        }
}

