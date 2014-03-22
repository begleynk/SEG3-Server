package ModelObjects;

/**
 * Created by ahmetersahin on 16/03/2014.
 */
public class PatientQuestionnaireLog {
    private int pqlkey;
    private String P_NHS_number_OLD;
    private String P_NHS_number_NEW;
    private String Q_id_OLD;
    private String Q_id_NEW;
    private String Completed_OLD;
    private String Completed_NEW;

    private String SQL_action;
    private String Time_enter;


    public PatientQuestionnaireLog(int pqlkey, String P_NHS_number_OLD, String P_NHS_number_NEW, String Q_id_OLD, String Q_id_NEW,
                                   String Completed_OLD, String Completed_NEW, String SQL_action, String Time_enter) {
        this.pqlkey = pqlkey;
        this.P_NHS_number_OLD = P_NHS_number_OLD;
        this.P_NHS_number_NEW = P_NHS_number_NEW;
        this.Q_id_OLD = Q_id_OLD;
        this.Q_id_NEW = Q_id_NEW;
        this.Completed_OLD = Completed_OLD;
        this.Completed_NEW = Completed_NEW;
        this.SQL_action = SQL_action;
        this.Time_enter = Time_enter;

    }

    public int getPqlkey (){ return pqlkey; }
    public String getNhsNumberOLD () {return P_NHS_number_OLD;}
    public String getNhsNumberNEW () {return P_NHS_number_NEW;}
    public String getQidOLD () { return Q_id_OLD; }
    public String getQidNEW () { return Q_id_NEW; }
    public String getCompletedOLD () { return Completed_OLD; }
    public String getCompletedNEW () { return Completed_NEW; }
    public String getSqlAction () {return SQL_action;}
    public String getTimeEnter () {return Time_enter;}





    public String toString() {
        return "Key: " + getPqlkey() +
                "   Old NHS No: " +getNhsNumberOLD() +
                "   New NHS No: " +getNhsNumberNEW() +
                "   OLD Questionnaire ID: " + getQidOLD() +
                "   NEW Questionnaire ID: " + getQidNEW() +
                "   OLD Completed: " + getCompletedOLD() +
                "   NEW Completed: " + getCompletedNEW() +
                "   Action: " +getSqlAction() +
                "   Time: " +getTimeEnter();
    }
}
