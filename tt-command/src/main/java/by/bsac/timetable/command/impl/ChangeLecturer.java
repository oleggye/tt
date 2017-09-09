package by.bsac.timetable.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class ChangeLecturer implements ICommand {

	@Autowired
	private IRecordService service;

	@Override
	public void execute(Request request) throws CommandException {
		Lecturer oldLecturer = (Lecturer) request.getValue("oldLecturer");
		Lecturer newLecturer = (Lecturer) request.getValue("newLecturer");

		try {
			service.changeLecturerForAllRecords(oldLecturer, newLecturer);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}
