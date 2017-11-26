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

    protected void sendNotification(){

    }

    @Override
    public String toString(){
        String holder = getClass().getSimpleName() + " \n";
        if(emailList.size()>0){
            holder += "EMAIL: \n";
            for(int x=0; x< emailList.size(); x++){
                holder += emailList.get(x) + "\n";
            }
        }

        if(phoneNumberList.size()>0){
            holder += "PHONE NUMBER: \n";
            for(int x=0; x< phoneNumberList.size(); x++){
                holder += phoneNumberList.get(x) + "\n";
            }
        }

        return holder;
    }


}
