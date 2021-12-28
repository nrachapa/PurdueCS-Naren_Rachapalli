import java.util.ArrayList;

public class RegNet 
{
	//creates a regional network
	//G: the original graph
	//max: the budget
	public static Graph run(Graph G, int max) 
	{
		if (G == null) return null;
		Graph mst = KruskalAlgo(G);

		System.out.println("Step 1\nKruskal MST:");
		for (int i = 0; i < mst.edges().size(); i++) {
			System.out.println(mst.sortedEdges().get(i).toString());
		}
		System.out.println();

		while (mst.totalWeight() > max) {
			for (int i = mst.sortedEdges().size() - 1; i >= 0; i--) {
				Edge e = mst.sortedEdges().get(i);
				if (mst.adj(e.u).size() == 1 || mst.adj(e.v).size() == 1) {
					mst.removeEdge(e);
					mst = mst.connGraph();
					i = mst.sortedEdges().size();
				}
				if (!(mst.totalWeight() > max)) break;
				if (i <= 1) i = mst.sortedEdges().size();
			}
		}
		mst = mst.connGraph();

		System.out.println("Step 2\nAfter Budget Removal MST:");
		for (int i = 0; i < mst.edges().size(); i++) {
			System.out.println(mst.edges().get(i).toString());
		}
		System.out.println();

		Graph stops_graph = new Graph(mst.V());
		stops_graph.setCodes(mst.getCodes());
		for (int i = 0; i < mst.V() - 1; i++) {
			int[] edgeTo = bfs(mst, i);

			//			System.out.printf("edgeTo of mst and vertex %d\n", i);
			//			for (int x : edgeTo) System.out.println(x);
			//			System.out.println();

			for (int j = i + 1; j < mst.V(); j++) {
				int stop_w = calc_stops(edgeTo, i, j);
				if (stop_w <= 0) continue;
				Edge e = new Edge(mst.getCode(i), mst.getCode(j), stop_w), alt = new Edge(mst.getCode(j), mst.getCode(i), stop_w);
				if (!stops_graph.edges().contains(alt)) {
					stops_graph.addEdge(e);
				}
			}
		}		

//		System.out.printf("Step 3.5\nAll Stops:\n%s\n", stops_graph.toString());

		Edge[] stops_array = new Edge[stops_graph.edges().size()];
		for (int idx = 0; idx < stops_array.length; idx++) {
			stops_array[idx] = stops_graph.edges().get(idx);
		}
		sort_stops_arr(G, stops_array);

		System.out.printf("\nSorted Stops Array:\n");
		for (int idx = 0; idx < stops_array.length; idx++) {
			System.out.println(stops_array[idx].toString());
		}
		System.out.println();

		for (int idx = stops_array.length - 1; idx >= 0; idx--) {
			int current_w = mst.totalWeight();
			Edge stop_edge = stops_array[idx];
			Edge d_edge = G.getEdge(stop_edge.ui(), stop_edge.vi());
			if ((current_w + d_edge.w) <= max) mst.addEdge(d_edge);
		}

		mst = mst.connGraph();

		return mst;
	}

	private static int calc_stops(int[] edgeTo, int s, int e) {
		int stop_w = 0;

		if (edgeTo[s] == 0) {
			int tmp = e;
			e = s;
			for (; tmp != e; tmp = edgeTo[tmp]) {
				if (tmp == 0) {
					tmp = e;
					while (tmp != 0) {
						tmp = edgeTo[tmp];
						stop_w++;
					}
					stop_w -= 3;
					return stop_w;
				}
				stop_w++;
			}
		} else {
			int tmp = s;
			for (; tmp != e; tmp = edgeTo[tmp]) {
				if (tmp == 0) {
					tmp = e;
					while (tmp != 0) {
						tmp = edgeTo[tmp];
						stop_w++;
					}
					stop_w -= 3;
					return stop_w;
				}
				stop_w++;
			}
		}
		return stop_w;
	}

	private static void sort_stops_arr(Graph G, Edge[] stops_array) {
		for (int idx = 1; idx < stops_array.length; idx++) {
			Edge key = stops_array[idx];
			int jdx = idx - 1;
			while (jdx >= 0 && G.getEdgeWeight(stops_array[jdx].ui(), stops_array[jdx].vi()) < G.getEdgeWeight(key.ui(), key.vi())) {
				stops_array[jdx + 1] = stops_array[jdx--];
			}
			stops_array[jdx + 1] = key;
		}
		for (int idx = 1; idx < stops_array.length; idx++) {
			Edge key = stops_array[idx];
			int jdx = idx - 1;
			while (jdx >= 0 && stops_array[jdx].w > key.w) {
				stops_array[jdx + 1] = stops_array[jdx--];
			}
			stops_array[jdx + 1] = key;
		}
		return;
	}


	private static Graph KruskalAlgo(Graph G) {
		Graph tmp = G.connGraph();
		int num_of_verticies = tmp.V();
		Graph mst = new Graph(num_of_verticies);
		mst.setCodes(tmp.getCodes());
		UnionFind subsets = new UnionFind(num_of_verticies);
		for (Edge e : tmp.sortedEdges()) {
			if (!subsets.connected(e.ui(), e.vi())) {
				mst.addEdge(e);
				subsets.union(e.ui(), e.vi());    			
			}
		}
		return mst;
	}


	private static int[] bfs(Graph G, int v) {
		ArrayList<Integer> queue = new ArrayList<Integer>();
		ArrayList<String> visited = new ArrayList<String>();
		int[] edgeTo = new int[G.V()];
		visited.add(G.getCode(v));
		queue.add(v);

		while(!queue.isEmpty()) {
			int vert = queue.remove(0);
			ArrayList<Integer> adjList = G.adj(vert);
			if (adjList != null && !adjList.isEmpty()) {
				for (int w : adjList) {
					if (!visited.contains(G.getCode(w))) {
						queue.add(w);
						visited.add(G.getCode(w));
						edgeTo[w] = vert;
					}
				}
			}
		}
		return edgeTo;

	}

	public static void main(String[] args) 
	{
		//create the original graph
		Graph O = new Graph("src/testFiles/largeGraph1.txt");

		//create the regional graphs
		String[] codes = O.getCodes();
		Graph[] regions = new Graph[7];

		//Region 1
		int numV = 23;
		int r = 0;
		int s = 0;
		String[] reg1Codes = new String[numV];
		copyCodes(codes, s, reg1Codes);
		regions[r] = O.subgraph(reg1Codes);

		//Region 2
		r++;
		s += numV;
		s += 4;
		numV = 14;
		String[] reg2Codes = new String[numV];
		copyCodes(codes, s, reg2Codes);
		regions[r] = O.subgraph(reg2Codes);

		//Region 3
		r++;
		s += numV;
		s+= 5;
		numV = 9;
		String[] reg3Codes = new String[numV];
		copyCodes(codes, s, reg3Codes);
		regions[r] = O.subgraph(reg3Codes);

		//Region 4
		r++;
		s += numV;
		s+= 3;
		numV = 13;
		String[] reg4Codes = new String[numV];
		copyCodes(codes, s, reg4Codes);
		regions[r] = O.subgraph(reg4Codes);

		//Region 5
		r++;
		s += numV;
		s+= 1;
		numV = 25;
		String[] reg5Codes = new String[numV];
		copyCodes(codes, s, reg5Codes);
		regions[r] = O.subgraph(reg5Codes);

		//Region 6
		r++;
		s += numV;
		s+= 7;
		numV = 19;
		String[] reg6Codes = new String[numV];
		copyCodes(codes, s, reg6Codes);
		regions[r] = O.subgraph(reg6Codes);

		//Region 7
		r++;
		s += numV;
		s+= 5;
		numV = 16;
		String[] reg7Codes = new String[numV];
		copyCodes(codes, s, reg7Codes);
		regions[r] = O.subgraph(reg7Codes);

		boolean passed = true;

		//Build Regional Networks

		Graph R = regions[3];
		regions[3] = RegNet.run(R, R.V()*500);
		Graph E = new Graph("src/testFiles/largeReg" + (3 + 1) + ".txt");

		if(!regions[3].equals(E)) 
		{
			passed = false;
			System.out.println("Expected:");
			for (int i = 0; i < E.sortedEdges().size(); i++) System.out.println(E.sortedEdges().get(i).toString());
			System.out.println("\nActual:");
			for (int i = 0; i < regions[3].sortedEdges().size(); i++) System.out.println(regions[3].sortedEdges().get(i).toString());
			System.out.println();

		}
		printMsg(passed);
	}

	private static void printMsg(boolean passed) 
	{
		if(passed) 
			System.out.println("Passed Part C tests!");
		else
			System.out.println("Did not pass all parts of the part C tests.");
	}



	private static void copyCodes(String[] orig, int start, String[] reg) 
	{
		for(int i = 0; i < reg.length; i++) 
			reg[i] = orig[i + start];
	}
}
