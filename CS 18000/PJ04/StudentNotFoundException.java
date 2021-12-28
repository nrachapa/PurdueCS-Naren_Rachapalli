package PJ04;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public class StudentNotFoundException extends Exception {
	
	public StudentNotFoundException() {
		new StudentNotFoundException("null");
	}
	
	public StudentNotFoundException(String message) {
		super(message);
	}

}
