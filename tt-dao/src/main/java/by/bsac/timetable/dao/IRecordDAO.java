package by.bsac.timetable.dao;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;

public interface IRecordDAO {

  public void add(Record mainRecord);

  public void addAll(List<Record> listRecord);

  public void update(Record mainRecord);

  public void updateAll(List<Record> listRecord);

  public Record getById(Integer idRecord);

  public List<Record> getAll();

  public void delete(Record mainRecord);

  public void deleteAll(List<Record> recordList);

  public List<Record> getRecordListByGroupAndDatesWhichNotCancelled(Group group, Date dateFrom,
      Date dateTo);

  public Record getRecordForGroupLikeThis(Group group, Record record);

  public List<Record> getRecordListByGroupAndSubjectFor(Group group, SubjectFor subjectFor);

  public void replaceClassroomForAllRecords(Classroom oldClassroom, Classroom newClassroom);

  public void replaceLecturerForAllRecords(Lecturer oldLecturer, Lecturer newLecturer);
  
  public void deleteAllRecordsByFlow(Flow flow);
}
