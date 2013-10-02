package graph_lib.entities;

import java.util.UUID;

public class Node {

	private String id;
	private String name;
	
	public Node(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Node) {
				Node n = (Node)obj;
				if (n.getId() != null && this.getId() != null && n.getId().equals(this.getId())) {
					return true;
				}
			} else if (obj instanceof String) {
				String s = (String)obj;
				if (this.getId() != null && s.equals(this.getId())) {
					return true;
				}
			}	
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
