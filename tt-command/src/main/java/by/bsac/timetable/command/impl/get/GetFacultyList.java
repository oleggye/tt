package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.exception.ServiceException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetFacultyList implements ICommand {

  @Autowired
  private IFacultyService service;

  @Override
  public void execute(Request request) throws CommandException {

    try {
      List<Faculty> facultyList = service.getAllFaculties();
      request.putParam("facultyList", facultyList);
    } catch (ServiceException e) {
      throw new CommandException(e);
    }
  }
}
