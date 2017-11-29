package prototype.prototype_jeff;

import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Refund extends Notification{

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

    @Override
    protected void sendNotification(){
        if (!emailList.isEmpty()) {
            sendEmail("Large Refund Detected",
                    "A refund was just issued that exceeded $" + getRefundAmount() + "\n\n" +
                            "If that wasn't you, you may need to look into this.");
        }
        if (!phoneNumberList.isEmpty()) {
            sendMobileText("A refund was just issued that exceeded $"
                    + getRefundAmount() + "\n\n" +
                    "If that wasn't you, you may need to look into this.");
        }
    }
}
