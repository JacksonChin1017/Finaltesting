import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String userID, userPassword;
        int choice, attempt = 0;
        boolean isLoop = true;
        while (true) {
            ConsoleTextDisplay.printMainMenu();
            choice = InputValidation.getInteger(1, 2);
            if (choice == 1) {
                while (true) {
                    {
                        ConsoleTextDisplay.printSystemMainTitle();
                        System.out.println("Dear User Please Login Your Account : ");
                        System.out.print("Username : ");
                        userID = input.nextLine();
                        System.out.print("Password : ");
                        userPassword = input.nextLine();
                        User user = Admin.login(userID, userPassword);
                        if (user instanceof Admin admin) {
                            attempt = 0;
                            System.out.println("Admin");
                        } else if (user instanceof SalesManager salesManager) {
                            attempt = 0;
                            while (isLoop) {
                                choice = ConsoleTextDisplay.salesManagerMenu();
                                switch (choice) {
                                    case 1 -> {
                                        Item item = ConsoleTextDisplay.AddItemMenu();
                                        if (item != null) {
                                            salesManager.AddItem(item);
                                        }
                                    }
                                    case 2 -> {
                                        FileHandling itemFile = new FileHandling("Item.txt");
                                        if(itemFile.getRows() == 0){
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add item first");
                                        }else{
                                            salesManager.DeleteItem(ConsoleTextDisplay.deleteItemMenu());
                                        }

                                    }
                                    case 3 -> {
                                        FileHandling itemFile = new FileHandling("Item.txt");
                                        if(itemFile.getRows() == 0){
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add item first");
                                        }else {
                                            String itemID = ConsoleTextDisplay.EditItemMainMenu();
                                            String[] changedInfo = ConsoleTextDisplay.EditItemSubMenu();
                                            salesManager.EditItem(itemID, changedInfo[0], changedInfo[1]);
                                        }
                                    }
                                    case 4 -> {
                                        salesManager.AddSupplier(ConsoleTextDisplay.AddSupplierMenu());
                                    }
                                    case 5 -> {
                                        FileHandling supplierFile = new FileHandling("Supplier.txt");
                                        if(supplierFile.getRows() == 0){
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add supplier first");
                                        }else {
                                            salesManager.DeleteSupplier(ConsoleTextDisplay.deleteSupplierMenu());
                                        }
                                    }
                                    case 6 -> {
                                        FileHandling supplierFile = new FileHandling("Supplier.txt");
                                        if(supplierFile.getRows() == 0){
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add supplier first");
                                        }else {
                                            ArrayList<String> arrayList = ConsoleTextDisplay.editSupplierMenu();
                                            if (arrayList.size() == 4) {
                                                salesManager.EditSupplierItem(arrayList.get(0), arrayList.get(1), arrayList.get(2), arrayList.get(3));
                                            } else {
                                                salesManager.EditSupplierInfo(arrayList.get(0), arrayList.get(1), arrayList.get(2));
                                            }
                                        }

                                    }
                                    case 7 -> {
                                        salesManager.AddDailyItemWiseSales(ConsoleTextDisplay.AddDailySalesMenu(salesManager));
                                    }
                                    case 8 -> {
                                        FileHandling salesFile = new FileHandling("ItemSales.txt");
                                        if(salesFile.getRows() == 0){
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  daily item sales first");
                                        }else {
                                            salesManager.DeleteDailyItemWiseSales(ConsoleTextDisplay.deleteDailySalesMenu());
                                        }
                                    }
                                    case 9 -> {
                                        FileHandling salesFile = new FileHandling("ItemSales.txt");
                                        if(salesFile.getRows() == 0){
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  daily item sales first");
                                        }else {
                                            ArrayList<Object> editDetail = ConsoleTextDisplay.editDailySales();
                                            salesManager.EditDailyItemSales((Item) editDetail.get(0), (String) editDetail.get(1), (String) editDetail.get(2), (String) editDetail.get(3));
                                        }

                                    }
                                    case 10 -> {
                                        salesManager.AddPurchaseRequisition(ConsoleTextDisplay.AddPrMenu(salesManager));
                                    }
                                    case 11 -> {
                                        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
                                        if (prFile.getRows() == 0) {
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  daily item sales first");
                                        } else {
                                            salesManager.DeletePurchaseRequisition(ConsoleTextDisplay.deletePrMenu());
                                        }
                                    }
                                    case 12 -> {
                                        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
                                        if (prFile.getRows() == 0) {
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  Purchase Requisition first");
                                        } else {
                                            ArrayList<String> editDetail = ConsoleTextDisplay.editPrMenu(salesManager);
                                            salesManager.EditPurchaseRequisition(editDetail.get(0), editDetail.get(1), editDetail.get(2),editDetail.get(3));
                                        }

                                    }
                                    case 13 -> salesManager.displayPersonalRequisition();
                                    case 14 -> User.displayPurchaseOrder();
                                    case 15 -> {
                                        isLoop = false;
                                    }
                                }
                            }
                        } else if (user instanceof PurchaseManager  purchaseManager) {
                            attempt = 0;
                            boolean isLogout = false;
                            while (!isLogout) {
                                choice = ConsoleTextDisplay.purchaseManagerMenu();
                                switch (choice) {
                                    case 1 -> User.displayItem();
                                    case 2 -> User.displaySupplier();
                                    case 3 -> User.displayAllRequisition();
                                    case 4 ->{
                                        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
                                        if (prFile.getRows() == 0) {
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  Purchase Requisition first");
                                        } else {
                                            purchaseManager.AddPurchaseOrder(ConsoleTextDisplay.AddPurchaseOrder(purchaseManager));
                                        }
                                    }

                                    case 5 ->{
                                        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
                                        if (poFile.getRows() == 0) {
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  Purchase Order first");
                                        } else {
                                            purchaseManager.DeletePurchaseOrder(ConsoleTextDisplay.deletePurchaseOder());
                                        }
                                    }
                                    case 6 -> {
                                        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
                                        if (poFile.getRows() == 0) {
                                            ConsoleTextDisplay.printSystemMainTitle();
                                            System.out.println("no record founded,please add  Purchase Order first");
                                        } else {
                                            ArrayList<String> editDetail = ConsoleTextDisplay.editPoMenu();
                                            purchaseManager.EditPurchaseOrder(editDetail.get(0), editDetail.get(1), editDetail.get(2),editDetail.get(3),editDetail.get(4));
                                        }

                                    }
                                    case 7 -> User.displayPurchaseOrder();
                                    case 8 -> isLogout = true;

                                }
                            }
                        } else {
                            ConsoleTextDisplay.printHeadingInConsole("Incorrect Username or Password", 80);
                            attempt++;
                        }
                        if (attempt > 3) {
                            ConsoleTextDisplay.lockingSystem(10);
                        }
                    }
                }
            } else {
                break;
            }
        }
    }
}