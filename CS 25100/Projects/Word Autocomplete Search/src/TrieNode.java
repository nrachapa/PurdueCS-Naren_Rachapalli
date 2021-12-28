import java.util.LinkedList;
import java.util.List;

public class TrieNode {
	public LinkedList<TrieNode> children;
	public boolean end_of_word;
	public LinkedList<String> edgeLabel;
	
	
	public TrieNode(boolean end) {
		this.children = new LinkedList<TrieNode>();
		this.edgeLabel = new LinkedList<String>();
		for (int i = 0; i <= 26; i++) {
			this.children.add(null);
			this.edgeLabel.add(null);
		}
		this.end_of_word = end;
	}
}

