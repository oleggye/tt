package by.bsac.timetable.command.impl.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.service.IFlowService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class UpdateFlow implements ICommand {

	@Autowired
	private IFlowService service;

	@Override
	public void execute(Request request) throws CommandException {
		Flow flow = (Flow) request.getValue("flow");

		try {
			service.updateFlow(flow);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}