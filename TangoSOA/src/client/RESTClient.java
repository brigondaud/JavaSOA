package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

/**
 * REST Client that can interrogates a REST web service containing groceries
 * receipts.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
public class RESTClient {
	
	public static String servicePath = "http://localhost:8080/rest/receipts";
	
	/**
	 * An instance of client to perform requests to the service.
	 */
	private Client client;
	
	/**
	 * The REST client is built using the URI to the web service.
	 */
	public RESTClient() {
		this.client = ClientBuilder.newClient();
	}
	
	/**
	 * Build a request in JSON format.
	 * 
	 * @return A post / get ready request.
	 */
	public Invocation.Builder build() {
		return client
				.target(servicePath)
				.request(MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Build a request in JSON format.
	 * 
	 * @param id The id of the resource to retrieve or delete
	 * @return A post / get ready request.
	 */
	public Invocation.Builder build(int id) {
		return client
				.target(servicePath)
				.path("/" + String.valueOf(id))
				.request(MediaType.APPLICATION_JSON);
	}
}
