package prototype.prototype_jeff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.OrderConnector;


import com.clover.sdk.v1.Intents;
import android.accounts.Account;
import com.clover.sdk.v3.order.Order;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jeff on 10/12/2017.
 */

public class RefundReceiver extends BroadcastReceiver {
    public String lastOrderId;
    public List<String> list = new ArrayList<String>();
    private OrderConnector orderConnector;
    private Account mAccount;
    private Order lastOrder;

    AlertDialog.Builder builder;
    PopupActivity pa = new PopupActivity();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intents.ACTION_ORDER_CREATED) || action.equals(Intents.ACTION_REFUND)) {
            final String orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID);
            lastOrderId=orderId;
            Log.d("DEBUGGER_JEFF", "ORDER FIRED, id of order: "+lastOrderId.toString());
            mAccount=MainActivity.mAccount;
            Log.d("DEBUGGER_JEFF", "ACCOUNT: "+mAccount);
            orderConnector=new OrderConnector(context, mAccount, null);
            Log.d("DEBUGGER_JEFF", "ORDER CONNECTOR CREATED ");
            orderConnector.connect();
            if(orderConnector.isConnected()){
                Log.d("DEBUGGER_JEFF", "ORDER CONNECTOR CONNECTED ");
            }
            //ERROR HAPPENS IN TRY BLOCK, SERVICE INVOKED ON MAIN THREAD
            try{lastOrder=orderConnector.getOrder(lastOrderId);
                Log.d("LAST ORDER: ", lastOrder.toString());
            }
            catch (RemoteException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            } catch (BindingException e) {
                e.printStackTrace();
            }



            //builder = new AlertDialog.Builder(context);
            //builder.setTitle("Order Placed");
            //builder.setMessage("Order ID: " + lastOrderId.toString());

            try {
                Bundle bundle = intent.getExtras();
                String message = "I need this to say something";
                pa.setTitle("Title");

                Intent newIntent = new Intent(context, PopupActivity.class);
                newIntent.putExtra("alarm_message", message);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            } catch (Exception e) {
                e.printStackTrace();

            }





        }

    }
}
