package application.admin.form;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeForm {
     static Stage hForm;

     public HomeForm() {
    }

    public Stage homeForm() throws IOException {
        if (hForm == null) { // Check if rForm is already initialized
            hForm = new Stage();
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/application/admin/Home.fxml"));
            Scene sc = new Scene(root, 1366,700);
            hForm.setScene(sc);
            hForm.setTitle("Register Form");
        }
        return hForm;
    }
}