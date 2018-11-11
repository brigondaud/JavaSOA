package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import model.Facture;

/**
 * @author Alice Breton, Laora Heintz, Loïc Poncet, Baptiste Rigondaud
 */
@Path("/factures")
public class FactureService {
	
	@GET
	@Path("/test")
	@Produces("text/plain")
	public Response HelloWorld() {
		String message = "Hello World!";
		return Response.status(200).entity(message).build();
	}
	
	@GET
	@Produces("application/json")
	public void getAllFactures() {
		// TODO
	}
	
	@GET
	@Path("/{id}")
	public Facture getFacture(@PathParam("id") int id) {
		// TODO
		return null;
	}
	

}
