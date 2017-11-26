package prototype.prototype_jeff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationManager extends AppCompatActivity {


    private Button prevButton;
    private Button nexButton;
    private Button delButton;

    private TextView numberOfText;
    private TextView infoText;

    private int currentIndex =0;
    private ArrayList<Notification> notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);


        prevButton = (Button) findViewById(R.id.prevButton);
        nexButton = (Button) findViewById(R.id.nextButton);
        delButton = (Button) findViewById(R.id.deleteButton);

        numberOfText = (TextView) findViewById(R.id.numberOfText);
        infoText = (TextView) findViewById(R.id.infoText);

        updateInfo();

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex >0){
                    currentIndex--;
                }
            }
        });

        nexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    private void updateInfo(){
        if(notifications != null) {
            numberOfText.setText(currentIndex + " of " + notifications.size());

            if(notifications.get(currentIndex) != null){
                infoText.setText((CharSequence) notifications.get(currentIndex).toString());

            }
        }
    }

}
