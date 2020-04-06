package test;

import javax.ws.rs.core.Response;

public class TempException extends Exception {
	private int statusCode;
	private String responseBody;

	public TempException() {
		super();
	}

	public TempException(Response response) {
		super();
		this.statusCode = response.getStatus();
		this.responseBody = response.readEntity(String.class);
	}

	public TempException(String message) {
		super(message);
	}

	public TempException(String message, Throwable cause) {
		super(message, cause);
	}

	public TempException(Throwable cause) {
		super(cause);
	}

	protected TempException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}