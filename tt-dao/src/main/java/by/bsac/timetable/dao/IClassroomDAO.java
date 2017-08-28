package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;

public interface IClassroomDAO {

	public void add(Classroom chair) throws DAOException;

	public void addAll(List<Classroom> listClassroom) throws DAOException;

	public void update(Classroom chair) throws DAOException;

	public void updateAll(List<Classroom> listClassroom) throws DAOException;

	public Classroom getById(Short chair_id) throws DAOException;

	public List<Classroom> getAll() throws DAOException;

	public void delete(Classroom chair) throws DAOException;

	/**
	 * Method updates all references of oldClassroom to newClassroom
	 */
	public void changeClassroom(Classroom oldClassroom, Classroom newClassroom) throws DAOException;
}