package service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import repository.ReceiptRepository;
import resources.Receipt;

/**
 * @author Alice Breton, Laora Heintz, Lo�c Poncet, Baptiste Rigondaud
 */

@Path("/receipts")
public class ReceiptService {
	
	private ReceiptRepository receiptRepository;
	
	public ReceiptService() {
		this.receiptRepository = new ReceiptRepository();
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
	public List<Receipt> getAllReceipts() {
		return this.receiptRepository.getAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Receipt getReceipt(@PathParam("id") Integer id) {
		return this.receiptRepository.getOne(id);
	}
	
	@POST
	@Consumes("application/json")
	public Response addReceipt(Receipt receipt) {
		this.receiptRepository.save(receipt);
		return Response.status(Response.Status.CREATED.getStatusCode())
				.header(
						"Location", 
						"/rest" + receipt.path()).build();
	}
	
	@DELETE
	public Response deleteAll() {
		this.receiptRepository.deleteAll();
		return Response.status(Response.Status.NO_CONTENT.getStatusCode())
				.build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteOne(@PathParam("id") Integer id) {
		if (this.receiptRepository.getOne(id) == null) {
			return Response.status(Response.Status.NOT_FOUND.getStatusCode())
					.build();
		} else {
			this.receiptRepository.deleteOne(id);
			return Response.status(Response.Status.NO_CONTENT.getStatusCode())
					.build();
		}
	}
	

}
