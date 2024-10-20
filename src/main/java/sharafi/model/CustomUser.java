package sharafi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "costum_user")
public class CustomUser {
	
    public CustomUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
    
    public CustomUser() {
    	
    }

	@Id
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "CustomUser [username=" + username + ", password=" + password + "]";
	}
}