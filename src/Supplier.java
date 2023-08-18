import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String supplierID;
    private String supplierName;
    private String location;
    private ArrayList<Item> supplierItemArrayList;
    private ArrayList<Double> supplierItemPriceArrayList;

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Item> getSupplierItem() {
        return supplierItemArrayList;
    }

    public ArrayList<Double> getSupplierItemPrice() {
        return supplierItemPriceArrayList;
    }

    public Supplier(String supplierID, String supplierName, String location, List<Item> supplierItems, List<Double> supplierItemPrices) {
        ArrayList<Item> supplierItemArrayList = new ArrayList<Item>();
        ArrayList<Double> supplierItemPriceArrayList = new ArrayList<Double>();
        this.supplierItemArrayList = supplierItemArrayList;
        this.supplierItemPriceArrayList = supplierItemPriceArrayList;
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.location = location;
        for (Item supplierItem:
                supplierItems) {
            supplierItemArrayList.add(supplierItem);
        }
        for (Double supplierItemPrice:
                supplierItemPrices){
            supplierItemPriceArrayList.add(supplierItemPrice);
        }
    }

}