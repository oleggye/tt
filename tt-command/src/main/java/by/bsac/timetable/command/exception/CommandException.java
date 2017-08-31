package by.bsac.timetable.command.exception;

public class CommandException extends Exception {

  private static final long serialVersionUID = 1L;

  public CommandException(Throwable cause) {
    super(cause);
  }

  public CommandException(String message, Throwable cause) {
    super(message, cause);
  }
}
