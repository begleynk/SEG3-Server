package ModelObjects;

/**
 * Created by ahmetersahin on 20/03/2014.
 */
public class Admin {

    private String A_username;
    private String A_password;

    public Admin (String A_username, String A_password){
        this.A_username = A_username;
        this.A_password = A_password;
    }

    public String getA_username (){ return A_username; }

    public void setA_username(String A_username) { this.A_username = A_username;}

    public String getA_password() { return A_password; }

    public void setA_password(String A_password) {this.A_password = A_password;}

    public String toString(){
        return "Username: " + getA_username() +
                "  Password: " + getA_password();
    }

}
