package EuropeMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import struc.Client;
import struc.Review;

public class ReviewController implements Initializable {
	private static String u, c, a, n;
	@FXML
	private TextArea header, comment;
	@FXML
	private TextField user, city, age, name;
	@FXML
	private Button reviewBtn, cancelBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user.setText(u); user.setEditable(false);
		city.setText(c); city.setEditable(false);
		age.setText(a); age.setEditable(false);
		name.setText(n); name.setEditable(false);
	}
	
	@FXML
	private void cancelButtonAction(ActionEvent event) throws IOException {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	@FXML
	private void reviewButtonAction(ActionEvent event) throws IOException {
		int cpt = 0;
		try {
			File f = new File("src\\reviews\\rev-" + user.getText() + "_" + cpt + ".txt");
			while(!f.createNewFile()) {
				f = new File("src\\reviews\\rev-" + user.getText() + "_" + cpt++ + ".txt");
			}
			FileWriter myWriter = new FileWriter(f);
			Review r = new Review(comment.getText(), user.getText());
			/*
			String s = "Review posted by ";
			s += user.getText() + ", from " + city.getText() + ", Age " + age.getText() + "\nOn " + java.time.LocalDate.now() + "\n\n";  
			myWriter.write(s += comment.getText());
			*/
			myWriter.write(r.getTotal());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	public static void transferMessage(String message) {
		Client cl = Client.getClientHashMap().get(message);
		u = cl.getUsername();
		c = cl.getCity();
		a = String.valueOf(cl.getAge());
		n = cl.getName();
	}
	
}
