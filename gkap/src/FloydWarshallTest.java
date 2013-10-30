import floyd_warshall.*;
import graph_importer.Importer;
import graph_lib.AIGraph;

public class FloydWarshallTest {
	
	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		graph = Importer.importExample(graph, "graph_02", attrNames);
		
		FloydWarshallImpl algo = new FloydWarshallImpl(graph, attrNames[0]);
		algo.printMatrices();
//		String str = algo.stringRouteToTarget("München");
		
//		System.out.println(str);
		
		System.out.println("##########");
		
		FloydWarshallImpl algo2 = new FloydWarshallImpl(graph, attrNames[0]);
		
//		String str2 = algo2.stringRouteToTarget("Stuttgart");
		
//		System.out.println(str2);
	}

}



