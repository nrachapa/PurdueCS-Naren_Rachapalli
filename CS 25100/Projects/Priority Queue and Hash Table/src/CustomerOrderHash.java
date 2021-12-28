/**
 * CS 251: Data Structures and Algorithms
 * Project 2: Part 2
 *
 * TODO: Complete CustomerOrderHash.
 *
 * @author Chirayu Garg, TODO: add your name here
 * @username garg104, TODO: add your Purdue username here
 * @sources: N/A			
*/
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class CustomerOrderHash {
	private ArrayList[] table;
	private int numOrders;
	private int tableCapacity;

	/**
	 * Constructor of the class.
	 * TODO: complete the default Constructor of the class
	 *
	 * Initilalize a new CustomerOrder array with the argument passed.
	 *
	 */
	public CustomerOrderHash(int capacity) {
		this.numOrders = 0;
		if (this.isPrime(capacity)) {
			this.tableCapacity = capacity;
		} else {
			this.tableCapacity = this.getNextPrime(capacity);
		}
		this.table = new ArrayList[this.tableCapacity];
		for (int i = 0; i < this.tableCapacity; i++) this.table[i] = new ArrayList<CustomerOrder>();
	}


	/**
	 *
	 * TODO: return the CustomerOrder with the given name
	 * TODO: return null if the CustomerOrder is not in the table
	 *
	 */
	public CustomerOrder get(String name) throws NoSuchAlgorithmException {
		int idx = this.hash_idx(name);
		//System.out.printf("String: %s -> Hash_Idx: %d\n", name, idx);
		if (idx < 0 || idx >= this.tableCapacity || this.table[idx] == null || this.table[idx].isEmpty()) return null;
		CustomerOrder found = (CustomerOrder) this.table[idx].get(0);
		if (found.getName().equals(name)) return found;
		for (int i = 1; i < this.table[idx].size(); i++) {
			if ((found = (CustomerOrder) this.table[idx].get(i)) != null) {
				if (found.getName().equals(name)) {
					return found;
				}
			}
		}
		return null;
		
	}


	/**
	 *
	 * TODO: put CustomerOrder c into the table
	 *
	 */
	public void put(CustomerOrder c) throws NoSuchAlgorithmException {
		int idx = this.hash_idx(c.getName());
		if (this.get(c.getName()) != null) return;
		this.table[idx].add(c);
		this.numOrders++;
		return;
	}



	/**
	 *
	 * TODO: remove and return the CustomerOrder with the given name;
	 * TODO: return null if CustomerOrder doesn't exist
	 *
	 */
	public CustomerOrder remove(String name) throws NoSuchAlgorithmException {
		int idx = this.hash_idx(name);
		if (idx < 0 || idx >= this.tableCapacity || this.table[idx] == null || this.table[idx].isEmpty()) return null;
		CustomerOrder found = (CustomerOrder) this.table[idx].get(0);
		if (found.getName().equals(name)) {
			this.table[idx].remove(0);
			this.numOrders--;
			return found;
		}
		for (int i = 1; i < this.table[idx].size(); i++) {
			if ((found = (CustomerOrder) this.table[idx].get(i)) != null) {
				if (found.getName().equals(name)) {
					this.table[idx].remove(i);
					this.numOrders--;
					return found;
				}
			}
		}
		return null;
	}


	/**
	 *
	 * TODO: return the number of Customers in the table
	 *
	 */
	public int size() {
		return this.numOrders;
	}



	//get the next prime number p >= num
	private int getNextPrime(int num) {
		if (num == 2 || num == 3)
			return num;

		int rem = num % 6;

		switch (rem) {
		case 0:
		case 4:
			num++;
			break;
		case 2:
			num += 3;
			break;
		case 3:
			num += 2;
			break;
		}

		while (!isPrime(num)) {
			if (num % 6 == 5) {
				num += 2;
			} else {
				num += 4;
			}
		}

		return num;
	}

	//determines if a number > 3 is prime
	private boolean isPrime(int num) {
		if (num % 2 == 0) {
			return false;
		}

		int x = 3;

		for (int i = x; i < num; i += 2) {
			if (num % i == 0) {
				return false;
			}
		}

		return true;
	}
	
	private int hash_idx(String s) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] byte_array = md.digest(s.getBytes());
		String hex_string = "";
		for (int i = 0; i < byte_array.length; i++) {
			String temp = Integer.toHexString(0xff & byte_array[i]);
			if (temp.length() == 1) {
				hex_string += "0";
			}
			hex_string += temp;
		}
		String hash_string = new String();
		for (int i = 0; i < hex_string.length(); i += 2) {
			hash_string += (char) Integer.parseInt(hex_string.substring(i, i + 2), 16);
		}
		int hash_int;
		if ((hash_int = (hash_string.hashCode() % this.tableCapacity)) < 0) hash_int += this.tableCapacity;
		return hash_int;
	}

	public ArrayList<CustomerOrder>[] getArray() {
		return this.table;
	}

}

