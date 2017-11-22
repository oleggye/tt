package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.exception.ServiceException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetLecturerList implements ICommand {

	@Autowired
	private ILecturerService service;

	@Override
	public void execute(Request request) throws CommandException {
		try {
			List<Lecturer> lecturerList = service.getAllLecturers();
			request.putParam("lecturerList", lecturerList);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
	}
}