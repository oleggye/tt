package by.bsac.timetable.command.impl.update;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UpdateRecord implements ICommand {

	@Autowired
	private IRecordService service;

	@Override
	public void execute(Request request) throws CommandException {
		Record updateRecord = (Record) request.getValue("updateRecord");
		Record initialRecord = (Record) request.getValue("initialRecord");

		try {
			service.updateRecord(initialRecord, updateRecord);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}