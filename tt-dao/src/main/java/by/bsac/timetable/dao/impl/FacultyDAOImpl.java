package by.bsac.timetable.dao.impl;

import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.entity.Faculty;

@Repository
public class FacultyDAOImpl extends AbstractHibernateDAO<Faculty, Byte> implements IFacultyDAO {

  public FacultyDAOImpl() {
    super(Faculty.class);
  }
}
