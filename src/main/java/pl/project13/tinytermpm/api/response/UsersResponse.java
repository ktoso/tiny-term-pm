package pl.project13.tinytermpm.api.response;

import pl.project13.tinytermpm.api.model.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
public class UsersResponse {

    @XmlElement(name = "user")
    List<User> _users;

    public UsersResponse() {
    }

    public List<User> getUsers() {
        return _users;
    }

    public void setUsers(List<User> users) {
        this._users = users;
    }

    @Override
    public String toString() {
        return "UsersResponse{" +
                "_project=" + _users +
                '}';
    }
}
