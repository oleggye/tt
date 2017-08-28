package by.bsac.timetable.dao.impl;

import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.entity.Cancellation;

@Repository
public class CancellationDAOImpl extends AbstractHibernateDAO<Cancellation, Integer>
    implements ICancellationDAO {

  public CancellationDAOImpl() {
    super(Cancellation.class);
  }
}
