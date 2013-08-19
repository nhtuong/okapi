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

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.table.TableModel;

/**
 * Basic Edit for a Table Cell
 * @author Thierry LEFORT
 * 11 févr. 08
 *
 */
public class CellUndoableEdit extends AbstractUndoableEdit {
	/** row and column being edited */
	private int _row, _column;
	/** old and new value */
	private Object _oldValue, _newValue;
	/** Model being edited */
	private TableModel _model;
	
	/**
	 * 
	 * @param model model under edition
	 * @param row row being edited
	 * @param column column being edited
	 * @param oldValue previous value
	 * @param newValue new value
	 */
	public CellUndoableEdit(TableModel model, int row, int column, Object oldValue, Object newValue) {
		_model = model;
		_row = row;
		_column = column;
		_oldValue = oldValue;
		_newValue = newValue;
	}
	
	/**
	 * 
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		_model.setValueAt(_newValue, _row, _column);
	}
	
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		_model.setValueAt(_oldValue, _row, _column);
	}
	

}