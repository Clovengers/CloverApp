package prototype.prototype_jeff;

import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Periodic {

    private ArrayList<String> emailList = new ArrayList<String>(); // Emails that will get the notifications

    private ArrayList<String> phoneNumberList = new ArrayList<String>(); // Phone numbers that will get the notifications

    private int timeToWait; // TODO unsure how time will work, this is a placeholder

    public Periodic(ArrayList<String> emails, ArrayList<String> phoneNumbers, int time) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setTimeToWait(time);
    }


    public void setEmailList(ArrayList<String> list) {
        emailList = list;
    }

    public void setPhoneNumberList(ArrayList<String> list) {
        phoneNumberList = list;
    }

    public void setTimeToWait(int time) {
        timeToWait = time;
    }

    public ArrayList<String> getEmailList() {
        return emailList;
    }

    public ArrayList<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    public int getTimeToWait(){
        return timeToWait;
    }
}
