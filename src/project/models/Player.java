package project.models;

import java.sql.*;

public class Player {
	private int id;
    private String fName;
    private String lName;
    private int number;
    private int teamId;
    
   public Player(int id , String fName  , String lName , int number , int teamId) {
	 this.id = id;
	 this.fName= fName;
	 this.lName = lName;
	 this.number = number;
	 this.teamId= teamId;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
    public static ResultSet getAllPlayers(Connection conn ) throws SQLException {
    	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM players ");
    	
        ResultSet rs = pstmt.executeQuery();
        
        return rs;
    }
    
    public static ResultSet searchPlayers(Connection connection, String fName) throws SQLException {
    	PreparedStatement  stmt = connection.prepareStatement("SELECT * FROM players WHERE first_name = ?");
        stmt.setString(1, fName);
        ResultSet rs = stmt.executeQuery();
        
        return rs;
    }
    
    public static void insertPlayers(Connection conn, String fName, String lName, int number, int teamId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO players (first_name,  last_name, `number`, teamId) VALUES (?, ?, ?, ?)");
        pstmt.setString(1, fName);
        pstmt.setString(2, lName);
        pstmt.setInt(3, number);
        pstmt.setInt(4, teamId);
        pstmt.executeUpdate();
    }
    
    public static void updatePlayers(Connection conn, int id, String fName, String lName, int number, int teamId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("UPDATE players SET first_name = ?, last_name = ?, `number` = ?, teamId = ? WHERE id = ?");
        pstmt.setString(1, fName);
        pstmt.setString(2, lName);
        pstmt.setInt(3, number);
        pstmt.setInt(4, teamId);
        pstmt.setInt(5, id);
        pstmt.executeUpdate();
    }
    
    public static void deletePlayers(Connection conn, int id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM players WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }



}
