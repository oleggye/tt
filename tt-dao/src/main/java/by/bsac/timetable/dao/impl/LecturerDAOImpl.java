package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LecturerDAOImpl extends AbstractHibernateDAO<Lecturer, Short> implements ILecturerDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(LecturerDAOImpl.class.getName());

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
