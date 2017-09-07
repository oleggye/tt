package by.bsac.timetable.dao.impl;

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
      return manager.createQuery("from Lecturer as lec where lec.chair =?1 ", Lecturer.class)
          .setParameter(1, chair).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  public List<Lecturer> getAllWithSimilarName(String nameLecturer) throws DAOException {
    try {
      final String likeConst = "%";
      return manager
          .createQuery("from Lecturer as lec where lec.nameLecturer like :name ", Lecturer.class)
          .setParameter("name", nameLecturer + likeConst).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}