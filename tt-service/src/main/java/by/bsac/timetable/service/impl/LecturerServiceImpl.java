package by.bsac.timetable.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class LecturerServiceImpl implements ILecturerService {

	@Autowired
	private IValidationService service;

	@Autowired
	private ILecturerDAO dao;

	@Override
	public void addLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException {
		service.validateLecturer(lecturer, false);
		try {
			dao.add(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при вставке", e);
		}
	}

	@Override
	public void updateLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException {
		service.validateLecturer(lecturer, true);
		try {
			dao.update(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении", e);
		}
	}

	@Override
	public Lecturer getLecturerById(short lecturer_id) throws ServiceException {
		try {
			return dao.getById(lecturer_id);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателя", e);
		}
	}

	@Override
	public List<Lecturer> getAllLecturers() throws ServiceException {
		try {
			return dao.getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателей", e);
		}
	}

	@Override
	public void deleteLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException {
		service.validateLecturer(lecturer, true);
		try {
			dao.delete(lecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении", e);
		}
	}

	@Override
	public List<Lecturer> getLecturersRecordsByChairId(Chair chair)
			throws ServiceException, ServiceValidationException {
		service.validateChair(chair, true);
		try {
			return dao.getLecturerListByChair(chair);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателей", e);
		}
	}

	// FIXME: add check of name param
	@Override
	public List<Lecturer> getLecturerListByName(String nameLecturer)
			throws ServiceException, ServiceValidationException {
		try {
			return dao.getAllWithSimilarName(nameLecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении преподавателей", e);
		}
	}

	@Override
	public void changeLecturer(Lecturer oldLecturer, Lecturer newLecturer)
			throws ServiceException, ServiceValidationException {
		service.validateLecturer(oldLecturer, true);
		service.validateLecturer(newLecturer, true);
		try {
			dao.changeLecturer(oldLecturer, newLecturer);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении преподавателя", e);
		}
	}
}