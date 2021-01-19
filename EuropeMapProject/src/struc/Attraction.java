package struc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;

public class Attraction {
	private String name, city, website, imgURL;
	private ArrayList<String> facts;
	private int id;
	
	public Attraction (int idt, String n, String c, String i, ArrayList<String> f) {
		id = idt;
		name = n;
		city = c;
		imgURL ="src\\pictures\\" + i + ".jpg";
		facts = f;
	}
	
	public String getAttName() {return name;}
	
	public int getAttId() {return id;}
	
	public String getAttCity() {return city;}
	
	public String getWebsite() {return website;}
	
	public Image getImg() throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream(imgURL);
		Image image = new Image(inputStream);
		return image;
	}
	
	public ArrayList<String> getFacts() {return facts;}
	
	public static HashMap<Integer, Attraction> getAttractionsCity(String cit) {
		HashMap<Integer, Attraction> mapFileContents = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_tourism.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
                String[] parts = line.split("\t");
                ArrayList<String> s = new ArrayList<>();
                s.add(0, parts[3]);s.add(1, parts[4]);s.add(2, parts[5]);s.add(parts[6].trim());
                Attraction a = new Attraction(Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[7].trim(), s);
                if (parts[2].trim().equals(cit)) {
                	mapFileContents.put(a.getAttId(), a);
                }
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
        return mapFileContents;
    }
	
	public static HashMap<Integer, Attraction> getAttracCity(String cit) {
		return null;
	}
	
	public static HashMap<String, Attraction> getAllAttractions() {
		HashMap<String, Attraction> mapFileContents = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_tourism.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
                String[] parts = line.split("\t");
                ArrayList<String> s = new ArrayList<>();
                s.add(0, parts[3]);s.add(1, parts[4]);s.add(2, parts[5]);s.add(parts[6].trim());
                Attraction a = new Attraction(Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[7].trim(), s);
	            mapFileContents.put(a.getAttName(), a);
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
        return mapFileContents;
    }
}
