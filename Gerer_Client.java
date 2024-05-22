package appli_poo;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Gerer_Client extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableClients;
    private Connect c = new Connect();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gerer_Client frame = new Gerer_Client();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Gerer_Client() {
    	setTitle("Gestion des Clients");
        c.connectToDatabase();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1038, 644);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btn_ajouter_client = new JButton("Ajouter client");
        btn_ajouter_client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ajouter_Client ac = new Ajouter_Client();
                ac.setVisible(true);
            }
        });
        btn_ajouter_client.setForeground(Color.BLACK);
        btn_ajouter_client.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_ajouter_client.setBounds(74, 80, 218, 48);
        contentPane.add(btn_ajouter_client);

        JButton btn_supprimer_client = new JButton("Supprimer client");
        btn_supprimer_client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableClients.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Gerer_Client.this, "Veuillez sélectionner un client à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String clientID = tableClients.getValueAt(selectedRow, 0).toString();

                int option = JOptionPane.showConfirmDialog(Gerer_Client.this, "Êtes-vous sûr de vouloir supprimer ce client ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    String deleteQuery = "DELETE FROM CLIENT WHERE ID_CLIENT = " + clientID;
                    try {
                        Statement stat = c.getConnection().createStatement();
                        stat.executeUpdate(deleteQuery);
                        JOptionPane.showMessageDialog(Gerer_Client.this, "Client supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        refreshClientList();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Gerer_Client.this, "Erreur lors de la suppression du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btn_supprimer_client.setForeground(Color.BLACK);
        btn_supprimer_client.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_supprimer_client.setBounds(357, 80, 272, 48);
        contentPane.add(btn_supprimer_client);

        JButton btn_modifier_client = new JButton("Modifier client");
        btn_modifier_client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableClients.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Gerer_Client.this, "Veuillez sélectionner un client à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String clientID = tableClients.getValueAt(selectedRow, 0).toString();
                String clientNom = tableClients.getValueAt(selectedRow, 1).toString();
                String clientPrenom = tableClients.getValueAt(selectedRow, 2).toString();
                String clientContact = tableClients.getValueAt(selectedRow, 3).toString();
                String clientEmail = tableClients.getValueAt(selectedRow, 4).toString();

                String newNom = JOptionPane.showInputDialog(Gerer_Client.this, "Entrez le nouveau nom du client :", clientNom);
                String newPrenom = JOptionPane.showInputDialog(Gerer_Client.this, "Entrez le nouveau prénom du client :", clientPrenom);
                String newContact = JOptionPane.showInputDialog(Gerer_Client.this, "Entrez le nouveau contact du client :", clientContact);
                String newEmail = JOptionPane.showInputDialog(Gerer_Client.this, "Entrez le nouvel email du client :", clientEmail);

                String updateQuery = "UPDATE CLIENT SET NOMC = '" + newNom + "', PRENOMC = '" + newPrenom + "', CONTACTC = '" + newContact + "', EMAILC = '" + newEmail + "' WHERE ID_CLIENT = " + clientID;
                try {
                    Statement stat = c.getConnection().createStatement();
                    stat.executeUpdate(updateQuery);
                    JOptionPane.showMessageDialog(Gerer_Client.this, "Détails du client mis à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    refreshClientList();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Gerer_Client.this, "Erreur lors de la mise à jour des détails du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn_modifier_client.setForeground(Color.BLACK);
        btn_modifier_client.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_modifier_client.setBounds(727, 80, 235, 48);
        contentPane.add(btn_modifier_client);

        JButton btn_afficher_clients = new JButton("Afficher clients");
        btn_afficher_clients.setBackground(new Color(255, 255, 128));
        btn_afficher_clients.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshClientList();
            }
        });
        btn_afficher_clients.setForeground(Color.BLACK);
        btn_afficher_clients.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_afficher_clients.setBounds(74, 144, 218, 54);
        contentPane.add(btn_afficher_clients);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(74, 208, 901, 346);
        contentPane.add(scrollPane);

        tableClients = new JTable();
        scrollPane.setViewportView(tableClients);

        JButton btn_exit = new JButton("Exit");
        btn_exit.setBackground(new Color(255, 255, 128));
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.closeConnection();
                dispose();
            }
        });
        btn_exit.setForeground(Color.RED);
        btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
        btn_exit.setBounds(897, 564, 117, 33);
        contentPane.add(btn_exit);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 1024, 54);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("GESTION DES CLIENTS");
        lblNewLabel.setBounds(317, 10, 303, 29);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
    }

    private void refreshClientList() {
        String query = "SELECT * FROM CLIENT";
        try {
            if (c.getConnection() == null || c.getConnection().isClosed()) {
                c.connectToDatabase();
            }

            Statement stat = c.getConnection().createStatement();
            ResultSet res = stat.executeQuery(query);
            tableClients.setModel(DbUtils.resultSetToTableModel(res));

            res.close();
            stat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de la liste des clients.");
        }
    }
}
