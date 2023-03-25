package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trucks.truckers4.Model.Cargo;
import trucks.truckers4.Model.Status;
import trucks.truckers4.Model.Truck;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.CargoHib;
import trucks.truckers4.hibernate.TruckHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CargoCreationView {
    public TextField nameField;
    public TextField weightField;
    public TextField lengthField;
    public TextField widthField;
    public TextField heightField;
    public Button actionButton;
    public Button backButton;
    Tab previousTab;

    EntityManagerFactory emf;
    CargoHib cargoHib;
    User user;
    Cargo cargo;

    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        this.user = user;
        cargoHib = new CargoHib(emf);

        previousTab = tab;
    }

    public void setData(EntityManagerFactory emf, User user, Cargo cargo, Tab tab) {
        this.emf = emf;
        this.user = user;
        cargoHib = new CargoHib(emf);
        this.cargo = cargo;

        previousTab = tab;

        FillFields(cargo);
        actionButton.setOnAction(actionEvent -> {updateCargo(cargo);});
        actionButton.setText("Update cargo");
        backButton.setOnAction(actionEvent -> {closeWindow();});
    }

    private void updateCargo(Cargo cargo) {
        cargo.setName(nameField.getText());
        cargo.setWeight(Integer.parseInt(weightField.getText()));
        cargo.setLength(Integer.parseInt(lengthField.getText()));
        cargo.setWidth(Integer.parseInt(widthField.getText()));
        cargo.setHeight(Integer.parseInt(heightField.getText()));
        cargoHib.updateCargo(cargo);
        closeWindow();
    }

    private void FillFields(Cargo cargo) {
        this.nameField.setText(cargo.getName());
        this.weightField.setText(String.valueOf(cargo.getWeight()));
        this.lengthField.setText(String.valueOf(cargo.getLength()));
        this.widthField.setText(String.valueOf(cargo.getWidth()));
        this.heightField.setText(String.valueOf(cargo.getHeight()));
    }

    public void addCargo(ActionEvent actionEvent) {
        if(!widthField.getText().equals("") && !weightField.getText().equals("") && !lengthField.getText().equals("") && !heightField.getText().equals("")){
            Cargo cargo = new Cargo(nameField.getText(), Integer.parseInt(weightField.getText()), Integer.parseInt(lengthField.getText()), Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
            if(cargo.isValid()){
                cargoHib.createCargo(cargo);
                try {
                    openHomePage(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FXutils.alertMessage(Alert.AlertType.INFORMATION, "Cargo created", "Cargo was added to the database", "Congratulations!");
                return;
            }
            else{
                FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Cargo is missing some information", "Please fill in all the fields.");
                return;
            }
        }else {
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Cargo is missing some information", "Please fill in all the fields.");
        }
    }

    public void openHomePage(ActionEvent actionEvent) throws IOException {
        FXutils.openHomeView((Stage)nameField.getScene().getWindow(), emf, user, previousTab);
    }

    public void closeWindow(){
        nameField.getScene().getWindow().hide();
    }
}
