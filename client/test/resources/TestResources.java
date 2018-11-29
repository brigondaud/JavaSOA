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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestResources {
	
	/**
	 * The client generating the requests.
	 */
	private RESTClient client;
	
	public TestResources() {
		this.client = new RESTClient();
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
            r = new Receipt(i);
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
		List<Product> products = new ArrayList<>();
		Product p = new Product("tomate", 1, 2);
		products.add(p);
		Receipt r = new Receipt(42, products);
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
		Receipt r = new Receipt(42);
		Receipt r2 = new Receipt(42);
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
		Receipt r = new Receipt(42);
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
}
