/**
 * CS 251: Data Structures and Algorithms Project 3: Part 2
 * <p>
 *
 * @author Shivam Bairoloya
 * @username sbairoli
 */

public class Node {

	public Tuple data;
	public Node left;
	public Node right;
	public int height;

	/**
	 * Initialize with data
	 *
	 * @param data initial value
	 */
	public Node(Tuple data) {
		this.data = data;
		this.left = null;
		this.right = null;
		this.height = 0;
	}

	/**
	 * initialize all parameters
	 *
	 * @param data   initial data
	 * @param left   left child
	 * @param right  right child
	 * @param height height of node
	 */
	public Node(Tuple data, Node left, Node right, int height) {
		this.data = data;
		this.left = left;
		this.right = right;
		this.height = height;
	}

	/**
	 * Returns a String representation of the item
	 *
	 * @return toString representation
	 */
	@Override
	public String toString() {
		return this.data.toString();
	}
}
