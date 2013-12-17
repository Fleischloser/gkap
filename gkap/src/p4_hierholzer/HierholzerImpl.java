package p4_hierholzer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import graph_lib.AIGraph;

public class HierholzerImpl {
	
	
	private AIGraph graph;
	
	private List<String> globalUsedEdges;
	private List<String> foundCircles;
	
	private String globalStartNode;
	
	private String globalDist = "distance";
	
	private long countGraphBegin = 0;
	private long countGraphEnd = 0;
	
	//Wir speichern die Kante die benutzt wurde um nicht bei dem raussuchen von 
	//der kante zwischen start und zeil unnötig viele zugriffe zu machen
	private String attrNodeUsedEdge = "used_edge";
	
	public HierholzerImpl(AIGraph g, String start) {
		
		this.graph = g;
		
		this.countGraphBegin = g.getCountGraphAccesses();
		
		if (start == null || start.length() == 0) {
			start = this.graph.getVertexes().get(0);
		}
		
		this.globalStartNode = start;
		this.globalUsedEdges = new ArrayList<String>();
		
		foundCircles = this.doFindCircle(this.globalStartNode);
		System.out.println("----------------");
		tempPrintEdges(this.globalStartNode, this.foundCircles);
		System.out.println("################");
		System.out.println();
		
		if (foundCircles.size() > 0) {
			innerDo();
		} else {
			//ERROR kein euler Kreis!!!
			System.err.println("ERROR kein Eulerkreis!!!");
		}
		
		this.countGraphEnd = g.getCountGraphAccesses();

		
		System.out.println();
		System.out.println();
		
		boolean b = false;
		if (this.globalUsedEdges.size() == this.graph.getEdges().size()) {
			b = true;
		}
		System.out.println("EULER-TOUR-FOUND:"+b);
		printCircle();

		
		System.out.println();
		System.out.println();
		System.out.println("COUNT - GRAPH:" + (this.countGraphEnd - 
				this.countGraphBegin));
		
	}
	
	private void tempPrintEdges(String startNode, List<String> edges) {
		
		System.out.println("Circle size "+startNode+":"+edges.size());
		if (edges.size() > 0) {
			System.out.print("Circle from "+startNode);
			String tmpN = startNode;
			for (String tmpE : edges) {
				String targetNode = null;
				if (this.graph.getSource(tmpE).equals(tmpN)) {
					targetNode = this.graph.getTarget(tmpE);
				} else {
					targetNode = this.graph.getSource(tmpE);
				}
				tmpN = targetNode;
				System.out.print(":"+this.graph.getValE(tmpE, this.globalDist)+
						":"+targetNode);
			}
			System.out.println();
		}
	}
	
	private void innerDo() {
		
		String startNode = this.globalStartNode;
		//boolean newCircleFound = false;
		//List<String> newFoundCircles = new ArrayList<String>();
		int i = 0;
		
		while (i < this.foundCircles.size() && this.globalUsedEdges.size() 
				< this.graph.getEdges().size()) {
			String edge = this.foundCircles.get(i);
			String targetNode = null;
			if (this.graph.getSource(edge).equals(startNode)) {
				targetNode = this.graph.getTarget(edge);
			} else {
				targetNode = this.graph.getSource(edge);
			}
			
			List<String> tmpCircle = doFindCircle(targetNode);
			this.foundCircles.addAll((i+1), tmpCircle);

			tempPrintEdges(targetNode, tmpCircle);
			System.out.println("----------------");
			tempPrintEdges(this.globalStartNode, this.foundCircles);
			System.out.println("################");
			System.out.println();
			
			startNode = targetNode;
			i = i +1;
		}
		
	}
	
	
	private List<String> doFindCircle(String startNode) {
		List<String> ret = new ArrayList<String>();
		
		Stack<String> stackNodes = new Stack<String>();
		List<String> tmpUsedEdges = new ArrayList<String>();
		String firstUsedEdge = null;
		String nextNode = null;
		if (startNode != null) {
			System.out.println("doFindCircle: "+startNode);
			
			List<String> edgeList = this.graph.getIncident(startNode);
			for (String edge : edgeList) {
				if (!this.globalUsedEdges.contains(edge)) {
					
					String targetNode = null;
					if (this.graph.getSource(edge).equals(startNode)) {
						//current ist Start von der Kante
						targetNode = this.graph.getTarget(edge);
					} else {
						//current ist Target von der Kante
						targetNode = this.graph.getSource(edge);
					}

					nextNode = targetNode;
					firstUsedEdge = edge;
					this.graph.setStrV(targetNode, this.attrNodeUsedEdge, edge);
					break;
				}
			}
		}

		Stack<String> inspectedNodes = new Stack<String>();
		if (nextNode != null) {
			stackNodes.push(nextNode);
			inspectedNodes.push(startNode);
		}
		
		
		boolean circleFound = false;
		
		while (!stackNodes.isEmpty() && !circleFound) {
			String currentNode = stackNodes.pop();
			inspectedNodes.push(currentNode);
			String tmpUsed = this.graph.getStrV(currentNode, this.attrNodeUsedEdge);
			if (tmpUsed != null && tmpUsed.length() > 0) {
				tmpUsedEdges.add(tmpUsed);
			}
			
//			System.out.print("tmpUsed");
//			for (String ttt : tmpUsedEdges) {
//				System.out.print(":"+this.graph.getValE(ttt, this.globalDist));
//			}
//			System.out.println();
			
			List<String> edgeList = this.graph.getIncident(currentNode);
			for (String edge : edgeList) {
				if (!this.globalUsedEdges.contains(edge) && !tmpUsedEdges.contains(edge)) {
					
					String targetNode = null;
					if (this.graph.getSource(edge).equals(currentNode)) {
						//current ist Start von der Kante
						targetNode = this.graph.getTarget(edge);
					} else {
						//current ist Target von der Kante
						targetNode = this.graph.getSource(edge);
					}

					if (targetNode.equals(startNode)) {
						circleFound = true;
						this.graph.setStrV(targetNode, this.attrNodeUsedEdge, edge);
						inspectedNodes.push(targetNode);
						break;
						
					} else if (!stackNodes.contains(targetNode) && 
							!inspectedNodes.contains(targetNode) && !circleFound) {
						stackNodes.push(targetNode);
						
						this.graph.setStrV(targetNode, this.attrNodeUsedEdge, edge);
					}
					
				}
			}
		}
		
		if (circleFound) {
			
			List<String> tempEdges = new ArrayList<String>();
			
			List<String> tmpNodes = new ArrayList<String>();
			String expectedNode = null;
			while (inspectedNodes.size() > 0) {
				String currentNode = inspectedNodes.pop();
				
				if (expectedNode == null || currentNode.equals(expectedNode)) {
					String usedEdge = this.graph.getStrV(currentNode, 
							this.attrNodeUsedEdge);
					
					String targetNode = null;
					if (this.graph.getSource(usedEdge).equals(currentNode)) {
						targetNode = this.graph.getTarget(usedEdge);
					} else {
						targetNode = this.graph.getSource(usedEdge);
					}

					if (targetNode.equals(startNode)) {
						break;
					}
					tempEdges.add(usedEdge);
					tmpNodes.add(targetNode);
					expectedNode = targetNode;
				}
				
			}

			tempEdges.add(firstUsedEdge);
			this.globalUsedEdges.addAll(tempEdges);
			
			ret = tempEdges;
		}
		
		return ret;
	}
	
	private void printCircle() {
		
		String currNode = this.globalStartNode;
		StringBuffer sb = new StringBuffer();
		sb.append(currNode);
		
		for (String edge : this.foundCircles) {
			String target = null;
			if (this.graph.getSource(edge).equals(currNode)) {
				target = this.graph.getTarget(edge);
			} else {
				target = this.graph.getSource(edge);
			}
			currNode = target;
			sb.append(":"+this.graph.getValE(edge, this.globalDist)+":"+target);
		}
		
		System.out.println(sb.toString());
	}

}
