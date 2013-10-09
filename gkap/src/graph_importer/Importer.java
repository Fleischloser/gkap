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
			while ((line = reader.readLine()) != null) {
				
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
