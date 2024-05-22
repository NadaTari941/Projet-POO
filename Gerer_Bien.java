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

public class Gerer_Bien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableBiens;
    private Connect c = new Connect();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gerer_Bien frame = new Gerer_Bien();
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
    public Gerer_Bien() {
        setTitle("Gestion des Binimmobilers");
        c.connectToDatabase();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1039, 630);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btn_ajouter_bien = new JButton("Ajouter Bien");
        btn_ajouter_bien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ajouter_bien ab = new Ajouter_bien();
                ab.setVisible(true);
            }
        });
        btn_ajouter_bien.setForeground(new Color(0, 0, 0));
        btn_ajouter_bien.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_ajouter_bien.setBounds(75, 68, 235, 50);
        contentPane.add(btn_ajouter_bien);

        JButton btn_supprimer_bien = new JButton("Supprimer Bien");
        btn_supprimer_bien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableBiens.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Gerer_Bien.this, "Veuillez sélectionner un bien à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String bienID = tableBiens.getValueAt(selectedRow, 0).toString();

                int option = JOptionPane.showConfirmDialog(Gerer_Bien.this, "Êtes-vous sûr de vouloir supprimer ce bien ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    String deleteQuery = "DELETE FROM BIENIMMOBILIER WHERE ID_BIENIMMOBILIER = " + bienID;
                    try {
                        Statement stat = c.getConnection().createStatement();
                        stat.executeUpdate(deleteQuery);
                        JOptionPane.showMessageDialog(Gerer_Bien.this, "Bien supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        refreshBienList();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Gerer_Bien.this, "Erreur lors de la suppression du bien.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btn_supprimer_bien.setForeground(new Color(0, 0, 0));
        btn_supprimer_bien.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_supprimer_bien.setBounds(398, 68, 244, 50);
        contentPane.add(btn_supprimer_bien);

        JButton btn_modifier_bien = new JButton("Modifier Bien");
        btn_modifier_bien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableBiens.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Gerer_Bien.this, "Veuillez sélectionner un bien à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String bienID = tableBiens.getValueAt(selectedRow, 0).toString();
                String bienType = tableBiens.getValueAt(selectedRow, 1).toString();
                String bienTaille = tableBiens.getValueAt(selectedRow, 2).toString();
                String bienPrix = tableBiens.getValueAt(selectedRow, 3).toString();
                String bienLocation = tableBiens.getValueAt(selectedRow, 4).toString();
                String bienDescription = tableBiens.getValueAt(selectedRow, 5).toString();

                String newType = JOptionPane.showInputDialog(Gerer_Bien.this, "Entrez le nouveau type de bien :", bienType);
                String newTaille = JOptionPane.showInputDialog(Gerer_Bien.this, "Entrez la nouvelle taille du bien :", bienTaille);
                String newPrix = JOptionPane.showInputDialog(Gerer_Bien.this, "Entrez le nouveau prix du bien :", bienPrix);
                String newLocation = JOptionPane.showInputDialog(Gerer_Bien.this, "Entrez la nouvelle location du bien :", bienLocation);
                String newDescription = JOptionPane.showInputDialog(Gerer_Bien.this, "Entrez la nouvelle description du bien :", bienDescription);

                String updateQuery = "UPDATE BIENIMMOBILIER SET TYPE = '" + newType + "', TAILLE = " + newTaille + ", PRIX = " + newPrix + ", LOCATION = '" + newLocation + "', DESCRIPTION = '" + newDescription + "' WHERE ID_BIENIMMOBILIER = " + bienID;
                try {
                    Statement stat = c.getConnection().createStatement();
                    stat.executeUpdate(updateQuery);
                    JOptionPane.showMessageDialog(Gerer_Bien.this, "Détails du bien mis à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    refreshBienList();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Gerer_Bien.this, "Erreur lors de la mise à jour des détails du bien.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn_modifier_bien.setForeground(new Color(0, 0, 0));
        btn_modifier_bien.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_modifier_bien.setBounds(717, 68, 235, 50);
        contentPane.add(btn_modifier_bien);

        JButton btn_afficher_biens = new JButton("Afficher Biens");
        btn_afficher_biens.setBackground(new Color(255, 255, 128));
        btn_afficher_biens.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshBienList();
            }
        });
        btn_afficher_biens.setForeground(new Color(0, 255, 128));
        btn_afficher_biens.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_afficher_biens.setBounds(75, 146, 235, 55);
        contentPane.add(btn_afficher_biens);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(76, 211, 876, 318);
        contentPane.add(scrollPane);

        tableBiens = new JTable();
        scrollPane.setViewportView(tableBiens);

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
        btn_exit.setBounds(898, 539, 117, 33);
        contentPane.add(btn_exit);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 1025, 58);
        contentPane.add(panel);
        
        JLabel lblNewLabel = new JLabel(" GESTION DES BIENIMMOBILIERS");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
        
        JButton btn_recherchee = new JButton("Recherche Bien");
        btn_recherchee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Recherche rechercheFrame = new Recherche(c.getConnection());
                rechercheFrame.setVisible(true);
            }
        });
        btn_recherchee.setForeground(Color.BLACK);
        btn_recherchee.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_recherchee.setBounds(398, 146, 244, 55);
        contentPane.add(btn_recherchee);
    }

    public void refreshBienList() {
        String selectQuery = "SELECT * FROM BIENIMMOBILIER";
        try {
            Statement stat = c.getConnection().createStatement();
            ResultSet rs = stat.executeQuery(selectQuery);
            tableBiens.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
