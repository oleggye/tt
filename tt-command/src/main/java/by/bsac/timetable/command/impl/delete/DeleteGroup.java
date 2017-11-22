package by.bsac.timetable.command.impl.delete;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DeleteGroup implements ICommand {

	@Autowired
	private IGroupService service;

	@Override
	public void execute(Request request) throws CommandException {
		Group group = (Group) request.getValue("group");

		try {
			service.deleteGroup(group);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}