package tests;

import graph_importer.Importer;
import graph_lib.AIGraph;

public class AIGraphTest {
	
	public static void main(String[] args) {
		AIGraph g = AIGraph.init();
		
		//Name der Attribute die importiert werden
		String[] attrNames = {"distance"};
		
		//Name des Graphen der importiert werden soll. (Gültigkeit von: graph_01 bis graph_11)
		String graphName = "graph_01";
		
		g = Importer.importExample(g, graphName, attrNames);
		
		if (g != null) {
			g.printGraphToConsole();
		} else {
			System.err.println("Der Graph konnte nicht importiert werden");
		}
		
	}

}
