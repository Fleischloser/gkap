package p4_hamilton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.management.RuntimeErrorException;

import graph_lib.AIGraph;

public class NearestInsertionAlgorithmImpl {

	private AIGraph graph;
	private String edgeDistAttrName;
	private String globalStartNode;
	private String nodeAttreUsedEdge = "used_edge";
	
	private LinkedList<String> circleNodes = new LinkedList<String>();
	
	public NearestInsertionAlgorithmImpl(AIGraph graph, String edgeDistAttrName, String startNode) {
		this.graph = graph;
		this.edgeDistAttrName = edgeDistAttrName;
		
		if (startNode == null || startNode.length() == 0) {
			startNode = this.graph.getVertexes().get(0);
		}

		this.globalStartNode = startNode;
		
		circleNodes.add(this.globalStartNode);
		
		this.doStep2();
	}
	
	public void doStep2() {
		String[] nearestNodeEdge = this.findNearestInsertion();
		String nearestNode = nearestNodeEdge[0];
		String nearestEdge = nearestNodeEdge[1];
		
		//Raussuchen wo einfügen....
		if (this.circleNodes.size() == 1) {
			this.circleNodes.addLast(nearestNode);
			this.graph.setStrV(this.circleNodes.get(0), this.nodeAttreUsedEdge, nearestEdge);
		} else {
			
			List<String> allEdgesNearest = this.graph.getIncident(nearestNode);
			
			String edgeToPrev = null;
			String edgeToNext = null;
			int minDist = -1;
			
			for (int i = 0; i < this.circleNodes.size(); i++) {
				String prevNode = this.circleNodes.get(i);
				
				String nextNode = null;
				if (i+1 >= this.circleNodes.size()) {
					nextNode = this.circleNodes.getFirst();
				} else {
					nextNode = this.circleNodes.get(i+1);
				}
				
				String innerEdgeToPrev = null;
				int innerDistToPrev = -1;
				String innerEdgeToNext = null;
				int innerDistToNext = -1;
				for (String edgeNear : allEdgesNearest) {
					String otherNode = this.getOtherNodeFromEdge(edgeNear, nearestNode);
					int distNear = this.graph.getValE(edgeNear, this.edgeDistAttrName);
					if (otherNode.equals(prevNode)) {
						//zeigt auf prev node
						if (innerEdgeToPrev == null || innerDistToPrev > distNear) {
							innerEdgeToPrev = edgeNear;
							innerDistToPrev = distNear;
						}
					} else if (otherNode.equals(nextNode)) {
						//zeigt auf next node
						if (innerEdgeToNext == null || innerDistToNext > distNear) {
							innerEdgeToNext = edgeNear;
							innerDistToNext = distNear;
						}
					}
				}
				
				if (innerEdgeToPrev != null && innerEdgeToNext != null) {
					//die summe der kosten über diese Kante von prev zu next

					int sumDistNear = innerDistToPrev = innerDistToNext;
					int sumFromStartToPrev = this.getDistanceBetweenIndexes(0, i);
					int sumFromNextToEnd = this.getDistanceBetweenIndexes((i+1), this.circleNodes.size());
					
					int sumSum = sumDistNear + sumFromStartToPrev + sumFromNextToEnd;
					
					if (edgeToPrev == null || sumSum < minDist) {
						edgeToPrev = innerEdgeToPrev;
						edgeToNext = innerEdgeToNext;
						minDist = sumSum;
					}
				}
			}
			
		}
	}
	
	public int getDistanceBetweenIndexes(int startIdx, int targetIdx) {
		int ret = 0;
		
		for (int i = startIdx; i < targetIdx; i++) {
			String node = this.circleNodes.get(i);
			String usedEdge = this.graph.getStrV(node, this.nodeAttreUsedEdge);
			int dist = this.graph.getValE(usedEdge, this.edgeDistAttrName);
			
			ret = ret + dist;
		}
		
		return ret;
	}
	
	public String[] findNearestInsertion() {
		
		String edgeMin = null;
		String nodeMin = null;
		int distMin = -1;
		
		for (String node : this.circleNodes) {
			List<String> edges = this.graph.getIncident(node);
			for (String edge : edges) {
				String target = this.getOtherNodeFromEdge(edge, node);
				if (!this.circleNodes.contains(target)) {
					//Node noch nicht im Kreis
					int dist = this.graph.getValE(edge, this.edgeDistAttrName);
					if (edgeMin == null || distMin > dist) {
						edgeMin = edge;
						nodeMin = target;
						distMin = dist;
					}
				}
			}
		}
		
		return new String[]{nodeMin,edgeMin};
	}
	
	
	public String getOtherNodeFromEdge(String edge, String node) {
		String ret = null;
		
		if (this.graph.getSource(edge).equals(node)) {
			ret = this.graph.getTarget(edge);
		} else {
			ret = this.graph.getSource(edge);
		}
		
		return ret;
	}

}
