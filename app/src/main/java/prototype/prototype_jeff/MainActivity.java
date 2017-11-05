package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clover.sdk.v3.order.OrderConnector;


public class MainActivity extends AppCompatActivity {

    private Account mAccount;
    private OrderConnector mOrderConnector;
    private TextView mTextView;
    private RefundReceiver refundReceiver = new RefundReceiver();

    //Declaring objects in layout
    Button infoButton;
    Button helpButton;
    Button wizardButton;

    //Used to create pop up msgs
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connecting buttons to their id's
        infoButton = (Button) findViewById(R.id.getInfoButton);
        helpButton = (Button) findViewById(R.id.functioButton);
        wizardButton = (Button) findViewById(R.id.WIZARD_BUTTON);

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

        wizardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchWizard();
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

    private void launchWizard() {

        Intent intent = new Intent(this, NotificationWizard.class);
        startActivity(intent);
    }

}