package resources;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import client.RESTClient;

class TestResources {
	
	/**
	 * The client generating the requests.
	 */
	private RESTClient client;
	
	public TestResources() {
		this.client = new RESTClient();
	}
	
	/**
	 * Creates and gets a product.
	 */
	@Test
	void createAndGetSameProductShouldBeEquals() {
		Product p = new Product("tomate", 1);
		client.build(p).post(Entity.entity(p, MediaType.APPLICATION_JSON));
		Product p2 = client.build(p).get(Product.class);
		assertEquals(p, p2);
	}

}
