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
