package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.entity.Chair;

public interface IChairDAO {

  public void add(Chair chair);

  public void addAll(List<Chair> listChair);

  public void update(Chair chair);

  public void updateAll(List<Chair> listChair);

  public Chair getById(Byte idChair);

  public List<Chair> getAll();

  public void delete(Chair chair);
}
