package ModelObjects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Niklas Begley on 27/02/2014.
 *
 */
public class Patient
{
    private String nhsNumber;
    private String first_name;
    private String middle_name;
    private String surname;
    private String dateOfBirth;
    private String postcode;

    // For the purposes of GUI controls
    private StringProperty property_nhs_number;
    private StringProperty property_full_name;
    private BooleanProperty property_is_assigned;
    private Boolean orignal_assignment;

    public Patient(String nhsNumber, String first_name, String middle_name, String surname, String dateOfBirth, String postcode)
    {
        this.nhsNumber = nhsNumber;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.postcode = postcode;

        property_nhs_number = new SimpleStringProperty(nhsNumber);
        property_full_name = new SimpleStringProperty(getFull_name());
        property_is_assigned = new SimpleBooleanProperty(false);
        orignal_assignment = false;
    }


    public String getNhsNumber()
    {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber)
    {
        this.nhsNumber = nhsNumber;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getMiddle_name()
    {
        return middle_name;
    }

    public void setMiddle_name(String middle_name)
    {
        this.middle_name = middle_name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public String getFull_name() {
        if (getMiddle_name().length() > 0) {
            return getFirst_name() + " " + getMiddle_name() + " " + getSurname();
        } else {
            return getFirst_name() + " " + getSurname();
        }
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
        return "NHSNumber: " + getNhsNumber() +
                "  FirstName: " + getFirst_name() +
                "  MiddleName: " + getMiddle_name() +
                "  LastName: " + getSurname() +
                "  DateOfBirth: " + getDateOfBirth() +
                "  Postcode: " + getPostcode()
                + "  isAssigned: " + ((getProperty_is_assigned()) ? "true" : "false");
    }
}