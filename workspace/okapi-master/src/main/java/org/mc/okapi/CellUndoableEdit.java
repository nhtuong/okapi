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