package application.main;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


public class HomeForm {
	
	public AnchorPane homesForm() throws IOException {
		AnchorPane root= (AnchorPane)FXMLLoader.load(getClass().getResource("/myform/Home.fxml"));
	
		return root;
		
	}
}
