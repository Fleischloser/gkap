package tests;

import graph_importer.Importer;
import graph_lib.AIGraph;
import p3_edmonds_karp.EdmondsKarpImpl;

public class EdmondsKarpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		//Import des Graphen der benutzt werden soll
//		graph = Importer.importExample(graph, "graph_12", attrNames);
//		EdmondsKarpImpl algo = new EdmondsKarpImpl(graph, attrNames[0], "q", "s");
		
//		graph = Importer.importExample(graph, "graph_09", attrNames);
//		new EdmondsKarpImpl(graph, attrNames[0], "Quelle", "Senke");
		
		graph = Importer.importExample(graph, "graph_08", attrNames);
		new EdmondsKarpImpl(graph, attrNames[0], "Rostock", "München");
	}

}
