package Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Faizan Joya on 22/03/14.
 * SEG3-Server
 */
public class DateCheckHelper {
    final static String DATE_FORMAT = "dd-MM-yyyy";

    public static boolean isDateValid(String day, String month, String year){
        String date = day + "-" + month + "-" + year;
        try {
            DateFormat format = new SimpleDateFormat(DATE_FORMAT);
            format.setLenient(false);
            format.parse(date);
            return true;
        } catch (ParseException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public static String checkDMY(String d, String m, String y){
        String to_return="";
        if (d.length() != 2 || !d.matches("^\\d{2}$")) {
            to_return += "DoB day needs to be exactly 2 digits \n";
        }

        if (m.length() != 2 || !m.matches("^\\d{2}$")) {
            to_return += "DoB month needs to be exactly 2 digits \n";
        }

        if (y.length() != 4 || !y.matches("^\\d{4}$")) {
            to_return += "DoB year needs to be exactly 4 digits \n";
        }
        return to_return;
    }
}



