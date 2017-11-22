package by.bsac.timetable.dao;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import java.util.List;

public interface ILecturerDAO {

  public void add(Lecturer lecturer);

  public void addAll(List<Lecturer> listLecturer);

  public void update(Lecturer lecturer);

  public void updateAll(List<Lecturer> listLecturer);

  public Lecturer getById(Short idLecturer);

  public List<Lecturer> getAll();

  public void delete(Lecturer lecturer);

  public List<Lecturer> getLecturerListByChair(Chair chair);

  public List<Lecturer> getAllWithSimilarName(String nameLecturer);
}
