import java.util.ArrayList;
import java.util.List;

public class DailySales {
    private String salesID;
    private String salesDate;
    private ArrayList<Item> itemArrayList;
    private ArrayList<Integer> quantityArrayList;

    private User user;

    public String getSalesID() {
        return salesID;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public ArrayList<Integer> getQuantityArrayList() {
        return quantityArrayList;
    }

    public User getUser() {
        return user;
    }

    public DailySales(String salesID, String salesDate, List<Item> items, List<Integer> quantity, User user) {
        ArrayList<Item> itemArrayList = new ArrayList<>();
        ArrayList<Integer> quanttiyArrayList = new ArrayList<>();

        this.salesDate = salesDate;
        this.itemArrayList = itemArrayList;
        this.quantityArrayList = quanttiyArrayList;
        this.user = user;
        this.salesID = salesID;
        itemArrayList.addAll(items);
        quanttiyArrayList.addAll(quantity);
    }
}