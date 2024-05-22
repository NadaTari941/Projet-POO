package appli_poo;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class Login {

	private JFrame frmEspaceDeTravaille;
	private JTextField LoginF;
	private JButton btn_login;
	private JPasswordField PassF;
	private Connect c=new Connect();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmEspaceDeTravaille.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	 
	public Login() {
		initialize();
		c.connectToDatabase();
	
		
	}

	private void initialize() {
		frmEspaceDeTravaille = new JFrame();
		frmEspaceDeTravaille.getContentPane().setBackground(new Color(192, 192, 192));
		frmEspaceDeTravaille.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 10));
		frmEspaceDeTravaille.setTitle("Conexion");
		frmEspaceDeTravaille.getContentPane().setForeground(new Color(128, 128, 128));
		frmEspaceDeTravaille.setBounds(100, 100, 696, 332);
		frmEspaceDeTravaille.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEspaceDeTravaille.getContentPane().setLayout(null);
		
		LoginF = new JTextField();
		LoginF.setBounds(405, 58, 231, 40);
		LoginF.setFont(new Font("Tahoma", Font.BOLD, 19));
		frmEspaceDeTravaille.getContentPane().add(LoginF);
		LoginF.setColumns(10);
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setBounds(405, 14, 202, 29);
		loginLabel.setFont(new Font("Serif", Font.BOLD, 20));
		frmEspaceDeTravaille.getContentPane().add(loginLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(405, 108, 202, 29);
		lblPassword.setFont(new Font("Serif", Font.BOLD, 20));
		frmEspaceDeTravaille.getContentPane().add(lblPassword);
		
		btn_login = new JButton("Login");
		btn_login.setBounds(413, 262, 104, 21);
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				String loginV=LoginF.getText();
				char[] passChars = PassF.getPassword();
		        String passV = new String(passChars);
		        
		        if (loginV.equals(c.getUsername()) && passV.equals(c.getPassword())) {
		            System.out.println("Login successful");
		            c.closeConnection();
		            paneau_confg main=new paneau_confg();
		            main.setVisible(true);
		            frmEspaceDeTravaille.setVisible(false);
		            
		            
		        } else {
		            System.out.println("Invalid username or password");
		            JOptionPane.showMessageDialog(btn_login,"verifier votre nom d'utulisateur  (ou) votre mot de pass");
		        }
				

			}
		});
		btn_login.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmEspaceDeTravaille.getContentPane().add(btn_login);
		
		PassF = new JPasswordField();
		PassF.setBounds(405, 147, 231, 40);
		PassF.setFont(new Font("Tahoma", Font.BOLD, 19));
		frmEspaceDeTravaille.getContentPane().add(PassF);
		
		JButton btn_annuler_1 = new JButton("Exit");
        btn_annuler_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Dispose du frame
                frmEspaceDeTravaille.dispose();
            }
        });
        btn_annuler_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btn_annuler_1.setBounds(568, 262, 104, 21);
        frmEspaceDeTravaille.getContentPane().add(btn_annuler_1);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\atamena\\Downloads\\login-icon-3060.png"));
        lblNewLabel.setBounds(10, 14, 372, 269);
        frmEspaceDeTravaille.getContentPane().add(lblNewLabel);}	
}