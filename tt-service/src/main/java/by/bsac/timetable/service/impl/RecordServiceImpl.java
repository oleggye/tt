package by.bsac.timetable.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.entity.Cancellation;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.builder.CancellationBuilder;
import by.bsac.timetable.entity.builder.RecordBuilder;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.DateUtil;
import by.bsac.timetable.util.LessonFor;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.WeekNumber;

@Service
public class RecordServiceImpl implements IRecordService {
  private static final RecordBuilder RECORD_BUILDER = new RecordBuilder();
  private static final CancellationBuilder CANCEL_BUILDER = new CancellationBuilder();
  @Autowired
  private IValidationService service;

  @Autowired
  private IRecordDAO recordDAO;

  @Autowired
  private ICancellationDAO cancellationDao;

  @Autowired
  private IGroupDAO groupDao;

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addRecord(Record record) throws ServiceException, ServiceValidationException {
    service.validateRecord(record, false);

    LessonFor lessonFor = LessonFor.subjectForToLessonFor(record.getSubjectFor());
    try {
      /* если это пара для всего потока, то нужно добавить всем */
      if (lessonFor.equals(LessonFor.FULL_FLOW)) {
        addFlowRecord(record);
      } else {
        checkRecordForConflict(record);
        recordDAO.add(record);
      }
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при вставке", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addRecordByAtWeekSet(Record record, Set<WeekNumber> weekSet)
      throws ServiceException, ServiceValidationException {
    service.validateRecord(record, false);

    List<Record> resultList = new LinkedList<>();

    LessonFor lessonFor = LessonFor.subjectForToLessonFor(record.getSubjectFor());
    try {
      Iterator<WeekNumber> it = weekSet.iterator();
      while (it.hasNext()) {
        WeekNumber weekNumber = it.next();
        byte weekNumberValue = weekNumber.getWeekNumber();
        record.setWeekNumber(weekNumberValue);

        /* если это пара для всего потока, то нужно добавить всем */
        if (lessonFor.equals(LessonFor.FULL_FLOW)) {
          List<Record> constructFlowRecordList = constructFlowRecordList(record);
          resultList.addAll(constructFlowRecordList);
        } else {
          checkRecordForConflict(record);
          resultList.add(RECORD_BUILDER.copy(record));
        }
      }
      recordDAO.addAll(resultList);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при вставке", e);
    }
  }


  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateRecord(Record initialRecord, Record updateRecord)
      throws ServiceException, ServiceValidationException {
    service.validateRecord(initialRecord, true);
    service.validateRecord(updateRecord, false);

    /*
     * если это пара на одну дату и дату изменили, то нужно обновить номер недели и дня
     */
    if (updateRecord.getDateFrom().equals(updateRecord.getDateTo())
        && !updateRecord.getDateFrom().equals(initialRecord.getDateFrom())
        && !updateRecord.getDateTo().equals(initialRecord.getDateTo())) {

      /*System.out.println(updateRecord.getDateFrom().getClass());*/
      byte weekDay = DateUtil.getWeekDay(updateRecord.getDateFrom());
      byte weekNumber = DateUtil.getWeekNumber(updateRecord.getDateFrom());
      updateRecord.setWeekDay(weekDay);
      updateRecord.setWeekNumber(weekNumber);
    }

    LessonFor lessonFor = LessonFor.subjectForToLessonFor(updateRecord.getSubjectFor());
    try {
      /* если это пара для всего потока, то нужно обновить у всех */
      if (lessonFor.equals(LessonFor.FULL_FLOW)) {
        updateFlowRecord(initialRecord, updateRecord);
      } else {
        recordDAO.update(updateRecord);
      }
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Record getRecord(int idRecord) throws ServiceException {
    try {
      return recordDAO.getById(idRecord);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении занятия", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteRecord(Record record) throws ServiceException, ServiceValidationException {
    service.validateRecord(record, true);
    try {
      recordDAO.delete(record);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении занятия", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Record> getAllRecordListForGroup(Group group, Date dateFrom, Date dateTo)
      throws ServiceException, ServiceValidationException {
    service.validateGroup(group, true);
    try {
      return recordDAO.getRecordListByGroupAndDatesWhichNotCancelled(group, dateFrom, dateTo);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении занятий", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void cancelRecord(Record initialRecord, Record cancelRecord,
      LessonPeriod cancelLessonPeriod) throws ServiceException, ServiceValidationException {

    service.validateRecord(initialRecord, true);
    service.validateRecord(cancelRecord, false);

    try {
      /*
       * удаление по номеру недели или это пара на одну дату, то удаляем данную запись
       */
      LessonFor lessonFor = LessonFor.subjectForToLessonFor(cancelRecord.getSubjectFor());
      if (cancelLessonPeriod.equals(LessonPeriod.FOR_WEEK_NUMBER)
          || initialRecord.getDateFrom().equals(initialRecord.getDateTo())) {

        /* если это пара для всего потока, то нужно обновить у всех */
        if (lessonFor.equals(LessonFor.FULL_FLOW)) {
          deleteFlowRecord(cancelRecord);
        } else {
          recordDAO.delete(cancelRecord);
        }

      } else {
        if (lessonFor.equals(LessonFor.FULL_FLOW)) {
          cancelFlowRecord(initialRecord, cancelRecord);
        } else {
          Cancellation cancellation = new Cancellation();
          cancellation.setDateFrom(cancelRecord.getDateFrom());
          cancellation.setDateTo(cancelRecord.getDateTo());
          cancellation.setRecord(initialRecord);
          cancellationDao.add(cancellation);
        }
      }
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при отмене занятия", e);
    }
  }

  /* @Transactional(value = TxType.MANDATORY) */
  private void addFlowRecord(Record addRecord) throws ServiceException, ServiceValidationException {

    List<Record> recordList = new LinkedList<>();

    List<Group> groupList = groupDao.getGroupListByFlow(addRecord.getGroup().getFlow());
    for (Group group : groupList) {
      addRecord.setGroup(group);
      checkRecordForConflict(addRecord);
      recordList.add(new RecordBuilder().copy(addRecord));
    }
    recordDAO.addAll(recordList);
  }

  private List<Record> constructFlowRecordList(Record addRecord)
      throws ServiceException, ServiceValidationException {

    List<Record> recordList = new LinkedList<>();

    List<Group> groupList = groupDao.getGroupListByFlow(addRecord.getGroup().getFlow());
    for (Group group : groupList) {
      addRecord.setGroup(group);
      checkRecordForConflict(addRecord);
      recordList.add(RECORD_BUILDER.copy(addRecord));
    }
    return recordList;
  }

  /* @Transactional(value = TxType.MANDATORY) */
  private void updateFlowRecord(Record initialRecord, Record updateRecord)
      throws ServiceException, ServiceValidationException {
    List<Record> resultList = new LinkedList<>();

    List<Group> groupList = groupDao.getGroupListByFlow(initialRecord.getGroup().getFlow());
    for (Group group : groupList) {
      Record thisGroupRecord = recordDAO.getRecordForGroupLikeThis(group, initialRecord);
      if (thisGroupRecord != null) {
        updateRecord.setGroup(group);
        updateRecord.setIdRecord(thisGroupRecord.getIdRecord());
        checkRecordForConflict(updateRecord);
      }
    }

    for (Group group : groupList) {
      Record thisGroupRecord = recordDAO.getRecordForGroupLikeThis(group, initialRecord);
      if (thisGroupRecord != null) {
        updateRecord.setGroup(group);
        updateRecord.setIdRecord(thisGroupRecord.getIdRecord());
        resultList.add(RECORD_BUILDER.copy(updateRecord));
      }
    }
    recordDAO.updateAll(resultList);
    // try {
    // checkRecordForConflict(updateRecord);
    // recordList.add((Record) updateRecord.clone());
    // } catch (CloneNotSupportedException e) {
    // throw new ServiceException("Ошибка при обновлении записи для потока",
    // e);
    // }
    // // factory.getRecordDAO().update(updateRecord);
    // }
    // factory.getRecordDAO().updateAll(recordList);
  }

  /* @Transactional(value = TxType.MANDATORY) */
  private void cancelFlowRecord(Record initalRecord, Record cancelRecord) {
    List<Cancellation> resultList = new LinkedList<>();
    
    Cancellation cancellation = new Cancellation();
    cancellation.setDateFrom(cancelRecord.getDateFrom());
    cancellation.setDateTo(cancelRecord.getDateTo());

    List<Group> groupList = groupDao.getGroupListByFlow(initalRecord.getGroup().getFlow());
    for (Group group : groupList) {
      Record thisGroupRecord = recordDAO.getRecordForGroupLikeThis(group, initalRecord);
      if (thisGroupRecord != null) {
        cancellation.setRecord(thisGroupRecord);
        resultList.add(CANCEL_BUILDER.copy(cancellation));
      }
    }
    cancellationDao.addAll(resultList);
  }

  /* @Transactional(value = TxType.MANDATORY) */
  private void deleteFlowRecord(Record initalRecord) {
    List<Record> resultList = new LinkedList<>();

    List<Group> groupList = groupDao.getGroupListByFlow(initalRecord.getGroup().getFlow());
    for (Group group : groupList) {
      Record thisGroupRecord = recordDAO.getRecordForGroupLikeThis(group, initalRecord);
      if (thisGroupRecord != null) {
        resultList.add(thisGroupRecord);
      }
    }
    recordDAO.deleteAll(resultList);
  }

  /**
   * Method checks whether the passed instance of {@link Record} conflicts with similar records
   * 
   * @param record
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  /* @Transactional(value = TxType.MANDATORY) */
  private void checkRecordForConflict(Record record)
      throws ServiceException, ServiceValidationException {
    List<Record> recordList =
        getAllRecordListForGroup(record.getGroup(), record.getDateFrom(), record.getDateTo());

    for (Record elem : recordList) {
      if (elem.getIdRecord() != record.getIdRecord()
          && elem.getWeekNumber() == record.getWeekNumber()
          && elem.getWeekDay() == record.getWeekDay()
          && elem.getSubjOrdinalNumber() == record.getSubjOrdinalNumber()
          && elem.getSubjectFor().equals(record.getSubjectFor())) {

        throw new ServiceValidationException("Запись конфликтует с другой!");
      }
    }

  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void changeClassroomForAllRecords(Classroom oldClassroom, Classroom newClassroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(oldClassroom, true);
    service.validateClassroom(newClassroom, true);
    try {
      recordDAO.replaceClassroomForAllRecords(oldClassroom, newClassroom);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении аудитории", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void changeLecturerForAllRecords(Lecturer oldLecturer, Lecturer newLecturer)
      throws ServiceException, ServiceValidationException {
    service.validateLecturer(oldLecturer, true);
    service.validateLecturer(newLecturer, true);
    try {
      recordDAO.replaceLecturerForAllRecords(oldLecturer, newLecturer);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении преподавателя", e);
    }
  }
}
