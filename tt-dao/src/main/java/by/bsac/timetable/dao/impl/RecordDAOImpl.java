package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RecordDAOImpl extends AbstractHibernateDAO<Record, Integer> implements IRecordDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(RecordDAOImpl.class.getName());

  private static final String GROUP_PARAM_NAME = "group";
  private static final Byte SUBJECT_FOR_FULL_FLOW_ID = 4;

  public RecordDAOImpl() {
    super(Record.class);
  }

  @Override
  public List<Record> getRecordListByGroupAndDatesWhichNotCancelled(Group group, Date dateFrom,
      Date dateTo) {
    LOGGER.debug("getRecordListByGroupAndDate: group=" + group + ", dateFrom=" + dateFrom
        + ",dateTo=" + dateTo);
    return manager
        .createQuery("SELECT distinct rec FROM Record as rec " + "LEFT JOIN rec.cancellations can "
            + "WHERE rec.idRecord not in " + "(SELECT cans.record.idRecord FROM Cancellation cans "
            + "WHERE (:dateFrom BETWEEN cans.dateFrom AND cans.dateTo) "
            + "OR (:dateTo BETWEEN cans.dateFrom AND cans.dateTo) "
            + "OR (:dateFrom <= cans.dateFrom AND :dateTo >= cans.dateTo)) "
            + "AND rec.group =:group " + "AND ((:dateFrom BETWEEN rec.dateFrom AND rec.dateTo) "
            + "OR (:dateTo BETWEEN rec.dateFrom AND rec.dateTo) "
            + "OR (:dateFrom <= rec.dateFrom AND :dateTo >= rec.dateTo))", Record.class)
        .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo)
        .setParameter(GROUP_PARAM_NAME, group).getResultList();
  }

  /*it is very strange method! Don't exactly know for what...*/
  @Override
  public Record getRecordForGroupLikeThis(Group group, Record record) {
    LOGGER.debug("getRecordForGroupLikeThis: group=" + group + "record=" + record);
    return manager
        .createQuery("select rec from Record as rec where rec.group =:group and "
            + "rec.weekNumber =:weekNumber and rec.weekDay =:weekDay and "
            + "rec.subjOrdinalNumber =:subjOrdinalNumber and rec.dateFrom =:dateFrom and "
            + "rec.dateTo =:dateTo", Record.class)
        .setParameter(GROUP_PARAM_NAME, group).setParameter("weekNumber", record.getWeekNumber())
        .setParameter("weekDay", record.getWeekDay())
        .setParameter("subjOrdinalNumber", record.getSubjOrdinalNumber())
        .setParameter("dateFrom", record.getDateFrom()).setParameter("dateTo", record.getDateTo())
        .getSingleResult();
  }

  @Override
  public List<Record> getRecordListByGroupAndSubjectFor(Group group, SubjectFor subjectFor) {
    LOGGER.debug("getRecordListByGroupAndSubjectFor: group=" + group + "subjectFor=" + subjectFor);
    return manager
        .createQuery(
            "select rec from Record rec where rec.group =:group and rec.subjectFor =:subjectFor",
            Record.class)
        .setParameter(GROUP_PARAM_NAME, group).setParameter("subjectFor", subjectFor).getResultList();
  }

  @Override
  public void replaceClassroomForAllRecords(Classroom oldClassroom, Classroom newClassroom) {
    LOGGER.debug("replaceClassroom: [oldClassroom= " + oldClassroom + ", newClassroom= "
        + newClassroom + "]");
    manager
        .createNativeQuery(
            "UPDATE record as rec SET rec.id_classroom =:newId WHERE rec.id_classroom =:oldId")
        .setParameter("newId", newClassroom.getIdClassroom())
        .setParameter("oldId", oldClassroom.getIdClassroom()).executeUpdate();
  }

  @Override
  public void replaceLecturerForAllRecords(Lecturer oldLecturer, Lecturer newLecturer) {
    LOGGER.debug(
        "replaceLecturer: [oldLecturer= " + oldLecturer + ", newLecturer= " + newLecturer + "]");
    manager
        .createNativeQuery(
            "UPDATE record as rec SET rec.id_lecturer =:newId WHERE rec.id_lecturer =:oldId")
        .setParameter("newId", newLecturer.getIdLecturer())
        .setParameter("oldId", oldLecturer.getIdLecturer()).executeUpdate();
  }

  @Override
  public void deleteAllRecordsByFlow(Flow flow) {
    LOGGER.debug("deleteAllRecordsByFlow: flow=" + flow);
    /**/
/*<<<<<<< HEAD
    manager.createQuery("delete from Record rec where"
        + " rec.group"
        + " in(select gr from Group gr where gr.flow =:flow)"
       *//* + " and rec.subjectFor =:id_subjectFor"*//*)
        .setParameter("flow", flow)
        //.setParameter("id_subjectFor", SUBJECT_FOR_FULL_FLOW_ID)
=======*/
    manager.createNativeQuery(
        "delete from record where record.id_group " 
            + "in (select id_group from `group` join flow where flow.id_flow = 1) and record.id_subject_for :=idSubjectFor")
        .setParameter("idFlow", flow.getIdFlow())
        .setParameter("subjectFor", SUBJECT_FOR_FULL_FLOW_ID)
        .executeUpdate();
    /*manager.createNativeQuery(
        "delete from record where record.id_group "
            + "in(select id_group from groupp join flow where flow.id_flow =:idFlow) "
            + "and record.id_subject_for =:subjectFor")*/

      /*  .setParameter("flow", flow)
        .setParameter("subjectFor", SUBJECT_FOR_FULL_FLOW_ID)     .executeUpdate();*/

   /* manager.createQuery("delete from Record rec "
        + "where rec.group.flow =:flow and rec.subjectFor.id =:id_subjectFor")
        .setParameter("flow", flow)
        .setParameter("id_subjectFor", SUBJECT_FOR_FULL_FLOW_ID)
        .executeUpdate();*/
  }
}
