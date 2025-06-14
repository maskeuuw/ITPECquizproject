package application.admin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import application.admin.form.ForgotPassForm;
import application.admin.form.SignInForm;


//import javafx.event.ActionEvent;

//import javafx.scene.control.HyperLink;

public class ForgotPassController {
  
  
  @FXML
  private TextField txtEmail;

  @FXML
  private TextField txtId;
    
    public void btnForgotPassAction() throws SQLException, ClassNotFoundException, IOException {
        if (txtEmail.getText().equals("") || txtId.getText().equals("")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Required Data");
            alert.setHeaderText("Fill all required data!");
            alert.setContentText("Please you need to fill all required data.");
            alert.show();
        } else {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Database connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mbtcdb", "root", "root");
            String query = "SELECT * FROM admin WHERE email=? and password=?";

            // Prepare and execute query
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtEmail.getText());
            pst.setString(2, txtId.getText());
            //ResultSet rs = pst.executeQuery();

            
            }
            
          
            
            }
        
    
    @FXML
	void handleSignIn(ActionEvent event) throws IOException {
		System.out.println("Forgot Password Form");

		ForgotPassForm.pForm.close();

		SignInForm s = new SignInForm();
		s.signInForm().show();

	}
    
//    @FXML
//	void btnForgotPassAction(ActionEvent event) throws IOException {
//		System.out.println("Forgot Password Form");

//		ForgotPassForm.pForm.close();

//		NewPasForm s = new NewPasForm();
//		s.NewPasForm().show();

//	}
    
    
}