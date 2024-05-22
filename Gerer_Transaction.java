package appli_poo;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

public class Gerer_Transaction extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private Connect c = new Connect();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gerer_Transaction frame = new Gerer_Transaction();
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
    public Gerer_Transaction() {
    	setTitle("Gestion de Transaction");
        c.connectToDatabase();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1090, 636);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel contentPane_1 = new JPanel();
        contentPane_1.setBackground(new Color(0, 0, 255));
        contentPane_1.setLayout(null);
        contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane_1.setBounds(0, 0, 1076, 599);
        contentPane.add(contentPane_1);

        JButton btn_ajouter_trans = new JButton("Ajouter transaction");
        btn_ajouter_trans.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ajouter_Transaction at = new Ajouter_Transaction();
                at.setVisible(true);
            }
        });
        btn_ajouter_trans.setForeground(new Color(0, 0, 0));
        btn_ajouter_trans.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_ajouter_trans.setBounds(63, 96, 242, 51);
        contentPane_1.add(btn_ajouter_trans);

        JButton btn_supprimer_trans = new JButton("Supprimer transaction");
        btn_supprimer_trans.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cette transaction?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        long transactionId = (long) table.getValueAt(selectedRow, 0);
                        deleteTransaction(transactionId);
                        loadTransactions();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une transaction à supprimer.");
                }
            }
        });
        btn_supprimer_trans.setForeground(new Color(0, 0, 0));
        btn_supprimer_trans.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_supprimer_trans.setBounds(413, 96, 259, 51);
        contentPane_1.add(btn_supprimer_trans);

        JButton btn_modifier_trans = new JButton("Modifier transaction");
        btn_modifier_trans.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Gerer_Transaction.this, "Veuillez sélectionner une transaction à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String transactionID = table.getValueAt(selectedRow, 0).toString();
                String transactionType = table.getValueAt(selectedRow, 1).toString();
                String transactionDetails = table.getValueAt(selectedRow, 2).toString();
                String transactionDate = table.getValueAt(selectedRow, 3).toString();
                String transactionMontant = table.getValueAt(selectedRow, 4).toString();

                String newType = JOptionPane.showInputDialog(Gerer_Transaction.this, "Entrez le nouveau type de transaction :", transactionType);
                String newDetails = JOptionPane.showInputDialog(Gerer_Transaction.this, "Entrez les nouveaux détails de transaction :", transactionDetails);
                String newDate = JOptionPane.showInputDialog(Gerer_Transaction.this, "Entrez la nouvelle date de transaction (yyyy-mm-dd) :", transactionDate);
                String newMontant = JOptionPane.showInputDialog(Gerer_Transaction.this, "Entrez le nouveau montant de transaction :", transactionMontant);

                if (newType != null && newDetails != null && newDate != null && newMontant != null) {
                    String updateQuery = "UPDATE TRANSACTION SET TYPE = '" + newType + "', DETAILS = '" + newDetails + "', DATE_TRANSACTION = TO_DATE('" + newDate + "', 'YYYY-MM-DD'), MONTANT = " + newMontant + " WHERE ID_TRANSACTION = " + transactionID;
                    try {
                        Statement stat = c.getConnection().createStatement();
                        stat.executeUpdate(updateQuery);
                        JOptionPane.showMessageDialog(Gerer_Transaction.this, "Détails de la transaction mis à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        loadTransactions();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Gerer_Transaction.this, "Erreur lors de la mise à jour des détails de la transaction.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btn_modifier_trans.setForeground(new Color(0, 0, 0));
        btn_modifier_trans.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_modifier_trans.setBounds(774, 96, 292, 51);
        contentPane_1.add(btn_modifier_trans);

        JButton btn_afficher_transactions = new JButton("Afficher transactions");
        btn_afficher_transactions.setBackground(new Color(255, 255, 128));
        btn_afficher_transactions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTransactions();
            }
        });
        btn_afficher_transactions.setForeground(new Color(0, 255, 128));
        btn_afficher_transactions.setFont(new Font("Sitka Text", Font.BOLD, 19));
        btn_afficher_transactions.setBounds(63, 168, 242, 45);
        contentPane_1.add(btn_afficher_transactions);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(63, 223, 1003, 312);
        contentPane_1.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "ID", "Type", "Details", "Date", "Montant", "Bien", "Client", "Agent"
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
        btn_exit.setBounds(949, 545, 117, 33);
        contentPane_1.add(btn_exit);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 1101, 51);
        contentPane_1.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("GESTION DES TRANSACTIONS");
        lblNewLabel.setBounds(337, 10, 366, 29);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
    }

    private void loadTransactions() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try {
            Statement stat = c.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("SELECT t.ID_TRANSACTION, t.TYPE, t.DETAILS, t.DATE_TRANSACTION, t.MONTANT, " +
                                             "b.TYPE AS BIEN_TYPE, c.NOMC || ' ' || c.PRENOMC AS CLIENT_NAME, a.NOMA || ' ' || a.PRENOMA AS AGENT_NAME " +
                                             "FROM TRANSACTION t " +
                                             "JOIN BIENIMMOBILIER b ON t.ID_BIEN = b.ID_BIENIMMOBILIER " +
                                             "JOIN CLIENT c ON t.ID_CLIENT = c.ID_CLIENT " +
                                             "JOIN AGENT a ON t.ID_AGENT = a.ID_AGENT");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getLong("ID_TRANSACTION"),
                    rs.getString("TYPE"),
                    rs.getString("DETAILS"),
                    rs.getDate("DATE_TRANSACTION"),
                    rs.getFloat("MONTANT"),
                    rs.getString("BIEN_TYPE"),
                    rs.getString("CLIENT_NAME"),
                    rs.getString("AGENT_NAME")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTransaction(long transactionId) {
        try {
            Statement stat = c.getConnection().createStatement();
            stat.executeUpdate("DELETE FROM TRANSACTION WHERE ID_TRANSACTION = " + transactionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
