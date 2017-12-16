package prototype.prototype_jeff;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Abstract: A class which will act as a Super to other notification classes due to their
 * similarities
 * <p>
 * Updated: 1 December 2017
 */

public class Notification extends AppCompatActivity {

    protected ArrayList<String> emailList = new ArrayList<String>();

    protected ArrayList<String> phoneNumberList = new ArrayList<String>();

    protected void setEmailList(ArrayList<String> list) {
        emailList = list;
    }

    protected void setPhoneNumberList(ArrayList<String> list) {
        phoneNumberList = list;
    }

    protected void addEmail(String email) {
        emailList.add(email);
    }

    protected void addPhoneNumber(String phoneNumber) {
        phoneNumberList.add(phoneNumber);
    }

    protected ArrayList<String> getEmailList() {
        return emailList;
    }

    protected ArrayList<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    private String emailToBeSent;
    protected String message = "Generic Notification Alert";
    protected String phoneMessage = "Generic Phone Notification Alert";

    protected void sendNotification() {
        if (!emailList.isEmpty()) {

            for (String s : emailList) {
                sendEmail(this.getClass().getSimpleName() + " Alert", message, s);
                Log.d("EMAIL SENDING TO:", s);
            }


        }
        if (!phoneNumberList.isEmpty()) {

            for (String p : phoneNumberList) {
                sendMobileText(phoneMessage, p);
            }

        }
    }

    @Override
    public String toString() {
        String holder = getClass().getSimpleName() + " \n";
        Log.d("Notfication", "Notification, email check size" + emailList.size());
        if (emailList.size() > 0) {
            holder += "EMAIL: \n";
            for (int x = 0; x < emailList.size(); x++) {
                holder += emailList.get(x) + "\n";
            }
        }

        if (phoneNumberList.size() > 0) {
            holder += "PHONE NUMBER: \n";
            for (int x = 0; x < phoneNumberList.size(); x++) {
                holder += phoneNumberList.get(x) + "\n";
            }
        }

        return holder;
    }


    protected void sendMobileText(String body, String phoneNum) {
        ArrayList<String> phoneCarriers = new ArrayList<>();
        phoneCarriers.add("@txt.att.net");
        phoneCarriers.add("@tmomail.net");
        phoneCarriers.add("@vtext.com");
        phoneCarriers.add("@messaging.sprintpcs.com");
        phoneCarriers.add("@pm.sprint.com");
        phoneCarriers.add("@vmobl.com");
        phoneCarriers.add("@mmst5.tracfone.com");
        phoneCarriers.add("@mymetropcs.com");
        phoneCarriers.add("@myboostmobile.com");
        phoneCarriers.add("@mms.cricketwireless.net");
        phoneCarriers.add("@ptel.com");
        phoneCarriers.add("@text.republicwireless.com");
        phoneCarriers.add("@msg.fi.google.com");
        phoneCarriers.add("@tms.suncom.com");
        phoneCarriers.add("@message.ting.com");
        phoneCarriers.add("@email.uscc.net");
        phoneCarriers.add("@cingularme.com");
        phoneCarriers.add("@cspire1.com");

        String host = "smtp.gmail.com";
        final String user = "SeniorProjectClover@gmail.com";
        final String pass = "S3n10rPr0j3ct";


        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        for (int i = 0; i < phoneCarriers.size(); i++) {
            emailToBeSent = phoneNum + phoneCarriers.get(i);
            MailSenderTask mailSenderTask = new MailSenderTask(session, getClass().getSimpleName(), body, emailToBeSent);
            try {
                String holder = mailSenderTask.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    protected void sendEmail(String mailSubject, String mailText, String address) {
        String host = "smtp.gmail.com";
        final String user = "SeniorProjectClover@gmail.com";
        final String pass = "S3n10rPr0j3ct";
        emailToBeSent = address;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        MailSenderTask mailSenderTask = new MailSenderTask(session, mailSubject, mailText + MainActivity.RECEIVEDINERROR, emailToBeSent);
        try {
            String holder = mailSenderTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //sendMobileText(mailText);
    }

    /**
     * This class exists just to send an email
     */
    private class MailSenderTask extends AsyncTask<String, Void, String> {

        Session mailSession;
        String mailSubject;
        String mailText;
        String mailDestination;

        public MailSenderTask(Session session, String subject, String text, String destination) {
            mailSession = session;
            mailSubject = subject;
            mailText = text;
            mailDestination = destination;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String from = "SeniorProjectClover@gmail.com";
//                String to = "SeniorProjectClover@gmail.com";
                String to = mailDestination;

                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                msg.setSubject(mailSubject);
                msg.setText(mailText);

                Transport.send(msg);
            } catch (MessagingException e) {
                System.err.println(e);
            }

            return null;
        }

    }
}
