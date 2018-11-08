package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * REST Client that can interrogates a REST web service containing groceries
 * receipts.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
public class RESTClient {
	
	/**
	 * The path to the REST web service
	 */
	private String servicePath;
	
	/**
	 * An instance of client to perform requests to the service.
	 */
	private Client client;
	
	/**
	 * The REST client is built using the URI to the web service.
	 * 
	 * @param servicePath The URI to the web service.
	 */
	public RESTClient(String servicePath) {
		this.servicePath = servicePath;
		this.client = ClientBuilder.newClient();
	}
}
