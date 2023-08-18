public class SalesManager extends User implements Manageable{
    public SalesManager(String userID, String userPassword, String role, String userName, String contactNumber, String email) {
        super(userID, userPassword, role, userName, contactNumber, email);
    }
    public SalesManager(){}
}