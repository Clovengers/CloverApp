package prototype.prototype_jeff;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Periodic extends Notification {

    private ArrayList<String> emailList = new ArrayList<String>(); // Emails that will get the notifications

    private ArrayList<String> phoneNumberList = new ArrayList<String>(); // Phone numbers that will get the notifications

    private int dayOfWeek; // TODO unsure how time will work, this is a placeholder

    private int numberOfDaysInterval = 7;

    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, int dayOfWeek, int numDays) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setDayOfWeek(dayOfWeek);
        Calendar cal = Calendar.getInstance();
        cal.get(Calendar.DAY_OF_WEEK);

    }

    protected void setDayOfWeek(int dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }

    protected int getDayOfWeek(){
        return dayOfWeek;
    }

    protected void setNumberOfDaysInterval(int interval){
        numberOfDaysInterval = interval;
    }

    protected int getNumberOfDaysInterval(){
        return numberOfDaysInterval;
    }



    @Override
    public String toString(){

        return "";
    }
}
