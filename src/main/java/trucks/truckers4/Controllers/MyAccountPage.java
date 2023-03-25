package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.UserHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class MyAccountPage {
    public TextField nameField;
    public TextField surnameField;
    public TextField emailField;
    public TextField phoneField;
    public PasswordField passwordField;
    public DatePicker birthDateField;

    Tab previousTab;

    User currentUser;
    User userInfo;
    UserHib userHib;
    EntityManagerFactory emf;
    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        userHib = new UserHib(emf);
        this.currentUser = user;
        this.userInfo = user;

        previousTab = tab;

        fillFields();
    }

    public void setData(EntityManagerFactory emf, User currentUser, User userInfo, Tab tab) {
        this.emf = emf;
        userHib = new UserHib(emf);
        this.currentUser = currentUser;
        this.userInfo = userInfo;

        previousTab = tab;

        fillFields();
    }

    private void fillFields(){
        nameField.setText(userInfo.getName());
        surnameField.setText(userInfo.getSurname());
        emailField.setText(userInfo.getEmail());
        phoneField.setText(userInfo.getPhone());
        passwordField.setText(userInfo.getPassword());
        birthDateField.setValue(userInfo.getBirthDate());
    }



    public void save(ActionEvent actionEvent) {
        userInfo.setName(nameField.getText());
        userInfo.setSurname(surnameField.getText());
        userInfo.setEmail(emailField.getText());
        userInfo.setPhone(phoneField.getText());
        userInfo.setPassword(passwordField.getText());
        userInfo.setBirthDate(birthDateField.getValue());

        userHib.updateUser(userInfo);

        openHomePage();
    }

    public void cancel(ActionEvent actionEvent) {
        openHomePage();
    }

    public void deleteAccount(ActionEvent actionEvent) {
        userHib.deleteUser(userInfo);
        openLoginPage();
    }

    private void openLoginPage(){
        try {
            FXutils.openLoginPage((Stage)nameField.getScene().getWindow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openHomePage(){
        FXutils.openHomeView((Stage)nameField.getScene().getWindow(), emf, currentUser, previousTab);
    }
}
