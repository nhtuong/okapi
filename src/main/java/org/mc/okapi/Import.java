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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Import extends JFrame {

	private JPanel contentPane;
	private JTextField tffilename;
	public JFileChooser chooser;
	public String datafile;
	private MainFrame mf;
	public StringMatrix datmat;
	public CSVTableView tw;
	public DefaultTableModel data;
	public String[][] dada;
	public String delim;
	/**
	 * Create the frame.
	 */
	public Import(MainFrame mf) {
		
		this.mf = mf;
		
		setTitle("Importing...");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/extra/science_32.png"));
		setBounds(100, 100, 392, 138);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDelimiter = new JLabel("Delimiter:");
		lblDelimiter.setBounds(10, 11, 67, 14);
		contentPane.add(lblDelimiter);
		
		JLabel lblFile = new JLabel("File:");
		lblFile.setBounds(36, 36, 46, 14);
		contentPane.add(lblFile);
		chooser = new JFileChooser(System.getProperty("user.dir"));
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				

				
				
			  	chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt","txt"));
			  	chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.csv","csv"));
			  	
			  	//System.out.println(chooser.getSelectedFile());
			  	
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					
					String workingdir = chooser.getCurrentDirectory().getPath();
					System.out.println(workingdir);					
					chooser.setCurrentDirectory(new File(workingdir));
				  	
				  	datafile = chooser.getSelectedFile().getAbsolutePath();
				  	tffilename.setText(datafile);
				}else {
					
				}
				
				
			}
		});
		btnBrowse.setBounds(261, 30, 89, 25);
		contentPane.add(btnBrowse);
		
		tffilename = new JTextField();
		tffilename.setBounds(87, 30, 176, 23);
		contentPane.add(tffilename);
		tffilename.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnCancel.setBounds(199, 66, 89, 23);
		contentPane.add(btnCancel);
		
		final JRadioButton rbSemicoma = new JRadioButton(";");
		rbSemicoma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delim = rbSemicoma.getText();
			}
		});
		rbSemicoma.setBounds(83, 7, 34, 23);
		contentPane.add(rbSemicoma);
		
		final JRadioButton rbComma = new JRadioButton(",");
		rbComma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delim = rbComma.getText();
			}
		});
		rbComma.setBounds(118, 7, 29, 23);
		contentPane.add(rbComma);
		
		final JRadioButton rbTab = new JRadioButton("\\t");
		rbTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delim = rbTab.getText();
			}
		});
		rbTab.setBounds(149, 7, 40, 23);
		contentPane.add(rbTab);
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rbSemicoma);
	    group.add(rbComma);
	    group.add(rbTab);
	    


	    
		JButton btnImport = new JButton("Import...");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				  //create TableView
				tw = new CSVTableView();
	            
	            FileReader fin = null;
				try {
					fin = new FileReader(datafile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
	            data = tw.createTableModel(fin, null,delim);
	            
	            
				//CSVReader reader = new CSVReader(new FileReader(datafile));
				//datmat = reader.readAll();
				
				dada = new String[data.getRowCount()][data.getColumnCount()];
				for (int i=0;i<data.getRowCount();i++){
					for (int j=0;j<data.getColumnCount();j++){								
						dada[i][j] = data.getValueAt(i,j).toString();								
					}
				}
				
				try {
					Update();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		btnImport.setBounds(84, 64, 89, 23);
		contentPane.add(btnImport);
		

		
	
	}
	
	private void Update() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		this.mf.UpdateTablePane(dada, data, tw);
	}
}
