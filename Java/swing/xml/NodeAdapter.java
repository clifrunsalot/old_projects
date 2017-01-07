import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.Node;


/**
 * NodeAdapter.  This class manages the attributes of a node.
 *
 * @author Clifton B. Hudson
 * @version 1.0 22 Mar 2004
 */
public class NodeAdapter{
	
	/**
	 * This is a local copy of the node passed to the constructor.
	 */
	protected org.w3c.dom.Node node;
	
	/**
	 * This array represents the possible node types.
	 */
	static final String [] typeName = {	"none",
													"Element",
													"Attr",
													"Text",
													"CDATA",
													"EntityRef",
													"Entity",
													"ProcInstr",
													"Comment",
													"Document",
													"DocType",
													"DocFragment",
													"Notation"};
	
	/**
	 * Constructor.
	 */
	public NodeAdapter(org.w3c.dom.Node n){
		node = n;
	}
	
	/**
	 * This converts the value of the local node object to a string.
	 * This is used for display purposes when the nodes are displayed in 
	 * a JTree object.
	 */
	public String toString(){
		
		String s = null;
		
		/**
		 * This covers ELEMENT-TYPE nodes.
		 * Get the node name, trim off the white space, and 
		 * copy it into s.
		 */
		if(!getName().startsWith("#")){
			s = getName().trim();
			return s;
		}

		/**
		 * This covers TEXT-TYPE nodes.
		 * Get the node value, trim off the white space, get the 
		 * substring between 0 and the first newline, and
		 * copy it into s.
		 */			
		if(getValue() != null){
			s = getValue().trim();
			return s;		
      }
		return s;
	}

	/**
	 * This returns the value of the local node object.  Be sure to use
	 * the trim() method to trim off any white space characters before
	 * returning the string.
	 */	
	public String getValue(){
		if(node.getNodeValue() != null)
			return node.getNodeValue().trim();
		return "";
	}
	
	/**
	 * This returns the integer representation of local node object.
	 */
	public int getType(){
		return node.getNodeType();
	}
	
	/**
	 * This returns the name of the local node object.
	 */
	public String getName(){
		return node.getNodeName().toString();
	}
	
	/**
	 * This return the index of the given node.  (-1) means that the node
	 * doesn't exist.
	 */
	public int getIndex(NodeAdapter n){
		for(int i=0; i<this.getCount(); i++){
			NodeAdapter child = this.getChild(i);
			if(child.node == n.node){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * This returns the child node located at the given index of the
	 * local node object.
	 */
	public NodeAdapter getChild(int index){
		org.w3c.dom.Node n = node.getChildNodes().item(index); 
		return new NodeAdapter(n);
	}

	/**
	 * This returns the number of child nodes belonging to the local
	 * node object.
	 */	
	public int getCount(){
		return node.getChildNodes().getLength();
	}
	
}


