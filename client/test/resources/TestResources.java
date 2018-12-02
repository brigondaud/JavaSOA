package resources;

import client.RESTClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestResources {
	
	/**
	 * The client generating the requests.
	 */
	private RESTClient client;
	
	public TestResources() {
		this.client = new RESTClient(System.getProperty("webServiceIP"));
	}

	/**
	 * Delete all the entries in the database
	 */
	@BeforeEach
	void DeleteEntries() {
		Response response = this.client.build().delete();
        assertEquals(204, response.getStatus());
	}

	/**
	 * Create multiple receipt and then retrieve them.
	 */
	@Test
	void createAndGetShouldBeEquals() {
		List<Receipt> receipts = new ArrayList<>();
		Receipt r;
        for (int i = 0; i < 100; i++) {
            r = this.createValidReceipt(i);
            receipts.add(r);
		    Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		    assertEquals(201, response.getStatus());
        }
		List<Receipt> getResult = this.client.build().get(new GenericType<List<Receipt>>() {});
		assertEquals(receipts, getResult);
	}
	
	/**
	 * Create and gets a receipt.
	 */
	@Test
	void createAndGetSameReceiptShouldBeEquals() {
		Receipt r = this.createValidReceipt(42);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		Receipt r2 = this.client.build(r.getId()).get(Receipt.class);
		assertEquals(r, r2);
	}

	/**
	 * Create two receipts with same ID, The second addition should not work.
	 */
	@Test
	void createTwoReceiptsWithSameIdShouldNotWork() {
		Receipt r = this.createValidReceipt(42);
		Receipt r2 = this.createValidReceipt(42);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		response = this.client.build().post(Entity.entity(r2, MediaType.APPLICATION_JSON));
		assertNotEquals(201, response.getStatus());
	}

    /**
     * Create a receipt and then delete it
     */
    @Test
    void createAndDeleteShouldWork() {
		Receipt r = this.createValidReceipt(42);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		response = this.client.build(r.getId()).delete();
        assertEquals(204, response.getStatus());
    }

	/**
	 * Create and get a receipt, then check if the products are valid
	 */
	@Test
	void productsShouldBeEquals() {
		List<Product> products = new ArrayList<>();
		Product p1 = new Product("tomate", 1, 2);
		Product p2 = new Product("pdt", 2, 4);
		products.add(p1);
		products.add(p2);
		Receipt r = new Receipt(42, products);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		Receipt r2 = this.client.build(r.getId()).get(Receipt.class);
		assertEquals(products, r2.getProducts());
	}

	/**
	 * Create a receipt with invalid Product name
	 */
	@Test
	void createReceiptWithInvalidProductNameShouldNotWork() {
		List<Product> products = new ArrayList<>();
		Product product = new Product("", 10, 11);
		products.add(product);
		Receipt r = new Receipt(42, products);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
	}

	/**
	 * Create a receipt with invalid Product quantity
	 */
	@Test
	void createReceiptWithInvalidProductQuantityShouldNotWork() {
		List<Product> products = new ArrayList<>();
	    Product product = new Product("tomate", 0, 0);
	    products.add(product);
		Receipt r = new Receipt(42, products);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
	}

	/**
	 * Create a receipt with invalid Product price
	 */
	@Test
	void createReceiptWithInvalidProductPriceShouldNotWork() {
		List<Product> products = new ArrayList<>();
		Product product = new Product("tomate", -1, 1);
		products.add(product);
		Receipt r = new Receipt(42, products);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
	}

	/**
	 * Create a receipt with invalid Product list
	 */
	@Test
	void createReceiptWithInvalidProductListShouldNotWork() {
		Receipt r = new Receipt(42);
		Response response = this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
	}

	/**
	 * Create a valid Receipt with one product
	 *
	 * @param id The id to give to the created Receipt
	 * @return A valid Receipt Object, ready to be send to the API
	 */
	private Receipt createValidReceipt(int id) {
		List<Product> products = new ArrayList<>();
		Product p = new Product("tomate", 1, 2);
		products.add(p);
		return new Receipt(id, products);
	}

	private void populateDatabase() {
		List<Product> availableProducts = new ArrayList<>();
		Product product1 = new Product("tomate", 4, 4);
		Product product2 = new Product("carotte", 2, 2);
		Product product3 = new Product("pomme de terre", 1, 8);
		Product product4 = new Product("salade", 1, 1);
		Product product5 = new Product("poivron", 1, 6);
		Product product6 = new Product("ciboulette", 1, 2);
		Product product7 = new Product("poireau", 1, 3);
		Product product8 = new Product("menthe", 1, 4);
		Product product9 = new Product("poire", 1, 6);
		Product product10 = new Product("framboise", 1, 2);
		availableProducts.add(product1);
		availableProducts.add(product2);
		availableProducts.add(product3);
		availableProducts.add(product4);
		availableProducts.add(product5);
		availableProducts.add(product6);
		availableProducts.add(product7);
		availableProducts.add(product8);
		availableProducts.add(product9);
		availableProducts.add(product10);
		// Create 2000 receipts
		for (int i = 0; i < 2000; i++) {
			List<Product> products = this.createRandomProductList(availableProducts);
			Receipt r = new Receipt(i, products);
			this.client.build().post(Entity.entity(r, MediaType.APPLICATION_JSON));
		}
	}


	/**
	 * Create a random list of products from a given available products list
	 *
	 * @param available A list containing all the available products
	 * @return A list containing a random number of randomly chosen products
	 */
	 private List<Product> createRandomProductList(List<Product> available) {
		List<Product> res = new ArrayList<>();
		Random random = new Random();
		int size = random.nextInt(9) + 1;
	    for (int i = 0; i < size; i++) {
	    	int indexProduct = random.nextInt(available.size());
	    	Product product = available.get(indexProduct);
	    	res.add(product);
		}
		return res;
	}
}
