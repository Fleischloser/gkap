package graph_importer;

import graph_lib.AIGraph;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Importer {
	
	/**
	 * 
	 * @param example (graph_01,graph_02,graph_03,graph_04,...graph_11)
	 * @return
	 */
	public AIGraph importExample(String example) {
		AIGraph ret = null;
		
		InputStream is = null;
		
		try {
			//die gewünschte Datei einlesen.
			is = this.getClass().getResourceAsStream("examples/"+example+".grapth");
			
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
			
			while ((line = reader.readLine()) != null) {
				String[] arr = line.split(",");
				
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (is != null) {
			ret = AIGraph.init();
			
		}
		return ret;
	}

}
