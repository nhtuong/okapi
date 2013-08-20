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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.math.linear.BigMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.math.R.RserverConf;
import org.math.R.Rsession;
import org.rosuda.REngine.REXPMismatchException;

import au.com.bytecode.opencsv.CSVReader;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.table.*;
import javax.swing.undo.UndoManager;

import static org.math.R.Rsession.*;

public class MainFrame extends JFrame implements KeyListener{

	 
	public JPanel contentPane;
	public JScrollPane tabledata;
	public String datafile;
	public JScrollPane scrollPane = new JScrollPane();
	public CSVTableView tw; 
	public DefaultTableModel data;
	public JMenu mnTwowayAnalysis;
	public JMenuItem mntmUndo;
	public JMenuItem mntmRedo;
	public JMenuItem mntmCopy;
	public JMenuItem mntmCut;
	public JMenuItem mntmPaste;
	public JMenuItem mntmExport;
	public JMenuItem mntmCorrelation;
	public StringMatrix datmat;
	public JFileChooser chooser;
	private Import imp;
	public Rsession rs;
	public JMenu mnR;
	public JMenuItem mntmPlotR;
	public JMenuItem mnRConnector;
	public JMenu mnPreferences;
	public String cmd;
	public String outfile;
	public JTable table;
	public Clipboard system;
	private String rowstring,value;
	private ExcelAdapter excelTable;
	public UndoManager ud;
	public UndoManagerTableModel udm;
	
	public OutputGraph load;
	private TestR tr;
	private String okapititle;
	private String version;
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		okapititle = "Okapi (R) - a GUI Omics Data Mining Toolkit";
		version = "v0.0.2";
		setTitle(okapititle+" - "+version);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/extra/science_32.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	    setMinimumSize(new Dimension(500, 300)); 
		setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setToolTipText("Exit the application");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		chooser = new JFileChooser(System.getProperty("user.dir"));
		
		JMenuItem mntmImport = new JMenuItem("Import...");
		mntmImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		
		imp = new Import(this);
		
		mntmImport.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				  try {
					  
					   // fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
					  	
					  	
					  
						
						

					  	
					   
					   
					  
					  imp.setVisible(true);
			            
			            


			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				  
			/*	CSVTable frame = new CSVTable("Faculty List Example","http://proctinator.com/help/faculty.csv");
			    frame.setVisible(true);
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
			}
		});
		mntmImport.setIcon(new ImageIcon("images/ico/extra/database_add.png"));
		mnFile.add(mntmImport);
		
		mntmExport = new JMenuItem("Export...");
		
		mntmExport.setEnabled(false);
		
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            
				try {
					Export exdialog = new Export();
					exdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					exdialog.setVisible(true);
					exdialog.data = data;
					exdialog.tw = tw;

					
				} catch (Exception e) {
					e.printStackTrace();
				}

	            
	            
			}
		});
		mntmExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mntmExport.setIcon(new ImageIcon("images/ico/extra/database_go.png"));
		mnFile.add(mntmExport);
		
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mntmExit.setIcon(new ImageIcon("images/ico/extra/door_out.png"));
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic('E');
		menuBar.add(mnEdit);
		
	      KeyStroke undo = KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK,false);

	      KeyStroke redo = KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK,false);
	
	     
	      addKeyListener(this);

	      
		mntmUndo = new JMenuItem("Undo");
		mntmUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ud.undo();
				mntmRedo.setEnabled(true);
				mntmUndo.setEnabled(ud.canUndo());

				
			}
		});
	
		mntmUndo.setEnabled(false);
		mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		mntmUndo.setIcon(new ImageIcon("images/ico/extra/arrow_undo.png"));
		mnEdit.add(mntmUndo);
		
		mntmRedo = new JMenuItem("Redo");
		mntmRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ud.redo();
				mntmUndo.setEnabled(true);
				mntmRedo.setEnabled(ud.canRedo());
				
			}
		});
		mntmRedo.setEnabled(false);
		mntmRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		mntmRedo.setIcon(new ImageIcon("images/ico/extra/arrow_redo.png"));
		mnEdit.add(mntmRedo);
		
		system = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		mntmCopy = new JMenuItem("Copy");
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				mntmUndo.setEnabled(true);
				excelTable.copy(table, system, rowstring, value);
			}
		});
		mntmCopy.setEnabled(false);
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mntmCopy.setIcon(new ImageIcon("images/ico/extra/page_copy.png"));
		mnEdit.add(mntmCopy);
		
		mntmCut = new JMenuItem("Cut");
		mntmCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmUndo.setEnabled(true);
				excelTable.cut(table, system, rowstring, value);
			      
			}
		});
		mntmCut.setEnabled(false);
		mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mntmCut.setIcon(new ImageIcon("images/ico/extra/cut.png"));
		mnEdit.add(mntmCut);
		
		mntmPaste = new JMenuItem("Paste");
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmUndo.setEnabled(true);
				excelTable.paste(table, system, rowstring, value);
			}
		});

		mntmPaste.setEnabled(false);
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mntmPaste.setIcon(new ImageIcon("images/ico/extra/paste_plain.png"));
		mnEdit.add(mntmPaste);
		
		JMenu mnTools = new JMenu("Tools");
		mnTools.setMnemonic('T');
		menuBar.add(mnTools);
		
		
		mnTwowayAnalysis = new JMenu("Two-way Analysis");
		mnTools.add(mnTwowayAnalysis);
		mnTwowayAnalysis.setEnabled(false);
		mnTwowayAnalysis.setIcon(new ImageIcon("images/ico/extra/two-way.jpeg"));
		
		JMenuItem mntmPiechart = new JMenuItem("PieChart");
		mntmPiechart.setEnabled(false);
		mnTwowayAnalysis.add(mntmPiechart);
		mntmPiechart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		    	//Piechart
		    	String[] label = {"Linux", "Mac", "Windows"};
		        double[] value = {29, 20, 51};
		    					
		        PieChart demo = new PieChart(label, value, "Comparison", "Which operating system are you using?");
		        demo.pack();
		        demo.setVisible(true);
				
			}
		});
		mntmPiechart.setIcon(new ImageIcon("images/ico/extra/pie_chart.png"));
		
		mntmCorrelation = new JMenuItem("Correlation");
		mnTwowayAnalysis.add(mntmCorrelation);
		mntmCorrelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					Correlation dialogCor = new Correlation();
					

					
					dialogCor.inFile = datafile;
					dialogCor.data = data;
					dialogCor.tw = tw;
					dialogCor.datmat = datmat;
					
					dialogCor.setValues();
					dialogCor.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialogCor.setVisible(true);					
					
					
					
				} catch (Exception ecor) {
					ecor.printStackTrace();
				}
				
				
		    	/*long[] observed = {10, 9, 11};
		    	double[] expected = {10.1, 9.8, 10.3};
		    	
		    	double[] numbers = new double[] {1,4,3,5,6,10};
		    	
		    	//print array
		    	System.out.println(Arrays.toString(expected));
		    	
		    	//chi square test
		    	System.out.println(TestUtils.chiSquareTest(expected, observed,0.05));
		    	
		    	double mean = StatUtils.mean(expected);
		    	double std = StatUtils.variance(expected);
		    	double median = StatUtils.percentile(expected, 50);
		    	
		    	System.out.println(mean);
		    	

		    	PearsonsCorrelation corr = new PearsonsCorrelation();
		    	
		    	double[] x = {10, 9, 11};
		    	double[] y = {10, 9, 11};
		    	
		    	System.out.println(corr.correlation(x, y));*/
		        

		    	
			}
		});
		mntmCorrelation.setIcon(new ImageIcon("images/ico/extra/cor.png"));
		
		mntmCorrelation.setEnabled(false);
		
		mnR = new JMenu("R");
		mnTools.add(mnR);
		mnR.setIcon(new ImageIcon("images/ico/extra/r.png"));
		mnR.setEnabled(false);
		
		tr = new TestR(this);
		
		mntmPlotR = new JMenuItem("Statistical Testing");
		mnR.add(mntmPlotR);
		mntmPlotR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*rs.set("df", new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}, "x1", "x2", "x3"); //create data frame from given vectors
				cmd = "plot(rnorm(100))";
				outfile = "data/plot.jpg";
	        	rs.toJPEG(new File(outfile), 400, 400, cmd); //create jpeg file from R graphical command (like plot)
	            
	        	OutputGraph og = new OutputGraph("Output Graph",outfile);*/
				
				tr.inFile = datafile;
				tr.data = data;
				tr.tw = tw;
				tr.datmat = datmat;
				
				tr.setValues();			
				
				tr.setVisible(true);
	        	 
	        	 
	        	
	        	/*String mean = "";
				try {
					mean = rs.eval("min(10,2,3)").asString();
				} catch (REXPMismatchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

	        	 

	            
	            
	            
			}
		});
		mntmPlotR.setEnabled(false);
		
		JMenu mnNetworkAnalysis = new JMenu("Network Analysis");
		mnTools.add(mnNetworkAnalysis);
		
		mnNetworkAnalysis.setIcon(new ImageIcon("images/ico/extra/net.png"));
		
		JMenuItem mntmNetwork = new JMenuItem("Visualization");
		mnNetworkAnalysis.add(mntmNetwork);
		mntmNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	//Gephi gp = new Gephi("data/polblogs.gml","out_polblogs.pdf");
		    	
				Visualization viz = new Visualization();
				viz.setVisible(true);
				
		    	
		    	
		 
			}			
		});
		//mntmNetwork.setIcon(new ImageIcon("images/ico/extra/net.png"));
		
		mnPreferences = new JMenu("Preferences");
		mnPreferences.setMnemonic('P');
		menuBar.add(mnPreferences);
		
		mnRConnector = new JMenuItem("R Connector");
		mnPreferences.add(mnRConnector);
		mnRConnector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   	try {
			   		
			   		
			   		load = new OutputGraph("Loading...","images/ico/extra/ajax_loading.gif");
			   		load.dialog.setVisible(true);
	        		final JPanel rinstalled = new JPanel();
	        		int dialogButton = JOptionPane.YES_NO_OPTION;
				    JOptionPane.showMessageDialog(rinstalled, "Make sure that RServe has been installed.", "R requirements", dialogButton);
				    
		
			   		
		        	//Rsession ss = Rsession.newInstanceTry(System.out,null);
		        	rs = Rsession.newInstanceTry(System.out,RserverConf.parse("R://localhost"));
		        	
			   		
			
		    		//start Rserve
		    		//Runtime.getRuntime().exec("CMD /C start R CMD Rserve");
		            //Thread.sleep(1000);
		    		
		            
		   
		        	
		        	if (rs.status == "Connecting..."){
		        		mnR.setEnabled(true);
		        		mntmPlotR.setEnabled(true);
		        		load.dialog.setVisible(false);
		        		final JPanel rconn = new JPanel();
					    JOptionPane.showMessageDialog(rconn, "R Connector is successfully loaded.\n Go to \"Tools\" > \"R\" for R-based analysis tools.", "R connecting...",
					    JOptionPane.INFORMATION_MESSAGE);
					    				    
					    
		        	}
		        		
					
					
				
		    	} catch (Exception e) {
		    		Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
		    	}

			}
		});
		mnRConnector.setIcon(new ImageIcon("images/ico/extra/r.png"));
		
		JMenu mnHelps = new JMenu("Helps");
		mnHelps.setMnemonic('H');
		menuBar.add(mnHelps);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   final JPanel panel = new JPanel();
				    JOptionPane.showMessageDialog(panel, okapititle+" - "+version +"\nCopyright (C) 2013 - INSERM U872 - Nutriomics Team \nDeveloper: Hoai-Tuong Nguyen", "About Okapi (R)",
				    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JMenuItem mntmUserGuide = new JMenuItem("User Guide");
		mntmUserGuide.setEnabled(false);
		mntmUserGuide.setIcon(new ImageIcon("images/ico/extra/help.png"));
		mnHelps.add(mntmUserGuide);
		
		JMenuItem mntmUpdates = new JMenuItem("Updates");
		mntmUpdates.setEnabled(false);
		mntmUpdates.setIcon(new ImageIcon("images/ico/extra/arrow_refresh.png"));
		mnHelps.add(mntmUpdates);
		
		mntmAbout.setIcon(new ImageIcon("images/ico/extra/information.png"));
		mnHelps.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
	}
	
	public void UpdateTablePane(String [][] ldada, DefaultTableModel ldata, CSVTableView ltw) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		data = ldata;
		tw = ltw;
		datmat = new StringMatrix();						
		datmat.data = ldada;
		
		System.out.println(Arrays.toString(datmat.getColumnElements(1)));
		
		
        //JFrame f = new JFrame();
        //f.setTitle("MC Dataviewer - Alpha");
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.getContentPane().add(new JScrollPane(new JTable(m)));
		
		contentPane.removeAll();
		table = new JTable(ldata);
        table.setCellSelectionEnabled(true);
        //this.setTitle("Excel Lent JTABLE");
        //table.setBackground(Color.pink);
		excelTable = new ExcelAdapter(table, this);
        

        


   
         ud = UndoManagerTableModel.install(table, ldata);
         udm = new UndoManagerTableModel(ldata);
 
         ColumnsAutoSizer.sizeColumnsToFit(table);
 		
 		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
 		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
         table.setFillsViewportHeight(true);
         
         TableCellListener tcl = new TableCellListener(table, action);
        
		tabledata = new JScrollPane(table);
		tabledata.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
		tabledata.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		contentPane.add(tabledata);
        setContentPane(contentPane);
        
        
        //f.setSize(200, 300);
        
      //enable "Export..." button
        mntmExport.setEnabled(true);
      //enable "Correlation..." button
        mntmCorrelation.setEnabled(true);
        mnTwowayAnalysis.setEnabled(true);
        
        mntmCopy.setEnabled(true);
        mntmCut.setEnabled(true);
        mntmPaste.setEnabled(true);
        //mntmUndo.setEnabled(true);
        //mntmRedo.setEnabled(true);
	}

	Action action = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        TableCellListener tcl = (TableCellListener)e.getSource();
	        System.out.println("Row   : " + tcl.getRow());
	        System.out.println("Column: " + tcl.getColumn());
	        System.out.println("Old   : " + tcl.getOldValue());
	        System.out.println("New   : " + tcl.getNewValue());
	        
	        mntmUndo.setEnabled(table.getFocusTraversalKeysEnabled());
	        
	    }
	};

	
	
	
	
	public void keyPressed(KeyEvent e) {
		
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
