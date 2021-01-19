package struc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Manager extends User {
	private int id;
	
	public Manager(String f, String l, String u, String p, int i) {
		super(f, l, u, p);
		id = i;
	}
	
	public int getIdent() {return id;}
	
	public String getMInformation() {
		return this.getInformation() + "\t" + getIdent();
	}
	
	public static HashMap<String, Manager> getManagerHashMap(){
		HashMap<String, Manager> list = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_manager.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
            	String[] parts = line.split("\t");
                Manager m = new Manager(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                list.put(m.getUsername(), m);
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
