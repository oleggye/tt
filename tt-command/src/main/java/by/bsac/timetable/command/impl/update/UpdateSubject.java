package by.bsac.timetable.command.impl.update;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UpdateSubject implements ICommand {

	@Autowired
	private ISubjectService service;

	@Override
	public void execute(Request request) throws CommandException {
		Subject subject = (Subject) request.getValue("subject");

		try {
			service.updateSubject(subject);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}