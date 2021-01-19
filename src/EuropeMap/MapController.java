package EuropeMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import struc.City;
import struc.Country;
import struc.DataManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class MapController {
	private static String user;
	private static int age;
    @FXML
    private VBox root_vbox;
    @FXML
    private ListView<String> countriesListView, citiesListView;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info, att1, att2, att3;
    @FXML
    private ImageView igm;
    @FXML
    private Button reviewButton, logoutButton;
    @FXML
    private Label welcomeMessage;
    
    private final HashMap<String, Country> hm = struc.Country.getHashMapCountries();
    private HashMap<String, City> hashCities = null;
    Group zoomGroup;

    @FXML
    void initialize() {
    	welcomeMessage.setText("Welcome " + user + " !");
    	ObservableList<String> names = FXCollections.observableArrayList();
        Set<Entry<String, Country>> set = hm.entrySet();
        Iterator<Entry<String, Country>> i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, Country> me = i.next();
            names.add((String) me.getKey());
        }
        Collections.sort(names);

        countriesListView.setItems(names);
        map_pin.setVisible(false);

        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
    }

    @FXML
    void countriesListClicked(MouseEvent event) throws FileNotFoundException {
    	String item = countriesListView.getSelectionModel().getSelectedItem();
        Country c = hm.get(item);
        igm.setImage(c.getFlag());
        
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = c.getPosX() / mapWidth;
        double scrollV = c.getPosY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setStyle("-fx-background-color: black, red;");
        map_pin.setLayoutX(c.getPosX() - (pinW / 2));
        map_pin.setLayoutY(c.getPosY() - (pinH));
        pin_info.setText(c.toStringue());
        pin_info.setGraphic(igm);
        map_pin.setVisible(true);
        att1.setVisible(false);att2.setVisible(false);att3.setVisible(false);
        
        hashCities = c.getHashMapCities();
        ObservableList<String> cities = FXCollections.observableArrayList();
        Set<Entry<String, City>> set = hashCities.entrySet();
        Iterator<Entry<String, City>> i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, City> me = i.next();
            cities.add((String) me.getKey());
        }
        Collections.sort(cities);
        citiesListView.setItems(cities);
        DataManagement.updateCountryStats(c, age);
    }
    
    @FXML
    void citiesListClicked(MouseEvent event) {        
    	String item = citiesListView.getSelectionModel().getSelectedItem();
        City c = hashCities.get(item);
        
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = c.getPosX() / mapWidth;
        double scrollV = c.getPosY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setStyle("-fx-background-color: black, blue;");
        map_pin.setLayoutX(c.getPosX() - (pinW / 2));
        map_pin.setLayoutY(c.getPosY() - (pinH));
        pin_info.setText(c.toStringue());
        map_pin.setVisible(true);
        if (!(c.getAttractions().get(0).equals("N/A"))) {
        	att1.setVisible(true);att2.setVisible(true);att3.setVisible(true);
            att1.setText(c.getAttractions().get(0));att2.setText(c.getAttractions().get(1));att3.setText(c.getAttractions().get(2));
        } else if (c.getAttractions().get(0).equals("N/A")) {
        	att1.setVisible(false);att2.setVisible(false);att3.setVisible(false);
        }
        DataManagement.updateCityStats(c, age);
    }

    @FXML
    void zoomIn(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    @FXML
    void attractionView(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Attraction.fxml"));
    	String s = ((MenuItem)event.getSource()).getText();
    	TourismController.transferMessage(s);
    	Parent root1 = (Parent) loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root1));
    	stage.setResizable(false);
    	stage.show();
    }

    private void zoom(double scaleValue) {
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }
    
    public static void transferMessage(String message, int a) {
    	user = message;
    	age = a;
    }
    
    @FXML
    void leaveReview(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Review.fxml"));
    	ReviewController.transferMessage(user);
    	Parent root1 = (Parent) loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root1));
    	stage.setResizable(false);
    	stage.show();
    }
    
    @FXML
	private void logoutFunction(ActionEvent event) throws IOException{
    	DataManagement.updateManagement();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
    	Parent root1 = (Parent) loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root1));
    	stage.show();
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

}