package test;

import javax.ws.rs.core.Response;

public class TempResponse {
	private Response userResponse;
	private Response todoResponse;

	public Response getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(Response userResponse) {
		this.userResponse = userResponse;
	}

	public Response getTodoResponse() {
		return todoResponse;
	}

	public void setTodoResponse(Response todoResponse) {
		this.todoResponse = todoResponse;
	}
}