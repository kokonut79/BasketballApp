package project.models;


import java.sql.*;

public class Team {
	private int id;
    private String name;
    private double budget;
    private int teamId;

    public Team(int id, String name, double budget , int teamId) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.teamId = teamId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getBuget() {
    	return budget;
    }
    public void setBudget(double budget) {
    	this.budget = budget;
    }

	public int getTeamIdTeams() {
		return teamId;
	}

	public void setTeamIdTeams(int teamId) {
		this.teamId = teamId;
	}

    public static ResultSet getAllTeams(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM team");
        
        return rs;
    }
    
    public static ResultSet searchTeams(Connection connection, String name) throws SQLException {
    	PreparedStatement  stmt = connection.prepareStatement("SELECT * FROM team WHERE name = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        
        return rs;
    }

    public static void insertTeam(Connection connection, String name, int budget) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO team (name, budget) VALUES (?, ?)");
        pstmt.setString(1, name);
        pstmt.setInt(2, budget);
        pstmt.executeUpdate();
    }

    public static void updateTeam(Connection connection,  String name, double budget,int id) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE team SET name = ?, budget = ? WHERE id = ?");
        pstmt.setString(1, name);
        pstmt.setDouble(2, budget);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
    }

    public static void deleteTeam(Connection connection, int id ) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM team WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public static void SearchPlayerAndTeam(Connection connection, String fName , String name  ) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("SELECT p.first_name,"
        		+ " t.name  FROM players INNER JOIN team ON p.teamId = t.id;");
        pstmt.setString(1, fName);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
    }
    
    public static int getTeamIdByName(Connection conn, String Name) throws SQLException {
        int teamId = 0;
        String query = "SELECT id FROM team WHERE name = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Name);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                teamId = rs.getInt("id");
            }
        }
        
        return teamId;
    }

	
}

