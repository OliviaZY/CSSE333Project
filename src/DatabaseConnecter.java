import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 
 * Creates a connection to the MySQL database that holds all of MeetMe's info
 *
 * @author garretje.
 *         Created Apr 26, 2018.
 */
public class DatabaseConnecter {
	//URL of our database is currently hardcoded in
	private final String DB_URL = "jdbc:mysql://zhou3306.csse.rose-hulman.edu/meetme?autoReconnect=true&useSSL=false";
	private Connection con;

	public DatabaseConnecter(){
		try{
			con = DriverManager.getConnection(DB_URL, "root", "chooKah7");
		}
		catch(SQLException s){
			System.out.println("Exception thrown in Database Connecter");
			
		}
		
	}
	/**
	 * 
	 *Returns the created database connection
	 *
	 * @return the connection to the database
	 */
	public Connection getConnection(){
		return con;
	}
	public void closeConnection(){
		try {
			con.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
}
