package components;

import by.bsac.timetable.entity.IName;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class OneColumnTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public OneColumnTableCellRenderer() {
		this.setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	protected void setValue(Object value) {
		String printValue = null;
		if (value instanceof IName) {
			printValue = ((IName) value).getName();
		} else {
			printValue = value.toString();
		}
		super.setValue(printValue);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
