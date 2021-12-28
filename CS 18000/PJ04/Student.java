package PJ04;
/**
*
* Project 4 - StudentNotFoundException
* 
* 
* @author Naren Rachapalli, lab sec 008 
* @version October 31, 2020
*/
public class Student extends Object {
	private String purdueId;
	private String name;
	private String email;
	private int gradYear;
	private Address address;
	
	public Student(String purdueId, String name, String email, int gradYear, Address address) {
		this.purdueId = purdueId;
		this.name = name;
		this.email = email;
		this.gradYear = gradYear;
		this.address = address;
	}
	
	public Student(String purdueId, String name, String email, int gradYear) {
		this.purdueId = purdueId;
		this.name = name;
		this.email = email;
		this.gradYear = gradYear;
		this.address = new Address(0, "Unassigned", "Unassigned");
	}

	/**
	 * @return the purdueId
	 */
	public String getPurdueId() {
		return purdueId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the gradYear
	 */
	public int getGradYear() {
		return gradYear;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param purdueId the purdueId to set
	 */
	public void setPurdueId(String purdueId) {
		this.purdueId = purdueId;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param gradYear the gradYear to set
	 */
	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format("Student{purdueId='%s', name='%s', email='%s', gradYear=%d, address=%s}", this.purdueId, this.name, this.email, this.gradYear, this.address.toString());
	}
	
	/*
	 * Ensure that `Student`'s `toString` method returns a properly formatted String! 
	 * expected:<...000000000000', name=['Purdue Pete', email='example@purdue.edu', 
	 * gradYear=2020, address=Address{street='102 Purdue Way', building=]'Random Building', r...> 
	 * but was:<...000000000000', name=[ 'Purdue Pete', email='example@purdue.edu', gradYear=2020, address=Address{street='102 
	 * Purdue Way', building= ]'Random Building', r...>
	 */
	
	
	

}
