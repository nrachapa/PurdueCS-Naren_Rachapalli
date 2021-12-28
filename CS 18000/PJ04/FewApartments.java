package PJ04;

import java.io.*;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public class FewApartments extends RoomsToRent {
	
	public FewApartments(String roomStructureFileName, String tenantInfoFileName) throws FileNotFoundException, InvalidStudentException {
		super(roomStructureFileName, tenantInfoFileName);
	}
	
	@Override
	public double getMonthlyCost() {
		double cost = 795;
		return cost;
	}

}
