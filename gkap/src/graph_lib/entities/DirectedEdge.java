package graph_lib.entities;

import java.util.UUID;

public class DirectedEdge extends Edge {

	private Node start;
	private Node target;
	
	public DirectedEdge(Node start, Node target) {
		this.start = start;
		this.target = target;
	}

	public Node getStart() {
		return start;
	}

	public Node getTarget() {
		return target;
	}
	
	
}
