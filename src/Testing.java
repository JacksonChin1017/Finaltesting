import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        Admin admin = new Admin();

//        admin.registration("A001", "123", "Admin", "Jerry", "012918291",
//                "wengkang@gmail.com");
//        admin.registration("P001", "123", "PurchaseManager", "Jerry", "012918291",
//                "wengkang@gmail.com");
//        admin.registration("S001", "123", "SalesManager", "Jerry", "012918291",
//                "wengkang@gmail.com");
//        PurchaseManager purchaseManager = new PurchaseManager("M001", "123", "purchaseManager", "M001", "12345", "1234");
//
//        purchaseManager.AddPurchaseOrder(ConsoleTextDisplay.AddPurchaseOrder(purchaseManager));

        FileHandling f = new FileHandling("Item.txt");
        System.out.println(f.searchRow("itemID","I001"));
    }
}