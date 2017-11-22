package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetLecturerListByName implements ICommand {

	@Autowired
	private ILecturerService service;

	@Override
	public void execute(Request request) throws CommandException {
		String name = (String) request.getValue("name");

		try {
			List<Lecturer> lecturerList = service.getLecturerListByName(name);
			request.putParam("lecturerList", lecturerList);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}