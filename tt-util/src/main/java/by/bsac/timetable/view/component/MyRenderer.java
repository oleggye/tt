package by.bsac.timetable.view.component;

import by.bsac.timetable.entity.IName;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

class MyRenderer<E> implements ListCellRenderer<E> {

	private JComponent listCellRendererComponent;

	private JLabel label;

	public MyRenderer() {

		listCellRendererComponent = new JPanel();
		listCellRendererComponent.setLayout(new GridBagLayout());

		label = new JLabel();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.1;

		listCellRendererComponent.add(label, gbc);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {

		Color fgColor = isSelected ? list.getSelectionForeground() : list.getForeground();
		Color bgColor = isSelected ? list.getSelectionBackground() : list.getBackground();

		listCellRendererComponent.setForeground(fgColor);
		listCellRendererComponent.setBackground(bgColor);

		label.setForeground(fgColor);
		label.setBackground(bgColor);

		if (value != null) {
			if (value instanceof IName) {
				IName name = (IName) value;
				label.setText(name.getName());
			} else {
				label.setText(value.toString());
			}
		}
		return listCellRendererComponent;
	}
}