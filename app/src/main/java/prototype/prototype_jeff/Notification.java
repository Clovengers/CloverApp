package prototype.prototype_jeff;

import java.util.ArrayList;

/**
 * Created by Brian French on 11/24/2017.
 */

public class Notification {
    private ArrayList<String> emailList;

    private ArrayList<String> phoneNumberList;

    protected void setEmailList(ArrayList<String> list) {
        emailList = list;
    }

    protected void setPhoneNumberList(ArrayList<String> list) {
        phoneNumberList = list;
    }

    protected ArrayList<String> getEmailList() {
        return emailList;
    }

    protected ArrayList<String> getPhoneNumberList() {
        return phoneNumberList;
    }


}
