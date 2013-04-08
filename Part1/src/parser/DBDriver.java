package parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DBDriver {
	private Connection con;
	private static long id;
	private String tableName;
	private LinkedHashMap<String, String> columns;
	
	public DBDriver(String url, String user, String password) throws SQLException {
		con = DriverManager.getConnection(url, user, password);
	}

	public void addEntry(DBObject dbobj) throws SQLException {
		String stm = generateInsertStatement();
		
		PreparedStatement pst = con.prepareStatement(stm);
		LinkedList<String> values = dbobj.getValues();
		
		for(String s: columns.keySet()) {
			if (columns.get(s).equalsIgnoreCase("bigint")) {
				pst.setLong(1, generateID());
			} if (columns.get(s).regionMatches(true, 0, "VARCHAR(", 0, "VARCHAR(".length())) {
				if(columns.get(s).endsWith("255)")) {
					pst.setString(2, values.pop());
				} else if (columns.get(s).endsWith("3000)")) {
					if(values.isEmpty())
						pst.setString(3, "");
					else {
						String val = values.pop();
						if(val.length() > 3000) {
							pst.setString(3, val.substring(0, 2999));
						} else
							pst.setString(3, val);
					}
				}
			}
		}
		
		pst.executeUpdate();
		
	}

	private String generateInsertStatement() {
		String stm = "INSERT INTO " + tableName + "(";
		boolean first = true;
		
		for(String s: columns.keySet()) {
			if(first) {
				stm = stm.concat(s);
				first = false;
			} else
				stm = stm.concat(", " + s);
		}
		
		stm = stm.concat(") VALUES(");
		first = true;
		
		for(String s: columns.keySet()) {
			if(first) {
				stm = stm.concat("?");
				first = false;
			} else
				stm = stm.concat(", ?");
		}
		
		stm = stm.concat(")");
		
		return stm;
	}

	public void setupTable(String tableName, LinkedHashMap<String, String> map, String primaryKey) throws SQLException {
		this.tableName = tableName;
		columns = map;
		
		String dropTable = "DROP TABLE IF EXISTS " + tableName;
		String createTable = "CREATE TABLE " + tableName + "(";
		
		boolean first = true;
		
		for(String s : columns.keySet()) {
			if(first) {
				createTable = createTable.concat(s + " " + columns.get(s));
				first = false;
			} else
				createTable = createTable.concat(", " + s + " " + columns.get(s));
		}
		
		createTable = createTable.concat(")");
		
		String alterTable = "ALTER TABLE " + tableName + " ADD PRIMARY KEY (" + primaryKey + ")";
		
		Statement st = con.createStatement();
		
		con.setAutoCommit(false);
		
		st.execute(dropTable);
		st.execute(createTable);
		st.execute(alterTable);
		
		con.commit();
		
		con.setAutoCommit(true);
	}
	
	private long generateID() {
		return id++;
	}
}
