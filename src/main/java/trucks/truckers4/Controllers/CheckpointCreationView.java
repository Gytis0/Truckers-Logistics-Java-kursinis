package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.annotations.Check;
import trucks.truckers4.Model.Checkpoint;
import trucks.truckers4.Model.Truck;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.CheckpointHib;
import trucks.truckers4.hibernate.TruckHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CheckpointCreationView {

    public TextField addressField;
    public DatePicker arrivalField;
    public DatePicker departureField;
    public Button actionButton;
    public Button backButton;
    EntityManagerFactory emf;
    CheckpointHib checkpointHib;
    User user;
    Checkpoint checkpoint;
    private Tab previousTab;

    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        this.user = user;
        checkpointHib = new CheckpointHib(emf);

        previousTab = tab;
    }

    public void setData(EntityManagerFactory emf, User user, Checkpoint checkpoint, Tab tab) {
        this.emf = emf;
        this.user = user;
        checkpointHib = new CheckpointHib(emf);
        this.checkpoint = checkpoint;

        previousTab = tab;

        FillFields(checkpoint);
        actionButton.setOnAction(actionEvent -> {updateCheckpoint(checkpoint);});
        actionButton.setText("Update checkpoint");
        backButton.setOnAction(actionEvent -> {closeWindow();});
    }

    private void updateCheckpoint(Checkpoint checkpoint) {
        checkpoint.setAddress(addressField.getText());
        checkpoint.setArrivalDate(arrivalField.getValue());
        checkpoint.setDepartureDate(departureField.getValue());
        checkpointHib.updateCheckpoint(checkpoint);
        closeWindow();
    }

    private void FillFields(Checkpoint checkpoint) {
        this.addressField.setText(checkpoint.getAddress());
        this.arrivalField.setValue(checkpoint.getArrivalDate());
        this.departureField.setValue(checkpoint.getDepartureDate());
    }

    public void addCheckpoint(ActionEvent actionEvent) {
        Checkpoint checkpoint1 = new Checkpoint(addressField.getText(), arrivalField.getValue(), departureField.getValue());
        if(checkpoint1.isValid()){
            checkpointHib.createCheckpoint(checkpoint1);
            try {
                openHomePage(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Checkpoint created", "Checkpoint was added to the database", "Congratulations!");
        }
        else {
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Checkpoint is missing some information", "Please fill in all the fields.");
        }
    }

    public void openHomePage(ActionEvent actionEvent) throws IOException {
        FXutils.openHomeView((Stage)addressField.getScene().getWindow(), emf, user, previousTab);
    }

    public void closeWindow(){
        addressField.getScene().getWindow().hide();
    }
}
