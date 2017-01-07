import javax.swing.*; 
import javax.swing.text.*; 
import java.lang.Character;
import java.awt.Toolkit;
import java.text.*;

public class DecimalField extends JTextField 
{
    private NumberFormat format;

    public DecimalField(double value, int columns, NumberFormat f) 
    {
        super(columns);
        d = new Debug();
        f = (DecimalFormat)NumberFormat.getInstance();
   	    ((DecimalFormat)f).applyPattern("######0.00");
		((DecimalFormat)f).setDecimalSeparatorAlwaysShown(true);
        ((DecimalFormat)f).setMaximumFractionDigits(2);
        ((DecimalFormat)f).setPositiveSuffix(" ");
        setDocument(new FormattedDocument(f));
        format = f;
        setValue(value);
    }

    public double getValue() 
    {
        double retVal = 0.0;

        try 
        {
            retVal = format.parse(getText()).doubleValue();
        } catch (ParseException e) 
        {
            // This should never happen because insertString allows
            // only properly formatted data to get in the field.
            Toolkit.getDefaultToolkit().beep();
            System.err.println("getValue: could not parse: " + getText());
        }
        return retVal;
    }

    public void setValue(double value) 
    {
        setText(format.format(value));
    }
    
	public class FormattedDocument extends PlainDocument 
	{
	    private Format format;

	    public FormattedDocument(Format f) 
	    {
	        format = f;
	    }

	    public Format getFormat() 
	    {
	        return format;
	    }
	    
	    public void insertString(int offs, String str, AttributeSet a) 
	        throws BadLocationException 
	    {

	        String currentText = getText(0, getLength());
	        d.print("currentText: " + currentText);
	        String beforeOffset = currentText.substring(0, offs);
	        d.print("beforeOffset: " + beforeOffset);
	        String afterOffset = currentText.substring(offs, currentText.length());
	        d.print("afterOffset: " + afterOffset);
	        String proposedResult = beforeOffset + str + afterOffset;
	        d.print("proposedResult: " + proposedResult);
	        
	        try 
	        {
				Double.parseDouble(proposedResult);
	            super.insertString(offs, str, a);
	        }
	        catch (NumberFormatException n)
	        {
	            Toolkit.getDefaultToolkit().beep();
 	            System.err.println("insert: could not parse: " + proposedResult);
	        }
	    }

	    public void remove(int offs, int len) throws BadLocationException 
	    {
	        String currentText = getText(0, getLength());
	        String beforeOffset = currentText.substring(0, offs);
	        String afterOffset = currentText.substring(len + offs,currentText.length());
	        String proposedResult = beforeOffset + afterOffset;

	        try 
	        {
	            if (proposedResult.length() != 0)
				Double.parseDouble(proposedResult);
	            super.remove(offs, len);
	        } catch (NumberFormatException n) 
	        {
	            Toolkit.getDefaultToolkit().beep();
	            System.err.println("remove: could not parse: " + proposedResult);
	        }
	    }
	}
	private Debug d;
}
