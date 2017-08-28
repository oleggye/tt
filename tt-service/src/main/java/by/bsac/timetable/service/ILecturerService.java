package by.bsac.timetable.service;

import java.util.List;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface ILecturerService {

	public void addLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException;

	public void updateLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException;

	public Lecturer getLecturerById(short lecturer_id) throws ServiceException;

	public List<Lecturer> getAllLecturers() throws ServiceException;

	public void deleteLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException;

	public List<Lecturer> getLecturersRecordsByChairId(Chair chair) throws ServiceException, ServiceValidationException;

	public List<Lecturer> getLecturerListByName(String name) throws ServiceException, ServiceValidationException;

	public void changeLecturer(Lecturer oldLecturer, Lecturer newLecturer)
			throws ServiceException, ServiceValidationException;
}