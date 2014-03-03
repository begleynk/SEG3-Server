package ModelObjects;

import java.util.Date;

/**
 * Created by NiklasBegley on 27/02/2014.
 */
public class Patient
{
    private String nhsNumber;
    private String first_name;
    private String middle_name;
    private String surname;
    private Date dateOfBirth;
    private String postcode;
    private String disability;

    public Patient(String nhsNumber, String first_name, String middle_name, String surname, Date dateOfBirth, String postcode, String disability)
    {
        this.nhsNumber = nhsNumber;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.postcode = postcode;
        this.disability = disability;
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

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
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

    public String getDisability()
    {
        return disability;
    }

    public void setDisability(String disability)
    {
        this.disability = disability;
    }
}