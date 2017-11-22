package by.bsac.timetable.service;

import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;

public interface IFacultyService {

	public void addFaculties(Faculty faculty) throws ServiceException, ServiceValidationException;

	public void updateFaculty(Faculty faculty) throws ServiceException, ServiceValidationException;

	public Faculty getFacultyById(byte idFaculty) throws ServiceException;

	public List<Faculty> getAllFaculties() throws ServiceException;

	public void deleteFaculty(Faculty faculty) throws ServiceException, ServiceValidationException;
}