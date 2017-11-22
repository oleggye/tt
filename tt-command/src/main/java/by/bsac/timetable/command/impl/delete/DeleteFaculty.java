package by.bsac.timetable.command.impl.delete;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DeleteFaculty implements ICommand {

	@Autowired
	private IFacultyService service;

	@Override
	public void execute(Request request) throws CommandException {
		Faculty faculty = (Faculty) request.getValue("faculty");

		try {
			service.deleteFaculty(faculty);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}