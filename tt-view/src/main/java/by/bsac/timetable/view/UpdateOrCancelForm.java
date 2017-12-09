package by.bsac.timetable.view;

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
import by.bsac.timetable.entity.builder.RecordBuilder;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.util.LessonFor;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.LessonType;
import by.bsac.timetable.util.UtilityClass;
import by.bsac.timetable.util.WeekNumber;
import by.bsac.timetable.view.component.ClassroomRenderer;
import by.bsac.timetable.view.component.MyComboBox;
import by.bsac.timetable.view.util.FormInitializer;
import by.bsac.timetable.view.util.UpdateOrCancelInitializer;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.event.ChangeEvent;
import org.jdatepicker.impl.JDatePickerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * FIXME: можно добавить флаг, которые будет использоваться для проверки того, что ничего не
 * изменено, но нажата кнопка "ОК"
 */
public class UpdateOrCancelForm extends JDialog {

  private static final Logger LOGGER = LoggerFactory.getLogger(UpdateOrCancelForm.class.getName());

  private static final long serialVersionUID = 1L;
  private static final String DATE_FORMAT = "dd-MM-yyyy";
  private static final String FONT_NAME = "Tahoma";

  private final UpdateOrCancelInitializer initializer =
      new UpdateOrCancelInitializer(this);

  private JFrame parent;

  private Record initialRecord;
  private Record updateRecord;
  private Record cancelRecord;

  private ActionMode action;

  private boolean isInitialized = false;
  private byte educationLevel = 1;

  /**
   * предварительная инициализация переменных
   */
  private LessonFor lessonFor = LessonFor.FULL_FLOW;
  private LessonPeriod updateLessonPeriod = LessonPeriod.FOR_ONE_DATE;
  private LessonPeriod cancelLessonPeriod = LessonPeriod.FOR_ONE_DATE;
  private LessonType lessonType = LessonType.LECTURE;
  private Set<WeekNumber> updateWeekSet;
  private Set<WeekNumber> cancelWeekSet;

  /**
   * Constructor for editing a record (UPDATE/CANCEL)
   */
  public UpdateOrCancelForm(JFrame parent, Date lessonDate, Group group, Record record,
      byte weekNumber, byte currentWeekDay, byte lessonOrdinalNumber) {

    initializer.initFormFieldList(parent, lessonDate, record, group, weekNumber, currentWeekDay,
        lessonOrdinalNumber);

    setResizable(false);
    setLocationRelativeTo(parent);
    setBounds(100, 100, 741, 576);

    String date = new SimpleDateFormat(DATE_FORMAT).format(lessonDate);

    getContentPane().setLayout(null);

    JPanel buttonPane = new JPanel();
    buttonPane.setBounds(0, 500, 725, 35);
    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(buttonPane);
    {
      JButton okButton = new JButton("OK");
      okButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
      okButton.setActionCommand("OK");
      buttonPane.add(okButton);
      getRootPane().setDefaultButton(okButton);
      okButton.addActionListener((ActionEvent e) -> {
        okButton.setEnabled(false);
        try {
          ICommand command = CommandProvider.getInstance().getCommand(action);
          Request request = new Request();

          if (action.equals(ActionMode.Update_Record)) {
            request.putParam("updateRecord", updateRecord);
          } else {
            request.putParam("cancelRecord", cancelRecord);
            request.putParam("cancelLessonPeriod", cancelLessonPeriod);
          }
          request.putParam("initialRecord", initialRecord);
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
      cancelButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
      cancelButton.setActionCommand("Cancel");
      buttonPane.add(cancelButton);
      cancelButton.addActionListener((ActionEvent e) -> {
        this.dispose();
      });
    }

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setBounds(0, 0, 735, 498);

    tabbedPane.addChangeListener((ChangeEvent e) -> {
      int selectedIndex = tabbedPane.getSelectedIndex();
      if (selectedIndex == 0) {
        action = ActionMode.Update_Record;
      } else {
        action = ActionMode.Cancel_Record;
      }
    });
    getContentPane().add(tabbedPane);

    if (action.equals(ActionMode.Update_Record)) {

      JPanel updateTab = new JPanel();
      tabbedPane.addTab("Обновить запись", null, updateTab, null);
      updateTab.setLayout(null);
      constructUpdateTab(updateTab, date);

      JPanel cancelTab = new JPanel();
      tabbedPane.addTab("Отменить запись", null, cancelTab, null);
      cancelTab.setLayout(null);
      constructCancelTab(cancelTab, lessonDate, date);
    }
  }

  private void constructUpdateTab(JPanel updatePanel, String lessonDate) {

    JComboBox<Classroom> classroomComboBox = new MyComboBox<>(new ClassroomRenderer<>());
    classroomComboBox.setBounds(10, 11, 159, 27);

    JLabel selectedDateLabel = new JLabel(lessonDate);

    JDatePickerImpl oneDatePickerForUpdate = initializer.initDatePicker(updateRecord.getDateFrom());
    oneDatePickerForUpdate.addActionListener((ActionEvent e) -> {
      Date dateValue = (Date) oneDatePickerForUpdate.getModel().getValue();
      updateRecord.setDateFrom(dateValue);
      updateRecord.setDateTo(dateValue);
      if (updateLessonPeriod.equals(LessonPeriod.FOR_ONE_DATE)) {
        String formatedDate = new SimpleDateFormat(DATE_FORMAT).format(dateValue);
        selectedDateLabel.setText(formatedDate);
      }
      initClassroomComboBox(classroomComboBox, dateValue, updateRecord);
    });
    JDatePickerImpl fromDatePickerForUpdate =
        initializer.initDatePicker(updateRecord.getDateFrom());
    fromDatePickerForUpdate.addActionListener((ActionEvent e) -> {
      // FIXME: проверка на то, чтобы dateFrom <= dateTo
      Date dateValue = (Date) fromDatePickerForUpdate.getModel().getValue();
      updateRecord.setDateFrom(dateValue);
      initClassroomComboBox(classroomComboBox, dateValue, updateRecord);
    });

    JDatePickerImpl toDatePickerForUpdate = initializer.initDatePicker(updateRecord.getDateTo());
    toDatePickerForUpdate.addActionListener((ActionEvent e) -> {
      // FIXME: проверка на то, чтобы dateTo >= dateFrom
      Date value = (Date) toDatePickerForUpdate.getModel().getValue();
      LOGGER.info("selected date to:" + value);
      updateRecord.setDateTo(value);
    });

    JLabel label = new JLabel("Дата:");
    label.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label.setBounds(30, 20, 77, 20);
    updatePanel.add(label);

    JLabel lblNewLabel = new JLabel("Выбраная дата:");
    lblNewLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    lblNewLabel.setBounds(206, 11, 125, 19);
    updatePanel.add(lblNewLabel);

    selectedDateLabel.setForeground(Color.RED);
    selectedDateLabel.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    selectedDateLabel.setBounds(344, 13, 115, 14);
    updatePanel.add(selectedDateLabel);

    JLabel label_1 = new JLabel("Пара назначается:");
    label_1.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_1.setBounds(30, 118, 166, 20);
    updatePanel.add(label_1);

    JLabel label_2 = new JLabel("Тип пары:");
    label_2.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_2.setBounds(30, 210, 166, 20);
    updatePanel.add(label_2);

    JPanel panel_3 = new JPanel();
    panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_3.setBounds(30, 41, 676, 77);
    updatePanel.add(panel_3);
    panel_3.setLayout(null);

    JLabel fromDateLabel = new JLabel("с"), toDateLabel = new JLabel("по");

    ButtonGroup periodBtnGroup = new ButtonGroup();

    JRadioButton radioButton = new JRadioButton("Пара на выбранную дату");
    radioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton.setBounds(6, 7, 227, 23);

    if (updateLessonPeriod.equals(LessonPeriod.FOR_ONE_DATE)) {
      radioButton.setSelected(true);
      fromDatePickerForUpdate.setVisible(false);
      toDatePickerForUpdate.setVisible(false);
      fromDateLabel.setVisible(false);
      toDateLabel.setVisible(false);
    } else {
      radioButton.setEnabled(false);
    }
    // TODO: нужно убедится в необходимости обработки данного события
    radioButton.addActionListener((ActionEvent e) -> {
      updateLessonPeriod = LessonPeriod.FOR_ONE_DATE;
      Date date = (Date) oneDatePickerForUpdate.getModel().getValue();
      updateRecord.setDateFrom(date);
      updateRecord.setDateTo(date);
    });
    periodBtnGroup.add(radioButton);
    panel_3.add(radioButton);

    JRadioButton radioButton_1 = new JRadioButton("Пара на промежуток");
    radioButton_1.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_1.setBounds(6, 44, 190, 23);

    if (updateLessonPeriod.equals(LessonPeriod.FOR_THE_PERIOD)) {
      radioButton_1.setSelected(true);
    } else {
      radioButton_1.setEnabled(false);
    }
    // TODO: нужно убедится в необходимости обработки данного события
    radioButton_1.addActionListener((ActionEvent e) -> {
      updateLessonPeriod = LessonPeriod.FOR_THE_PERIOD;
      updateRecord.setDateFrom((Date) fromDatePickerForUpdate.getModel().getValue());
      updateRecord.setDateTo((Date) toDatePickerForUpdate.getModel().getValue());
    });
    panel_3.add(radioButton_1);
    periodBtnGroup.add(radioButton_1);

    JLabel label_3 = new JLabel("выбрать другую дату:");
    label_3.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    label_3.setBounds(273, 10, 180, 17);
    panel_3.add(label_3);

    JPanel panel = new JPanel();
    panel.setBounds(463, 7, 204, 33);
    panel.add(oneDatePickerForUpdate);
    panel_3.add(panel);

    JPanel panel_1 = new JPanel();
    panel_1.setBounds(221, 37, 204, 33);
    panel_1.add(fromDatePickerForUpdate);
    panel_3.add(panel_1);

    JPanel panel_2 = new JPanel();
    panel_2.setBounds(463, 40, 204, 33);
    panel_2.add(toDatePickerForUpdate);
    panel_3.add(panel_2);

    fromDateLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    fromDateLabel.setBounds(200, 48, 18, 14);
    panel_3.add(fromDateLabel);

    toDateLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    toDateLabel.setBounds(435, 48, 18, 14);
    panel_3.add(toDateLabel);

    JPanel panel_4 = new JPanel();
    panel_4.setLayout(null);
    panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_4.setBounds(30, 139, 676, 69);
    updatePanel.add(panel_4);

    ButtonGroup classForBtnGroup = new ButtonGroup();

    JRadioButton rdbtnNewRadioButton = new JRadioButton("для всего потока");
    rdbtnNewRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    rdbtnNewRadioButton.setBounds(6, 7, 166, 23);

    if (lessonFor.equals(LessonFor.FULL_FLOW)) {
      rdbtnNewRadioButton.setSelected(true);
    } else {
      rdbtnNewRadioButton.setEnabled(false);
    }
    rdbtnNewRadioButton.addActionListener((ActionEvent e) -> {
      lessonFor = LessonFor.FULL_FLOW;
    });
    classForBtnGroup.add(rdbtnNewRadioButton);
    panel_4.add(rdbtnNewRadioButton);

    JRadioButton radioButton_2 = new JRadioButton("для всей группы");
    radioButton_2.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_2.setBounds(226, 7, 152, 23);

    if (lessonFor.equals(LessonFor.FULL_GROUP)) {
      radioButton_2.setSelected(true);
    } else {
      radioButton_2.setEnabled(false);
    }
    radioButton_2.addActionListener((ActionEvent e) -> {
      lessonFor = LessonFor.FULL_GROUP;
    });
    classForBtnGroup.add(radioButton_2);
    panel_4.add(radioButton_2);

    JRadioButton radioButton_3 = new JRadioButton("для первой подгруппы");
    radioButton_3.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_3.setBounds(442, 7, 193, 23);

    if (lessonFor.equals(LessonFor.FIRST_SUBGROUP)) {
      radioButton_3.setSelected(true);
    } else {
      radioButton_3.setEnabled(false);
    }
    radioButton_3.addActionListener((ActionEvent e) -> {
      lessonFor = LessonFor.FIRST_SUBGROUP;
    });
    classForBtnGroup.add(radioButton_3);
    panel_4.add(radioButton_3);

    JRadioButton radioButton_4 = new JRadioButton("для второй подгруппы");
    radioButton_4.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_4.setBounds(442, 38, 193, 23);

    if (lessonFor.equals(LessonFor.SECOND_SUBGROUP)) {
      radioButton_4.setSelected(true);
    } else {
      radioButton_4.setEnabled(false);
    }
    radioButton_4.addActionListener((ActionEvent e) -> {
      lessonFor = LessonFor.SECOND_SUBGROUP;
    });
    classForBtnGroup.add(radioButton_4);
    panel_4.add(radioButton_4);

    JLabel label_11 = new JLabel("(поточная пара)");
    label_11.setFont(new Font(FONT_NAME, Font.ITALIC, 14));
    label_11.setBounds(27, 28, 135, 14);
    panel_4.add(label_11);

    JPanel panel_5 = new JPanel();
    panel_5.setLayout(null);
    panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_5.setBounds(30, 233, 676, 53);
    updatePanel.add(panel_5);

    ButtonGroup classTypeBtnGroup = new ButtonGroup();

    JRadioButton radioButton_5 = new JRadioButton("Лекция");
    radioButton_5.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_5.setBounds(6, 3, 94, 23);

    if (lessonType.equals(LessonType.LECTURE)) {
      radioButton_5.setSelected(true);
    }
    radioButton_5.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.LECTURE;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_5);
    panel_5.add(radioButton_5);

    JRadioButton radioButton_6 = new JRadioButton("Практическая");
    radioButton_6.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_6.setBounds(124, 7, 131, 23);

    if (lessonType.equals(LessonType.PRACTICE_WORK)) {
      radioButton_6.setSelected(true);
    }
    radioButton_6.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.PRACTICE_WORK;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_6);
    panel_5.add(radioButton_6);

    JRadioButton radioButton_7 = new JRadioButton("Консультация");
    radioButton_7.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_7.setBounds(257, 7, 140, 23);

    if (lessonType.equals(LessonType.CONSULTATION)) {
      radioButton_7.setSelected(true);
    }
    radioButton_7.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.CONSULTATION;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_7);
    panel_5.add(radioButton_7);

    JRadioButton radioButton_8 = new JRadioButton("Зачет");
    radioButton_8.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_8.setBounds(422, 7, 74, 23);

    if (lessonType.equals(LessonType.CREDIT)) {
      radioButton_8.setSelected(true);
    }
    radioButton_8.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.CREDIT;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_8);
    panel_5.add(radioButton_8);

    JRadioButton radioButton_9 = new JRadioButton("Экзамен");
    radioButton_9.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_9.setBounds(361, 25, 94, 23);

    if (lessonType.equals(LessonType.EXAM)) {
      radioButton_9.setSelected(true);
    }
    radioButton_9.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.EXAM;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_9);
    panel_5.add(radioButton_9);

    JRadioButton radioButton_10 = new JRadioButton("Лабораторная");
    radioButton_10.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_10.setBounds(52, 25, 131, 23);

    if (lessonType.equals(LessonType.LABORATORY_WORK)) {
      radioButton_10.setSelected(true);
    }
    radioButton_10.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.LABORATORY_WORK;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_10);
    panel_5.add(radioButton_10);

    JRadioButton radioButton_11 = new JRadioButton("Учебное занятие");
    radioButton_11.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_11.setBounds(521, 5, 149, 23);

    if (lessonType.equals(LessonType.TRAINING_SESSION)) {
      radioButton_11.setSelected(true);
    }
    radioButton_11.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.TRAINING_SESSION;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_11);
    panel_5.add(radioButton_11);

    JRadioButton radioButton_12 = new JRadioButton("Переезд");
    radioButton_12.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    radioButton_12.setBounds(521, 27, 149, 23);

    if (lessonType.equals(LessonType.MOVE)) {
      radioButton_12.setSelected(true);
    }
    radioButton_12.addActionListener((ActionEvent e) -> {
      lessonType = LessonType.MOVE;
      updateRecord.setSubjectType(lessonType.lessonTypeToSubjectType());
    });
    classTypeBtnGroup.add(radioButton_12);
    panel_5.add(radioButton_12);

    JPanel panel_6 = new JPanel();
    panel_6.setLayout(null);
    panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_6.setBounds(30, 313, 676, 77);
    updatePanel.add(panel_6);

    JComboBox<Chair> updateChairComboBox = new MyComboBox<>();
    updateChairComboBox.setBounds(20, 36, 206, 27);

    panel_6.add(updateChairComboBox);

    JLabel lblNewLabel_2 = new JLabel("Кафедра");
    lblNewLabel_2.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    lblNewLabel_2.setBounds(20, 11, 112, 20);
    panel_6.add(lblNewLabel_2);

    JComboBox<Lecturer> updateLecturerComboBox = new MyComboBox<>();
    updateLecturerComboBox.setBounds(248, 36, 179, 27);
    updateLecturerComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
      if (evt.getStateChange() == ItemEvent.SELECTED) {
        if (updateLecturerComboBox.getItemCount() > 0) {
          updateRecord.setLecturer((Lecturer) updateLecturerComboBox.getSelectedItem());
        }
      }
    });
    panel_6.add(updateLecturerComboBox);

    JLabel label_7 = new JLabel("Преподаватель");
    label_7.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    label_7.setBounds(246, 11, 112, 20);
    panel_6.add(label_7);

    JLabel label_8 = new JLabel("Предмет");
    label_8.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    label_8.setBounds(450, 11, 112, 20);
    panel_6.add(label_8);

    JComboBox<Subject> updateSubjectComboBox = new MyComboBox<>();
    updateSubjectComboBox.setBounds(450, 36, 216, 27);
    updateSubjectComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
      if (evt.getStateChange() == ItemEvent.SELECTED) {
        if (updateSubjectComboBox.getItemCount() > 0) {
          updateRecord.setSubject((Subject) updateSubjectComboBox.getSelectedItem());
        }
      }
    });
    panel_6.add(updateSubjectComboBox);

    updateChairComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
      if (evt.getStateChange() == ItemEvent.SELECTED
          || evt.getStateChange() == ItemEvent.ITEM_STATE_CHANGED) {
        try {
          initializer.initSubjectComboBox(updateSubjectComboBox, updateChairComboBox);
          initializer.initLecturerComboBox(updateLecturerComboBox, updateChairComboBox);
          if (isInitialized) {
            if (updateSubjectComboBox.getItemCount() > 0) {
              updateRecord.setSubject((Subject) updateSubjectComboBox.getSelectedItem());
            }
            if (updateLecturerComboBox.getItemCount() > 0) {
              updateRecord.setLecturer((Lecturer) updateLecturerComboBox.getSelectedItem());
            }
          }
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        }
      }
    });

    JLabel label_6 = new JLabel("Дисциплина:");
    label_6.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_6.setBounds(30, 289, 166, 20);
    updatePanel.add(label_6);

    JPanel weekPanel = new JPanel();
    JLabel dublicateOnWeekLabel = new JLabel("Дублировать (неделя):");
    weekPanel.setLayout(null);
    weekPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
    weekPanel.setBounds(55, 420, 247, 43);
    updatePanel.add(weekPanel);

    if (updateLessonPeriod.equals(LessonPeriod.FOR_ONE_DATE)) {
      weekPanel.setVisible(false);
      dublicateOnWeekLabel.setVisible(false);
    }

    JCheckBox firstWeekForAdd = new JCheckBox("1");
    firstWeekForAdd.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    firstWeekForAdd.setBounds(10, 10, 42, 23);
    if (updateWeekSet.contains(WeekNumber.FIRST)) {
      firstWeekForAdd.setSelected(true);
    }
    firstWeekForAdd.setEnabled(false);
    firstWeekForAdd.addActionListener((ActionEvent e) -> {
      if (firstWeekForAdd.isSelected()) {
        updateWeekSet.add(WeekNumber.FIRST);
      } else {
        updateWeekSet.remove(WeekNumber.FIRST);
      }
      /*JOptionPane.showMessageDialog(this.getContentPane(), updateWeekSet);*/
    });
    weekPanel.add(firstWeekForAdd);

    JCheckBox secondWeekForAdd = new JCheckBox("2");
    secondWeekForAdd.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    secondWeekForAdd.setBounds(65, 10, 42, 23);
    if (updateWeekSet.contains(WeekNumber.SECOND)) {
      secondWeekForAdd.setSelected(true);
    }
    secondWeekForAdd.setEnabled(false);
    secondWeekForAdd.addActionListener((ActionEvent e) -> {
      if (secondWeekForAdd.isSelected()) {
        updateWeekSet.add(WeekNumber.SECOND);
      } else {
        updateWeekSet.remove(WeekNumber.SECOND);
      }
    });
    weekPanel.add(secondWeekForAdd);

    JCheckBox thirdWeekForAdd = new JCheckBox("3");
    thirdWeekForAdd.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    thirdWeekForAdd.setBounds(128, 10, 42, 23);
    if (updateWeekSet.contains(WeekNumber.THIRD)) {
      thirdWeekForAdd.setSelected(true);
    }
    thirdWeekForAdd.setEnabled(false);
    thirdWeekForAdd.addActionListener((ActionEvent e) -> {
      if (thirdWeekForAdd.isSelected()) {
        updateWeekSet.add(WeekNumber.THIRD);
      } else {
        updateWeekSet.remove(WeekNumber.THIRD);
      }
    });
    weekPanel.add(thirdWeekForAdd);

    JCheckBox fourthWeekForAdd = new JCheckBox("4");
    fourthWeekForAdd.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    fourthWeekForAdd.setBounds(195, 10, 42, 23);
    if (updateWeekSet.contains(WeekNumber.FOURTH)) {
      fourthWeekForAdd.setSelected(true);
    }
    fourthWeekForAdd.setEnabled(false);
    fourthWeekForAdd.addActionListener((ActionEvent e) -> {
      if (fourthWeekForAdd.isSelected()) {
        updateWeekSet.add(WeekNumber.FOURTH);
      } else {
        updateWeekSet.remove(WeekNumber.FOURTH);
      }
    });
    weekPanel.add(fourthWeekForAdd);

    JPanel panel_10 = new JPanel();
    panel_10.setLayout(null);
    panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_10.setBounds(480, 420, 197, 46);
    updatePanel.add(panel_10);

    dublicateOnWeekLabel.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    dublicateOnWeekLabel.setBounds(55, 394, 215, 20);
    updatePanel.add(dublicateOnWeekLabel);

    classroomComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
      if (classroomComboBox.getItemCount() > 0) {
        updateRecord.setClassroom((Classroom) classroomComboBox.getSelectedItem());
      }
    });

    panel_10.add(classroomComboBox);

    JLabel label_10 = new JLabel("Аудитория:");
    label_10.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_10.setBounds(480, 403, 166, 20);
    updatePanel.add(label_10);

    /*
     * устанавливаем значения выпадающих списков согласно переданной записи (currentRecord)
     */
    try {
      FormInitializer.initChairComboBox(updateChairComboBox);
      if (updateChairComboBox.getItemCount() != 0) {
        Chair chair = updateRecord.getLecturer().getChair();
        LOGGER.info("idChair:" + chair.getIdChair());
        // updateChairComboBox.setSelectedItem(chair);
        UtilityClass.selectCBItemById(updateChairComboBox, chair);

        FormInitializer.initSubjectComboBox(updateSubjectComboBox, updateChairComboBox,
            educationLevel);
        FormInitializer.initLecturerComboBox(updateLecturerComboBox, updateChairComboBox);

        Subject subject = updateRecord.getSubject();
        LOGGER.info("idSubject:" + subject.getIdSubject());
        UtilityClass.selectCBItemById(updateSubjectComboBox, subject);

        Lecturer lecturer = updateRecord.getLecturer();
        LOGGER.info("idLecturer:" + lecturer.getIdLecturer());
        UtilityClass.selectCBItemById(updateLecturerComboBox, lecturer);
      }
      Date dateValue = parseDate(updateRecord.getDateFrom().getTime());
      initClassroomComboBox(classroomComboBox, dateValue, updateRecord);

      Classroom classroom = updateRecord.getClassroom();
      LOGGER.info("classroom:" + classroom.getName());
      UtilityClass.selectCBItemById(classroomComboBox, classroom);
      isInitialized = true;

    } catch (CommandException ex) {
      LOGGER.error(ex.getCause().getMessage(), ex);
      JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
    }
  }

  private void constructCancelTab(JPanel cancelTab, Date lessonDate, String date) {
    /*-------------------------------------------------*/
    /*----------панель для отмены расписания-----------*/
    /*-------------------------------------------------*/

    JDatePickerImpl oneDatePickerForCanceling = initializer.initDatePicker(lessonDate);
    oneDatePickerForCanceling.addActionListener((ActionEvent e) -> {
      Date value = (Date) oneDatePickerForCanceling.getModel().getValue();
      LOGGER.info("selected date:" + value);
      cancelRecord.setDateFrom(value);
      cancelRecord.setDateTo(value);
    });

    JDatePickerImpl fromDatePickerForCanceling =
        initializer.initDatePicker(cancelRecord.getDateFrom());
    fromDatePickerForCanceling.addActionListener((ActionEvent e) -> {
      Date value = (Date) fromDatePickerForCanceling.getModel().getValue();
      LOGGER.info("selected date:" + value);
      cancelRecord.setDateFrom(value);
    });
    JDatePickerImpl toDatePickerForCanceling = initializer.initDatePicker(cancelRecord.getDateTo());
    toDatePickerForCanceling.addActionListener((ActionEvent e) -> {
      Date value = (Date) toDatePickerForCanceling.getModel().getValue();
      LOGGER.info("selected date:" + value);
      cancelRecord.setDateTo(value);
    });
    ButtonGroup periodBtnGroup = new ButtonGroup();

    JLabel label_13 = new JLabel("Выбраная дата:");
    label_13.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    label_13.setBounds(206, 11, 125, 19);
    cancelTab.add(label_13);

    JLabel label_14 = new JLabel(date);
    label_14.setForeground(Color.RED);
    label_14.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_14.setBounds(344, 13, 115, 14);
    cancelTab.add(label_14);

    JLabel label_15 = new JLabel("Пара отменяется:");
    label_15.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_15.setBounds(30, 194, 166, 20);
    cancelTab.add(label_15);

    JPanel panel_11 = new JPanel();
    panel_11.setLayout(null);
    panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_11.setBounds(30, 225, 676, 69);
    cancelTab.add(panel_11);

    JRadioButton forFullFlowRadioButton = new JRadioButton("для всего потока");
    forFullFlowRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    forFullFlowRadioButton.setBounds(6, 7, 166, 23);

    if (lessonFor.equals(LessonFor.FULL_FLOW)) {
      forFullFlowRadioButton.setSelected(true);
    } else {
      forFullFlowRadioButton.setEnabled(false);
    }
    panel_11.add(forFullFlowRadioButton);

    JRadioButton forFirstGroupRadioButton = new JRadioButton("для всей группы");
    forFirstGroupRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (lessonFor.equals(LessonFor.FULL_GROUP)) {
      forFirstGroupRadioButton.setSelected(true);
    } else {
      forFirstGroupRadioButton.setEnabled(false);
    }

    forFirstGroupRadioButton.setBounds(226, 7, 152, 23);
    panel_11.add(forFirstGroupRadioButton);

    JRadioButton forFirstSubgroupRadioButton = new JRadioButton("для первой подгруппы");
    forFirstSubgroupRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (lessonFor.equals(LessonFor.FIRST_SUBGROUP)) {
      forFirstSubgroupRadioButton.setSelected(true);
    } else {
      forFirstSubgroupRadioButton.setEnabled(false);
    }
    forFirstSubgroupRadioButton.setBounds(442, 7, 193, 23);
    panel_11.add(forFirstSubgroupRadioButton);

    JRadioButton forSecondSubgroupRadioButton = new JRadioButton("для второй подгруппы");
    forSecondSubgroupRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (lessonFor.equals(LessonFor.SECOND_SUBGROUP)) {
      forSecondSubgroupRadioButton.setSelected(true);
    } else {
      forSecondSubgroupRadioButton.setEnabled(false);
    }
    forSecondSubgroupRadioButton.setBounds(442, 38, 193, 23);
    panel_11.add(forSecondSubgroupRadioButton);

    JLabel label_16 = new JLabel("(поточная пара)");
    label_16.setFont(new Font(FONT_NAME, Font.ITALIC, 14));
    label_16.setBounds(27, 28, 135, 14);
    panel_11.add(label_16);

    JPanel panel_12 = new JPanel();
    panel_12.setLayout(null);
    panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_12.setBounds(30, 41, 676, 142);
    cancelTab.add(panel_12);

    final JPanel cancelByTheDatePanel = new JPanel(), cancelFromTheDatePanel = new JPanel(),
        cancelToTheDatePanel = new JPanel(), cancelByWeekNumberPanel = new JPanel();
    final JLabel selectAnotherCancelDateLabel = new JLabel("выбрать другую дату:"),
        cancelFromLabel = new JLabel("с"), cancelToLabel = new JLabel("по");

    final JRadioButton forOneDateCancelRadioButton = new JRadioButton("На выбранную дату"),
        forRangeDateCancelRadioButton = new JRadioButton("На промежуток"),
        forWeekNumberCancelRadioButton = new JRadioButton("По номеру недели");

    forOneDateCancelRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    forOneDateCancelRadioButton.setBounds(6, 7, 227, 23);

    if (cancelLessonPeriod.equals(LessonPeriod.FOR_ONE_DATE)) {
      forOneDateCancelRadioButton.setSelected(true);
      forRangeDateCancelRadioButton.setEnabled(false);
      forWeekNumberCancelRadioButton.setEnabled(false);
      oneDatePickerForCanceling.setVisible(false);
      selectAnotherCancelDateLabel.setVisible(false);

      cancelFromLabel.setVisible(false);
      cancelFromTheDatePanel.setVisible(false);
      cancelToLabel.setVisible(false);
      cancelToTheDatePanel.setVisible(false);
      cancelByWeekNumberPanel.setVisible(false);
    }
    forOneDateCancelRadioButton.addActionListener((ActionEvent e) -> {
      cancelLessonPeriod = LessonPeriod.FOR_ONE_DATE;
      Date value = (Date) oneDatePickerForCanceling.getModel().getValue();
      cancelRecord.setDateFrom(value);
      cancelRecord.setDateTo(value);

      // TODO: пока убрал, так как это мешает инициализации
      // cancelByTheDatePanel.setVisible(true);
      // selectAnotherCancelDateLabel.setVisible(true);
      // cancelFromLabel.setVisible(false);
      // cancelFromTheDatePanel.setVisible(false);
      // cancelToLabel.setVisible(false);
      // cancelToTheDatePanel.setVisible(false);
      // cancelByWeekNumberPanel.setVisible(false);
    });
    periodBtnGroup.add(forOneDateCancelRadioButton);
    panel_12.add(forOneDateCancelRadioButton);

    // forRangeDateCancelRadioButton= new JRadioButton("На промежуток") ;
    forRangeDateCancelRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    forRangeDateCancelRadioButton.setBounds(6, 54, 190, 23);

    if (cancelLessonPeriod.equals(LessonPeriod.FOR_THE_PERIOD)) {
      forRangeDateCancelRadioButton.setSelected(true);
    }
    forRangeDateCancelRadioButton.addActionListener((ActionEvent e) -> {
      cancelLessonPeriod = LessonPeriod.FOR_THE_PERIOD;
      Date dateFromValue = (Date) fromDatePickerForCanceling.getModel().getValue();
      Date dateToValue = (Date) toDatePickerForCanceling.getModel().getValue();

      cancelRecord.setDateFrom(dateFromValue);
      cancelRecord.setDateTo(dateToValue);

      // TODO: пока убрал, так как это мешает инициализации
      // oneDatePickerForCanceling.setEnabled(false);
      //
      // cancelFromTheDatePanel.setVisible(true);
      // cancelFromLabel.setVisible(true);
      // cancelToTheDatePanel.setVisible(true);
      // cancelToLabel.setVisible(true);
      // selectAnotherCancelDateLabel.setVisible(false);
      // cancelByTheDatePanel.setVisible(false);
      // cancelByWeekNumberPanel.setVisible(false);
    });
    periodBtnGroup.add(forRangeDateCancelRadioButton);
    panel_12.add(forRangeDateCancelRadioButton);

    selectAnotherCancelDateLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    selectAnotherCancelDateLabel.setBounds(273, 10, 180, 17);
    panel_12.add(selectAnotherCancelDateLabel);

    cancelByTheDatePanel.setBounds(463, 7, 204, 33);
    cancelByTheDatePanel.add(oneDatePickerForCanceling);
    panel_12.add(cancelByTheDatePanel);

    cancelFromTheDatePanel.setBounds(221, 47, 204, 33);
    cancelFromTheDatePanel.add(fromDatePickerForCanceling);
    // cancelFromTheDatePanel.setVisible(false);
    panel_12.add(cancelFromTheDatePanel);

    cancelToTheDatePanel.setBounds(463, 50, 204, 33);
    cancelToTheDatePanel.add(toDatePickerForCanceling);
    // cancelToTheDatePanel.setVisible(false);
    panel_12.add(cancelToTheDatePanel);

    cancelFromLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    cancelFromLabel.setBounds(200, 58, 18, 14);
    // cancelFromLabel.setVisible(false);
    panel_12.add(cancelFromLabel);

    cancelToLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    cancelToLabel.setBounds(435, 58, 18, 14);
    // cancelToLabel.setVisible(false);
    panel_12.add(cancelToLabel);

    cancelByWeekNumberPanel.setLayout(null);
    cancelByWeekNumberPanel.setBorder(null);
    cancelByWeekNumberPanel.setBounds(273, 94, 247, 33);
    // cancelByWeekNumberPanel.setVisible(false);
    panel_12.add(cancelByWeekNumberPanel);

    JCheckBox firstWeekCBForCancel = new JCheckBox("1");
    firstWeekCBForCancel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (cancelWeekSet.contains(WeekNumber.FIRST)) {
      firstWeekCBForCancel.setSelected(true);
    }
    firstWeekCBForCancel.setEnabled(false);
    firstWeekCBForCancel.addActionListener((ActionEvent e) -> {
      if (firstWeekCBForCancel.isSelected()) {
        cancelWeekSet.add(WeekNumber.FIRST);
      } else {
        cancelWeekSet.remove(WeekNumber.FIRST);
      }
      JOptionPane.showMessageDialog(this.getContentPane(), updateWeekSet);
    });
    firstWeekCBForCancel.setBounds(10, 10, 42, 23);
    cancelByWeekNumberPanel.add(firstWeekCBForCancel);

    JCheckBox secondWeekCBForCancel = new JCheckBox("2");
    secondWeekCBForCancel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (cancelWeekSet.contains(WeekNumber.SECOND)) {
      secondWeekCBForCancel.setSelected(true);
    }
    secondWeekCBForCancel.setEnabled(false);
    secondWeekCBForCancel.addActionListener((ActionEvent e) -> {
      if (secondWeekCBForCancel.isSelected()) {
        cancelWeekSet.add(WeekNumber.SECOND);
      } else {
        cancelWeekSet.remove(WeekNumber.SECOND);
      }
    });
    secondWeekCBForCancel.setBounds(65, 10, 42, 23);
    cancelByWeekNumberPanel.add(secondWeekCBForCancel);

    JCheckBox thirdWeekCBForCancel = new JCheckBox("3");
    thirdWeekCBForCancel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (cancelWeekSet.contains(WeekNumber.THIRD)) {
      thirdWeekCBForCancel.setSelected(true);
    }
    thirdWeekCBForCancel.setEnabled(false);
    thirdWeekCBForCancel.addActionListener((ActionEvent e) -> {
      if (thirdWeekCBForCancel.isSelected()) {
        cancelWeekSet.add(WeekNumber.THIRD);
      } else {
        cancelWeekSet.remove(WeekNumber.THIRD);
      }
    });
    thirdWeekCBForCancel.setBounds(128, 10, 42, 23);
    cancelByWeekNumberPanel.add(thirdWeekCBForCancel);

    JCheckBox fourthWeekCBForCancel = new JCheckBox("4");
    fourthWeekCBForCancel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));

    if (cancelWeekSet.contains(WeekNumber.FOURTH)) {
      fourthWeekCBForCancel.setSelected(true);
    }
    fourthWeekCBForCancel.setEnabled(false);
    fourthWeekCBForCancel.addActionListener((ActionEvent e) -> {
      if (fourthWeekCBForCancel.isSelected()) {
        cancelWeekSet.add(WeekNumber.FOURTH);
      } else {
        cancelWeekSet.remove(WeekNumber.FOURTH);
      }
    });
    fourthWeekCBForCancel.setBounds(195, 10, 42, 23);
    cancelByWeekNumberPanel.add(fourthWeekCBForCancel);

    // JRadioButton forWeekNumberCancelRadioButton = new JRadioButton("По
    // номеру недели");
    forWeekNumberCancelRadioButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    forWeekNumberCancelRadioButton.setBounds(6, 104, 190, 23);

    if (cancelLessonPeriod.equals(LessonPeriod.FOR_WEEK_NUMBER)) {
      forWeekNumberCancelRadioButton.setSelected(true);
    }

    forWeekNumberCancelRadioButton.addActionListener((ActionEvent e) -> {
      cancelLessonPeriod = LessonPeriod.FOR_WEEK_NUMBER;

      // TODO: пока убрал, так как это мешает инициализации
      // cancelByWeekNumberPanel.setVisible(true);
      // cancelFromLabel.setVisible(false);
      // cancelFromTheDatePanel.setVisible(false);
      // cancelToLabel.setVisible(false);
      // cancelToTheDatePanel.setVisible(false);
      // selectAnotherCancelDateLabel.setVisible(false);
      // cancelByTheDatePanel.setVisible(false);
    });
    periodBtnGroup.add(forWeekNumberCancelRadioButton);
    panel_12.add(forWeekNumberCancelRadioButton);

    JLabel label_19 = new JLabel("Дата:");
    label_19.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label_19.setBounds(30, 20, 77, 20);
    cancelTab.add(label_19);
  }

  public JFrame getParent() {
    return parent;
  }

  public void setParent(JFrame parent) {
    this.parent = parent;
  }

  public ActionMode getAction() {
    return action;
  }

  public void setAction(ActionMode action) {
    this.action = action;
  }

  public LessonFor getLessonFor() {
    return lessonFor;
  }

  public void setLessonFor(LessonFor lessonFor) {
    this.lessonFor = lessonFor;
  }

  public LessonPeriod getUpdateLessonPeriod() {
    return updateLessonPeriod;
  }

  public void setUpdateLessonPeriod(LessonPeriod updateLessonPeriod) {
    this.updateLessonPeriod = updateLessonPeriod;
  }

  public LessonPeriod getCancelLessonPeriod() {
    return cancelLessonPeriod;
  }

  public void setCancelLessonPeriod(LessonPeriod cancelLessonPeriod) {
    this.cancelLessonPeriod = cancelLessonPeriod;
  }

  public LessonType getLessonType() {
    return lessonType;
  }

  public void setLessonType(LessonType lessonType) {
    this.lessonType = lessonType;
  }

  public Set<WeekNumber> getUpdateWeekSet() {
    return updateWeekSet;
  }

  public void setUpdateWeekSet(Set<WeekNumber> updateWeekSet) {
    this.updateWeekSet = updateWeekSet;
  }

  public Set<WeekNumber> getCancelWeekSet() {
    return cancelWeekSet;
  }

  public void setCancelWeekSet(Set<WeekNumber> cancelWeekSet) {
    this.cancelWeekSet = cancelWeekSet;
  }

  public Record getUpdateRecord() {
    return updateRecord;
  }

  public void setUpdateRecord(Record updateRecord) {
    this.updateRecord = updateRecord;
  }

  public Record getCancelRecord() {
    return cancelRecord;
  }

  public void setCancelRecord(Record cancelRecord) {
    this.cancelRecord = cancelRecord;
  }

  public Record getInitialRecord() {
    return initialRecord;
  }

  public void setInitialRecord(Record initialRecord) {
    this.initialRecord = initialRecord;
  }

  public void setEducationLevel(byte educationLevel) {
    this.educationLevel = educationLevel;
  }

  private void initClassroomComboBox(JComboBox<Classroom> classroomComboBox, Date referenceDate,
      Record updateRecord) {
    try {
      Record record = new RecordBuilder()
          .buildWeekDay(updateRecord.getWeekDay())
          .buildWeekNumber(updateRecord.getWeekNumber())
          .buildSubjOrdinalNumber(updateRecord.getSubjOrdinalNumber())
          .build();
      FormInitializer.initClassroomComboBox(
          classroomComboBox, referenceDate, record);
    } catch (CommandException ex) {
      LOGGER.error(ex.getCause().getMessage(), ex);
      JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
    }
  }

  private Date parseDate(long dateValue) {
    return new Date(dateValue);
  }
}
