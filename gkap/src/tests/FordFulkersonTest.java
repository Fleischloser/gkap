package tests;

import p3_ford_fulkerson.FordFulkersonImpl;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class FordFulkersonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		//Import des Graphen der benutzt werden soll
//		graph = Importer.importExample(graph, "graph_12", attrNames);
//		
//		FordFulkersonImpl algo = new FordFulkersonImpl(graph, attrNames[0], "q", "s");
		
		graph = Importer.importExample(graph, "graph_08", attrNames);
		
		new FordFulkersonImpl(graph, attrNames[0], "Rostock", "München");
	}

}
