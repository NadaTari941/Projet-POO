package appli_poo;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class modifier extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldID;
    private JTextField textFieldClient;
    private JTextField textFieldBien;
    private JTextField textFieldDate;
    private JTextField textFieldHeure;
    private Connection conn;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Connect connection = new Connect();
                    connection.connectToDatabase();
                    modifier frame = new modifier(connection.getConnection());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public modifier(Connection connection) {
        setTitle("Modifier Rendez-vous");
        this.conn = connection;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblId.setBounds(27, 30, 86, 14);
        contentPane.add(lblId);

        textFieldID = new JTextField();
        textFieldID.setBounds(123, 28, 86, 20);
        contentPane.add(textFieldID);
        textFieldID.setColumns(10);

        JLabel lblClient = new JLabel("Client:");
        lblClient.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblClient.setBounds(27, 80, 86, 14);
        contentPane.add(lblClient);

        textFieldClient = new JTextField();
        textFieldClient.setBounds(123, 78, 86, 20);
        contentPane.add(textFieldClient);
        textFieldClient.setColumns(10);

        JLabel lblBien = new JLabel("Bien:");
        lblBien.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBien.setBounds(27, 130, 86, 14);
        contentPane.add(lblBien);

        textFieldBien = new JTextField();
        textFieldBien.setBounds(123, 128, 86, 20);
        contentPane.add(textFieldBien);
        textFieldBien.setColumns(10);

        JLabel lblDate = new JLabel("Date:");
        lblDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDate.setBounds(27, 180, 86, 14);
        contentPane.add(lblDate);

        textFieldDate = new JTextField();
        textFieldDate.setBounds(123, 178, 86, 20);
        contentPane.add(textFieldDate);
        textFieldDate.setColumns(10);

        JLabel lblHeure = new JLabel("Heure:");
        lblHeure.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblHeure.setBounds(27, 230, 86, 14);
        contentPane.add(lblHeure);

        textFieldHeure = new JTextField();
        textFieldHeure.setBounds(123, 228, 86, 20);
        contentPane.add(textFieldHeure);
        textFieldHeure.setColumns(10);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.setBackground(new Color(255, 255, 128));
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRendezVous();
            }
        });
        btnModifier.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnModifier.setBounds(27, 290, 100, 30);
        contentPane.add(btnModifier);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBackground(new Color(255, 255, 128));
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnAnnuler.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAnnuler.setBounds(160, 290, 100, 30);
        contentPane.add(btnAnnuler);
    }

    private void updateRendezVous() {
        String idStr = textFieldID.getText();
        String clientStr = textFieldClient.getText();
        String bienStr = textFieldBien.getText();
        String dateStr = textFieldDate.getText();
        String heureStr = textFieldHeure.getText();

        try {
            long id = Long.parseLong(idStr);
            // Validate other fields as needed
            // Assuming you have validated other fields, proceed to update the database

            String query = "UPDATE RENDEZVOUS SET ID_CLIENT=?, ID_BIEN=?, DATE_RENDEZVOUS=?, HEURE_RENDEZVOUS=? WHERE ID_RENDEZVOUS=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, Long.parseLong(clientStr));
            pstmt.setLong(2, Long.parseLong(bienStr));
            pstmt.setString(3, dateStr);
            pstmt.setString(4, heureStr);
            pstmt.setLong(5, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Rendez-vous modifié avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la modification du rendez-vous.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

            pstmt.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir des valeurs numériques valides pour les champs ID, Client et Bien.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du rendez-vous.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
