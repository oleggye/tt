package by.bsac.timetable.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;

@Repository
public class LecturerDAOImpl extends AbstractHibernateDAO<Lecturer, Short> implements ILecturerDAO {

  private static final Logger LOGGER = LogManager.getLogger(LecturerDAOImpl.class.getName());

  private static final String LIKE_CONST = "%";

  public LecturerDAOImpl() {
    super(Lecturer.class);
  }

  @Override
  public List<Lecturer> getLecturerListByChair(Chair chair) {
    LOGGER.debug("getLecturerListByChair: chair= " + chair);
    return manager.createQuery("from Lecturer as lec where lec.chair =?1 ", Lecturer.class)
        .setParameter(1, chair).getResultList();
  }

  public List<Lecturer> getAllWithSimilarName(String nameLecturer) {
    LOGGER.debug("getAllWithSimilarName: nameLecturer= " + nameLecturer);
    return manager
        .createQuery("from Lecturer as lec where lec.nameLecturer like :name ", Lecturer.class)
        .setParameter("name", nameLecturer + LIKE_CONST).getResultList();
  }
}
