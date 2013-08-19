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