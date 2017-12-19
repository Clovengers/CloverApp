package prototype.prototype_jeff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class NotificationManager extends AppCompatActivity {


//    private Button prevButton;
//    private Button nexButton;
//    private Button delButton;
    private TableLayout tableOfNotifications;

//    private TextView numberOfText;
//    private TextView infoText;

//    private int currentIndex =0;
    private ArrayList<Notification> notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(MainActivity.color);

        setContentView(R.layout.activity_notification_manager);

//        prevButton = (Button) findViewById(R.id.prevButton);
//        nexButton = (Button) findViewById(R.id.nextButton);
//        delButton = (Button) findViewById(R.id.deleteButton);
        tableOfNotifications = (TableLayout) findViewById(R.id.tableOfNotifications);

//        numberOfText = (TextView) findViewById(R.id.numberOfText);
//        infoText = (TextView) findViewById(R.id.infoText);

        notifications = MainActivity.refundReceiver.getNotifications();
        updateInfo();

        prevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("MANAGER: ", "notifications list " + notifications.toString()+ " size " + notifications.size());


                if(currentIndex >0){
                    currentIndex--;
                    updateInfo();
                }
            }
        });

        nexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex +1 < notifications.size()){
                    currentIndex++;
                    updateInfo();
                }
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(notifications.size()  != 0) {
                    if (notifications.get(currentIndex).getClass().getSimpleName().equals("Refund")) {
                        MainActivity.refundReceiver.refundList.remove(notifications.get(currentIndex));
                    }

                    if (notifications.get(currentIndex).getClass().getSimpleName().equals("Stock")) {
                        MainActivity.refundReceiver.stockList.remove(notifications.get(currentIndex));
                    }

                    if (notifications.get(currentIndex).getClass().getSimpleName().equals("Periodic")) {
                        MainActivity.periodicList.remove(notifications.get(currentIndex));
                    }

                    notifications.remove(currentIndex);
                    //Currently recreates the DB with new info
                    MainActivity.deleteNotification();
                    currentIndex = 0;
                    updateInfo();

                }

            }
        });



    }


    private void updateInfo(){
        notifications = new ArrayList<Notification>();

        notifications = MainActivity.refundReceiver.getNotifications();
        Log.d("MANAGER: ", "notifications list " + notifications.toString()+ " size " + notifications.size());


//        if(notifications != null && notifications.size() != 0) {
//            numberOfText.setText((currentIndex + 1) + " of " + notifications.size());
//
//            if(notifications.get(currentIndex) != null){
//                Log.d("MANAGER", "Test Update Info notification to string " + notifications.get(currentIndex).toString());
//                infoText.setText((CharSequence) notifications.get(currentIndex).toString());
//
//            }
//        }else{
//            numberOfText.setText("0 of 0");
//            infoText.setText("No Notifcations Saved");
//        }

        for (final Notification n: notifications) {
            TableRow tableRow = new TableRow(this);
            TextView txtNotification = new TextView(this);
            TextView txtAmount = new TextView(this);
            TextView txtReceivedAt = new TextView(this);
            Button deleteNotificationButton = new Button(this);
            if (n.getClass().getSimpleName().equals("Periodic")) {
                txtNotification.setText("Sales Total\t\t\t\t");
                txtAmount.setText(((Periodic) n).getNumberOfMinutesInterval() + "\t\t\t\t");
            } else if (n.getClass().getSimpleName().equals("Refund")) {
                txtNotification.setText("Refund\t\t\t\t");
                txtAmount.setText(NumberFormat.getCurrencyInstance().format(((Refund) n).getRefundAmount()) + "\t\t\t\t");
            }
            if (MainActivity.sizeChecker(n.getEmailList()) != null) {
                txtReceivedAt.setText(n.getEmailList().get(0) + "\t");
            } else if (MainActivity.sizeChecker(n.getPhoneNumberList()) != null) {
                txtReceivedAt.setText(n.getPhoneNumberList().get(0) + "\t");
            }
            deleteNotificationButton.setText("DELETE");
            deleteNotificationButton.setOnClickListener(new View.OnClickListener() {
                Notification givenNotification = n;
                @Override
                public void onClick(View view) {
                    if(notifications.size()  != 0) {
                        if (n.getClass().getSimpleName().equals("Refund")) {
                            MainActivity.refundReceiver.refundList.remove(n);
                        }

                        if (n.getClass().getSimpleName().equals("Periodic")) {
                            NotificationWizard.periodicList.remove(n);
                        }

                        //Currently recreates the DB with new info
                        MainActivity.deleteNotification();
                        startActivity(new Intent(getApplicationContext(), NotificationManager.class));
                        finish();
                    }
                }
            });
            tableRow.addView(txtNotification);
            tableRow.addView(txtAmount);
            tableRow.addView(txtReceivedAt);
            tableRow.addView(deleteNotificationButton);
            tableOfNotifications.addView(tableRow);
        }
    }

}
