package floyd_warshall;

import java.util.List;
import graph_lib.AIGraph;

public class FloydWarshallImpl {
	private AIGraph graph;
	private String edgeDistAttrName;
	private boolean negCircle = false;
	private long countAccessGraph;
	private long countAccessMatrix = 0;

	private int numberOfVertices;
	private int[][] D;
	private int[][] T;
	List<String>listOfVertices;
	
	/**
	 * Initialisieren des Algorithmus mit einem Graphen und dem Attributnamen der Kosten an der Kante
	 * 
	 * @param graph - Graph der untersucht werden soll
	 * @param edgeDistAttrName - Attributname über den die Kosten an der Kante geholt werden können
	 */
	public FloydWarshallImpl (AIGraph graph, String edgeDistAttrName){
		this.graph = graph;
		this.edgeDistAttrName = edgeDistAttrName;
		this.listOfVertices = graph.getVertexes();
		this.numberOfVertices = listOfVertices.size();
		this.D = new int[this.numberOfVertices][this.numberOfVertices];
		this.T = new int[this.numberOfVertices][this.numberOfVertices];

		long beforeAcc = this.graph.getCountGraphAccesses();
		for (int i = 0; i < this.numberOfVertices; i++){
			List<String>edgesOfCurrentVertex = graph.getIncident(this.listOfVertices.get(i));
			for (int j = 0; j < this.numberOfVertices; j++){
				T[i][j] = -1;
				if (i == j){
					D[i][j] = 0;
				}else{
					int dist = Integer.MAX_VALUE;
					for(String edge : edgesOfCurrentVertex){
						if(graph.getTarget(edge) == this.listOfVertices.get(j)){
							dist = graph.getValE(edge, this.edgeDistAttrName);
							break;
						}
						
						int isDierectedEdge = graph.getValE(edge, "isDirectedEdge");
						if (isDierectedEdge == 0) {
							if(graph.getSource(edge) == listOfVertices.get(j)){
								dist = graph.getValE(edge, this.edgeDistAttrName);
								break;
							}
						}
					}
					D[i][j]	= dist;		
				}
					
			}
		}
		
		//this.printVariableVals();
		//System.out.println("-------");
		//this.printMatrices();
		doAlgorithm();
		this.countAccessGraph = this.graph.getCountGraphAccesses() - beforeAcc; 
	}
	
	public long getCountForGraph() {
		return this.countAccessGraph;
	}
	
	public long getCountMatrix() {
		return this.countAccessMatrix;
	}
	
	/**
	 * Algorithmus zum berchnen der Matrizen
	 */
	private void doAlgorithm(){
		for(int j = 0; j < this.numberOfVertices; j++){
			if (this.negCircle) {
				break;
			}
			for(int i = 0; i < this.numberOfVertices; i++){
				if(i == j){
					continue;
				}
				for(int k = 0; k < this.numberOfVertices; k++){
					if(k == j){
						continue;
					}
					countAccessMatrix++;
					int temp = D[i][k];

					countAccessMatrix++;
					countAccessMatrix++;
					int dist = D[i][j] + D[j][k];
					
					if (dist < Integer.MAX_VALUE && dist >= 0 && temp > dist) {

						countAccessMatrix++;
						D[i][k] = dist;
						
						countAccessMatrix++;
						T[i][k] = j;
					}
				}

				countAccessMatrix++;
				if(D[i][i] < 0){
					this.negCircle = true;
					break;
				}
			}
			//this.printMatrices();
		}
	}
	
	/**
	 * Printausgabe welcher Index welchem Knoten entspricht.
	 * Nur zum Testen
	 */
	@SuppressWarnings("unused")
	private void printVariableVals () {
		for (int i = 0; i < this.listOfVertices.size(); i++ ) {
			System.out.println(i+"<>"+this.listOfVertices.get(i));
		}
	}
	
	/**
	 * Ermittelt den Pfad vom Startknoten zum Zielknoten und die geringsten Kosten
	 * 
	 * @param source - Startknoten
	 * @param target - Zielknoten
	 * @return String : <START>##<ZWISCHENKNOTEN>##<ZWISCHENKNOTEN>##<ZIEL>#<KOSTEN>
	 */
	public String stringRouteSourceToTarget (String source, String target) {
		
		if (this.negCircle) {
			return "ERROR:1001:NEG Circle";	
		}
		
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
			if (dist == Integer.MAX_VALUE) {
				return "ERROR:1000:No route from "+this.listOfVertices.get(idxSource)+" to "+this.listOfVertices.get(idxTarget);
			}
			
			String ret = this.listOfVertices.get(idxSource);
			ret = ret + "#" + recursive(idxSource, idxTarget);			
			ret = ret + "#" + this.listOfVertices.get(idxTarget);
			ret = ret + "#" + dist;
			
			return ret;
		}
		
		return "ERROR:1002:Start or Target unknown";
	}
	
	/**
	 * Rekusives ermitteln der Printausgabe des Pfades
	 * 
	 * @param idxSource
	 * @param idxTarget
	 * @return String
	 */
	private String recursive(int idxSource, int idxTarget) {
		int actIdx = T[idxSource][idxTarget];
		if (actIdx >= 0) {
			String ret = recursive(idxSource, actIdx) + "#" + this.listOfVertices.get(actIdx);
			
			ret = ret + "#" + recursive(actIdx, idxTarget);
			
			return ret;
		}
		
		return "";
	}
	
	/**
	 * Printausgabe der Matrizen
	 */
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
		
		System.out.format("%9s", "");
		for (int i = 0; i < numberOfVertices; i++){
			System.out.format("%6s", this.listOfVertices.get(i).substring(0, (this.listOfVertices.get(i).length() > 5 ? 5 : this.listOfVertices.get(i).length())));
		}
		System.out.println();
		
		for (int i = 0; i < numberOfVertices; i++){
			System.out.format("%2s %6s", ""+i, this.listOfVertices.get(i).substring(0, (this.listOfVertices.get(i).length() > 5 ? 5 : this.listOfVertices.get(i).length())));
			for (int j = 0; j < numberOfVertices; j++){
				//System.out.print(" " + T[i][j]);
				System.out.format("%6s", T[i][j]);
			}
			System.out.println();
		}
		System.out.println("#######################");
	}
	
} 
