package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

/**
 * @author Alice Breton, Baptiste Rigondaud, Lo�c Poncet, Laora Heintz
 */
 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.datastax.driver.mapping.Mapper;

import resources.Product;
 
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
	
	@POST
	@Consumes("application/json")
	public Response addProduct(Product product) {
		return null;
	}
	
	@DELETE
	public Response deleteAll() {
		return null;
	}
	
	@DELETE
	@Path("/{name}")
	public Response deleteOne(@PathParam("name") String name) {
		return null;
	}

}