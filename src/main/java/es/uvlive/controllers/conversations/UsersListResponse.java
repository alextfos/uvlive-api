package es.uvlive.controllers.conversations;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.uvlive.controllers.BaseResponse;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "users", propOrder = {
    "users"
})
@XmlRootElement(name = "response")
public class UsersListResponse extends BaseResponse {
	
    @XmlElement(name = "users")
	Collection<UserResponse> users;
	
	public UsersListResponse() {
		users = new ArrayList<UserResponse>();
	}

	public Collection<UserResponse> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserResponse> users) {
		this.users = users;
	}

}
