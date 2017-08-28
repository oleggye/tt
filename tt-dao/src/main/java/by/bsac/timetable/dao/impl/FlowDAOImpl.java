package by.bsac.timetable.dao.Impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Flow;

@Repository
public class FlowDAOImpl extends AbstractHibernateDAO<Flow,Short> implements IFlowDAO {

	private static final Logger LOGGER = LogManager.getLogger(FlowDAOImpl.class.getName());

	public FlowDAOImpl() {
		super(Flow.class);
	}

	@Override
	public List<Flow> getAllWithSimilarName(String nameFlow) throws DAOException {
		LOGGER.debug("getAllWithSimilarName: nameFlow= " + nameFlow);
		try {

			CriteriaBuilder builder = manager.getCriteriaBuilder();

			CriteriaQuery<Flow> query = builder.createQuery(Flow.class);
			Root<Flow> f = query.from(Flow.class);

			query.select(f).where(builder.like(f.get("name"), nameFlow));

			TypedQuery<Flow> typedQuery = manager.createQuery(query);

			return typedQuery.getResultList();
			/*
			 * Criteria criteria = session.createCriteria(Flow.class, "flow");
			 * criteria.add(Restrictions.ilike("flow.name", nameFlow,
			 * MatchMode.START)); flowList = criteria.list();
			 * HibernateUtil.commitTransaction();
			 */

		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}