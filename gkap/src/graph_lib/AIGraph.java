package graph_lib;

import graph_lib.entities.Edge;
import graph_lib.entities.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIGraph {
	public static AIGraph init() {
		return new AIGraph();
	}
	
	//Speichern der Nodes in einer Map damit man zu einer ID schnell das Objekt findet.
	private Map<String, Node> nodeMap = new HashMap<String, Node>();
	
	//Speichern der Edges in einer Map damit man zu einer ID schnell das Objekt findet.
	private Map<String, Edge> edgeMap = new HashMap<String, Edge>();
	
	public String addVertex(String name) {
		Node n = new Node(name);
		nodeMap.put(n.getId(), n);
		
		return n.getId();
	}
	
	public void deleteVertex(String id) {
		
		Node node = nodeMap.get(id);
		if (node != null) {
			for(Map.Entry<String, Edge> entrySet : this.edgeMap.entrySet()) {
				Edge edge = entrySet.getValue();
				
					if (	(edge.getNode1().equals(node))
							||
							(edge.getNode2().equals(node))
						) {
						//Kante löschen
						edgeMap.remove(edge.getId());
						}
			}
			nodeMap.remove(node.getId());
		}
	}
	
	
	/**
	 * Wenn idNode1 oder idNode2 nicht im Graphen vorhanden ist,
	 * wird null zurückgegeben und keine Kante erstellt.
	 * 
	 * @param idNode1
	 * @param idNode2
	 * @return ID des Edges oder null
	 */
	public String addEdgeU(String idNode1, String idNode2) {
		Node node1 = this.nodeMap.get(idNode1);
		Node node2 = this.nodeMap.get(idNode2);
		if (node1 != null && node2 != null) {
			Edge edge = new Edge(node1, node2, false);
			
			this.edgeMap.put(edge.getId(), edge);
			
			return edge.getId();
		}
		
		return null;
	}
	
	/**
	 * Wenn idStartNode oder idTargetNode nicht im Graphen vorhanden ist,
	 * wird null zurückgegeben und keine Kante erstellt.
	 * 
	 * @param idStartNode
	 * @param idTargetNode
	 * @return ID des Edges oder null
	 */
	public String addEdgeD(String idStartNode, String idTargetNode) {
		Node node1 = this.nodeMap.get(idStartNode);
		Node node2 = this.nodeMap.get(idTargetNode);
		if (node1 != null && node2 != null) {
			Edge edge = new Edge(node1, node2, true);
			
			this.edgeMap.put(edge.getId(), edge);
			
			return edge.getId();
		}
		
		return null;
	}
	

	public void deleteEdge(String idNode1, String idNode2) {
		Node node1 = this.nodeMap.get(idNode1);
		Node node2 = this.nodeMap.get(idNode2);
		if (node1 != null && node2 != null) {
			
			//Liste der Kanten die gelöscht werden sollen. Beim schleifen über eine Liste oder Map sollte man daraus nix löschen
			List<Edge> toDelEdges = new ArrayList<Edge>();
			
			//Schleifen über alle Knoten die wir haben
			for(Map.Entry<String, Edge> entrySet : this.edgeMap.entrySet()) {
				Edge edge = entrySet.getValue();
				
				if (edge.isDirected()) {
					//Gerichtete Kante also ist start zeil wichtig
					if (edge.getNode1().equals(node1) && edge.getNode2().equals(node2)) {
						//Kante löschen
						toDelEdges.add(edge);
					}
					
				} else {
					//Ungerichtet, also beide Kombinationen testen
					if (	(edge.getNode1().equals(node1) && edge.getNode2().equals(node2))
							||
							(edge.getNode1().equals(node2) && edge.getNode2().equals(node1))
						) {
						//Kante löschen
						toDelEdges.add(edge);
					}
				}
				
			}
			
			//löschen der Kanten
			for (Edge edge : toDelEdges) {
				this.edgeMap.remove(edge.getId());
			}
			
		}	
	}
}
