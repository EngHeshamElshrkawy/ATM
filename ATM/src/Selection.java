import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Selection {


    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/ATM.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public User selectUser(int id){
    	String sql;
            sql = "SELECT * FROM users WHERE id == " + String.valueOf(id);
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             
        	 User selectedUser = new User(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("pin"), rs.getFloat("balance"), rs.getInt("isAdmin"));
        	 return selectedUser;
        } catch (SQLException e) {
            return null;
        }
        
    }
    

    public int getNewID(){
    	String sql;
    		sql = "SELECT * FROM users ORDER BY id DESC LIMIT 1";
    	
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             return rs.getInt("id");
        } catch (SQLException e) {
            return 0;
        }
        
    }
    
    public void selectTransaction(int id){
        String sql = "SELECT * FROM transactions WHERE id == " + String.valueOf(id) ;
    	String direction = "";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
        	System.out.println("ID" + "\t" + "Operation Type" + "\t" + "Amount");
        	System.out.println("--" + "\t" + "--------------" + "\t" + "------");
            while (rs.next()) {
            	switch(rs.getInt("type")) {
            	case 0:
            		direction = "Withdraw";
            		break;
            	case 1:
            		direction = "Diposit";
            		break;
            	case 2:
            		direction = "Transfer";
            		break;
 
            	
            	
            	}

                System.out.println(rs.getInt("id") +  "\t  " + direction + "\t" + rs.getFloat("amount"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}