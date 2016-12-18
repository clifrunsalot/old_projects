import java.io.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;import java.util.*;


class MyProcess
{
	public MyProcess(String s)
	{
		name = new String(s);
		heartbeatRequired = false;
		origHeartbeatRequired = false;
		commentedOut = false;
		origCommentedOut = false;
	}
	
	public MyProcess()
	{
	}

	public void setName(String s)
	{
		name = s;
	}

	public String getName()
	{
		return name;
	}

	public void setHeartbeatRequired(boolean b)
	{
		heartbeatRequired = b;
	}

	public boolean getHeartbeatRequired()
	{
		return heartbeatRequired;
	}

	public void setOrigHeartbeatRequired(boolean b)
	{
		origHeartbeatRequired = b;
	}

	public boolean getOrigHeartbeatRequired()
	{
		return origHeartbeatRequired;
	}

	public void setCommentedOut(boolean b)
	{
		commentedOut = b;
	}

	public boolean getCommentedOut()
	{
		return commentedOut;
	}	
	
	public void setOrigCommentedOut(boolean b)
	{
		origCommentedOut = b;
	}

	public boolean getOrigCommentedOut()
	{
		return origCommentedOut;
	}	
	
	private MyPrint p;
	private	String name;
	private boolean commentedOut;
	private boolean origCommentedOut;
	private boolean heartbeatRequired;
	private boolean origHeartbeatRequired;
}

