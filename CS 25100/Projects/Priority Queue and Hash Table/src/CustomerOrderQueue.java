/**
 * CS 251: Data Structures and Algorithms
 * Project 2: Part 1
 *
 * TODO: Complete CustomerOrderQueue.
 *
 * @author Chirayu Garg, Naren Rachapalli
 * @username garg104, nrachapa
 * @sources TODO: list your sources here
 */


public class CustomerOrderQueue {
	private CustomerOrder[] orderList;
	private int numOrders;

	/**
	 *
	 * @return return the priority queue
	 *
	 */
	public CustomerOrder[] getOrderList() {
		return orderList;
	}

	/**
	 *
	 * @return return the number of orders
	 *
	 */
	public int getNumOrders() {
		return this.numOrders;
	}

	/**
	 * Constructor of the class.
	 * TODO: complete the default Constructor of the class
	 *
	 * Initilalize a new CustomerOrder array with the argument passed.
	 *
	 */
	public CustomerOrderQueue(int capacity) {
		this.numOrders = 0;
		this.orderList = new CustomerOrder[capacity];
	}

	/**
	 * TODO: insert a new customer order into the priority queue.
	 *
	 * @return return the index at which the customer was inserted
	 * Keep looking and edit it and figure out how to use the debugger
	 */
	public int insert(CustomerOrder c) {
		if (this.size() == this.orderList.length) return -1;
		int curr_idx;
		this.orderList[(curr_idx = this.getNumOrders())] = c;
		c.setPosInQueue(this.numOrders);
		this.numOrders++;
		while (curr_idx > 0 && orderList[((curr_idx - 1) / 2)].compareTo(c) == -1) {
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
	public CustomerOrder delMax() {
		if (this.isEmpty()) return null;
		CustomerOrder max = this.getMax();
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
	 * TODO: return the number of Customers currently in the queue
	 *
	 * @return return the number of Customers currently in the queue
	 *
	 */
	public int size() {
		return this.getNumOrders();
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
	 * TODO: return but do not remove the customer with the maximum priority
	 *
	 * @return return the customer with the maximum priority
	 *
	 */
	public CustomerOrder getMax() {
		if (this.isEmpty()) return null;
		return this.orderList[0];
	}

//	private void heapify(int idx) {
//		int big_idx = idx;
//
//		if (((2 * idx) + 1) < this.size() && this.orderList[big_idx].compareTo(this.orderList[((2 * idx) + 1)]) > 0) {
//			big_idx = ((2 * idx) + 1);
//		}
//
//		if (((2 * idx) + 2) < this.size() && this.orderList[big_idx].compareTo(this.orderList[((2 * idx) + 2)]) > 0) {
//			big_idx = ((2 * idx) + 2);
//		}
//
//		if (big_idx != idx) {
//			this.swap(big_idx, idx);
//			heapify(big_idx);
//		}
//	}
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

//	public static void main(String args[]) {
//		CustomerOrderQueue cq = new CustomerOrderQueue(8);
//		cq.insert(new CustomerOrder("I1",23 - 8,-1));
//		cq.insert(new CustomerOrder("I2",10 - 8,-1));
//		cq.insert(new CustomerOrder("I3",17 - 8,-1));
//		cq.insert(new CustomerOrder("I4",28 - 8,-1));
//		cq.insert(new CustomerOrder("I5",34 - 8,-1));
//		cq.insert(new CustomerOrder("I6",89 - 8,-1));
//		cq.insert(new CustomerOrder("I7",22 - 8,-1));
//		cq.insert(new CustomerOrder("I8",9 - 8,-1));
//		for (int xs = 0; xs < cq.size(); xs++) System.out.println(cq.getOrderList()[xs].toString());
//		System.out.printf("size: %d\n", cq.size());
//		System.out.println("Max: " + cq.delMax().toString());
//		
//		
//		System.out.println();
//
//		for (int xs = 0; xs < cq.size(); xs++) System.out.println(cq.getOrderList()[xs].toString());
//		System.out.printf("size: %d\n", cq.size());
//		System.out.println("Max: " + cq.getMax().toString());
//		
//	}

}

