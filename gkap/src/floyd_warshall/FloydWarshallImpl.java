package floyd_warshall;

import java.util.List;
import graph_lib.AIGraph;

public class FloydWarshallImpl {
	private AIGraph graph;
	private String startNode;
	private String edgeDistAttrName;
	private boolean negCircle = false;
	private boolean overflowError = false;
	private long countAccessForAlgo = -1L;

	private String attrDistanceName = "distance";
	private String attrUsedEdge = "usedEdge";
	private int numberOfVertices;
	private int[][] D;
	private int[][] T;
	List<String>listOfVertices;
	
	public FloydWarshallImpl (AIGraph graph, String edgeDistAttrName){
		this.graph = graph;
		this.edgeDistAttrName = edgeDistAttrName;
		listOfVertices = graph.getVertexes();
		numberOfVertices = listOfVertices.size();
		D = new int[numberOfVertices][numberOfVertices];
		T = new int[numberOfVertices][numberOfVertices];
		for (int i = 0; i < numberOfVertices; i++){
			List<String>edgesOfCurrentVertex = graph.getIncident(listOfVertices.get(i));
			for (int j = 0; j < numberOfVertices; j++){
				T[i][j] = 0;
				if (i == j){
					D[i][j] = 0;
				}else{
					int dist = Integer.MAX_VALUE;
					for(String edge : edgesOfCurrentVertex){
						if(graph.getTarget(edge) == listOfVertices.get(j)){
							dist = graph.getValE(edge, edgeDistAttrName);
							break;
						}
					}
					D[i][j]	= dist;		
				}
					
			}
		}
		long beforeAcc = this.graph.getCountGraphAccesses();
		doAlgorithm();
		countAccessForAlgo = this.graph.getCountGraphAccesses() - beforeAcc; 
	}	
	
	private void doAlgorithm(){
		
	}
	
	public void printMatrices(){
		for (int i = 0; i < numberOfVertices; i++){
			for (int j = 0; j < numberOfVertices; j++){
				System.out.print(" " + D[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < numberOfVertices; i++){
			for (int j = 0; j < numberOfVertices; j++){
				System.out.print(" " + T[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
} 
