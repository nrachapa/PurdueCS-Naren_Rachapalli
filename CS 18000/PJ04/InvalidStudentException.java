package PJ04;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public class InvalidStudentException extends Exception {
	
	public InvalidStudentException() {
		new InvalidStudentException("null");
	}
	
	public InvalidStudentException(String message) {
		super(message);
	}
}
