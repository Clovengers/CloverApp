package prototype.prototype_jeff;

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

    protected String message = "Generic Notification Alert";
    protected int type;

    protected Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, Calendar calendar, int numDays, int type) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setDayOfWeek(dayOfWeek);
        numberOfDaysInterval = numDays;
        daysSince = 0;
        this.calendar = calendar;
        this.type = type;


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
        //type 1 = inventory
        //type 2 = sales data

        if(type == 1){
            message = "Your periodic inventory alert";
        }
        if(type == 2){
            message = "Your periodic sales alert";
        }



        Calendar cal = Calendar.getInstance();
        daysSince = cal.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR);
        if (daysSince >= numberOfDaysInterval){
            daysSince =0;
            calendar = cal;
            if(emailList.size() >0 ){
                //TODO send email about sales data or something
                //TODO main activity get information from inventory
                for(String s : emailList){
                    sendEmail("Periodic Alert", "The body of the email", s );

                }

            }

            if(phoneNumberList.size() >0 ){
                //TODO send text
                sendMobileText("Periodic Test");
            }
        }
    }


}
