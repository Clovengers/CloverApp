package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.order.OrderConnector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;

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
    private RefundReceiver refundReceiver = new RefundReceiver();

    private Intent emailInent;

    //Declaring objects in layout
    private Button infoButton;
    private Button helpButton;
    private Button emailButton;
    private Button settingsButton;

    // things that inherit from View that will be shown/hidden by the settings button
    ArrayList<View> settings;

    //Used to create pop up msgs
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                sendEmail("Test email", "This is a test email");
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Use this line of code to output popups, additional features can be added with more methods
                //builder = new AlertDialog.Builder(MainActivity.this).setTitle("Inventory Data").setMessage("Some information formated into a string");
                //builder.show();
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
                toggleSettingsVisibility();
            }
        });

    }

    public void sendEmail(String mailSubject, String mailText) {
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
        mailSenderTask.execute();
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

        list.add(findViewById(R.id.setting1CheckBox));

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


    private void sendEmail2(){
        String emailTo = "brifrench@yahoo.com";

        emailInent = new Intent(Intent.ACTION_SEND);
        emailInent.setData(Uri.parse("mailto:"));
        emailInent.setType("text/plain");

        emailInent.putExtra(Intent.EXTRA_EMAIL, emailTo);
        emailInent.putExtra(Intent.EXTRA_TEXT, "This is the email body ");
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"brifrench@yahoo.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}