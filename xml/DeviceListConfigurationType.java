import java.util.Vector;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import org.w3c.dom .Document;


/**
 * DeviceListConfigurationType.  This class manages a list of device types.
 *
 * @author Clifton B. Hudson
 * @version 1.0 22 Mar 2004
 */
public class DeviceListConfigurationType{

	/**
	 * These string variables represent node names of interest 
	 * when the JTree is passed in.
	 */
	private final String DOMAINNAME = "DomainName";
	private final String IORSTRING = "IORString";
	private final String TYPES = "Types";
	private final String TYPE = "Type";
	private final String ID = "ID";
	private final String TYPENAME = "TypeName";
	private final String DEVICELIST = "DeviceList";
	private final String DEVICENAME = "DeviceName";

	/**
	 * This array represents the list of device-related node names.
	 */
	private final String [] nodeNameArray = {	ID,
															TYPENAME,
															DEVICELIST,
															DEVICENAME};
														
	/**
	 * These constants represent the ELEMENT-TYPE and TEXT-TYPE nodes.
	 */
	private final int ELEMENTNODE = 1;
	private final int TEXTNODE = 3;

	/**
	 * This list represents the list of all device types.
	 */
	private Vector list = new Vector();
	
	/**
	 * This represents the Domain Name common to all
	 * devices on the target.
	 */
	 private String domainName;
	
	/**
	 * This represents the IOR String common to all
	 * devices on the target.
	 */
	 private String IORString;
	
	/**
	 * Constructor.
	 */
	public DeviceListConfigurationType(JTree tree){
		domainName = "";
		IORString = "";
		processTree(tree);
	}
	
	/**
	 * This returns TRUE if the string passed in is in the
	 * array of valid node names.
	 */
	private boolean isValidNodeName(String s){
		boolean valid = false;
		for(int i=0; i<nodeNameArray.length; i++){
			if(s.equals(nodeNameArray[i])){
				valid = true;
				break;
			}
		}
		return valid;
	}
	
	/**
	 * This returns TRUE if the node passed in is an ELEMENT-TYPE node.
	 */
	private boolean isElementNode(Object n){
		if(((NodeAdapter)n).getType() == ELEMENTNODE){
			return true;
		}
		return false;
	}
	
	/**
	 * This returns TRUE if the node passed in is a TEXT-TYPE node.
	 */
	private boolean isTextNode(Object n){
		if(((NodeAdapter)n).getType() == TEXTNODE){
			return true;
		}
		return false;
	}
	
	/**
	 * This returns the device that was extracted from the node passed in.
	 */
	private DeviceType getDeviceType(Object n){
		
		String id = null;
		String typeName = null;
		String deviceName = null;
	
		DeviceType deviceType = new DeviceType();
		
		NodeAdapter parent = (NodeAdapter)n;
		int count = parent.getCount();
		
		/**
		 * For each child of the node passed in, determine its type and
		 * extract its value.
		 */
		for(int i=0; i<count; i++){
			
			NodeAdapter node = (NodeAdapter)parent.getChild(i);
			
			/**
			 * Is this an ELEMENT-TYPE node with a name in the 
			 * nodeNameArray and does it have a non-empty node value?
			 */
			if(isElementNode(node)
				&& isValidNodeName(node.getName())){
					
				/**
				 * ID
				 */
				if(node.getName().equals(ID)){
					
					id = node.getChild(0).getValue();
					deviceType.setID(id);
				}
				
				/**
				 * TypeName
				 */
				else if(node.getName().equals(TYPENAME)){
					
					typeName = node.getChild(0).getValue();
					deviceType.setName(typeName);
				}
				
				/**
				 * DeviceList
				 */
				else if(node.getName().equals(DEVICELIST)){
					
					NodeAdapter deviceListNode = (NodeAdapter)node;
					int nodeCount = deviceListNode.getCount();
					
					/**
					 * 
					 * Since this node has child nodes that represent the
					 * Devices under the DeviceType node, extract the names.
					 */
					for(int j=0; j<nodeCount; j++){
						
						if(deviceListNode.getChild(j).getName().equals(DEVICENAME)){
							deviceName = deviceListNode.getChild(j).getChild(0).getValue();
							deviceType.addDevice(deviceName);
						}
					}
				}
			}
		}
		return deviceType;
	}
	
	/**
	 * This extracts the configuration information from the JTree object passed in
	 */
	private void processTree(JTree tree){
		
		DeviceType deviceType = new DeviceType();
		
		/**
		 * Gain access to the model of the JTree passed to the constructor.
		 */
		TreeModelAdapter model = (TreeModelAdapter)tree.getModel();
		
		/**
		 * Get the root node of the JTree.
		 */
		NodeAdapter root = new NodeAdapter(model.getDocument().getDocumentElement());
		int count = root.getCount();
		
		/**
		 * For each child of the root node, determine its type and extract its 
		 * information as required.
		 */
		for(int i=0; i<count; i++){
			NodeAdapter node = (NodeAdapter)model.getChild(root,i);
			
			/**
			 * If the node name is "DomainName", extract and store the string.
			 */
			if(node.getName().equals(DOMAINNAME)){
				domainName = node.getChild(0).getValue();
			}
			
			/**
			 * If the node name is "IORStringPath", extract and store the string.
			 */
			if(node.getName().equals(IORSTRING)){
				IORString = node.getChild(0).getValue();
			}
			
			/** 
			 * If the node name is "Types", then a subtree with at least one
			 * device type has been found.  Attempt to extract its information.
			 */
			if(node.getName().equals(TYPES)){
				
				for(int j=0; j<node.getCount(); j++){
					NodeAdapter typeNode = (NodeAdapter)node.getChild(j);
					
					if(typeNode.getName().equals(TYPE)){
				
						deviceType = getDeviceType(typeNode);
						if(deviceType != null){
							list.add(deviceType);
						}
					}
				}
			}
		}
	}
	
	/**
	 * This returns the Domain Name.
	 */
	public String getDomainName(){
		return domainName;
	}
	
	/**
	 * This returns the IOR String.
	 */
	public String getIORString(){
		return IORString;
	}

	/**
	 * This returns the list of devices.
	 */	
	public Vector getList(){
		return list;
	}
	
	/**
	 * This is for debugging only.  It prints out the devices in the list.
	 */
	public void printList(){
		
		System.out.println("\nThere are " + list.size() + " devices in this list.\n");
		
		for(int i=0; i<list.size(); i++){
			DeviceType deviceType = (DeviceType)list.elementAt(i);
			System.out.println(deviceType.getID());
			System.out.println(deviceType.getName());
			for(int j=0; j<deviceType.getDeviceList().size(); j++){
				System.out.println(deviceType.getDevice(j));
			}
			System.out.println();
		}
	}
		
}
	
