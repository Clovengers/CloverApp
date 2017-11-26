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
    public ArrayList<Refund> refundList = new ArrayList<Refund>();
    public ArrayList<Stock> stockList = new ArrayList<Stock>();
    protected ArrayList<Notification> list = new ArrayList<Notification>();
    protected static String lastOrderId;
    protected static OrderConnector orderConnector;
    private Account mAccount;
    private static Order lastOrder;
    private static MainActivity mainActivity;

    PopupActivity pa = new PopupActivity();

    public RefundReceiver(MainActivity mainActivity) {
        RefundReceiver.mainActivity = mainActivity;
    }

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
                    if (lastOrder.getTotal() == 0) { // it's just an order
                        mainActivity.sendEmail("Order Detected",
                                "An order was just placed.\n\n" +
                                        "If that wasn't you, you may need to look into this.");
                    } else if (lastOrder.getTotal() < -5000) // refund exceeds $50
                        mainActivity.sendEmail("Large Refund Detected",
                                "A refund was just issued that exceeded $50.\n\n" +
                                        "If that wasn't you, you may need to look into this.");
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

    protected ArrayList<Notification> getNotifications(){

        list = new ArrayList<Notification>();

        for (int x =0; x< refundList.size(); x++){
            list.add(refundList.get(x));
        }
        for (int x =0; x< stockList.size(); x++){
            list.add(stockList.get(x));
        }



        return list;
    }
}
