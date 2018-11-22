package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import client.RESTClient;

/**
 * A series of test to create and get products from the web service.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
class TestProducts {
	
	/**
	 * The client to connect to the REST service.
	 */
	RESTClient client;
	
	@BeforeClass
	void initTest() {
		this.client = new RESTClient();
	}
	
	@Test
	void postAndGetProduct() {
		fail("Not yet implemented");
	}
	
	@Test
	void postAndGetReceipt() {
		fail("Not yet implemented");
	}

}
