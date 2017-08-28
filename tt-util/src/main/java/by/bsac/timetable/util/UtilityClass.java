package by.bsac.timetable.util;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.util.VerticalLabelUI;

public class UtilityClass {

	private JPanel addDayOfWeekTitlesToPanel() {

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		// задаем дни недели в метках
		JLabel[] jLbls = new JLabel[] { new JLabel("Понедельник"), new JLabel("Вторник"), new JLabel("Среда"),
				new JLabel("Четверг"), new JLabel("Пятница"), new JLabel("Суббота"), new JLabel("Воскресенье") };
		for (JLabel lbl : jLbls) {
			lbl.setUI(new VerticalLabelUI(false));// задаем вертикальное
			// отображение для текста в
			// метках
			lbl.setHorizontalAlignment(SwingConstants.CENTER);// задаем
			// горизонтальное
			// выравнивание
			// по центру
			panel.add(lbl);
		}
		return panel;
	}

	public static JPanel addTableTitlesToPanel() {

		JPanel subDOWSubpanel2 = new JPanel();
		subDOWSubpanel2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		subDOWSubpanel2.setLayout(new GridLayout(1, 0, 0, 0));
		// DOWSubpanel.add(subDOWSubpanel2);

		JLabel[] tableTitles = new JLabel[] { new JLabel("№"), new JLabel("1-ая подгруппа"),
				new JLabel("2-ая подгруппа") };

		JPanel titleSubDOWSubpanel2 = new JPanel();
		titleSubDOWSubpanel2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		titleSubDOWSubpanel2.setLayout(new FlowLayout());

		for (JLabel lbl : tableTitles) {

			lbl.setHorizontalAlignment(JLabel.LEFT);// задаем горизонтальное
			// выравнивание по
			// центру
			// lbl.setVerticalAlignment(JLabel.LEFT);// задаем вертикальное
			// выравнивание по центру
			// lbl.setFont(new Font("Verdana", Font.BOLD, 16));// изменяем шрифт
			// lbl.setForeground(Color.b);//изменяем цвет
			titleSubDOWSubpanel2.add(lbl);// добавляем на панель daysOfWeekpanel

			// subDOWSubpanel2.add(titleSubDOWSubpanel2);
		}
		return titleSubDOWSubpanel2;
	}

	public static JPanel addNumberOfWeekTitlesToPanel() {

		JPanel subDOWSubpanel1 = new JPanel();
		subDOWSubpanel1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		subDOWSubpanel1.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel[] jNumbOfWeekLbls = new JLabel[] { new JLabel("I"), new JLabel("II"), new JLabel("III"),
				new JLabel("IV") };

		for (JLabel lbl : jNumbOfWeekLbls) {
			lbl.setHorizontalAlignment(JLabel.CENTER);// задаем горизонтальное
			// выравнивание по
			// центру
			lbl.setVerticalAlignment(JLabel.CENTER);// задаем вертикальное
			// выравнивание по центру
			lbl.setFont(new Font("Verdana", Font.BOLD, 16));// изменяем шрифт
			// lbl.setForeground(Color.b);//изменяем цвет
			subDOWSubpanel1.add(lbl);// добавляем на панель daysOfWeekpanel
		}
		return subDOWSubpanel1;
	}

	public static <E> void selectCBItemById(JComboBox<E> comboBox, E object) {
		int size = comboBox.getModel().getSize();
		E item;
		for (int index = 0; index < size; index++) {
			item = comboBox.getItemAt(index);
			if (item.equals(object)) {
				comboBox.setSelectedIndex(index);
				break;
			}
		}
	}
}