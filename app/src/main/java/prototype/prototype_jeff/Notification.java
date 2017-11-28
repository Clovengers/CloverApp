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
 * Created by Brian French on 11/24/2017.
 */

public class Notification extends AppCompatActivity {
    private ArrayList<String> emailList = new ArrayList<String>();

    private ArrayList<String> phoneNumberList = new ArrayList<String>();

    protected void setEmailList(ArrayList<String> list) {
        emailList = list;
    }

    protected void setPhoneNumberList(ArrayList<String> list) {
        phoneNumberList = list;
    }

    protected void addEmail(String email){
        emailList.add(email);
    }

    protected void addPhoneNumber(String phoneNumber){
        phoneNumberList.add(phoneNumber);
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
        Log.d("Notfication", "Notification, email check size" + emailList.size() );
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


    protected void sendMobileText(String body) {
//        String phoneNum = "1234567890";
        String phoneNum = phoneNumberList.get(0);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager.getDefault().sendTextMessage(phoneNum, null, body, null, null);
        } else {
        }
    }

    protected void sendEmail(String mailSubject, String mailText) {
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

        MailSenderTask mailSenderTask = new MailSenderTask(session, mailSubject, mailText);
        try {
            String holder = mailSenderTask.execute().get();
        }catch (InterruptedException e) {
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

        public MailSenderTask(Session session, String subject, String text) {
            mailSession = session;
            mailSubject = subject;
            mailText = text;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String from = "SeniorProjectClover@gmail.com";
                String to = "SeniorProjectClover@gmail.com";
                //String to = emailList.get(0);

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
