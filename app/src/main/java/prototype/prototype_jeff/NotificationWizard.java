package prototype.prototype_jeff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class NotificationWizard extends AppCompatActivity {


    Spinner spinner;

    RadioGroup chosenIntervalRGroup;
    RadioButton minuteRBN;
    RadioButton hourRBN;
    RadioButton dayRBN;
    RadioButton monthRBN;

    EditText email;
    EditText phoneNumber;

    CheckBox phoneBox;
    CheckBox emailBox;

    CheckBox invCheckBox;
    CheckBox refundCheckBox;

    EditText inventoryInputText;
    EditText refundInputText;

    protected static String recipientEmailAddress = "SeniorProjectClover@gmail.com";
    protected static String recipientPhoneNumber = "1234567890";

    protected static ArrayList<Periodic> periodicList = new ArrayList<Periodic>();


    CheckBox refundBox;
    CheckBox stockBox;
    Button submit;
    Refund refund;
    Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_wizard);


        stock = new Stock(null, null, null, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //Init of Object
        refundBox = (CheckBox) findViewById(R.id.refundCheckBox);
        stockBox = (CheckBox) findViewById(R.id.invCheckBox);
        submit = (Button) findViewById(R.id.submitButton);
        phoneNumber = (EditText) findViewById(R.id.phoneText);
        email = (EditText) findViewById(R.id.emailText);
        phoneBox = (CheckBox) findViewById(R.id.phoneBox);
        emailBox = (CheckBox) findViewById(R.id.emailBox);

        invCheckBox = (CheckBox) findViewById(R.id.invCheckBox);
        refundCheckBox = (CheckBox) findViewById(R.id.refundCheckBox);

        inventoryInputText = (EditText) findViewById(R.id.invInputText);
        refundInputText = (EditText) findViewById(R.id.refundInputText);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setSupportActionBar(toolbar);

        chosenIntervalRGroup = (RadioGroup) findViewById(R.id.chosenIntervalRGroup);
        minuteRBN = (RadioButton) findViewById(R.id.minuteRBN);
        hourRBN = (RadioButton) findViewById(R.id.hourRBN);
        dayRBN = (RadioButton) findViewById(R.id.dayRBN);
        monthRBN = (RadioButton) findViewById(R.id.monthRBN);


        invCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invCheckBox.isChecked()){
                    inventoryInputText.setVisibility(View.VISIBLE);
                    refundInputText.setVisibility(View.INVISIBLE);
                    refundCheckBox.setChecked(false);
                }else{
                    inventoryInputText.setVisibility(View.INVISIBLE);

                }

            }
        });

        refundCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(refundCheckBox.isChecked()){
                    refundInputText.setVisibility(View.VISIBLE);
                    inventoryInputText.setVisibility(View.INVISIBLE);
                    invCheckBox.setChecked(false);
                }else{
                    refundInputText.setVisibility(View.INVISIBLE);

                }

            }
        });




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if periodic
                if(spinner.getSelectedItemPosition() == 0){
                    invCheckBox.setVisibility(View.VISIBLE);
                    refundCheckBox.setVisibility(View.VISIBLE);
                    inventoryInputText.setHint("????");  //TODO: what should these really say?
                    refundInputText.setHint("????"); //TODO: what should these really say?
                    chosenIntervalRGroup.setVisibility(View.VISIBLE);
                }
                //if custom
                if(spinner.getSelectedItemPosition() == 1){
                    invCheckBox.setVisibility(View.VISIBLE);
                    refundCheckBox.setVisibility(View.VISIBLE);
                    inventoryInputText.setHint("???????"); //TODO: what should these really say?
                    refundInputText.setHint("Dollars");
                    chosenIntervalRGroup.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                invCheckBox.setVisibility(View.INVISIBLE);
                refundCheckBox.setVisibility(View.INVISIBLE);
                inventoryInputText.setVisibility(View.INVISIBLE);
                refundInputText.setVisibility(View.INVISIBLE);
                chosenIntervalRGroup.setVisibility(View.INVISIBLE);
            }


        });




        //Phone number check box. Listens for on click
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

                //Custom Notification
                if(spinner.getSelectedItemPosition() == 1) {


                    if (refundBox.isChecked()) {
                        refund = createRefund();
                        MainActivity.refundReceiver.refundList.add(createRefund());
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    if (stockBox.isChecked()) {
                        stock = createStock();
                        MainActivity.refundReceiver.stockList.add(stock);
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                }

                //Periodic Notification
                if(spinner.getSelectedItemPosition() == 0){
                    Periodic createdPeriodic = createPeriodic();
                    if (createdPeriodic != null) {
                        periodicList.add(createdPeriodic);
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }


                }
        });
    }


    private Periodic createPeriodic(){
        //7 number of days inbetween sending or weekly
        Periodic periodic;
        if (minuteRBN.isChecked()) {
            periodic = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance(), 0);
        } else if (hourRBN.isChecked()) {
            periodic = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance(), 0, 60);
        } else if (dayRBN.isChecked()) {
            periodic = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance() , 1);
        } else if (monthRBN.isChecked()){
            periodic = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance() , 30);
        } else {
            return null;
        }

        String s = email.getText().toString();
        Log.d("JEFF EMAIL CHECK", s);
        if (emailBox.isChecked() && s != "" && s != null) {
            periodic.addEmail(s);
            Log.d("JEFF EMAIL ADD CHECK", s);
        }
        s = phoneNumber.getText().toString();
        Log.d("JEFF PHONE CHECK", s);
        if (phoneBox.isChecked() && s != "" && s != null) {
            periodic.addPhoneNumber(s);
            Log.d("JEFF PHONE ADD CHECK", s);
        }


        return periodic;
    }



    // TODO NEED A WAY TO GET THE REFUND PRICE WANTED (TEXTBOX)
    private Refund createRefund() {
        double amount = Double.parseDouble(refundInputText.getText().toString());
        Refund refund = new Refund(new ArrayList<String>(), new ArrayList<String>(), amount );
        Log.d("NW RA:", "" + amount );

        String s = email.getText().toString();
        Log.d("JEFF EMAIL CHECK", s);
        if (emailBox.isChecked() && s != "" && s != null) {
            refund.emailList.add(s);
            Log.d("JEFF EMAIL ADD CHECK", s);
        }
        s = phoneNumber.getText().toString();
        Log.d("JEFF PHONE CHECK", s);
        if (phoneBox.isChecked() && s != "" && s != null) {
            refund.phoneNumberList.add(s);
            Log.d("JEFF PHONE ADD CHECK", s);
        }
        return refund;
    }


    //TODO NEED A WAY TO GET THE INVENTORY ITEM IN QUESTION
    private Stock createStock() {
        stock = new Stock(new ArrayList<String>(), new ArrayList<String>(), null, Integer.parseInt(inventoryInputText.getText().toString()) );
        String s = email.getText().toString();
        Log.d("JEFF EMAIL CHECK", s);
        if (emailBox.isChecked() && s != "" && s != null) {
            stock.emailList.add(s);
            Log.d("JEFF EMAIL ADD CHECK", s);
        }
        s = phoneNumber.getText().toString();
        Log.d("JEFF PHONE CHECK", s);
        if (phoneBox.isChecked() && s != "" && s != null) {
            stock.phoneNumberList.add(s);
            Log.d("JEFF PHONE ADD CHECK", s);
        }
        return stock;
    }
}