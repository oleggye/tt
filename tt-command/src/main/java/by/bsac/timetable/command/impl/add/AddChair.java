package by.bsac.timetable.command.impl.add;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AddChair implements ICommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddChair.class.getName());

  @Autowired
  private IChairService service;

  @Override
  public void execute(Request request) throws CommandException {

    Chair chair = (Chair) request.getValue("chair");

    LOGGER.debug("AddChair: {}", chair);

    try {
      service.addChair(chair);
    } catch (ServiceException | ServiceValidationException e) {
      throw new CommandException(e);
    }
  }
}