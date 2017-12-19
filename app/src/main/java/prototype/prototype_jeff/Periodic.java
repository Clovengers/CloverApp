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
    private long numberOfMinutesInterval = 1; // 60 would be hourly

    private int daysSince;
    private long timeSince;
    private Calendar calendar;
    private boolean testing = false;
    private double salesAmount;


    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, Calendar calendar, int numDays) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setDayOfWeek(dayOfWeek);
        numberOfMinutesInterval = numDays*24*60;
        daysSince = 0;
        this.calendar = calendar;
        salesAmount = MainActivity.totalSales;
    }

    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, Calendar calendar, int numDays, long numMinutes) {
        this(emails, phoneNumbers, calendar, numDays);
        numberOfMinutesInterval = numMinutes;
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

//    protected String message = "Periodic message email";
//
//    protected String phoneMessage = "Periodic message text";

    @Override
    protected void sendNotification(){
        Calendar cal = Calendar.getInstance();
        if(testing){
            numberOfDaysInterval = -1;
        }


        timeSince = cal.getTimeInMillis() - calendar.getTimeInMillis();
        if (timeSince >= numberOfMinutesInterval * 60 * 1000) {

            daysSince = 0;
            timeSince = 0;
            calendar = cal;
            if (!emailList.isEmpty()) {

                message = "EMPTY";
                for (String s : emailList) {
                    sendEmail(this.getClass().getSimpleName() + " Alert", message, s);
                    Log.d("EMAIL SENDING TO:", s);
                }


            }
            if (!phoneNumberList.isEmpty()) {

                phoneMessage = "Sales data total $" + (MainActivity.totalSales / 100.0);
                for (String p : phoneNumberList) {
                    sendMobileText(phoneMessage, p);
                }

            }
        } else {
            Log.d("Hourly testing", "TimeSince:" + timeSince
                    + " NumberOfMinutesIntervalScaled:" + (numberOfMinutesInterval * 60 * 1000));
        }

    }

    @Override
    public String toString(){
        String holder = getClass().getSimpleName() + " \n";
        Log.d("Notfication", "Notification, email check size" + emailList.size() );
        if(emailList.size()>0){
            holder += "EMAIL: \n";
            for(String s : emailList){
                if(s != null){
                    holder += s + "\n";
                }
            }
        }

        if (phoneNumberList.size() > 0) {
            holder += "PHONE NUMBER: \n";

            for(String s : phoneNumberList){
                if(s != null){
                    holder += s + "\n";
                }

            }
            /**
             for (int x = 0; x < phoneNumberList.size(); x++) {
             holder += phoneNumberList.get(x) + "\n";
             }
             **/
        }

        holder += "TIME: " + numberOfDaysInterval + " days " + numberOfMinutesInterval + " minutes";

        return holder;
    }

    protected Calendar getCalendar(){
        return calendar;
    }

}
