package prototype.prototype_jeff;

import java.util.ArrayList;
import com.clover.sdk.v3.inventory.Item;

/**
 * Created by Jeff on 11/5/2017.
 */

public class Stock {
    private ArrayList<String> emailList=new ArrayList<String>(); // Emails that will get the notifications

    private ArrayList<String> phoneNumberList=new ArrayList<String>(); // Phone numbers that will get the notifications

    private Item stockItem; // Item that will be watched

    private int itemPercentage; // The percentage that the user will want to know when the stock goes below

    public Stock (ArrayList<String> emails, ArrayList<String> phoneNumbers, Item item, int percentage){
        setEmailList(emails);
        setPhoneNumberList(phoneNumbers);
        setStockItem(item);
        setItemPercentage(percentage);
    }
    public void setEmailList(ArrayList<String> list){
        emailList=list;
    }

    public void setPhoneNumberList (ArrayList<String> list){
        phoneNumberList=list;
    }
    public void setStockItem (Item item){
        stockItem=item;
    }
    public void setItemPercentage(int percentage){
        itemPercentage=percentage;
    }

    public ArrayList<String> getEmailList(){
        return emailList;
    }
    public ArrayList<String> getPhoneNumberList(){
        return phoneNumberList;
    }
    public Item getStockItem(){
        return stockItem;
    }
    public int getItemPercentage(){
        return itemPercentage;
    }
}