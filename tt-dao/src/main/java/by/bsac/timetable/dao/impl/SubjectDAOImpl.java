package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDAOImpl extends AbstractHibernateDAO<Subject, Short> implements ISubjectDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDAOImpl.class.getName());

  private static final String LIKE_CONST = "%";

  public SubjectDAOImpl() {
    super(Subject.class);
  }

  @Override
  public List<Subject> getSubjectListByChair(Chair chair) {
    LOGGER.debug("getSubjectListByChair: chair= " + chair);
    return manager.createQuery("from Subject as sbj where sbj.chair =:chair ", Subject.class)
        .setParameter("chair", chair).getResultList();
  }

  @Override
  public List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel) {
    LOGGER.debug("getSubjectListByChairAndEduLevel: chair= " + chair + ", eduLevel" + eduLevel);
    return manager
        .createQuery("from Subject as sbj where sbj.chair =:chair and sbj.eduLevel =:eduLevel",
            Subject.class)
        .setParameter("chair", chair).setParameter("eduLevel", eduLevel).getResultList();
  }

  @Override
  public List<Subject> getAllWithSimilarName(String nameSubject) {
    LOGGER.debug("getAllWithSimilarName: nameSubject= " + nameSubject);
    return manager
        .createQuery("select distinct sbj from Subject as sbj where sbj.nameSubject like :name ",
            Subject.class)
        .setParameter("name", nameSubject + LIKE_CONST).getResultList();
  }
}
