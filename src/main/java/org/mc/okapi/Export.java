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

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Export extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblExportPath;
	private JTextField tfpath;
	private JButton btnExport;
	private JButton btnCancel;
	public CSVTableView tw;
	public DefaultTableModel data;
	public String filename;
	public String pathname;
	

	/**
	 * Create the dialog.
	 */
	public Export() {
		setTitle("Exporting data...");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/ico/extra/science_32.png"));
		setBounds(100, 100, 450, 116);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblExportPath = new JLabel("Output Filename:");
			lblExportPath.setBounds(10, 12, 112, 20);
		}
		
		tfpath = new JTextField();
		tfpath.setBounds(129, 12, 203, 20);
		tfpath.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setBounds(224, 12, 0, 0);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(228, 12, 0, 0);
		{
			btnExport = new JButton("Export...");
			btnExport.setBounds(129, 43, 95, 23);
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					exportData(data, tw, tfpath.getText());
					System.out.println(tfpath.getText());
					dispose();
				}
			});
			getRootPane().setDefaultButton(btnExport);
		}
		

		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					pathname = chooser.getSelectedFile().getAbsoluteFile().toString();
					tfpath.setText(pathname);
				}
				
				
				
				
				
			}
		});
		btnBrowse.setBounds(330, 11, 94, 23);
		{
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
				
			});
			btnCancel.setBounds(255, 43, 77, 23);
		}
		contentPanel.setLayout(null);
		contentPanel.add(lblExportPath);
		contentPanel.add(tfpath);
		contentPanel.add(label);
		contentPanel.add(label_1);
		contentPanel.add(btnExport);

		contentPanel.add(btnBrowse);
		contentPanel.add(btnCancel);
	}
	
	public void exportData(DefaultTableModel data, CSVTableView tw, String filename){
		FileWriter out;
		try {
			out = new FileWriter(filename);
			tw.defaultTableModelToStream(data, out);
            out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
