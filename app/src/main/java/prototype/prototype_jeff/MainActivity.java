package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.IntentFilter;
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

    private Account mAccount;
    private OrderConnector mOrderConnector;
    private TextView mTextView;
    private RefundReceiver refundReceiver = new RefundReceiver();

    //Declaring objects in layout
    Button infoButton;
    Button helpButton;
    Button settingsButton;

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

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Use this line of code to output popups, additional features can be added with more methods
                builder = new AlertDialog.Builder(MainActivity.this).setTitle("Inventory Data").setMessage("Some information formated into a string");
                builder.show();
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

        sendEmail();
    }

    public void sendEmail() {
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

        MailSenderTask mailSenderTask = new MailSenderTask();
        mailSenderTask.mailSession = session;
        mailSenderTask.execute();
    }

    /**
     * This class exists just to send an email
     */
    private class MailSenderTask extends AsyncTask<String, Void, String> {

        Session mailSession;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String from = "SeniorProjectClover@gmail.com";
                String to = "mendelsoa8@students.rowan.edu";
                String subject = "Test email";
                String messageText = "This is a test email";

                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                msg.setSubject(subject);
                msg.setText(messageText);

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
        Log.d("DEBUGGER_JEFF", "PAUSE START");
    }

    }