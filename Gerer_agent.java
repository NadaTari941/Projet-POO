package appli_poo;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Gerer_agent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable liste_agents;
	private Connect c=new Connect();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gerer_agent frame = new Gerer_agent();
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
	public Gerer_agent() {
		setTitle("Gestion des Agents");
		c.connectToDatabase();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 662);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btn_ajouter_agent = new JButton("Ajouter agent");
		btn_ajouter_agent.setBounds(56, 90, 218, 40);
		btn_ajouter_agent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ajouter_agent ag=new Ajouter_agent();
				ag.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		btn_ajouter_agent.setForeground(new Color(0, 0, 0));
		btn_ajouter_agent.setFont(new Font("Sitka Text", Font.BOLD, 19));
		contentPane.add(btn_ajouter_agent);
		
		JButton btn_supprimer_agent = new JButton("Supprimer agent");
		btn_supprimer_agent.setBounds(356, 90, 218, 40);
		btn_supprimer_agent.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		        int selectedRow = liste_agents.getSelectedRow();

		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(Gerer_agent.this, "Veuillez sélectionner un agent à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		       
		        String agentID = liste_agents.getValueAt(selectedRow, 0).toString();

		       
		        int option = JOptionPane.showConfirmDialog(Gerer_agent.this, "Êtes-vous sûr de vouloir supprimer cet agent ?", "Confirmation", JOptionPane.YES_NO_OPTION);

		        if (option == JOptionPane.YES_OPTION) {
		            
		            String deleteTransactionsQuery = "DELETE FROM TRANSACTION WHERE ID_AGENT = " + agentID;
		            String deleteAgentQuery = "DELETE FROM AGENT WHERE ID_Agent = " + agentID;
		            try {
		                Connection conn = c.getConnection();
		                Statement stat = conn.createStatement();
		                
		               
		                conn.setAutoCommit(false);
		                
		                
		                stat.executeUpdate(deleteTransactionsQuery);
		                
		              
		                stat.executeUpdate(deleteAgentQuery);
		                
		               
		                conn.commit();
		                
		                JOptionPane.showMessageDialog(Gerer_agent.this, "Agent supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
		                
		                
		                refreshAgentList();
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(Gerer_agent.this, "Erreur lors de la suppression de l'agent.", "Erreur", JOptionPane.ERROR_MESSAGE);
		                try {
		                    c.getConnection().rollback(); // 
		                } catch (SQLException rollbackEx) {
		                    rollbackEx.printStackTrace();
		                }
		            }
		        }
		    }

		    private void refreshAgentList() {
		       
		        String query = "SELECT * FROM AGENT";
		        
		        try {
		            if (c.getConnection() == null || c.getConnection().isClosed()) {
		               
		                c.connectToDatabase();
		            }
		            
		            Statement stat = c.getConnection().createStatement();
		            ResultSet res = stat.executeQuery(query);
		            
		            // Update the JTable with the new ResultSet
		            liste_agents.setModel(DbUtils.resultSetToTableModel(res));
		            
		           
		            res.close();
		            stat.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(btn_supprimer_agent, "Erreur lors de la mise à jour de la liste des agents.");
		        }
		    
		}

			 private void refreshAgentList1() {
				    
				    String query = "SELECT * FROM AGENT";
				    
				    try {
				        if (c.getConnection() == null || c.getConnection().isClosed()) {
				           
				            c.connectToDatabase();
				        }
				        
				        Statement stat = c.getConnection().createStatement();
				        ResultSet res = stat.executeQuery(query);
				        
				       
				        liste_agents.setModel(DbUtils.resultSetToTableModel(res));
				        
				        
				        res.close();
				        stat.close();
				    } catch (SQLException ex) {
				        ex.printStackTrace();
				        JOptionPane.showMessageDialog(btn_supprimer_agent, "Erreur lors de la mise à jour de la liste des agents.");
				    }
				}
		});
		btn_supprimer_agent.setForeground(new Color(0, 0, 0));
		btn_supprimer_agent.setFont(new Font("Sitka Text", Font.BOLD, 19));
		contentPane.add(btn_supprimer_agent);
		
		JButton btn_modifier_agent = new JButton("Modifier agent");
		btn_modifier_agent.setBounds(657, 90, 218, 40);
		btn_modifier_agent.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        
			        int selectedRow = liste_agents.getSelectedRow();
			        
			        if (selectedRow == -1) {
			            JOptionPane.showMessageDialog(Gerer_agent.this, "Veuillez sélectionner un agent à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
			            return;
			        }
			        
			        
			        String agentID = liste_agents.getValueAt(selectedRow, 0).toString();
			        String agentNom = liste_agents.getValueAt(selectedRow, 1).toString();
			        String agentPrenom = liste_agents.getValueAt(selectedRow, 2).toString();
			        String agentContact = liste_agents.getValueAt(selectedRow, 3).toString();
			        
			        
			        String newNom = JOptionPane.showInputDialog(Gerer_agent.this, "Entrez le nouveau nom de l'agent :", agentNom);
			        String newPrenom = JOptionPane.showInputDialog(Gerer_agent.this, "Entrez le nouveau prénom de l'agent :", agentPrenom);
			        String newContact = JOptionPane.showInputDialog(Gerer_agent.this, "Entrez le nouveau contact de l'agent :", agentContact);
			        
			        
			        String updateQuery = "UPDATE AGENT SET NomA = '" + newNom + "', PrenomA = '" + newPrenom + "', ContactA = '" + newContact + "' WHERE ID_Agent = " + agentID;
			        try {
			            Statement stat = c.getConnection().createStatement();
			            stat.executeUpdate(updateQuery);
			            JOptionPane.showMessageDialog(Gerer_agent.this, "Détails de l'agent mis à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
			            
			            // Refresh the table
			            refreshAgentList();
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			            JOptionPane.showMessageDialog(Gerer_agent.this, "Erreur lors de la mise à jour des détails de l'agent.", "Erreur", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			 private void refreshAgentList() {
				   
				    String query = "SELECT * FROM AGENT";
				    
				    try {
				        if (c.getConnection() == null || c.getConnection().isClosed()) {
				           
				            c.connectToDatabase();
				        }
				        
				        Statement stat = c.getConnection().createStatement();
				        ResultSet res = stat.executeQuery(query);
				        
				      
				        liste_agents.setModel(DbUtils.resultSetToTableModel(res));
				        
				       
				        res.close();
				        stat.close();
				    } catch (SQLException ex) {
				        ex.printStackTrace();
				        JOptionPane.showMessageDialog(btn_supprimer_agent, "Erreur lors de la mise à jour de la liste des agents.");
				    }
				}

		});
		btn_modifier_agent.setForeground(new Color(0, 0, 0));
		btn_modifier_agent.setFont(new Font("Sitka Text", Font.BOLD, 19));
		contentPane.add(btn_modifier_agent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 204, 876, 355);
		contentPane.add(scrollPane);
		
		liste_agents = new JTable();
		scrollPane.setViewportView(liste_agents);
		
		JButton btn_afficher_agents = new JButton("afficher agents");
		btn_afficher_agents.setBackground(new Color(255, 255, 128));
		btn_afficher_agents.setBounds(55, 154, 218, 40);
		btn_afficher_agents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query="SELECT * FROM AGENT";
				try {
					Statement stat = c.getConnection().createStatement();
					ResultSet res=stat.executeQuery(query);
					liste_agents.setModel(DbUtils.resultSetToTableModel(res));

					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_afficher_agents.setForeground(new Color(0, 0, 0));
		btn_afficher_agents.setFont(new Font("Sitka Text", Font.BOLD, 19));
		contentPane.add(btn_afficher_agents);
		
		JButton btn_exit = new JButton("Exit");
		btn_exit.setBackground(new Color(255, 255, 128));
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.closeConnection();
				dispose();
			}
		});
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
		btn_exit.setForeground(new Color(255, 0, 0));
		btn_exit.setBounds(815, 569, 117, 33);
		contentPane.add(btn_exit);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 128));
		panel.setBounds(0, 0, 942, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("  GESTION DES AGENTS");
		lblNewLabel.setBounds(293, 10, 316, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(lblNewLabel);
	}
}
