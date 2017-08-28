package by.bsac.timetable.command;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;

public interface ICommand {

	public void execute(Request request) throws CommandException;
}
