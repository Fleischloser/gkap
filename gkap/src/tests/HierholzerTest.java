package tests;

import p4_hierholzer.HierholzerImpl;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class HierholzerTest {

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
		graph = Importer.importExample(graph, "graph_02", attrNames);
		new HierholzerImpl(graph, "München");
		

//		graph = AIGraph.init();
//		graph = Importer.importExample(graph, "graph_12", attrNames);
//		new HierholzerImpl(graph, null);
	}

}
