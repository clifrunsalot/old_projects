import java.util.Vector;

/**
 * DeviceType.  This class manages the attributes of a device.
 *
 * @author Clifton B. Hudson
 * @version 1.0 22 Mar 2004
 */
public class DeviceType{
	
	private String ID;
	private String name;
	private Vector deviceList;
	
	/**
	 * Constructor
	 */
	public DeviceType(){
		ID = "";
		name = "";
		deviceList = new Vector();
	}
	
	/**
	 * Sets the ID for a device.
	 */
	public void setID(String i){
		ID = i;
	}
	
	/**
	 * Sets the name for a device type.
	 */
	public void setName(String n){
		name = n;
	}
	
	/**
	 * Adds a device to the list of device types.
	 */
	public void addDevice(String d){
		deviceList.add(d);
	}
	
	/**
	 * Returns the ID of a device.
	 */
	public String getID(){
		return ID;
	}
	
	/**
	 * Returns the name of the device type.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the list of devices for the device type.
	 */
	public Vector getDeviceList(){
		return deviceList;
	}
	
	/**
	 * Returns the name of the device located at the given index of the 
	 * internal device list.
	 */
	public String getDevice(int i){
		return (String)deviceList.elementAt(i);
	}
}
		