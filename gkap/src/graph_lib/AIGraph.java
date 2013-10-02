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
	
	Map<String, Node> nodeMap = new HashMap<String, Node>();
	List<Edge> edgeList = new ArrayList<Edge>();
	
	public String addVertex(String name) {
		Node n = new Node(name);
		nodeMap.put(n.getId(), n);
		
		return n.getId();
	}
	
	public void deleteVertex(String id) {
		
		Node node = nodeMap.get(id);
		if (node != null) {
			//löschen
		}
	}

}
