import java.util.ArrayList;
import java.util.List;

/**
 * CS 251: Data Structures and Algorithms Project 3: Part 2
 * <p>
 * TODO: Complete implementation of AVL.Java
 *
 * @author Shivam Bairoloya, Naren Rachapalli
 * @username sbairoli, nrachapa
 * @sources -, N/A
 */
public class AVL {

	/**
	 * Insert a tuple in the tree
	 *
	 * @param node  The root of the tree
	 * @param tuple The data to be inserted
	 * @return root of the tree
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Node insert(Node node, Tuple tuple) {
		if (node == null) {
			return new Node(tuple);
		}
		if (tuple.compareTo(node.data) == -1) {
			node.left = insert(node.left, tuple);
		} else if (tuple.compareTo(node.data) == 1) {
			node.right = insert(node.right, tuple);
		}
		node.height = calc_height(node);
		int bal = balanceFactor(node);
		Node res = rotate(node, bal, tuple);
		res.height = calc_height(res);
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Node rotate(Node node, int bal, Tuple tuple) {
		if (bal > 1 && node.left != null) {
			if ((tuple.compareTo(node.left.data) == -1)) {
				return right_rotate(node);
			}
			if ((tuple.compareTo(node.left.data) == 1)) {
				node.left = left_rotate(node.left);
				return right_rotate(node);
			}

		} else if (bal < -1 && node.right != null) {
			if ((tuple.compareTo(node.right.data) == 1)) {
				return left_rotate(node);
			}

			if ((tuple.compareTo(node.right.data) == -1)) {
				node.right = right_rotate(node.right);
				return left_rotate(node);
			}
		}
		return node;
	}

	private Node right_rotate(Node node) {
		Node a = node.left, b = a.right;
		a.right = node;
		node.left = b;
		a.height = calc_height(a);
		node.height = calc_height(node);
		return a;
	}

	private Node left_rotate(Node node) {
		Node a = node.right, b = a.left;
		a.left = node;
		node.right = b;
		a.height = calc_height(a);
		node.height = calc_height(node);
		return a;
	}

	private int calc_height(Node node) {
		int left = height(node.left), right = height(node.right);
		if (left < right) {
			return right + 1;
		}
		return left + 1;
	}

	/**
	 * Returns the height of a particular node Returns -1 if the node is null
	 *
	 * @param node the node
	 * @return the height
	 */
	public int height(Node node) {
		if (node == null) {
			return -1;
		}
		return node.height;
	}

	/**
	 * Returns the balance factor of a particular node
	 *
	 * @param node
	 * @return the balance factor
	 */
	public int balanceFactor(Node node) {
		// assume node is not null
		return (height(node.left) - height(node.right));
	}

	/**
	 * Returns the preorder traversal
	 *
	 * @param node
	 * @return
	 */
	public List<Node> preorder(Node node) {
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		if (node.left != null) {
			nodes.addAll(preorder(node.left));
		}
		if (node.right != null) {
			nodes.addAll(preorder(node.right));
		}
		return nodes;
	}

	/**
	 * Returns the inorder traversal
	 *
	 * @param node
	 * @return
	 */
	public List<Node> inorder(Node node) {
		List<Node> nodes = new ArrayList<Node>();
		if (node.left != null) {
			nodes.addAll(inorder(node.left));
		}
		nodes.add(node);
		if (node.right != null) {
			nodes.addAll(inorder(node.right));
		}
		return nodes;
	}

	/**
	 * Returns the level order traversal
	 *
	 * @param node
	 * @return
	 */
	public String levelOrder(Node node) {
		int height = height(node);
		List<List<Node>> levels = new ArrayList<>();
		for (int i = 1; i <= height + 1; i++) {
			levels.add(levelOrderHelper(node, i));
		}
		StringBuilder out = new StringBuilder();
		int i = 0;
		for (List<Node> level : levels) {
			out.append("Level ").append(i).append(": ");
			for (Node x : level) {
				out.append("|").append(x).append("|");
			}
			out.append("\n");
			i++;
		}
		return out.toString();
	}

	/**
	 * Recursive function to aid level order traversal
	 *
	 * @param curr
	 * @param level
	 * @return
	 */
	private List<Node> levelOrderHelper(Node curr, int level) {
		List<Node> currLevel = new ArrayList<>();
		if (curr == null) {
			currLevel.add(null);
			return currLevel;
		}
		if (level == 1) {
			currLevel.add(curr);
			return currLevel;
		}

		currLevel.addAll(levelOrderHelper(curr.left, level - 1));
		currLevel.addAll(levelOrderHelper(curr.right, level - 1));
		return currLevel;
	}

	/**
	 * For manual testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}
}