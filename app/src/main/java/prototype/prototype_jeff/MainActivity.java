package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.sdk.v3.order.OrderConnector;




public class MainActivity extends AppCompatActivity {

    private Account mAccount;
    private OrderConnector mOrderConnector;
    private TextView mTextView;
    private RefundReceiver refundReceiver = new RefundReceiver();

    private Intent emailInent;

    //Declaring objects in layout
    private Button infoButton;
    private Button helpButton;

    private Button emailButton;


    //Used to create pop up msgs
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connecting buttons to their id's
        infoButton = (Button) findViewById(R.id.getInfoButton);
        helpButton = (Button) findViewById(R.id.functioButton);
        emailButton = (Button) findViewById(R.id.emailButton);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendEmail();

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