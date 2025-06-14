package application.student.form;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgetPwdForm {
	public static Stage fForm;

    public Stage forgetPwdForm() {
        try {
        	 if (fForm == null) { // Check if rForm is already initialized
                 fForm = new Stage();
                 AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/application/student/ForgetPassword.fxml"));
                 Scene sc = new Scene(root, 1366,700);
                 fForm.setScene(sc);
                 fForm.setTitle("Forget Password Form");
           
        	 }
        	 return fForm;
        } catch (IOException e) {
            System.err.println("Failed to load ForgetPassword.fxml: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
