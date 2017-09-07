package by.bsac.timetable.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;

@Repository
public class ClassroomDAOImpl extends AbstractHibernateDAO<Classroom, Short>
    implements IClassroomDAO {

  public ClassroomDAOImpl() {
    super(Classroom.class);
  }

  // TODO: just stub
  @Override
  public List<Classroom> getClassroomListForGroupByDates(Group group, Date dateFrom, Date dateTo)
      throws DAOException {
    // TODO Auto-generated method stub
    return null;
  }
}
