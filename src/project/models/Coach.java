package project.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Coach {
	private int id ; 
	private String fName;
    private String lName;
    private int teamId;
	    
    public Coach(int id , String fName , String lName, int teamId) {
    	this.setId(id); 
    	this.setfName(fName);
   	 	this.setlName(lName);
        this.setTeamId(teamId);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
    public static ResultSet getAllCoaches(Connection conn ) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM coaches "); 	
	    ResultSet rs = pstmt.executeQuery();
	        
	    return rs;
	 }
	 
	  public static void insertCoaches(Connection conn, String fName, String lName, int teamId) throws SQLException {
	        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO coaches (first_name,  last_name, teamId) VALUES (?, ?, ?)");
	        pstmt.setString(1, fName);
	        pstmt.setString(2, lName);
	        pstmt.setInt(3, teamId);
	        pstmt.executeUpdate();
	    }
	    
	    public static void updateCoaches(Connection conn, int id, String fName, String lName, int teamId) throws SQLException {
	        PreparedStatement pstmt = conn.prepareStatement("UPDATE coaches SET first_name = ?, last_name = ?, teamId = ? WHERE id = ?");
	        pstmt.setString(1, fName);
	        pstmt.setString(2, lName);
	        pstmt.setInt(3, teamId);
	        pstmt.setInt(4, id);
	        pstmt.executeUpdate();
	    }
	    
	   

		public static ResultSet searchCoaches(Connection conn, String fName) throws SQLException{
			PreparedStatement  stmt = conn.prepareStatement("SELECT * FROM coaches WHERE first_name = ?");
	        stmt.setString(1, fName);
	        ResultSet rs = stmt.executeQuery();
	        
	        return rs;
		}

		public static void deleteCoaches(Connection conn, int id) throws SQLException {
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM coaches WHERE id = ?");
	        pstmt.setInt(1, id);
	        pstmt.executeUpdate();
			
		}
}
