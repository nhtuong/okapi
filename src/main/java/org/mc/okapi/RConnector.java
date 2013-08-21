package org.mc.okapi;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.math.R.RserverConf;
import org.math.R.Rsession;

public class RConnector extends JFrame {

	private JPanel contentPane;
	private JTextField tfRscript;
	public JFileChooser chooser;
	public MainFrame mf;
	public Rsession rs;
	public OutputGraph load;
	
	/**
	 * Create the frame.
	 */
	public RConnector(MainFrame mf) {

		setTitle("R Connector Configuration");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/extra/science_32.png"));
		setBounds(100, 100, 450, 108);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.mf = mf;
		
		JLabel lblRscript = new JLabel("Path Rscript path:");
		lblRscript.setBounds(10, 12, 133, 14);
		contentPane.add(lblRscript);
		
		tfRscript = new JTextField();
		tfRscript.setBounds(121, 8, 211, 23);
		contentPane.add(tfRscript);
		tfRscript.setColumns(10);
		
		
		String line = null;
		try {
    	      ProcessBuilder   ps =new ProcessBuilder("which","R");

    	      ps.redirectErrorStream(true);

    	      Process pr = ps.start();
    	      
    	      BufferedReader in = new BufferedReader(new 

    	      InputStreamReader(pr.getInputStream()));
    	      
    	      File rfile = new File(in.readLine());
    	      
    	      tfRscript.setText(rfile.getParent());
    	      while ((line = in.readLine()) != null) {
    	          System.out.println(line);
    	      }
    	      pr.waitFor();
    	      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

	
		            
 


			  	
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					String workingdir = chooser.getCurrentDirectory().getPath();
					System.out.println(workingdir);					
					chooser.setCurrentDirectory(new File(workingdir));

				  	tfRscript.setText(chooser.getSelectedFile().getAbsolutePath());
				  	
				}else {
					
				}
		    	
			}
		});
		btnBrowse.setBounds(329, 7, 95, 24);
		contentPane.add(btnBrowse);
		
		JButton btnConnector = new JButton("Connect...");
		btnConnector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	try {
		    	      //rcaller = new RCaller();
						String filename = tfRscript.getText();
						//filename = filename.replace("\\", "\\\\");
						System.out.println(filename);	
						//rcaller.setRscriptExecutable("%R_HOME%");
		    	      
						/* //rcaller.setRscriptExecutable("CMD /C start R CMD Rserve");
		    	      
		    	      /*try {
		    	          String line;
		    	          Process p = Runtime.getRuntime().exec( "CMD /C start where java" );

		    	          BufferedReader in = new BufferedReader(
		    	                  new InputStreamReader(p.getInputStream()) );
		    	          while ((line = in.readLine()) != null) {
		    	            System.out.println(line);
		    	          }
		    	          in.close();
		    	        }
		    	        catch (Exception ex) {
		    	          // ...
		    	        }*/
		    	      

		    	      
		    	      //SET PATH=%PATH%;C:\Program Files (x86)\Java\jdk1.6.0_05\bin
		    	      
		    	     /* System.out.println(ps.environment().get("JAVA_HOME"));
		    	      rcaller.setRscriptExecutable(ps.environment().get("R_HOME")+"\\bin\\Rscript.exe");

						
		    	      rcode = new RCode();
		    	      rcode.clear();

		    	      double[] x = new double[]{1, 4, 3, 5, 6, 10};
		    	      double[] y = new double[]{1, 4, 3, 5, 6, 10};

		    	      rcode.addDoubleArray("x", x);
		    	      rcode.addDoubleArray("y", y);
		    	      File file = rcode.startPlot();
		    	      System.out.println("Plot will be saved to : " + file);
		    	      rcode.addRCode("plot(x,y)");
		    	      rcode.endPlot();

		    	      rcaller.setRCode(rcode);
		    	      rcaller.runOnly();
		    	      rcode.showPlot(file);*/
		    	      
			    		//start Rserve
			    		Runtime.getRuntime().exec("CMD /C start R CMD Rserve");
			            Thread.sleep(1000);

				    	
				   		
			            load = new OutputGraph("Loading...","images/ico/extra/ajax_loading.gif");
			            load.dialog.setVisible(true);
		        		final JPanel rinstalled = new JPanel();
		        		int dialogButton = JOptionPane.INFORMATION_MESSAGE;
					    JOptionPane.showMessageDialog(rinstalled, "Make sure that RServe has been installed.", "R requirements", dialogButton);
					    
					    
				   		
			        	//rs = Rsession.newInstanceTry(System.out,null);
					    rs = Rsession.newInstanceTry(System.out,RserverConf.parse("R://localhost"));
			        	
		
			        	
				
			    		//start Rserve
			    		//Runtime.getRuntime().exec("CMD /C start R CMD Rserve");
			            //Thread.sleep(1000);
			    		
			        	System.out.println(rs.status);
			        	
			        	
			        	if (rs.status == "Ready"){
			        		Update();
			        		load.dialog.setVisible(false);
			        		final JPanel rconn = new JPanel();
						    JOptionPane.showMessageDialog(rconn, "R Connector is successfully loaded.\n Go to \"Tools\" > \"R\" for R-based analysis tools.", "R connecting...",
						    JOptionPane.INFORMATION_MESSAGE);
			        	} 
			        		
			        	dispose();
		  
		    	    } catch (Exception ex) {
		    	      System.out.println(ex.toString());
		    	    }
			}
		});
		btnConnector.setBounds(121, 37, 104, 23);
		contentPane.add(btnConnector);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(243, 37, 89, 23);
		contentPane.add(btnCancel);
	}

	public void Update(){

		this.mf.mnR.setEnabled(true);
		this.mf.mntmPlotR.setEnabled(true);
	}
	
}
