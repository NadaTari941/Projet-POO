package appli_poo;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Ajouter_Client extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField IDCF;
    private JTextField NOMCF;
    private JTextField PrenomCF;
    private JTextField ContactCF;
    private JTextField emailCF;
    private Connect c = new Connect();
    private JButton btn_exit;
    private JPanel panel;
    private JLabel lblNewLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ajouter_Client frame = new Ajouter_Client();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ajouter_Client() {
    	setTitle("Ajoute Client");
        c.connectToDatabase();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 559, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel IDA = new JLabel("ID Client :");
        IDA.setFont(new Font("Tahoma", Font.BOLD, 19));
        IDA.setBounds(22, 68, 116, 33);
        contentPane.add(IDA);
        
        IDCF = new JTextField();
        IDCF.setFont(new Font("Tahoma", Font.BOLD, 19));
        IDCF.setColumns(10);
        IDCF.setBounds(172, 64, 294, 42);
        contentPane.add(IDCF);
        
        JLabel NOMA = new JLabel("NOM Client :");
        NOMA.setFont(new Font("Tahoma", Font.BOLD, 19));
        NOMA.setBounds(22, 136, 130, 33);
        contentPane.add(NOMA);
        
        NOMCF = new JTextField();
        NOMCF.setFont(new Font("Tahoma", Font.BOLD, 19));
        NOMCF.setColumns(10);
        NOMCF.setBounds(172, 127, 294, 42);
        contentPane.add(NOMCF);
        
        JLabel PRENOM_A = new JLabel("PRENOM Client :");
        PRENOM_A.setFont(new Font("Tahoma", Font.BOLD, 19));
        PRENOM_A.setBounds(18, 196, 169, 33);
        contentPane.add(PRENOM_A);
        
        PrenomCF = new JTextField();
        PrenomCF.setFont(new Font("Tahoma", Font.BOLD, 19));
        PrenomCF.setColumns(10);
        PrenomCF.setBounds(197, 189, 269, 48);
        contentPane.add(PrenomCF);
        
        JLabel CONTACTA = new JLabel("CONTACT Client :");
        CONTACTA.setFont(new Font("Tahoma", Font.BOLD, 19));
        CONTACTA.setBounds(22, 267, 169, 33);
        contentPane.add(CONTACTA);
        
        ContactCF = new JTextField();
        ContactCF.setFont(new Font("Tahoma", Font.BOLD, 19));
        ContactCF.setColumns(10);
        ContactCF.setBounds(209, 263, 257, 42);
        contentPane.add(ContactCF);
        
        JLabel emailC = new JLabel("Email Client :");
        emailC.setFont(new Font("Tahoma", Font.BOLD, 19));
        emailC.setBounds(22, 328, 141, 33);
        contentPane.add(emailC);
        
        emailCF = new JTextField();
        emailCF.setFont(new Font("Tahoma", Font.BOLD, 19));
        emailCF.setColumns(10);
        emailCF.setBounds(197, 319, 310, 42);
        contentPane.add(emailCF);
        
        JButton btn_ajouter_client = new JButton("CONFIRMER");
        btn_ajouter_client.setBackground(new Color(255, 255, 128));
        btn_ajouter_client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idclient = IDCF.getText();
                long idc;
                try {
                    idc = Long.parseLong(idclient);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid input for client ID: " + idclient);
                    return;
                }

                String nomC = NOMCF.getText();
                String prenomC = PrenomCF.getText();
                String contC = ContactCF.getText();
                String emailC = emailCF.getText();

                String query = "INSERT INTO CLIENT(ID_CLIENT, NOMC, PRENOMC, CONTACTC, EMAILC) VALUES(" + idc + ",'" + nomC + "','" + prenomC + "','" + contC + "','" + emailC + "')";
                try {
                    if (c.getConnection() != null) {
                        Statement stat = c.getConnection().createStatement();
                        stat.execute(query);
                        JOptionPane.showMessageDialog(btn_ajouter_client, "Le client a été bien inséré!");
                        c.closeConnection();
                    } else {
                        System.err.println("Connection is not initialized!");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btn_ajouter_client.setFont(new Font("SansSerif", Font.BOLD, 19));
        btn_ajouter_client.setForeground(new Color(0, 0, 0));
        btn_ajouter_client.setBounds(165, 398, 177, 64);
        contentPane.add(btn_ajouter_client);
        
        btn_exit = new JButton("Exit");
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btn_exit.setForeground(new Color(255, 0, 0));
        btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
        btn_exit.setBackground(new Color(255, 255, 128));
        btn_exit.setBounds(419, 510, 116, 33);
        contentPane.add(btn_exit);
        
        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 545, 42);
        contentPane.add(panel);
        
        lblNewLabel = new JLabel("AJOUTE CLIENT");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
    }
}
