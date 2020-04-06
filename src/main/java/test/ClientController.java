package test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/temp")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class ClientController {

	@Inject
	TempRepository tempRepository;

	@GET
	public Response getConsolidateReport() throws TempException {
		return Response.ok().entity(tempRepository.getConsolidateResponse()).build();
	}
}
