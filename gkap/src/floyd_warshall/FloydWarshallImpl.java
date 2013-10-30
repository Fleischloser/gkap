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
				T[i][j] = -1;
				if (i == j){
					D[i][j] = 0;
				}else{
					int dist = Integer.MAX_VALUE;
					for(String edge : edgesOfCurrentVertex){
						if(graph.getTarget(edge) == listOfVertices.get(j)){
							dist = graph.getValE(edge, edgeDistAttrName);
							break;
						}
						
						int isDierectedEdge = graph.getValE(edge, "isDirectedEdge");
						if (isDierectedEdge == 0) {
							if(graph.getSource(edge) == listOfVertices.get(j)){
								dist = graph.getValE(edge, edgeDistAttrName);
								break;
							}
						}
					}
					D[i][j]	= dist;		
				}
					
			}
		}
		long beforeAcc = this.graph.getCountGraphAccesses();
		//this.printVariableVals();
		//System.out.println("-------");
		//this.printMatrices();
		doAlgorithm();
		countAccessForAlgo = this.graph.getCountGraphAccesses() - beforeAcc; 
	}	
	
	private void doAlgorithm(){
		for(int j = 0; j < numberOfVertices; j++){
			for(int i = 0; i < numberOfVertices; i++){
				if(i == j){
					continue;
				}
				for(int k = 0; k < numberOfVertices; k++){
					if(k == j){
						continue;
					}
					int temp = D[i][k];
					int dist = D[i][j] + D[j][k];
					if (dist < Integer.MAX_VALUE && dist >= 0 && temp > dist) {
						D[i][k] = dist;
					}
					
					if(temp != D[i][k]){
						T[i][k] = j;
					}
				}
				if(D[i][i] < 0){
					negCircle = true;
					break;
				}
			}
			//this.printMatrices();
		}
	}
	
	public void printVariableVals () {
		for (int i = 0; i < this.listOfVertices.size(); i++ ) {
			System.out.println(i+"<>"+this.listOfVertices.get(i));
		}
	}
	
	public String stringRouteSourceToTarget (String source, String target) {
		
		int idxSource = -1;
		int idxTarget = -1;
		for (int i = 0; i < this.listOfVertices.size(); i++) {
			if (this.listOfVertices.get(i).equals(source)) {
				idxSource = i;
			} else if (this.listOfVertices.get(i).equals(target)) {
				idxTarget = i;
			}
		}
		
		if (idxSource >= 0 && idxTarget >= 0) {
			int dist = D[idxSource][idxTarget];
			
			String ret = this.listOfVertices.get(idxSource);
			ret = ret + "#" + recursive(T[idxSource][idxTarget], idxTarget);
			ret = ret + "#" + this.listOfVertices.get(idxTarget);
			ret = ret + "#" + dist;
			
			return ret;
		}
		
		return "";
	}
	
	private String recursive(int idxSource, int idxTarget) {
		if (idxSource >= 0) {
			String ret = this.listOfVertices.get(idxSource);
			
			int newSource = T[idxSource][idxTarget];
			if (newSource >= 0) {
				ret = ret + "#" + recursive(newSource, idxTarget);
			}
			
			return ret;
		}
		
		return "";
	}
	
	public void printMatrices(){
		
		System.out.format("%6s", "");
		for (int i = 0; i < numberOfVertices; i++){
			System.out.format("%6s", this.listOfVertices.get(i).substring(0, (this.listOfVertices.get(i).length() > 5 ? 5 : this.listOfVertices.get(i).length())));
		}
		System.out.println();
		
		for (int i = 0; i < numberOfVertices; i++){
			System.out.format("%6s", this.listOfVertices.get(i).substring(0, (this.listOfVertices.get(i).length() > 5 ? 5 : this.listOfVertices.get(i).length())));
			for (int j = 0; j < numberOfVertices; j++){
				//System.out.print(" " + (D[i][j] == Integer.MAX_VALUE ? "x" : D[i][j]));
				System.out.format("%6s", (D[i][j] == Integer.MAX_VALUE ? "x" : D[i][j]));
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.format("%6s", "");
		for (int i = 0; i < numberOfVertices; i++){
			System.out.format("%6s", this.listOfVertices.get(i).substring(0, (this.listOfVertices.get(i).length() > 5 ? 5 : this.listOfVertices.get(i).length())));
		}
		System.out.println();
		
		for (int i = 0; i < numberOfVertices; i++){
			System.out.format("%6s", this.listOfVertices.get(i).substring(0, (this.listOfVertices.get(i).length() > 5 ? 5 : this.listOfVertices.get(i).length())));
			for (int j = 0; j < numberOfVertices; j++){
				//System.out.print(" " + T[i][j]);
				System.out.format("%6s", T[i][j]);
			}
			System.out.println();
		}
		System.out.println("#######################");
	}
	
} 
