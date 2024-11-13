package za.ac.cput.voteserver;

import java.sql.SQLException;

/**
 *
 * @author Emkay
 */
public class VoteServer {

    public static void main(String[] args) throws SQLException {
        ServerGui gui = new ServerGui();
        gui.ServerGui();
        serverCon con = new serverCon();
        con.communicate();
        databaseCon db = new databaseCon();
        db.databaseCon();
    }
}
