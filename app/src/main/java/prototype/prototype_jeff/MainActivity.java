package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/*
 * Astract: The first activity to run when the app is started
 * This creates all required parts of the application including layout functionality
 *
 * Updated: 1 December 2017
 */


public class MainActivity extends AppCompatActivity {

    static public Account mAccount;
    private OrderConnector mOrderConnector;
    private TextView mTextView;

    static public RefundReceiver refundReceiver;
    static public MainActivity mainActivity;

    private Intent emailInent;

    //Declaring objects in layout
    private Button infoButton;
    private Button helpButton;
    private Button emailButton;
    private Button settingsButton;
    private boolean demo = true;
    private String inputText = "";


    protected ArrayList<Periodic> periodicList = new ArrayList<Periodic>();

    protected static double totalSales = 0;


    // things that inherit from View that will be shown/hidden by the settings button
    ArrayList<View> settings;

    //Used to create pop up msgs
    AlertDialog.Builder builder;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.mainActivity = this;
        setContentView(R.layout.activity_main);
        refundReceiver = new RefundReceiver();
        RefundReceiver.orderConnector = new OrderConnector(this, mAccount, null);
        refundReceiver.orderConnector.connect();

        timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("Sales data testing", "" + totalSales);

                if (NotificationWizard.periodicList != null) {
                    //send notification method call on each saved periodic
                    ArrayList<Periodic> periodicArrayList = NotificationWizard.periodicList;

                    for (Periodic p : periodicArrayList) {
                        p.sendNotification();
                    }

                }

            }
        };

        if(demo){
            // schedule the task to run starting now and then every minute...
            //TODO multiple by 60
            timer.schedule(hourlyTask, 0l,  1000*60);
        }else{
            // schedule the task to run starting now and then every hour...
            timer.schedule(hourlyTask, 0l, 1000 * 60 * 60);
        }


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
                Periodic test = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance(), 7);
                ArrayList<String> emailTestList = new ArrayList<String>();
                emailTestList.add("First");
                emailTestList.add("Second");
                emailTestList.add("Third");

                for (String s : emailTestList) {
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

                builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog alert  = builder.create();
                View mView = getLayoutInflater().inflate(R.layout.pop_up_activity, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
                Button mCancel = (Button) mView.findViewById(R.id.dismiss);
                Button mSubmit2 = (Button) mView.findViewById(R.id.etSubmit);
                mSubmit2.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if(!mEmail.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                // Set up the input
                //final EditText input = new EditText(MainActivity.this);
                //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                //builder.setView(input);

                // Set up the buttons
                //builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //@Override
                  //  public void onClick(DialogInterface dialog, int which) {
                  //      inputText = input.getText().toString();
           //           }
                });
                mCancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        alert.dismiss();
                    }
                                           });
               // builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 //   @Override
                   // public void onClick(DialogInterface dialog, int which) {
                    //    dialog.cancel();
                    //}
                //});

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();


                }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationManager.class));

            }
        });
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
//                String to = "SeniorProjectClover@gmail.com";
                String to = NotificationWizard.recipientEmailAddress;

                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                msg.setSubject(mailSubject);
                msg.setText(mailText);

                Transport.send(msg);

//                HashSet<String> gateways = new HashSet<>();
//                gateways.add("@txt.att.net");
//                gateways.add("@tmomail.net");
//                gateways.add("@vtext.com");
//                gateways.add("@messaging.sprintpcs.com");
//                gateways.add("@pm.sprint.com");
//                gateways.add("@vmobl.com");
//                gateways.add("@mmst5.tracfone.com");
//                gateways.add("@mymetropcs.com");
//                gateways.add("@myboostmobile.com");
//                gateways.add("@mms.cricketwireless.net");
//                gateways.add("@ptel.com");
//                gateways.add("@text.republicwireless.com");
//                gateways.add("@msg.fi.google.com");
//                gateways.add("@tms.suncom.com");
//                gateways.add("@message.ting.com");
//                gateways.add("@email.uscc.net");
//                gateways.add("@cingularme.com");
//                gateways.add("@cspire1.com");

//                to = NotificationWizard.recipientPhoneNumber + "@vtext.com";
//                msg = new MimeMessage(mailSession);
//                msg.setFrom(new InternetAddress(from));
//                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//                msg.setSubject(mailSubject);
//                msg.setText(mailText);
//
//                Transport.send(msg);


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

        for (View v : list) {
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
    }

    @Override
    public void onPause() {
        super.onPause();
        getApplicationContext().registerReceiver(refundReceiver, new IntentFilter("com.clover.intent.action.ORDER_CREATED"));
        mAccount = CloverAccount.getAccount(this);
    }

    protected void onUpdate() {
        //TODO add in timer for periodic checking of current data

        for (int x = 0; x < periodicList.size(); x++) {
            periodicList.get(x).sendNotification();
        }
    }
}