import java.util.Objects;

public class Item {
    private String itemID;
    private String itemName;
    private int quantity;
    private String expireDate;
    private double sellPrice;
    private double supplyPrice;

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getSupplyPrice() {
        return supplyPrice;
    }


    public Item(String itemID, String itemName, int quantity, String expireDate, double sellPrice, double supplyPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.expireDate = expireDate;
        this.sellPrice = sellPrice;
        this.supplyPrice = supplyPrice;
    }

    public static Item createItem(String itemID) {
        FileHandling fh = new FileHandling("Item.txt");
        String itemStr = fh.searchRow("itemID", itemID);
        if (itemStr != null) {
            String[] itemTemp = itemStr.split(",");
            return new Item(itemTemp[0], itemTemp[1], Integer.parseInt(itemTemp[2]), itemTemp[3], Double.parseDouble(itemTemp[4]), Double.parseDouble(itemTemp[5]));
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity && Double.compare(sellPrice, item.sellPrice) == 0 && Double.compare(supplyPrice, item.supplyPrice) == 0 && Objects.equals(itemID, item.itemID) && Objects.equals(itemName, item.itemName) && Objects.equals(expireDate, item.expireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemID, itemName, quantity, expireDate, sellPrice, supplyPrice);
    }
}