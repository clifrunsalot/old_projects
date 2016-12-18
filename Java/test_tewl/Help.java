import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class Help extends JFrame
		implements HyperlinkListener
{
	public Help()
	{
		setTitle("Help");
		setSize(400,300);
		getContentPane().setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();

		topPanel.setLayout (new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.CENTER);

		try
		{
			URL url = new URL("file:///" + sPath + "help.htm");

			html = new JEditorPane(url);
			html.setEditable(false);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getViewport().add(html, BorderLayout.CENTER);

			topPanel.add(scrollPane, BorderLayout.CENTER);

			html.addHyperlinkListener (this);
		}
		catch(MalformedURLException e)
		{
			System.out.println("Malformed URL: " + e);
		}
		catch (IOException e)
		{
			System.out.println("IOException: " + e);
		}

// 		this.addWindowListener (new WindowAdapter()
// 		{
// 			public void windowClosing(WindowEvent e)
// 			{
// //				System.exit(0);
// 				hide();
// 			}
// 		});
	}

    public void windowClosing(WindowEvent we)
    {
    	this.hide();
    }

    public void windowOpened(WindowEvent we)
    {}

    public void windowIconified(WindowEvent we)
    {}

    public void windowDeiconified(WindowEvent we)
    {}

    public void windowClosed(WindowEvent we)
    {}

    public void windowActivated(WindowEvent we)
    {}

    public void windowDeactivated(WindowEvent we)
    {}

    public void hyperlinkUpdate (HyperlinkEvent event)
	{
		if (event.getEventType() ==
			HyperlinkEvent.EventType.ACTIVATED)
		{
			Cursor cursor = html.getCursor();
			Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
			html.setCursor(waitCursor);

			JEditorPane pane = (JEditorPane) event.getSource();

			if(event instanceof HTMLFrameHyperlinkEvent)
			{
				HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent)event;
				HTMLDocument doc = (HTMLDocument)pane.getDocument();
				doc.processHTMLFrameHyperlinkEvent(evt);
			}
			else
			{
				try
				{
					pane.setPage(event.getURL());
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}
			html.setCursor(cursor);
		}
	}

	private JEditorPane html;
	private String  sPath = System.getProperty ("user.dir") + "/";
}


