package test;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class TempRepositoryImpl implements TempRepository {

	@Inject
	@RestClient
	private TempClient tempClient;

	public TempResponse getConsolidateResponse() throws TempException {
		TempResponse tempResponse = new TempResponse();
		final CountDownLatch latch = new CountDownLatch(2);
		final AtomicReference<Throwable> throwableAtomicReference = new AtomicReference<>();
		tempClient.getTodos().whenComplete((response, throwable) -> {
			if (throwable == null) {
				tempResponse.setTodoResponse(response);
			} else {
				throwableAtomicReference.set(throwable);
			}
			latch.countDown();
		});
		tempClient.getUsers().whenComplete((response, throwable) -> {
			if (throwable == null) {
				tempResponse.setUserResponse(response);
			} else {
				throwableAtomicReference.set(throwable);
			}
			latch.countDown();
		});


		try {
			latch.await();
		} catch (InterruptedException ex) {
			throw new WebApplicationException(ex, 500);
		}

		Throwable t = throwableAtomicReference.get();
		if (t != null) {
			throw new WebApplicationException("Failure in downstream service", t, 500);
		}

		return tempResponse;
	}
}

