package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import repository.Repository;
import resources.Product;
import resources.Receipt;

/**
 * @author Alice Breton, Laora Heintz, Lo�c Poncet, Baptiste Rigondaud
 */

@Path("/receipts")
public class ReceiptService {
	
	private Repository<Receipt> repository;
	
	public ReceiptService() {
		this.repository = new Repository<Receipt>(Receipt.class);
	}
	
	@GET
	@Path("/test")
	@Produces("text/plain")
	public Response HelloWorld() {
		String message = "Hello World!";
		return Response.status(200).entity(message).build();
	}
	
	@GET
	@Produces("application/json")
	public Response getAllReceipts() {
		return null;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Receipt getReceipt(@PathParam("id") Integer id) {
		Receipt receipt = new Receipt(id);
		return this.repository.getOne(receipt);
	}
	
	@POST
	@Consumes("application/json")
	public Response addReceipt(Receipt receipt) {
		this.repository.save(receipt);
		return Response.status(Response.Status.CREATED.getStatusCode())
				.header(
						"Location", 
						String.format("%s", receipt.getResourcePath())).build();
	}
	
	@DELETE
	public Response deleteAll() {
		return null;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteOne(@PathParam("id") Integer id) {
		return null;
	}
	

}
