package service;

/**
 * @author Alice Breton, Baptiste Rigondaud, Loïc Poncet, Laora Heintz
 */
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
 
@Path("/products")
public class ProductService {
	
	@GET
	@Produces("application/json")
	public void getAllProducts() {
		// TODO
	}
	
	@GET
	@Path("/{name}")
	public void getProduct(@PathParam("name") String name) {
		// TODO
	}

}