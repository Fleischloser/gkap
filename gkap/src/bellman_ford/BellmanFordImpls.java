package bellman_ford;

import java.util.List;

import graph_importer.Importer;
import graph_lib.AIGraph;


/**
 * http://www-m9.ma.tum.de/material/spp/Bellman-Ford-Algorithmus.html
 * 
 * @author schulz_a
 *
 */
public class BellmanFordImpls {

	public static void main(String[] args) {
		
		AIGraph graph = AIGraph.init();
		String[] attrNames = {"distance"};
		
		graph = Importer.importExample(graph, "graph_03", attrNames);
		graph.printGraphToConsole();
		System.out.println("##########");
		System.out.println("##########");
		System.out.println("##########");
		
		String attrDistanceName = "distance";
		String attrUsedEdge = "usedEdge";
		
		List<String> nodeList = graph.getVertexes();
		if (nodeList.size() > 0) {
			//Init des Graphen
			String startNode = nodeList.get(0);
			graph.setValV(startNode, attrDistanceName, 0);
			
			for (int i = 1; i < nodeList.size(); i++) {
				graph.setValV(nodeList.get(i), attrDistanceName, Integer.MAX_VALUE);
			}

			List<String> edgeList = graph.getEdges();
			for (int i = 0; i < nodeList.size(); i++) {
				for (String edgeId : edgeList) {
					String sourceId = graph.getSource(edgeId);
					String targetId = graph.getTarget(edgeId);
					int valSource = graph.getValV(sourceId, attrDistanceName);
					int valTarget = graph.getValV(targetId, attrDistanceName);
					int valEdge = graph.getValE(edgeId, attrNames[0]);
					
					if (valSource < Integer.MAX_VALUE) {
						int dist = valSource + valEdge;
						if (valTarget > dist) {
							graph.setValV(targetId, attrDistanceName, dist);
							graph.setStrV(targetId, attrUsedEdge, edgeId);
						}
					}
					
				}
			}
			
			boolean negCricle = false;
			for (String edgeId : edgeList) {
				String sourceId = graph.getSource(edgeId);
				String targetId = graph.getTarget(edgeId);
				int valSource = graph.getValV(sourceId, attrDistanceName);
				int valTarget = graph.getValV(targetId, attrDistanceName);
				int valEdge = graph.getValE(edgeId, attrNames[0]);
				
				int dist = valSource + valEdge;
				if (valTarget > dist) {
					negCricle = true;
					break;
				}	
			}
			
			for (String nodeId : nodeList) {
				String usedEdge = graph.getStrV(nodeId, attrUsedEdge);
				String sourceNode = graph.getSource(usedEdge);
				System.out.println(nodeId+":"+graph.getValV(nodeId, attrDistanceName)+" -> "+ sourceNode);
			}
		}
		
	}
	
	
}
