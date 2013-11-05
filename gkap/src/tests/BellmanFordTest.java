package tests;

import bellman_ford.BellmanFordImpl;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class BellmanFordTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		//Import des Graphen der benutzt werden soll
		graph = Importer.importExample(graph, "graph_02", attrNames);
		
		//Initialisieren des Algorithmus mit <START> und dem Attributnamen wo sich die Kosten an der Kante befinden
		BellmanFordImpl algo = new BellmanFordImpl(graph, "München", attrNames[0]);
		
		//Ermitteln des Pfades und der Kosten zum <ZIEL>
		String str = algo.stringRouteToTarget("Hamburg");
		
		//Print den Pfad
		System.out.println(str);
		System.out.println("Count Graph access:"+algo.getCountOfGraphAccess());
		
	}

}
