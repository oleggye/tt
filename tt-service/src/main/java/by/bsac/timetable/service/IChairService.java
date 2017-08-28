package by.bsac.timetable.service;

import java.util.List;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IChairService {

  public void addChair(Chair chair) throws ServiceException, ServiceValidationException;

  public void updateChair(Chair chair) throws ServiceException, ServiceValidationException;

  public Chair getChairById(byte idChair) throws ServiceException, ServiceValidationException;

  public List<Chair> getAllChair() throws ServiceException;

  public void deleteChair(Chair chair) throws ServiceException, ServiceValidationException;
}
