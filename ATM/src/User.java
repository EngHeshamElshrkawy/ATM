import java.util.*;
class User{
    private String firstName;
    private String lastName;
    private int pin;
    private float balance;
    private int isAdmin;
    public User(String firstName,String lastName,int pin, float balance, int isAdmin){
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    public int getPin(){
    	return this.pin;
    }
    
    
    public int isAdmin(){
        return this.isAdmin;
    }


   public String getFirstName(){
    return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }


    public String Diposit(float amount){
        return "Success!";
    }

    public String Withdraw(float amount){
        return "Success!";
    }


    public float getBalance(){
        return this.balance;
    }

    

}
