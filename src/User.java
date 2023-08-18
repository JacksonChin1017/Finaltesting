import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String userID;
    private String userPassword;
    private String role;
    private String userName;
    private String contactNumber;
    private String email;

    public User() {
    }

    public User(String userID, String userPassword, String role, String userName, String contactNumber, String email) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.role = role;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID) && Objects.equals(userPassword, user.userPassword);
    }

    public void displayPersonalRequisition() {
        FileHandling fileHandling = new FileHandling("PurchaseRequisition.txt");
        if (fileHandling.getRows() > 0) {
            fileHandling.printData("userID", this.getUserID());
        } else {
            System.out.println("You didn't post any purchase requisition");
        }
    }

    public static void displayAllRequisition() {
        FileHandling fileHandling = new FileHandling("PurchaseRequisition.txt");
        if (fileHandling.getRows() > 0) {
            fileHandling.printData();
        } else {
            System.out.println("No Purchase Requisition has been added");
        }
    }

    public static void displayPurchaseOrder() {
        FileHandling fileHandling = new FileHandling("PurchaseOrder.txt");
        if (fileHandling.getRows() > 0) {
            fileHandling.printData();
        } else {
            System.out.println("No Purchase Order has been added");
        }
    }

    public static void displayItem() {
        FileHandling fileHandling = new FileHandling("Item.txt");
        if (fileHandling.getRows() > 0) {
            fileHandling.printData();
        } else {
            System.out.println("No Item has been added");
        }
    }

    public static void displaySupplier() {
        FileHandling fileHandling = new FileHandling("Supplier.txt");
        if (fileHandling.getRows() > 0) {
            fileHandling.printData();
        } else {
            System.out.println("No Supplier has been added");
        }
    }

    public static void displayItemSales() {
        FileHandling fileHandling = new FileHandling("ItemSales.txt");
        if (fileHandling.getRows() > 0) {
            fileHandling.printData();
        } else {
            System.out.println("No Daily Sales has been added");
        }
    }

    public static User login(String userID, String userPassword) {
        try {
            FileInputStream fis = new FileInputStream("Users.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                User temp;
                Object obj = ois.readObject();
                if (obj != null) {
                    temp = (User) obj;
                    if (userID.equals(temp.getUserID()) && userPassword.equals(temp.getUserPassword())) {
                        return temp;
                    }
                } else {
                    return null;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", userName='" + userName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}