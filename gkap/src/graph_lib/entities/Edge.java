package graph_lib.entities;

import java.util.Map;

public class Edge {
	
	private String id;
	private Map<String, Object> attributes;
	

	public String getId() {
		return id;
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
