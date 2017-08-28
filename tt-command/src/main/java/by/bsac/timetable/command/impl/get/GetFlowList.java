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

@Controller
public class GetFlowList implements ICommand {

	@Autowired
	private IFlowService service;

	@Override
	public void execute(Request request) throws CommandException {
		try {
			List<Flow> flowList = service.getFlowList();
			request.putParam("flowList", flowList);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
	}
}