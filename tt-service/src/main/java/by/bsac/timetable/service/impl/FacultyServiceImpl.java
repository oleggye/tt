package by.bsac.timetable.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class FacultyServiceImpl implements IFacultyService {

	@Autowired
	private IValidationService service;

	@Autowired
	private IFacultyDAO dao;

	@Override
	public void addFaculties(Faculty faculty) throws ServiceException, ServiceValidationException {
		service.validateFaculty(faculty, false);
		try {
			dao.add(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при добавлении факультета", e);
		}
	}

	@Override
	public void updateFaculty(Faculty faculty) throws ServiceException, ServiceValidationException {
		service.validateFaculty(faculty, true);
		try {
			dao.update(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении факультета", e);
		}
	}

	@Override
	public Faculty getFacultyById(byte idFaculty) throws ServiceException {
		try {
			return dao.getById(idFaculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении факультета", e);
		}
	}

	@Override
	public List<Faculty> getAllFaculties() throws ServiceException {
		try {
			return dao.getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении факультетов", e);
		}
	}

	@Override
	public void deleteFaculty(Faculty faculty) throws ServiceException, ServiceValidationException {
		service.validateFaculty(faculty, true);
		try {
			dao.delete(faculty);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении группы", e);
		}
	}
}