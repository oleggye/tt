package by.bsac.timetable.service;

import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;

public interface IGroupService {

	public void addGroup(Group group) throws ServiceException, ServiceValidationException;

	public void updateGroup(Group group) throws ServiceException, ServiceValidationException;

	public Group getGroupById(short group_id) throws ServiceException;

	public List<Group> getGroupList() throws ServiceException;

	public void deleteGroup(Group group) throws ServiceException, ServiceValidationException;

	public List<Group> getGroupListByFaculty(Faculty faculty) throws ServiceException, ServiceValidationException;

	public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel)
			throws ServiceException, ServiceValidationException;

	public List<Group> getGroupListByFlow(Flow flow) throws ServiceException, ServiceValidationException;

	public void changeGroupFlow(Group group, Flow newFlow) throws ServiceException, ServiceValidationException;

	public List<Group> getGroupListByName(String name) throws ServiceException, ServiceValidationException;
}