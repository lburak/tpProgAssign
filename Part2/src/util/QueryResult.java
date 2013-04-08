package util;

public class QueryResult {
	private String name;
	private String description;
	private String keyword;
	private int rank;
	
	public QueryResult(String nm, String dscrptn, String kwrd) {
		name = nm;
		description = dscrptn;
		keyword = kwrd;
		calculateRank();
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public int getRank() {
		return rank;
	}
	
	private void calculateRank() {
		rank = description.split(keyword).length;
	}

}
