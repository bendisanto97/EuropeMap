package struc;

public class User {
	protected String firstName, lastName, username, password;
	
	public User (String f, String l, String u, String p) {
		firstName = f;
		lastName = l;
		username = u;
		password = p;
	}
	
	public String getUsername() {return username;}
	
	public String getInformation() {return firstName + "\t" + lastName + "\t" + username + "\t" + password;}
	
	public String getName() {return firstName + " " + lastName;}
	
	public String getUserData() {return username + "\t" + password;}
}
