package PJ04;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public class OccupiedRoomException extends Exception {
	
	public OccupiedRoomException() {
		new OccupiedRoomException("null");
	}
	
	public OccupiedRoomException(String message) {
		super(message);
	}

}
