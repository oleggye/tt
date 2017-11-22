package components;

import by.bsac.timetable.entity.IName;
import by.bsac.timetable.util.GetNamesClass;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class OneColumnTableModel<E> extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private final Class<E> clazz;

	public OneColumnTableModel(Class<E> clazz, List<? extends IName> list, String title) {
		this.clazz = clazz;
		Vector<?> vector = GetNamesClass.listToNameVector(list);
		Vector<String> titleVector = new Vector<>();
		titleVector.add(title);
		super.setDataVector(vector, titleVector);
	}

	@Override
	public Class<E> getColumnClass(int columnIndex) {
		return clazz;
	}
}