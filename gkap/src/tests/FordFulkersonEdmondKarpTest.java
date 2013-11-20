package tests;

import graph_importer.Importer;
import graph_lib.AIGraph;
import p3_abstract.FordFulkersonEdmondKarpImpl;

public class FordFulkersonEdmondKarpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		//Import des Graphen der benutzt werden soll
//		graph = Importer.importExample(graph, "graph_12", attrNames);
//		FordFulkersonEdmondKarpImpl algo = new FordFulkersonEdmondKarpImpl
//		(graph, attrNames[0], "q", "s", true);
		
		graph = Importer.importExample(graph, "graph_09", attrNames);
		new FordFulkersonEdmondKarpImpl(graph, attrNames[0], "Quelle", "Senke", false);
		
//		graph = Importer.importExample(graph, "graph_08", attrNames);
//		new FordFulkersonEdmondKarpImpl(graph, attrNames[0], "Rostock", "München", false);
		

	}

}
