package by.bsac.timetable.dao;

import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import java.util.Date;
import java.util.List;

public interface IClassroomDAO {

  public void add(Classroom chair);

  public void addAll(List<Classroom> listClassroom);

  public void update(Classroom chair);

  public void updateAll(List<Classroom> listClassroom);

  public Classroom getById(Short idChair);

  public List<Classroom> getAll();

  public void delete(Classroom chair);

  public List<Classroom> getClassroomListForGroupByDates(Group group, Date dateFrom, Date dateTo);

  public List<Classroom> getReservedClassroomListByDatesAndRecord(Date dateFrom, Date dateTo,
      Record record);
}
