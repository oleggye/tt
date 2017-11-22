package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.exception.ServiceException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetClassroomList implements ICommand {

	@Autowired
	private IClassroomService service;

	@Override
	public void execute(Request request) throws CommandException {
		try {
			List<Classroom> classroomList = service.getClassroomList();
			request.putParam("classroomList", classroomList);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
	}
}