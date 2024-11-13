package za.ac.cput.voteserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Emkay
 */

public class databaseCon {

    private String dbURL = "jdbc:derby://localhost:1527/Server;create=true";
    private String username = "username";
    private String password = "password";
    private Connection con;

    
public Connection derbyConnection() {
    try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection(dbURL, username, password);
        System.out.println("Database connected successfully!");
        return con;
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.out.println("Driver not found: " + e.getMessage());
    }
    return null; 
}



    public void databaseCon() {
        try {
            System.out.println("Waiting for connection...");
            con = derbyConnection();
            if (con != null) {
                System.out.println("Connected to the database!!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public boolean carExists(String carName) {
        String query = "SELECT COUNT(*) FROM CARS WHERE CARNAME = ?";
        try (PreparedStatement state = con.prepareStatement(query)) {
            state.setString(1, carName);
            try (ResultSet rs = state.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, the car exists
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error checking if car exists: " + ex.getMessage());
        }
        return false;
    }

    public void update(String carName) {
    String update = "UPDATE CARS SET VOTENUM = VOTENUM + 1 WHERE CARNAME = ?";
    try (PreparedStatement state = con.prepareStatement(update)) {
        state.setString(1, carName);
        state.executeUpdate();
        JOptionPane.showMessageDialog(null, "Successfully voted for: " + carName);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Update Failed: " + e.getMessage(), "WARNING", 0);
    }
}

public String addCar(String carName, int voteNum) {
    String add = "INSERT INTO CARS (CARNAME, VOTENUM) VALUES(?, ?)";
    try (PreparedStatement state = con.prepareStatement(add)) {
        state.setString(1, carName);
        state.setInt(2, voteNum);
        int result = state.executeUpdate();
        if (result > 0) {
            return carName; 
        }
    } catch (SQLException e) {
        System.out.println("Error adding car: " + e.getMessage());
    }
    return null;
}



    public List<String[]> getVotes() {
        String query = "SELECT * FROM CARS";
        List<String[]> voteData = new ArrayList<>();
        try (PreparedStatement state = con.prepareStatement(query);
             ResultSet rs = state.executeQuery()) {
            while (rs.next()) {
                String[] carVote = new String[2];
                carVote[0] = rs.getString("CARNAME");
                carVote[1] = Integer.toString(rs.getInt("VOTENUM"));
                voteData.add(carVote);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching votes: " + e.getMessage(), "WARNING", 0);
        }
        return voteData;
    }


    public void shutdown() {
        closeConnection();
    }
}
