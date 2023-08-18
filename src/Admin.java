//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Admin extends User implements Serializable, Purchasable, Manageable {
    public Admin(String userID, String userPassword, String role, String userName, String contactNumber, String email) {
        super(userID, userPassword, role, userName, contactNumber, email);
    }

    public Admin() {
    }

    public void registration(String userID, String userPassword, String role, String userName, String contactNumber, String email) {
        User newUser;
        if (role.equals("Admin")) {
            newUser = new Admin(userID, userPassword, role, userName, contactNumber, email);
        } else if (role.equals("SalesManager")) {
            newUser = new SalesManager(userID, userPassword, role, userName, contactNumber, email);
        } else {
            if (!role.equals("PurchaseManager")) {
                System.out.println("Invalid role. Registration failed.");
                return;
            }

            newUser = new PurchaseManager(userID, userPassword, role, userName, contactNumber, email);
        }

        List<User> users = new ArrayList<>();
        boolean exist = false;

        try {
            FileInputStream fis = new FileInputStream("Users.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            User temp;
            do {
                Object obj;
                do {
                    obj = ois.readObject();
                } while(obj == null);

                temp = (User)obj;
                users.add(temp);
            } while(!temp.equals(newUser));

            exist = true;
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException var15) {
        }

        if (!exist) {
            try {
                users.add(newUser);
                FileOutputStream fos = new FileOutputStream("Users.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                Iterator var18 = users.iterator();

                while(var18.hasNext()) {
                    User tempUser = (User)var18.next();
                    oos.writeObject(tempUser);
                }

                oos.close();
                fos.close();
            } catch (IOException var14) {
                System.out.println("Error occurred while registering account");
            }
        } else {
            System.out.println("Account Already Exist, Try to choose another User ID");
        }

    }
}
