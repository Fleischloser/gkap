package tests;

import bellman_ford.BellmanFordImpl;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class BellmanFortTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		graph = Importer.importExample(graph, "graph_03", attrNames);
		
		BellmanFordImpl algo = new BellmanFordImpl(graph, "v", attrNames[0]);
		
		String str = algo.stringRouteToTarget("x");
		
		System.out.println(str);
	}

}
