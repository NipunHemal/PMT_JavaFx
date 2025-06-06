package lk.ijse.project_management_tool.utils;

import com.sun.source.tree.BreakTree;
import javafx.scene.control.*;

public class ValidationUtils {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }


    public static boolean isValidNic(String nic) {
        return nic != null && nic.matches("^[0-9]{9}[vVxX]||[0-9]{12}$");
    }

    public static boolean isValidMobile(String mobile) {
        return mobile != null && mobile.matches("^(\\d+)|(\\d+\\.\\d{2})$");
    }

    public static boolean validatePasswordMatch(PasswordField passwordField, PasswordField confirmPasswordField) {
//        if (true) return true;
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Clear previous styles
        passwordField.getStyleClass().removeAll("error-field", "valid-field");
        confirmPasswordField.getStyleClass().removeAll("error-field", "valid-field");

        boolean isPasswordEmpty = isEmpty(password);
        boolean isConfirmPasswordEmpty = isEmpty(confirmPassword);
        boolean isPasswordValid = isValidPassword(password);
        boolean isEquals = password.equals(confirmPassword);

        if (isPasswordEmpty || isConfirmPasswordEmpty || !isPasswordValid || !isEquals) {
            passwordField.getStyleClass().add("error-field");
            confirmPasswordField.getStyleClass().add("error-field");
            return false;
        } else {
            passwordField.getStyleClass().add("valid-field");
            confirmPasswordField.getStyleClass().add("valid-field");
            return true;
        }

    }


    public static boolean validateInput(Control control, String type) {
//        if (true) return true;
        String value = "";

        // Extract text or selected value based on control type
        if (control instanceof TextInputControl textInput) {
            value = textInput.getText().trim();
        } else if (control instanceof ComboBox comboBox) {
            Object selected = comboBox.getValue();
            value = (selected != null) ? selected.toString().trim() : "";
        } else if (control instanceof DatePicker datePicker) {
            value = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        } else {
            System.err.println("Unsupported control type: " + control.getClass().getSimpleName());
            return false;
        }

        // Clear previous styles
        control.getStyleClass().removeAll("error-field", "valid-field");

        // Validation logic
        switch (type.toLowerCase()) {
            case "email":
                if (!isValidEmail(value)) {
                    control.getStyleClass().add("error-field");
                    return false;
                }
                control.getStyleClass().add("valid-field");
                break;

            case "mobile":
                if (!isValidMobile(value)) {
                    control.getStyleClass().add("error-field");
                    return false;
                }
                control.getStyleClass().add("valid-field");
                break;

            case "empty":
                if (isEmpty(value)) {
                    control.getStyleClass().add("error-field");
                    return false;
                }
                control.getStyleClass().add("valid-field");
                break;

            case "numeric":
                if (!isNumeric(value)) {
                    control.getStyleClass().add("error-field");
                    return false;
                }
                control.getStyleClass().add("valid-field");
                break;

            case "nic":
                if (!isValidNic(value)) {
                    control.getStyleClass().add("error-field");
                    return false;
                }
                control.getStyleClass().add("valid-field");
                break;

            case "password":
                if (!isValidPassword(value)) {
                    control.getStyleClass().add("error-field");
                    return false;
                }
                control.getStyleClass().add("valid-field");
                break;
            default:
                System.err.println("Unsupported validation type: " + type);
                return false;
        }
        return true;
    }
}
