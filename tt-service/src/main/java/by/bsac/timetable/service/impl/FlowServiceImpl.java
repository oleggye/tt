package by.bsac.timetable.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.service.IFlowService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class FlowServiceImpl implements IFlowService {

	@Autowired
	private IValidationService service;

	@Autowired
	private IFlowDAO dao;

	@Override
	public void addFlow(Flow flow) throws ServiceException, ServiceValidationException {
		service.validateFlow(flow, false);
		try {
			dao.add(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при добавлении потока", e);
		}
	}

	@Override
	public void updateFlow(Flow flow) throws ServiceException, ServiceValidationException {
		service.validateFlow(flow, true);
		try {
			dao.update(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при обновлении потока", e);
		}
	}

	@Override
	public Flow getFlowById(short idFlow) throws ServiceException, ServiceValidationException {
		try {
			return dao.getById(idFlow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении потока с id: " + idFlow, e);
		}
	}

	@Override
	public List<Flow> getFlowList() throws ServiceException {
		try {
			return dao.getAll();
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении всех потоков", e);
		}
	}

	@Override
	public void deleteFlow(Flow flow) throws ServiceException, ServiceValidationException {
		service.validateFlow(flow, true);
		try {
			dao.delete(flow);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при удалении потока", e);
		}
	}

	// FIXME: add check of name param
	@Override
	public List<Flow> getFlowListByName(String name) throws ServiceException, ServiceValidationException {
		try {
			return dao.getAllWithSimilarName(name);
		} catch (DAOException e) {
			throw new ServiceException("Ошибка при получении потоков", e);
		}
	}
}