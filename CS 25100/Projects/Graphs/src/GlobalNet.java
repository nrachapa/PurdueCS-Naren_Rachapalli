import java.util.ArrayList;

public class GlobalNet 
{
	//creates a global network 
	//O : the original graph
	//regions: the regional graphs
	public static Graph run(Graph O, Graph[] regions) 
	{
		Graph regional_graph = generate_regional_graph(O, regions);
		for (int i = 0; i < regions.length; i++) {
			Dijkstra_Output output = dijkstra_path(O, regions[i], O.index(regions[i].getCodes()[0]));
			int[] dist = output.getDist();
			int[] prev = output.getPrev();
			for (int j = i + 1; j < regions.length; j++) {
				int vertex = -1;
				String[] codes = regions[j].getCodes();
				for (int c = 0; c < codes.length; c++) {
					if (vertex == -1) {
						vertex = regional_graph.index(codes[c]);
					} else if (dist[regional_graph.index(codes[c])] < dist[vertex]){
						vertex = regional_graph.index(codes[c]);
					}

				}

				do {
					regional_graph.addEdge(O.getEdge(vertex, prev[vertex]));
					vertex = prev[vertex];
				} while(prev[vertex] != -1);

			}
		}
		regional_graph = regional_graph.connGraph();

		return regional_graph;
	}

	private static Dijkstra_Output dijkstra_path(Graph G, Graph regions, int v) {
		int[] dist = new int[G.V()];
		int[] prev = new int[G.V()];
		DistQueue Q = new DistQueue(G.V());
		dist[v] = 0;
		for (int idx = 0; idx < G.V(); idx++) {
			if (idx != v) {
				dist[idx] = 1000000;
			}
			prev[idx] = -1;
			Q.insert(idx, dist[idx]);
		}
		while (!Q.isEmpty()) {
			int min = Q.delMin();
			String[] rcodes = regions.getCodes();
			for (int i = 0; i < rcodes.length; i++) {
				if (rcodes[i].equals(G.getCode(min))) {
					dist[min] = 0;
					prev[min] = -1;
					break;
				}
			}
			ArrayList<Integer> adjList = G.adj(min);
			for (int i = 0; i < adjList.size(); i++) {
				int w = adjList.get(i);
				int d = dist[min] + G.getEdgeWeight(min, w);
				if (d < dist[w]) {
					dist[w] = d;
					prev[w] = min;
					Q.set(w, d);
				}
			}
		}
		Dijkstra_Output output = new Dijkstra_Output(dist, prev);
		return output;

	}
	
	private static Graph generate_regional_graph(Graph G, Graph[] regions) {
		Graph regional_graph = new Graph(G.V());
		regional_graph.setCodes(G.getCodes());
		for (int i = 0; i < regions.length; i++) {
			ArrayList<Edge> regional_edges = regions[i].sortedEdges();
			for (int j = 0; j < regional_edges.size(); j++) {
				Edge e = regional_edges.get(j);
				regional_graph.addEdge(e);
			}
		}
		return regional_graph;
	}

}


