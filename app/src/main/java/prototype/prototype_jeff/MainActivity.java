package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.order.OrderConnector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
//    static public MainActivity mainActivity;
    static public InformationSelection informationSelection;

    private Intent emailInent;

    //Declaring objects in layout
    private String type = null;
    private Button infoButton;
    private Button helpButton;
    private Button emailButton;
    private Button settingsButton;
    private boolean demo = true;
    private String inputText = "";
    static public DatabaseHelper myDB;

    // Set this constant to "" if you don't think it should be included in the emails sent
    public static final String RECEIVEDINERROR = "\n\nIf you received this in error, " +
            "please delete it and block seniorprojectclover@gmail.com.";


    static protected ArrayList<Periodic> periodicList = new ArrayList<Periodic>();

    protected static double totalSales = 0;


    // things that inherit from View that will be shown/hidden by the settings button
    ArrayList<View> settings;

    //Used to create pop up msgs
    AlertDialog.Builder builder;
    private ArrayList<String> dataArray= new ArrayList<String>();
    private Timer timer;

    protected static int color = Color.parseColor("#5CB7DA");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MainActivity.mainActivity = this;
        getWindow().getDecorView().setBackgroundColor(MainActivity.color);

        informationSelection = new InformationSelection();


        //start database
        myDB = new DatabaseHelper(this);
        Log.d("DATABASE", myDB.getDatabaseName());
        // uncomment code below to wipe the database
        //myDB.deleteAll();
        Cursor result = myDB.getData();


        if (result.getCount() == 0) {
            Log.d("DATABASE", "EMPTY");
            //START FIRST TIME USE ACTIVITY
            /*contentValues.put(COL_2, type);
            contentValues.put(COL_3, threshhold);
            contentValues.put(COL_4, data);
            contentValues.put(COL_5, email);
            contentValues.put(COL_6, phone);
            contentValues(Col_7, value*/
        } else {
            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                type = result.getString(1);
                Log.d("DATABASE", type);
                if (type.equals("REFUND")) {
                    //creates a Refund with just the threshhold in constructor
                    Refund refund= new Refund(null, null, Double.parseDouble(result.getString(2)));
                    Log.d("REFUND CREATION", "THRESHHOLD=" + refund.getRefundAmount());

                    // Adds the email to an arraylist then sets the Refund arraylist to this arraylist

                    dataArray.add(result.getString(4));
                    if(dataArray != null){
                        refund.emailList=dataArray;

                    }

                    // create empty arraylist, repeat for phone number
                    dataArray=new ArrayList<String>();
                    dataArray.add(result.getString(5));
                    if(dataArray != null){
                        refund.phoneNumberList=dataArray;

                    }


                    // Adds the refund notification
                    Log.d("REFUND CREATION", "DATA= " + refund.toString());
                    refundReceiver.refundList.add(refund);
                    dataArray = new ArrayList<String>();

                } else if (type.equals("PERIODIC")) {
                    dataArray=new ArrayList<String>();

                    String x = result.getString(3);

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    long milliSeconds= Long.parseLong(x);
                    System.out.println(milliSeconds);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(milliSeconds);
                    Long numMinutes = Long.parseLong(result.getString(6));


                    Periodic periodic = new Periodic(dataArray, dataArray, calendar, numMinutes);

                    // Adds the email to an arraylist then sets the Refund arraylist to this arraylist
                    dataArray.add(result.getString(4));
                    periodic.emailList=dataArray;

                    // create empty arraylist, repeat for phone number
                    dataArray=new ArrayList<String>();
                    dataArray.add(result.getString(5));
                    periodic.phoneNumberList=dataArray;

                    periodicList.add(periodic);





                }
            }
        }


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

        if (demo) {
            // schedule the task to run starting now and then every minute...
            //TODO multiple by 60
            timer.schedule(hourlyTask, 0l, 1000 * 60);
        } else {
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

                Log.d("DataBase toString", "" + myDB.getReadableDatabase().toString());



            }
        });

        //BEGIN BUTTON
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), InformationSelection.class));

            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.pop_up_activity, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
                //final TextView title = (TextView) mView.findViewById(R.id.TitleTextView);
                //title.setText("Testing This");
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mEmail.getText().toString().isEmpty()) {
                                   Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                   dialog.dismiss();

                        } else {
                                 Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                    }
                });


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

        MailSenderTask mailSenderTask = new MailSenderTask(session, mailSubject, mailText + RECEIVEDINERROR);

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

    private static ArrayList<Refund> getRefundsDB() {
        ArrayList<Refund> list = new ArrayList<Refund>();

        return list;
    }

    private static ArrayList<Periodic> getPeriodicsDB() {
        ArrayList<Periodic> list = new ArrayList<Periodic>();
        myDB.getReadableDatabase();
        return list;
    }

    //TODO do this the proper way
    protected static boolean deleteNotification(){
        myDB.deleteAll();
        ArrayList<Refund> list = refundReceiver.refundList;
        for(Refund refund : list){
            if(refund != null) {
                myDB.insertData("REFUND", refund.getRefundAmount(), -1, sizeChecker(refund.emailList), sizeChecker(refund.phoneNumberList), (long)-1);
            }

        }

        for(Periodic periodic : periodicList){
            if(periodic != null){
                myDB.insertData("PERIODIC", -1.0, periodic.getCalendar().getTimeInMillis(), sizeChecker(periodic.emailList), sizeChecker(periodic.phoneNumberList), periodic.numberOfMinutesInterval);
            }
        }



        return true;
    }


    //Checks the size of a String array and that the first string is not empty
    protected static String sizeChecker(ArrayList<String> list){
        if(list.size()>0){
            if(list.get(0)!= null){
                if(!list.get(0).equals("")){
                    return list.get(0);

                }

            }
        }
        return null;
    }

}