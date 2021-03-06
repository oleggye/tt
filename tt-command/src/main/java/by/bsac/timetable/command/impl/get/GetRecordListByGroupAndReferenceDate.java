package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.DateUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetRecordListByGroupAndReferenceDate implements ICommand {

	@Autowired
	private IRecordService service;

	@Override
	public void execute(Request request) throws CommandException {

		Group group = (Group) request.getValue("group");
		Date referenceDate = (Date) request.getValue("referenceDate");

		Date[] dateArr = DateUtil.getDateFromAndDateToByReferenceDate(referenceDate);
		Date dateFrom = dateArr[0];
		Date dateTo = dateArr[1];

		try {
			List<Record> recordList = service.getAllRecordListForGroup(group, dateFrom, dateTo);

			request.putParam("recordList", recordList);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}