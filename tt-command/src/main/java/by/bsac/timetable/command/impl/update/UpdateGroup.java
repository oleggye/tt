package by.bsac.timetable.command.impl.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class UpdateGroup implements ICommand {

	@Autowired
	private IGroupService service;

	@Override
	public void execute(Request request) throws CommandException {
		Group group = (Group) request.getValue("group");
		Flow groupFlow = group.getFlow();
		Flow newFlow = (Flow) request.getValue("flow");

		try {

			if (groupFlow != null && !groupFlow.equals(newFlow)) {
				// удалить все записи группы и задать новый поток
				service.changeGroupFlow(group, newFlow);
			} else {
				group.setFlow(newFlow);
				service.updateGroup(group);
			}
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}