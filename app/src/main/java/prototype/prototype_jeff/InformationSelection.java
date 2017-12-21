package prototype.prototype_jeff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

// This class is an activity which handles the creation of notifications. User interface included.
public class InformationSelection extends AppCompatActivity {
    CheckBox minuteCheck;
    CheckBox hourCheck;
    CheckBox dayCheck;
    private Button refundButton;
    private Button periodicButton;
    protected Double refundAmount;
    private String type ="";
    private double value =0;
    protected Double periodicTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setBackgroundColor(MainActivity.color);
        refundButton = (Button) findViewById(R.id.refundButton);
        periodicButton = (Button) findViewById(R.id.periodicButton);


        //sets background to consitent color
        getWindow().getDecorView().setBackgroundColor(MainActivity.color);


        refundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(InformationSelection.this);
                View mView = getLayoutInflater().inflate(R.layout.refund_pop_up_activity, null);
                final EditText mRefund = (EditText) mView.findViewById(R.id.etRefund);
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
                        try { // if (mRefund.getText().toString().length() > 0 && isNumeric(mRefund.getText().toString()) ) {
                            refundAmount = Double.parseDouble(mRefund.getText().toString());
                            refundAmount *= -1;

                            type = "REFUND";
                            value = refundAmount;
                            //Send refund amount to next screen
                            MainActivity.informationSelection.setType(type);
                            MainActivity.informationSelection.setValue(value);
                            startActivity(new Intent(getApplicationContext(), TypeSelection.class));
                            finish();

                        } catch (NumberFormatException | NullPointerException e) {
                            Toast.makeText(InformationSelection.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        } finally {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        periodicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(InformationSelection.this);
                View mView = getLayoutInflater().inflate(R.layout.periodic_pop_up, null);
                final EditText mPeriodic = (EditText) mView.findViewById(R.id.etPeriodic);


                minuteCheck = (CheckBox) mView.findViewById(R.id.minBox);
                hourCheck = (CheckBox)mView.findViewById(R.id.hBox);
                dayCheck = (CheckBox)mView.findViewById(R.id.daysBox);
                minuteCheck.setChecked(true);
                //sets background to consitent color
                minuteCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (minuteCheck.isChecked()) {
                            hourCheck.setChecked(false);
                            dayCheck.setChecked(false);
                        }
                    }
                });
                hourCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hourCheck.isChecked()) {
                            minuteCheck.setChecked(false);
                            dayCheck.setChecked(false);
                        }
                    }
                });
                dayCheck.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(dayCheck.isChecked()){
                            minuteCheck.setChecked(false);
                            hourCheck.setChecked(false);
                        }
                    }
                });





                //TODO attach periodic popup to this
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
                        periodicTime = Double.parseDouble(mPeriodic.getText().toString());
                        type = "PERIODIC";
                        if (minuteCheck.isChecked()) {

                            value = periodicTime;
                        }
                        if (hourCheck.isChecked()) {

                            periodicTime = periodicTime * 60;
                            value = periodicTime;
                        }
                        if (dayCheck.isChecked()) {

                            periodicTime = periodicTime * 1440;
                            value = periodicTime;
                        }


                        MainActivity.informationSelection.setType(type);
                        MainActivity.informationSelection.setValue(value);
                        startActivity(new Intent(getApplicationContext(), TypeSelection.class));
                        finish();


                    }
                    //startActivity(new Intent(getApplicationContext(), InformationSelection.class));
                    // finish();
                });
            }




        });
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    protected String getType(){
        return type;
    }

    protected double getValue(){
        return value;
    }

    protected void setType(String type){
        this.type = type;
    }

    protected void setValue(double value){
            this.value = value;
    }

    //TODO popup menu to read refund amount
    //TODO create a spinner object to select the periodic time

}
