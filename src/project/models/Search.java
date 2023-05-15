package project.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Search {
	private String pName; 
	private String tName;
	
	public Search(String pName , String tName) {
		this.pName = pName;
		this.tName = tName;
	}
	
	  public static ResultSet getPlayerAndTeamNames(Connection conn, String pName, String tName) throws SQLException {
		        String query = "SELECT p.first_name,p.last_name, t.name " +
		                "FROM players p " +
		                "LEFT JOIN team t ON p.teamId = t.id " +
		                "WHERE p.first_name = ? AND t.name = ?";
	        var statement = conn.prepareStatement(query);
	        statement.setString(1, pName);
	        statement.setString(2, tName);
	       var  resultSet = statement.executeQuery();
	        return resultSet;
	    }
	  
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
}
