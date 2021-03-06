package by.bsac.timetable.dao;

import by.bsac.timetable.entity.Flow;
import java.util.List;

public interface IFlowDAO {

  public void add(Flow flow);

  public void addAll(List<Flow> listFlow);

  public void update(Flow flow);

  public Flow getById(Short idFlow);

  public List<Flow> getAll();

  public void delete(Flow flow);

  public List<Flow> getAllWithSimilarName(String nameFlow);
}
