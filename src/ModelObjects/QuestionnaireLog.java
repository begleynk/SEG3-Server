package ModelObjects;

/**
 * Created by ahmetersahin on 16/03/2014.
 */
public class QuestionnaireLog {

    private int qlkey;
    private int Q_id_OLD;
    private int Q_id_NEW;
    private String Q_title_OLD;
    private String Q_title_NEW;
    private String Q_state_OLD;
    private String Q_state_NEW;
    private String SQL_action;
    private String Time_enter;


    public QuestionnaireLog (int qlkey, int Q_id_OLD, int Q_id_NEW, String Q_title_OLD, String Q_title_NEW,
                             String Q_state_OLD, String Q_state_NEW, String SQL_action, String Time_enter){
        this.qlkey = qlkey;
        this.Q_id_OLD = Q_id_OLD;
        this.Q_id_NEW = Q_id_NEW;
        this.Q_title_OLD = Q_title_OLD;
        this.Q_title_NEW = Q_title_NEW;
        this.Q_state_OLD = Q_state_OLD;
        this.Q_state_NEW = Q_state_NEW;
        this.SQL_action = SQL_action;
        this.Time_enter = Time_enter;
    }

    public int getQlkey () {return qlkey;}
    public int getIdOLD () {return Q_id_OLD;}
    public int getIdNEW () {return Q_id_NEW;}
    public String getTitleOLD () {return Q_title_OLD;}
    public String getTitleNEW () {return Q_title_NEW;}
    public String getStateOLD () {return Q_state_OLD;}
    public String getStateNEW () {return Q_state_NEW;}
    public String getSqlAction () {return SQL_action;}
    public String getTimeEnter () {return Time_enter;}

    public String toString (){
        return "Key: " + getQlkey() +
                " Old Questionnaire ID: " +getIdOLD() +
                " New Questionnaire ID: " +getIdNEW() +
                " Old Questionnaire Title: " +getTitleOLD() +
                " New Questionnaire Title: " +getTitleNEW() +
                " Old Questionnaire State: " +getStateOLD() +
                " New Questionnaire State: " +getStateNEW() +
                " Action: " +getSqlAction() +
                " Time: " +getTimeEnter();
    }
}
