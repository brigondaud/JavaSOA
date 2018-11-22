package resources;

import java.util.HashMap;
import java.util.Map;

import com.datastax.driver.mapping.annotations.Table;

/**
 * A receipt is made of several products with their quantities.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
@Table(name = "receipts")
public class Receipt extends RESTResource<Integer> {
	
	/**
	 * Key: the id of the receipt.
	 */
	private Integer id;
	
	/**
	 * Holds the information of the receipt: the products and the quantities bought.
	 */
	private Map<Product, Integer> products;
	
	/**
	 * A Receipt is built using an id.
	 * 
	 * @param id The id (key) of the receipt.
	 */
	public Receipt(Integer id) {
		super(id);
		this.products = new HashMap();
	}
	
	@Override
	public String getResource() {
		return "/receipts";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Product, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<Product, Integer> products) {
		this.products = products;
	}
}
