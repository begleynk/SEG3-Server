package ModelObjects;

/**
 * Created by ahmetersahin on 20/03/2014.
 */
public class Admin {

    private String A_passcode;

    public Admin (String A_passcode){
        this.A_passcode = A_passcode;
    }

    public String getA_passcode() { return A_passcode; }

    public void setA_passcode(String A_passcode) {this.A_passcode = A_passcode;}

    public String toString() {
        return "Passcode: " + getA_passcode();
    }

}
