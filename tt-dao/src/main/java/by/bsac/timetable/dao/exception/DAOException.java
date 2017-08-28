/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.dao.exception;

/**
 *
 * @author hello
 */
public class DAOException extends Exception {
   
	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
