package za.ac.cput.voteclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Emkay
 */
public class ClientGui extends JFrame {
    ClientCon con;

    protected static JComboBox<String> comboBox;
    private JLabel title;
    private JPanel centerPanel;
    private JPanel vehiclePanel;
    private JPanel bottomPanel;
    private JButton voteButton;
    private JButton viewButton;
    private JButton exitButton;
    private JLabel vehicleLabel;
    private JTable table;
    protected static DefaultTableModel tmodel;

    public ClientGui() throws Exception {
    super("Car of the Year");

    // Initialize the JTable and set its model first
    tmodel = new DefaultTableModel();
    table = new JTable(tmodel);

    tmodel.addColumn("Car name");
    tmodel.addColumn("Car Votes");
    tmodel.addRow(new Object[] {"Ford Ranger", 0});
    tmodel.addRow(new Object[] {"Audi A3", 0});
    tmodel.addRow(new Object[] {"Suzuki", 0});
    tmodel.addRow(new Object[] {"BMW X3", 0});
    tmodel.addRow(new Object[] {"Mercedes G63", 0});

    table.setFillsViewportHeight(true);
    table.setPreferredScrollableViewportSize(new Dimension(500, 100));

    // Now create the ClientCon instance, passing the initialized JTable
    con = new ClientCon(table);

    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    centerPanel = new JPanel();
    vehiclePanel = new JPanel();
    bottomPanel = new JPanel();
    voteButton = new JButton("Vote");
    viewButton = new JButton("View");
    exitButton = new JButton("Exit");
    title = new JLabel("Car of the Year", SwingConstants.CENTER);
    vehicleLabel = new JLabel("Vehicle: ");

    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setForeground(Color.black);
    title.setVisible(true);
    title.setBackground(Color.BLACK);

    centerPanel.setLayout(new BorderLayout());
    String[] cars = {"Ford Ranger", "Audi A3", "Suzuki", "BMW X3", "Toyota"};
    comboBox = new JComboBox<>(cars);
    comboBox.setPreferredSize(new Dimension(150, 25));

    vehiclePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    vehiclePanel.add(vehicleLabel);
    vehiclePanel.add(comboBox);
    vehiclePanel.setBackground(Color.DARK_GRAY);
    vehicleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    vehicleLabel.setForeground(Color.WHITE);
    

    centerPanel.add(vehiclePanel, BorderLayout.NORTH);

    bottomPanel.setLayout(new GridLayout(1, 3));
    bottomPanel.add(voteButton);
    voteButton.setFocusable(false);
    bottomPanel.add(viewButton);
    viewButton.setFocusable(false);
    bottomPanel.add(exitButton);
    exitButton.setFocusable(false);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(500, 150));

    centerPanel.add(scrollPane, BorderLayout.CENTER);

    this.add(title, BorderLayout.NORTH);
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
}

        
    public void Buttons() throws Exception{

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == voteButton) {
                    con.sendVote();

                    }
                   
                }
            
        });

        
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== viewButton){
                    try {
                        con.fetchVotes();
                    } catch (Exception ex) {
                        Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== exitButton){
                    System.exit(0);
                }
            }
        });
    }
    
 

}

