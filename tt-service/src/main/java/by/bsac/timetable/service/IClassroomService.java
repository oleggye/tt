package by.bsac.timetable.service;

import java.time.LocalDate;
import java.util.List;

import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IClassroomService {

	public void addClassroom(Classroom classRoom) throws ServiceException, ServiceValidationException;;

	public void updateClassroom(Classroom classRoom) throws ServiceException, ServiceValidationException;;

	public Classroom getClassroom(short idClassroom) throws ServiceException;

	public void deleteClassroom(Classroom classRoom) throws ServiceException, ServiceValidationException;

	public List<Classroom> getClassroomListForGroup(Group group, LocalDate dateFrom, LocalDate dateTo)
			throws ServiceException;

	public List<Classroom> getClassroomList() throws ServiceException;

	public void changeClassroom(Classroom oldClassroom, Classroom newClassroom)
			throws ServiceException, ServiceValidationException;
}