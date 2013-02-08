/*
 * Cedar Software
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to Cedar, Inc. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2006, Cedar, Inc. All Rights Reserved.
 */
package org.hodgson.development.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import org.hodgson.development.images.RHImageLoader;
import org.hodgson.development.util.AttributeChecker;

public class RHTextPane extends JPanel implements ItemListener, ActionListener,
		CaretListener, DocumentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int[] FONT_SIZES =
		{ 8, 10, 12, 14, 16, 18, 20, 24, 36 };

	private JToolBar mTB;
	private JComboBox mFontFamily, mFontSize;
	private JTextPane mTP1, mTP2;
	private ButtonGroup mAlignmentGroup;

	private HashMap mButtons;
	private HashMap mDocuments;

	private static String[] sFontStyleNames =
		{ "bold", "italic", "underline" };

	private ButtonModel mNullButtonModel;

	private JColorChooser mCC = new JColorChooser();
	private String mInputHTML = "<font color='blue'>The <I>quick</I> </font><font color='brown'>brown</font> <font color='green'>fox jumps over the lazy dog.</font>";

	Color mLastColor = Color.BLACK;

	public RHTextPane()
	{
		super(new BorderLayout());

		mTB = new JToolBar();
		mTB.setFocusable(false);
		// mTB.setFloatable(false);
		mTB.setBorderPainted(true);

		populateToolBar();

		mTP1 = new JTextPane();
		mTP1.setName("HTML");
		mTP1.setBorder(new TitledBorder("HTML"));
		mTP1.setEditorKit(new HTMLEditorKit());
		mTP1.addCaretListener(this);
		mTP1.getDocument().addDocumentListener(this);

		mTP2 = new JTextPane();
		mTP2.setName("RTF");
		mTP2.setBorder(new TitledBorder("RTF"));
		mTP2.setEditorKit(new RTFEditorKit());
		mTP2.addCaretListener(this);
		mTP2.getDocument().addDocumentListener(this);

		mDocuments = new HashMap();
		mDocuments.put(mTP1.getDocument(), mTP1);
		mDocuments.put(mTP2.getDocument(), mTP2);

		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				new JScrollPane(mTP1), new JScrollPane(mTP2));

		add(mTB, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	public void caretUpdate(CaretEvent e)
	{
		if (e.getSource() instanceof JTextPane)
		{
			final JTextPane tp = (JTextPane) e.getSource();

			Runnable r = new Runnable()
			{
				public void run()
				{
					for (int i = 0; i < sFontStyleNames.length; i++)
					{
						String buttonName = sFontStyleNames[i];
						JToggleButton button = (JToggleButton) mButtons
								.get(buttonName);
						button.setSelected(false);
					}
					mAlignmentGroup.setSelected(mNullButtonModel, true);

					JToggleButton button1 = (JToggleButton) mButtons
							.get(sFontStyleNames[0]);
					button1.setSelected(AttributeChecker.isBold(tp));

					JToggleButton button2 = (JToggleButton) mButtons
							.get(sFontStyleNames[1]);
					button2.setSelected(AttributeChecker.isItalic(tp));

					JToggleButton button3 = (JToggleButton) mButtons
							.get(sFontStyleNames[2]);
					button3.setSelected(AttributeChecker.isUnderline(tp));

					/*
					 * Check for alignment attribute and select relevant alignment button.
					 */
					int align = AttributeChecker.checkAlignment(tp);

					if (align != -1)
					{
						JToggleButton button = (JToggleButton) mButtons
								.get(AttributeChecker.ALIGNMENT_NAMES[align]);
						mAlignmentGroup.setSelected(button.getModel(), true);
					}
					mLastColor = AttributeChecker.checkColour(tp);

					mFontFamily.setSelectedItem(AttributeChecker
							.checkFontFamily(tp));
					mFontSize.setSelectedItem(AttributeChecker
							.checkFontSize(tp));

					RepaintManager rm = RepaintManager.currentManager(mTB);
					rm.markCompletelyDirty(mTB);
					rm.paintDirtyRegions();

					AttributeChecker.checkAttributes(tp);
				}
			};

			// Perform at end of swing event queue to allow JTextPane Caret position to be updated first.
			SwingUtilities.invokeLater(r);
		}
	}

	public void changedUpdate(DocumentEvent pE)
	{
		Document d = pE.getDocument();

		JTextPane tp = (JTextPane) mDocuments.get(d);
		tp.requestFocus();
	}

	private Color chooseColour(Color oldColour)
	{
		class ColourChangeListener implements ActionListener
		{
			boolean mHasChanged;

			public void actionPerformed(ActionEvent ae)
			{
				mHasChanged = ae.getActionCommand().equalsIgnoreCase("OK");
			}

			public boolean hasChanged()
			{
				return mHasChanged;
			}
		}
		ColourChangeListener cl = new ColourChangeListener();

		mCC.setColor(oldColour);
		JColorChooser.createDialog(this, "Choose Colour", true, mCC, cl, cl)
				.setVisible(true);

		return cl.hasChanged() ? mCC.getColor() : oldColour;
	}

	public AbstractButton createButton(Action a, String name, boolean isToggle)
	{
		a.putValue(Action.NAME, name);
		a.putValue(Action.SMALL_ICON, RHImageLoader.getImageIcon(name
				.toUpperCase()
				+ ".gif"));

		AbstractButton b1 = null;

		if (isToggle)
		{
			b1 = new JToggleButton(a);
			b1.setSelectedIcon(RHImageLoader.getImageIcon(name.toUpperCase()
					+ "-ON.gif"));
		}
		else
		{
			b1 = new JButton(a);
		}
		b1.setText("");

		mButtons.put(name.toLowerCase(), b1);

		return b1;
	}

	public void insertUpdate(DocumentEvent pE)
	{
	}

	public void itemStateChanged(ItemEvent e)
	{
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			String item = e.getItem().toString();

			try
			{
				// If its an int it's a font size change.
				int size = Integer.parseInt(item);

				Action fontSize = new StyledEditorKit.FontSizeAction(
						"FontSize", size);
				fontSize.actionPerformed(new ActionEvent(mTB, 0, String
						.valueOf(size)));
			}
			catch (NumberFormatException e1)
			{
				// otherwise it's a font family change.
				Action fontFamily = new StyledEditorKit.FontFamilyAction(item,
						item);
				fontFamily.actionPerformed(new ActionEvent(mTB, 0, item));
			}
		}
	}

	private void populateToolBar()
	{
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		mButtons = new HashMap();

		Action clear = new AbstractAction("Clear", null)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					mTP1.setText("");
					mTP2.setText("");
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		};
		mTB.add(clear);

		Action load = new AbstractAction("Open", RHImageLoader
				.getImageIcon("OPEN.gif"))
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					mTP1.setText("");
					mTP2.setText("");
					mTP1.getEditorKit().read(new StringReader(mInputHTML),
							mTP1.getDocument(), 0);
					mTP2.getEditorKit().read(
							new FileInputStream("c:\\temp\\test.rtf"),
							mTP2.getDocument(), 0);
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		};
		mTB.add(createButton(load, "Open", false));

		Action save = new AbstractAction("Save", RHImageLoader
				.getImageIcon("SAVE.gif"))
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					mTP1.getEditorKit().write(System.out, mTP1.getDocument(),
							0, mTP1.getDocument().getLength());
					System.out.println("**********************************");
					mTP2.getEditorKit().write(System.out, mTP2.getDocument(),
							0, mTP2.getDocument().getLength());
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}

			}
		};
		mTB.add(createButton(save, "Save", false));

		mTB.addSeparator(new Dimension(40, 12));

		mFontFamily = new JComboBox(fonts);
		mFontFamily.addItemListener(this);
		mTB.add(mFontFamily);

		mTB.addSeparator(new Dimension(10, 16));

		mFontSize = new JComboBox();

		for (int x = 0; x < FONT_SIZES.length; x++)
		{
			mFontSize.addItem(String.valueOf(FONT_SIZES[x]));
		}
		mFontSize.addItemListener(this);
		mTB.add(mFontSize);

		mTB.addSeparator();

		Action colourChoice = new AbstractAction("Colour Chooser",
				RHImageLoader.getImageIcon("COLOUR.gif"))
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Color c = chooseColour(mLastColor);

				Action fontColor = new StyledEditorKit.ForegroundAction(
						"fg-colour", c);
				fontColor.actionPerformed(new ActionEvent(e.getSource(), e
						.getID(), String.valueOf(c.getRGB())));
			}
		};

		mTB.add(createButton(colourChoice, "Colour", false));

		mTB.addSeparator();

		Action bold = new StyledEditorKit.BoldAction();
		mTB.add(createButton(bold, "Bold", true));

		Action italic = new StyledEditorKit.ItalicAction();
		mTB.add(createButton(italic, "Italic", true));

		Action underline = new StyledEditorKit.UnderlineAction();
		mTB.add(createButton(underline, "Underline", true));

		mTB.addSeparator();

		Action leftJustify = new StyledEditorKit.AlignmentAction("Left",
				StyleConstants.ALIGN_LEFT); // LEFT
		AbstractButton b1 = createButton(leftJustify, "Left-Align", true);

		Action centerJustify = new StyledEditorKit.AlignmentAction("Center",
				StyleConstants.ALIGN_CENTER); // CENTER
		AbstractButton b2 = createButton(centerJustify, "Center-Align", true);

		Action rightJustify = new StyledEditorKit.AlignmentAction("Right",
				StyleConstants.ALIGN_RIGHT); // RIGHT
		AbstractButton b3 = createButton(rightJustify, "Right-Align", true);

		// Action justified = new StyledEditorKit.AlignmentAction("Justified", StyleConstants.ALIGN_JUSTIFIED);
		// //Justified
		// AbstractButton b4 = createButton(rightJustify, "Justified", true);

		JToggleButton nullButton = new JToggleButton();
		mNullButtonModel = nullButton.getModel();

		mAlignmentGroup = new ButtonGroup();
		mAlignmentGroup.add(b1);
		mAlignmentGroup.add(b2);
		mAlignmentGroup.add(b3);
		mAlignmentGroup.add(nullButton);

		mTB.add(b1);
		mTB.add(b2);
		mTB.add(b3);
	}

	public void removeUpdate(DocumentEvent pE)
	{
	}
}
