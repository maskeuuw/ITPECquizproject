package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import myform.form.YearForm;

public class Main extends Application {

	public static BorderPane root;
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
//			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/myform/Yearadd.fxml"));
//			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/myform/ChapterAndType.fxml"));
//			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/myform/MainViews.fxml"));
			root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/main/MainViews.fxml"));
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/myform/Image.fxml"));
			Scene scene = new Scene(root, 450, 450);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			
//			YearForm yf = new YearForm();
//			yf.yearForm();
//			YearForm.yform.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);

	}

}
