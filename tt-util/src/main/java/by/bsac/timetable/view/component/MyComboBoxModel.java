package by.bsac.timetable.view.component;

import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class MyComboBoxModel<E> extends DefaultComboBoxModel<E> {

	private static final long serialVersionUID = 1L;

	public MyComboBoxModel(List<E> list) {
		for (E elem : list) {
			super.addElement(elem);
		}
	}
}