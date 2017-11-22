package by.bsac.timetable.service;

import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;

public interface IFlowService {

	public void addFlow(Flow flow) throws ServiceException, ServiceValidationException;

	public void updateFlow(Flow flow) throws ServiceException, ServiceValidationException;

	public Flow getFlowById(short idFlow) throws ServiceException, ServiceValidationException;

	public List<Flow> getFlowList() throws ServiceException;

	public void deleteFlow(Flow flow) throws ServiceException, ServiceValidationException;

	public List<Flow> getFlowListByName(String name) throws ServiceException, ServiceValidationException;
}