package prototype.prototype_jeff;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Periodic extends Notification {

    private int dayOfWeek;

    private int numberOfDaysInterval = 7; //7 would be weekly

    private int daysSince;
    private Calendar calendar;
    private boolean testing = true;



    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, Calendar calendar, int numDays) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setDayOfWeek(dayOfWeek);
        numberOfDaysInterval = numDays;
        daysSince = 0;
        this.calendar = calendar;

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

    protected String message = "Periodc message email";

    protected String phoneMessage = "Periodic message text";

    @Override
    protected void sendNotification(){
        Calendar cal = Calendar.getInstance();
        daysSince = cal.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR);

        if(testing){
            numberOfDaysInterval = -1;
        }

        if (daysSince >= numberOfDaysInterval){
            daysSince =0;
            calendar = cal;
            if (!emailList.isEmpty()) {

                for(String s : emailList){
                    sendEmail(this.getClass().getSimpleName() + " Alert", message, s  );
                    Log.d("EMAIL SENDING TO:", s );
                }



            }
            if (!phoneNumberList.isEmpty()) {

                for(String p : phoneNumberList){
                    sendMobileText(phoneMessage, p);
                }

            }
        }
    }


}
