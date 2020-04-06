package test;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class TempExceptionResponseMapper implements ResponseExceptionMapper<TempException> {

	private static final Logger log = Logger.getLogger("TempExceptionResponseMapper");

	@Override
	public TempException toThrowable(Response response) {
		return new TempException(response);
	}
}