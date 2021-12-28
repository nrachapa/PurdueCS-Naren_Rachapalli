/**
 * CS 251: Data Structures and Algorithms
 * Project 2: Part 3
 * <p>
 * TODO: Complete CustomerOrderQueue.
 *
 * @author Chirayu Garg, TODO: add your name here
 * @username garg104, TODO: add your Purdue username here
 * @sources TODO: list your sources here
 */

import java.security.NoSuchAlgorithmException;


public class BetterCustomerOrderQueue {
	private CustomerOrder[] orderList;
	private CustomerOrderHash table;
	private int numOrders;

	/**
	 *
	 * Return the CustomerOrderQueue
	 *
	 */
	public CustomerOrder[] getOrderList() {
		return orderList;
	}

	/**
	 *
	 * Return the number of orders in the queue
	 *
	 */
	public int getNumOrders() {
		return numOrders;
	}


	/**
	 * Constructor of the class.
	 * TODO: complete the default Constructor of the class
	 *
	 * Initialize a new CustomerOrderQueue and CustomerOrderHash
	 *
	 */
	public BetterCustomerOrderQueue(int capacity) {
		this.numOrders = 0;
		this.table = new CustomerOrderHash(capacity);
		this.orderList = new CustomerOrder[capacity];
	}

	/**
	 * TODO: insert a new customer order.
	 *
	 * @return return the index at which the customer was inserted;
	 * return -1 if the Customer could not be inserted
	 *
	 */
	public int insert(CustomerOrder c) throws NoSuchAlgorithmException {
		if (this.size() == this.orderList.length || this.table.get(c.getName()) != null) return -1;

		this.table.put(c);
		int curr_idx;
		this.orderList[(curr_idx = this.getNumOrders())] = c;
		c.setPosInQueue(this.numOrders);
		this.numOrders++;
		while (curr_idx > 0 && this.orderList[((curr_idx - 1) / 2)].compareTo(c) == -1) {
			swap(((curr_idx - 1) / 2), curr_idx);
			curr_idx = ((curr_idx - 1) / 2);
		}
		return curr_idx;
	}

	/**
	 * TODO: remove the customer with the highest priority from the queue
	 *
	 * @return return the customer removed
	 *
	 */
	public CustomerOrder delMax() throws NoSuchAlgorithmException {
		if (this.isEmpty()) return null;

		CustomerOrder max = this.getMax();

		this.table.remove(max.getName()); //delete from hash

		//delete from priority queue
		this.orderList[0] = this.orderList[this.size() - 1];
		this.orderList[this.size() - 1] = null;
		max.setPosInQueue(-1);
		this.numOrders--;
		if (this.orderList[0] != null) this.orderList[0].setPosInQueue(0);
		int idx = 0;
		while (true) {
			int big_idx = idx;

			if (((2 * idx) + 1) < this.size() && this.orderList[big_idx].compareTo(this.orderList[((2 * idx) + 1)]) < 0) {
				big_idx = ((2 * idx) + 1);
			}

			if (((2 * idx) + 2) < this.size() && this.orderList[big_idx].compareTo(this.orderList[((2 * idx) + 2)]) < 0) {
				big_idx = ((2 * idx) + 2);
			}

			if (big_idx != idx) {
				this.swap(big_idx, idx);
				idx = big_idx;
				continue;
			}
			break;
		}
		return max;
	}


	/**
	 * TODO: return but do not remove the customer with the maximum priority
	 *
	 * @return return the customer with the maximum priority
	 *
	 */
	public CustomerOrder getMax() {
		if (this.isEmpty()) return null;
		return this.orderList[0];
	}

	/**
	 * TODO: check if the priority queue is empty or not
	 *
	 * @return return true if the queue is empty; false else
	 *
	 */
	public boolean isEmpty() {
		if (this.size() == 0) return true;
		return false;
	}

	/**
	 * TODO: return the number of Customers currently in the queue
	 *
	 * @return return the number of Customers currently in the queue
	 *
	 */
	public int size() {
		return this.getNumOrders();
	}

	/**
	 *
	 * TODO: return the CustomerOrder with the given name
	 *
	 */
	public CustomerOrder get(String name) throws NoSuchAlgorithmException {
		return this.table.get(name);
	}

	/**
	 *
	 * TODO: remove and return the CustomerOrder with the specified name from the queue;
	 * TODO: return null if the CustomerOrder isn't in the queue
	 *
	 */
	public CustomerOrder remove(String name) throws NoSuchAlgorithmException {


		//remove from priority queue
		CustomerOrder removed;
		if ((removed = this.table.remove(name)) == null) return null;
		
		if (removed.getPosInQueue() == this.size() - 1) {
			this.orderList[removed.getPosInQueue()] = null;
			this.numOrders--;
			removed.setPosInQueue(-1);
			return removed;
		}

		this.orderList[removed.getPosInQueue()] = this.orderList[this.size() - 1];
		this.orderList[this.size() - 1] = null;

		int idx  = removed.getPosInQueue();
		removed.setPosInQueue(-1);
		this.numOrders--;

		if (this.orderList[idx] != null) this.orderList[idx].setPosInQueue(idx);

		while (true) {
			int big_idx = idx;

			if (((2 * idx) + 1) < this.size() && this.orderList[big_idx].compareTo(this.orderList[((2 * idx) + 1)]) < 0) {
				big_idx = ((2 * idx) + 1);
			}

			if (((2 * idx) + 2) < this.size() && this.orderList[big_idx].compareTo(this.orderList[((2 * idx) + 2)]) < 0) {
				big_idx = ((2 * idx) + 2);
			}

			if (big_idx != idx) {
				this.swap(big_idx, idx);
				idx = big_idx;
				continue;
			}
			break;
		}
		
		while (idx > 0 && this.orderList[((idx - 1) / 2)].compareTo(this.orderList[idx]) == -1) {
			swap(((idx - 1) / 2), idx);
			idx = ((idx - 1) / 2);
		}
		
		
		return removed;
	}

	/**
	 *
	 * TODO: update the orderDeliveryTime of the Customer with the specified name to newTime
	 *
	 */
	public void update(String name, int newTime) throws NoSuchAlgorithmException {
		CustomerOrder original_order;
		if((original_order = this.get(name)) == null) return;
		this.remove(name);
		original_order.setOrderDeliveryTime(newTime);
		this.insert(original_order);
	}

	private void swap(int pos1, int pos2) {
		CustomerOrder temp = this.orderList[pos1];

		int pos_in_queue = temp.getPosInQueue();
		int pos2_in_queue = this.orderList[pos2].getPosInQueue();

		temp.setPosInQueue(pos2_in_queue);
		this.orderList[pos2].setPosInQueue(pos_in_queue);

		this.orderList[pos1] = this.orderList[pos2];
		this.orderList[pos2] = temp;
		return;
	}
	

	public static void main(String args[]) throws NoSuchAlgorithmException {
		BetterCustomerOrderQueue cq = new BetterCustomerOrderQueue(8);
		cq.insert(new CustomerOrder("I1",23 - 8,-1));
		cq.insert(new CustomerOrder("I2",10 - 8,-1));
		cq.insert(new CustomerOrder("I3",17 - 8,-1));
		cq.insert(new CustomerOrder("I4",28 - 8,-1));
		cq.insert(new CustomerOrder("I5",34 - 8,-1));
		cq.insert(new CustomerOrder("I6",89 - 8,-1));
		cq.insert(new CustomerOrder("I7",22 - 8,-1));
		cq.insert(new CustomerOrder("I8",9 - 8,-1));
		for (int xs = 0; xs < cq.size(); xs++) System.out.println(cq.getOrderList()[xs].toString());
		System.out.printf("size: %d\n", cq.size());
		System.out.println("Max: " + cq.delMax().toString());
		cq.update("I6", 9);


		System.out.println();

		for (int xs = 0; xs < cq.size(); xs++) System.out.println(cq.getOrderList()[xs].toString());
		System.out.printf("size: %d\n", cq.size());
		System.out.println("Max: " + cq.getMax().toString());

	}


}

