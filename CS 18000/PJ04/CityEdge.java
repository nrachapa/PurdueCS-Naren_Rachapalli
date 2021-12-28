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
public class CityEdge extends RoomsToRent {
	private double costMultiplier;
	
	public CityEdge (String roomStructureFileName, String tenantInfoFileName, double costMultiplier) throws FileNotFoundException, InvalidStudentException {
		super(roomStructureFileName, tenantInfoFileName);
		this.costMultiplier = costMultiplier;
	}
	
	@Override
	public double getMonthlyCost() {
		double cost = 820 * this.costMultiplier;
		return cost;
	}
}
