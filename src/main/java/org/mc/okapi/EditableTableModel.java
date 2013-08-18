package org.mc.okapi;

import javax.swing.table.AbstractTableModel;

public class EditableTableModel extends AbstractTableModel {
	
	private String[][] _data = new String[][] {
			{"0:0", "0:1", "0:2", "0:3"},
			{"1:0", "1:1", "1:2", "1:3"},
			{"2:0", "2:1", "2:2", "2:3"},
			{"3:0", "3:1", "3:2", "3:3"},
	}; 
	
	public int getColumnCount() {
		return _data.length;
	}

	public int getRowCount() {
		return _data[0].length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return _data[rowIndex][columnIndex];
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		_data[rowIndex][columnIndex] = (String) value;
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
}
