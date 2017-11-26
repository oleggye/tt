package by.bsac.timetable.view.component;

import by.bsac.timetable.entity.IName;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

public class MyComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;

	public MyComboBox() {
		this.setRenderer(new MyRenderer<>());
		this.setToolTipText("");
	}

	public MyComboBox(ListCellRenderer<E> renderer) {
		this.setRenderer(renderer);
		this.setToolTipText("");
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		String tip;

		Object selectedItem = this.getSelectedItem();
		if (selectedItem instanceof IName) {
			tip = ((IName) selectedItem).getName();
		} else {
			tip = selectedItem.toString();
		}
		return tip;
	}
}