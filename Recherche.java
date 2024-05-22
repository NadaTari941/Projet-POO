package aapli_poo;
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
import net.proteanit.sql.DbUtils;

public class Recherche extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldType;
    private JTextField textFieldTaille;
    private JTextField textFieldPrixMax;
    private JTextField textFieldLocalisation;
    private JTable table;
    private Connection conn;
    private JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Connect connection = new Connect();
                    connection.connectToDatabase();
                    Recherche frame = new Recherche(connection.getConnection());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Recherche(Connection connection) {
        setTitle("recherche bienimmobilers");
        this.conn = connection;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 847, 705);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 273, 813, 395);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel lblType = new JLabel("TYPE :");
        lblType.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblType.setBounds(10, 71, 82, 20);
        contentPane.add(lblType);

        textFieldType = new JTextField();
        textFieldType.setFont(new Font("Tahoma", Font.BOLD, 17));
        textFieldType.setBounds(85, 59, 177, 47);
        contentPane.add(textFieldType);
        textFieldType.setColumns(10);

        JLabel lblTaille = new JLabel("TAILLE :");
        lblTaille.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTaille.setBounds(10, 139, 98, 20);
        contentPane.add(lblTaille);

        textFieldTaille = new JTextField();
        textFieldTaille.setFont(new Font("Tahoma", Font.BOLD, 17));
        textFieldTaille.setColumns(10);
        textFieldTaille.setBounds(121, 127, 177, 47);
        contentPane.add(textFieldTaille);

        JLabel lblPrixMax = new JLabel("PRIX MAX :");
        lblPrixMax.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPrixMax.setBounds(268, 71, 120, 20);
        contentPane.add(lblPrixMax);

        textFieldPrixMax = new JTextField();
        textFieldPrixMax.setFont(new Font("Tahoma", Font.BOLD, 17));
        textFieldPrixMax.setColumns(10);
        textFieldPrixMax.setBounds(382, 59, 154, 47);
        contentPane.add(textFieldPrixMax);

        JLabel lblLocalisation = new JLabel("LOCALISATION :");
        lblLocalisation.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblLocalisation.setBounds(382, 139, 154, 20);
        contentPane.add(lblLocalisation);

        textFieldLocalisation = new JTextField();
        textFieldLocalisation.setFont(new Font("Tahoma", Font.BOLD, 17));
        textFieldLocalisation.setColumns(10);
        textFieldLocalisation.setBounds(545, 125, 182, 51);
        contentPane.add(textFieldLocalisation);

        JButton btnRecherche = new JButton("Recherche");
        btnRecherche.setBackground(new Color(255, 255, 128));
        btnRecherche.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchBienImmobilier();
            }
        });
        btnRecherche.setForeground(new Color(0, 255, 128));
        btnRecherche.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnRecherche.setBounds(190, 203, 177, 46);
        contentPane.add(btnRecherche);
        
        JButton btnannulerr = new JButton("Annuler");
        btnannulerr.setBackground(new Color(255, 255, 128));
        btnannulerr.setForeground(new Color(255, 0, 0));
        btnannulerr.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnannulerr.setBounds(475, 199, 167, 54);
        contentPane.add(btnannulerr);
        
        JLabel lblPrixMin = new JLabel("PRIX MIN :");
        lblPrixMin.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPrixMin.setBounds(546, 71, 120, 20);
        contentPane.add(lblPrixMin);
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.BOLD, 17));
        textField.setColumns(10);
        textField.setBounds(654, 59, 169, 47);
        contentPane.add(textField);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 833, 39);
        contentPane.add(panel);
        
        JLabel lblNewLabel = new JLabel("RECHERCHE BIENIMMOBILIER");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);

        btnannulerr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

    }

    private void searchBienImmobilier() {
        String type = textFieldType.getText();
        String taille = textFieldTaille.getText();
        String prixMax = textFieldPrixMax.getText();
        String localisation = textFieldLocalisation.getText();

        String query = "SELECT * FROM BIENIMMOBILIER WHERE 1=1";

        if (!type.isEmpty()) {
            query += " AND TYPE LIKE ?";
        }
        if (!taille.isEmpty()) {
            query += " AND TAILLE <= ?";
        }
        if (!prixMax.isEmpty()) {
            query += " AND PRIX <= ?";
        }
        if (!localisation.isEmpty()) {
            query += " AND LOCATION LIKE ?";
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            int parameterIndex = 1;

            if (!type.isEmpty()) {
                pstmt.setString(parameterIndex++, "%" + type + "%");
            }
            if (!taille.isEmpty()) {
                pstmt.setString(parameterIndex++, taille);
            }
            if (!prixMax.isEmpty()) {
                pstmt.setString(parameterIndex++, prixMax);
            }
            if (!localisation.isEmpty()) {
                pstmt.setString(parameterIndex, "%" + localisation + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
