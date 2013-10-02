package tests;

import graph_lib.AIGraph;

public class AIGraphTest {
	
	public static void main(String[] args) {
		AIGraph g = AIGraph.init();
		
		String id = g.addVertex("Name1");
		
		g.deleteVertex(id);
	}

}
