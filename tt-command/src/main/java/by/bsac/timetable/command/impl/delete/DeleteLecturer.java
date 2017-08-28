package by.bsac.timetable.command.impl.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class DeleteLecturer implements ICommand {

	@Autowired
	private ILecturerService service;

	@Override
	public void execute(Request request) throws CommandException {
		Lecturer lecturer = (Lecturer) request.getValue("lecturer");

		try {
			service.deleteLecturer(lecturer);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}