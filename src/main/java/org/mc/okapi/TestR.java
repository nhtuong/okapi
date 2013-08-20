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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TestR extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public String inFile;
	public String inPath;
	
	public String outFile;
	public String outPath;
	
	public CSVTableView tw;
	public DefaultTableModel data;
	public StringMatrix datmat;
	private JComboBox cbX;
	private JComboBox cbY;
	public String cmd;
	public String outfile;
	private JComboBox cbTypeTest;
	/**
	 * Create the dialog.
	 */
	public TestR(final MainFrame mf) {

		setTitle("Pairewise Testing...");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/extra/science_32.png"));
		setBounds(100, 100, 450, 140);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblIx = new JLabel("X:");
		lblIx.setBounds(110, 11, 46, 14);
		contentPanel.add(lblIx);
		
		JLabel lblIy = new JLabel("Y:");
		lblIy.setBounds(286, 11, 46, 14);
		contentPanel.add(lblIy);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					double[] X = new double[data.getRowCount()];
					double[] Y = new double[data.getRowCount()];
					
					for (int i=0;i<X.length;i++){					
						X[i] = Double.valueOf(datmat.getColumnElements(cbX.getSelectedIndex())[i]);
					}
					
					for (int i=0;i<Y.length;i++){
						Y[i] = Double.valueOf(datmat.getColumnElements(cbY.getSelectedIndex())[i]);
					}					
					
					mf.rs.set("x", X);
					mf.rs.set("y", Y);
					mf.rs.set("xlab", cbY.getSelectedItem().toString());
					mf.rs.set("ylab", cbY.getSelectedItem().toString());
					
					mf.rs.eval("cor<-cor(x,y)");
					
					
					//mf.rs.set("df", new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}, "x1", "x2", "x3"); //create data frame from given vectors
					String cmd1 = "plot(x,y,main=paste(\"r=\",cor),xlab=xlab,ylab=ylab)";
					if (cbTypeTest.getSelectedItem().toString()=="t Student")
						cmd  = "t.test(x,y)";
					if (cbTypeTest.getSelectedItem().toString()=="Wilcoxon")
						cmd  = "wilcox.test(x,y)";

					
					outfile = "data/plot.jpg";
					mf.rs.toJPEG(new File(outfile), 400, 400, cmd1); //create jpeg file from R graphical command (like plot)
		            
		        	OutputGraph og = new OutputGraph("Output Graph",outfile);

		        	og.dialog.setVisible(true);
		        	
		        	
		        	String html = mf.rs.asString(cmd); //format in html using R2HTML
		  


		            Output outputdiag = new Output("");
		            //outputdiag.display.append(mean);
		            outputdiag.display.append("\n");
		            outputdiag.display.append(html);
		            
		            
		            
		            
					//System.out.println(Arrays.toString(datmat.getColumnElements(1)));
					
					
					//Runtime.getRuntime().exec("CMD /C start R CMD BATCH --no-save --no-restore --slave -f src/r/cor.R \"--args inFile='"+inFile+"' outFile='"+outPath+"/"+tfoutfile.getText()+"' iX='"+tfiX.getText()+"' iY='"+tfiY.getText()+"'\" ");
					//Runtime.getRuntime().exec("CMD /C start Rscript cor.R '"+inFile+"' '"+outPath+"/"+tfoutfile.getText()+"' "+tfiX.getText()+" "+tfiY.getText());
					//Rsession ss = Rsession.newInstanceTry(System.out,null);
					//Rsession rs = Rsession.newInstanceTry(System.out,RserverConf.parse("R://localhost"));
			


				
						
					/*	Rsession rs = Rsession.newInstanceTry(System.out,RserverConf.parse("R://localhost"));
						
						rs.set("df", data); //create R variable from java one
						
						rs.save(new File("save.Rdata"), "df"); //save variables in .Rdata*/
						
						
						/*RConnection c;
						try {
							
		
							c = new RConnection();
							
							
							
							double[] dataX = {1,4,5,8};
							double[] dataY = {1,7,9,12};
							
							c.assign("x", dataX);
							c.assign("y", dataY);
							
							//c.assign("z", datmat.getColumnElements(1));
						
							
							//String[] d= c.eval("c(z)").asStrings();
							
							//System.out.println(d[1].toString());
							System.out.println(Arrays.toString(datmat.getColumnElements(1)));
					
							
						} catch (RserveException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (REngineException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						
						

					
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
				}
				
				
			});
			okButton.setBounds(175, 72, 57, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			cancelButton.setBounds(242, 72, 74, 23);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
		
		cbX = new JComboBox();

		cbX.setBounds(128, 8, 104, 20);
		contentPanel.add(cbX);


		
		cbY = new JComboBox();
		cbY.setBounds(307, 8, 104, 20);
		contentPanel.add(cbY);
		
		cbTypeTest = new JComboBox();
		cbTypeTest.setBounds(203, 42, 129, 20);
		cbTypeTest.addItem("t Student");
		cbTypeTest.addItem("Wilcoxon");
		contentPanel.add(cbTypeTest);
		
		
		
		JLabel lblTypeOfTest = new JLabel("Type of test:");
		lblTypeOfTest.setBounds(120, 45, 104, 14);
		contentPanel.add(lblTypeOfTest);

	}
	
	public void setValues() {	
		
		//set value for X list
		for (int i=0;i<data.getColumnCount();i++){
			cbX.addItem(data.getColumnName(i));
		}
		
		//set value for Y list
		for (int i=0;i<data.getColumnCount();i++){
			cbY.addItem(data.getColumnName(i));
		}
		
	}

}
