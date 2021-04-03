import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner; 

public class ATM{

    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable() {
        String url = "jdbc:sqlite:C://sqlite/db/ATM.db";
                String users_table = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	pin integer,\n"
                + "	firstName text NOT NULL,\n"
                + "	lastName text NOT NULL,\n"
                + "	isAdmin integer,\n"
                + "	balance float \n"
                + ");";
                String transactions_table = "CREATE TABLE IF NOT EXISTS transactions (\n"
                + "	id integer,\n"
                + "	type integer,\n"
                + "	amount float \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(users_table);
            stmt.execute(transactions_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    public static void menu(int isAdmin) {
    	System.out.print("1. Transaction History\n");
		System.out.print("2. Withdraw\n");
		System.out.print("3. Diposit\n");
		System.out.print("4. Transfer\n");
		System.out.print("5. Quit\n");
    	if(isAdmin != 0) {
    		System.out.print("6. Create New User (Admin Privileges) \n");}

    }
    
    
    	
   
    
    
    public static void main(String[] args) {
    	createNewDatabase("ATM.db");
    	createNewTable();
    	Insertion insertClass = new Insertion();    	
    	Selection selectClass = new Selection();
    	Updating updateClass = new Updating();
    	if (selectClass.selectUser(1) == null) {
        	insertClass.insertUser(123, "John", "Doe", 1, 100);
    	}
	
    	while(true) {
        	Scanner scanObj = new Scanner(System.in);  
            System.out.println("Enter ID:  ");
            int id = scanObj.nextInt();
            User currentUser = selectClass.selectUser(id);
            int choice;
    		float amount;
    		int transferID;
            if (currentUser != null) {
                System.out.println("Enter PIN: ");
                int pin = scanObj.nextInt();
                if(pin == currentUser.getPin()){
                	do {           
                		currentUser = selectClass.selectUser(id);
                		System.out.println("\n");
                		System.out.println("Current User: " + currentUser.getFirstName() + " " + currentUser.getLastName());
                        System.out.println("Current balance: " + String.valueOf(currentUser.getBalance()) + "\n");
                		menu(currentUser.isAdmin());
                        System.out.println("Enter your choice: ");
                        choice = scanObj.nextInt();
                        switch(choice) {
                        	case 1:
                        		//Transaction
                        		selectClass.selectTransaction(id);
                        		break;
                        	case 2:
                        		//Withdraw
                                System.out.println("Enter the amount to withdraw!");
                                amount = scanObj.nextFloat(); 
                                if (amount <= currentUser.getBalance() && amount >0) {
                            		insertClass.insertTransaction(id, 0, -1 * amount);
                            		updateClass.update(id, currentUser.getBalance() - amount);
                                    System.out.println("Transaction Completed!");
                                }else {
                                    System.out.println("Error!");
                                }
                        		break;
                        	case 3:
                        		//Diposit
                                System.out.println("Enter the amount to Diposit!");
                                amount = scanObj.nextFloat(); 
                                if (amount > 0) {
                            		insertClass.insertTransaction(id, 1, amount);
                            		updateClass.update(id, currentUser.getBalance() + amount);
                                    System.out.println("Transaction Completed!");
                                }
                        		break;
                        	case 4:
                        		//Transfer
                        		System.out.println("Enter the amount to Transfer!");
                                amount = scanObj.nextFloat(); 
                                if (amount <= currentUser.getBalance() && amount > 0) {
                            		System.out.println("Enter the ID of the other user!");
                                    transferID = scanObj.nextInt(); 
                                    User transferUser = selectClass.selectUser(transferID);
                                	if(transferUser != null) {
                                		insertClass.insertTransaction(id, 2, -1 * amount);
                                		insertClass.insertTransaction(transferID, 2, amount);
                                		updateClass.update(id, currentUser.getBalance() - amount);
                                		updateClass.update(transferID, transferUser.getBalance() + amount);
                                        System.out.println("Transaction Completed!");
                                	}else {
                                    	System.out.print("There's no user with this ID!");
                                	}
                                	
                                }else {
                                    System.out.println("Error!");
                                }
                        		break;
                        	case 6:
                        		//Check admin
                        		if(currentUser.isAdmin() != 0) {
                        			String newFirstName;
                        			String newLastName;
                        			float newBalance;
                        			int newPin;
                        			int newIsAdmin;
                        			int newID;
                        	    	System.out.print("Enter The First Name Of The New User");
                        	    	newFirstName = scanObj.next();
                        	    	System.out.print("Enter The Last Name Of The New User");
                        	    	newLastName = scanObj.next();
                        	    	System.out.print("Enter The Balance Of The New User");
                        	    	newBalance = scanObj.nextFloat();
                        	    	System.out.print("Enter The PIN Of The New User");
                        	    	newPin = scanObj.nextInt();
                        	    	System.out.print("Enter The Admin Status Of The New User (0 For Not Admin Or 1 For Admin)");
                        	    	newIsAdmin = scanObj.nextInt();
                                	insertClass.insertUser(newPin, newFirstName, newLastName, newIsAdmin, newBalance);
                                	newID = selectClass.getNewID();
                                	System.out.print("User Added With An ID: " + String.valueOf(newID));

                        		}else {
                                	System.out.print("Please Enter A Valid Choice!");
                        		}
                        		break;
                        	case 5:
                        		break;
                        	default:
                            	System.out.print("Please Enter A Valid Choice!");
                        		break;
                      }
                        	
                		
                	}while(choice != 5);

                }else {
                	System.out.print("Wrong PIN!\n");
                }
            }else {
            	System.out.print("There's no user with this ID!\n");
            }
    		
    	}

       

   
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	

    }
}