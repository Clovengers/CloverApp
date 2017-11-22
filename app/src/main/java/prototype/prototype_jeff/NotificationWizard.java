package prototype.prototype_jeff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class NotificationWizard extends AppCompatActivity {
    Spinner spinner;
    EditText email;
    EditText phoneNumber;
    CheckBox phoneBox;
    CheckBox emailBox;
    CheckBox refundBox;
    CheckBox stockBox;
    Button submit;
    Refund refund;
    Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_wizard);

        refund=new Refund(null, null, 0);
        stock=new Stock(null, null, null, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        refundBox = (CheckBox) findViewById(R.id.refundCheckBox);
        stockBox = (CheckBox) findViewById(R.id.invCheckBox);
        submit = (Button) findViewById(R.id.submitButton);
        phoneNumber = (EditText) findViewById(R.id.phoneText);
        email = (EditText) findViewById(R.id.emailText);
        phoneBox = (CheckBox) findViewById(R.id.phoneBox);
        emailBox = (CheckBox) findViewById(R.id.emailBox);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        phoneBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneBox.isChecked()) {
                    phoneNumber.setVisibility(View.VISIBLE);
                } else {
                    phoneNumber.setVisibility(View.INVISIBLE);
                }

            }
        });

        emailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailBox.isChecked()) {
                    email.setVisibility(View.VISIBLE);
                } else {
                    email.setVisibility(View.INVISIBLE);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(refundBox.isChecked()){

                    refund=createRefund();
                    MainActivity.refundReceiver.refundList.add(refund);
                    Log.d("JEFF", MainActivity.refundReceiver.refundList.toString());
                }
                if(stockBox.isChecked()){
                    createStock();
                }
            }
        });}

    // TODO NEED A WAY TO GET THE REFUND PRICE WANTED (TEXTBOX)
    private Refund createRefund() {
        ArrayList<String> array = new ArrayList<String>();
        String s=email.getText().toString();
        if(emailBox.isChecked()&&s!=""&&s!=null){
            array.add(s);
            refund.setEmailList(array);
        }
        s=phoneNumber.getText().toString();
        if(phoneBox.isChecked()&&s!=""&&s!=null){
            array=new ArrayList<String>();
            array.add(s);
        }
        return refund;
    }

    //TODO NEED A WAY TO GET THE INVENTORY ITEM IN QUESTION
    private Stock createStock() {
        ArrayList<String> array = new ArrayList<String>();
        String s=email.getText().toString();
        if(emailBox.isChecked()&&s!=""&&s!=null){
            array.add(s);
            stock.setEmailList(array);
        }
        s=phoneNumber.getText().toString();
        if(phoneBox.isChecked()&&s!=""&&s!=null){
            array=new ArrayList<String>();
            array.add(s);
        }
        return stock;
    }
}