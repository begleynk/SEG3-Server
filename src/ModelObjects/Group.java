package ModelObjects;

/**
 * Created by ahmetersahin on 24/03/2014.
 */
public class Group {
    private String P_group;

    public Group (String P_group){
        this.P_group = P_group;
    }
    public String getPatientGroup() { return P_group; }

    public String toString(){
        return "Patient Group: " + getPatientGroup();
    }
}