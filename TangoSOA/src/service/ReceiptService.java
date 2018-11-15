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
	

}
