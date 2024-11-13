package za.ac.cput.voteserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Emkay
 */
public class ServerGui extends JFrame{

    protected static JTextArea area;
    private JLabel text;
    private JPanel panel;

    public void ServerGui() {

        setVisible(true);
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        text = new JLabel("Server side");
        area = new JTextArea();

        panel.setLayout(new GridLayout(2, 1));
        panel.setPreferredSize(new Dimension(300, 300));
        panel.add(text);
        panel.add(area);
        area.setEditable(false);
        setLocationRelativeTo(null);

        this.add(panel, BorderLayout.CENTER);

    }

}

