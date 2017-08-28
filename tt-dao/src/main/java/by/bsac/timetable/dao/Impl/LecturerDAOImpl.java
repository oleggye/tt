package by.bsac.timetable.dao.Impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;

@Repository
public class LecturerDAOImpl extends AbstractHibernateDAO<Lecturer, Short> implements ILecturerDAO {

  private static final Logger LOGGER = LogManager.getLogger(LecturerDAOImpl.class.getName());

  public LecturerDAOImpl() {
    super(Lecturer.class);
  }

  @Override
  public List<Lecturer> getLecturerListByChair(Chair chair) throws DAOException {

    try {
      /*
       * Session session = HibernateUtil.getSession(); HibernateUtil.beginTransaction();
       * 
       * Criteria criteria = session.createCriteria(Lecturer.class, "lecturer");
       * criteria.add(Restrictions.eq("lecturer.chair", chair)); lecturerList = criteria.list();
       * 
       * HibernateUtil.commitTransaction();
       */

      return manager.createQuery("from Lecturer as lec where lec.chair =?1 ", Lecturer.class)
          .setParameter(1, chair).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  public List<Lecturer> getAllWithSimilarName(String nameLecturer) throws DAOException {
    try {
      /*
       * Session session = HibernateUtil.getSession(); HibernateUtil.beginTransaction(); Criteria
       * criteria = session.createCriteria(Lecturer.class, "lecturer");
       * criteria.add(Restrictions.ilike("lecturer.nameLecturer", nameLecturer, MatchMode.START));
       * lecturerList = criteria.list(); HibernateUtil.commitTransaction();
       */

      return manager
          .createQuery("from Lecturer as lec where lec.nameLecturer like ?1 ", Lecturer.class)
          .setParameter(1, nameLecturer).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  /**
   * Method updates all references of oldLecturer to newLecturer
   */
  @Override
  public void changeLecturer(Lecturer oldLecturer, Lecturer newLecturer) throws DAOException {
    try {
      // SQLQuery sqlQuery = session
      // .createSQLQuery("UPDATE timetable.record as rec SET rec.id_lecturer=? WHERE
      // rec.id_lecturer=?;");
      // sqlQuery.setShort(0, oldLecturer.getIdLecturer());
      // sqlQuery.setShort(1, newLecturer.getIdLecturer());
      // sqlQuery.executeUpdate();

      manager
          .createQuery(
              "UPDATE timetable.record as rec SET rec.id_lecturer=?1 WHERE rec.id_lecturer=?2;")
          .setParameter(1, oldLecturer.getIdLecturer()).setParameter(2, newLecturer.getIdLecturer())
          .executeUpdate();

    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
