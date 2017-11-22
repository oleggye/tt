package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDAOImpl extends AbstractHibernateDAO<Group, Short> implements IGroupDAO {

  private static final Logger LOGGER = LogManager.getLogger(GroupDAOImpl.class.getName());

  private static final String LIKE_CONST = "%";

  public GroupDAOImpl() {
    super(Group.class);
  }

  @Override
  public List<Group> getGroupListByFaculty(Faculty faculty) {
    LOGGER.debug("getGroupListByFaculty: faculty= " + faculty);
    return manager
        .createQuery("select gr from Group as gr where gr.faculty = :faculty", Group.class)
        .setParameter("faculty", faculty).getResultList();
  }

  @Override
  public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel) {
    LOGGER.debug(
        "getGroupListByFacultyAndEduLevel: faculty= " + faculty + ", eduLevel= " + eduLevel + "]");
    return manager
        .createQuery(
            "select gr from Group as gr where gr.faculty = :faculty and gr.eduLevel = :eduLevel",
            Group.class)
        .setParameter("faculty", faculty).setParameter("eduLevel", (Byte) eduLevel).getResultList();
  }

  @Override
  public List<Group> getGroupListByFlow(Flow flow) {
    LOGGER.debug("getGroupListByFlow");
    return manager.createQuery("select gr from Group gr where gr.flow = :flow ", Group.class)
        .setParameter("flow", flow).getResultList();
  }

  @Override
  public List<Group> getAllWithSimilarName(String nameGroup) {
    return manager
        .createQuery("select distinct gr from Group as gr where gr.nameGroup like :nameGroup",
            Group.class)
        .setParameter("nameGroup", nameGroup + LIKE_CONST).getResultList();
  }
}
