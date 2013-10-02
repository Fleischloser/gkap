package graph_lib.entities;

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

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Node) {
			Node n = (Node)obj;
			if (n.getId() != null && this.getId() != null && n.getId().equals(this.getId())) {
				return true;
			}
		}
		
		return false;
	}

}
