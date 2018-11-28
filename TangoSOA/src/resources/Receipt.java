package resources;

import java.util.ArrayList;
import java.util.List;

/**
 * A receipt is made of several products with their quantities.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
public class Receipt {
	
	/**
	 * Key: the id of the receipt.
	 */
	private Integer id;
	
	/**
	 * Holds the information of the receipt: the products and the quantities bought.
	 */
	private List<Product> products;

	public Receipt() {
		super();
	}

	/**
	 * A Receipt is built using an id.
	 * 
	 * @param id The id (key) of the receipt.
	 */	
	public Receipt(Integer id) {
		this.id = id;
		this.products = new ArrayList<>();
	}

	public Receipt(Integer id, List<Product> products) {
		this.id = id;
		this.products = products;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
    public boolean equals(Object o) {
		if (!(o instanceof Receipt)) {
			return false;
		}
		Receipt otherReceipt = (Receipt) o;
		return this.id == otherReceipt.id;
	}
}
