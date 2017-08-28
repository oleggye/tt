package by.bsac.timetable.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 2380337551880565586L;

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}
}