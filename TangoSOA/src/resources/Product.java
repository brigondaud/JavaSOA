package resources;

import com.datastax.driver.mapping.annotations.Table;

/**
 * A product has a name and a unit price.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
@Table(name = "products")
public class Product extends RESTResource<String> {
	
	/**
	 * Key: the name of the product.
	 */
	private String name;
	
	/**
	 * The product's price. It is represented as an integer to make it simple.
	 */
	private int price;
	
	/**
	 * A product is built using its name.
	 * 
	 * @param name Product's name.
	 */
	public Product(String name) {
		super(name);
		this.price = 0;
	}
	
	@Override
	public String getResource() {
		return "/products";
	}

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
		
}
