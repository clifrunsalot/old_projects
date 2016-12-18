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


/**
*	This class manages a network protocol.
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/
class Protocol
{
	/**
	*	This is a variable of the MyPrint class.  Use this for debugging purposes only.  See the MyPrint class for usages.
	*/
	private MyPrint p;
	
	/**
	*	This is the name of protocol.
	*/
	private String name;
	
	/**
	*	This is a Vector list of objects.
	*/
	private Vector objects;

	
	/**
	*	This constructs an object of the class.
	*/
	public Protocol(String nm)
	{
		name = new String(nm);
		objects = new Vector();
		p = new MyPrint();
	}
	
	/**
	*	Returns name of the protocol object.
	*	
	*	@return The name of protocol
	*/
	public String getName()
	{
		return name;
	}
	
	/**
	*	Adds an object to the protocol.
	*	
	*	@param object is an EnumerationType object
	*	
	*	@return void
	*/
	public void add(EnumerationType object)
	{
		objects.add(object);
	}
	
	/**
	*	Gets the objects associated with this protocol class.
	*	
	*	@return A vector of objects
	*/
	public Vector getObjects()
	{
		return objects;
	}
	
	/**
	*	Uses the MyPrint Class to print the protocol object.
	*	
	*	@return void
	*/
	public void print()
	{
		p.print("name: " + name);
		for(int i=0; i<objects.size(); i++)
		{
			if(objects.elementAt(i) instanceof EnumerationType)
			{
				((EnumerationType)objects.elementAt(i)).print();
			}
		}
	}
	
}