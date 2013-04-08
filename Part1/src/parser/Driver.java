package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Properties;

public class Driver {
	public static void main(String [] args) {

        Properties props = new Properties();
        FileInputStream in = null;
        
        try {
            in = new FileInputStream("C:\\tpProgAssign\\Part1\\src\\parser\\jsondatabase.properties");
            props.load(in);

        } catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
            
            try {
                 if (in != null) {
                     in.close();
                 }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        
        
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.password");
        String jsonFileName = props.getProperty("json.file");
        
        String newtable = "newtable.";
        String json = "json.";
        String tableName = null;
        String primaryKey = null;
        
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        LinkedList<String> fields = new LinkedList<String>();
        
        for(Object s : props.keySet()) {
        	String ss = (String) s;
        	if (ss.startsWith(json)) {
        		if(ss.regionMatches(json.length(), "field", 0, "field".length())) {
        			fields.add(props.getProperty(ss));
        		}	
        	} else if (ss.startsWith(newtable)) {
        		if (ss.regionMatches(newtable.length(), "name", 0, "name".length())) {
        			tableName = props.getProperty(ss);
        		} else if(ss.regionMatches(newtable.length(), "primarykey", 0, "primarykey".length())) {
        			primaryKey = props.getProperty(ss);
        		} else if(ss.regionMatches(newtable.length(), "column", 0, "column".length())) {
        			map.put(props.getProperty(ss).split("_")[0], props.getProperty(ss).split("_")[1]);
        		}
        			
        	}
        }
        
        map = reverse(map);
        
        JSONParser jparser;
        
        try {
        	jparser = new JSONParser(jsonFileName);
        	jparser.setFields(fields);
        	jparser.startDB(url, user, passwd);
        	jparser.setupTable(tableName, map, primaryKey);
        	jparser.parseAndClose();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static LinkedHashMap<String, String> reverse(LinkedHashMap<String, String> map) {
		if(map.size() <= 1)
			return map;
		
		String first = map.keySet().toArray(new String[0])[0];
		String value = map.remove(first);
		
		LinkedHashMap<String, String> reversed = reverse(map);
		
		reversed.put(first, value);
		
		return reversed;
	}
}
