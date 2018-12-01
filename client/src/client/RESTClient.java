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
	
	/**
	 * The path to the REST API.
	 */
	private String servicePath;

	private String mostUsedProductPath;
	
	/**
	 * An instance of client to perform requests to the service.
	 */
	private Client client;
	
	/**
	 * The REST client is built using the the web service IP.
         * 
        * @param webServiceIp The IP address of the web service.
	 */
	public RESTClient(String webServiceIp) {
                setServiceIp(webServiceIp);
		this.client = ClientBuilder.newClient();
	}

	/**
	 * Build a request in JSON format to the REST API.
	 *
	 * @return A post / get ready request.
	 */
	public Invocation.Builder buildMostUsedProduct() {
		return client
				.target(mostUsedProductPath)
				.request(MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Build a request in JSON format to the REST API.
	 * 
	 * @return A post / get ready request.
	 */
	public Invocation.Builder build() {
		return client
				.target(servicePath)
				.request(MediaType.APPLICATION_JSON);
	}
	
	/**
	 * Build a request in JSON format to REST API.
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

        /**
         * Setter for the web service ip.
         * @param webServiceIp 
         */
        private void setServiceIp(String webServiceIp) {
            if(webServiceIp == null) throw new IllegalArgumentException("Bad web service IP.");
            this.servicePath = "http://" + webServiceIp + ":8000/rest/receipts";
			this.mostUsedProductPath = "http://" + webServiceIp + ":8000/mostUsedProduct";
        }
}
