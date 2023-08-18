import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseRequisition {
    private String requestDate;
    private String purchaseRequisitionID;
    private ArrayList<Item> itemList;
    private ArrayList<String> quantityList;
    private String userID;

    public PurchaseRequisition(String purchaseRequisitionID, String userID, List<Item> items, List<String> quantity, String requestDate) {
        ArrayList<Item> itemArrayList = new ArrayList<>();
        ArrayList<String> quantityArrayList = new ArrayList<>();
        this.purchaseRequisitionID = purchaseRequisitionID;
        this.userID = userID;
        this.quantityList = quantityArrayList;
        this.requestDate = requestDate;
        this.itemList = itemArrayList;
        itemArrayList.addAll(items);
        quantityArrayList.addAll(quantity);
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getPurchaseRequisitionID() {
        return purchaseRequisitionID;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public ArrayList<String> getQuantity() {
        return quantityList;
    }

    public String getUserID() {
        return userID;
    }

    public static PurchaseRequisition createPr(String prID) {
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        String line = prFile.searchRow("purchaseRequisitionID", prID);

        if (line != null) {
            ArrayList<String> itemList = prFile.searchMultipleRow("purchaseRequisitionID", prID, "itemID");
            ArrayList<String> quantityList = prFile.searchMultipleRow("purchaseRequisitionID", prID, "quantity");
            String[] pr = line.split(",");
            ArrayList<Item> itemArrayList = new ArrayList<>();
            for (String item : itemList) {
                itemArrayList.add(Item.createItem(item));
            }
            ArrayList<String> quantityArrayList = new ArrayList<>();
            for (String quantity : quantityList) {
                quantityArrayList.add(quantity);
            }
            return new PurchaseRequisition(pr[0], pr[1], itemArrayList, quantityArrayList, pr[4]);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRequisition that = (PurchaseRequisition) o;
        return Objects.equals(requestDate, that.requestDate) && Objects.equals(purchaseRequisitionID, that.purchaseRequisitionID) && Objects.equals(itemList, that.itemList) && Objects.equals(quantityList, that.quantityList) && Objects.equals(userID, that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestDate, purchaseRequisitionID, itemList, quantityList, userID);
    }
}