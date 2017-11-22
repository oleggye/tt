package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.entity.Chair;
import org.springframework.stereotype.Repository;

@Repository
public class ChairDAOImpl extends AbstractHibernateDAO<Chair,Byte> implements IChairDAO {

	public ChairDAOImpl() {
		super(Chair.class);
	}
}