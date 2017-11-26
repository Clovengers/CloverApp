package prototype.prototype_jeff;

import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Refund extends Notification{
    public ArrayList<String> emailList = new ArrayList<String>(); // Emails that will get the notifications

    public ArrayList<String> phoneNumberList = new ArrayList<String>(); // Phone numbers that will get the notifications

    private double refundAmount; // Amount that the user wants to be alerted if a refund is equal or over

    public Refund(ArrayList<String> emails, ArrayList<String> phoneNumbers, double amount) {
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setRefundAmount(amount);
    }

    public void setEmailList(ArrayList<String> list) {
        emailList = list;
    }

    public void setPhoneNumberList(ArrayList<String> list) {
        phoneNumberList = list;
    }

    public void setRefundAmount(double refund) {
        refundAmount = refund;
    }

    public ArrayList<String> getEmailList() {
        return emailList;
    }

    public ArrayList<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    public double getRefundAmount() {
        return refundAmount;
    }
}
