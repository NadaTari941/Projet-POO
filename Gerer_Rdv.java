package appli_poo;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class Gerer_Rdv extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private Connect c = new Connect();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gerer_Rdv frame = new Gerer_Rdv();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Gerer_Rdv() {
        setTitle("Gestion des Rendez_Vous");
        c.connectToDatabase();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1090, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel contentPane_1 = new JPanel();
        contentPane_1.setBackground(new Color(0, 0, 255));
        contentPane_1.setLayout(null);
        contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane_1.setBounds(0, 0, 1086, 506);
        contentPane.add(contentPane_1);

        JButton btn_ajouter_rdv = new JButton("Ajouter Rendez-vous");
        btn_ajouter_rdv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ajouter_Rdv ajouterRdv = new Ajouter_Rdv();
                ajouterRdv.setVisible(true);
            }
        });
        btn_ajouter_rdv.setForeground(new Color(0, 0, 0));
        btn_ajouter_rdv.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_ajouter_rdv.setBounds(63, 67, 242, 39);
        contentPane_1.add(btn_ajouter_rdv);

        JButton btn_supprimer_rdv = new JButton("Supprimer Rendez-vous");
        btn_supprimer_rdv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce rendez-vous?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        long rdvId = (long) table.getValueAt(selectedRow, 0);
                        deleteRendezVous(rdvId);
                        loadRendezVous();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un rendez-vous à supprimer.");
                }
            }
        });
        btn_supprimer_rdv.setForeground(new Color(0, 0, 0));
        btn_supprimer_rdv.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_supprimer_rdv.setBounds(393, 67, 282, 39);
        contentPane_1.add(btn_supprimer_rdv);
         
        
        
        JButton btn_afficher_rdvs = new JButton("Afficher Rendez-vous");
        btn_afficher_rdvs.setBackground(new Color(255, 255, 128));
        btn_afficher_rdvs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadRendezVous();
            }
        });
        btn_afficher_rdvs.setForeground(new Color(0, 0, 0));
        btn_afficher_rdvs.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_afficher_rdvs.setBounds(63, 116, 242, 47);
        contentPane_1.add(btn_afficher_rdvs);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(63, 186, 938, 257);
        contentPane_1.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Client", "Bien", "Date", "Heure"
                }
        ));

        JButton btn_exit = new JButton("Exit");
        btn_exit.setBackground(new Color(255, 255, 128));
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btn_exit.setForeground(Color.RED);
        btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
        btn_exit.setBounds(927, 463, 117, 33);
        contentPane_1.add(btn_exit);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 1076, 47);
        contentPane_1.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("GESTION DES RENDEZVOUS");
        lblNewLabel.setBounds(369, 5, 396, 29);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
        
        JButton btn_modifier = new JButton("modifier Rendez-vous");
        btn_modifier.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        btn_modifier.setForeground(Color.BLACK);
        btn_modifier.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_modifier.setBounds(736, 67, 282, 39);
        contentPane_1.add(btn_modifier);
    }

    private void loadRendezVous() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try {
            Statement stat = c.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("SELECT ID_RENDEZVOUS, ID_CLIENT, ID_BIEN, DATE_RENDEZVOUS, HEURE_RENDEZVOUS FROM RENDEZVOUS");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getLong("ID_RENDEZVOUS"),
                    rs.getLong("ID_CLIENT"),
                    rs.getLong("ID_BIEN"),
                    rs.getDate("DATE_RENDEZVOUS"),
                    rs.getString("HEURE_RENDEZVOUS")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRendezVous(long rdvId) {
        try {
            Statement stat = c.getConnection().createStatement();
            stat.executeUpdate
            ("DELETE FROM RENDEZVOUS WHERE ID_RENDEZVOUS = " + rdvId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}