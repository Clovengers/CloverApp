package prototype.prototype_jeff;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Brian on 10/14/2017.
 * Used by RefundReceiver. R.layout.pop_up_activity now used for certain dialog inputs
 * in the GUI overhaul.
 */
public class PopupActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }
}
