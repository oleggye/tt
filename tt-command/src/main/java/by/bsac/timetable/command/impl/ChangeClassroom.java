package by.bsac.timetable.command.impl;

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
public class ChangeClassroom implements ICommand {

	@Autowired
	private IClassroomService service;

	@Override
	public void execute(Request request) throws CommandException {
		Classroom oldClassroom = (Classroom) request.getValue("oldClassroom");
		Classroom newClassroom = (Classroom) request.getValue("newClassroom");

		try {
			service.changeClassroom(oldClassroom, newClassroom);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}