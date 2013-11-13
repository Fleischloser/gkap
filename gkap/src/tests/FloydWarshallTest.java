package tests;
import p2_floyd_warshall.*;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class FloydWarshallTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		//Import des Graphen der benutzt werden soll
		graph = Importer.importExample(graph, "graph_02", attrNames);
		
		//Initialisieren des Algorithmus
		FloydWarshallImpl algo = new FloydWarshallImpl(graph, attrNames[0]);
		
		//Ausgabe der Matrizen
		algo.printMatrices();
		
		//Ermitteln des Pfades und der Kosten von <START> zum <ZIEL>
		String path = algo.stringRouteSourceToTarget("München", "Lübeck");
		
		//Print den Pfad
		System.out.println("path:"+path);
		System.out.println("count Graph:"+algo.getCountForGraph());
		System.out.println("count Matrix:"+algo.getCountMatrix());
		System.out.println("Sum:"+(algo.getCountMatrix()+algo.getCountForGraph()));

	}

}



