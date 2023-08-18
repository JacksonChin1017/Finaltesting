
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public interface Purchasable {
    default void AddPurchaseOrder(PurchaseOrder po) {
        LocalDate currentDate = LocalDate.now();
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        for (PurchaseRequisition pr : po.getPurchaseRequisitionList()) {
            Iterator<Item> iterator = pr.getItemList().iterator();
            Iterator<String> iterator2 = pr.getQuantity().iterator();
            while (iterator.hasNext() && iterator2.hasNext()){
                Item item  = iterator.next();
                String quantity = iterator2.next();
                poFile.addRow(po.getPurchaseOrderID(), pr.getPurchaseRequisitionID(), po.getUser().getUserID(), pr.getUserID(),
                        item.getItemID(),quantity, String.valueOf(currentDate), pr.getRequestDate());
            }

            prFile.deleteRow("purchaseRequisitionID", pr.getPurchaseRequisitionID());
        }
    }

    default void DeletePurchaseOrder(String poID) {
        if (poID != null) {
            ArrayList<String> prIDs = new ArrayList<>();
            ArrayList<String> salesManagerIDs = new ArrayList<>();
            ArrayList<String> itemIDs = new ArrayList<>();
            ArrayList<String> quantityArrayList = new ArrayList<>();
            ArrayList<String> requestDateArrayList = new ArrayList<>();

            ArrayList<PurchaseRequisition> prArrayList = new ArrayList<>();
            FileHandling poFile = new FileHandling("PurchaseOrder.txt");
            FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
            prIDs = poFile.searchMultipleRow("purchaseOrderID",poID,"purchaseRequisitionID");
            salesManagerIDs = poFile.searchMultipleRow("purchaseOrderID",poID,"salesManagerID");
            itemIDs = poFile.searchMultipleRow("purchaseOrderID",poID,"itemID");
            quantityArrayList = poFile.searchMultipleRow("purchaseOrderID",poID,"quantity");
            requestDateArrayList = poFile.searchMultipleRow("purchaseOrderID",poID,"requestDate");
            Iterator<String> iterator = prIDs.iterator();
            Iterator<String> iterator2 = salesManagerIDs.iterator();
            Iterator<String> iterator3 = itemIDs.iterator();
            Iterator<String> iterator4 = quantityArrayList.iterator();
            Iterator<String> iterator5 = requestDateArrayList.iterator();
            while(iterator.hasNext()){
                String prID = iterator.next();
                String salesManagerID = iterator2.next();
                String itemID = iterator3.next();
                String quantity = iterator4.next();
                String requestDate = iterator5.next();
                prFile.addRow(prID,salesManagerID,itemID,quantity,requestDate);
            }
            poFile.deleteRow("purchaseOrderID", poID);
        } else {
            System.out.println("No Purchase Order has been added");
        }
    }

    default void EditPurchaseOrder(String poID,String prID,String itemID, String editCol, String newContent) {
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        if(itemID != null){
            if (editCol.equalsIgnoreCase("purchaseRequisitionID")) {
                String salesManagerID = poFile.searchRow("purchaseOrderID", poID).split(",")[3];
                String quantity = poFile.searchRow("purchaseOrderID", poID).split(",")[4];
            }
            poFile.editRow("purchaseOrderID", poID,"purchaseRequisitionID",prID,"itemID", itemID,editCol, newContent);
        }
    }

}
