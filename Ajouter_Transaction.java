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
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Ajouter_Transaction extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField IDTF;
    private JTextField TypeTF;
    private JTextField DetailsTF;
    private JTextField DateTF;
    private JTextField MontantTF;
    private JComboBox<ComboBoxItem> BienIDCB;
    private JComboBox<ComboBoxItem> ClientIDCB;
    private JComboBox<ComboBoxItem> AgentIDCB;
    private Connect c = new Connect();
    private JButton btn_exit;
    private JPanel panel;
    private JLabel lblNewLabel;

    
     
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ajouter_Transaction frame = new Ajouter_Transaction();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creattion frame.
     */
    public Ajouter_Transaction() {
    	setTitle("Ajouter transaction");
        c.connectToDatabase();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 678);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblID = new JLabel("ID Transaction:");
        lblID.setBounds(32, 57, 180, 33);
        lblID.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblID);

        IDTF = new JTextField();
        IDTF.setBounds(222, 57, 294, 41);
        IDTF.setFont(new Font("Tahoma", Font.BOLD, 19));
        IDTF.setColumns(10);
        contentPane.add(IDTF);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(32, 112, 180, 33);
        lblType.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblType);

        TypeTF = new JTextField();
        TypeTF.setBounds(222, 108, 294, 42);
        TypeTF.setFont(new Font("Tahoma", Font.BOLD, 19));
        TypeTF.setColumns(10);
        contentPane.add(TypeTF);

        JLabel lblDetails = new JLabel("Details:");
        lblDetails.setBounds(32, 168, 180, 33);
        lblDetails.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblDetails);

        DetailsTF = new JTextField();
        DetailsTF.setBounds(220, 160, 294, 50);
        DetailsTF.setFont(new Font("Tahoma", Font.BOLD, 19));
        DetailsTF.setColumns(10);
        contentPane.add(DetailsTF);

        JLabel lblDate = new JLabel("Date (YYYY-MM-DD):");
        lblDate.setBounds(34, 239, 209, 33);
        lblDate.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblDate);

        DateTF = new JTextField();
        DateTF.setBounds(253, 235, 263, 41);
        DateTF.setFont(new Font("Tahoma", Font.BOLD, 19));
        DateTF.setColumns(10);
        contentPane.add(DateTF);

        JLabel lblMontant = new JLabel("Montant:");
        lblMontant.setBounds(32, 297, 180, 33);
        lblMontant.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblMontant);

        MontantTF = new JTextField();
        MontantTF.setBounds(222, 297, 294, 33);
        MontantTF.setFont(new Font("Tahoma", Font.BOLD, 19));
        MontantTF.setColumns(10);
        contentPane.add(MontantTF);

        JLabel lblBienID = new JLabel("Bien:");
        lblBienID.setBounds(32, 340, 180, 33);
        lblBienID.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblBienID);

        BienIDCB = new JComboBox<>();
        BienIDCB.setBounds(220, 340, 294, 41);
        BienIDCB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(BienIDCB);

        JLabel lblClientID = new JLabel("Client:");
        lblClientID.setBounds(32, 400, 180, 33);
        lblClientID.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblClientID);

        ClientIDCB = new JComboBox<>();
        ClientIDCB.setBounds(220, 392, 294, 41);
        ClientIDCB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(ClientIDCB);

        JLabel lblAgentID = new JLabel("Agent:");
        lblAgentID.setBounds(32
        		, 460, 180, 33);
        lblAgentID.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(lblAgentID);

        AgentIDCB = new JComboBox<>();
        AgentIDCB.setBounds(220, 452, 294, 41);
        AgentIDCB.setFont(new Font("Tahoma", Font.BOLD, 19));
        contentPane.add(AgentIDCB);

        // Populate JComboBoxes
        populateComboBoxes();

        JButton btn_add_transaction = new JButton("Ajouter Transaction");
        btn_add_transaction.setBackground(new Color(255, 255, 128));
        btn_add_transaction.setBounds(140, 541, 250, 42);
        btn_add_transaction.setForeground(Color.GREEN);
        btn_add_transaction.setFont(new Font("SansSerif", Font.BOLD, 19));
        btn_add_transaction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idTransactionStr = IDTF.getText();
                long idTransaction;
                try {
                    idTransaction = Long.parseLong(idTransactionStr);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid input for transaction ID: " + idTransactionStr);
                    return;
                }

                String type = TypeTF.getText();
                String details = DetailsTF.getText();
                String dateTransaction = DateTF.getText();
                String montantStr = MontantTF.getText();
                float montant;
                try {
                    montant = Float.parseFloat(montantStr);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid input for montant: " + montantStr);
                    return;
                }
                ComboBoxItem bienItem = (ComboBoxItem) BienIDCB.getSelectedItem();
                ComboBoxItem clientItem = (ComboBoxItem) ClientIDCB.getSelectedItem();
                ComboBoxItem agentItem = (ComboBoxItem) AgentIDCB.getSelectedItem();

                long bienId = bienItem.getId();
                long clientId = clientItem.getId();
                long agentId = agentItem.getId();

                String query = "INSERT INTO TRANSACTION(ID_TRANSACTION, TYPE, DETAILS, DATE_TRANSACTION, MONTANT, ID_BIEN, ID_CLIENT, ID_AGENT) " +
                               "VALUES(" + idTransaction + ", '" + type + "', '" + details + "', TO_DATE('" + dateTransaction + "', 'YYYY-MM-DD'), " + montant + ", " + bienId + ", " + clientId + ", " + agentId + ")";
                try {
                    if (c.getConnection() != null) {
                        Statement stat = c.getConnection().createStatement();
                        stat.execute(query);
                        JOptionPane.showMessageDialog(btn_add_transaction, "La transaction a été bien insérée!");
                        c.closeConnection();
                    } else {
                        System.err.println("Connection is not initialized!");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(btn_add_transaction);

        btn_exit = new JButton("Exit");
        btn_exit.setBounds(436, 598, 116, 33);
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btn_exit.setForeground(new Color(255, 0, 0));
        btn_exit.setFont(new Font("Tahoma", Font.BOLD, 19));
        btn_exit.setBackground(new Color(255, 255, 128));
        contentPane.add(btn_exit);
        
        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 128));
        panel.setBounds(0, 0, 586, 41);
        contentPane.add(panel);
        
        lblNewLabel = new JLabel("AJOUTE TRANSACTION ");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(lblNewLabel);
    }

    private void populateComboBoxes() {
        try {
            Statement stat = c.getConnection().createStatement();

            // Populate BienIDCB
            ResultSet rs = stat.executeQuery("SELECT ID_BIENIMMOBILIER, TYPE FROM bienimmobilier");
            while (rs.next()) {
                BienIDCB.addItem(new ComboBoxItem(rs.getLong("ID_BIENIMMOBILIER"), rs.getString("TYPE")));
            }

            // Populate ClientIDCB
            rs = stat.executeQuery("SELECT ID_CLIENT, NOMC || ' ' || PRENOMC AS FULLNAME FROM client");
            while (rs.next()) {
                ClientIDCB.addItem(new ComboBoxItem(rs.getLong("ID_CLIENT"), rs.getString("FULLNAME")));
            }

            // Populate AgentIDCB
            rs = stat.executeQuery("SELECT ID_AGENT, NOMA || ' ' || PRENOMA AS FULLNAME FROM agent");
            while (rs.next()) {
                AgentIDCB.addItem(new ComboBoxItem(rs.getLong("ID_AGENT"), rs.getString("FULLNAME")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
