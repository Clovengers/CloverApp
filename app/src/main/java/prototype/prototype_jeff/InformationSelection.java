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

        refundButton = (Button) findViewById(R.id.refundButton);
        periodicButton = (Button) findViewById(R.id.periodicButton);
        minuteCheck = (CheckBox) findViewById(R.id.minBox);
        hourCheck = (CheckBox) findViewById(R.id.hBox);
        dayCheck = (CheckBox) findViewById(R.id.daysBox);
        //sets background to consitent color
        getWindow().getDecorView().setBackgroundColor(MainActivity.color);
        minuteCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (minuteCheck.isChecked()) {
                            hourCheck.setVisibility(View.INVISIBLE);
                            dayCheck.setVisibility(View.INVISIBLE);
                            hourCheck.setChecked(false);
                            dayCheck.setChecked(false);
                            }
                            else{
                            minuteCheck.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        hourCheck.setOnClickListener(new View.OnClickListener() {
                   @Override
                    public void onClick(View v) {
                        if (hourCheck.isChecked()) {
                            minuteCheck.setVisibility(View.INVISIBLE);
                            dayCheck.setVisibility(View.INVISIBLE);
                            minuteCheck.setChecked(false);
                            dayCheck.setChecked(false);
                        }
                        else{
                            hourCheck.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        dayCheck.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(dayCheck.isChecked()){
                            minuteCheck.setVisibility(View.INVISIBLE);
                            hourCheck.setVisibility(View.INVISIBLE);
                            minuteCheck.setChecked(false);
                            hourCheck.setChecked(false);
                        }
                        else{
                            dayCheck.setVisibility(View.INVISIBLE);
                        }
                    }
                });


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

//                minuteRBN.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (minuteRBN.isChecked()) {
//                            hourRBN.setVisibility(View.INVISIBLE);
//                            dayRBN.setVisibility(View.INVISIBLE);
//                            hourRBN.setChecked(false);
//                            dayRBN.setChecked(false);
//                            }
//                    }
//                });
//                hourRBN.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (hourRBN.isChecked()) {
//                            minuteRBN.setVisibility(View.INVISIBLE);
//                            dayRBN.setVisibility(View.INVISIBLE);
//                            minuteRBN.setChecked(false);
//                            dayRBN.setChecked(false);
//                        }
//                    }
//                });
//                dayRBN.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        if(dayRBN.isChecked()){
//                            minuteRBN.setVisibility(View.INVISIBLE);
//                            hourRBN.setVisibility(View.INVISIBLE);
//                            minuteRBN.setChecked(false);
//                            hourRBN.setChecked(false);
//                        }
//                    }
//                });
//              if (minuteRBN.isChecked()) {
//                    hourRBN.setVisibility(mView.INVISIBLE);
//                    dayRBN.setVisibility(mView.INVISIBLE);
//                }
//                if (hourRBN.isChecked()) {
//                    minuteRBN.setVisibility(mView.INVISIBLE);
//                    dayRBN.setVisibility(mView.INVISIBLE);
//                }
//                if (dayRBN.isChecked()) {
//                    minuteRBN.setVisibility(mView.INVISIBLE);
//                    hourRBN.setVisibility(mView.INVISIBLE);
//                }

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
