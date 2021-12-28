
public class Dijkstra_Output {
	private int[] dist;
	private int[] prev;

	public Dijkstra_Output(int[] d, int[] p) {
		this.dist = d;
		this.prev = p;
	}

	public int[] getDist() {
		return this.dist;
	}

	public int[] getPrev() {
		return this.prev;
	}

	public void setDist(int[] tmp) {
		this.dist = tmp;
	}

	public void setPrev(int[] tmp) {
		this.prev = tmp;
	}
	
	public void print_output() {
		String dist_out = "Dist: [", prev_out = "Prev: [";
		for (int i = 0; i < this.dist.length; i++) {
			if (i != this.dist.length - 1) {
				dist_out += String.format("%d, ", this.dist[i]);
			} else {
				dist_out += String.format("%d]", this.dist[i]);
				break;
			}
		}
		System.out.println(dist_out);
		for (int i = 0; i < this.prev.length; i++) {
			if (i != this.prev.length - 1) {
				dist_out += String.format("%d, ", this.prev[i]);
			} else {
				dist_out += String.format("%d]", this.prev[i]);
				break;
			}
		}
		System.out.println(prev_out);
		return;
	}
}
