package prototype.prototype_jeff;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

// TODO Create series of buttons to create new notifications then add them lists
public class CreateNotificationActivity extends AppCompatActivity {

    //Declaring widgets
    private Button nextButton;
    private Button resetButton;
    private Button backButton;
    private RadioButton periodicRadioButton;
    private RadioButton customRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        nextButton = (Button) findViewById(R.id.NEXT_BUTTON);
        resetButton = (Button) findViewById(R.id.RESET_BUTTON);
        backButton = (Button) findViewById(R.id.BACK_BUTTON);

        periodicRadioButton = (RadioButton) findViewById(R.id.PERIODIC_RADIO);
        customRadioButton = (RadioButton) findViewById(R.id.CUSTOM_RADIO);

        periodicRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        customRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
