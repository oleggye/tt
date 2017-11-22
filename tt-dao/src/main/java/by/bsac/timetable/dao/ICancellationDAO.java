package by.bsac.timetable.dao;

import by.bsac.timetable.entity.Cancellation;
import java.util.List;

public interface ICancellationDAO {

  public void add(Cancellation object);

  public void addAll(List<Cancellation> listCancellation);

  public void update(Cancellation object);

  public void updateAll(List<Cancellation> listCancellation);

  public void delete(Cancellation object);
  
  public void deleteAll(List<Cancellation> listCancellation);

  public Cancellation getById(Integer id);

  public List<Cancellation> getAll();
}
