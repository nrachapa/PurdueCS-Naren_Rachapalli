package PJ04;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public class Address extends Object {
	private final String building; 
	private final int roomNumber;
	private final String street;
	
	public Address(int roomNumber, String building, String street) {
		this.building = building;
		this.roomNumber = roomNumber;
		this.street = street;
	}

	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	@Override
	public String toString() {
		return String.format("Address{street='%s', building= '%s', roomNumber=%d}", this.street, this.building, this.roomNumber);
	}

	
	
	
	

}
