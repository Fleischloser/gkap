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
	
	private long countAccesses = 0L;
	
	/**
	 * Speichern der Nodes in einer Map damit man zu einer ID 
	 * schnell das Objekt findet.
	*/
	private Map<String, Node> nodeMap = new HashMap<String, Node>();
	
	/**
	 *Speichern der Edges in einer Map damit man zu einer ID 
	 *schnell das Objekt findet.
	 **/
	private Map<String, Edge> edgeMap = new HashMap<String, Edge>();
	
	/** 
	 * erweitern des Graphen um einen Node
	 * wenn bereits ein Node mit dem übergebenen Namen existiert, 
	 * wird kein neuer Node angelegt.
	 */
	public String addVertex(String name) {
		
		if (nodeMap.containsKey(name)) {
			return nodeMap.get(name).getId();
		}
		//andernfalss neuen Node erzeugen und in die NodeMap eintragen 
		Node n = new Node(name);
		nodeMap.put(n.getId(), n);
		
		return n.getId();
	}
	
	/**
	 * Löschen eines Node aus dem Graphen
	 * Wenn der Node existiert, werden alle Kanten aus der edgeMap 
	 * geholt und überprüft, ob der zu löschende Node Start- oder 
	 * Ziel dieser Kante ist. In diesem Fall, wird die Kante gelöscht
	 * @param ID des Nodes/Vertex?Knoten 
	 */
	public void deleteVertex(String id) {
		
		Node node = nodeMap.get(id);
		if (node != null) {
			for(Map.Entry<String, Edge> entrySet : this.edgeMap.entrySet()) {
				Edge edge = entrySet.getValue();
				/**
				 
				 */
				if ((edge.getNode1().equals(node)) 
						|| (edge.getNode2().equals(node))) {
					//Kante löschen
					edgeMap.remove(edge.getId());
				}
			}//zu guter letzt den Node löschen
			nodeMap.remove(node.getId());
		}
	}
	
	/**
	 * Wenn idNode1 oder idNode2 nicht im Graphen vorhanden ist,
	 * wird null zurückgegeben und keine Kante erstellt.
	 * 
	 * @param idNode1 Name des Start-Nodes
	 * @param idNode2 Name des Ziel-Nodes
	 * @return ID der Edge oder null
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
	 * @return ID der Edge oder null
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
			
			/**
			 * Liste der Kanten die gelöscht werden sollen. Beim schleifen über 
			 * eine Liste oder Map sollte man daraus nichts löschen
			 * */
			List<Edge> toDelEdges = new ArrayList<Edge>();
			
			//Schleifen über alle Knoten die wir haben
			for(Map.Entry<String, Edge> entrySet : this.edgeMap.entrySet()) {
				Edge edge = entrySet.getValue();
				
				if (edge.isDirected()) {
					//Gerichtete Kante also nur löschen, wenn Start und Ziel stimmen
					if (edge.getNode1().equals(node1) 
							&& edge.getNode2().equals(node2)) {
						//Kante löschen
						toDelEdges.add(edge);
					}
					
				} else {
					//Ungerichtet, also beide Kombinationen testen
					if ((edge.getNode1().equals(node1) 
							&& edge.getNode2().equals(node2))
						|| (edge.getNode1().equals(node2) 
							&& edge.getNode2().equals(node1))) {
						
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
	public boolean isEmpty(){
		//der Graph ist leer, wenn er keine Nodes enthält, also die nodeMad leer ist
		return nodeMap.isEmpty();
	}
	
	/**
	 *liefern des Start-Nodes einer Kante
	 *auch bei ungerichteten Kanten wird immer der erste Node geliefert 
	 */
	public String getSource(String edgeID){
		Edge e = edgeMap.get(edgeID);
		if (e != null){
			return e.getNode1().getId();
		}else{
			return null;
		}
	}
	
	/**
	 *liefern des Ziel-Nodes einer Kante
	 *auch bei ungerichteten Kanten wird immer der zweite Node geliefert 
	 */
	public String getTarget(String edgeID){
		Edge e = edgeMap.get(edgeID);
		if (e != null){
			return e.getNode2().getId();
		}else{
			return null;
		}
	}
	
	/**
	 * liefert eine Liste mit den IDs aller Kanten, die mit einem Node 
	 * verbunden sind
	 * */
	public List <String> getIncident(String nodeID){
		Node node = nodeMap.get(nodeID);
		List <String> result = new ArrayList<String>();
		if (node != null) {
			for(Map.Entry<String, Edge> entrySet : this.edgeMap.entrySet()) {
				Edge edge = entrySet.getValue();
				
				if ((edge.getNode1().equals(node)) || (edge.getNode2().equals(node))) {
					result.add(edge.getId());
				}
			}
		}
		return result;
	}
	
	/**
	 * liefert eine Liste mit den IDs aller Node, die mit einem Node 
	 * benachbart sind
	 * */
	public List <String> getAdjacent(String nodeID){
		Node node = nodeMap.get(nodeID);
		List <String> result = new ArrayList<String>();
		if (node != null) {
			for(Map.Entry<String, Edge> entrySet : this.edgeMap.entrySet()) {
				Edge edge = entrySet.getValue();
				
				if (edge.getNode1().equals(node)){
					result.add(edge.getNode2().getId());
				}else if(edge.getNode2().equals(node)){
					result.add(edge.getNode1().getId());
				}
			}
		}
		return result;
	}
	
	/**
	 * liefert Liste aller Nodes des Graphen
	 * */
	public List<String> getVertexes(){
		List <String> result = new ArrayList<String>(nodeMap.keySet());		
		return result;
	}
	
	/**
	 * liefert Liste aller Kanten des Graphen
	 * */
	public List<String> getEdges(){
		List <String> result = new ArrayList<String>(edgeMap.keySet());		
		return result;
	}
	
	/**
	 * liefert ein Attribut einer Kante als Integer
	 * im Fehlerfall maxint
	 * */
	public int getValE(String edgeID, String attr){
		Edge e = edgeMap.get(edgeID);
		if (e != null){
			Object val = e.getAttr(attr);
			if(val != null && val instanceof Integer){
				return (Integer)val;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	/**
	 * liefert ein Attribut einer Kante als String
	 * im Fehlerfall leerer String
	 * */
	public String getStrE(String edgeID, String attr){
		Edge e = edgeMap.get(edgeID);
		if (e != null){
			Object val = e.getAttr(attr);
			if(val != null && val instanceof String){
				return (String)val;
			}
		}
		return "";
	}
	
	/**
	 * liefert ein Attribut eines Node als Integer
	 * im Fehlerfall maxint
	 * */
	public int getValV(String nodeID, String attr){
		Node n = nodeMap.get(nodeID);
		if (n != null){
			Object val = n.getAttr(attr);
			if(val != null && val instanceof Integer){
				return (Integer)val;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	/**
	 * liefert ein Attribut eines Node als String
	 * im Fehlerfall leerer String
	 * */
	public String getStrV(String nodeID, String attr){
		Node n = nodeMap.get(nodeID);
		if (n!= null){
			Object val = n.getAttr(attr);
			if(val != null && val instanceof String){
				return (String)val;
			}
		}
		return "";
	}
	
	/**
	 * liefert Liste aller Attribute eines Node
	 * im Fehlerfall null
	 * */
	public List<String> getAttrV(String nodeID){
		Node n = nodeMap.get(nodeID);
		if (n!= null){	
			return n.getAttrKeys();	
		}
		return null;
	}
	
	/**
	 * liefert Liste aller Attribute einer Kante
	 * im Fehlerfall null
	 * */
	public List<String> getAttrE(String edgeID){
		Edge e = edgeMap.get(edgeID);
		if (e != null) {
			return e.getAttrKeys();
		}
		return null;
	}
	
	/**
	 * setzen eines Attributes einer Kante als Integer
	 * existierende Attribute werden dabei überschrieben
	 * */
	public void setValE(String edgeID, String attr, int val) {
		Edge e = this.edgeMap.get(edgeID);
		if (e != null) {
			e.setAttr(attr, val);
		}
	}
	
	/**
	 * setzen eines Attributes eines Node als Integer
	 * existierende Attribute werden dabei überschrieben
	 * */
	public void setValV(String nodeID, String attr, int val) {
		Node n = this.nodeMap.get(nodeID);
		if (n != null) {
			n.setAttr(attr, val);
		}
	}
	
	/**
	 * setzen eines Attributes einer Kante als String
	 * existierende Attribute werden dabei überschrieben
	 * */
	public void setStrE(String edgeID, String attr, String val) {
		Edge e = this.edgeMap.get(edgeID);
		if (e != null) {
			e.setAttr(attr, val);
		}
	}
	
	/**
	 * setzen eines Attributes eines Node als String
	 * existierende Attribute werden dabei überschrieben
	 * */
	public void setStrV(String nodeID, String attr, String val) {
		Node n = this.nodeMap.get(nodeID);
		if (n != null) {
			n.setAttr(attr, val);
		}
	}
	

	public void printGraphToConsole() {
		System.out.println(this+":");
		List<String> nodeList = this.getVertexes();
		for (String node : nodeList) {
			System.out.println("Node:"+node);
			
			List<String> edgeList = this.getIncident(node);
			for (String edge : edgeList) {
				Edge edgeObj = this.edgeMap.get(edge);
				String target = this.getTarget(edge);
				if (edgeObj.isDirected() && target.equals(node)) {
					continue;
				}
				
				if (target.equals(node)) {
					target = this.getSource(edge);
				}
				
				List<String> attrKeys = edgeObj.getAttrKeys();
				System.out.print("  ->Edge:"+target+" ");
				for (String attrName : attrKeys) {
					System.out.print("Attribute:"+attrName+":"+this.getValE(edge, attrName));
				}
				System.out.println("");
			}
		}
	}
	
	public long getCountGraphAccesses () {
		return this.countAccesses;
	}
}
