

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.*;

public class Output extends JFrame {

	private JPanel contentPane;

	public JTextArea display;

	/**
	 * Create the frame.
	 */
	public Output(String content) {
		JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Output message" ) );

	    // create the middle panel components

	    display = new JTextArea ( 16, 58 );
	    display.setEditable ( false ); // set textArea non-editable
	    display.setLineWrap(true);
	    display.setWrapStyleWord(true);
	    
	    display.append(content);
	    JScrollPane scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add Textarea in to middle panel
	    middlePanel.add(scroll );

	    // My code
	    JFrame frame = new JFrame ();
	    frame.add(middlePanel );
	    frame.pack();
	    frame.setLocationRelativeTo ( null );
	    frame.setResizable(false);
	    frame.setVisible(true);

		

	}

}
