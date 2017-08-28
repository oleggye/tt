package by.bsac.timetable.service;

import java.util.List;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface ISubjectService {

	public void addSubject(Subject subject) throws ServiceException, ServiceValidationException;

	public void updateSubject(Subject subject) throws ServiceException, ServiceValidationException;

	public Subject getSubjectById(short subject_id) throws ServiceException;

	public List<Subject> getAllSubjects() throws ServiceException;

	public void deleteSubject(Subject subject) throws ServiceException, ServiceValidationException;

	public List<Subject> getSubjectsRecordsByChairId(Chair chair) throws ServiceException, ServiceValidationException;

	public List<Subject> getSubjectsRecordsByChairIdAndEduLevel(Chair chair, byte eduLevel)
			throws ServiceException, ServiceValidationException;

	public List<Subject> getSubjectListByName(String name) throws ServiceException, ServiceValidationException;
}