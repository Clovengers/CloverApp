package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jeff on 10/12/2017.
 */

public class RefundReceiver extends BroadcastReceiver {
    public static String lastOrderId;
    public ArrayList<Refund> refundList = new ArrayList<Refund>();
    public ArrayList<Stock> stockList = new ArrayList<Stock>();
    protected static OrderConnector orderConnector;
    private Account mAccount;
    private static Order lastOrder;

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
            //orderConnector=new OrderConnector(context, mAccount, null);
            Log.d("DEBUGGER_JEFF", "ORDER CONNECTOR CREATED ");
            //orderConnector.connect();
            if(orderConnector.isConnected()){
                Log.d("DEBUGGER_JEFF", "ORDER CONNECTOR CONNECTED ");
            }



            try {
                Intent newIntent = new Intent(context, PopupActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            } catch (Exception e) {
                e.printStackTrace();

            }

            new OrderAsyncTask().execute();





        }

    }



    protected static class OrderAsyncTask extends AsyncTask<Void, Void, Order> {

        @Override
        protected final Order doInBackground(Void... params) {

            try {





                if (lastOrderId == null) {
                    orderConnector.disconnect();
                } else {
                    lastOrder=orderConnector.getOrder(lastOrderId);
                    Log.d("LAST ORDER: ", lastOrder.toString());
                    return lastOrder;
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            } catch (BindingException e) {
                e.printStackTrace();
            } finally {

            }
            return null;
        }


    }








}
