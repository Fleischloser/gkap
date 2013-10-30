package tests;

import bellman_ford.BellmanFordImpl;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class BellmanFordTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		graph = Importer.importExample(graph, "graph_02", attrNames);
		
		BellmanFordImpl algo = new BellmanFordImpl(graph, "München", attrNames[0]);
		
		String str = algo.stringRouteToTarget("Augsburg");
		
		System.out.println(str);
		
		algo = new BellmanFordImpl(graph, "München", attrNames[0]);
		
		//graph.getAttrV("München");
		String used = graph.getStrV("München", "usedEdge");
		System.out.println("us:"+used);
		str = algo.stringRouteToTarget("Augsburg");
		
		System.out.println(str);
	}

}
