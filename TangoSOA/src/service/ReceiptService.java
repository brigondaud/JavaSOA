package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import resources.Receipt;

/**
 * @author Alice Breton, Laora Heintz, Lo�c Poncet, Baptiste Rigondaud
 */

@Path("/receipts")
public class ReceiptService {
	
	@GET
	@Path("/test")
	@Produces("text/plain")
	public Response HelloWorld() {
		String message = "Hello World!";
		return Response.status(200).entity(message).build();
	}
	
	@GET
	@Produces("application/json")
	public void getAllReceipts() {
		// TODO
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public void getReceipt(@PathParam("id") Integer id) {
		// TODO
	}
	
	@POST
	@Consumes("application/json")
	public Response addReceipt(Receipt receipt) {
		return null;
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
