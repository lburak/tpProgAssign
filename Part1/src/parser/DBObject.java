package parser;

import java.util.LinkedList;

public class DBObject {
	LinkedList<String> values;
	
	public DBObject() {
		values = new LinkedList<String>();
	}
	
	public void addValue(String value) {
		values.add(value);
	}
	
	public LinkedList<String> getValues() {
		return values;
	}
}
