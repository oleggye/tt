package by.bsac.timetable.view;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class EduForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private byte edu_level = 1;

	/**
	 * Create the dialog.
	 */
	public EduForm() {
		setResizable(false);
		setModal(true);
		setForeground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel label = new JLabel(
				"\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0443\u0440\u043E\u0432\u0435\u043D\u044C \u043E\u0431\u0440\u0430\u0437\u043E\u0432\u0430\u043D\u0438\u044F");
		label.setForeground(Color.RED);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label.setBounds(21, 11, 225, 40);
		panel.add(label);

		JButton btnNewButton = new JButton(
				"\u0412\u044B\u0441\u0448\u0435\u0435 \u043E\u0431\u0440\u0430\u0437\u043E\u0432\u0430\u043D\u0438\u0435");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton.setBounds(31, 62, 187, 32);
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEduLevel((byte) 1); // задаем ВО
				dispose();
			}
		});

		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton(
				"\u0421\u0440\u0435\u0434\u043D\u0435\u0435 \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u044C\u043D\u043E\u0435");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton_1.setBounds(31, 118, 187, 32);
		btnNewButton_1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEduLevel((byte) 2); // задаем ССО
				dispose();
			}
		});

		panel.add(btnNewButton_1);
		setBounds(100, 100, 259, 199);
	}

	public byte getEduLevel() {
		return this.edu_level;
	}

	private void setEduLevel(byte edu_level) {
		this.edu_level = edu_level;
	}
}