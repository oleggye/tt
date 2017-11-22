package tableClasses;

import by.bsac.timetable.entity.IName;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class MyTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public MyTableCellRenderer() {
		super.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value != null) {
		}
		return component;
	}

	@Override
	protected void setValue(Object value) {

		Object printedValue;
		if (value instanceof IName) {
			printedValue = ((IName) value).getName();
		} else {
			printedValue = value;
		}
		setText((printedValue == null) ? "" : printedValue.toString());
	}
}