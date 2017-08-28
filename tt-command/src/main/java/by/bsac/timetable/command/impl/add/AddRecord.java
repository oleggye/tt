package by.bsac.timetable.command.impl.add;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.WeekNumber;

@Controller
public class AddRecord implements ICommand {

	@Autowired
	private IRecordService service;

	@Override
	public void execute(Request request) throws CommandException {
		Record record = (Record) request.getValue("addRecord");
		Set<WeekNumber> weekSet = (Set<WeekNumber>) request.getValue("weekSet");

		try {
			Iterator<WeekNumber> it = weekSet.iterator();
			while (it.hasNext()) {
				WeekNumber weekNumber = it.next();
				byte weekNumberValue = weekNumber.getWeekNumber();
				record.setWeekNumber(weekNumberValue);
				service.addRecord(record);
			}
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}