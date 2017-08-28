package by.bsac.timetable.view.util;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.util.Checker;
import by.bsac.timetable.util.DateLabelFormatter;
import by.bsac.timetable.util.LessonFor;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.LessonType;
import by.bsac.timetable.util.WeekNumber;
import by.bsac.timetable.view.UpdateOrCancelForm;
import components.MyComboBoxModel;

public class UpdateOrCancelInitializer {
  private UpdateOrCancelForm form;

  public UpdateOrCancelInitializer(UpdateOrCancelForm form) {
    this.form = form;
  }

  /**
   * Method for initializing form field if we run form in editing mode
   * 
   * @param parent
   * @param lessonDate
   * @param record
   * @param group
   * @param weekNumber
   * @param weekDay
   * @param lessonOrdinalNumber
   */
  public void initFormFieldList(JFrame parent, Date lessonDate, Record record, Group group,
      byte weekNumber, byte weekDay, byte lessonOrdinalNumber) {

    // HibernateUtil.getSession();

    Record initialRecord, updateRecord, cancelRecord;
    try {
      // HibernateUtil.getSession();

      initialRecord = record;
      updateRecord = (Record) record.clone();
      cancelRecord = (Record) record.clone();

    } catch (CloneNotSupportedException e) {
      // HibernateUtil.closeSession();
      throw new RuntimeException(e);
    }
    form.setParent(parent);
    form.setInitialRecord(initialRecord);
    form.setUpdateRecord(updateRecord);
    form.setCancelRecord(cancelRecord);
    byte educationLevel = record.getGroup().getEduLevel();
    form.setEducationLevel(educationLevel);

    /* разбираемся с датой переданной записи */
    if (Checker.isRecordForOneDate(record)) {
      form.setUpdateLessonPeriod(LessonPeriod.FOR_ONE_DATE);
      form.setCancelLessonPeriod(LessonPeriod.FOR_ONE_DATE);
    } else {
      form.setUpdateLessonPeriod(LessonPeriod.FOR_THE_PERIOD);
      form.setCancelLessonPeriod(LessonPeriod.FOR_THE_PERIOD);
    }

    form.setLessonFor(LessonFor.subjectForToLessonFor(record.getSubjectFor()));
    form.setLessonType(LessonType.subjectTypeToLessonType(record.getSubjectType()));

    form.setAction(ActionMode.Update_Record);

    Set<WeekNumber> updateWeekSet = new HashSet<>();
    Set<WeekNumber> cancelWeekSet = new HashSet<>();
    updateWeekSet.add(WeekNumber.getWeekNumberInstance(weekNumber));
    cancelWeekSet.add(WeekNumber.getWeekNumberInstance(weekNumber));

    form.setUpdateWeekSet(updateWeekSet);
    form.setCancelWeekSet(cancelWeekSet);

    // HibernateUtil.closeSession();
  }

  /**
   * Method instantiate a new instance of {@link JDatePickerImpl} with certain date
   * 
   * @param date
   * @return
   */
  public JDatePickerImpl initDatePicker(Date date) {
    /* задаем дату для календаря в качестве опорной */

    UtilDateModel model = new UtilDateModel();
    model.setValue(date);

    Properties p = new Properties();
    p.put("text.today", "Сегодня");
    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    return new JDatePickerImpl(datePanel, new DateLabelFormatter());
  }

  /**
   * Method gets the list of {@link Chair} instances and inits JComboBox param
   * 
   * @param chairComboBox
   * @throws ServiceException
   */
  public void initChairComboBox(JComboBox<Chair> chairComboBox) throws CommandException {

    // HibernateUtil.getSession();
    try {
      List<Chair> chairsList = CommandFacade.getChairList();

      DefaultComboBoxModel<Chair> model = new MyComboBoxModel<>(chairsList);
      chairComboBox.setModel(model);
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Subject} instances and inits JComboBox param
   * 
   * @param subjectComboBox
   * @param chairComboBox
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public void initSubjectComboBox(JComboBox<Subject> subjectComboBox,
      JComboBox<Chair> chairComboBox) throws CommandException {

    // HibernateUtil.getSession();
    try {

      Chair selectedChair = (Chair) chairComboBox.getSelectedItem();
      byte educationLevel = form.getUpdateRecord().getGroup().getEduLevel();
      List<Subject> subjectsList =
          CommandFacade.getSubjectListByChairAndEduLevel(selectedChair, educationLevel);

      subjectComboBox.removeAllItems();

      if (!subjectsList.isEmpty()) {
        DefaultComboBoxModel<Subject> model = new MyComboBoxModel<>(subjectsList);
        subjectComboBox.setModel(model);
      }
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Lecturer} instances and inits JComboBox param
   * 
   * @param lecturerComboBox
   * @param chairComboBox
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public void initLecturerComboBox(JComboBox<Lecturer> lecturerComboBox,
      JComboBox<Chair> chairComboBox) throws CommandException {

    // HibernateUtil.getSession();
    Chair chair = (Chair) chairComboBox.getSelectedItem();
    try {
      List<Lecturer> lecturersList = CommandFacade.getLecturerListByChair(chair);

      lecturerComboBox.removeAllItems();
      if (!lecturersList.isEmpty()) {
        DefaultComboBoxModel<Lecturer> model = new MyComboBoxModel<>(lecturersList);
        lecturerComboBox.setModel(model);
      }
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Classroom} instances and inits JComboBox param
   * 
   * @param lecturerComboBox
   * @param chairComboBox
   * @throws ServiceException
   */
  public void initClassroomComboBox(JComboBox<Classroom> classroomComboBox)
      throws CommandException {

    // HibernateUtil.getSession();
    try {
      List<Classroom> classroomsList = CommandFacade.getClassroomList();

      DefaultComboBoxModel<Classroom> model = new MyComboBoxModel<>(classroomsList);
      classroomComboBox.setModel(model);
    } finally {
      // HibernateUtil.closeSession();
      if (classroomComboBox.getItemCount() > 0) {
        classroomComboBox.setSelectedIndex(0);
      }
    }
  }
}
