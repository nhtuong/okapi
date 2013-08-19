
/*
(c) INSERM U872, 2013

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation;
    version 2 of the License.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Contact: https://github.com/nhtuong/okapi

Authors:
Hoai-Tuong Nguyen <hoai-tuong.nguyen@inserm.fr>

*/

package org.mc.okapi;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Visualization extends JFrame {

	private JPanel contentPane;
	private JTextField txInfile;
	private JLabel lblOutfile;
	private JTextField tfOutfile;
	private JButton btnBrowseOutfile;
	private JButton btnVisualize;
	private JButton btnCancel;
	private JFileChooser chooser;
	private String inFile;
	private String outFile;
	private JComboBox cbLayout;

	/**
	 * Create the frame.
	 */
	public Visualization() {
		setTitle("Visualization");
		
		setBounds(100, 100, 450, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txInfile = new JTextField();
		txInfile.setBounds(153, 22, 182, 23);
		contentPane.add(txInfile);
		txInfile.setColumns(10);
		
		
		chooser = new JFileChooser(System.getProperty("user.dir"));
		
		JButton btnBrowseInfile = new JButton("Browse...");
		btnBrowseInfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt","txt"));
			  	chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.dot","dot"));			  	
			  	chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.gml","gml"));
			  	
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					
					String workingdir = chooser.getCurrentDirectory().getPath();
					System.out.println(workingdir);					
					chooser.setCurrentDirectory(new File(workingdir));
				  	
				  	inFile = chooser.getSelectedFile().getAbsolutePath();
				  	txInfile.setText(inFile);
				} 
				
				
			}
		});
		btnBrowseInfile.setBounds(335, 22, 89, 25);
		contentPane.add(btnBrowseInfile);
		
		JLabel lblInfile = new JLabel("Graph Input Filename: ");
		lblInfile.setBounds(10, 28, 157, 14);
		contentPane.add(lblInfile);
		
		lblOutfile = new JLabel("Graph Output Filename: ");
		lblOutfile.setBounds(10, 60, 157, 14);
		contentPane.add(lblOutfile);
		
		tfOutfile = new JTextField();
		tfOutfile.setColumns(10);
		tfOutfile.setBounds(153, 53, 182, 24);
		contentPane.add(tfOutfile);
		
		
		
		btnBrowseOutfile = new JButton("Browse...");
		btnBrowseOutfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.pdf","pdf"));
			  	
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					
					String workingdir = chooser.getCurrentDirectory().getPath();
					System.out.println(workingdir);					
					chooser.setCurrentDirectory(new File(workingdir));
				  	
				  	outFile = chooser.getSelectedFile().getAbsolutePath();
				  	tfOutfile.setText(outFile);
				} 
			}
		});
		btnBrowseOutfile.setBounds(335, 53, 89, 26);
		contentPane.add(btnBrowseOutfile);
		
		btnVisualize = new JButton("Visualize...");
		btnVisualize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gephi gp = new Gephi(txInfile.getText(),tfOutfile.getText(), String.valueOf(cbLayout.getSelectedItem()));
				System.out.println(String.valueOf(cbLayout.getSelectedItem()));
				try {
					Desktop.getDesktop().open(new File(tfOutfile.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				dispose();
				
		    	//GephiRanking gpranking = new GephiRanking("data/PowerGrid.gml","outranking_PowerGrid.pdf");
			}
		});
		btnVisualize.setBounds(153, 117, 94, 23);
		contentPane.add(btnVisualize);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(257, 117, 78, 23);
		contentPane.add(btnCancel);
		
		cbLayout = new JComboBox();
		cbLayout.setBounds(153, 86, 182, 23);
		cbLayout.addItem("Force Atlas 2");
		cbLayout.addItem("Fruchterman Reingold");
		cbLayout.addItem("Yifan Hu");
		
		contentPane.add(cbLayout);
		
		JLabel lblLayout = new JLabel("Layout Algorithm:");
		lblLayout.setBounds(10, 89, 143, 14);
		contentPane.add(lblLayout);
	}
}
