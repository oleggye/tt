package by.bsac.timetable.command.impl.get;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class GetLecturerListByChair implements ICommand {

	@Autowired
	private ILecturerService service;

	@Override
	public void execute(Request request) throws CommandException {

		Chair chair = (Chair) request.getValue("chair");

		try {
			List<Lecturer> lecturerList = service.getLecturersRecordsByChairId(chair);
			request.putParam("lecturerList", lecturerList);

		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}