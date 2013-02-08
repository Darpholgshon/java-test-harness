package net.press.pa.ui.awt;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import net.press.pa.ui.awt.document.CharLimitDocument;
import net.press.pa.util.BrowserHelper;

/**
 * Description: A field containing a URL.
 * 
 * @author <a href="mailto:ralph.hodgson@pa.press.net">ralphho
 */
public class URLField extends JTextField
{
	private static final long serialVersionUID = -4622575909681791940L;

	/**
	 * Default Constructor
	 */
	public URLField()
	{
		super();
		setDocument(new CharLimitDocument(255));

		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (!e.isControlDown())
				{
					launchBrowser(getText());
				}
			}

			public void mouseEntered(MouseEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(Cursor.getDefaultCursor());
			}
		});
	}

	private void launchBrowser(String url)
	{
		if (BrowserHelper.isWebAddress(url))
		{
			BrowserHelper.displayURL(url);
		}
	}
}