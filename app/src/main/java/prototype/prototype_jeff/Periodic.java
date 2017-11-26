package prototype.prototype_jeff;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Periodic extends Notification {

    private ArrayList<String> emailList = new ArrayList<String>(); // Emails that will get the notifications

    private ArrayList<String> phoneNumberList = new ArrayList<String>(); // Phone numbers that will get the notifications

    private int dayOfWeek;

    private int numberOfDaysInterval = 7; //7 would be weekly

    private int daysSince;

    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, int dayOfWeek, int numDays) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setDayOfWeek(dayOfWeek);

        daysSince = 0;

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
    protected void sendNotification(){
        Calendar cal = Calendar.getInstance();

        if ( cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek){
            daysSince =0;
            if(emailList.size() >0 ){
                //TODO send email about sales data or something
                //TODO main activity get information from inventory
            }

            if(phoneNumberList.size() >0 ){
                //TODO send text 
            }
        }
    }


}
