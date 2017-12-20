package prototype.prototype_jeff;

import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Refund extends Notification{

    private double refundAmount; // Amount that the user wants to be alerted if a refund is equal or over

    private boolean check = false;



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
        message = "A refund was just issued of " + NumberFormat.getCurrencyInstance().format(amount) + ". This exceeds your threshold of "
                + NumberFormat.getCurrencyInstance().format(refundAmount) + "."
                + "\n\n" + "If that wasn't you, you may need to look into this." + MainActivity.RECEIVEDINERROR;

        phoneMessage = "A refund was just issued of $" + NumberFormat.getCurrencyInstance().format(amount) + ". This exceeds your threshold of "
                + NumberFormat.getCurrencyInstance().format(refundAmount) + "."
                + "\n\n" + "If that wasn't you, you may need to look into this.";

        if (!emailList.isEmpty()) {

            for(String s : emailList){
                if(s != null){
                    sendEmail(this.getClass().getSimpleName() + " Alert", message, s  );
                    Log.d("EMAIL SENDING TO:", s );
                }



            }



        }
        if (!phoneNumberList.isEmpty()) {

            for(String p : phoneNumberList){
                if(p != null){
                    sendMobileText(phoneMessage, p);

                }


            }

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
        holder += "AMOUNT: " + -refundAmount;

        return holder;
    }

}
