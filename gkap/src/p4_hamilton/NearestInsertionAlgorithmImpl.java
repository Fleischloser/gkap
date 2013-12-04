package p4_hamilton;

import graph_lib.AIGraph;

public class NearestInsertionAlgorithmImpl {

	private AIGraph graph;
	private String edgeDistAttrName;
	private String globalStartNode;
	
	
	public NearestInsertionAlgorithmImpl(AIGraph graph, String edgeDistAttrName, String startNode) {
		this.graph = graph;
		this.edgeDistAttrName = edgeDistAttrName;
		
		if (startNode == null || startNode.length() == 0) {
			startNode = this.graph.getVertexes().get(0);
		}

		this.globalStartNode = startNode;
		
		
	}
	

}
