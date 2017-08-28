package by.bsac.timetable.command.impl.get;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Controller
public class GetSubjectListByChairAndEduLevel implements ICommand {

	@Autowired
	private ISubjectService service;

	@Override
	public void execute(Request request) throws CommandException {

		Chair chair = (Chair) request.getValue("chair");
		byte eduLevel = (byte) request.getValue("eduLevel");

		try {
			List<Subject> subjectList = service.getSubjectsRecordsByChairIdAndEduLevel(chair, eduLevel);
			request.putParam("subjectList", subjectList);
		} catch (ServiceException | ServiceValidationException e) {
			throw new CommandException(e);
		}
	}
}