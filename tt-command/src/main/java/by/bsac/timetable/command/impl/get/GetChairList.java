package by.bsac.timetable.command.impl.get;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.exception.ServiceException;

@Controller
public class GetChairList implements ICommand {

	@Autowired
	private IChairService service;

	@Override
	public void execute(Request request) throws CommandException {
		try {
			List<Chair> chairList = service.getAllChair();
			request.putParam("chairList", chairList);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
	}
}