package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 */
@Path("/receipts")
public class ProductService {
	
	@GET
	@Produces("application/json")
	public void getAllProducts() {
		// TODO
	}
	
	@GET
	@Path("/{name}")
	public Receipt getProduct(@PathParam("name") String name) {
		// TODO
		return null;
	}
	

}
