/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

package afatds.app.ppt;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/**
*	This class represents the combobox model that is used to change a combobox list 
*	without generating repetitive triggers to the actionlistener everytime an item
*	is added to the combobox.
*	
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/
public class GenericComboBoxModel extends DefaultComboBoxModel{

 	private Object item;
 	private Vector list;
 	
	public GenericComboBoxModel(Vector v){
		list = new Vector(v);
	}
	
	public void addElement(Object anObject){
		list.add(anObject);
	    int length = getSize();
	    fireIntervalAdded(this, length-1, length-1);
	}
 
	public Object getElementAt(int index){
		return (Object)list.elementAt(index);
	}
	
	public int getIndexOf(Object anObject){
		return list.indexOf(anObject);
	}
	
 	public Object getSelectedItem(){
	 	return item;
 	}
 	
 	public int getSize(){
	 	return list.size();
 	}
 	
	public void insertElementAt(Object anObject, int index){
		list.insertElementAt(anObject,index);
	    fireIntervalAdded(this, index, index);
	}
	
	public void removeAllElements(){
		list.removeAllElements();
		int length = list.size();
		fireIntervalRemoved(this, length, length);
	}
	
	public void removeElement(Object anObject){
		int index = list.indexOf(anObject);
		list.removeElement(anObject);
		fireIntervalRemoved(this, index, index);
	}
	
	public void removeElementAt(int index){
		list.removeElementAt(index);
		fireIntervalRemoved(this, index, index);
	}
	
 	public void setSelectedItem(Object anObject){
	 	item = anObject;
 	}

}

