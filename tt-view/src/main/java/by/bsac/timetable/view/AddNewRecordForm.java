package by.bsac.timetable.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.util.LessonFor;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.LessonType;
import by.bsac.timetable.util.WeekNumber;
import by.bsac.timetable.view.util.AddNewRecordInitializer;
import by.bsac.timetable.view.util.FormInitializer;
import components.ClassroomRenderer;
import components.MyComboBox;

public class AddNewRecordForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(AddNewRecordForm.class.getName());

	private final AddNewRecordInitializer initializer = new AddNewRecordInitializer(this);

	private JFrame parent;
	private byte currentWeekDay = 1;
	private WeekNumber currentWeekNumber = WeekNumber.FIRST;
	private byte lessonOrdinalNumber = 1;

	private Record addRecord = new Record();

	private ActionMode action = ActionMode.Add_Record;
	private byte educationLevel = 1;

	/**
	 * предварительная инициализация переменных
	 */
	private LessonFor lessonFor = LessonFor.FULL_FLOW;
	private LessonPeriod lessonPeriod = LessonPeriod.FOR_ONE_DATE;
	private LessonType lessonType = LessonType.LECTURE;
	private Set<WeekNumber> weekSet = new HashSet<>();

	/**
	 * Constructor for adding a record
	 * 
	 * @param parent
	 * @param lessonDate
	 * @param weekNumber
	 * @param currentWeekDay
	 * @param lessonOrdinalNumber
	 */
	public AddNewRecordForm(JFrame parent, Date lessonDate, Group group, byte weekNumber, byte currentWeekDay,
			byte lessonOrdinalNumber) {

		initializer.initFormFieldList(parent, lessonDate, group, weekNumber, currentWeekDay, lessonOrdinalNumber);

		setResizable(false);
		setLocationRelativeTo(parent);
		setBounds(100, 100, 741, 576);

		String date = new SimpleDateFormat("dd-MM-yyyy").format(lessonDate);

		getContentPane().setLayout(null);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 500, 725, 35);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		{
			JButton okButton = new JButton("OK");
			okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener((ActionEvent e) -> {
				okButton.setEnabled(false);

				ICommand command = CommandProvider.getInstance().getCommand(action);
				Request request = new Request();
				request.putParam("addRecord", addRecord);
				request.putParam("weekSet", weekSet);

				try {
					command.execute(request);
					JOptionPane.showMessageDialog(getContentPane(), "Операция выполнена успешно!");
					this.dispose();
				} catch (CommandException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				} finally {
					okButton.setEnabled(true);
				}
			});
		}

		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
			cancelButton.addActionListener((ActionEvent e) -> {
				this.dispose();
			});
		}

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 735, 498);

		getContentPane().add(tabbedPane);

		JPanel addTab = new JPanel();
		tabbedPane.addTab("Добавить запись", null, addTab, null);
		addTab.setLayout(null);
		constractAddTab(addTab, date);
	}

	private void constractAddTab(JPanel addTab, String lessonDate) {

		JLabel selectedDateLabel = new JLabel(lessonDate);
		JPanel duplicateOnWeeksPanel = new JPanel();

		JDatePickerImpl oneDatePickerForAdding = initializer.initDatePicker(addRecord.getDateFrom());
		oneDatePickerForAdding.addActionListener((ActionEvent e) -> {
			Date date = (Date) oneDatePickerForAdding.getModel().getValue();
			addRecord.setDateFrom(date);
			addRecord.setDateTo(date);
			if (lessonPeriod.equals(LessonPeriod.FOR_ONE_DATE)) {
				String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
				selectedDateLabel.setText(formatedDate);
			}

		});
		JDatePickerImpl fromDatePickerForAdd = initializer.initDatePicker(addRecord.getDateFrom());
		fromDatePickerForAdd.addActionListener((ActionEvent e) -> {
			Date date = (Date) fromDatePickerForAdd.getModel().getValue();
			addRecord.setDateFrom(date);
		});

		JDatePickerImpl toDatePickerForAdd = initializer.initDatePicker(addRecord.getDateFrom());
		toDatePickerForAdd.addActionListener((ActionEvent e) -> {
			Date date = (Date) toDatePickerForAdd.getModel().getValue();
			addRecord.setDateTo(date);
		});

		JLabel label = new JLabel("Дата:");
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(30, 20, 77, 20);
		addTab.add(label);

		JLabel lblNewLabel = new JLabel("Выбраная дата:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(206, 11, 125, 19);
		addTab.add(lblNewLabel);

		selectedDateLabel.setForeground(Color.RED);
		selectedDateLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		selectedDateLabel.setBounds(344, 13, 178, 14);
		addTab.add(selectedDateLabel);

		JLabel label_1 = new JLabel("Пара назначается:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_1.setBounds(30, 118, 166, 20);
		addTab.add(label_1);

		JLabel label_2 = new JLabel("Тип пары:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_2.setBounds(30, 210, 166, 20);
		addTab.add(label_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(30, 41, 676, 77);
		addTab.add(panel_3);
		panel_3.setLayout(null);

		ButtonGroup periodBtnGroup = new ButtonGroup();

		JRadioButton radioButton = new JRadioButton("Пара на выбранную дату");
		radioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton.setBounds(6, 7, 227, 23);
		radioButton.setSelected(true);

		radioButton.addActionListener((ActionEvent e) -> {
			lessonPeriod = LessonPeriod.FOR_ONE_DATE;
			Date date = (Date) oneDatePickerForAdding.getModel().getValue();
			addRecord.setDateFrom(date);
			addRecord.setDateTo(date);
			String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
			selectedDateLabel.setText(formatedDate);

			duplicateOnWeeksPanel.setVisible(false);

			// fromDatePickerForAdding.setVisible(false);
			// toDatePickerForAdding.setVisible(false);
			// fromDateLabel.setVisible(false);
			// toDateLabel.setVisible(false);
		});
		periodBtnGroup.add(radioButton);
		panel_3.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("Пара на промежуток");
		radioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_1.setBounds(6, 44, 190, 23);
		radioButton_1.addActionListener((ActionEvent e) -> {
			lessonPeriod = LessonPeriod.FOR_THE_PERIOD;
			addRecord.setDateFrom((Date) fromDatePickerForAdd.getModel().getValue());
			addRecord.setDateTo((Date) toDatePickerForAdd.getModel().getValue());
			selectedDateLabel.setText("выбран интервал");

			duplicateOnWeeksPanel.setVisible(true);
		});
		panel_3.add(radioButton_1);
		periodBtnGroup.add(radioButton_1);

		JLabel label_3 = new JLabel("выбрать другую дату:");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_3.setBounds(273, 10, 180, 17);
		panel_3.add(label_3);

		JPanel panel = new JPanel();
		panel.setBounds(463, 7, 204, 33);
		panel.add(oneDatePickerForAdding);
		panel_3.add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(221, 37, 204, 33);
		panel_1.add(fromDatePickerForAdd);
		panel_3.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(463, 40, 204, 33);
		panel_2.add(toDatePickerForAdd);
		panel_3.add(panel_2);

		JLabel label_4 = new JLabel("с");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_4.setBounds(200, 48, 18, 14);
		panel_3.add(label_4);

		JLabel label_5 = new JLabel("по");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_5.setBounds(435, 48, 18, 14);
		panel_3.add(label_5);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBounds(30, 139, 676, 69);
		addTab.add(panel_4);

		ButtonGroup classForBtnGroup = new ButtonGroup();

		JRadioButton rdbtnNewRadioButton = new JRadioButton("для всего потока");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton.setBounds(6, 7, 166, 23);

		if (lessonFor.equals(LessonFor.FULL_FLOW)) {
			rdbtnNewRadioButton.setSelected(true);
		} else {
			rdbtnNewRadioButton.setEnabled(false);
		}
		rdbtnNewRadioButton.addActionListener((ActionEvent e) -> {
			lessonFor = LessonFor.FULL_FLOW;
			addRecord.setSubjectFor(lessonFor.lessonForToSubjectFor());
		});
		classForBtnGroup.add(rdbtnNewRadioButton);
		panel_4.add(rdbtnNewRadioButton);

		JRadioButton radioButton_2 = new JRadioButton("для всей группы");
		radioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_2.setBounds(226, 7, 152, 23);

		if (lessonFor.equals(LessonFor.FULL_GROUP)) {
			radioButton_2.setSelected(true);
		}
		radioButton_2.addActionListener((ActionEvent e) -> {
			lessonFor = LessonFor.FULL_GROUP;
			addRecord.setSubjectFor(lessonFor.lessonForToSubjectFor());
		});
		classForBtnGroup.add(radioButton_2);
		panel_4.add(radioButton_2);

		JRadioButton radioButton_3 = new JRadioButton("для первой подгруппы");
		radioButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_3.setBounds(442, 7, 193, 23);
		radioButton_3.addActionListener((ActionEvent e) -> {
			lessonFor = LessonFor.FIRST_SUBGROUP;
			addRecord.setSubjectFor(lessonFor.lessonForToSubjectFor());
		});
		classForBtnGroup.add(radioButton_3);
		panel_4.add(radioButton_3);

		JRadioButton radioButton_4 = new JRadioButton("для второй подгруппы");
		radioButton_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_4.setBounds(442, 38, 193, 23);
		radioButton_4.addActionListener((ActionEvent e) -> {
			lessonFor = LessonFor.SECOND_SUBGROUP;
			addRecord.setSubjectFor(lessonFor.lessonForToSubjectFor());
		});
		classForBtnGroup.add(radioButton_4);
		panel_4.add(radioButton_4);

		JLabel label_11 = new JLabel("(поточная пара)");
		label_11.setFont(new Font("Tahoma", Font.ITALIC, 14));
		label_11.setBounds(27, 28, 135, 14);
		panel_4.add(label_11);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_5.setBounds(30, 233, 676, 53);
		addTab.add(panel_5);

		ButtonGroup classTypeBtnGroup = new ButtonGroup();

		JRadioButton radioButton_5 = new JRadioButton("Лекция");
		radioButton_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_5.setBounds(6, 3, 94, 23);
		radioButton_5.setSelected(true);

		radioButton_5.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.LECTURE;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_5);
		panel_5.add(radioButton_5);

		JRadioButton radioButton_6 = new JRadioButton("Практическая");
		radioButton_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_6.setBounds(124, 3, 131, 23);

		radioButton_6.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.PRACTICE_WORK;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_6);
		panel_5.add(radioButton_6);

		JRadioButton radioButton_7 = new JRadioButton("Консультация");
		radioButton_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_7.setBounds(257, 3, 140, 23);

		radioButton_7.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.CONSULTATION;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_7);
		panel_5.add(radioButton_7);

		JRadioButton radioButton_8 = new JRadioButton("Зачет");
		radioButton_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_8.setBounds(422, 3, 74, 23);

		radioButton_8.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.CREDIT;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_8);
		panel_5.add(radioButton_8);

		JRadioButton radioButton_9 = new JRadioButton("Экзамен");
		radioButton_9.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_9.setBounds(361, 25, 94, 23);

		radioButton_9.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.EXAM;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_9);
		panel_5.add(radioButton_9);

		JRadioButton radioButton_10 = new JRadioButton("Лабораторная");
		radioButton_10.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_10.setBounds(52, 25, 131, 23);

		radioButton_10.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.LABORATORY_WORK;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_10);
		panel_5.add(radioButton_10);

		JRadioButton radioButton_11 = new JRadioButton("Учебное занятие");
		radioButton_11.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_11.setBounds(521, 5, 149, 23);

		radioButton_11.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.TRAINING_SESSION;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_11);
		panel_5.add(radioButton_11);

		JRadioButton radioButton_12 = new JRadioButton("Переезд");
		radioButton_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_12.setBounds(521, 27, 149, 23);

		radioButton_12.addActionListener((ActionEvent e) -> {
			lessonType = LessonType.MOVE;
			addRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
		});
		classTypeBtnGroup.add(radioButton_12);
		panel_5.add(radioButton_12);

		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.setBounds(30, 313, 676, 77);
		addTab.add(panel_6);

		JComboBox<Chair> chairComboBox = new MyComboBox<>();
		chairComboBox.setBounds(20, 36, 206, 27);
		panel_6.add(chairComboBox);

		JLabel lblNewLabel_2 = new JLabel("Кафедра");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(20, 11, 112, 20);
		panel_6.add(lblNewLabel_2);

		JComboBox<Lecturer> lecturerComboBox = new MyComboBox<>();
		lecturerComboBox.setBounds(248, 36, 179, 27);
		lecturerComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				if (lecturerComboBox.getItemCount() > 0) {
					addRecord.setLecturer((Lecturer) lecturerComboBox.getSelectedItem());
				}
			}
		});
		panel_6.add(lecturerComboBox);

		JLabel label_7 = new JLabel("Преподаватель");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_7.setBounds(246, 11, 112, 20);
		panel_6.add(label_7);

		JLabel label_8 = new JLabel("Предмет");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_8.setBounds(450, 11, 112, 20);
		panel_6.add(label_8);

		JComboBox<Subject> subjectComboBox = new MyComboBox<>();
		subjectComboBox.setBounds(450, 36, 216, 27);
		subjectComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				if (subjectComboBox.getItemCount() > 0) {
					addRecord.setSubject((Subject) subjectComboBox.getSelectedItem());
				}
			}
		});
		panel_6.add(subjectComboBox);

		chairComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				try {
					subjectComboBox.removeAllItems();

					lecturerComboBox.removeAllItems();

					FormInitializer.initSubjectComboBox(subjectComboBox, chairComboBox, educationLevel);
					addRecord.setSubject((Subject) subjectComboBox.getSelectedItem());
					FormInitializer.initLecturerComboBox(lecturerComboBox, chairComboBox);
					addRecord.setLecturer((Lecturer) lecturerComboBox.getSelectedItem());

				} catch (CommandException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				}
			}
		});

		JLabel label_6 = new JLabel("Дисциплина:");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_6.setBounds(30, 289, 166, 20);
		addTab.add(label_6);

		duplicateOnWeeksPanel.setLayout(null);
		duplicateOnWeeksPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		duplicateOnWeeksPanel.setBounds(55, 420, 247, 43);

		if (lessonPeriod.equals(LessonPeriod.FOR_ONE_DATE)) {
			duplicateOnWeeksPanel.setVisible(false);
		}
		addTab.add(duplicateOnWeeksPanel);

		JCheckBox firstWeekForAdd = new JCheckBox("1");
		firstWeekForAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		firstWeekForAdd.setBounds(10, 10, 42, 23);
		if (currentWeekNumber.equals(WeekNumber.FIRST)) {
			firstWeekForAdd.setSelected(true);
		}
		firstWeekForAdd.addActionListener((ActionEvent e) -> {
			if (firstWeekForAdd.isSelected()) {
				weekSet.add(WeekNumber.FIRST);
			} else {
				weekSet.remove(WeekNumber.FIRST);
			}
			JOptionPane.showMessageDialog(this.getContentPane(), weekSet);
		});
		duplicateOnWeeksPanel.add(firstWeekForAdd);

		JCheckBox secondWeekForAdd = new JCheckBox("2");
		secondWeekForAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		secondWeekForAdd.setBounds(65, 10, 42, 23);
		if (currentWeekNumber.equals(WeekNumber.SECOND)) {
			secondWeekForAdd.setSelected(true);
		}
		secondWeekForAdd.addActionListener((ActionEvent e) -> {
			if (secondWeekForAdd.isSelected()) {
				weekSet.add(WeekNumber.SECOND);
			} else {
				weekSet.remove(WeekNumber.SECOND);
			}
		});
		duplicateOnWeeksPanel.add(secondWeekForAdd);

		JCheckBox thirdWeekForAdd = new JCheckBox("3");
		thirdWeekForAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		thirdWeekForAdd.setBounds(128, 10, 42, 23);
		if (currentWeekNumber.equals(WeekNumber.THIRD)) {
			thirdWeekForAdd.setSelected(true);
		}
		thirdWeekForAdd.addActionListener((ActionEvent e) -> {
			if (thirdWeekForAdd.isSelected()) {
				weekSet.add(WeekNumber.THIRD);
			} else {
				weekSet.remove(WeekNumber.THIRD);
			}
		});
		duplicateOnWeeksPanel.add(thirdWeekForAdd);

		JCheckBox fourthWeekForAdd = new JCheckBox("4");
		fourthWeekForAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fourthWeekForAdd.setBounds(195, 10, 42, 23);
		if (currentWeekNumber.equals(WeekNumber.FOURTH)) {
			fourthWeekForAdd.setSelected(true);
		}
		fourthWeekForAdd.addActionListener((ActionEvent e) -> {
			if (fourthWeekForAdd.isSelected()) {
				weekSet.add(WeekNumber.FOURTH);
			} else {
				weekSet.remove(WeekNumber.FOURTH);
			}
		});
		duplicateOnWeeksPanel.add(fourthWeekForAdd);

		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBounds(480, 420, 197, 46);
		addTab.add(panel_10);

		JLabel label_9 = new JLabel("Дублировать (неделя):");
		label_9.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_9.setBounds(55, 394, 215, 20);
		addTab.add(label_9);

		JComboBox<Classroom> classroomComboBox = new MyComboBox<>(new ClassroomRenderer<>());
		classroomComboBox.setBounds(10, 11, 159, 27);

		classroomComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				if (classroomComboBox.getItemCount() > 0) {
					addRecord.setClassroom((Classroom) classroomComboBox.getSelectedItem());
				}
			}
		});
		panel_10.add(classroomComboBox);

		JLabel label_10 = new JLabel("Аудитория:");
		label_10.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_10.setBounds(480, 403, 166, 20);
		addTab.add(label_10);

		try {
			//HibernateUtil.getSession();
			FormInitializer.initChairComboBox(chairComboBox);
			if (chairComboBox.getItemCount() != 0) {
				FormInitializer.initSubjectComboBox(subjectComboBox, chairComboBox, educationLevel);
				addRecord.setSubject((Subject) subjectComboBox.getSelectedItem());
				FormInitializer.initLecturerComboBox(lecturerComboBox, chairComboBox);
				addRecord.setLecturer((Lecturer) lecturerComboBox.getSelectedItem());
			}
			FormInitializer.initClassroomComboBox(classroomComboBox);
			addRecord.setClassroom((Classroom) classroomComboBox.getSelectedItem());
		} catch (CommandException ex) {
			LOGGER.error(ex.getCause().getMessage(), ex);
			JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
		} finally {
			//HibernateUtil.closeSession();
		}
	}

	@Override
	public JFrame getParent() {
		return parent;
	}

	public void setParent(JFrame parent) {
		this.parent = parent;
	}

	public byte getCurrentWeekDay() {
		return currentWeekDay;
	}

	public void setCurrentWeekDay(byte currentWeekDay) {
		this.currentWeekDay = currentWeekDay;
	}

	public WeekNumber getCurrentWeekNumber() {
		return currentWeekNumber;
	}

	public void setCurrentWeekNumber(WeekNumber currentWeekNumber) {
		this.currentWeekNumber = currentWeekNumber;
	}

	public byte getLessonOrdinalNumber() {
		return lessonOrdinalNumber;
	}

	public void setLessonOrdinalNumber(byte lessonOrdinalNumber) {
		this.lessonOrdinalNumber = lessonOrdinalNumber;
	}

	public Record getCurrentRecord() {
		return addRecord;
	}

	public void setAddRecord(Record currentRecord) {
		this.addRecord = currentRecord;
	}

	public ActionMode getAction() {
		return action;
	}

	public void setAction(ActionMode action) {
		this.action = action;
	}

	public int getEdu_level() {
		return educationLevel;
	}

	public void setEdu_level(byte edu_level) {
		this.educationLevel = edu_level;
	}

	public LessonFor getLessonFor() {
		return lessonFor;
	}

	public void setLessonFor(LessonFor lessonFor) {
		this.lessonFor = lessonFor;
	}

	public LessonPeriod getLessonPeriod() {
		return lessonPeriod;
	}

	public void setLessonPeriod(LessonPeriod lessonPeriod) {
		this.lessonPeriod = lessonPeriod;
	}

	public LessonType getLessonType() {
		return lessonType;
	}

	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}

	public Set<WeekNumber> getWeekSet() {
		return weekSet;
	}

	public void setAddWeekSet(Set<WeekNumber> weekSet) {
		this.weekSet = weekSet;
	}

	public Record getAddRecord() {
		return addRecord;
	}
}