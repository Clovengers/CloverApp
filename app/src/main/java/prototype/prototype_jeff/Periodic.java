package prototype.prototype_jeff;

import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Periodic extends Notification {

    private int dayOfWeek;

    private int numberOfDaysInterval; //7 would be weekly

    protected long numberOfMinutesInterval = 1; // 60 would be hourly

    private int daysSince;
    private long timeSince;
    private Calendar calendar;
    private boolean testing = false;
    private double salesAmount;
    private boolean check = false;

    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, Calendar calendar, int numDays) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setDayOfWeek(dayOfWeek);
        //numberOfMinutesInterval = numDays*24*60;
        daysSince = 0;
        this.calendar = calendar;
        salesAmount = 0;
    }

    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, Calendar calendar, long numMinutes) {
        this(emails, phoneNumbers, calendar, 0);
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

    public long getNumberOfMinutesInterval() { return numberOfMinutesInterval; }

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
                message = "Sales data total: " + NumberFormat.getCurrencyInstance().format((MainActivity.totalSales - salesAmount) / 100.0);
                for (String s : emailList) {
                    if( s != null){
                        sendEmail(this.getClass().getSimpleName() + " Alert", message, s);
                        //salesAmount = MainActivity.totalSales;

                        Log.d("EMAIL SENDING TO:", s);
                    }

                }



            }
            if (!phoneNumberList.isEmpty()) {

                phoneMessage = "Sales data total: " + NumberFormat.getCurrencyInstance().format((MainActivity.totalSales-  salesAmount) / 100.0);
                for (String p : phoneNumberList) {
                    if( p != null){
                        sendMobileText(phoneMessage, p);
                        //salesAmount = MainActivity.totalSales;
                            Log.d("Kill me", "please"+ salesAmount);
                        salesAmount=0;

                    }
                }
            }
        } else {
            Log.d("Hourly testing", "TimeSince:" + timeSince
                    + " NumberOfMinutesIntervalScaled:" + (numberOfMinutesInterval * 60 * 1000));
        }

    }

    @Override
    public String toString(){
        check = false;
        String holder = getClass().getSimpleName() + " \n";
        Log.d("Notfication", "Notification, email check size" + emailList.size() );
        if(emailList.size()>0){
            for(String s : emailList){
                if(s != null){
                    if( !check){
                        holder += "EMAIL: \n";
                        check = true;
                    }
                    holder += s + "\n";
                }
            }
        }

        if (phoneNumberList.size() > 0) {

            for(String s : phoneNumberList){
                if(s != null){
                    if( !check){
                        holder += "PHONE NUMBER: \n";
                        check = true;
                    }
                    holder += s + "\n";
                }

            }
        }

        holder += "TIME: " + numberOfDaysInterval + " days " + numberOfMinutesInterval + " minutes";

        return holder;
    }

    protected Calendar getCalendar(){
        return calendar;
    }

    protected void addSale(double sale){
        salesAmount += sale;
    }
}
