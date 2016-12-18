import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JEditorPane;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.tree.TreeCellEditor;
import java.util.Vector;
import java.awt.Component;
import javax.swing.event.CellEditorListener;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.CellEditor;
import java.util.Hashtable;
import javax.swing.tree.TreeCellRenderer;
import java.util.Enumeration;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Insets;

class SCAFrame
    extends JFrame
    implements TreeSelectionListener,
    ActionListener {

  private JTree tree = null;
  private CheckBoxNode root = null;
  private JScrollPane scrollPane = null;
  private JSplitPane splitPane = null;
  private JEditorPane editorPane = null;
  private JPanel panel = null;
  private JToolBar toolBar = null;
  private String[] tests = {
      "test1", "test2", "test3"};

  public SCAFrame() {

    setTitle("Script Test GUI Prototype");

    root = new CheckBoxNode("SCA", null);
    CheckBoxNode test = new CheckBoxNode(tests[0],
                                         "running ....... c:\\script0.java");
    root.add(test);
    test.add(new CheckBoxNode(tests[1], "running ....... c:\\script1.java"));
    test.add(new CheckBoxNode(tests[2], "running ....... c:\\script2.java"));

    tree = new JTree(root);
    tree.setCellRenderer(new CheckRenderer());
    tree.setCellEditor(new CheckEditor());
    tree.setEditable(true);
    tree.addTreeSelectionListener(this);
    tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

    scrollPane = new JScrollPane(tree);
    scrollPane.setPreferredSize(new Dimension(200, 250));
    editorPane = new JEditorPane();
    editorPane.setPreferredSize(new Dimension(200, 250));

    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane,
                               editorPane);

    toolBar = new JToolBar("Action Bar", JToolBar.HORIZONTAL);
    toolBar.setFloatable(false);
    addToolBarButton(toolBar, false, "START", "START", null);
    toolBar.addSeparator();
    addToolBarButton(toolBar, false, "STOP", "STOP", null);
    toolBar.addSeparator();
    addToolBarButton(toolBar, false, "EXIT", "EXIT", null);

    panel = new JPanel(new BorderLayout());
    panel.add(toolBar, BorderLayout.NORTH);
    panel.add(splitPane, BorderLayout.CENTER);

    getContentPane().add(panel);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    tree.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if ( (selRow != -1) && (path != null)) {
          CheckBoxNode node = (CheckBoxNode) tree.getLastSelectedPathComponent();
          System.out.println("mouse - " + node.isSelected());

          boolean isSelected = !node.isSelected();
          node.setSelected(isSelected);

          if (node.scriptPath != null && node.isSelected()) {
            editorPane.setText(node.scriptPath);
          }
          else {
            editorPane.setText(null);
          }
          tree.revalidate();
          repaint();
        }
      }
    });
  }

  public void valueChanged(TreeSelectionEvent event) {

// 		if(event.getSource() == tree){
// 			TreePath path = tree.getSelectionPath();
// 			if(path != null){
// 				CheckBoxNode node = (CheckBoxNode)tree.getLastSelectedPathComponent();
//
// 				boolean isSelected = !node.isSelected();
// 				node.setSelected(isSelected);
//
// 				if (node.scriptPath != null && node.isSelected()){
// 					editorPane.setText(node.scriptPath);
// 				}
// 				else{
// 					editorPane.setText(null);
// 				}
// 				tree.revalidate();
// 				repaint();
// 			}
// 		}
  }

  public JButton addToolBarButton(JToolBar bar,
                                  boolean useImage,
                                  String text,
                                  String label,
                                  String help) {

    JButton b;

    if (useImage) {
      b = new JButton(new ImageIcon(label + ".gif"));
    }
    else {
      b = new JButton();
    }

    bar.add(b);

    if (text != null) {
      b.setText(text);
    }
    else {
      b.setMargin(new Insets(0, 0, 0, 0));
    }

    if (help != null) {
      b.setToolTipText(help);
    }

    b.setActionCommand(label + "_ToolBar");
    b.addActionListener(this);

    return b;
  }

  public void actionPerformed(ActionEvent event) {
    String source = event.getActionCommand();
    System.out.println("pressed - " + source);

    if (source.equals("EXIT_ToolBar")) {
      System.exit(0);
    }
  }
}

class CheckRenderer
    extends JCheckBox
    implements TreeCellRenderer {

  public CheckRenderer() {
    super();
    setBackground(Color.white);
  }

  public Component getTreeCellRendererComponent(JTree tree,
                                                Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf,
                                                int row,
                                                boolean hasFocus) {

    String title = tree.convertValueToText(value,
                                           isSelected,
                                           expanded,
                                           leaf,
                                           row,
                                           hasFocus);

    setSelected( ( (CheckBoxNode) value).isSelected());
    setText(title);
    return this;
  }

}

class CheckEditor
    extends JCheckBox
    implements TreeCellEditor {

  private Vector listeners = new Vector();

  public CheckEditor() {
    super();
  }

  public Component getTreeCellEditorComponent(JTree tree,
                                              Object value,
                                              boolean isSelected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row) {

    CheckBoxNode node = (CheckBoxNode) value;
    setSelected(node.isSelected());
    setText(node.toString());
    System.out.println("*********************************");
    System.out.println("editor - " + ( (CheckBoxNode) value).isSelected());
    return this;
  }

  public void addCellEditorListener(CellEditorListener l) {
    listeners.addElement(l);
  }

  public void cancelCellEditing() {
  }

  public Object getCellEditorValue() {
    System.out.println("getvalue");
    System.out.println("" + this.isSelected());
    return this;
  }

  public boolean isCellEditable(EventObject anEvent) {
    return true;
  }

  public void removeCellEditorListener(CellEditorListener l) {
    listeners.removeElement(l);
  }

  public boolean shouldSelectCell(EventObject anEvent) {
    return true;
  }

  public boolean stopCellEditing() {
    return true;
  }
}

class CheckBoxNode
    extends DefaultMutableTreeNode {

  protected String scriptPath;
  protected boolean isSelected;
  protected boolean propagate;

  public CheckBoxNode() {
    this(null, null, true, true);
  }

  public CheckBoxNode(Object userObject, String path) {
    this(userObject, path, true, false);
  }

  public CheckBoxNode(Object userObject, String path, boolean selected) {
    this(userObject, path, selected, false);
  }

  public CheckBoxNode(Object userObject, String path, boolean selected,
                      boolean propagate) {
    super(userObject, selected);
    this.isSelected = selected;
    this.propagate = propagate;
    scriptPath = path;
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
    propagate = selected;
    if (propagate) {
      selectChildren(propagate);
    }
  }

  public void selectChildren(boolean b) {
    if (children != null) {
      System.out.println("selectChildren - " + b);
      Enumeration enum = children.elements();
      while (enum.hasMoreElements()) {
        CheckBoxNode node = (CheckBoxNode) enum.nextElement();
        node.setSelected(b);
      }
    }
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setUserObject(Object obj) {
    if (obj == this) {
      return;
    }
    else {
      super.setUserObject(obj);
    }
  }
}

public class script_test {
  public static void main(String[] args) {
    SCAFrame frame = new SCAFrame();
    frame.pack();
    frame.setVisible(true);
  }
}
