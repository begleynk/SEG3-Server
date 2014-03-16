package ModelObjects;

/**
 * Created by ahmetersahin on 16/03/2014.
 */
public class PatientLog {
    private int plkey;
    private String P_NHS_number_OLD;
    private String P_NHS_number_NEW;
    private String P_first_name_OLD;
    private String P_first_name_NEW;
    private String P_middle_name_OLD;
    private String P_middle_name_NEW;
    private String P_surname_OLD;
    private String P_surname_NEW;
    private String P_date_of_birth_OLD;
    private String P_date_of_birth_NEW;
    private String P_postcode_OLD;
    private String P_postcode_NEW;
    private String SQL_action;
    private String Time_enter;


    public PatientLog (int plkey, String P_NHS_number_OLD, String P_NHS_number_NEW, String P_first_name_OLD, String P_first_name_NEW,
                       String P_middle_name_OLD, String P_middle_name_NEW, String P_surname_OLD, String P_surname_NEW,
                       String P_date_of_birth_OLD, String P_date_of_birth_NEW, String P_postcode_OLD, String P_postcode_NEW,
                       String SQL_action, String Time_enter) {
        this.plkey = plkey;
        this.P_NHS_number_OLD = P_NHS_number_OLD;
        this.P_NHS_number_NEW = P_NHS_number_NEW;
        this.P_first_name_OLD = P_first_name_OLD;
        this.P_first_name_NEW = P_first_name_NEW;
        this.P_middle_name_OLD = P_middle_name_OLD;
        this.P_middle_name_NEW = P_middle_name_NEW;
        this.P_surname_OLD = P_surname_OLD;
        this.P_surname_NEW = P_surname_NEW;
        this.P_date_of_birth_OLD = P_date_of_birth_OLD;
        this.P_date_of_birth_NEW = P_date_of_birth_NEW;
        this.P_postcode_OLD = P_postcode_OLD;
        this.P_postcode_NEW = P_postcode_NEW;
        this.SQL_action = SQL_action;
        this.Time_enter = Time_enter;

    }

    public int getPlkey (){ return plkey; }
    public String getNhsNumberOLD () {return P_NHS_number_OLD;}
    public String getNhsNumberNEW () {return P_NHS_number_NEW;}
    public String getFirstNameOLD () {return P_first_name_OLD;}
    public String getFirstNameNEW () {return P_first_name_NEW;}
    public String getMiddleNameOLD () {return P_middle_name_OLD;}
    public String getMiddleNameNEW () {return P_middle_name_NEW;}
    public String getSurnameOLD () {return P_surname_OLD;}
    public String getSurnameNEW () {return P_surname_NEW;}
    public String getDateOfBirthOLD () {return P_date_of_birth_OLD;}
    public String getDateOfBirthNEW () {return P_date_of_birth_NEW;}
    public String getPostcodeOLD () {return P_postcode_OLD;}
    public String getPostcodeNEW () {return P_postcode_NEW;}
    public String getSqlAction () {return SQL_action;}
    public String getTimeEnter () {return Time_enter;}





    public String toString() {
        return "Key: " + getPlkey() +
                " Old NHS No: " +getNhsNumberOLD() +
                " New NHS No: " +getNhsNumberNEW() +
                " Old First Name: " +getFirstNameOLD() +
                " New First Name: " +getFirstNameNEW() +
                " Old Middle Name: " +getMiddleNameOLD() +
                " New Middle NAme: " +getMiddleNameNEW() +
                " Old Surname: " +getSurnameOLD() +
                " New Surname: " +getSurnameNEW() +
                " Old DOB: " +getDateOfBirthOLD() +
                " New DOB: " +getDateOfBirthNEW() +
                " Old Postcode: " +getPostcodeOLD() +
                " New Postcode: " +getPostcodeNEW() +
                " Action: " +getSqlAction() +
                " Time: " +getTimeEnter();
    }
}
