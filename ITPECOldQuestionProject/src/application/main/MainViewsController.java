package application.main;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import application.Main;
import application.main.*;
import application.student.form.StudentMainForm;

public class MainViewsController {

	@FXML
	void btnhome(ActionEvent event) throws IOException {

		System.out.println("home page");
		HomeForm hm = new HomeForm();
		Main.root.setCenter(hm.homesForm());

	}

	@FXML
	void btnteacher(ActionEvent event) throws IOException {

		System.out.println("Teacher...");

	}

	@FXML
	void btnstudent(ActionEvent event) {

		System.out.println("student....");
	}

	@FXML
	void btnadmin(ActionEvent event) {

		System.out.println("admin");
	}

	@FXML
	void btnRegister(ActionEvent event) {
		System.out.println("register");
		StudentMainForm mf = new StudentMainForm();
		mf.showMainForm();
	}

	@FXML
	void btnLogin(ActionEvent event) {
		System.out.println("login");
		StudentMainForm mf = new StudentMainForm();
		mf.showMainForm();
	}

}
