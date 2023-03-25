package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import trucks.truckers4.Model.Admin;
import trucks.truckers4.Model.Driver;
import trucks.truckers4.Model.Manager;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.UserHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RegistrationView {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public TextField surnameField;
    public TextField phoneField;
    public TextField emailField;
    public CheckBox isManager;
    public DatePicker dateField;
    public Button signUpButton;
    public Button backButton;

    EntityManagerFactory entityManagerFactory;
    UserHib userHib;
    private Tab previousTab;

    // Used when admin wants to create a new user
    public void setData(EntityManagerFactory emf, Tab tab){
        entityManagerFactory = emf;
        userHib = new UserHib(entityManagerFactory);

        previousTab = tab;

        backButton.setText("Back");
        backButton.setOnAction(actionEvent -> {openUserManagement();});

        signUpButton.setText("Create new user");
        signUpButton.setOnAction(actionEvent -> {registerAsAdmin();});
    }

    public void setData(EntityManagerFactory emf, String username, String password){
        entityManagerFactory = emf;
        userHib = new UserHib(entityManagerFactory);
        usernameField.setText(username);
        passwordField.setText(password);
    }

    public void register(ActionEvent actionEvent) {
        User user;
        if(isManager.isSelected()){
            user = new Manager(nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), dateField.getValue());
        }
        else {
            user = new Driver(nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), dateField.getValue());
        }
        if(user.isValid()){
            userHib.createUser(user);
            try {
                openLogin(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Registration report", "Registration successful", "Welcome to the Truckers!");

        }
        else{
            FXutils.alertMessage(Alert.AlertType.ERROR, "Registration report", "Missing info", "Please fill in all the fields");

        }
    }

    public void registerAsAdmin() {
        User user;
        if(isManager.isSelected()){
            user = new Manager(nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), dateField.getValue());
        }
        else {
            user = new Driver(nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), dateField.getValue());
        }
        if(user.isValid()){
            userHib.createUser(user);
            openUserManagement();

            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Registration report", "Registration successful", "Welcome to the Truckers!");

        }
        else{
            FXutils.alertMessage(Alert.AlertType.ERROR, "Registration report", "Missing info", "Please fill in all the fields");
        }
    }


    public void openLogin(ActionEvent actionEvent) throws IOException {
        FXutils.openLoginPage((Stage)usernameField.getScene().getWindow());
    }

    public void openUserManagement(){
        Admin admin = new Admin();
        FXutils.openHomeView((Stage) usernameField.getScene().getWindow(), entityManagerFactory, admin, previousTab);
    }
}