package tests;

import ford_fulkerson.FordFulkersonImpl;
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
		graph = Importer.importExample(graph, "graph_12", attrNames);
		
		FordFulkersonImpl algo = new FordFulkersonImpl(graph, attrNames[0], "q", "s");
	}

}
