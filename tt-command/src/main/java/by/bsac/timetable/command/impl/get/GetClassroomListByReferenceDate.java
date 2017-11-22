package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.DateUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetClassroomListByReferenceDate implements ICommand {

  @Autowired
  private IClassroomService service;

  @Override
  public void execute(Request request) throws CommandException {
    Date referenceDate = (Date) request.getValue("referenceDate");
    Record record = (Record) request.getValue("record");

    Date[] dateArr = DateUtil.getDateFromAndDateToByReferenceDate(referenceDate);
    Date dateFrom = dateArr[0];
    Date dateTo = dateArr[1];
    try {
      List<Classroom> classroomList =
          service.getClassroomListByDatesAndRecordParams(dateFrom, dateTo, record);
      request.putParam("classroomList", classroomList);
    } catch (ServiceException | ServiceValidationException e) {
      throw new CommandException(e);
    }
  }
}
