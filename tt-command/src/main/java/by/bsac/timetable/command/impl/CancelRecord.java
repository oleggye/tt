package by.bsac.timetable.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.LessonPeriod;

@Controller
public class CancelRecord implements ICommand {

	@Autowired
	private IRecordService service;

	@Override
	public void execute(Request request) throws CommandException {
		Record cancelRecord = (Record) request.getValue("cancelRecord");
		LessonPeriod cancelLessonPeriod = (LessonPeriod) request.getValue("cancelLessonPeriod");
		Record initialRecord = (Record) request.getValue("initialRecord");

		try {
			service.cancelRecord(initialRecord, cancelRecord, cancelLessonPeriod);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}