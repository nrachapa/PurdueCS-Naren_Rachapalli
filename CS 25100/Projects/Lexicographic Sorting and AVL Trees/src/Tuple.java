/**
 * CS 251: Data Structures and Algorithms Project 3: Part 1
 * <p>
 * TODO: Complete implementation of Tuple.Java
 *
 * @author Shivam Bairoloya, Naren Rachapalli
 * @username sbairoli, nrachapa
 * @sources N/A
 */

public class Tuple<Item extends Comparable<Item>> {

	private Item[] items;

	/**
	 * Initialize items
	 *
	 * @param items the initial value
	 */
	public Tuple(Item[] items) {
		this.items = items;
	}

	/**
	 * Getter
	 *
	 * @return items
	 */
	public Item[] getItems() {
		return items;
	}

	/**
	 * Setter
	 *
	 * @param items to set
	 */
	public void setItems(Item[] items) {
		this.items = items;
	}

	/**
	 * Returns a String representation of the items
	 *
	 * @return toString representation
	 */
	@Override
	public String toString() {
		StringBuilder x = new StringBuilder("");
		for (int i = 0; i < items.length; i++) {
			x.append(items[i]);
		}
		return x.toString();
	}

	/**
	 * Lexicographically compares the items
	 *
	 * @param toCompare items to compare with
	 * @return -1 if less 0 if it is the same and 1 if it is greater
	 */

	public int compareTo(Tuple<Item> toCompare) {
		// TODO: Implement compareTo
		int obj_len = this.getItems().length, obj_idx = 0;
		int oth_len = toCompare.getItems().length, oth_idx = 0;

		// Edge Case
		if (obj_len == 0 && oth_len != 0) {
			return -1;
		} else if (obj_len != 0 && oth_len == 0) {
			return 1;
		} else if (obj_len == 0 && oth_len == 0) {
			return 0;
		}

		// lexicographic sorting
		int res = 1;
		if (obj_len < oth_len)
			res = -1;

		while (obj_idx < obj_len && oth_idx < oth_len) {
			if ((res = this.getItems()[obj_idx++].compareTo(toCompare.getItems()[oth_idx++])) != 0) {
				if (res > 0) {
					return 1;
				}
				return -1;
			}
			if (obj_len < oth_len)
				res = -1;
			else if (obj_len > oth_len)
				res = 1;
			else
				res = 0;
		}
		return res;

	}

	/**
	 * For manual testing
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

	}
}
