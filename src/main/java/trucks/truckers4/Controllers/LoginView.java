package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trucks.truckers4.Model.Admin;
import trucks.truckers4.Model.Manager;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.UserHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;

public class LoginView {
    public TextField usernameField;
    public PasswordField passwordField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Truckers");
    UserHib userHib = new UserHib(entityManagerFactory);

    public void validate(ActionEvent actionEvent) throws IOException {
        if(usernameField.getText().equals("admin") && passwordField.getText().equals("admin")){
            Admin admin = new Admin();
            FXutils.openHomeView((Stage)usernameField.getScene().getWindow(), entityManagerFactory, admin, null);
            return;
        }

        User user = userHib.getUserByLogin(usernameField.getText(), passwordField.getText());
        if(user == null){
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Login report", "No such user", "There's no such user. Please check credentials");
        }
        else {
            FXutils.openHomeView((Stage)usernameField.getScene().getWindow(), entityManagerFactory, user, null);
        }
    }

    public void openRegistration(ActionEvent actionEvent) throws IOException {
        FXutils.openRegistrationPage((Stage)usernameField.getScene().getWindow(), entityManagerFactory, usernameField.getText(), passwordField.getText());
    }
}
