package prototype.prototype_jeff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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

    CheckBox invCheckBox;
    CheckBox refundCheckBox;

    EditText inventoryInputText;
    EditText refundInputText;
    Button submitButton;

    public static String recipientEmailAddress = "SeniorProjectClover@gmail.com";
    public static String recipientPhoneNumber = "1234567890";

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

        invCheckBox = (CheckBox) findViewById(R.id.invCheckBox);
        refundCheckBox = (CheckBox) findViewById(R.id.refundCheckBox);

        inventoryInputText = (EditText) findViewById(R.id.invInputText);
        refundInputText = (EditText) findViewById(R.id.refundInputText);
        submitButton = (Button) findViewById(R.id.submitButton);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        invCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invCheckBox.isChecked()){
                    inventoryInputText.setVisibility(View.VISIBLE);
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
                    invCheckBox.setVisibility(View.INVISIBLE);
                    refundCheckBox.setVisibility(View.INVISIBLE);
                    inventoryInputText.setVisibility(View.INVISIBLE);
                    refundInputText.setVisibility(View.INVISIBLE);
                }
                //if custom
                if(spinner.getSelectedItemPosition() == 1){
                    invCheckBox.setVisibility(View.VISIBLE);
                    refundCheckBox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                invCheckBox.setVisibility(View.INVISIBLE);
                refundCheckBox.setVisibility(View.INVISIBLE);
                inventoryInputText.setVisibility(View.INVISIBLE);
                refundInputText.setVisibility(View.INVISIBLE);
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
