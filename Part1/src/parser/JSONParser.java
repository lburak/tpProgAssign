package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JSONParser {
	
	DBDriver dbdriver;
	JsonReader reader;
	LinkedList<String> fields;
	
	public JSONParser(String filename) throws FileNotFoundException {
		reader = new JsonReader(new FileReader(filename));
		fields = new LinkedList<String>();
		reader.setLenient(true);
	}
	
	public void startDB(String url, String user, String password) throws SQLException {
		dbdriver = new DBDriver(url, user, password);
	}
	
	public void setupTable(String tableName, LinkedHashMap<String, String> map,  String primaryKey) throws SQLException {
		dbdriver.setupTable(tableName, map, primaryKey);
	}
	
	public void parseAndClose() throws IOException, SQLException {
		JsonToken jt = reader.peek();
		
		
		switch(jt) {
			case BEGIN_OBJECT: 	consumeObject();
								break;
			
			case END_DOCUMENT:	return;
			
			case BEGIN_ARRAY:
			case END_OBJECT:
			case END_ARRAY:
			case NUMBER:
			case STRING:
			case NAME:
			case NULL:
			case BOOLEAN:
			default:	break;
		}
		
		jt = reader.peek();
		
		if(jt == JsonToken.END_DOCUMENT) {
			reader.close();
			return;
		} else
			parseAndClose();
	}
	
	private void consumeObject() throws IOException, SQLException {
		reader.beginObject();
		DBObject dbobj = new DBObject();
		while(reader.hasNext()) {
			String fieldName = reader.nextName();
			if(fields.contains(fieldName)) {
				String value = reader.nextString();
				dbobj.addValue(value);
			} else
				reader.skipValue();
		}
		
		reader.endObject();
		dbdriver.addEntry(dbobj);
	}
	
	public void setFields(LinkedList<String> fields) {
		this.fields = fields;
	}
}
