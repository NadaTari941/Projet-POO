import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class paneau_confg extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paneau_confg frame = new paneau_confg();
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
	public paneau_confg() {
		setTitle("Gestion Génerale");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 958, 373);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_gerer_agent = new JButton("Gerer agent");
		btn_gerer_agent.setBackground(new Color(255, 255, 255));
		btn_gerer_agent.setForeground(new Color(0, 0, 0));
		btn_gerer_agent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gerer_agent ga=new Gerer_agent();
				ga.setVisible(true);
				
				
			}
		});
		btn_gerer_agent.setFont(new Font("Sitka Text", Font.BOLD, 19));
		btn_gerer_agent.setBounds(98, 61, 207, 94);
		contentPane.add(btn_gerer_agent);
		
		JButton btn_gerer_immobilier = new JButton("Gerer immobilier");
		btn_gerer_immobilier.setBackground(new Color(255, 255, 255));
		btn_gerer_immobilier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Gerer_Bien gb=new Gerer_Bien();
				gb.setVisible(true);
				
			}
		});
		btn_gerer_immobilier.setForeground(new Color(0, 0, 0));
		btn_gerer_immobilier.setFont(new Font("Sitka Text", Font.BOLD, 19));
		btn_gerer_immobilier.setBounds(641, 64, 221, 91);
		contentPane.add(btn_gerer_immobilier);
		
		JButton btn_gerer_client = new JButton("Gerer client");
		btn_gerer_client.setBackground(new Color(255, 255, 255));
		btn_gerer_client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gerer_Client gc=new Gerer_Client();
				gc.setVisible(true);			}
		});
		btn_gerer_client.setForeground(new Color(0, 0, 0));
		btn_gerer_client.setFont(new Font("Sitka Text", Font.BOLD, 19));
		btn_gerer_client.setBounds(379, 61, 207, 94);
		contentPane.add(btn_gerer_client);
		
		JButton btn_gerer_rendez_vous = new JButton("Gerer rendez-vous");
		btn_gerer_rendez_vous.setBackground(new Color(255, 255, 255));
		btn_gerer_rendez_vous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gerer_Rdv gr=new Gerer_Rdv();
				gr.setVisible(true);
			}
		});
		btn_gerer_rendez_vous.setForeground(new Color(0, 0, 0));
		btn_gerer_rendez_vous.setFont(new Font("Sitka Text", Font.BOLD, 19));
		btn_gerer_rendez_vous.setBounds(379, 201, 221, 94);
		contentPane.add(btn_gerer_rendez_vous);
		
		JButton btn_gerer_transaction = new JButton("Gerer transaction");
		btn_gerer_transaction.setBackground(new Color(255, 255, 255));
		btn_gerer_transaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gerer_Transaction gt=new Gerer_Transaction();
				gt.setVisible(true);
			}
		});
		btn_gerer_transaction.setForeground(new Color(0, 0, 0));
		btn_gerer_transaction.setFont(new Font("Sitka Text", Font.BOLD, 19));
		btn_gerer_transaction.setBounds(98, 201, 207, 94);
		contentPane.add(btn_gerer_transaction);
		
		JButton btn_exit = new JButton("Exit");
		btn_exit.setBackground(new Color(255, 255, 128));
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btn_exit.setForeground(new Color(0, 0, 0));
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
		btn_exit.setBounds(817, 285, 117, 41);
		contentPane.add(btn_exit);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 0));
		panel.setBounds(0, 0, 944, 35);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("GESTION GENERALE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblNewLabel);
	}
}
