package bellman_ford;

import java.util.List;

import graph_lib.AIGraph;


/**
 * http://www-m9.ma.tum.de/material/spp/Bellman-Ford-Algorithmus.html
 * 
 */
public class BellmanFordImpl {

	private AIGraph graph;
	private String startNode;
	private String edgeDistAttrName;
	private boolean negCircle = false;
	private boolean overflowError = false;
	private long countAccessForAlgo = -1L;

	private String attrDistanceName = "distance";
	private String attrUsedEdge = "usedEdge";
	
	/*
	 *Initialisieren der Suche 
	 */
	public BellmanFordImpl (AIGraph graph, String startNode, String edgeDistAttrName) {
		this.graph = graph;
		this.startNode = startNode;
		this.edgeDistAttrName = edgeDistAttrName;
		
		long beforeAcc = this.graph.getCountGraphAccesses();
		doAlgorithmus();
		countAccessForAlgo = this.graph.getCountGraphAccesses() - beforeAcc; 
		
	}
	
	public String stringRouteToTarget(String target) {
		if (negCircle) {
			return "ERROR:1001:negCircle";
		} else if (overflowError) {
			return "ERROR:1002:overflowError";
		}
		
		int distance = graph.getValV(target, attrDistanceName);
		String ret = "#"+distance;
		
		String path = recursive(target, "");
		
		return path+ret;
	}
	
	private String recursive(String node, String str) {

		if (node.equals(this.startNode)) {
			return node;
		}
		
		String usedEdge = graph.getStrV(node, attrUsedEdge);
		String sourceNode = graph.getSource(usedEdge);
		if (node.equals(sourceNode)) {
			//Kann nur passieren wenn es sich um eine ungerichtete Kante handelt
			sourceNode = graph.getTarget(usedEdge);
		}
		
		String ret = (recursive(sourceNode, str))+":"+node;
		
		return ret;
	}
	
	private void doAlgorithmus() {
		
		List<String> nodeList = graph.getVertexes();
		if (nodeList.size() > 0) {
			//Init des Graphen
			for (String nodeId : nodeList) {
				graph.setStrV(nodeId, attrUsedEdge, null);
				if (!nodeId.equals(this.startNode)) {
					graph.setValV(nodeId, attrDistanceName, Integer.MAX_VALUE);
				} else {
					graph.setValV(nodeId, attrDistanceName, 0);
				}
			}
			

			List<String> edgeList = graph.getEdges();
			for (int i = 0; i < nodeList.size(); i++) {
				for (String edgeId : edgeList) {
					String sourceId = graph.getSource(edgeId);
					String targetId = graph.getTarget(edgeId);
					int valSource = graph.getValV(sourceId, attrDistanceName);
					int valTarget = graph.getValV(targetId, attrDistanceName);
					int valEdge = graph.getValE(edgeId, edgeDistAttrName);
					
					int dist = valSource + valEdge;
					if (dist < Integer.MAX_VALUE && dist >= 0 && valTarget > dist) {
						graph.setValV(targetId, attrDistanceName, dist);
						graph.setStrV(targetId, attrUsedEdge, edgeId);
					} else if (valSource < Integer.MAX_VALUE && dist < 0) {
						//ERROR OVERFLOW!!!!
						overflowError = true;
					}
					
					
					int isDierectedEdge = graph.getValE(edgeId, "isDirectedEdge");
					if (isDierectedEdge == 0) {
						//Ungerichtet also auch die andere Richtung testen...
						//das bedeutet source und target tauschen
						sourceId = graph.getTarget(edgeId);
						targetId = graph.getSource(edgeId);
						
						valSource = graph.getValV(sourceId, attrDistanceName);
						valTarget = graph.getValV(targetId, attrDistanceName);
						valEdge = graph.getValE(edgeId, edgeDistAttrName);
						
						dist = valSource + valEdge;
						if (dist < Integer.MAX_VALUE && dist >= 0 && valTarget > dist) {
							graph.setValV(targetId, attrDistanceName, dist);
							graph.setStrV(targetId, attrUsedEdge, edgeId);
						} else if (valSource < Integer.MAX_VALUE && dist < 0) {
							//ERROR OVERFLOW!!!!
							overflowError = true;
						}
					}
				}
			}
			
			negCircle = false;
			for (String edgeId : edgeList) {
				String sourceId = graph.getSource(edgeId);
				String targetId = graph.getTarget(edgeId);
				int valSource = graph.getValV(sourceId, attrDistanceName);
				int valTarget = graph.getValV(targetId, attrDistanceName);
				int valEdge = graph.getValE(edgeId, edgeDistAttrName);
				
				int dist = valSource + valEdge;
				if (dist < Integer.MAX_VALUE && dist >= 0 && valTarget > dist) {
					negCircle = true;
					break;
				} else if (valSource < Integer.MAX_VALUE && dist < 0) {
					//ERROR OVERFLOW!!!!
					overflowError = true;
				}
				
				
				int isDierectedEdge = graph.getValE(edgeId, "isDirectedEdge");
				if (isDierectedEdge == 0) {
					//Ungerichtet also auch die andere Richtung testen...
					//das bedeutet source und target tauschen
					sourceId = graph.getTarget(edgeId);
					targetId = graph.getSource(edgeId);
					
					valSource = graph.getValV(sourceId, attrDistanceName);
					valTarget = graph.getValV(targetId, attrDistanceName);
					valEdge = graph.getValE(edgeId, edgeDistAttrName);
					
					dist = valSource + valEdge;
					if (dist < Integer.MAX_VALUE && dist >= 0 && valTarget > dist) {
						negCircle = true;
						break;
					} else if (valSource < Integer.MAX_VALUE && dist < 0) {
						//ERROR OVERFLOW!!!!
						overflowError = true;
					}
				}
			}
		}	
	}
	
	public long getCountOfGraphAccess() {
		return this.countAccessForAlgo;
	}
	
}
