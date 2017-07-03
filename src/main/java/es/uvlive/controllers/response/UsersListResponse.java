package es.uvlive.controllers.response;

import java.util.ArrayList;
import java.util.Collection;


public class UsersListResponse extends BaseResponse {

	Collection<UserResponse> users;
	
	public UsersListResponse() {
		users = new ArrayList<>();
	}

	public Collection<UserResponse> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserResponse> users) {
		this.users = users;
	}

}
