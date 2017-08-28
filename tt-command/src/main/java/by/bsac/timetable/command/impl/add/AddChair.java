package by.bsac.timetable.command.impl.add;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class AddChair implements ICommand {

	private static final Logger LOGGER = LogManager.getLogger(AddChair.class.getName());

	@Autowired
	private IChairService service;

	@Override
	public void execute(Request request) throws CommandException {

		Chair chair = (Chair) request.getValue("chair");

		LOGGER.debug("AddChair: " + chair);

		try {
			service.addChair(chair);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}