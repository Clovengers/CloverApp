package prototype.prototype_jeff;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Refund extends Notification{

    private double refundAmount; // Amount that the user wants to be alerted if a refund is equal or over




    public Refund(ArrayList<String> emails, ArrayList<String> phoneNumbers, double amount) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        //setRefundAmount(amount);
        this.refundAmount = amount;
        Log.d("erfund amount:", "" + refundAmount );

    }

    protected void setRefundAmount(double amount) {

        this.refundAmount = amount;
    }

    protected double getRefundAmount() {
        return refundAmount;
    }

    protected void sendNotification(double amount){
        message = "A refund was just issued of $" + amount + " this exceeds your threshold of $" + refundAmount
                + "\n\n" + "If that wasn't you, you may need to look into this.";

        phoneMessage = "A refund was just issued of $" + amount + " this exceeds your threshold of $" + refundAmount
                + "\n\n" + "If that wasn't you, you may need to look into this.";

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
        holder += "AMOUNT: " + refundAmount;

        return holder;
    }

}
