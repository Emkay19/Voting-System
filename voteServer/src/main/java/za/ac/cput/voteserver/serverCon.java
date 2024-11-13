package za.ac.cput.voteserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Emkay
 */
public class serverCon {

    private ServerSocket server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private databaseCon db = new databaseCon();

    public serverCon()throws SQLException {
        try {
            ServerGui.area.append("Waiting for connection...\n");
            server = new ServerSocket(6000); // same port as client 
            socket = server.accept();
            ServerGui.area.append("Connected\n");
            db.databaseCon();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection with client failed", "WARNING!", 0);
        }
    }

    public void communicate() throws SQLException {
        try {
            // Ensure streams are initialized once at the beginning of the connection
            if (out == null) {
                out = new ObjectOutputStream(socket.getOutputStream());
            }
            if (in == null) {
                in = new ObjectInputStream(socket.getInputStream());
            }

            // Keep listening for client requests in a loop
            while (true) {
                String request = (String) in.readObject(); 
                if (request != null) {
                    if ("fetchVotes".equals(request)) {
                        List<String[]> voteData = db.getVotes(); 
                        out.writeObject(voteData); 
                        out.flush(); 
                    } else {
                        if (db.carExists(request)) {
                            db.update(request);  
                        } else {
                            db.addCar(request, 1);
                        }
                        out.writeObject("Vote received for: " + request); 
                        out.flush();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Communication Failed: " + e.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
        } finally {
            // Only close the socket if the loop is broken or an unrecoverable error occurs
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close(); 
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error closing socket: " + e.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}
