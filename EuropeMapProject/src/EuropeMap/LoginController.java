package EuropeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import struc.Client;
import struc.DataManagement;
import struc.Manager;

public class LoginController implements Initializable {
	@FXML
	private TextField usrName;
	@FXML
	private PasswordField usrPwd;
	@FXML
	private Label labelUsername, labelPassword;
	@FXML
	private Button signinButton, signupButton, exitButton;
	@Override
    public void initialize(URL url, ResourceBundle rb) {}
	
	@FXML
	private void exitButtonAction(ActionEvent event) throws IOException {
		Platform.exit();
	}
	
	@FXML
	private void signinButtonAction(ActionEvent event) throws IOException {
		loadSigninScene();
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	private void loadSigninScene() throws IOException {
		String a = checkLogin(usrName.getText(), usrPwd.getText());
		DataManagement d;
		FXMLLoader loader = null;
    	switch(a) {
	    	case "N":
	    		loader = new FXMLLoader(getClass().getResource("Null.fxml"));
	    		break;
	    	case "C":
	    		d = new DataManagement();
	    		Client c = Client.getClientHashMap().get(usrName.getText());
	    		DataManagement.updateClientStats(c.getUsername(), c.getAge());
	    		loader = new FXMLLoader(getClass().getResource("Map.fxml"));
	    		MapController.transferMessage(c.getUsername(), c.getAge());
	    		break;
	    	case "M":
	    		d = new DataManagement();
	    		Manager m = Manager.getManagerHashMap().get(usrName.getText());
	    		StatsController.transferMessage(m.getName());
	    		loader = new FXMLLoader(getClass().getResource("Statistics.fxml"));
	    		break;
    	}
    	Parent root1 = (Parent) loader.load();;
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root1));
    	stage.show();
    }
	
	@FXML
	private void signupButtonAction(ActionEvent event) throws IOException{
		loadSignupScene();
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
    
    private void loadSignupScene() throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();
    }
    
    private String checkLogin(String usr, String pwd) throws FileNotFoundException{
    	String checker = "";
        BufferedReader br = null;
        try {
        	File file = new File("src\\data\\data_users.txt");
        	br = new BufferedReader( new FileReader(file) );
        	String line = null;
        	while ((line = br.readLine()) != null) {
            	String[] parts = line.split("\t");
            	if (parts[0].contentEquals(usr) && parts[1].contentEquals(pwd)) {      		
            		checker = parts[2].trim();
            	} else if (!parts[0].contentEquals(usr) && parts[1].contentEquals(pwd)){
            		checker = "N";
            	}
            }
        } catch(Exception e) {e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                	String d = br.readLine();
                	String[] part = d.split("\t");
                	if (part[0].equals(usr) && part[1].equals(pwd))
                		checker = "N";
                	else
                		checker = part[2].trim();
                    br.close(); 
                } catch(Exception e){};
            }
        }
        return checker;
    }
}