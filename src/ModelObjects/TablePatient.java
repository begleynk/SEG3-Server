package ModelObjects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Me on 19/03/2014.
 */
public class TablePatient extends Patient {

    // For the purposes of GUI controls
    private StringProperty property_nhs_number;
    private StringProperty property_full_name;
    private BooleanProperty property_is_assigned;
    private Boolean orignal_assignment;

    public TablePatient(String nhsNumber, String first_name, String middle_name, String surname, String dateOfBirth, String postcode) {
        super(nhsNumber, first_name, middle_name, surname, dateOfBirth, postcode);

        property_nhs_number = new SimpleStringProperty(nhsNumber);
        property_full_name = new SimpleStringProperty(getFull_name());
        property_is_assigned = new SimpleBooleanProperty(false);
        orignal_assignment = false;
    }

    public String getProperty_nhs_number() {
        return property_nhs_number.get();
    }

    public StringProperty property_nhs_numberProperty() {
        return property_nhs_number;
    }

    public void setProperty_nhs_number(String property_nhs_number) {
        this.property_nhs_number.set(property_nhs_number);
    }

    public String getProperty_full_name() {
        return property_full_name.get();
    }

    public StringProperty property_full_nameProperty() {
        return property_full_name;
    }

    public void setProperty_full_name(String property_full_name) {
        this.property_full_name.set(property_full_name);
    }

    public boolean getProperty_is_assigned() {
        return property_is_assigned.get();
    }

    public BooleanProperty property_is_assignedProperty() {
        return property_is_assigned;
    }

    public void setProperty_is_assigned(boolean property_is_assigned) {
        this.property_is_assigned.set(property_is_assigned);
    }

    public Boolean getOrignal_assignment() {
        return orignal_assignment;
    }

    public void setOrignal_assignment(Boolean orignal_assignment) {
        this.orignal_assignment = orignal_assignment;
    }

    @Override
    public String toString() {
        return super.toString() + "  isAssigned: " + ((getProperty_is_assigned()) ? "true" : "false");
    }

}
