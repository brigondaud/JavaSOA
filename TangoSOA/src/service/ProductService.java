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

import repository.Repository;
import resources.Product;
 
@Path("/products")
public class ProductService {
	
	private Repository<Product> repository;
	
	public ProductService() {
		this.repository = new Repository<Product>(Product.class);
	}
	
	@GET
	@Produces("application/json")
	public void getAllProducts() {
		// TODO
	}
	
	@GET
	@Path("/{name}")
	public Product getProduct(@PathParam("name") String name) {
		Product product = new Product(name);
		return this.repository.getOne(product);
	}
	
	@POST
	@Consumes("application/json")
	public Response addProduct(Product product) {
		this.repository.save(product);
		return Response.status(Response.Status.CREATED.getStatusCode())
				.header(
						"Location", 
						String.format("%s", product.getResourcePath())).build();
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