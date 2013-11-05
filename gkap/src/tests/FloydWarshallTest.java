package tests;
import floyd_warshall.*;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class FloydWarshallTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		//Import des Graphen der benutzt werden soll
		graph = Importer.importExample(graph, "graph_08", attrNames);
		
		//Initialisieren des Algorithmus
		FloydWarshallImpl algo = new FloydWarshallImpl(graph, attrNames[0]);
		
		//Ausgabe der Matrizen
		algo.printMatrices();
		
		//Ermitteln des Pfades und der Kosten von <START> zum <ZIEL>
		String path = algo.stringRouteSourceToTarget("Hamburg", "Dresden");
		System.out.println("path:"+path);
		System.out.println("count:"+algo.getCountForAlgo());
		
		path = algo.stringRouteSourceToTarget("München", "Hamburg");
		System.out.println("path:"+path);

	}

}



