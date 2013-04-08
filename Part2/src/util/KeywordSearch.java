package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;

public class KeywordSearch {
	public static void main (String [] args) {
		
		if(args.length < 1) {
			System.out.println("Too few arguments");
			System.exit(1);
		} else if (args.length > 1) {
			System.out.println("Too many arguments");
			System.exit(1);
		}
		
		String searchTerm = args[0];
		
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
        
        String newtable = "newtable.";
        
        String tableName = null;
        String primaryKey = null;
        
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        
        for(Object s : props.keySet()) {
        	String ss = (String) s;
        	if (ss.startsWith(newtable)) {
        		if (ss.regionMatches(newtable.length(), "name", 0, "name".length())) {
        			tableName = props.getProperty(ss);
        		} else if(ss.regionMatches(newtable.length(), "primarykey", 0, "primarykey".length())) {
        			primaryKey = props.getProperty(ss);
        		} else if(ss.regionMatches(newtable.length(), "column", 0, "column".length())) {
        			map.put(props.getProperty(ss).split("_")[0], props.getProperty(ss).split("_")[1]);
        		}
        			
        	}
        }
        
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        
        String query = generateQuery(searchTerm);
        Vector<QueryResult> qrArray = new Vector<QueryResult>();
        QueryResult qr;
        int i = 0;
        try {
        	
			con = DriverManager.getConnection(url, user, passwd);
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				qr = new QueryResult(rs.getString(1), rs.getString(2), searchTerm);
				qrArray.add(qr);
				i++;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(i);
        QueryResult [] sorted = qrArray.toArray(new QueryResult[qrArray.size()]);
        quicksort(sorted, 0, sorted.length-1);
        printResult(sorted);
	}
	
	private static void printResult(QueryResult [] array) {
		String format = "%-21s    %-13s";
		System.out.println(String.format(format, "Name", "Keywords#"));
		System.out.println(String.format(format, "------------------", "-------------"));
		format = "%-21s    %-13d";
		for(int i = array.length-1; i >= 0; i--) {
			System.out.println(String.format(format, array[i].getName().substring(0, 18), array[i].getRank()));
		}
	}

	private static void quicksort(QueryResult[] array, int left, int right) {
		int index = partition(array, left, right);
		if(left < index - 1)
			quicksort(array, left, index - 1);
		if(index < right)
			quicksort(array, index, right);
	}

	private static int partition(QueryResult[] array, int left, int right) {
		int i = left;
		int j = right;
		QueryResult tmp;
		QueryResult pivot = array[(left + right)/2];
		
		while(i <= j) {
				while(array[i].getRank() < pivot.getRank())
					i++;
				while(array[j].getRank() > pivot.getRank())
					j--;
				if(i <= j) {
					tmp = array[i];
					array[i] =  array[j];
					array[j] = tmp;
					i++;
					j--;
				}
		}
		
		return i;
	}

	private static String generateQuery(String searchTerm) {
		return "SELECT name, description FROM item WHERE to_tsvector('english', description) @@ to_tsquery('english', \'" + searchTerm + "\')";
	}
}
