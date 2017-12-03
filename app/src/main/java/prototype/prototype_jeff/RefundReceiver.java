package prototype.prototype_jeff;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;

import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Abstract: When created, a RefundReceiver object will receive specific broadcasted intents
 * When detected, these intents will be checked against ArrayLists of notifications
 * If criteria is met, an email and/or text message will be sent
 * This class now also contains the required methods for sending an email
 *
 * Updated: 1 December 2017
 */

public class RefundReceiver extends BroadcastReceiver {
    static public ArrayList<Refund> refundList = new ArrayList<Refund>();
    static public ArrayList<Stock> stockList = new ArrayList<Stock>();
    static private Stack<String> emails = new Stack<String>();
    protected ArrayList<Notification> list = new ArrayList<Notification>();
    protected static String lastOrderId;
    protected static OrderConnector orderConnector;
    private Account mAccount;
    private static Order lastOrder;
    //TODO fix the need for this
    protected static Boolean holder = true;//quick fix for two intents being sent each time

    PopupActivity pa = new PopupActivity();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intents.ACTION_ORDER_CREATED)) {
            final String orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID);
            lastOrderId = orderId;
            mAccount = MainActivity.mAccount;

            try {
                Intent newIntent = new Intent(context, PopupActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Order o = new OrderAsyncTask().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private static class OrderAsyncTask extends AsyncTask<Void, Void, Order> {

        @Override
        protected final Order doInBackground(Void... params) {
            String s = "";

            try {
                if (lastOrderId == null) {
                    orderConnector.disconnect();
                } else {
                    lastOrder = orderConnector.getOrder(lastOrderId);
                    MainActivity.totalSales += lastOrder.getTotal();

                }

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            } catch (BindingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Order order) {
            for (Refund refund : refundList) {
                double amt = refundList.get(0).getRefundAmount();
                holder = !holder;
                if (lastOrder.getTotal() < amt * -1 * 100 && holder) {
                    refund.sendNotification(lastOrder.getTotal());

                }
            }
            orderConnector.disconnect();
        }
    }

    protected ArrayList<Notification> getNotifications() {
        list = new ArrayList<Notification>();
        for (int x = 0; x < refundList.size(); x++) {
            list.add(refundList.get(x));
        }
        for (int x = 0; x < stockList.size(); x++) {
            list.add(stockList.get(x));
        }
        for (Periodic p : NotificationWizard.periodicList) {
            list.add(p);
        }
        return list;
    }


    protected static void sendEmail(String mailSubject, String mailText) {
        String host = "smtp.gmail.com";
        final String user = "SeniorProjectClover@gmail.com";
        final String pass = "S3n10rPr0j3ct";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        MailSenderTask mailSenderTask = new MailSenderTask(session, mailSubject, mailText);

        try {
            String holder = mailSenderTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * This class exists just to send an email
     */
    private static class MailSenderTask extends AsyncTask<String, Void, String> {

        Session mailSession;
        String mailSubject;
        String mailText;

        public MailSenderTask(Session session, String subject, String text) {
            mailSession = session;
            mailSubject = subject;
            mailText = text;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String from = "SeniorProjectClover@gmail.com";
                String to = "SeniorProjectClover@gmail.com";

                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                msg.setSubject(mailSubject);
                msg.setText(mailText);

                Transport.send(msg);
            } catch (MessagingException e) {
                System.err.println(e);
            }
            return null;
        }
    }
}
