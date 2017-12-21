package prototype.prototype_jeff;

import java.util.ArrayList;

/**
 * Created by Jeff on 11/5/2017.
 * Updated: December 20 2017
 * This class has not been updated since but it can be use for future implementation
 */

public class Stock extends Notification{
    private String stockItem; // Item that will be watched

    private int itemPercentage; // The percentage that the user will want to know when the stock goes below

    public Stock (ArrayList<String> emails, ArrayList<String> phoneNumbers, String item, int percentage){
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setStockItem(item);
        setItemPercentage(percentage);
    }

    protected void setStockItem (String item){
        stockItem=item;
    }
    protected void setItemPercentage(int percentage){
        itemPercentage=percentage;
    }

    protected String getStockItem(){
        return stockItem;
    }
    protected int getItemPercentage(){
        return itemPercentage;
    }
}
