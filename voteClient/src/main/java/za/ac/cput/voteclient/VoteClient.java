package za.ac.cput.voteclient;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Emkay
 */
public class VoteClient {

    public static void main(String[] args) throws Exception {
        try {
            ClientGui gui = new ClientGui();
            gui.Buttons(); 
            gui.pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
