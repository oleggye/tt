package by.bsac.timetable.dao;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import java.util.List;

public interface ISubjectDAO {

  public void add(Subject subject);

  public void addAll(List<Subject> listSubject);

  public void update(Subject subject);

  public void updateAll(List<Subject> listSubject);

  public Subject getById(Short idSubject);

  public List<Subject> getAll();

  public void delete(Subject subject);

  public List<Subject> getSubjectListByChair(Chair chair);

  public List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel);

  public List<Subject> getAllWithSimilarName(String nameSubject);
}
