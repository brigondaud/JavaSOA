package beans;

import java.io.Serializable;
import java.util.List;

/**
 * A Receipt bean such as stored in the receipt database.
 * 
 * @author Baptiste Rigondaud & Loïc Poncet & Alice Breton & Laora Heintz
 */
public class Receipt implements Serializable {
    
    /**
     * The receipt's id.
     */
    private int id;
    
    /**
     * The receipt's products list.
     */
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return id + ":" + products.size();
    }
}
