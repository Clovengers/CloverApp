package prototype.prototype_jeff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.clover.sdk.v1.Intents;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jeff on 10/12/2017.
 */

public class RefundReceiver extends BroadcastReceiver {
    public String lastOrderId;
    public List<String> list = new ArrayList<String>();

    AlertDialog.Builder builder;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intents.ACTION_ORDER_CREATED)) {
            final String orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID);
            lastOrderId=orderId;
            Log.d("DEBUGGER_JEFF", "ORDER FIRED, id of order: "+lastOrderId.toString());
            //builder = new AlertDialog.Builder(context);
            //builder.setTitle("Order Placed");
            //builder.setMessage("Order ID: " + lastOrderId.toString());

            try {
                Bundle bundle = intent.getExtras();
                String message = "I need this to say something";

                Intent newIntent = new Intent(context, PopupActivity.class);
                newIntent.putExtra("alarm_message", message);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            } catch (Exception e) {
                e.printStackTrace();

            }



           /* builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list.add(orderId);
                }
            });*/
            //builder.show();

        }

    }
}
