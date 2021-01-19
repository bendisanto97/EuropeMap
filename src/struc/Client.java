package struc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Client extends User {
	
	private String city;
	private int age, loginCount;
	
	public Client (String f, String l, String u, String p, int a, String c, int lc) {
		super(f, l, u, p);
		age = a;
		city = c;
		loginCount = lc;
	}
	
	public String getCity() {return city;}
	
	public int getAge() {return age;}
	
	public void setLoginCount() {loginCount++;}
	
	public int getLoginCount() {return loginCount;}
	
	public String getCInformation() {
		return this.getInformation() + "\t" + getCity() + "\t" + getAge() + "\t" + getLoginCount();
	}
	
	public static HashMap<String, Client> getClientHashMap(){
		HashMap<String, Client> list = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_clients.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
            	String[] parts = line.split("\t");
                Client u = new Client(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[5]), parts[4], Integer.parseInt(parts[6].trim()));
                list.put(u.getUsername(), u);
            }             
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try { 
                    br.close(); 
                } catch(Exception e){};
            }
        }
        return list;
	}
}
