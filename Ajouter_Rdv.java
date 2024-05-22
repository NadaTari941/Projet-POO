package appli_poo;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ajouter_Rdv extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField dateRdvField;
    private JTextField heureRdvField;
    private Connect c = new Connect();
    private JButton btn_exit;
    private JComboBox<String> bienComboBox;
    private JComboBox<String> clientComboBox;
    private JTextField IDRDVF;
    private JPanel panel;
    private JLabel lblNewLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ajouter_Rdv frame = new Ajouter_Rdv();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ajouter_Rdv() {
    	setTitle("Ajout Rendez_Vous");
        c.connectToDatabase();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 572, 536);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setForeground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblIdBien = new JLabel("Bien :");
        lblIdBien.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblIdBien.setBounds(29, 126, 116, 33);
        contentPane.add(lblIdBien);
        
        // ComboBox for Bien
        bienComboBox = new JComboBox<>();
        bienComboBox.setFont(new Font("Tahoma", Font.BOLD, 19));
        bienComboBox.setBounds(169, 121, 294, 42);
        contentPane.add(bienComboBox);
        
        JLabel lblIdClient = new JLabel("Client :");
        lblIdClient.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblIdClient.setBounds(29, 179, 130, 33);
        contentPane.add(lblIdClient);
        
        // ComboBox for Client
        clientComboBox = new JComboBox<>();
        clientComboBox.setFont(new Font("Tahoma", Font.BOLD, 19));
        clientComboBox.setBounds(179, 174, 284, 43);
        contentPane.add(clientComboBox);
        
        JLabel lblDateRdv = new JLabel("Date Rendez-vous :");
        lblDateRdv.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblDateRdv.setBounds(10, 246, 213, 33);
        contentPane.add(lblDateRdv);
        
        dateRdvField = new JTextField();
        dateRdvField.setFont(new Font("Tahoma", Font.BOLD, 19));
        dateRdvField.setColumns(10);
        dateRdvField.setBounds(244, 241, 219, 43);
        contentPane.add(dateRdvField);
        
        JLabel lblHeureRdv = new JLabel("Heure Rendez-vous :");
        lblHeureRdv.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblHeureRdv.setBounds(10, 311, 213, 33);
        contentPane.add(lblHeureRdv);
        
        heureRdvField = new JTextField();
        heureRdvField.setFont(new Font("Tahoma", Font.BOLD, 19));
        heureRdvField.setColumns(10);
        heureRdvField.setBounds(244, 304, 219, 42);
        contentPane.add(heureRdvField);
        
        JButton btnAjouterRdv = new JButton("Ajouter Rendez-vous");
        btnAjouterRdv.setBackground(new Color(255, 255, 128));
        btnAjouterRdv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bienName = (String) bienComboBox.getSelectedItem();
                String clientName = (String) clientComboBox.getSelectedItem();
                String dateRdv = dateRdvField.getText();
                String heureRdv = heureRdvField.getText();
                String idrdvf = IDRDVF.getText();
                long idrdv;
                try {
                    idrdv = Long.parseLong(idrdvf);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid input for client ID: " + idrdvf);
                    return;
                }

                Long bienID = getIDFromName("bienimmobilier", bienName);
                Long clientID = getIDFromName("client", clientName);

                if (bienID != -1 && clientID != -1) {
                    String query = "INSERT INTO rendezvous(ID_RENDEZVOUS,ID_BIEN, ID_CLIENT, DATE_RENDEZVOUS, HEURE_RENDEZVOUS) VALUES(?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
                    try {
                        if (c.getConnection() != null) {
                            PreparedStatement statement = c.getConnection().prepareStatement(query);
                            statement.setLong(1, idrdv);
                            statement.setLong(2, bienID);
                            statement.setLong(3, clientID);
                            statement.setString(4, dateRdv);
                            statement.setString(5, heureRdv);
                            statement.executeUpdate();
                            JOptionPane.showMessageDialog(btnAjouterRdv, "Le rendez-vous a été bien inséré!");
                            c.closeConnection();
                        } else {
                            System.err.println("Connection is not initialized!");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(btnAjouterRdv, "Erreur: Bien ou client introuvable.");
                }
            }
        });
        btnAjouterRdv.setFont(new Font("SansSerif", Font.BOLD, 19));
        btnAjouterRdv.setForeground(new Color(0, 255, 64));
        btnAjouterRdv.setBounds(135, 373, 261, 42);
        contentPane.add(btnAjouterRdv);
        
        btn_exit = new JButton("Exit");
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.closeConnection();
                dispose();
            }
        });
        btn_exit.setForeground(Color.RED);
        btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
        btn_exit.setBackground(new Color(255, 255, 128));
        btn_exit.setBounds(432, 435, 116, 33);
        contentPane.add(btn_exit);
        
        JLabel IDRDV = new JLabel("id_rendv");
        IDRDV.setFont(new Font("Tahoma", Font.BOLD, 19));
        IDRDV.setBounds(29, 67, 116, 33);
        contentPane.add(IDRDV);
        
        IDRDVF = new JTextField();
        IDRDVF.setFont(new Font("Tahoma", Font.BOLD, 19));
        IDRDVF.setColumns(10);
        IDRDVF.setBounds(181, 58, 282, 42);
        contentPane.add(IDRDVF);
        
        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 558, 48);
        contentPane.add(panel);
        
        lblNewLabel = new JLabel("AJOUTE RENDEZ-VOUS");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
        
        populateComboBoxes(); 
    }

    
    private void populateComboBoxes() {
        String queryBien = "SELECT DESCRIPTION FROM bienimmobilier";
        String queryClient = "SELECT NOMC FROM client";
        try {
            Statement statement = c.getConnection().createStatement();
            ResultSet resultSetBien = statement.executeQuery(queryBien);
            while (resultSetBien.next()) {
                bienComboBox.addItem(resultSetBien.getString(1));
            }
            resultSetBien.close(); 

            ResultSet resultSetClient = statement.executeQuery(queryClient);
            while (resultSetClient.next()) {
                clientComboBox.addItem(resultSetClient.getString(1));
            }
            resultSetClient.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Long getIDFromName(String tableName, String name) {
        String columnName = tableName.equals("bienimmobilier") ? "DESCRIPTION" : "NOMC";
        String query = "SELECT ID_" + tableName.toUpperCase() + " FROM " + tableName.toLowerCase() + " WHERE " + columnName + " = ?";
        try {
            PreparedStatement preparedStatement = c.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                return -1L; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        }
    }
}
