package org.hodgson.development;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.hodgson.development.awt.FormPanel;
import org.hodgson.development.awt.document.NumericDocument;
import org.hodgson.development.awt.event.RHWindowListener;

import sun.net.www.content.text.PlainTextInputStream;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TestHarness extends JFrame implements ActionListener
{
	private static String test_string = "The quick brown fox jumps over the lazy dog 1234567890 !\"£$%^&*()";
		
	static
	{
		UIManager.put("TextField.font", new FontUIResource( new Font("Arial", Font.BOLD, 12 ) ) );
		UIManager.put("TextPane.font", new FontUIResource( new Font("Arial", Font.BOLD, 12 ) ) );
	}
		
	char c = '\u1E02';
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[])
	{
		System.setProperty("file.encoding", "UTF-8");
		
		try
		{
			System.out.println(InetAddress.getLocalHost().getHostName().toUpperCase() );
			
			
//			String name = UIManager.getSystemLookAndFeelClassName();
//			UIManager.setLookAndFeel(name);
		}
		catch (Exception e)
		{
		}
		
		new TestHarness();
	}

	/**
	 * "printMessage()" method to print a message.
	 */
	public static void printMessage(Message message)
	{
		try
		{
			// Get the header information
			String from = ((InternetAddress) message.getFrom()[0])
					.getPersonal();
			if (from == null)
			{
				from = ((InternetAddress) message.getFrom()[0]).getAddress();
			}
			System.out.println("FROM: " + from);
			String subject = message.getSubject();
			System.out.println("SUBJECT: " + subject);
			// -- Get the message part (i.e. the message itself) --
			Part messagePart = message;
			Object content = messagePart.getContent();
			// -- or its first body part if it is a multipart message --
			if (content instanceof Multipart)
			{
				messagePart = ((Multipart) content).getBodyPart(0);
				System.out.println("[ Multipart Message ]");
			}
			// -- Get the content type --
			String contentType = messagePart.getContentType();
			// -- If the content is plain text, we can print it --
			System.out.println("CONTENT:" + contentType);
			if (contentType.startsWith("text/plain")
					|| contentType.startsWith("text/html"))
			{
				InputStream is = messagePart.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String thisLine = reader.readLine();
				while (thisLine != null)
				{
					System.out.println(thisLine);
					thisLine = reader.readLine();
				}
			}
			System.out.println("-----------------------------");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * recv - method to fetch messages and process them.
	 */
	public static void recv(String provider, String server, String user,
			String password)
	{
		Store store = null;
		Folder folder = null;
		try
		{
			// -- Get hold of the default session --
			Properties props = System.getProperties();
			Session session = Session.getDefaultInstance(props, null);
			// -- Get hold of a POP3 message store, and connect to it --
			store = session.getStore(provider);
			store.connect(server, user, password);
			// -- Try to get hold of the default folder --
			folder = store.getDefaultFolder();
			if (folder == null)
			{
				throw new Exception("No default folder");
			}
			// -- ...and its INBOX --
			folder = folder.getFolder("INBOX");
			if (folder == null)
			{
				throw new Exception("No POP3 INBOX");
			}
			// -- Open the folder for read only --
			folder.open(Folder.READ_ONLY);
			// -- Get the message wrappers and process them --
			Message[] msgs = folder.getMessages();
			for (int msgNum = 0; msgNum < msgs.length; msgNum++)
			{
				printMessage(msgs[msgNum]);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			// -- Close down nicely --
			try
			{
				if (folder != null)
				{
					folder.close(false);
				}
				if (store != null)
				{
					store.close();
				}
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
		}
	}

	private JButton mBut1, mBut2, mBut3, mBut4;
	private JTextField mTxt1;
	private TitledBorder mTB;
	private FormPanel mFP;
	private static final String EXIT_BUT = "Exit";

	private static final String ACTION_1 = "Search";
	private static final String ACTION_2 = "Add";
	private static final String ACTION_3 = "Cancel";
	Object[] mFixedColumns =
		{ "Make" };

	Object[][] mFixedData =
		{
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" },
			{ "Peugeot" } };

	Object[] mColumns =
		{ "MAP", "DESCR", "NUM_FLDS", "NUM_ACTS", "NUM_VARNTS", "NUM_LANGS",
				"BLK1_REPS", "BLK2_REPS", "LOCK_USR", "NEXT_VARNT_ID",
				"SHIP_PROT", "DSBLD" };

	Object[][] mData =
		{
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" },
			{ "306", "Storm Gray", "Manual", "Petrol" } };

	public TestHarness()
	{
		super("Test User Interface");
		mBut1 = new JButton(ACTION_1);
		mBut1.addActionListener(this);
		mBut2 = new JButton(ACTION_2);
		mBut2.addActionListener(this);
		mBut3 = new JButton(ACTION_3);
		mBut3.addActionListener(this);
		mBut4 = new JButton(EXIT_BUT);
		mBut4.addActionListener(this);
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(mBut1);
		buttonPanel.add(mBut2);
		buttonPanel.add(mBut3);
		// buttonPanel.add(mBut4);
				
		try
		{
			Container cp = getContentPane();
			cp.setLayout(new BorderLayout());
			
			mTxt1 = new JTextField();
			
			cp.add( mTxt1 );
			
			mTxt1.setText("" + c);
						
//			cp.add(mFP = new FormPanel("Adhoc Report Tool"),
//					BorderLayout.CENTER);
//			cp.add(buttonPanel, BorderLayout.SOUTH);
//			mTB = new TitledBorder("Buttons!");
//			buttonPanel.setBorder(mTB);
//
//			buildReportPanel();
//			buildResultPanel();
			
			
			System.out.println( "" + new Character( mTxt1.getText().charAt(0) ).hashCode() );
			System.out.println( "" + mTxt1.getText().charAt(0) );
//
//			mFP.selectPage(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		addWindowListener(new RHWindowListener(this));
		setSize(new Dimension(400, 480));
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		setLocation(screenSize.width / 2 - size.width / 2, screenSize.height
				/ 2 - size.height / 2);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(EXIT_BUT))
		{
			System.exit(0);
		}
		else if (e.getActionCommand().equals(ACTION_1))
		{
		}
		else if (e.getActionCommand().equals(ACTION_2))
		{
		}
		else if (e.getActionCommand().equals(ACTION_3))
		{
		}
	}

	private void buildReportPanel()
	{
		FormLayout layout = new FormLayout(
				"5px, d, 5px, d, 5px, d, 5px, d, 5px, d:grow, 5px",
				"10px, pref, " + "10px, pref, " + "5px, pref, " + "5px, pref, "
						+ "5px, pref," + "5px, pref:grow, " + "5px, pref, 5px ");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Search by: ", cc.xy(2, 2));
		builder.add(new JRadioButton("Postcode", true), cc.xy(4, 2));
		builder.add(new JRadioButton("Region"), cc.xy(6, 2));

		builder.addLabel("Category: ", cc.xy(2, 4));
		builder.add(new JComboBox(), cc.xy(4, 4));

		builder.addLabel("Subcategory: ", cc.xy(2, 6));
		builder.add(new JComboBox(), cc.xy(4, 6));

		builder.add(buildSubSubCategoryPanel(), cc.xywh(2, 8, 5, 6));

		builder.addLabel("Search Value: ", cc.xy(8, 6));
		builder.add(mTxt1 = new JTextField(), cc.xy(10, 6));
		builder.add(buildSearchValuesPanel(), cc.xywh(8, 8, 3, 6));

		mFP.addPanel(builder.getPanel());

		mTxt1.setDocument(new NumericDocument(3));
	}

	private void buildResultPanel()
	{
		final Object[][] myData =
			{
				{ "London", new Integer(1) },
				{ "Midlands", new Integer(3) },
				{ "North East", new Integer(5) },
				{ "North West", new Integer(7) },
				{ "South East", new Integer(8) },
				{ "South West", new Integer(12) },
				{ "Yorkshire", new Integer(42) },
				{ "Scotland", new Integer(4) },
				{ "Northern Ireland", new Integer(4) },
				{ "Wales", new Integer(1) } };

		final String[] columnNames =
			{ "Region/Postcode", "Event Count" };

		TableModel model = new AbstractTableModel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 3953478474194286673L;

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex)
			{
				return columnIndex == 0 ? String.class : Integer.class;
			}

			public int getColumnCount()
			{
				return myData[0].length;
			}

			public String getColumnName(int column)
			{
				return columnNames[column];
			}

			public int getRowCount()
			{
				return myData.length;
			}

			public Object getValueAt(int rowIndex, int columnIndex)
			{
				return myData[rowIndex][columnIndex];
			}
		};
		JTable table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Search Results"), js.getBorder()));
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		mFP.addPanel(js);
	}

	private JComponent buildSearchValuesPanel()
	{
		JList list = new JList();
		list.setPrototypeCellValue("North Yorkshire");

		JScrollPane js = new JScrollPane(list);
		js.setBorder(BorderFactory
				.createCompoundBorder(BorderFactory
						.createTitledBorder("Search Regions/Postcodes"), js
						.getBorder()));
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		return js;
	}

	private JComponent buildSubSubCategoryPanel()
	{
		final Object[][] myData =
			{
				{ "afri", new Boolean(false) },
				{ "asian", Boolean.FALSE },
				{ "ballet", new Boolean(false) },
				{ "fest", new Boolean(false) },
				{ "gay", new Boolean(false) },
				{ "ice", new Boolean(false) },
				{ "kid", new Boolean(false) },
				{ "maj", new Boolean(false) },
				{ "mixed", new Boolean(false) },
				{ "modern", new Boolean(false) },
				{ "multi", new Boolean(false) },
				{ "music", new Boolean(false) },
				{ "none", new Boolean(false) },
				{ "phys", new Boolean(false) },
				{ "solo", new Boolean(false) },
				{ "tour", new Boolean(false) },
				{ "trad", new Boolean(false) } };

		TableModel model = new AbstractTableModel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 62184028636536464L;

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex)
			{
				return columnIndex == 0 ? String.class : Boolean.class;
			}

			public int getColumnCount()
			{
				return myData[0].length;
			}

			public String getColumnName(int column)
			{
				return "";
			}

			public int getRowCount()
			{
				return myData.length;
			}

			public Object getValueAt(int rowIndex, int columnIndex)
			{
				return myData[rowIndex][columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return columnIndex == 1;
			}
		};
		JTable table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Sub Sub Categories"), js.getBorder()));
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		return js;
	}

	public Frame getFrame()
	{
		return this;
	}
}