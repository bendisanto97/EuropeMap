package EuropeMap;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.*;
import javafx.stage.Stage;
import struc.DataManagement;

public class StatsController implements Initializable {
	private static String user;
	@FXML
	private PieChart ageDist;
	@FXML
	private BarChart<String, Number> countryCount;
	@FXML
	private ListView<String> cities5, cities10;
	@FXML
	private TextArea header;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		header.setText("Welcome to the statistics page " + user);
		ObservableList<String> obs5 = setListViews(5);
		ObservableList<String> obs10 = setListViews(10);
		cities5.setItems(obs5);
		cities10.setItems(obs10);
		
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Under 25", DataManagement.getClientList().get(0)),
                new PieChart.Data("Between 25 & 50", DataManagement.getClientList().get(1)),
                new PieChart.Data("Between 50 & 75", DataManagement.getClientList().get(2)),
                new PieChart.Data("Over 75", DataManagement.getClientList().get(3)));
        ageDist.setData(pieChartData);
        countryCount.setLegendVisible(false);
        countryCount.getData().add(setBarChart());
	}
	
	public int calculateAverage(ArrayList <Integer> marks) {
		  int sum = 0;
		  for (int i = 0 ; i < marks.size() ; i++) {
			  sum += marks.get(i);
		  }
		  return sum;
	}
	
	public XYChart.Series<String, Number> setBarChart() {
		XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
		HashMap<String, Integer> sums = new HashMap<String, Integer>();
		for(Map.Entry<String, ArrayList<Integer>> entry : DataManagement.getCoutriesStats().entrySet()) {
			String key = entry.getKey();
			int sum = calculateAverage(entry.getValue());
			sums.put(key, sum);
		}
		for (Map.Entry<String, Integer> entry : sums.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();
			dataSeries1.getData().add(new Data<String, Number>(key, value));
		}
		return dataSeries1;
	}
	
	public ObservableList<String> setListViews(int size){
		HashMap<String, Integer> sums = new HashMap<String, Integer>();
		for(Map.Entry<String, ArrayList<Integer>> entry : DataManagement.getCitiesStats().entrySet()) {
			String key = entry.getKey();
			int sum = calculateAverage(entry.getValue());
			sums.put(key, sum);
		}
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>(); 
		sums.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		sortedMap.entrySet().stream().limit(size);
		List<String> tmp = new ArrayList<String>();
		
		for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			String key = entry.getKey();
			int sum = entry.getValue();
			tmp.add(key + " = " + String.valueOf(sum));
		}
		ObservableList<String> obstmp = FXCollections.observableArrayList();
		for (int i = 0 ; i < size ; i++) {
			obstmp.add(i+1 +  ". " + tmp.get(i));
		}
		return obstmp;
	}
	
	@FXML
	private void logoutFunction(ActionEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
    	Parent root1 = (Parent) loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root1));
    	stage.show();
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	public static void transferMessage(String s) {
		user = s;
	}
	
}
