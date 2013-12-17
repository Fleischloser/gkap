package p4_hamilton;

import java.util.LinkedList;
import java.util.List;

import graph_lib.AIGraph;

public class NearestInsertionAlgorithmImpl {

	private AIGraph graph;
	private String edgeDistAttrName;
	private String globalStartNode;
	private String nodeAttreUsedEdge = "used_edge";
	
	private LinkedList<String> circleNodes = new LinkedList<String>();
	private int countAllNodesInGraph = -1;

	private long countGraphBegin = 0;
	private long countGraphEnd = 0;
	
	public NearestInsertionAlgorithmImpl(AIGraph graph, String edgeDistAttrName, String startNode) {
		this.graph = graph;
		
		this.countGraphBegin = graph.getCountGraphAccesses();
		
		this.countAllNodesInGraph = this.graph.getVertexes().size();
		this.edgeDistAttrName = edgeDistAttrName;
		
		if (startNode == null || startNode.length() == 0) {
			startNode = this.graph.getVertexes().get(0);
		}

		this.globalStartNode = startNode;
		
		circleNodes.add(this.globalStartNode);
		
		this.doStep2();

		this.countGraphEnd = graph.getCountGraphAccesses();
		
		System.out.println();
		System.out.println();
		System.out.println("COUNT - GRAPH:" + (this.countGraphEnd - this.countGraphBegin));
	}
	
	public void doStep2() {
		String[] nearestNodeEdge = this.findNearestInsertion();
		String nearestNode = nearestNodeEdge[0];
		String nearestEdge = nearestNodeEdge[1];
		
		System.out.println("NEW NEAREST:"+nearestNode+" FROM EDGE: "+ this.getOtherNodeFromEdge(nearestEdge, nearestNode));
		
		//Raussuchen wo einfügen....
		if (this.circleNodes.size() == 1) {
			this.circleNodes.addLast(nearestNode);
			this.graph.setStrV(this.circleNodes.get(0), this.nodeAttreUsedEdge, nearestEdge);

			this.printCircleToConsole();
		} else {
			
			List<String> allEdgesNearest = this.graph.getIncident(nearestNode);
			
			String outerEdgeToPrev = null;
			String outerEdgeToNext = null;
			String outerPrevNode = null;
			String outerNextNode = null;
			int minDist = Integer.MAX_VALUE;
			
			for (int i = 0; i < this.circleNodes.size(); i++) {
				String prevNode = this.circleNodes.get(i);
				
				String nextNode = null;
				if (i+1 >= this.circleNodes.size()) {
					nextNode = this.circleNodes.getFirst();
				} else {
					nextNode = this.circleNodes.get(i+1);
				}
				
				String innerEdgeToPrev = null;
				int innerDistToPrev = Integer.MAX_VALUE;
				String innerEdgeToNext = null;
				int innerDistToNext = Integer.MAX_VALUE;
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

					int sumDistNear = innerDistToPrev + innerDistToNext;
					int sumFromStartToPrev = this.getDistanceBetweenIndexes(0, i);
					int sumFromNextToEnd = this.getDistanceBetweenIndexes((i+1), this.circleNodes.size());
					
					int sumSum = sumDistNear + sumFromStartToPrev + sumFromNextToEnd;
					System.out.println("sumSum:"+sumSum+" = Prev:" +sumFromStartToPrev+"## NEAREST:"+sumDistNear  +"## End:"+sumFromNextToEnd);
					
					if (outerEdgeToPrev == null || sumSum < minDist) {
						outerEdgeToPrev = innerEdgeToPrev;
						outerEdgeToNext = innerEdgeToNext;
						outerPrevNode = prevNode;
						outerNextNode = nextNode;
						minDist = sumSum;
						//System.out.println("minDist:"+minDist);
					}
				}
			}
			
			if (outerEdgeToPrev != null && outerEdgeToNext != null && outerPrevNode != null && outerNextNode != null) {

				this.graph.setStrV(outerPrevNode, this.nodeAttreUsedEdge, outerEdgeToPrev);
				this.graph.setStrV(nearestNode, this.nodeAttreUsedEdge, outerEdgeToNext);
				
				this.circleNodes.add(this.circleNodes.indexOf(outerNextNode), nearestNode);
				
				String lastNode = this.circleNodes.getLast();
				String lastNodeUsedEdge = this.graph.getStrV(lastNode, this.nodeAttreUsedEdge);
				if (lastNodeUsedEdge == null || lastNodeUsedEdge.length() == 0) {
					//der letzte Knoten in der Liste Zeigt noch nicht auf den Start...
					String firstNode = this.circleNodes.getFirst();
					
					List<String> edges = this.graph.getIncident(lastNode);
					String edgeMin = null;
					int distMin = Integer.MAX_VALUE;
					for (String edge : edges) {
						String target = this.getOtherNodeFromEdge(edge, lastNode);
						if (target.equals(firstNode)) {
							int dist = this.graph.getValE(edge, this.edgeDistAttrName);
							if (edgeMin == null || distMin > dist) {
								edgeMin = edge;
								distMin = dist;
							}
						}
					}
					
					if (edgeMin != null) {
						this.graph.setStrV(lastNode, this.nodeAttreUsedEdge, edgeMin);
					}
				}
				
				this.printCircleToConsole();
			}
			
		}
		
		if (this.circleNodes.size() >= this.countAllNodesInGraph) {
			//ende...
			System.out.println("****** ENDE *******");
			System.out.println();
		} else {
			doStep2();
		}
	}
	
	public int getDistanceBetweenIndexes(int startIdx, int targetIdx) {
		int ret = 0;
		
		for (int i = startIdx; i < targetIdx; i++) {
			String node = this.circleNodes.get(i);
			String usedEdge = this.graph.getStrV(node, this.nodeAttreUsedEdge);
			int dist = this.graph.getValE(usedEdge, this.edgeDistAttrName);
			
			if (dist != Integer.MAX_VALUE) {
				ret = ret + dist;
			}
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
	
	public void printCircleToConsole() {
		
		int total = 0;
		for (String node : this.circleNodes) {
			int dist = this.graph.getValE(this.graph.getStrV(node, this.nodeAttreUsedEdge), this.edgeDistAttrName);
			total = total + dist;
			System.out.print(node + ":" + dist + "#");
		}
		System.out.print(this.circleNodes.getFirst() + "  *** TOTAL: "+total);
		System.out.println();
		System.out.println("#############");
		System.out.println();
	}

}
