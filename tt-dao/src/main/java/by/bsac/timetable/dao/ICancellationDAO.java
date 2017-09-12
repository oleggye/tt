package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.entity.Cancellation;

public interface ICancellationDAO {

  public void add(Cancellation object);

  public void addAll(List<Cancellation> listCancellation);

  public void update(Cancellation object);

  public void updateAll(List<Cancellation> listCancellation);

  public void delete(Cancellation object);

  public Cancellation getById(Integer id);

  public List<Cancellation> getAll();
}
