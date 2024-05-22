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

public class Ajouter_agent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ID_AF;
	private JTextField NOM_AF;
	private JTextField PRENOM_AF;
	private JTextField CONTACTAF;
	private Connect c=new Connect();
	private JButton btn_exit;
	private JPanel panel;
	private JLabel lblNewLabel;

	
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ajouter_agent frame = new Ajouter_agent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Ajouter_agent() {
		setTitle("Ajoute Agent");
		
		c.connectToDatabase();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 541);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDA = new JLabel("ID Agent :");
		IDA.setBounds(32, 65, 116, 33);
		IDA.setFont(new Font("Tahoma", Font.BOLD, 19));
		contentPane.add(IDA);
		
		ID_AF = new JTextField();
		ID_AF.setBounds(172, 64, 294, 42);
		ID_AF.setFont(new Font("Tahoma", Font.BOLD, 19));
		contentPane.add(ID_AF);
		ID_AF.setColumns(10);
		
		JLabel NOMA = new JLabel("NOM Agent :");
		NOMA.setBounds(32, 133, 130, 33);
		NOMA.setFont(new Font("Tahoma", Font.BOLD, 19));
		contentPane.add(NOMA);
		
		NOM_AF = new JTextField();
		NOM_AF.setBounds(172, 124, 294, 42);
		NOM_AF.setFont(new Font("Tahoma", Font.BOLD, 19));
		NOM_AF.setColumns(10);
		contentPane.add(NOM_AF);
		
		JLabel PRENOM_A = new JLabel("PRENOM Agent :");
		PRENOM_A.setBounds(32, 200, 169, 33);
		PRENOM_A.setFont(new Font("Tahoma", Font.BOLD, 19));
		contentPane.add(PRENOM_A);
		
		PRENOM_AF = new JTextField();
		PRENOM_AF.setBounds(197, 189, 269, 48);
		PRENOM_AF.setFont(new Font("Tahoma", Font.BOLD, 19));
		PRENOM_AF.setColumns(10);
		contentPane.add(PRENOM_AF);
		
		JLabel CONTACTA = new JLabel("CONTACT Agent :");
		CONTACTA.setBounds(32, 260, 169, 33);
		CONTACTA.setFont(new Font("Tahoma", Font.BOLD, 19));
		contentPane.add(CONTACTA);
		
		CONTACTAF = new JTextField();
		CONTACTAF.setBounds(209, 253, 257, 48);
		CONTACTAF.setFont(new Font("Tahoma", Font.BOLD, 19));
		CONTACTAF.setColumns(10);
		contentPane.add(CONTACTAF);
		
		JButton btn_ajouter_agent = new JButton("Ajouter l'agent");
		btn_ajouter_agent.setBackground(new Color(255, 255, 128));
		btn_ajouter_agent.setBounds(136, 365, 203, 42);
		btn_ajouter_agent.setForeground(new Color(0, 0, 0));
		btn_ajouter_agent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String idagent = ID_AF.getText();
	        	    long ida;
	        	    try {
	        	        ida = Long.parseLong(idagent);
	        	    } catch (NumberFormatException ex) {
	        	        System.err.println("Invalid input for client ID: " + idagent);
	        	        return;
	        	    }

	        	    String nomA = NOM_AF.getText();
	        	    String prenomA = PRENOM_AF.getText();
	        	    String contA = CONTACTAF.getText();

	        	    String query = "INSERT INTO AGENT(ID_Agent,NomA,PrenomA,ContactA) VALUES(" + ida + ",'" + nomA + "','" + prenomA + "','" + contA + "')";
	        	    try {
	        	        if (c.getConnection() != null) {
	        	            Statement stat = c.getConnection().createStatement();
	        	            stat.execute(query);
	        	            JOptionPane.showMessageDialog(btn_ajouter_agent,"L'agent a été bien inseré!");
	        	            c.closeConnection();
	        	        } else {
	        	            System.err.println("Connection is not initialized!");
	        	        }
	        	    } catch (SQLException e1) {
	        	        e1.printStackTrace();
	        	    }
	        	}
				
		});
		btn_ajouter_agent.setFont(new Font("SansSerif", Font.BOLD, 19));
		contentPane.add(btn_ajouter_agent);
		
		btn_exit = new JButton("Exit");
		btn_exit.setBounds(383, 444, 116, 33);
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    dispose();
			}
		});
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
		btn_exit.setBackground(new Color(255, 255, 128));
		btn_exit.setForeground(new Color(255, 0, 0));
		contentPane.add(btn_exit);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 128));
		panel.setBounds(0, 0, 509, 48);
		contentPane.add(panel);
		
		lblNewLabel = new JLabel("AJOUTE AGENT");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(lblNewLabel);
	}

}
