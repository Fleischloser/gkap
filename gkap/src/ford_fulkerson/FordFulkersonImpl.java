package ford_fulkerson;

import graph_lib.AIGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;



public class FordFulkersonImpl {

	private AIGraph graph;
	private String attrEdgeCapacity;
	private String attrEdgeFlow = "act_flow";
	
	//Wir speichern die Kante die benutzt wurde um nicht bei dem raussuchen von der kante zwischen start und zeil unnötig viele zugriffe zu machen
	private String attrNodeUsedEdge = "used_edge";
	
	//Delta = Kapazität der Kante - dem Fluss der fließt
	private String attrNodeDelta = "delta";
	
	private Stack<String> markedVertices = new Stack<String>();
	private List<String> inspectedVertices = new ArrayList<String>();
	
	private String initSource;
	private String initSink;
	
	public FordFulkersonImpl(AIGraph g, String attrEdgeNameCapacity, String initSource, String initSink) {
		this.graph = g;
		this.attrEdgeCapacity = attrEdgeNameCapacity;
		this.initSource = initSource;
		this.initSink = initSink;
		
		List<String> edges = this.graph.getEdges();
		//Alle Kannten den Flow auf 0 setzten
		for (String edge : edges) {
			this.graph.setValE(edge, this.attrEdgeFlow, 0);
		}
		
		//Startknoten an in die markierte einfügen
		this.markedVertices.push(this.initSource);
		this.graph.setValV(this.initSource, this.attrNodeDelta, Integer.MAX_VALUE);
		
		step2();
	}
	
	public void step2() {
		boolean doStep3 = false;
		
		while (!this.markedVertices.isEmpty()) {
			String v = this.markedVertices.pop();
			this.inspectedVertices.add(v);
			
			List<String> incidents = this.graph.getIncident(v);
			for (String edge : incidents) {
				if (this.graph.getSource(edge).equals(v)) {
					//Vorwärtskante
					String target = this.graph.getTarget(edge);
					if (!this.markedVertices.contains(target) && !this.inspectedVertices.contains(target)) {
						int delta = -1;
						int maxEdge = (this.graph.getValE(edge, this.attrEdgeCapacity) - this.graph.getValE(edge, this.attrEdgeFlow));
						int maxSource = this.graph.getValV(v, this.attrNodeDelta);
						
						delta = maxSource > maxEdge ? maxEdge : maxSource;
						
						if (delta > 0) {
							//Fluss > 0 also kann da noch was fließen
							this.markedVertices.add(target);
							this.graph.setStrV(target, this.attrNodeUsedEdge, edge);
						}
						
						this.graph.setValV(target, this.attrNodeDelta, delta);
						
						if (target.equals(this.initSink)) {
							doStep3 = true;
							
							this.markedVertices.clear();
							break;
						}	
					}
//				} else if (this.graph.getTarget(edge).equals(v)) {
//					//Rückwärtskante
//					
//					//wenn source unmarkiert 
//					String prev = this.graph.getSource(edge);
//					if (!this.markedVertices.contains(prev)) {
//						int flow = this.graph.getValV(prev, this.attrEdgeFlow);
//						if (flow > 0) {
//							this.markedVertices.add(prev);
//							//setzten entgegengesetzt---
//						}
//					}
				}
			}
		}
		
		if (doStep3) {
			step3();
		} else {
			step4();
		}
		
	}
	
	public void step3() {
		
		int delta = this.graph.getValV(this.initSink, this.attrNodeDelta);
		String usedEdge = this.graph.getStrV(this.initSink, this.attrNodeUsedEdge);
		
		this.step3Recursiv(usedEdge, delta);
		
//		this.doPrint();
		
		this.markedVertices.clear();
		this.markedVertices.add(this.initSource);
		this.inspectedVertices.clear();

		step2();
	}
	
	public void step3Recursiv(String usedEdge, int delta) {
		if (usedEdge != null && usedEdge.length() > 0) {
			int flowAtEdge = this.graph.getValE(usedEdge, this.attrEdgeFlow);
			
			this.graph.setValE(usedEdge, this.attrEdgeFlow, (flowAtEdge + delta));
			
			String newUsedEdge = this.graph.getStrV(this.graph.getSource(usedEdge), this.attrNodeUsedEdge);
			this.step3Recursiv(newUsedEdge, delta);
		}
	}
	
	public void step4() {
		this.doPrint();
	}
	
	
	public void doPrint() {
		List<String> nodes = this.graph.getVertexes();
		
		for (String node : nodes) {
			List<String> edges = this.graph.getIncident(node);
			for (String edge : edges) {
				if (this.graph.getSource(edge).equals(node)) {
					System.out.format("%4s (%3s,%3s) %4s", 
							node, 
							this.graph.getValE(edge, this.attrEdgeCapacity), 
							this.graph.getValE(edge, this.attrEdgeFlow),
							this.graph.getTarget(edge));
					System.out.println();
				}
			}
			System.out.println("---------------");
		}
		
		System.out.println("#######################");
	}

}
