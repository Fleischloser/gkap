package tests;

import bellman_ford.BellmanFordImpl;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class BellmanFordTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		graph = Importer.importExample(graph, "graph_02", attrNames);
		
		BellmanFordImpl algo = new BellmanFordImpl(graph, "Augsburg", attrNames[0]);
		
		String str = algo.stringRouteToTarget("München");
		
		System.out.println(str);
		
		System.out.println("##########");
		
		BellmanFordImpl algo2 = new BellmanFordImpl(graph, "München", attrNames[0]);
		
		String str2 = algo2.stringRouteToTarget("Stuttgart");
		
		System.out.println(str2);
	}

}
