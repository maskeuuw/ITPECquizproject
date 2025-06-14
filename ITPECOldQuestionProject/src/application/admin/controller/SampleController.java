package application.admin.controller;

import java.io.IOException;

import application.admin.form.RegisterationForm;
import application.admin.form.SignInForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SampleController {
   @FXML
      void btnUserAction(ActionEvent event) throws IOException {
     System.out.println("Registeration Form");
     RegisterationForm r = new RegisterationForm();
     r.registerationForm().show();
      }
   @FXML
      void btnAdminAction(ActionEvent event) throws IOException {
     System.out.println("Login Form");
     SignInForm s = new SignInForm();
     s.signInForm().show();
      }

}