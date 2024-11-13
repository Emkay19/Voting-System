package za.ac.cput.voteclient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientCon {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static JTable table;

    public ClientCon(JTable cable) {
        if (cable == null) {
            throw new IllegalArgumentException("JTable cannot be null");
        }
        this.table = cable;
        try {
            System.out.println("Waiting for connection...");
            socket = new Socket("localhost", 6000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection to server failed: " + e.getMessage(), "WARNING!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void sendVote() {
        String combo = (String) ClientGui.comboBox.getSelectedItem();  // Get the selected item from the combo box

        try {
            if (socket != null && !socket.isClosed()) {
            
                if (out != null && in != null) {
                    out.writeObject(combo);  // Send the selected vote to the server
                    out.flush();  

                    String serverResponse = (String) in.readObject();  // Wait for the server's response
                    JOptionPane.showMessageDialog(null, "Server response: " + serverResponse);  // Display the response
                } else {
                    JOptionPane.showMessageDialog(null, "Streams are not initialized.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Socket is closed or null.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong: " + e.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void fetchVotes() {
        if (socket != null && !socket.isClosed()) {
            try {
                // Send request to fetch votes
                out.writeObject("fetchVotes");
                out.flush();

                // Receive vote data
                List<String[]> voteData = (List<String[]>) in.readObject();  // Reuse the input stream

                // Update the table with the fetched data
                if (voteData != null && !voteData.isEmpty()) {
                    updateTable(voteData);
                } else {
                    JOptionPane.showMessageDialog(null, "No votes available", "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error fetching votes: " + e.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Socket is not connected", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void closeConnection() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTable(List<String[]> voteData) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows

        for (String[] carVote : voteData) {
            model.addRow(carVote);  // Add new data to the table
        }
    }
}
