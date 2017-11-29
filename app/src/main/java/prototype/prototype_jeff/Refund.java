package prototype.prototype_jeff;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Refund extends Notification{

    private double refundAmount; // Amount that the user wants to be alerted if a refund is equal or over

    protected String message = "A refund was just issued that exceeded $" + refundAmount + "\n\n" +
            "If that wasn't you, you may need to look into this.";

    protected String phoneMessage = "A refund was just issued that exceeded $" + getRefundAmount() + "\n\n" +
            "If that wasn't you, you may need to look into this.";


    public Refund(ArrayList<String> emails, ArrayList<String> phoneNumbers, double amount) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setRefundAmount(amount);
    }

    protected void setRefundAmount(double refund) {
        refundAmount = refund;
    }

    protected double getRefundAmount() {
        return refundAmount;
    }

    protected void sendNotification(){
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
