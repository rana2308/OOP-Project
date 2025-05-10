package pharmacymanagementsystem;
import java.util.*;
import java.text.SimpleDateFormat;
enum Gender {
    MALE, FEMALE
}

public abstract class User {
	protected String username;
    protected String password;
    protected Date dateOfBirth;
    protected Gender gender;

 public User(String username, String password, Date dateOfBirth, Gender gender) {
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}

	public String getUsername() {
        return username;
    }
    
    public boolean checkPassword(String pwd) {
        return this.password.equals(pwd);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "User: " + username + ", DOB: " + sdf.format(dateOfBirth);
    }
}
