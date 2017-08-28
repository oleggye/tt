package by.bsac.timetable.command.impl.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class UpdateClassroom implements ICommand {

	@Autowired
	private IClassroomService service;

	@Override
	public void execute(Request request) throws CommandException {
		Classroom classroom = (Classroom) request.getValue("classroom");

		try {
			service.updateClassroom(classroom);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}