package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.entity.Faculty;

public interface IFacultyDAO {

  public void add(Faculty faculty);

  public void addAll(List<Faculty> listFaculty);

  public void update(Faculty faculty);

  public void updateAll(List<Faculty> listFaculty);

  public Faculty getById(Byte idFaculty);

  public List<Faculty> getAll();

  public void delete(Faculty faculty);
}
