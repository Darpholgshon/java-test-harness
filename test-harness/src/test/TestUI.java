package test;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class TestUI
{
	private static String test_string = "The quick brown fox jumps over the lazy dog 1234567890 !\"£$%^&*() Ḃ";
	
	static
	{
		UIManager.put("TextField.font", new FontUIResource( new Font("Arial", Font.PLAIN, 12 ) ) );
		UIManager.put("Button.font", new FontUIResource( new Font("Arial", Font.BOLD, 12 ) ) );
	}
		
	public static void main(String[] args)
	{
		JTextField text = new JTextField(40);
		text.setText(test_string);
				
		JButton button = new JButton("Ḃutton");
				
		JFrame f = new JFrame("UI Test Application");
		f.getContentPane().setLayout( new FlowLayout(FlowLayout.LEFT) );
		f.getContentPane().add( text );
		f.getContentPane().add( button );
		f.pack();
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}