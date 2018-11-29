package resources;

/**
 * A product has a name and a unit price.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
public class Product {
	
	/**
	 * The name of the product.
	 */
	private String name;
	
	/**
	 * The product's price. It is represented as an integer to make it simple.
	 */
	private int price;

    private int quantity;

    public Product() {
        super();
    }
	
	/**
	 * A product is built using its name.
	 * 
	 * @param name Product's name.
	 */
	public Product(String name) {
		this.name = name;
		this.price = 0;
	}
	
	public Product(String name, int price) {
		this.name = name;
		setPrice(price);
        this.quantity = 0;
	}

	public Product(String name, int price, int quantity) {
		this.name = name;
		setPrice(price);
        this.quantity = quantity;
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

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }    

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }
        Product other = (Product) o;
        return this.name.equals(other.getName())
                && this.price == other.getPrice()
                && this.quantity == other.getQuantity();
    }
}
