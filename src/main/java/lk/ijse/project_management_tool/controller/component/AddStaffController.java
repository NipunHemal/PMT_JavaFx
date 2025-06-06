package lk.ijse.project_management_tool.controller.component;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import lk.ijse.project_management_tool.controller.StaffController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddStaffController  implements Initializable {
    File selectedFile;
    public ImageView imgUserPic;
    public Button changePhotoButton;

    public TextField nameField;
    public TextField emailField;
    public TextField contactField;
    public TextArea addressField;
    public ComboBox roleComboBox;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;

    // controllers object
    StaffController staffController;

    // models object
    EmployeeModel employeeModel = new EmployeeModel();

    public void handleChangePhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(ReferenceUtils.primaryStage);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            imgUserPic.setImage(new Image(imagePath));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoles();
    }

    public void init(StaffController staffController){
        this.staffController = staffController;
    }

    public void loadRoles(){
        ArrayList<String> roles = new ArrayList<>(java.util.Arrays.asList("OWNER", "PROJECT_MANAGER", "TEAM_LEAD", "QA", "DEVELOPER", "BA", "FULLSTACK_DEVELOPER", "FRONTEND_DEVELOPER", "BACKEND_DEVELOPER", "MOBILE_DEVELOPER", "WEB_DEVELOPER", "TESTER"));
        roleComboBox.getItems().addAll(roles);
    }

    public void saveStaff() {
        String name = nameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String role = String.valueOf(roleComboBox.getValue());
        String password = passwordField.getText();
        String fileName = null;

        boolean isValidName = ValidationUtils.validateInput(nameField, "empty");
        boolean isValidEmail = ValidationUtils.validateInput(emailField, "email");
        boolean isValidContact = ValidationUtils.validateInput(contactField, "mobile");
        boolean isValidAddress = ValidationUtils.validateInput(addressField, "empty");
        boolean isValidRole = ValidationUtils.validateInput(roleComboBox, "empty");
        boolean isValidPassword = ValidationUtils.validateInput(passwordField, "password");
        boolean isMatchPassword = ValidationUtils.validatePasswordMatch(passwordField , confirmPasswordField);

        if (!(isValidName && isValidEmail && isValidContact && isValidAddress && isValidRole && isValidPassword)) {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
            return;
        }

        if (!isValidPassword){
            NotificationUtils.showError("Validation Error", "Please enter valid password");
        }

        if (!isMatchPassword) {
            NotificationUtils.showError("Validation Error", "Passwords do not match.");
            return;
        }

        if (selectedFile != null) {
            // Destination folder
            File destinationFolder = new File("/images/user"); // You can use any folder name
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs(); // Create folder if not exists
            }

            // Create destination file with the same name
            fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".png";
            File destFile = new File(destinationFolder,fileName );

            // Copy file
            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved to: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                NotificationUtils.showError("Error", "Failed to save image.");
                e.printStackTrace();
                return;
            }
        }

        EmployeeDto employeeDto = new EmployeeDto(
                name,
                email,
                contact,
                address,
                role,
                password,
                fileName
        );

        try{
            boolean isSaved = employeeModel.saveEmployee(employeeDto);
            if (isSaved){
                NotificationUtils.showSuccess("Success", "Employee saved successfully!");
                DialogUtil.close();
                staffController.initialize(null, null);
            } else {
                NotificationUtils.showError("Error", "Failed to save employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Error","Failed to save employee : "+ e.getMessage());
        }
    }
}
