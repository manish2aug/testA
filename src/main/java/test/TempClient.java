package test;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
@RegisterProvider(TempExceptionResponseMapper.class)
public interface TempClient {

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<Response> getTodos() throws TempException;

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<Response> getUsers() throws TempException;

}
