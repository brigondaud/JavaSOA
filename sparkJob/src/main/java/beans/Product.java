package beans;

import java.io.Serializable;

/**
 * A Product bean such as stored as a field of the receipt.
 * 
 * @author Baptiste Rigondaud & Loïc Poncet & Alice Breton & Laora Heintz
 */
public class Product implements Serializable {
    
    /**
     * The Product's name.
     */
    private String name;
    
    /**
     * The product's price.
     */
    private int price;
    
    /**
     * The product's quantity.
     */
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ":" + price + ":" + quantity;
    }
}
