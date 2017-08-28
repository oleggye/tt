package by.bsac.timetable.command.impl.get;

import java.util.List;

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
public class GetFlowListByName implements ICommand {

	@Autowired
	private IFlowService service;

	@Override
	public void execute(Request request) throws CommandException {
		String name = (String) request.getValue("name");

		try {
			List<Flow> flowList = service.getFlowListByName(name);
			request.putParam("flowList", flowList);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}