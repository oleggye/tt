package by.bsac.timetable.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.util.LessonFor;

@Repository
public class GroupDAOImpl extends AbstractHibernateDAO<Group, Short> implements IGroupDAO {

  private static final Logger LOGGER = LogManager.getLogger(GroupDAOImpl.class.getName());

  public GroupDAOImpl() {
    super(Group.class);
  }

  @Override
  public List<Group> getGroupListByFaculty(Faculty faculty) throws DAOException {
    LOGGER.debug("getGroupListByFaculty: faculty= " + faculty);
    try {
      return manager
          .createQuery("select gr from Group as gr where gr.faculty = :faculty", Group.class)
          .setParameter("faculty", faculty).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Transactional
  @Override
  public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel)
      throws DAOException {
    LOGGER.debug(
        "getGroupListByFacultyAndEduLevel: faculty= " + faculty + ", eduLevel= " + eduLevel + "]");
    try {
      return manager
          .createQuery(
              "select gr from Group as gr where gr.faculty = :faculty and gr.eduLevel = :eduLevel",
              Group.class)
          .setParameter("faculty", faculty).setParameter("eduLevel", (Byte) eduLevel)
          .getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public List<Group> getGroupListByFlow(Flow flow) throws DAOException {
    LOGGER.debug("getGroupListByFlow");
    try {
      return manager.createQuery("select gr from Group gr where gr.flow = :flow ", Group.class)
          .setParameter("flow", flow).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  // FIXME: this is just stub
  @Override
  public void changeGroupFlow(Group group, Flow newFlow) throws DAOException {
    LOGGER.debug("changeGroupFlow");

    SubjectFor subjectFor = (LessonFor.FULL_FLOW).lessonForToSubjectFor();
    try {
      /*
       * IRecordDAO dao = DAOFactory.getInstance().getRecordDAO(); List<Record> recordList =
       * dao.getRecordListByGroupAndSubjectFor(group, subjectFor);
       * 
       * Session session = HibernateUtil.getSession(); HibernateUtil.beginTransaction();
       * 
       * SQLQuery deleteRecordSqlQuery = session
       * .createSQLQuery("delete from timetable.record where record.id_record = ?;");
       * 
       * Iterator<Record> iter = recordList.listIterator(); while (iter.hasNext()) { Integer
       * idRecord = iter.next().getIdRecord(); deleteRecordSqlQuery.setInteger(0, idRecord);
       * deleteRecordSqlQuery.executeUpdate(); } group.setFlow(newFlow); session.update(group);
       * 
       * HibernateUtil.commitTransaction();
       */

    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public List<Group> getAllWithSimilarName(String nameGroup) throws DAOException {
    try {
      final String likeConst = "%";
      return manager.createQuery("from Group as gr where gr.nameGroup like :nameGroup", Group.class)
          .setParameter("nameGroup", nameGroup + likeConst).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
