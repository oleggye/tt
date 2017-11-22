package by.bsac.timetable.command.impl.get;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetSubjectListByName implements ICommand {

	@Autowired
	private ISubjectService service;

	@Override
	public void execute(Request request) throws CommandException {
		String name = (String) request.getValue("name");

		try {
			List<Subject> subjectList = service.getSubjectListByName(name);
			request.putParam("subjectList", subjectList);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}