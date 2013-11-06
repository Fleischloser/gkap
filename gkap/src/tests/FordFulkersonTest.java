package tests;

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
		
		
	}

}
