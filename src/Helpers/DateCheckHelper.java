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
}



