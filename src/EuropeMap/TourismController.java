package EuropeMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import struc.Attraction;

public class TourismController implements Initializable {
	@FXML
	private ImageView img1, img2, img3;
	@FXML
	private TextArea header, text1, text2, text3;
	@FXML
	private TabPane tPane;
	@FXML
	private Tab tab1, tab2, tab3;
	@FXML
	private Button returnMapButton;
	
	private static String monument;
	private HashMap<Integer, Attraction> attractions = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		HashMap<String, Attraction> aa = Attraction.getAllAttractions();
		String city = "";
		for (Map.Entry<String, Attraction> entry : aa.entrySet()) {	
			for (int i = 0 ; i < entry.getValue().getFacts().size() ; i++) {
				if (monument.equals(entry.getValue().getAttName())) {
					city = entry.getValue().getAttCity();
				}
			}
		}
		String tmp = header.getText() + city;
		header.setText(tmp);
		header.setEditable(false);
		attractions = Attraction.getAttractionsCity(city);
		for (int i = 1 ; i < 4 ; i++) {
			try {
				setTab(i, attractions, tPane);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void transferMessage(String m) {
		monument = m;
	}
	
	public void returnMapAction(ActionEvent event) throws IOException {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	public void setTab(int cpt, HashMap<Integer, Attraction> atts, TabPane t) throws FileNotFoundException {
		String s = "";
		ArrayList<String> f = null;
		switch(cpt) {
		case 1:
			tab1.setText(atts.get(cpt).getAttName());
			img1.setImage(atts.get(cpt).getImg());
			f = atts.get(cpt).getFacts();
			for (int i = 0 ; i < f.size() ; i++) {
				s += "- " + f.get(i).toString() + "\n";
			}
			text1.setText(s);
			if (monument.equals(atts.get(cpt).getAttName())) {
				t.getSelectionModel().select(tab1);
			}
			break;
		case 2:
			tab2.setText(atts.get(cpt).getAttName());
			img2.setImage(atts.get(cpt).getImg());
			f = atts.get(cpt).getFacts();
			for (int i = 0 ; i < f.size() ; i++) {
				s += "- " + f.get(i).toString() + "\n";
			}
			text2.setText(s);
			if (monument.equals(atts.get(cpt).getAttName())) {
				t.getSelectionModel().select(tab2);
			}
			break;
		case 3:
			tab3.setText(atts.get(cpt).getAttName());
			img3.setImage(atts.get(cpt).getImg());
			f = atts.get(cpt).getFacts();
			for (int i = 0 ; i < f.size() ; i++) {
				s += "- " + f.get(i).toString() + "\n";
			}
			text3.setText(s);
			if (monument.equals(atts.get(cpt).getAttName())) {
				t.getSelectionModel().select(tab3);
			}
			break;
		}
	}

}
