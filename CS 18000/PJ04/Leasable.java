package PJ04;

import java.util.*;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public interface Leasable {
	int countResidents();
	double getContractCost();
	void signContract(Student student, int roomNo) throws OccupiedRoomException;
	int signContract(Student student) throws OccupiedRoomException;
	Student viewResident(int roomNo) throws StudentNotFoundException;
	int countFloormates(int floor);
	ArrayList<Student> listResidents();
	int cancelContract(String purdueId) throws StudentNotFoundException; 
	void saveResidentInfoToFile(String filename);
}
