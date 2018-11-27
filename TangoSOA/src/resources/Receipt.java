package resources;

import java.util.List;
import java.util.ArrayList;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.PartitionKey;

/**
 * A receipt is made of several products with their quantities.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
@Table(keyspace = "ks", name = "receipts")
public class Receipt extends RESTResource<Integer> {
	
	/**
	 * Key: the id of the receipt.
	 */
    @PartitionKey
	private Integer id;
	
	/**
	 * Holds the information of the receipt: the products and the quantities bought.
	 */
	@Frozen
	private List<Product> products;
	
	/**
	 * A Receipt is built using an id.
	 * 
	 * @param id The id (key) of the receipt.
	 */	
	public Receipt(Integer id) {
		super(id);
		this.products = new ArrayList<>();
	}
	
	public Receipt() {
		super();
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
	public String path() {
		return "/receipts";
	}
}
