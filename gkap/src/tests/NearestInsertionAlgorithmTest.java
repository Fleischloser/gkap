package tests;

import graph_importer.Importer;
import graph_lib.AIGraph;
import p4_hamilton.NearestInsertionAlgorithmImpl;
import p4_hierholzer.HierholzerImpl;

public class NearestInsertionAlgorithmTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AIGraph graph = null;
		String[] attrNames = {"distance"};
		
//		graph = AIGraph.init();
//		graph = Importer.importExample(graph, "graph_p4_example", attrNames);
//		new HierholzerImpl(graph, "s");
		
		
		graph = AIGraph.init();
		graph = Importer.importExample(graph, "graph_11", attrNames);
		new NearestInsertionAlgorithmImpl(graph, attrNames[0], "v1");

//		graph = AIGraph.init();
//		graph = Importer.importExample(graph, "graph_12", attrNames);
//		new NearestInsertionAlgorithmImpl(graph, attrNames[0], "ge");
	}

}
