
import java.io.*;
import java.util.LinkedList;

public class Trie {
	private TrieNode root = new TrieNode(false);
	private String searchQuery;
	/**
	 *
	 * This function is used to build tree.
	 * Inputfile should be read in this function in order to use testcases.
	 *
	 * @param inputfile
	 * @throws Exception
	 */
	public void buildTrie (String inputfile) throws Exception {
		File fp = new File(inputfile);
		if (!fp.exists()) {
			System.out.println("Input File Path Doesn't Exist!");
			return;
		}
		BufferedReader br = new BufferedReader(new FileReader(fp.getAbsoluteFile()));
		String line;
		boolean past_first = false;
		while ((line = br.readLine()) !=  null) {
			if (past_first) {
				this.insert(line, this.root);
				continue;
			}
			this.searchQuery = line;
			past_first = true;
		}

		br.close();

	}
	/**
	 *
	 * This function is used to traverse the trie and compute the autocomplete wordlist.
	 * Output file should be produced here.
	 *
	 * @param outputfile
	 * @throws Exception
	 */
	public void autocomplete (String outputfile) throws Exception {

		File fp = new File(outputfile);
		if (!fp.exists()) fp.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(fp.getAbsoluteFile()));

		LinkedList<TrieNode> queue = new LinkedList<TrieNode>();
		LinkedList<String> out_string = new LinkedList<String>();
		LinkedList<String> string_queue = new LinkedList<String>();
		LinkedList<Integer> depth = new LinkedList<Integer>();
		LinkedList<Integer> depth_queue = new LinkedList<Integer>();

		int depth_num = 0;
		TrieNode trav = this.root;
		String query = this.searchQuery;
		while (depth_num++ >= 0) {
			int idx = query.charAt(0) - 'a';
			if (trav.children.get(idx) == null) break;
			if (trav.edgeLabel.get(idx).length() < query.length()) {
				if (!trav.edgeLabel.get(idx).equals(query.substring(0, trav.edgeLabel.get(idx).length()))) break;
				query = query.substring(trav.edgeLabel.get(idx).length());
				trav = trav.children.get(idx);
				continue;
			}
			if (trav.edgeLabel.get(idx).equals(query)) {
				trav = trav.children.get(idx);
				depth_num++;
			}
			break;
		}
		queue.add(trav);
		string_queue.add(this.searchQuery);
		depth_queue.add(depth_num);
		while (!queue.isEmpty()) {
			TrieNode node = queue.pop();
			String prefix = "";
			if (!string_queue.isEmpty()) prefix = string_queue.pop();
			if (!depth_queue.isEmpty()) depth_num = depth_queue.pop() + 1;

			for (int i = 0; i < node.children.size(); i++) {
				if (node.children.get(i) != null && node.edgeLabel.get(i) != null) {
					String out = prefix + node.edgeLabel.get(i);
					queue.add(node.children.get(i));
					depth_queue.add(depth_num);
					string_queue.add(out);				
					if (node.children.get(i).end_of_word) {
						out_string.add(out);
						depth.add(depth_num - 1);
					}

				}
			}
		}

		for (int i = 0; i < out_string.size(); i++) {
			String out;
			if (i == out_string.size() - 1) {
				out = String.format("%s %d", out_string.get(i), depth.get(i));
			} else {
				out = String.format("%s %d\n", out_string.get(i), depth.get(i));
			}
			bw.write(out);
		}
		bw.close();
	}


	public void insert(String word, TrieNode node) {
		if (word == null || word.equals("")) return;
		int idx = word.charAt(0) - 'a';
		if (node.edgeLabel.get(idx) == null) {
			node.children.set(idx, new TrieNode(true));
			node.edgeLabel.set(idx, word);

			return;
		}
		if (word.length() > node.edgeLabel.get(idx).length() && word.substring(0, node.edgeLabel.get(idx).length()).equals(node.edgeLabel.get(idx))) {
			insert(word.substring(node.edgeLabel.get(idx).length()),  node.children.get(idx));
            return;
		} 
		if (node.edgeLabel.get(idx).equals(word)){
			node.children.get(idx).end_of_word = true;
			return;
		}
		TrieNode child;
		String label, rem_word, rem_label;
		int label_idx, rem_label_idx;
		if (word.length() < node.edgeLabel.get(idx).length() && node.edgeLabel.get(idx).substring(0, word.length()).equals(word)) {
			child = node.children.get(idx);
			label = node.edgeLabel.get(idx);

			node.children.set(idx, new TrieNode(true));
			node.edgeLabel.set(idx, word);

			rem_label = label.substring(word.length(), label.length());
			rem_label_idx = rem_label.charAt(0) - 'a';
			node.children.get(idx).edgeLabel.set(rem_label_idx, rem_label);
			node.children.get(idx).children.set(rem_label_idx, child);
			return;
		} 
		int jdx = 0, rem_word_idx;
		while (jdx < word.length() && jdx < node.edgeLabel.get(idx).length() && 
				word.charAt(jdx) == node.edgeLabel.get(idx).charAt(jdx)) {
			jdx++;
		}
		child = node.children.get(idx);
		label = node.edgeLabel.get(idx);
		label_idx = label.charAt(0) - 'a';
		rem_word = word.substring(jdx);
		rem_word_idx = rem_word.charAt(0) - 'a';
		node.children.set(idx, new TrieNode(false));
		node.edgeLabel.set(label_idx, label.substring(0, jdx));
		rem_label = label.substring(jdx);
		rem_label_idx = rem_label.charAt(0) - 'a';
		node.children.get(idx).children.set(rem_word_idx, new TrieNode(true));
		node.children.get(idx).edgeLabel.set(rem_label_idx, rem_label);
		node.children.get(idx).edgeLabel.set(rem_word_idx, rem_word);
		node.children.get(idx).children.set(rem_label_idx, child);
		return;
	}
}


