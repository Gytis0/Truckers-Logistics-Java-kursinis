package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trucks.truckers4.Model.Truck;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.TruckHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class TruckCreationView {
    public TextField modelField;
    public TextField plateNumberField;
    public TextField weightField;
    public Button actionButton;
    public Button backButton;

    Truck truck;
    EntityManagerFactory emf;
    TruckHib truckHib;
    User user;
    private Tab previousTab;

    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        this.user = user;
        truckHib = new TruckHib(emf);

        previousTab = tab;
    }

    public void setData(EntityManagerFactory emf, User user, Truck truck, Tab tab) {
        this.emf = emf;
        this.user = user;
        truckHib = new TruckHib(emf);
        this.truck = truck;

        previousTab = tab;

        FillFields(truck);
        actionButton.setOnAction(actionEvent -> {updateTruck(truck);});
        actionButton.setText("Update truck");
        backButton.setOnAction(actionEvent -> {closeWindow();});
    }

    public void FillFields(Truck truck){
        this.modelField.setText(truck.getModel());
        this.plateNumberField.setText(truck.getPlateNumber());
        this.weightField.setText(String.valueOf(truck.getWeight()));
    }

    public void addTruck(ActionEvent actionEvent) {
        if(!weightField.getText().equals("")){
            Truck truck1 = new Truck(modelField.getText(), plateNumberField.getText(), Integer.parseInt(weightField.getText()));
            if(truck1.isValid()){
                truckHib.createTruck(truck1);
                try {
                    openHomePage(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FXutils.alertMessage(Alert.AlertType.INFORMATION, "Truck created", "Truck was added to the database", "Congratulations!");
                return;
            }
            else {
                FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Truck is missing some fields", "Please fill in all the fields.");
                return;
            }
        }else {
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Truck is missing some fields", "Please fill in all the fields.");
        }
    }

    public void updateTruck(Truck truck){
        truck.setModel(modelField.getText());
        truck.setPlateNumber(plateNumberField.getText());
        truck.setWeight(Integer.parseInt(weightField.getText()));
        truckHib.updateTruck(truck);
        closeWindow();
    }

    public void openHomePage(ActionEvent actionEvent) throws IOException {
        FXutils.openHomeView((Stage)modelField.getScene().getWindow(), emf, user, previousTab);
    }

    public void closeWindow(){
        modelField.getScene().getWindow().hide();
    }
}
