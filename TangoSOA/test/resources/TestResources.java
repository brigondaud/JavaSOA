package resources;

import client.RESTClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
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
		this.client.build().delete();
	}
	
	/**
	 * Creates and gets a receipt.
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
	 * Creates two receipts with same ID, The second addition should not work.
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

}
