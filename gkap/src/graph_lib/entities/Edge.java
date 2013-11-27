package graph_lib.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Edge {
	
	private String id;
	private Node node1;
	private Node node2;
	private Map<String, Object> attributes;
	private boolean directed;
	
	public Edge (Node node1, Node node2, boolean directed) {
		this.id = UUID.randomUUID().toString();
		this.node1 = node1;
		this.node2 = node2;
		this.directed = directed;
		this.attributes = new HashMap<String, Object>();
		
		//An die Kante attr anfügen ob gerichtet oder nicht
		this.setAttr("isDirectedEdge", directed ? 1 : 0);
	}

	public String getId() {
		return id;
	}

	public Node getNode1() {
		return node1;
	}

	public Node getNode2() {
		return node2;
	}

	public boolean isDirected() {
		return directed;
	}

	public Object getAttr(String attr) {
		return attributes.get(attr);
	}
	
	public void setAttr(String key, Object val){
		attributes.put(key, val);
	}
	
	public List<String> getAttrKeys(){
		return new ArrayList<String>(attributes.keySet());
	}
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Node) {
			Node n = (Node)obj;
			if (n.getId() != null && this.getId() != null 
					&& n.getId().equals(this.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
}
