package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Cancellation;

public interface ICancellationDAO {

	public void add(Cancellation object) throws DAOException;

	public void addAll(List<Cancellation> listCancellation) throws DAOException;

	public void update(Cancellation object) throws DAOException;

	public void updateAll(List<Cancellation> listCancellation) throws DAOException;

	public void delete(Cancellation object) throws DAOException;

	public Cancellation getById(Integer id) throws DAOException;

	public List<Cancellation> getAll() throws DAOException;
}
