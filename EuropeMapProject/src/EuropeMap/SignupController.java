package EuropeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import struc.Client;

public class SignupController implements Initializable {
	@FXML
	private TextField userName, firstName, lastName, age, city;
	@FXML
	private PasswordField password, passwordConfirmed;
	@FXML
	private Label labelUsername, labelPassword, labelPasswordConfirmed, labelFirstName, labelLastName, labelCity, labelAge;
	@FXML
	private Button registerButton;
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {}
	
	@FXML
	private void registerButtonAction(ActionEvent event) throws IOException{
		loadSignupScene();
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
    
    private void loadSignupScene() throws IOException{
    	String[] usrData = {firstName.getText(), lastName.getText(), userName.getText(), password.getText(), age.getText(), city.getText(), passwordConfirmed.getText()};
    	
    	if (checkRegister(usrData[2]) && (usrData[3].equals(usrData[6]))) {
    		Client u = new Client(usrData[0], usrData[1], usrData[2], usrData[3], Integer.parseInt(usrData[4]), usrData[5], 0);
    		addClient(u);
    		System.out.println("User created ! Back to Login");
    	} else {
    		System.out.println("User not created ! Back to Login");
    	}
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
    	Parent root1 = (Parent) loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root1));
    	stage.show();
    }

	private boolean checkRegister(String usr) throws FileNotFoundException {
    	boolean login = true;
        BufferedReader br = null;
        try {
        	File file = new File("src\\data\\data_clients.txt");
        	br = new BufferedReader( new FileReader(file) );
        	String line = null;
        	
        	while ((line = br.readLine()) != null) {
            	String[] parts = line.split("\t");
            	if (parts[2].trim().contentEquals(usr)) {
            		login = false;
            		System.out.println("Username already exists"); //Change to JavaFX Red
            	}
            }
        } catch(Exception e) {e.printStackTrace();
        } finally {
            if(br != null) {
                try { 
                    br.close(); 
                } catch(Exception e){};
            }
        }
        return login;
    }
	
	private void addClient(Client u) throws IOException {
		FileWriter fileWriter = new FileWriter("src\\data\\data_clients.txt", true);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(u.getCInformation());
	    printWriter.close();
		
	    fileWriter = new FileWriter("src\\data\\data_users.txt", true);
	    printWriter = new PrintWriter(fileWriter);
	    printWriter.println(u.getUserData() + "\tC");
	    printWriter.close();
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
}