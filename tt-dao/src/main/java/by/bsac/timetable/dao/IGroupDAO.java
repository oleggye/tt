package by.bsac.timetable.dao;

import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import java.util.List;

public interface IGroupDAO {

  public void add(Group group);

  public void addAll(List<Group> listGroup);

  public void update(Group group);

  public void updateAll(List<Group> listGroup);

  public Group getById(Short idGroup);

  public List<Group> getAll();

  public void delete(Group group);

  public List<Group> getGroupListByFaculty(Faculty faculty);

  public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel);

  public List<Group> getGroupListByFlow(Flow flow);

  public List<Group> getAllWithSimilarName(String nameLecturer);
}
