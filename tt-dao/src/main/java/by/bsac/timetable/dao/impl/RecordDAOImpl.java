package by.bsac.timetable.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;

@Repository
public class RecordDAOImpl extends AbstractHibernateDAO<Record, Integer> implements IRecordDAO {

  private static final Logger LOGGER = LogManager.getLogger(RecordDAOImpl.class.getName());

  public RecordDAOImpl() {
    super(Record.class);
  }

  @Override
  public List<Record> getRecordListByGroupAndDatesWhichNotCancelled(Group group, Date dateFrom, Date dateTo)
      throws DAOException {
    LOGGER.debug("getRecordListByGroupAndDate");
    try {
     return 
         manager.createQuery("SELECT distinct rec FROM Record as rec "
          + "LEFT JOIN rec.cancellations can "
          + "WHERE rec.idRecord not in "
          + "(SELECT cans.record.idRecord FROM Cancellation cans "
          + "WHERE (:dateFrom BETWEEN cans.dateFrom AND cans.dateTo) "
          + "OR (:dateTo BETWEEN cans.dateFrom AND cans.dateTo) "
          + "OR (:dateFrom <= cans.dateFrom AND :dateTo >= cans.dateTo)) "
          + "AND rec.group =:group "
          + "AND ((:dateFrom BETWEEN rec.dateFrom AND rec.dateTo) " + 
          "OR (:dateTo BETWEEN rec.dateFrom AND rec.dateTo) " + 
          "OR (:dateFrom <= rec.dateFrom AND :dateTo >= rec.dateTo))", Record.class)
      .setParameter("dateFrom", dateFrom)
      .setParameter("dateTo", dateTo)
      .setParameter("group", group)
      .getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  // FIXME: it is very strange method! Don't exactly know for what...
  @Override
  public Record getRecordForGroupLikeThis(Group group, Record record) throws DAOException {
    LOGGER.debug("getRecordForGroupLikeThis");
    try {
      return 
          manager
          .createQuery("select rec from Record as rec where rec.group =:group and "
              + "rec.weekNumber =:weekNumber and rec.weekDay =:weekDay and "
              + "rec.subjOrdinalNumber =:subjOrdinalNumber and rec.dateFrom =:dateFrom and "
              + "rec.dateTo =:dateTo and rec.idRecord <>:id", Record.class)
          .setParameter("group", group)
          .setParameter("weekNumber", record.getWeekNumber())
          .setParameter("weekDay", record.getWeekDay())
          .setParameter("subjOrdinalNumber", record.getSubjOrdinalNumber())
          .setParameter("dateFrom", record.getDateFrom())
          .setParameter("dateTo", record.getDateTo())
          .setParameter("id", record.getIdRecord())
          .getSingleResult();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public List<Record> getRecordListByGroupAndSubjectFor(Group group, SubjectFor subjectFor)
      throws DAOException {
    LOGGER.debug("getRecordListByGroupAndSubjectFor");
    try {
      return manager
          .createQuery(
              "select rec from Record rec where rec.group =:group and rec.subjectFor =:subjectFor",
              Record.class)
          .setParameter("group", group).setParameter("subjectFor", subjectFor).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public void replaceClassroomForAllRecords(Classroom oldClassroom, Classroom newClassroom)
      throws DAOException {
    LOGGER.debug("replaceClassroom: [oldClassroom= " + oldClassroom + ", newClassroom= "
        + newClassroom + "]");
    try {
      manager
          .createNativeQuery(
              "UPDATE record as rec SET rec.id_classroom =:newId WHERE rec.id_classroom =:oldId")
          .setParameter("newId", newClassroom.getIdClassroom())
          .setParameter("oldId", oldClassroom.getIdClassroom()).
          executeUpdate();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Override
  public void replaceLecturerForAllRecords(Lecturer oldLecturer, Lecturer newLecturer)
      throws DAOException {
    LOGGER.debug(
        "replaceLecturer: [oldLecturer= " + oldLecturer + ", newLecturer= " + newLecturer + "]");
    try {
      manager
          .createNativeQuery(
              "UPDATE record as rec SET rec.id_lecturer =:newId WHERE rec.id_lecturer =:oldId")
          .setParameter("newId", newLecturer.getIdLecturer())
          .setParameter("oldId", oldLecturer.getIdLecturer())
          .executeUpdate();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
