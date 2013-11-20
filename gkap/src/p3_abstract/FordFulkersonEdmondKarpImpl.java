package p3_abstract;

import graph_lib.AIGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class FordFulkersonEdmondKarpImpl {

	private AIGraph graph;
	private String attrEdgeCapacity;
	private String attrEdgeFlow = "act_flow";
	
	//Wir speichern die Kante die benutzt wurde um nicht bei dem raussuchen von der kante zwischen start und zeil unnötig viele zugriffe zu machen
	private String attrNodeUsedEdge = "used_edge";
	
	//Delta = Kapazität der Kante - dem Fluss der fließt
	private String attrNodeDelta = "delta";
	
	private IStackQueue<String> markedVertices = null;
	private List<String> inspectedVertices = new ArrayList<String>();
	
	private String initSource;
	private String initSink;

	private int backtracks = 0;
	public FordFulkersonEdmondKarpImpl(AIGraph g, String attrEdgeNameCapacity, String initSource, String initSink, boolean useFord) {
		this.graph = g;
		this.attrEdgeCapacity = attrEdgeNameCapacity;
		this.initSource = initSource;
		this.initSink = initSink;
		
		List<String> edges = this.graph.getEdges();
		//Alle Kannten den Flow auf 0 setzten
		for (String edge : edges) {
			this.graph.setValE(edge, this.attrEdgeFlow, 0);
		}
		
		if (useFord) {
			markedVertices = new StackImpl<String>();
		} else {
			markedVertices = new QueueImpl<String>();
		}
		
		//Startknoten an in die markierte einfügen
		this.markedVertices.add(this.initSource);
		this.graph.setValV(this.initSource, this.attrNodeDelta, Integer.MAX_VALUE);
		
		step2();
	}
	
	private void step2() {
		boolean doStep3 = false;
		
		while (!this.markedVertices.isEmpty() && !doStep3) {
			
			String v = this.markedVertices.get();
			this.inspectedVertices.add(v);
			
			List<String> incidents = this.graph.getIncident(v);
			for (String edge : incidents) {
				if (this.graph.getSource(edge).equals(v)) {
					//Vorwärtskante
					String target = this.graph.getTarget(edge);
					if (!this.markedVertices.contains(target) && !this.inspectedVertices.contains(target) && !doStep3) {
						int delta = -1;
						int maxEdge = (this.graph.getValE(edge, this.attrEdgeCapacity) - this.graph.getValE(edge, this.attrEdgeFlow));
						int maxSource = this.graph.getValV(v, this.attrNodeDelta);
						
						delta = (maxSource > maxEdge) ? maxEdge : maxSource;
						
						if (delta > 0) {
							//Fluss > 0 also kann da noch was fließen
							this.markedVertices.add(target);
							this.graph.setStrV(target, this.attrNodeUsedEdge, edge);

							this.graph.setValV(target, this.attrNodeDelta, delta);
							
							if (target.equals(this.initSink)) {
								doStep3 = true;
								break;
							}	
						}
						
					}
				} else if (this.graph.getTarget(edge).equals(v)) {
					//Rückwärtskante
					
					//wenn source unmarkiert 
					String prev = this.graph.getSource(edge);
					if (!this.markedVertices.contains(prev) && !this.inspectedVertices.contains(prev)) {
						int flow = this.graph.getValE(edge, this.attrEdgeFlow);
						if (flow > 0) {
							this.markedVertices.add(prev);
							//setzten entgegengesetzt---
							
							this.graph.setStrV(prev, this.attrNodeUsedEdge, edge);
							int maxDeltaSource = this.graph.getValV(v, this.attrNodeDelta);
							int delta = (maxDeltaSource > flow) ? flow : maxDeltaSource;
							this.graph.setValV(prev, this.attrNodeDelta, delta);
							
						}
					}
				}
			}
		}
		
		if (doStep3) {
			step3();
		} else {
			step4();
		}
		
	}
	
	private void step3() {
		
		int delta = this.graph.getValV(this.initSink, this.attrNodeDelta);
		String usedEdge = this.graph.getStrV(this.initSink, this.attrNodeUsedEdge);
		
		System.out.println(this.graph.getSource(usedEdge)+"::"+delta);
		this.step3Recursiv(usedEdge, delta, this.initSink);

		//Alles zurücksetzten
		this.clearDataForNextTurn();
		
		//Source hinzufügen
		this.markedVertices.add(this.initSource);
		this.graph.setValV(this.initSource, this.attrNodeDelta, Integer.MAX_VALUE);
		step2();
	}
	
	private void clearDataForNextTurn() {
		
		//Markierte löschen
		this.markedVertices.clear();
		
		//Inspzierte löschen
		this.inspectedVertices.clear();
		
		//Zurücksetzten der Used Edge (Eigentlich nicht nötig aber damit sauberer)
		List<String> nodes = this.graph.getVertexes();
		for (String node : nodes) {
			this.graph.setStrV(node, this.attrNodeUsedEdge, "");
			this.graph.setValV(node, this.attrNodeDelta, 0);
		}
	}
	
	private void step3Recursiv(String usedEdge, int delta, String target) {
		if (usedEdge != null && usedEdge.length() > 0 && delta > 0) {
			int flowAtEdge = this.graph.getValE(usedEdge, this.attrEdgeFlow);
			
			//Bin ich Source oder Target
			String source = this.graph.getSource(usedEdge);
			if (!source.equals(target)) {
				//target bin das Ziel also normale Kante
				this.graph.setValE(usedEdge, this.attrEdgeFlow, (flowAtEdge + delta));
				
				String newUsedEdge = this.graph.getStrV(source, this.attrNodeUsedEdge);
				this.step3Recursiv(newUsedEdge, delta, source);
			} else {
				//target bin der Source also entgegengesetzt... backtracking...
				source = this.graph.getTarget(usedEdge);
				
				System.out.println("Rückwärtskante von " + target + " nach " + source + ".");
				backtracks++;
				
				this.graph.setValE(usedEdge, this.attrEdgeFlow, (flowAtEdge - delta));
				
				String newUsedEdge = this.graph.getStrV(source, this.attrNodeUsedEdge);
				this.step3Recursiv(newUsedEdge, delta, source);
			}
		}
	}
	
	private void step4() {
		System.out.println();
		System.out.println("######################################");
		System.out.println("Ergebnis:");
		
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

		System.out.println("Es wurde über " + ((backtracks == 0) ? "keine": backtracks) + " Rückswärtskante" + ((backtracks == 1)?"":"n") + " gegangen.");
		System.out.println("#######################");
	}

}
