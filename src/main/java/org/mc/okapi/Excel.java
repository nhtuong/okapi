package org.mc.okapi;

import java.awt.*;
import javax.swing.*;
public class Excel extends Frame
   {
   BorderLayout borderLayout1 = new BorderLayout();
   JTable jTable1 ;
   Object[][] data=new Object[4][4];
   Object header[]= {"Jan","Feb","Mar","Apr"};
public static void main(String args[])
   {
	Excel myframe=new Excel();
   myframe.setSize(new Dimension(250,250));
   myframe.setVisible(true);
   }
public Excel()
   {
      super();
      try
      {
         jbInit();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
private void jbInit() throws Exception
   {
   for (int i=0;i<4;i++)
      for (int j=0;j<4;j++)
         data[i][j]=new Integer(i*10+j);
   System.out.println("Header length="+header[1]);
   jTable1=new JTable(data,header);
   jTable1.setCellSelectionEnabled(true);
   this.setTitle("Excel Lent JTABLE");
   jTable1.setBackground(Color.pink);
   this.setLayout(borderLayout1);
   this.setSize(new Dimension(400, 300));
   this.setBackground(Color.white);
   this.add(jTable1, BorderLayout.CENTER);
   // This is the line that does all the magic!
   ExcelAdapter myAd = new ExcelAdapter(jTable1,null);
   }
}