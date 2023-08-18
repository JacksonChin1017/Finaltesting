import java.time.LocalDate;
import java.util.Iterator;
import java.util.Objects;

public interface Manageable {
    default void AddItem(Item item) {
        FileHandling itemFile = new FileHandling("Item.txt");
        itemFile.addRow(item.getItemID(), item.getItemName(), String.valueOf(item.getQuantity()), item.getExpireDate(), String.valueOf(item.getSellPrice()), String.valueOf(item.getSupplyPrice()));
        System.out.println("Item added successfully!");
    }

    default void EditItem(String matchValue, String editCol, String newContent) {
        FileHandling itemFile = new FileHandling("Item.txt");
        itemFile.editRow("itemID", matchValue, editCol, newContent);
    }

    default void DeleteItem(Item item) {
        FileHandling itemFile = new FileHandling("Item.txt");
        if (item != null) {
            itemFile.deleteRow("itemID", item.getItemID());
            System.out.println("Item deleted successfully");
        } else {
            System.out.println("Item not found");
        }
    }

    default void AddSupplier(Supplier supplier) {
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        Iterator<Item> iterator = supplier.getSupplierItem().iterator();
        Iterator<Double> iterator2 = supplier.getSupplierItemPrice().iterator();

        while (iterator.hasNext() && iterator2.hasNext()) { //when one supplier supply many item
            Item supplierItem = iterator.next();
            Double supplierItemPrice = iterator2.next();
            supplierFile.addRow(supplier.getSupplierID(), supplier.getSupplierName(), supplier.getLocation(), supplierItem.getItemID(), String.valueOf(supplierItemPrice));
        }
        System.out.println("Supplier Added Successfully");
    }


    default void EditSupplierItem(String supplierID, String itemID, String editCol, String newContent) {
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        supplierFile.editRow("SupplierID", supplierID, "itemID", itemID, editCol, newContent);
    }

    default void EditSupplierInfo(String supplierID, String editCol, String newContent) {
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        supplierFile.editRow("SupplierID", supplierID, editCol, newContent);
    }

    default void DeleteSupplier(String supplierID) {
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        supplierFile.deleteRow("SupplierID", supplierID);
        System.out.println("Supplier is deleted successfully");
    }


    default void AddDailyItemWiseSales(DailySales dailySales) {
        FileHandling itemSalesFile = new FileHandling("ItemSales.txt");
        FileHandling itemFile = new FileHandling("Item.txt");

        double totalPrice;
        LocalDate currentDate = LocalDate.now();

        Iterator<Item> iterator = dailySales.getItemArrayList().iterator();
        Iterator<Integer> iterator2 = dailySales.getQuantityArrayList().iterator();

        while (iterator.hasNext() && iterator2.hasNext()) {
            Item item = iterator.next();
            Integer quantity = iterator2.next();
            String row = itemFile.searchRow("itemID", item.getItemID());
            if (row != null) {
                String[] items = row.split(",");
                totalPrice = Double.parseDouble(items[4]) * quantity;
                int balanceStock = Integer.parseInt(items[2]) - quantity;
                // Rest of your code to process the 'item' array
                itemSalesFile.addRow(dailySales.getSalesID(), item.getItemID(), String.valueOf(quantity), String.valueOf(totalPrice), String.valueOf(currentDate), dailySales.getUser().getUserID());
                itemFile.editRow("itemID", item.getItemID(), "quantity", String.valueOf(balanceStock));
            } else {
                // Handle the case when no matching row is found
                System.out.println("No matching row found.");
            }
        }
    }

    default void DeleteDailyItemWiseSales(String salesID) {
        FileHandling salesFile = new FileHandling("ItemSales.txt");
        salesFile.deleteRow("salesID", salesID);
        System.out.println("Sales Record Deleted Successfully");
    }


    default void EditDailyItemSales(Item item, String salesID, String editCol, String newContent) {
        FileHandling itemSalesFile = new FileHandling("ItemSales.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        int previousQuantity = Integer.parseInt(
                itemSalesFile.searchRow("salesID", "salesItem", salesID, item.getItemID()).split(",")[2]
        );
        if (editCol.equals("quantity")) {
//            *  Recall back previous stock
            itemFile.editRow("itemID", item.getItemID(), "quantity", Integer.toString((item.getQuantity() + previousQuantity)));
            itemSalesFile.editRow("salesID", salesID, "totalPrice", String.valueOf(item.getSellPrice() * Integer.parseInt(newContent)));
            itemFile.editRow("itemID", item.getItemID(), "quantity", String.valueOf(Objects.requireNonNull(Item.createItem(item.getItemID())).getQuantity() - Integer.parseInt(newContent)));
            itemSalesFile.editRow("salesID", salesID, editCol, newContent);
        } else if (editCol.equalsIgnoreCase("salesItem")) {
            itemFile.editRow("itemID", item.getItemID(), "quantity", Integer.toString((item.getQuantity() + previousQuantity)));
            itemFile.editRow("itemID", Objects.requireNonNull(Item.createItem(newContent)).getItemID(), "quantity", String.valueOf((Objects.requireNonNull(Item.createItem(newContent))).getQuantity() - previousQuantity));
            double newPrice = Double.parseDouble(
                    itemFile.searchRow("itemID", newContent).split(",")[5]
            );
            itemSalesFile.editRow("salesID", salesID, "totalPrice", Double.toString(newPrice));
            itemSalesFile.editRow("salesID", salesID, "salesItem", item.getItemID(), "salesItem", newContent);
        } else {
            itemSalesFile.editRow("salesID", salesID, editCol, newContent);
        }
    }

    default void AddPurchaseRequisition(PurchaseRequisition pr) {
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        Iterator<Item> iterator  = pr.getItemList().iterator();
        Iterator<String> iterator2 = pr.getQuantity().iterator();
        while(iterator.hasNext() && iterator2.hasNext()){
            Item prItem = iterator.next();
            String quantity = iterator2.next();
            prFile.addRow(pr.getPurchaseRequisitionID(), pr.getUserID(), prItem.getItemID(),quantity, pr.getRequestDate());
        }
    }

    default void DeletePurchaseRequisition(String prID) {
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        prFile.deleteRow("purchaseRequisitionID", prID);
    }

    default void EditPurchaseRequisition(String prID,String itemID, String editCol, String newContent) {
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        prFile.editRow("purchaseRequisitionID", prID,"itemID",itemID, editCol, newContent);
    }

}