package struc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javafx.scene.image.Image;

public class Country {
	private String countryName, imgURL, img;
	private double positionX, positionY, countryArea;
	private int countryPopulation;
	private City capital;
	private HashMap<String, City> cities;
	private ArrayList<Integer> countryStatistics;
	
	public Country (String country, double posX, double posY, int pop, double area, String cap, int capPop, double capArea, String u) {
		countryName = country;
		positionX = posX;
		positionY = posY;
		countryPopulation = pop;
		countryArea = area;
		img = u;
		imgURL = "src\\flags\\flag-" + u + ".png";
		capital = City.getHashMapCities(country).get(cap);
		cities = struc.City.getHashMapCities(countryName);
	}
	
	public String getName() {return countryName;}
	
	public City getCapital() {return capital;}
	
	public void setCountryStatistics(ArrayList<Integer> st) {countryStatistics = st;}
	
	public double getPosX() {return positionX;}
	
	public double getPosY() {return positionY;}
	
	public int getPopulation() {return countryPopulation;}
	
	public double getCountryArea() {return countryArea;}
	
	public ArrayList<Integer> getCountryStatistics(){return countryStatistics;}
	
	public String getFlagImg() {return img;}
	
	public HashMap<String, City> getHashMapCities(){return cities;}
	
	public Image getFlag() throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream(imgURL);
		Image image = new Image(inputStream);
		return image;
	}
	
	public static HashMap<String, Country> getHashMapCountries(){
    	HashMap<String, Country> mapFileContents = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File("src\\data\\data_countries.txt");
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
                String[] parts = line.split("\t");
                ArrayList<Integer> st = new ArrayList<Integer>();
                st.add(Integer.parseInt(parts[9].trim()));st.add(Integer.parseInt(parts[10].trim()));
                st.add(Integer.parseInt(parts[11].trim()));st.add(Integer.parseInt(parts[12].trim()));
                Country c = new Country(parts[0].trim(), Double.parseDouble( parts[1].trim() ), Double.parseDouble( parts[2].trim() ), Integer.parseInt( parts[3].trim() ), Double.parseDouble( parts[4].trim() ), parts[5].trim(), Integer.parseInt( parts[6].trim() ), Double.parseDouble( parts[7].trim() ), parts[8].trim());
                c.setCountryStatistics(st);
                mapFileContents.put(c.getName(), c);
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
		String s = "Country: " + countryName + "\nCapital: " + capital.getName() + "\nPopulation: " + formatter.format(countryPopulation) + " people\nArea: " + formatter.format(countryArea) + " km2";
		return s;
	}
	
	public String completeToStringue() {
		String s = "";
		s += getName() + "\t" + getPosX() + "\t" + getPosY() + "\t" + getPopulation() + "\t" + getCountryArea() + "\t" 
		+ capital.getName() + "\t" + capital.getPopulation() + "\t" + capital.getArea() + "\t" + getFlagImg();
		for (int i = 0 ; i < countryStatistics.size() ; i++) {
			s += "\t" + countryStatistics.get(i).toString().trim();
		}
		return s;
	}
}
