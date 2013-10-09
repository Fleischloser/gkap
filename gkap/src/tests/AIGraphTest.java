package tests;

import java.util.List;

import graph_importer.Importer;
import graph_lib.AIGraph;

public class AIGraphTest {
	
	public static void main(String[] args) {
		AIGraph g = AIGraph.init();
		
		String[] attrNames = {"distance"};
		String graphName = "graph_01";
		
		g = Importer.importExample(g, graphName, attrNames);
		
		if (g != null) {
			System.out.println(graphName+":");
			List<String> nodeList = g.getVertexes();
			for (String node : nodeList) {
				System.out.println("Node:"+node);
				
				List<String> edgeList = g.getIncident(node);
				for (String edge : edgeList) {
					String target = g.getTarget(edge);
					if (target.equals(node)) {
						target = g.getSource(edge);
					}
					System.out.println("  ->Edge:"+target+" "+attrNames[0]+":"+g.getValE(edge, attrNames[0]));	
				}
			}
		} else {
			System.err.println("Der Graph konnte nicht importiert werden");
		}
		
	}

}
