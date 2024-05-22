package appli_poo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
    private String username = "c##adminAgence"; 
    private String password = "123"; 
    private Connection conn; 

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void connectToDatabase() {
        // connection
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection avec succées !");
        } catch (SQLException e) {
            System.err.println("erreur a la connexion !");
            e.printStackTrace();
        }
    }

    // Getter et setters

    // Method connection
    public Connection getConnection() {
        return conn;
    }

    // Method déconnection
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("déconnexion avec succées !");
            }
        } catch (SQLException e) {
            System.err.println("erreur  a la déconnexion ");
            e.printStackTrace();
        }
    }
}
