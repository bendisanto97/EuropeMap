package struc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataManagement {
	private static HashMap<String, Integer> clientList;
	private static HashMap<String, ArrayList<Integer>> countriesStats;
	private static HashMap<String, ArrayList<Integer>> citiesStats;
	private static ArrayList<Integer> clientStats;
	
	public DataManagement() {
		clientList = setClientList();
		countriesStats = setCountriesStats();
		citiesStats = setCitiesStats();
		clientStats = setClientStats();
	}
	
	public static ArrayList<Integer> getClientList(){return clientStats;}
	
	public static HashMap<String, ArrayList<Integer>> getCoutriesStats(){return countriesStats;}
	
	public static HashMap<String, ArrayList<Integer>> getCitiesStats(){return citiesStats;}
	
	public static ArrayList<Integer> setClientStats() {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(0, 0);list.add(1, 0);list.add(2, 0);list.add(3, 0);
		for(Map.Entry<String, Client> entry : Client.getClientHashMap().entrySet()) {
			String key = entry.getKey();
			Client c = Client.getClientHashMap().get(key);
			if (c.getAge() <= 25) {
				list.set(0, list.get(0) + 1);
			} else if (c.getAge() > 25 && c.getAge() <= 50) {
				list.set(1, list.get(1) + 1);
			} else if (c.getAge() > 50 && c.getAge() <= 75) {
				list.set(2, list.get(2) + 1);
			} else if (c.getAge() > 75) {
				list.set(3, list.get(3) + 1);
			}
		}
		return list;
	}
	
	public static HashMap<String, Integer> setClientList() {
		HashMap<String, Integer> list = new HashMap<>();
		for(Map.Entry<String, Client> entry : Client.getClientHashMap().entrySet()) {
			String key = entry.getKey();
			list.put(key, entry.getValue().getLoginCount());
		}
		return list;
	}
	
	public static HashMap<String, ArrayList<Integer>> setCitiesStats() {
		HashMap<String, ArrayList<Integer>> list = new HashMap<>();
		for(Map.Entry<String, City> entry : City.getHashMapAllCities().entrySet()) {
			String key = entry.getKey();
			list.put(key, entry.getValue().getCityStatistics());
		}
		return list;
	}
	
	public static HashMap<String,ArrayList<Integer>> setCountriesStats() {
		HashMap<String, ArrayList<Integer>> list = new HashMap<>();
		for(Map.Entry<String, Country> entry : Country.getHashMapCountries().entrySet()) {
			String key = entry.getKey();
			list.put(key, entry.getValue().getCountryStatistics());
		}
		return list;
	}
	
	public static void updateClientStats(String c, int age) {
		clientList.put(c, clientList.get(c).intValue() + 1);
		if (age <= 25) {
			clientStats.set(0, clientStats.get(0) + 1);
		} else if (age > 25 && age <= 50) {
			clientStats.set(1, clientStats.get(1) + 1);
		} else if (age > 50 && age <= 75) {
			clientStats.set(2, clientStats.get(2) + 1);
		} else if (age > 75) {
			clientStats.set(3, clientStats.get(3) + 1);
		}
	}
	
	public static void updateCityStats(City c, int age) {
		if (age <= 25) {
			citiesStats.get(c.getName()).set(0, citiesStats.get(c.getName()).get(0) + 1);
		} else if (age > 25 && age <= 50) {
			citiesStats.get(c.getName()).set(1, citiesStats.get(c.getName()).get(1) + 1);
		} else if (age > 50 && age <= 75) {
			citiesStats.get(c.getName()).set(2, citiesStats.get(c.getName()).get(2) + 1);
		} else if (age > 75) {
			citiesStats.get(c.getName()).set(3, citiesStats.get(c.getName()).get(3) + 1);
		}
	}
	
	public static void updateCountryStats(Country c, int age) {
		if (age <= 25) {
			countriesStats.get(c.getName()).set(0, countriesStats.get(c.getName()).get(0) + 1);
		} else if (age > 25 && age <= 50) {
			countriesStats.get(c.getName()).set(1, countriesStats.get(c.getName()).get(1) + 1);
		} else if (age > 50 && age <= 75) {
			countriesStats.get(c.getName()).set(2, countriesStats.get(c.getName()).get(2) + 1);
		} else if (age > 75) {
			countriesStats.get(c.getName()).set(3, countriesStats.get(c.getName()).get(3) + 1);
		}
	}
	
	public static void updateManagement() throws IOException {
		HashMap<String, City> outingCity = City.getHashMapAllCities();
		for(Map.Entry<String, City> entry : outingCity.entrySet()) {
			String key = entry.getKey();
			City c = City.getHashMapAllCities().get(key);
			c.setStatistics(citiesStats.get(key));
			outingCity.replace(key, c);
		}
		HashMap<String, Country> outingCountry = Country.getHashMapCountries();
		for(Map.Entry<String, Country> entry : outingCountry.entrySet()) {
			String key = entry.getKey();
			Country c = Country.getHashMapCountries().get(key);
			c.setCountryStatistics(countriesStats.get(key));
			outingCountry.replace(key, c);
		}
		HashMap<String, Client> outingClient = Client.getClientHashMap();
		for(Map.Entry<String, Client> entry : outingClient.entrySet()) {
			String key = entry.getKey();
			Client c = Client.getClientHashMap().get(key);
			c.setLoginCount();
			outingClient.replace(key, c);
		}
		
		FileWriter fileWriter = new FileWriter("src\\data\\data_cities.txt", false);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    for(Map.Entry<String, City> entry : outingCity.entrySet()) {
			String key = entry.getKey();
			City c = outingCity.get(key);
			printWriter.println(c.completeToStringue());
		}printWriter.close();fileWriter.close();
	    
		FileWriter fileWriter2 = new FileWriter("src\\data\\data_countries.txt", false);
	    PrintWriter printWriter2 = new PrintWriter(fileWriter2);
	    for(Map.Entry<String, Country> entry : outingCountry.entrySet()) {
			String key = entry.getKey();
			Country c = outingCountry.get(key);
			printWriter2.println(c.completeToStringue());
		}printWriter2.close();fileWriter2.close();
		
		FileWriter fileWriter3 = new FileWriter("src\\data\\data_clients.txt", false);
	    PrintWriter printWriter3 = new PrintWriter(fileWriter3);
	    for(Map.Entry<String, Client> entry : outingClient.entrySet()) {
			String key = entry.getKey();
			Client c = outingClient.get(key);
			printWriter3.println(c.getCInformation());
		}printWriter3.close();fileWriter3.close();
	}
}
