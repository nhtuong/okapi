package org.mc.okapi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;
/**
 * ExcelAdapter enables Copy-Paste Clipboard functionality on JTables.
 * The clipboard data format used by the adapter is compatible with
 * the clipboard format used by Excel. This provides for clipboard
 * interoperability between enabled JTables and Excel.
 */
public class ExcelAdapter implements ActionListener
   {
   private String rowstring,value;
   private Clipboard system;
   private StringSelection stsel;
   private JTable table;
   public MainFrame _mf;
   /**
    * The Excel Adapter is constructed with a
    * JTable on which it enables Copy-Paste and acts
    * as a Clipboard listener.
    */
public ExcelAdapter(JTable myJTable, MainFrame frame)
   {
		_mf = frame;
      table = myJTable;
      KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
      // Identifying the copy KeyStroke user can modify this
      // to copy on some other Key combination.
      KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
      // Identifying the Paste KeyStroke user can modify this
      KeyStroke cut = KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK,false);
      
      //to copy on some other Key combination.
	table.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);
	table.registerKeyboardAction(this,"Cut",cut,JComponent.WHEN_FOCUSED);
	table.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
      system = Toolkit.getDefaultToolkit().getSystemClipboard();
   }
   /**
    * Public Accessor methods for the Table on which this adapter acts.
    */
public JTable getJTable() {return table;}
public void setJTable(JTable table) {this.table=table;}
   /**
    * This method is activated on the Keystrokes we are listening to
    * in this implementation. Here it listens for Copy and Paste ActionCommands.
    * Selections comprising non-adjacent cells result in invalid selection and
    * then copy action cannot be performed.
    * Paste is done by aligning the upper left corner of the selection with the
    * 1st element in the current selection of the JTable.
    */
public void actionPerformed(ActionEvent e)
   {
      if (e.getActionCommand().compareTo("Copy")==0)
      {	
    	  _mf.mntmUndo.setEnabled(true);
    	  copy(table, system, rowstring, value);
      }
      if (e.getActionCommand().compareTo("Cut")==0)
      {
    	  _mf.mntmUndo.setEnabled(true);
    	  cut(table, system, rowstring, value);
      }      
      if (e.getActionCommand().compareTo("Paste")==0)
      {
    	  _mf.mntmUndo.setEnabled(true);
    	  paste(table, system, rowstring, value);
      }
   }

	void copy(JTable table, Clipboard system, String rowstring, String value){
		 StringBuffer sbf=new StringBuffer();
         // Check to ensure we have selected only a contiguous block of
         // cells
         int numcols=table.getSelectedColumnCount();
         int numrows=table.getSelectedRowCount();
         int[] rowsselected=table.getSelectedRows();
         int[] colsselected=table.getSelectedColumns();
         if (!((numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0] &&
                numrows==rowsselected.length) &&
                		(numcols-1==colsselected[colsselected.length-1]-colsselected[0] &&
                numcols==colsselected.length)))
         {
            JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
                                          "Invalid Copy Selection",
                                          JOptionPane.ERROR_MESSAGE);
            return;
         }
         for (int i=0;i<numrows;i++)
         {
            for (int j=0;j<numcols;j++)
            {
            	sbf.append(table.getValueAt(rowsselected[i],colsselected[j]));
               if (j<numcols-1) sbf.append("\t");
            }
            sbf.append("\n");
         }
         stsel  = new StringSelection(sbf.toString());
         system = Toolkit.getDefaultToolkit().getSystemClipboard();
         system.setContents(stsel,stsel);
	}
	
	void cut(JTable table, Clipboard system, String rowstring, String value){
		System.out.println("Trying to Cut");
        int startRow=(table.getSelectedRows())[0];
        int startCol=(table.getSelectedColumns())[0];
        try
        {
           String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
           System.out.println("String is:"+trstring);
           StringTokenizer st1=new StringTokenizer(trstring,"\n");
           for(int i=0;st1.hasMoreTokens();i++)
           {
              rowstring=st1.nextToken();
              StringTokenizer st2=new StringTokenizer(rowstring,"\t");
              for(int j=0;st2.hasMoreTokens();j++)
              {
                 value=(String)st2.nextToken();
                 if (startRow+i< table.getRowCount()  &&
                     startCol+j< table.getColumnCount())
                    table.setValueAt("",startRow+i,startCol+j);
                 System.out.println("Putting "+ value+"atrow="+startRow+i+"column="+startCol+j);
             }
          }
       }
       catch(Exception ex){ex.printStackTrace();}
	}
	void paste(JTable table, Clipboard system, String rowstring, String value){
		System.out.println("Trying to Paste");
        int startRow=(table.getSelectedRows())[0];
        int startCol=(table.getSelectedColumns())[0];
        try
        {
           String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
           System.out.println("String is:"+trstring);
           StringTokenizer st1=new StringTokenizer(trstring,"\n");
           for(int i=0;st1.hasMoreTokens();i++)
           {
              rowstring=st1.nextToken();
              StringTokenizer st2=new StringTokenizer(rowstring,"\t");
              for(int j=0;st2.hasMoreTokens();j++)
              {
                 value=(String)st2.nextToken();
                 if (startRow+i< table.getRowCount()  &&
                     startCol+j< table.getColumnCount())
                    table.setValueAt(value,startRow+i,startCol+j);
                 System.out.println("Putting "+ value+"atrow="+startRow+i+"column="+startCol+j);
             }
          }
       }
       catch(Exception ex){ex.printStackTrace();}
	}
}
