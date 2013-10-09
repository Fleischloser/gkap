package graph_importer;

import graph_lib.AIGraph;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Importer {
	
	/**
	 * 
	 * @param graph 
	 * @param example (graph_01,graph_02,graph_03,graph_04,...graph_11)
	 * @param arrAttrNames String[]
	 * @return
	 */
	public static AIGraph importExample(AIGraph graph, String example, String[] arrAttrNames) {
		AIGraph ret = null;
		
		InputStream is = null;
		
		try {
			//die gewünschte Datei einlesen.
			is = Importer.class.getResourceAsStream("examples/"+example+".graph");
			
			//Buffered Reader um zeilenweise zu lesen
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line = null;
			line = reader.readLine();
			boolean directed = false;
			if (line != null) {
				//die erste Zeile enthält immer die Info ob der Graph gerichtet ist oder nicht
				if (line.equals("﻿#ungerichtet")) {
					directed = false;
				} else if (line.equals("﻿#gerichtet")) {
					directed = true;
				}
			}

			ret = graph;
			while ((line = reader.readLine()) != null) {
				String[] arr = line.split(",");

				String node1 = ret.addVertex(arr[0]);
				String node2 = ret.addVertex(arr[1]);
				
				for (int i = 2; i < arr.length; i++) {
					String attrName = "attr"+(i-1);
					String val = arr[i];
					
					if (arrAttrNames.length > (i-2)) {
						attrName = arrAttrNames[i-2];
					}
					
					String idEdge = null;
					if (directed) {
						idEdge = ret.addEdgeD(node1, node2);
					} else {
						idEdge = ret.addEdgeU(node1, node2);
					}
					
					try {
						int intVal = Integer.parseInt(val);
						ret.setValE(idEdge, attrName, intVal);
					} catch (Exception e) {
						ret.setStrE(idEdge, attrName, val);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
