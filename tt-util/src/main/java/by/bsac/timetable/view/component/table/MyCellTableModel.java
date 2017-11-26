package by.bsac.timetable.view.component.table;

import by.bsac.component.multiSpanCellTable.AttributiveCellTableModel;
import by.bsac.timetable.entity.Record;

public class MyCellTableModel extends AttributiveCellTableModel {

	private static final long serialVersionUID = 1L;

	Class[] columnTypes = new Class[] { Integer.class, Record.class, Record.class };

	public MyCellTableModel(Object[][] objects, String[] strings) {
		super(objects, strings);
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}
}
