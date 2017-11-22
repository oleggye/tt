package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetGroupListByFlow implements ICommand {

	@Autowired
	private IGroupService service;

	@Override
	public void execute(Request request) throws CommandException {

		Flow flow = (Flow) request.getValue("flow");

		try {
			List<Group> groupList = service.getGroupListByFlow(flow);
			request.putParam("groupList", groupList);

		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}