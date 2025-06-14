//package application.student.controller;
//
//import java.io.IOException;
//
//import application.admin.form.RegisterationForm;
//import application.student.form.StudentMainForm;
//import application.student.form.StudentRegisterationForm;
//import application.teacher.form.TeacherRegisterationform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//
//public class StudentMainViewController {
//	@FXML
//	void btnStudentAction(ActionEvent event) throws IOException {
//		System.out.println("Registeration Form");
//		StudentMainForm.mStage.close();
//		StudentRegisterationForm r = new StudentRegisterationForm();
//		r.stuRegisterationForm().show();
//	}
//
//	@FXML
//	void btinTeacherAction(ActionEvent event) throws IOException {
//		System.out.println("Teacher Form");
//		StudentMainForm.mStage.close();
//		TeacherRegisterationform r = new TeacherRegisterationform();
//		r.teacherRegisterationform().show();
//	}
//
//	@FXML
//	void btnAdminAction(ActionEvent event) throws IOException {
//		System.out.println("Admin Form");
//		StudentMainForm.mStage.close();
//		RegisterationForm r = new RegisterationForm();
//		r.registerationForm().show();
//	}
//
//}
