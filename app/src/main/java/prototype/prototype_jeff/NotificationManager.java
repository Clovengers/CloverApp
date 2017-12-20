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



    private TableLayout tableOfNotifications;


    private ArrayList<Notification> notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(MainActivity.color);

        setContentView(R.layout.activity_notification_manager);


        tableOfNotifications = (TableLayout) findViewById(R.id.tableOfNotifications);

        notifications = MainActivity.refundReceiver.getNotifications();
        updateInfo();

    }


    private void updateInfo(){
        notifications = new ArrayList<Notification>();

        notifications = MainActivity.refundReceiver.getNotifications();
        Log.d("MANAGER: ", "notifications list " + notifications.toString()+ " size " + notifications.size());

        for (final Notification n: notifications) {
            TableRow tableRow = new TableRow(this);
            TextView txtNotification = new TextView(this);
            TextView txtAmount = new TextView(this);
            TextView txtReceivedAt = new TextView(this);
            Button deleteNotificationButton = new Button(this);
            if (n.getClass().getSimpleName().equals("Periodic")) {
                txtNotification.setText("Sales Total\t");
                txtAmount.setText(((Periodic) n).getNumberOfMinutesInterval() + "\t");
            } else if (n.getClass().getSimpleName().equals("Refund")) {
                txtNotification.setText("Refund\t");
                txtAmount.setText(NumberFormat.getCurrencyInstance().format(((Refund) n).getRefundAmount()) + "\t");
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
                            MainActivity.periodicList.remove(n);
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
