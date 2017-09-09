package by.bsac.timetable.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
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

  /*
   * // TODO: изменить метод!
   * 
   * @Override public List<Record> getRecordListByGroupAndDate(Group group, Date dateFrom, Date
   * dateTo) throws DAOException {
   * 
   * List<Record> recordList = new ArrayList<>(); try { Session session =
   * HibernateUtil.getSession(); HibernateUtil.beginTransaction();
   * 
   * Criteria criteria = session.createCriteria(Record.class, "record");
   * criteria.add(Restrictions.eq("record.group", group)); // riteria.add(Restrictions.("eduLevel",
   * eduLevel)); recordList = criteria.list();
   * 
   * // short group_id = group.getIdGroup(); // Query query = session // .createQuery("from Record
   * as rec inner join rec.group as gr where // gr.idGroup = :group_id") // .setShort("group_id",
   * group_id); // mainRecords = query.list(); HibernateUtil.commitTransaction();
   * 
   * } catch (Exception e) { HibernateUtil.rollbackTransaction(); throw new
   * DAOException(e.getMessage(), e); } return recordList; }
   */

  // FIXME: stub!!!
  @Override
  public List<Record> getRecordListByGroupAndDates(Group group, Date dateFrom, Date dateTo)
      throws DAOException {

    LOGGER.debug("getRecordListByGroupAndDate");
    try {
      /*
       * Session session = HibernateUtil.getSession(); HibernateUtil.beginTransaction(); SQLQuery
       * sqlQuery = session .createSQLQuery("SELECT *" +
       * " FROM timetable.cancellation as cancel right join timetable.record as rec using(id_record)"
       * + " where rec.id_group = ? " + " and	not((rec.date_to < ?) or (rec.date_from > ?))" +
       * " and (cancel.id_record is null or ((cancel.date_to <rec.date_from) or (cancel.date_from > rec.date_to)))"
       * ) .addEntity(Record.class); sqlQuery.setShort(0, group.getIdGroup()); sqlQuery.setDate(1,
       * dateFrom); sqlQuery.setDate(2, dateTo);
       * 
       * recordList = sqlQuery.list(); HibernateUtil.commitTransaction();
       */

      return Collections.EMPTY_LIST;

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
