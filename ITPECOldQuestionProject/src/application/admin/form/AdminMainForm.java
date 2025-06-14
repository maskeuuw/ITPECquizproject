package application.admin.form;
  
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class AdminMainForm extends Application {
  @Override
  public void start(Stage primaryStage) {
    try {
    	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/admin/Sample.fxml"));
      Scene scene = new Scene(root,400,400);
      scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
      primaryStage.setResizable(false);
      primaryStage.getIcons().add(new Image("downLogo.jpg"));
      primaryStage.setTitle("Quiz Mania Ultimate Challenge");
      primaryStage.setScene(scene);
      primaryStage.getIcons().add(new Image("file:/C:/Java Programming/JavaWorkspace/MYProject/images/ppp.jpg")); 
      primaryStage.show();
    } catch(Exception e) {
      e.printStackTrace();   
    }
  }
}