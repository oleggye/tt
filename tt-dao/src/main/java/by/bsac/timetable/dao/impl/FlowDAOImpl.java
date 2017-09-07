package by.bsac.timetable.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Flow;

@Repository
public class FlowDAOImpl extends AbstractHibernateDAO<Flow, Short> implements IFlowDAO {

  private static final Logger LOGGER = LogManager.getLogger(FlowDAOImpl.class.getName());

  public FlowDAOImpl() {
    super(Flow.class);
  }

  @Override
  public List<Flow> getAllWithSimilarName(String nameFlow) throws DAOException {
    LOGGER.debug("getAllWithSimilarName: nameFlow= " + nameFlow);
    try {

      final String likeConst = "%";
      return manager.createQuery("from Flow as f where f.name like :name", Flow.class)
          .setParameter("name", nameFlow + likeConst).getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
