package org.mc.okapi;
import java.io.*;

import java.util.*;

import javax.swing.table.*;

public class CSVTableView {
	 /**
    *
    * @param dtm The DefaultTableModel to save to stream
    * @param out The stream to which to save
    *
    */
   public static void defaultTableModelToStream(DefaultTableModel dtm,
       Writer out) throws IOException {
       final String LINE_SEP = System.getProperty("line.separator");
       int numCols = dtm.getColumnCount();
       int numRows = dtm.getRowCount();

       // Write headers
       String sep = "";

       for (int i = 0; i < numCols; i++) {
           out.write(sep);
           out.write(dtm.getColumnName(i));
           sep = ",";
       }

       out.write(LINE_SEP);

       for (int r = 0; r < numRows; r++) {
           sep = "";

           for (int c = 0; c < numCols; c++) {
               out.write(sep);
               out.write(dtm.getValueAt(r, c).toString());
               sep = ",";
           }

           out.write(LINE_SEP);
       }
   }

   /**
    *
    *
    * @param in A CSV input stream to parse
    * @param headers A Vector containing the column headers. If this is null, it's assumed
    * that the first row contains column headers
    *
    * @return A DefaultTableModel containing the CSV values as type String
    */
   public static DefaultTableModel createTableModel(Reader in,
       Vector<Object> headers, String delim) {
       DefaultTableModel model = null;
       Scanner s = null;

       try {
           Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
           s = new Scanner(in);
           System.out.println(s.delimiter());
           while (s.hasNextLine()) {
               rows.add(new Vector<Object>(Arrays.asList(s.nextLine().split("\\s*"+delim+"\\s*",-1))));
               
           }

           if (headers == null) {
               headers = rows.remove(0);
               model = new DefaultTableModel(rows, headers);
           } else {
               model = new DefaultTableModel(rows, headers);
           }

           return model;
       } finally {
           s.close();
       }
   }
}
