package ford_fulkerson;

import graph_lib.AIGraph;

import java.util.List;
import java.util.Queue;



public class FordFulkersonImpl {

	private AIGraph graph;
	private String attrEdgeCapacity;
	private String attrEdgeFlow = "act_flow";
	
	//Wir speichern die Kante die benutzt wurde um nicht bei dem raussuchen von der kante zwischen start und zeil unnötig viele zugriffe zu machen
	private String attrNodeUsedEdge = "used_edge";
	
	//Delta = Kapazität der Kante - dem Fluss der fließt
	private String attrNodeDelta = "delta";
	
	private Queue<String> markedVertices;
	private List<String> insectedVertices;
	
	public FordFulkersonImpl(AIGraph g, String attrEdgeNameCapacity, String source, String sink) {
		this.graph = g;
		this.attrEdgeCapacity = attrEdgeNameCapacity;
		
		List<String> edges = this.graph.getEdges();
		//Alle Kannten den Flow auf 0 setzten
		for (String edge : edges) {
			this.graph.setValE(edge, this.attrEdgeFlow, 0);
		}
		
		//Startknoten an in die markierte einfügen
		this.markedVertices.add(source);
		this.graph.setValV(source, this.attrNodeDelta, Integer.MAX_VALUE);
		
		while (!this.markedVertices.isEmpty()) {
			String v = this.markedVertices.poll();
			this.insectedVertices.add(v);
			
			List<String> incidents = this.graph.getIncident(v);
			for (String edge : incidents) {
				if (this.graph.getSource(edge).equals(v)) {
					//Wir sind der source
					String target = this.graph.getTarget(edge);
					this.markedVertices.add(target);
					
					this.graph.setStrV(target, this.attrNodeUsedEdge, edge);
					
					int delta = -1;
					int maxEdge = (this.graph.getValE(edge, this.attrEdgeCapacity) - this.graph.getValE(edge, this.attrEdgeFlow));
					int maxSource = this.graph.getValV(source, this.attrNodeDelta);
					
					delta = maxSource > maxEdge ? maxEdge : maxSource;
					
					this.graph.setValV(target, this.attrNodeDelta, delta);
				}
			}
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
