package by.bsac.timetable.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;

@Repository
public class ClassroomDAOImpl extends AbstractHibernateDAO<Classroom, Short>
    implements IClassroomDAO {
  private static final Logger LOGGER = LogManager.getLogger(ClassroomDAOImpl.class.getName());

  public ClassroomDAOImpl() {
    super(Classroom.class);
  }

  // TODO: just stub
  @Override
  public List<Classroom> getClassroomListForGroupByDates(Group group, Date dateFrom, Date dateTo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Classroom> getReservedClassroomListByDatesAndRecord(Date dateFrom, Date dateTo, Record record) {
    LOGGER.debug("getReservedClassroomList: dateFrom=" + dateFrom + ",dateTo=" + dateTo);
    return manager
        .createQuery("SELECT distinct rec.classroom FROM Record as rec "
            + "LEFT JOIN rec.cancellations can WHERE rec.idRecord not in "
            + "(SELECT cans.record.idRecord FROM Cancellation cans "
            + "WHERE (:dateFrom BETWEEN cans.dateFrom AND cans.dateTo) "
            + "OR (:dateTo BETWEEN cans.dateFrom AND cans.dateTo) "
            + "OR (:dateFrom <= cans.dateFrom AND :dateTo >= cans.dateTo)) "
            + "AND NOT (:dateTo < rec.dateFrom OR :dateFrom > rec.dateTo) "
            + "AND rec.weekNumber =:weekNumber "
            + "AND rec.weekDay =:weekDay "
            + "AND rec.subjOrdinalNumber =:subjOrdinalNumber "
           /* + "AND ((:dateFrom BETWEEN rec.dateFrom AND rec.dateTo) "
            + "OR (:dateTo BETWEEN rec.dateFrom AND rec.dateTo) "
            + "OR (:dateFrom <= rec.dateFrom AND :dateTo >= rec.dateTo))"*/, Classroom.class)
        .setParameter("dateFrom", dateFrom)
        .setParameter("dateTo", dateTo)
        .setParameter("weekNumber", record.getWeekNumber())
        .setParameter("weekDay", record.getWeekDay())
        .setParameter("subjOrdinalNumber", record.getWeekDay())
        .getResultList();
    
    
  }
}
