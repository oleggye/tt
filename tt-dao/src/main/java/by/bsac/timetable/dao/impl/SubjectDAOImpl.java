package by.bsac.timetable.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;

@Repository
public class SubjectDAOImpl extends AbstractHibernateDAO<Subject, Short> implements ISubjectDAO {

  private static final Logger LOGGER = LogManager.getLogger(SubjectDAOImpl.class.getName());

  public SubjectDAOImpl() {
    super(Subject.class);
  }

  @Override
  public List<Subject> getSubjectListByChair(Chair chair) throws DAOException {
    try {
      return manager.createQuery("from Subject as sbj where sbj.chair =:chair ", Subject.class)
          .setParameter("chair", chair).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel)
      throws DAOException {
    try {
      return manager
          .createQuery("from Subject as sbj where sbj.chair =:chair and sbj.eduLevel =:eduLevel",
              Subject.class)
          .setParameter("chair", chair).setParameter("eduLevel", eduLevel).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public List<Subject> getAllWithSimilarName(String nameSubject) throws DAOException {
    try {
      final String likeConst = "%";
      return manager
          .createQuery("select distinct sbj from Subject as sbj where sbj.nameSubject like :name ", Subject.class)
          .setParameter("name", nameSubject + likeConst).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
