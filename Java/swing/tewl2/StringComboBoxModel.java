import java.io.*;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import java.util.Vector;

/**
 * Defines a model for a time format of hh:mm:ss.
 */
public class StringComboBoxModel implements ComboBoxModel{

	public StringComboBoxModel(String [] s){
		list = new Vector();
		 
		for(int i=0; i<s.length; i++){
			list.add(s[i]);
		}
	}

	public Object getSelectedItem(){
		return (Object)item;
	}
		
	public void setSelectedItem(Object anItem){
		item = (String)anItem;
	}

	public void addListDataListener(ListDataListener l){

	}
	
	public Object getElementAt(int index){
		return (Object)list.elementAt(index);
	}

	public int getSize(){
		return list.size();
	}
	
	public void removeListDataListener(ListDataListener l){

	}

	private String item;
	private Vector list;
}
