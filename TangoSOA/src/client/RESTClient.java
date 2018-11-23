package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import resources.RESTResource;

/**
 * REST Client that can interrogates a REST web service containing groceries
 * receipts.
 * 
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 *
 */
public class RESTClient {
	
	public static String servicePath = "http://localhost:8080/rest";
	
	/**
	 * An instance of client to perform requests to the service.
	 */
	private Client client;
	
	/**
	 * The REST client is built using the URI to the web service.
	 * 
	 * @param servicePath The URI to the web service.
	 */
	public RESTClient() {
		this.client = ClientBuilder.newClient();
	}
	
	/**
	 * Build a request in JSON format.
	 * 
	 * @param resource The resource with which the request is built.
	 * @return A post / get ready request.
	 */
	public Invocation.Builder build(RESTResource resource) {
		return client
				.target(getResourcePath(resource.path()))
				.path(resource.getKey().toString())
				.request(MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Builds a path to a resource.
	 * 
	 * @return The path to the resource.
	 */
	public static String getResourcePath(String resourcePath) {
		StringBuilder sb = new StringBuilder();
		sb.append(servicePath);
		sb.append(resourcePath);
		return sb.toString();
	}
}
