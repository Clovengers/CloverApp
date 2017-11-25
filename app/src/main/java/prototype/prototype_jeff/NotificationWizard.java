package prototype.prototype_jeff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class NotificationWizard extends AppCompatActivity {


    Spinner spinner;
    EditText email;
    EditText phoneNumber;

    CheckBox phoneBox;
    CheckBox emailBox;

<<<<<<< HEAD
    CheckBox invCheckBox;
    CheckBox refundCheckBox;

    EditText inventoryInputText;
    EditText refundInputText;
=======
    Button submitButton;

    public static String recipientEmailAddress = "SeniorProjectClover@gmail.com";
    public static String recipientPhoneNumber = "1234567890";
>>>>>>> 1aeec6cb0b81baca1ae7e5f48a7a1d611a20aff7

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_wizard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //Init of Object
        phoneNumber = (EditText) findViewById(R.id.phoneText);
        email = (EditText) findViewById(R.id.emailText);

        phoneBox = (CheckBox) findViewById(R.id.phoneBox);
        emailBox = (CheckBox) findViewById(R.id.emailBox);

<<<<<<< HEAD
        invCheckBox = (CheckBox) findViewById(R.id.invCheckBox);
        refundCheckBox = (CheckBox) findViewById(R.id.refundCheckBox);

        inventoryInputText = (EditText) findViewById(R.id.invInputText);
        refundInputText = (EditText) findViewById(R.id.refundInputText);
=======
        submitButton = (Button) findViewById(R.id.submitButton);
>>>>>>> 1aeec6cb0b81baca1ae7e5f48a7a1d611a20aff7

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        invCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invCheckBox.isChecked()){

                }else{

                }

            }
        });



        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if periodic
                if(spinner.getSelectedItemPosition() == 0){

                }
                //if custom
                if(spinner.getSelectedItemPosition() == 1){
                    invCheckBox.setVisibility(View.VISIBLE);
                    refundCheckBox.setVisibility(View.INVISIBLE);
                }



            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Phone number check box. Listens for on click
        phoneBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneBox.isChecked()){
                    phoneNumber.setVisibility(View.VISIBLE);
                }else{
                    phoneNumber.setVisibility(View.INVISIBLE);
                }

            }
        });

        emailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailBox.isChecked()){
                    email.setVisibility(View.VISIBLE);
                }else{
                    email.setVisibility(View.INVISIBLE);
                }

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipientEmailAddress = email.getText().toString();
                recipientPhoneNumber = phoneNumber.getText().toString();
            }
        });
    }

}
