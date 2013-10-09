package graph_lib.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Node {

	private String id;
	private String name;
	private Map<String, Object> attributes;
	
	public Node(String name) {
		//this.id = UUID.randomUUID().toString();
		this.id = name;
		this.name = name;
		this.attributes = new HashMap<String, Object>();
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Object getAttr(String attr) {
		return attributes.get(attr);
	}
	
	public void setAttr(String key, Object val){
		attributes.put(key, val);
	}
	
	public List<String> getAttrKeys(){
		return new ArrayList<String>(attributes.keySet());
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
}
