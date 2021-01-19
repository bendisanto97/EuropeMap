package struc;

public class Review {
	private Client c;
	private String comment, total;
	private static final String OUTLINE = "REVIEW POSTED BY ";
	
	public Review(String com, String clientKey) {
		comment = com;
		c = Client.getClientHashMap().get(clientKey);
		total = OUTLINE;
		setTotal();
	}
	
	public void setTotal() {
		String s = "";
		s += c.getUsername() + ", from " + c.getCity() + ", Age " + c.getAge();
		s += "\nOn " + java.time.LocalDate.now() + "\n\n";
		s += comment;
		total += s;
	}
	
	public String getTotal() {
		return total;
	}
}
