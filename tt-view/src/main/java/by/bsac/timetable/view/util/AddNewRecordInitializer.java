package by.bsac.timetable.view.util;

import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.entity.SubjectType;
import by.bsac.timetable.entity.builder.RecordBuilder;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.util.Checker;
import by.bsac.timetable.util.DateLabelFormatter;
import by.bsac.timetable.util.LessonFor;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.LessonType;
import by.bsac.timetable.util.WeekNumber;
import by.bsac.timetable.view.AddNewRecordForm;

public final class AddNewRecordInitializer {

  private AddNewRecordForm form;

  public AddNewRecordInitializer(AddNewRecordForm frame) {
    this.form = frame;
  }

  /**
   * Method for initializing form field if we run form in adding mode
   * 
   * @param parent
   * @param lessonDate
   * @param group
   * @param weekNumber
   * @param weekDay
   * @param lessonOrdinalNumber
   */
  public void initFormFieldList(JFrame parent, Date lessonDate, Group group, byte weekNumber,
      byte weekDay, byte lessonOrdinalNumber) {

    // HibernateUtil.getSession();
    form.setParent(parent);
    form.setAction(ActionMode.Add_Record);

    form.setLessonPeriod(LessonPeriod.FOR_ONE_DATE);

    if (Checker.isGroupInFlow(group)) {
      form.setLessonFor(LessonFor.FULL_FLOW);
    } else {
      form.setLessonFor(LessonFor.FULL_GROUP);
    }
    form.setLessonType(LessonType.LECTURE);

    form.setCurrentWeekNumber(WeekNumber.getWeekNumberInstance(weekNumber));
    form.getWeekSet().add(form.getCurrentWeekNumber());
    form.setCurrentWeekDay(weekDay);
    form.setLessonOrdinalNumber(lessonOrdinalNumber);
    form.setEdu_level(group.getEduLevel());

    SubjectType subjectType = form.getLessonType().lessonTypeToSubjectType();
    SubjectFor subjectFor = form.getLessonFor().lessonForToSubjectFor();

    Record addRecord =
        new RecordBuilder().buildDateFrom(lessonDate).buildDateTo(lessonDate).buildGroup(group)
            .buildSubjectFor(subjectFor).buildSubjectType(subjectType).buildWeekNumber(weekNumber)
            .buildWeekDay(weekDay).buildSubjOrdinalNumber(lessonOrdinalNumber).build();
    form.setAddRecord(addRecord);
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
}
