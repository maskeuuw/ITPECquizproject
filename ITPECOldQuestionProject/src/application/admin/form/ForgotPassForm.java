package application.admin.form;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgotPassForm {
  public static Stage pForm;
  public Stage forgotPassForm() throws IOException {
    pForm = new Stage();
    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/admin/ForgotPassword.fxml"));
    Scene sc = new Scene(root,801,500);
    pForm.setScene(sc);
    pForm.setTitle("ForgotPass");
    return pForm;
    
  }
}