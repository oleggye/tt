package by.bsac.timetable.command.impl.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class UpdateFaculty implements ICommand {

	@Autowired
	private IFacultyService service;

	@Override
	public void execute(Request request) throws CommandException {
		Faculty faculty = (Faculty) request.getValue("faculty");

		try {
			service.updateFaculty(faculty);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}