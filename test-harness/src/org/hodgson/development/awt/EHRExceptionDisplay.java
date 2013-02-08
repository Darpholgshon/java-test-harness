package org.hodgson.development.awt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * A class that displays an exception or error.
 * 
 * @version $Revision: 1.3 $
 * @author $Author: ralphho $
 */
public class EHRExceptionDisplay extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionPanel mDetailPanel = null;
	private Throwable mThrowable;
	private JButton mCancel = new JButton("OK");
	private JButton mDetails = new JButton("Details");

	/**
	 * Constructs the display.
	 * 
	 * @param t
	 *            the throwable object to display.
	 * @param title
	 *            frame title
	 */
	public EHRExceptionDisplay(JFrame frame, String title, Throwable t)
	{
		super(frame, title, true);

		mThrowable = t;
		initDialog();
		setVisible(true);
	}

	/**
	 * Constructs the display.
	 * 
	 * @param t
	 *            the throwable object to display.
	 */
	public EHRExceptionDisplay(JFrame frame, Throwable t)
	{
		this(frame, "Unexpected Error", t);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == mCancel)
		{
			dispose();
		}
		else if (e.getSource() == mDetails)
		{
			mDetailPanel.toggleVisibility();
			pack();
		}
	}

	private void initDialog()
	{
		// Get the content pane and set the layout.
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		ExceptionPanel main = new ExceptionPanel(null, true);
		main.add(new JLabel(mThrowable.getMessage(), (Icon) UIManager
				.get("OptionPane.errorIcon"), (int) Component.LEFT_ALIGNMENT),
				BorderLayout.WEST);

		// Create Buttons.
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttons.add(mCancel);
		buttons.add(mDetails);

		panel.add(main, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);

		// Create Details Panel.
		mDetailPanel = new ExceptionPanel("Exception Details", false);
		JTextArea detail = new JTextArea(parseExceptionDetails(mThrowable));
		detail.setEditable(false);
		detail.setCaretPosition(0);
		JScrollPane scrollpane = new JScrollPane(detail);

		mDetailPanel.add(scrollpane, BorderLayout.CENTER);
		mDetailPanel.setVisible(false);

		// Add the Panels to the dialog.
		cp.add(panel, BorderLayout.CENTER);
		cp.add(mDetailPanel, BorderLayout.SOUTH);

		// Add button listeners
		mCancel.addActionListener(this);
		mDetails.addActionListener(this);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private String parseExceptionDetails(Throwable t)
	{
		CharArrayWriter caw = new CharArrayWriter(2000);
		PrintWriter w = new PrintWriter(caw);
		t.printStackTrace(w);
		w.flush();
		w.close();

		return new String(caw.toCharArray());
	}
}

class ExceptionPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mIsSummary = false;

	public ExceptionPanel(String title, boolean isSummary)
	{
		super();
		mIsSummary = isSummary;

		if (title != null)
		{
			setBorder(new TitledBorder(title));
		}
		else
		{
			setBorder(new EmptyBorder(5, 10, 5, 10));
		}
		setLayout(new BorderLayout());
	}

	public Dimension getPreferredSize()
	{
		if (mIsSummary)
		{
			return new Dimension(480, 80);
		}
		return new Dimension(480, 200);
	}

	public void toggleVisibility()
	{
		setVisible(!isVisible());
	}
}