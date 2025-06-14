package application.student.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.main.DatabaseConnection;
import application.student.Student;
import application.student.form.ProfileForm;
import application.student.form.UpdateProfileForm;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class UpdateProfileController {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField txtStudentId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private DatePicker dpDOB;

	@FXML
	private ComboBox<String> cboPhonePrefix;

	@FXML
	private TextField txtPhone;

	@FXML
	private ComboBox<String> cboBatchPrefix;

	@FXML
	private ComboBox<String> cboClassPrefix;

	@FXML
	private TextArea txtAddress;

	@FXML
	private RadioButton optMale;

	@FXML
	private RadioButton optFemale;

	@FXML
	private ImageView profilePhoto;

	private String selectedPhoto;

	File selectedFile;

	@FXML
	private ToggleGroup genderToggleGroup;

	Student logStu = SignInController.login_student;

	private static final long IMAGE_SIZE_LIMIT = 300000;

	public static boolean isFileChooserOpen = false;

	@FXML
	private void initialize() {
		txtStudentId.setText(logStu.getStudent_id()); // Only take student_id from logStu

		try {
			DatabaseConnection db = new DatabaseConnection();
			Connection con = db.getConnetion();
			PreparedStatement pst = con.prepareStatement(
					"SELECT name, email, address, phone, gender, date_of_birth, class, batch, photo FROM student WHERE student_id = ?");
			pst.setString(1, txtStudentId.getText());

			var rs = pst.executeQuery();
			if (rs.next()) {
				txtName.setText(rs.getString("name"));
				txtEmail.setText(rs.getString("email"));
				dpDOB.setValue(rs.getDate("date_of_birth").toLocalDate());
				cboClassPrefix.setValue(rs.getString("class"));
				cboBatchPrefix.setValue(rs.getString("batch"));
				txtPhone.setText(rs.getString("phone"));
				txtAddress.setText(rs.getString("address"));

				// Set phone prefix
				String[] prefixArray = patternCheck(rs.getString("phone"), " ");
				cboPhonePrefix.setValue(prefixArray[0]);
				txtPhone.setText(prefixArray[1]);

				// Set gender
				if ("Male".equalsIgnoreCase(rs.getString("gender"))) {
					optMale.setSelected(true);
				} else if ("Female".equalsIgnoreCase(rs.getString("gender"))) {
					optFemale.setSelected(true);
				}

				// Retrieve and display photo
				byte[] photoBytes = rs.getBytes("photo");
				if (photoBytes != null) {
					profilePhoto.setImage(new Image(new ByteArrayInputStream(photoBytes)));
				} else {
					profilePhoto.setImage(new Image(
							"D:/Eclipse/JavaWorkspace/ITPECOldQuestionProject2/src/application/images/images.png"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve student data", e.getMessage());
		}

		// Initialize ComboBox values for phone prefix, batch, and class
		cboPhonePrefix.getItems().addAll("+95", "+44", "+1");
		cboBatchPrefix.getItems().addAll("Batch1", "Batch2", "Batch3", "Batch4", "Batch5", "Batch6", "Batch7", "Batch8");
		cboClassPrefix.getItems().addAll("FE", "IP");

		// Associate gender radio buttons with the ToggleGroup
		genderToggleGroup = new ToggleGroup();
		optMale.setToggleGroup(genderToggleGroup);
		optFemale.setToggleGroup(genderToggleGroup);
	}

	public String[] patternCheck(String data, String splitSymbol) {
		String[] phone = new String[2];
		phone = data.split(splitSymbol);
		return phone;

	}

	@FXML
	private void onBrowsePhoto() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			selectedFile = file; // Assign the selected file to the field
			selectedPhoto = file.getAbsolutePath();
			profilePhoto.setImage(new Image("file:" + selectedPhoto));
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("No file was selected.");
			alert.show();
		}
	}

	@FXML
	private void onUpdateProfile() {
	    try {
	        DatabaseConnection db = new DatabaseConnection();
	        Connection con = db.getConnetion();
	        PreparedStatement pst = con.prepareStatement(
	                "UPDATE student SET name=?, email=?, address=?, phone=?, gender=?, date_of_birth=?, class=?, batch=?, photo=? WHERE student_id=?");

	        pst.setString(1, txtName.getText());
	        pst.setString(2, txtEmail.getText());
	        pst.setString(3, txtAddress.getText());
	        pst.setString(4, cboPhonePrefix.getValue() + " " + txtPhone.getText());
	        pst.setString(5, optMale.isSelected() ? "Male" : "Female");
	        pst.setDate(6, java.sql.Date.valueOf(dpDOB.getValue()));
	        pst.setString(7, cboBatchPrefix.getValue());
	        pst.setString(8, cboClassPrefix.getValue());

	        if (selectedFile != null) {
	            // If a new file is chosen, update the photo
	            Path path = Paths.get(selectedFile.getAbsolutePath());
	            long fileSize = Files.size(path);

	            if (fileSize > IMAGE_SIZE_LIMIT) {
	                throw new IOException("Image size exceeds the 300 KB limit.");
	            }

	            FileInputStream fis = new FileInputStream(selectedFile);
	            pst.setBinaryStream(9, fis, (int) fileSize);
	        } else {
	            // Retrieve the existing photo from the database
	            PreparedStatement ps = con.prepareStatement("SELECT photo FROM student WHERE student_id = ?");
	            ps.setString(1, txtStudentId.getText());
	            ResultSet rs = ps.executeQuery();

	            if (rs.next() && rs.getBinaryStream("photo") != null) {
	                pst.setBinaryStream(9, rs.getBinaryStream("photo")); // Keep existing photo
	            } else {
	                pst.setBinaryStream(9, null); // If no existing photo, set NULL
	            }
	        }

	        pst.setString(10, txtStudentId.getText());
	        pst.executeUpdate();
	        UpdateProfileForm.closeForm();
			ProfileForm.profileStage.close();

	        showAlert(AlertType.INFORMATION, "Profile Updated", null, "Profile updated successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        showAlert(AlertType.ERROR, "Error", "An error occurred.", e.getMessage());
	    }
	}


	private void showAlert(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}

	@FXML
	private void onCancel() throws IOException {
		UpdateProfileForm.closeForm();;
	}
}
