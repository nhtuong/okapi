package org.mc.okapi;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class OutputGraph {
	public OutputGraph(String img){
    	JDialog dialog = new JDialog();
    	JLabel label = new JLabel();
    	label.setIcon(new ImageIcon(img));
    	dialog.setLocationRelativeTo(null);
    	dialog.setTitle("Output...");
    	dialog.add(label);
    	dialog.pack();
    	dialog.setVisible(true);
	}
}
