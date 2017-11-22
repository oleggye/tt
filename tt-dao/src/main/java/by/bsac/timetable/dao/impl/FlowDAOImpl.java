package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.entity.Flow;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class FlowDAOImpl extends AbstractHibernateDAO<Flow, Short> implements IFlowDAO {

  private static final Logger LOGGER = LogManager.getLogger(FlowDAOImpl.class.getName());

  private static final String LIKE_CONST = "%";

  public FlowDAOImpl() {
    super(Flow.class);
  }

  @Override
  public List<Flow> getAllWithSimilarName(String nameFlow) {
    LOGGER.debug("getAllWithSimilarName: nameFlow= " + nameFlow);
    return manager
        .createQuery("select distinct f from Flow as f where f.name like :name", Flow.class)
        .setParameter("name", nameFlow + LIKE_CONST).getResultList();
  }
}
