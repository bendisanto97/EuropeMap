package struc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class City {
	private String cityName, country;
	private ArrayList<String> cityAttraction;
	private ArrayList<Integer> cityStatistics;
	private HashMap<Integer, Attraction> attractions;
	private int cityPopulation;
	private double cityArea, cityDensity, positionX, positionY;
	
	public City (String name, double posX, double posY, int pop, double area, double dens, String s) {
		cityName = name;
		cityPopulation = pop;
		cityArea = area;
		cityDensity = dens;
		positionX = posX;
		positionY = posY;
		country = s;
		attractions = Attraction.getAttracCity(name);
	}
	
	public HashMap<Integer, Attraction> getAttraction() {return attractions;}
	
	public double getPosX() {return positionX;}
	
	public double getPosY() {return positionY;}
	
	public String getCountry() {return country;}
	
	public String getName() {return cityName;}
	
	public int getPopulation() {return cityPopulation;}
	
	public double getArea() {return cityArea;}
	
	public double getDensity() {return cityDensity;}
	
	public ArrayList<Integer> getCityStatistics() {return cityStatistics;}
	
	public ArrayList<String> getAttractions() {return cityAttraction;}
	
	public void setStatistics(ArrayList<Integer> stats) {cityStatistics = stats;}
	
	public void setAttractions(String s1, String s2, String s3) {
		cityAttraction = new ArrayList<String>();
		cityAttraction.add(s1);cityAttraction.add(s2);cityAttraction.add(s3);
	}
	
	public static HashMap<String, City> getHashMapCities(String country){
    	HashMap<String, City> mapFileContents = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_cities.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
                String[] parts = line.split("\t");
                ArrayList<Integer> st = new ArrayList<Integer>();
                st.add(Integer.parseInt(parts[10].trim()));st.add(Integer.parseInt(parts[11].trim()));
                st.add(Integer.parseInt(parts[12].trim()));st.add(Integer.parseInt(parts[13].trim()));
                City c = new City(parts[0].trim(), Double.parseDouble( parts[1].trim() ), Double.parseDouble( parts[2].trim() ), Integer.parseInt( parts[3].trim() ), Double.parseDouble( parts[4].trim() ), Double.parseDouble( parts[5].trim() ), parts[6].trim());
                c.setStatistics(st);c.setAttractions(parts[7].trim(), parts[8].trim(), parts[9].trim());
                if (parts[6].trim().equals(country)) {
                	mapFileContents.put(c.getName(), c);
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
	
	public static HashMap<String, City> getHashMapAllCities(){
    	HashMap<String, City> mapFileContents = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_cities.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
                String[] parts = line.split("\t");
                ArrayList<Integer> st = new ArrayList<Integer>();
                st.add(0, Integer.parseInt(parts[10].trim()));st.add(1, Integer.parseInt(parts[11].trim()));
                st.add(2, Integer.parseInt(parts[12].trim()));st.add(3, Integer.parseInt(parts[13].trim()));
                City c = new City(parts[0].trim(), Double.parseDouble( parts[1].trim() ), Double.parseDouble( parts[2].trim() ), Integer.parseInt( parts[3].trim() ), Double.parseDouble( parts[4].trim() ), Double.parseDouble( parts[5].trim() ), parts[6].trim());
                c.setStatistics(st);c.setAttractions(parts[7].trim(), parts[8].trim(), parts[9].trim());mapFileContents.put(c.getName(), c);
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
	
	public String toStringue() {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.UK);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setDecimalFormatSymbols(symbols);
		
		String s = "City: " + cityName + "\nPopulation: " + formatter.format(getPopulation()) + " people\nArea: " + formatter.format(getArea()) + " km2\nDensity: " + formatter.format(getDensity()) + "/km2";
		return s;
	}
	
	public String completeToStringue() {
		String s = "";
		s += getName() + "\t" + getPosX() + "\t" + getPosY() + "\t" + getPopulation() + "\t" + getArea() + "\t" + getDensity() + "\t" + country;
		for (int i = 0 ; i < cityAttraction.size() ; i++) {
			s += "\t" + cityAttraction.get(i).toString().trim();
		}
		for (int i = 0 ; i < cityStatistics.size() ; i++) {
			s += "\t" + cityStatistics.get(i).toString().trim();
		}
		return s;
	}
}
