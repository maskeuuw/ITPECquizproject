package application.teacher.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.main.DatabaseConnection;
import application.teacher.Teacher;
import application.teacher.form.ProfileForm;
import application.teacher.form.Updateprofileform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class updateprofilecontroller implements Initializable {

	@FXML
	private TextField txtid;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtname;

	@FXML
	private TextField txtspecial;

	@FXML
	private DatePicker dob;

	@FXML
	private RadioButton optmale;

	@FXML
	private ChoiceBox<String> combobox;

	@FXML
	private RadioButton optfemale;

	@FXML
	private TextArea txtaddress;

	@FXML
	private TextField txtphone;

	@FXML
	private ToggleGroup gendertoggleGroup;

	@FXML
	private ImageView profilePhoto;

	private String selectedPhoto;
	private File selectedFile;
	private static final long IMAGE_SIZE_LIMIT = 300000;

	Teacher logTr = TeacherLoginController.login_teacher;

	@FXML
	void btncancellinkAction(ActionEvent event) throws IOException {
		Updateprofileform.uqf.close();
	}

	@FXML
	private void onBrowsePhoto() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			selectedPhoto = selectedFile.getAbsolutePath();
			profilePhoto.setImage(new Image("file:" + selectedPhoto));
		} else {
			showAlert(AlertType.INFORMATION, "No File Selected", null, "No file was selected.");
		}
	}

	@FXML
	void btnUpdateAction(ActionEvent event) {
		try {
			DatabaseConnection db = new DatabaseConnection();
			Connection con = db.getConnetion();

			PreparedStatement pst = con.prepareStatement(
					"UPDATE teacher SET name=?, email=?, address=?, phone=?, gender=?, date_of_birth=?, specialism=?, photo=? WHERE teacher_id=?");

			pst.setString(1, txtname.getText());
			pst.setString(2, txtemail.getText());
			pst.setString(3, txtaddress.getText());
			pst.setString(4, combobox.getValue() + " " + txtphone.getText());
			pst.setString(5, optmale.isSelected() ? "Male" : "Female");
			pst.setDate(6, java.sql.Date.valueOf(dob.getValue()));
			pst.setString(7, txtspecial.getText());

			if (selectedFile != null) {
				// New image selected, update the photo
				Path path = Paths.get(selectedFile.getAbsolutePath());
				long fileSize = Files.size(path);

				if (fileSize > IMAGE_SIZE_LIMIT) {
					throw new IOException("Image size exceeds the 300 KB limit.");
				}

				FileInputStream fis = new FileInputStream(selectedFile);
				pst.setBinaryStream(8, fis, (int) fileSize);
			} else {
				// Retrieve and keep the existing photo from the database
				PreparedStatement ps = con.prepareStatement("SELECT photo FROM teacher WHERE teacher_id = ?");
				ps.setString(1, txtid.getText());
				ResultSet rs = ps.executeQuery();

				if (rs.next() && rs.getBinaryStream("photo") != null) {
					pst.setBinaryStream(8, rs.getBinaryStream("photo"));
				} else {
					pst.setBinaryStream(8, null); // No previous image, keep it null
				}

				rs.close();
				ps.close();
			}

			pst.setString(9, txtid.getText());

			// Validate required fields
			if (txtid.getText().isEmpty() || txtname.getText().isEmpty() || txtemail.getText().isEmpty()
					|| txtaddress.getText().isEmpty() || txtphone.getText().isEmpty()
					|| (!optmale.isSelected() && !optfemale.isSelected()) || dob.getValue() == null) {
				showAlert(AlertType.INFORMATION, "Required Data", "Fill all required data!",
						"Please fill all required fields.");
			} else {
				pst.executeUpdate();
				showAlert(AlertType.INFORMATION, "Success", null, "Profile updated successfully!");
				Updateprofileform.uqf.close();
				ProfileForm.profileStage.close();
			}

			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			showAlert(AlertType.ERROR, "Error", "An error occurred.", e.getMessage());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DatabaseConnection db = new DatabaseConnection();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = db.getConnetion();

			// Assuming the logged-in teacher's ID is stored in TeacherLoginController
			String teacherId = TeacherLoginController.login_teacher.getTeacher_id();

			pst = con.prepareStatement("SELECT * FROM teacher WHERE teacher_id = ?");
			pst.setString(1, teacherId);
			rs = pst.executeQuery();

			if (rs.next()) {
				txtid.setText(rs.getString("teacher_id"));
				txtname.setText(rs.getString("name"));
				txtemail.setText(rs.getString("email"));
				txtaddress.setText(rs.getString("address"));
				txtspecial.setText(rs.getString("specialism"));
				txtphone.setText(rs.getString("phone"));

				// Gender Selection
				String gender = rs.getString("gender");
				if (gender != null && gender.equals("Male")) {
					optmale.setSelected(true);
				} else {
					optfemale.setSelected(true);
				}

				// Date of Birth
				Date sqlDate = rs.getDate("date_of_birth");
				if (sqlDate != null) {
					dob.setValue(sqlDate.toLocalDate());
				}

				// Load profile image if available
				byte[] imageBytes = rs.getBytes("photo");
				if (imageBytes != null && imageBytes.length > 0) {
					profilePhoto.setImage(new Image(new ByteArrayInputStream(imageBytes)));
				}

				// Setting up phone prefix and number
				String phone = rs.getString("phone");
				String[] phoneParts = patternCheck(phone, " ");
				combobox.setValue(phoneParts[0]);
				txtphone.setText(phoneParts[1]);
			} else {
				System.out.println("No teacher data found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String[] patternCheck(String data, String splitSymbol) {
		String[] phone = new String[2];

		// Check if the phone number contains the separator
		if (data.contains(splitSymbol)) {
			phone = data.split(splitSymbol);
		} else {
			// If no separator, use a default value for the prefix and set the whole number
			phone[0] = "+95"; // Default prefix, or from user settings
			phone[1] = data; // The phone number itself
		}

		return phone;
	}

	private void showAlert(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
}
