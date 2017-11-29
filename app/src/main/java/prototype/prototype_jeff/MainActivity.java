package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.order.OrderConnector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MainActivity extends AppCompatActivity {

    static public Account mAccount;
    private OrderConnector mOrderConnector;
    private TextView mTextView;

    static public RefundReceiver refundReceiver;

    private Intent emailInent;

    //Declaring objects in layout
    private Button infoButton;
    private Button helpButton;
    private Button emailButton;
    private Button settingsButton;


    protected ArrayList<Periodic> periodicList = new ArrayList<Periodic>();

    // things that inherit from View that will be shown/hidden by the settings button
    ArrayList<View> settings;

    //Used to create pop up msgs
    AlertDialog.Builder builder;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refundReceiver=new RefundReceiver(this);
        RefundReceiver.orderConnector = new OrderConnector(this, mAccount, null);
        refundReceiver.orderConnector.connect();
        Refund refund1 = new Refund(new ArrayList<String>(), new ArrayList<String>(), 50);
        Refund refund2 = new Refund(new ArrayList<String>(), new ArrayList<String>(), 500);
        refund1.emailList.add("jeffreylehman94@gmail.com");
        refund2.emailList.add("lehmanj3@students.rowan.edu");
        RefundReceiver.refundList.add(refund1);
        RefundReceiver.refundList.add(refund2);

        //periodic Time check once an hour
        timer = new Timer();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {

                if(NotificationWizard.periodicList != null){
                    //send notification method call on each saved periodic
                    ArrayList<Periodic> periodicArrayList = NotificationWizard.periodicList;

                    for(Periodic p: periodicArrayList){
                        p.sendNotification();
                    }
                }

            }
        };

        // schedule the task to run starting now and then every hour...
        timer.schedule (hourlyTask, 0l, 1000*60*60);

        settings = establishSettings();

        //Connecting buttons to their id's
        infoButton = (Button) findViewById(R.id.getInfoButton);
        helpButton = (Button) findViewById(R.id.functioButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        emailButton = (Button) findViewById(R.id.emailButton);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendEmail();
                Periodic test = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance() , 7);
                ArrayList<String> emailTestList = new ArrayList<String>();
                emailTestList.add("First");
                emailTestList.add("Second");
                emailTestList.add("Third");

                for(String s: emailTestList){
                    sendEmail("Test email", s);

                }

            }
        });

        //BEGIN BUTTON
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //switches to a new screen
                startActivity(new Intent(getApplicationContext(), NotificationWizard.class));
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(MainActivity.this).setTitle("App Functionality").setMessage("Information About the app");
                builder.show();


            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationManager.class));

            }
        });
    }

    protected void sendMobileText(String body) {
//        String phoneNum = "1234567890";
        String phoneNum = NotificationWizard.recipientPhoneNumber;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager.getDefault().sendTextMessage(phoneNum, null, body, null, null);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Inadequate permissions", Toast.LENGTH_SHORT).show();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        sendMobileText(mailText);
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
                //String to = NotificationWizard.recipientEmailAddress;

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

    /**
     * all settings will be established here so their visibility can be toggled
     */
    private ArrayList<View> establishSettings() {
        ArrayList<View> list = new ArrayList<>();

        //list.add(findViewById(R.id.setting1CheckBox));

        for(View v: list) {
            v.setVisibility(View.INVISIBLE);
        }

        return list;
    }

    /**
     * toggles visibility of all settings
     */
    private void toggleSettingsVisibility() {
        if (settingsButton.getText().equals("Show Settings")) {
            for (View v : settings) {
                v.setVisibility(View.VISIBLE);
            }
            settingsButton.setText("Hide Settings");
        } else {
            for (View v : settings) {
                v.setVisibility(View.INVISIBLE);
            }
            settingsButton.setText("Show Settings");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUGGER_JEFF", "onResume START");

    }
    @Override
    public void onPause() {
        super.onPause();
        getApplicationContext().registerReceiver(refundReceiver, new IntentFilter("com.clover.intent.action.ORDER_CREATED"));
        mAccount= CloverAccount.getAccount(this);
        Log.d("DEBUGGER_JEFF", "PAUSE START");
    }


    protected void onUpdate(){
        //TODO add in timer for periodic checking of current data

        for(int x =0; x< periodicList.size(); x++){
            periodicList.get(x).sendNotification();
        }


    }


}