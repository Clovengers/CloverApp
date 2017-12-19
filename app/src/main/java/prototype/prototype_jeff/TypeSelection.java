package prototype.prototype_jeff;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class TypeSelection extends AppCompatActivity {

    private Button emailButton;
    private Button smsButton;
    private String type = "";
    private double value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setBackgroundColor(MainActivity.color);

        setContentView(R.layout.activity_type_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        value = MainActivity.informationSelection.getValue();
        type = MainActivity.informationSelection.getType();



        emailButton = (Button) findViewById(R.id.EmailButton);
        smsButton = (Button) findViewById(R.id.SMSButton);


        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create a popup that reads in email
                AlertDialog.Builder builder = new AlertDialog.Builder(TypeSelection.this);
                View mView = getLayoutInflater().inflate(R.layout.pop_up_activity, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);

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

                //Handles the data and acts like the previous submit button it gets the type from previous screen and then checks
                //works for both Refund and Periodic
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mEmail.getText().toString().isEmpty() || mEmail.getText().toString().contains("@") ) {
                            Toast.makeText(TypeSelection.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            if(type.equals("REFUND")){
                                Refund refund = new Refund(new ArrayList<String>(), new ArrayList<String>(), value);
                                refund.emailList.add(mEmail.getText().toString());


                                MainActivity.refundReceiver.refundList.add(refund);

                                //INSERTION INTO DATABASE, -1 is irrelevant since this is a Refund, not periodic
                                MainActivity.myDB.insertData("REFUND", refund.getRefundAmount(), -1, sizeChecker(refund.emailList), sizeChecker(refund.phoneNumberList));
                                finish();

                            }
                            if(type.equals("PERIODIC")){
                                Periodic periodic = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance(), 0, (long)value);
                                periodic.emailList.add(mEmail.getText().toString());

                                MainActivity.periodicList.add(periodic);
                                //TODO COMPLETE DATA INSERTION FOR PERIODIC BELOW. THE NEGATIVE VALUES ARE IRRELEVANT SINCE THIEY ARE FOR REFUNDS
                                //TODO THIS MAY NEED TO SAVE SOMETHING ELSE AS time, MAYBE CONVERT CALENDER TO STRING?
                                if(sizeChecker(periodic.emailList)!=null||sizeChecker(periodic.phoneNumberList)!=null) {
                                    long holder = Calendar.getInstance().getTimeInMillis();
                                    MainActivity.myDB.insertData("PERIODIC", -1.0, holder, sizeChecker(periodic.emailList), sizeChecker(periodic.phoneNumberList));
                                    Log.d("DATABSE ENTRY: ", periodic.toString());

                                    finish();
                                }


                            }


                            //close after done?
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            dialog.dismiss();

                        } else {
                            Toast.makeText(TypeSelection.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });



        //SMS was selected
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TypeSelection.this);
                View mView = getLayoutInflater().inflate(R.layout.txt_pop_up_activity, null);
                //Is actually a phone number in the case but copied for use
                final EditText mText = (EditText) mView.findViewById(R.id.etText);
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
                        //Checks for standard phone number can be changed
                        if (mText.getText().toString().length() >= 7 ) {
                            Toast.makeText(TypeSelection.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            //TODO Now a Refund or Periodic has to be created
                            if(type.equals("REFUND")){
                                Refund refund = new Refund(new ArrayList<String>(), new ArrayList<String>(), value);
                                refund.phoneNumberList.add(mText.getText().toString());


                                MainActivity.refundReceiver.refundList.add(refund);

                                //INSERTION INTO DATABASE, -1 is irrelevant since this is a Refund, not periodic
                                MainActivity.myDB.insertData("REFUND", refund.getRefundAmount(), -1, sizeChecker(refund.emailList), sizeChecker(refund.phoneNumberList));
                                finish();
                            }
                            if(type.equals("PERIODIC")){
                                Periodic periodic = new Periodic(new ArrayList<String>(), new ArrayList<String>(), Calendar.getInstance(), 0, (long)value);
                                periodic.phoneNumberList.add(mText.getText().toString());

                                MainActivity.periodicList.add(periodic);
                                //TODO COMPLETE DATA INSERTION FOR PERIODIC BELOW. THE NEGATIVE VALUES ARE IRRELEVANT SINCE THIEY ARE FOR REFUNDS
                                //TODO THIS MAY NEED TO SAVE SOMETHING ELSE AS time, MAYBE CONVERT CALENDER TO STRING?
                                if(sizeChecker(periodic.emailList)!=null||sizeChecker(periodic.phoneNumberList)!=null) {
                                    long holder = Calendar.getInstance().getTimeInMillis();
                                    MainActivity.myDB.insertData("PERIODIC", -1.0, holder, sizeChecker(periodic.emailList), sizeChecker(periodic.phoneNumberList));
                                    finish();
                                }
                            }



                            //startActivity(new Intent(getApplicationContext(), InformationSelection.class));
                            dialog.dismiss();

                        } else {
                            Toast.makeText(TypeSelection.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }

        });















    }

    //Checks the size of a String array and that the first string is not empty
    private String sizeChecker(ArrayList<String> list){
        if(list.size()>0&&!list.get(0).equals("")){
            return list.get(0);
        }
        return null;
    }



}
