/*
 * COMPANY NAME: Raytheon Company
 * COPYRIGHT: Copyright (c) 2004 Raytheon Company
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: AFATDS
 * CONTRACT NUMBER: DAAB07-C-E708
 */

package afatds.app.ppt;

import javax.swing.UIManager;

/**
*	This class invokes ProtocolTestDriver application that the operator will use to test protocols.
*
*	@author Clif Hudson AV7-3928
*   @version AV7-3928 04 Feb 2004 Intial Release
*/
public class test_protocol
{
    /**
    *	Main procedure
    *	
    *	@param args Arguments to start program
    *	@return void
    */
	public static void main(String [] args)
	{
        try
        {
            UIManager.setLookAndFeel
            (UIManager.getSystemLookAndFeelClassName());
			ProtocolTestDriver protocolTestDriver = new ProtocolTestDriver();
			protocolTestDriver.pack();
			protocolTestDriver.setVisible(true);
			Location app = new Location(protocolTestDriver);
			app.setLocation("nw");
        }
        catch(Exception e)
        {
			System.err.println("Program error.");
        }
	}

}