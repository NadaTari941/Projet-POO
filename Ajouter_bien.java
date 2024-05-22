package appli_poo;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ajouter_bien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField IDBF;
    private JTextField TypeBF;
    private JTextField TailleBF;
    private JTextField PrixBF;
    private JTextField LocationBF;
    private JTextField DescriptionBF;
    private JComboBox<String> agentComboBox;
    private Connect c = new Connect();
    private JButton btn_exit;
    private JPanel panel;
    private JLabel lblNewLabel;

   
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ajouter_bien frame = new Ajouter_bien();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public Ajouter_bien() {
    	setTitle("Ajoute bienimmobilier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 535, 616);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel IDB = new JLabel("ID Bien :");
        IDB.setBounds(24, 60, 98, 23);
        IDB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(IDB);

        IDBF = new JTextField();
        IDBF.setBounds(191, 52, 235, 39);
        IDBF.setBackground(new Color(255, 255, 255));
        IDBF.setFont(new Font("Tahoma", Font.BOLD, 19));
        IDBF.setColumns(10);
        contentPane.add(IDBF);

        JLabel TypeB = new JLabel("Type Bien :");
        TypeB.setBounds(24, 110, 142, 23);
        TypeB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(TypeB);

        TypeBF = new JTextField();
        TypeBF.setBounds(191, 101, 235, 42);
        TypeBF.setBackground(new Color(255, 255, 255));
        TypeBF.setFont(new Font("Tahoma", Font.BOLD, 19));
        TypeBF.setColumns(10);
        contentPane.add(TypeBF);

        JLabel TailleB = new JLabel("Taille Bien :");
        TailleB.setBounds(24, 173, 142, 23);
        TailleB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(TailleB);

        TailleBF = new JTextField();
        TailleBF.setBounds(191, 165, 235, 40);
        TailleBF.setBackground(new Color(255, 255, 255));
        TailleBF.setFont(new Font("Tahoma", Font.BOLD, 19));
        TailleBF.setColumns(10);
        contentPane.add(TailleBF);

        JLabel PrixB = new JLabel("Prix Bien :");
        PrixB.setBounds(24, 223, 142, 23);
        PrixB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(PrixB);

        PrixBF = new JTextField();
        PrixBF.setBounds(204, 215, 222, 39);
        PrixBF.setBackground(new Color(255, 255, 255));
        PrixBF.setFont(new Font("Tahoma", Font.BOLD, 19));
        PrixBF.setColumns(10);
        contentPane.add(PrixBF);

        LocationBF = new JTextField();
        LocationBF.setBounds(227, 264, 198, 42);
        LocationBF.setBackground(new Color(255, 255, 255));
        LocationBF.setFont(new Font("Tahoma", Font.BOLD, 19));
        LocationBF.setColumns(10);
        contentPane.add(LocationBF);

        JLabel LocationB = new JLabel("Localisation Bien :");
        LocationB.setBounds(27, 273, 182, 23);
        LocationB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(LocationB);

        DescriptionBF = new JTextField();
        DescriptionBF.setBounds(227, 321, 198, 51);
        DescriptionBF.setBackground(new Color(255, 255, 255));
        DescriptionBF.setToolTipText("");
        DescriptionBF.setFont(new Font("Tahoma", Font.PLAIN, 18));
        DescriptionBF.setColumns(10);
        contentPane.add(DescriptionBF);

        JLabel DescriptionB = new JLabel("Description Bien :");
        DescriptionB.setBounds(24, 334, 182, 23);
        DescriptionB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(DescriptionB);

        JLabel Agent = new JLabel("Agent Bien :");
        Agent.setBounds(24, 388, 142, 23);
        Agent.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(Agent);

        agentComboBox = new JComboBox<>();
        agentComboBox.setFont(new Font("Tahoma", Font.BOLD, 19));
        agentComboBox.setBounds(204, 382, 222, 42);
        agentComboBox.setBackground(new Color(192, 192, 192));
        contentPane.add(agentComboBox);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setForeground(new Color(0, 255, 128));
        btnAjouter.setBounds(252, 450, 174, 51);
        btnAjouter.setBackground(new Color(255, 255, 128));
        btnAjouter.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(btnAjouter);
        
        btn_exit = new JButton("Exit");
        btn_exit.setBackground(new Color(255, 255, 128));
        btn_exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btn_exit.setBounds(405, 536, 117, 33);
        btn_exit.setForeground(new Color(255, 0, 0));
        btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(btn_exit);
        
        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 558, 42);
        contentPane.add(panel);
        
        lblNewLabel = new JLabel("AJOUTE BIENIMMOBILIER");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);

        // Populate agent combo box
        populateAgentComboBox();

        // Action listener for adding a new bien
        btnAjouter.addActionListener(e -> ajouterBien());
    }

    private void populateAgentComboBox() {
        c.connectToDatabase();
        String query = "SELECT ID_AGENT, NOMA, PRENOMA FROM AGENT";
        try {
            Statement stat = c.getConnection().createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                String agentInfo = res.getString("ID_AGENT") + " - " + res.getString("NOMA") + " " + res.getString("PRENOMA");
                agentComboBox.addItem(agentInfo);
            }

            res.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ajouterBien() {
        String idBien = IDBF.getText();
        String typeBien = TypeBF.getText();
        String tailleBien = TailleBF.getText();
        String prixBien = PrixBF.getText();
        String locationBien = LocationBF.getText();
        String descriptionBien = DescriptionBF.getText();
        String agentInfo = (String) agentComboBox.getSelectedItem();

        if (agentInfo != null) {
            String[] agentParts = agentInfo.split(" - ");
            String agentID = agentParts[0];

            String insertQuery = "INSERT INTO BIENIMMOBILIER (ID_BIENIMMOBILIER, TYPE, TAILLE, PRIX, LOCATION, DESCRIPTION, ID_AGENT) " +
                    "VALUES (" + idBien + ", '" + typeBien + "', " + tailleBien + ", " + prixBien + ", '" + locationBien + "', '" + descriptionBien + "', " + agentID + ")";
            String query2="INSERT INTO AGENTBIEN VALUES("+agentID+","+idBien+")";
            try {
                Statement stat = c.getConnection().createStatement();
                stat.executeUpdate(insertQuery);
                stat.executeUpdate(query2);
                JOptionPane.showMessageDialog(this, "Bien ajouté avec succès!");
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du bien.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un agent.");
        }
    }
}
