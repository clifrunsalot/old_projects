import java.util.Vector;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.w3c.dom .Document;
import org.w3c.dom.Node;


/**
 * TreeModelAdapter.  This class is a special version of the TreeModel because
 * it is designed to handle creating a JTree object based on a Document object
 * passed to its constructor.  The only difference between this and an object
 * of the DefaultTreeModel class is that this class has a document element 
 * as its root node.  Otherwise, its methods resemble much of the same
 * functionality of the DefaultTreeModel.
 *
 * @author Clifton Hudson
 * @version 1.0 22 Mar 2004
 */
class TreeModelAdapter implements TreeModel{
	
	/**
	 * This is a local copy of the document passed to the
	 * constructor.
	 */
	private Document document;
	
	/**
	 * Constructor.
	 */
	public TreeModelAdapter(Document d){
		document = d;
	}
	
	/**
	 * This returns the local document object.
	 */
	public Document getDocument(){
		return document;
	}
	
	/**
	 * This returns the child node located at the given index of the 
	 * given parent node.
	 */
	public Object getChild(Object parent, int index){
		NodeAdapter node = (NodeAdapter)parent;
		return node.getChild(index);
	}
	
	/**
	 * This returns the number of child nodes belonging to the given 
	 * parent node.
	 */
	public int getChildCount(Object parent){
		NodeAdapter node = (NodeAdapter)parent;
		return node.getCount();
	}
	
	/**
	 * This returns the index of the child node belonging to the given 
	 * parent node.
	 */
	public int getIndexOfChild(Object parent, Object child){
		NodeAdapter node = (NodeAdapter)parent;
		return node.getIndex((NodeAdapter)child);
	}
	
	/**
	 * This returns the root element of the local document object.
	 */
	public Object getRoot(){
		return new NodeAdapter(document);
	}
	
	/**
	 * Returns TRUE if the node passed in has no child nodes.
	 */
	public boolean isLeaf(Object n){
		NodeAdapter node = (NodeAdapter)n;
		if(node.getCount() > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * These are methods that are required by the interface.
	 * If this class were to be used as part of the JTree object
	 * in a GUI that required the visual manipulation of its
	 * nodes, these methods would need to be fully implemented and
	 * a class-wide listener list would have to be added to manage
	 * the listeners.
	 */
	public void valueForPathChanged(TreePath path, Object newValue){}
	public void removeTreeModelListener(TreeModelListener l){}
	public void addTreeModelListener(TreeModelListener l){}
		
}