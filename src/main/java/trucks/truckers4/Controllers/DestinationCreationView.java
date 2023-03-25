package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import trucks.truckers4.Model.*;
import trucks.truckers4.hibernate.*;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public class DestinationCreationView {
    public ComboBox<Manager> managerDropdown;
    public ComboBox<Driver> driverDropdown;
    public ComboBox<Truck> truckDropdown;
    public ComboBox<Cargo> cargoDropdown;
    public ComboBox<Checkpoint> checkpointDropdown;
    public ComboBox<Status> statusDropdown;

    public ListView<Checkpoint> checkpointListView;
    public ListView<Manager> managersListView;

    public Button actionButton;
    public Button backButton;
    EntityManagerFactory emf;

    List<Manager> managersList;
    List<Driver> driverList;
    List<Truck> truckList;
    List<Cargo> cargoList;
    List<Checkpoint> checkpointList;

    UserHib userHib;
    TruckHib truckHib;
    CheckpointHib checkpointHib;
    CargoHib cargoHib;
    DestinationHib destinationHib;

    User user;
    Destination destination;
    private Tab previousTab;

    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        this.user = user;

        userHib = new UserHib(emf);
        truckHib = new TruckHib(emf);
        checkpointHib = new CheckpointHib(emf);
        cargoHib = new CargoHib(emf);
        destinationHib = new DestinationHib(emf);

        previousTab = tab;

        fillDropdowns();
        if(user.getClass() == Manager.class){
            managerDropdown.getSelectionModel().select((Manager) user);
            managersListView.getItems().add(managerDropdown.getSelectionModel().getSelectedItem());
        }
    }

    // used for updating
    public void setData(EntityManagerFactory emf, User user, Destination destination, Tab tab) {
        this.emf = emf;
        this.user = user;

        userHib = new UserHib(emf);
        truckHib = new TruckHib(emf);
        checkpointHib = new CheckpointHib(emf);
        cargoHib = new CargoHib(emf);
        destinationHib = new DestinationHib(emf);
        this.destination = destination;

        previousTab = tab;
        fillDropdowns();
        setDropdowns();
        fillLists();

        checkpointListView.getItems().setAll(destination.getCheckpoint());
        actionButton.setOnAction(actionEvent -> {
            updateDestination(destination);
        });
        actionButton.setText("Update destination");
        backButton.setOnAction(actionEvent -> {
            closeWindow();
        });
    }

    private void fillLists() {
        managersListView.getItems().setAll(destination.getManager());
        checkpointListView.getItems().setAll(destination.getCheckpoint());
    }

    private void setDropdowns() {
        managerDropdown.getSelectionModel().select(destination.getManager().get(0));
        driverDropdown.getSelectionModel().select(destination.getDriver());
        cargoDropdown.getSelectionModel().select(destination.getCargo());
        truckDropdown.getSelectionModel().select(destination.getTruck());
        statusDropdown.getSelectionModel().select(destination.getStatus());
    }

    private void updateDestination(Destination destination) {
        destination.setManager(managersListView.getItems());
        destination.setCargo(cargoDropdown.getValue());
        destination.setDriver(driverDropdown.getValue());
        destination.setTruck(truckDropdown.getValue());
        destination.setCheckpoint(checkpointListView.getItems());
        destination.setStatus(statusDropdown.getValue());
        destinationHib.updateDestination(destination);

        closeWindow();
    }

    public void fillDropdowns() {
        managersList = userHib.getAllManagers();
        driverList = userHib.getAllDrivers();
        truckList = truckHib.getAllTrucks();
        cargoList = cargoHib.getAllCargos();
        checkpointList = checkpointHib.getAllCheckpoints();


        managerDropdown.getItems().setAll(managersList);
        driverDropdown.getItems().setAll(driverList);
        truckDropdown.getItems().setAll(truckList);
        cargoDropdown.getItems().setAll(cargoList);
        checkpointDropdown.getItems().setAll(checkpointList);
        statusDropdown.getItems().setAll(Status.values());
    }

    public void openHomePage(ActionEvent actionEvent) throws IOException {
        FXutils.openHomeView((Stage) driverDropdown.getScene().getWindow(), emf, user, previousTab);
    }

    public void addDestination(ActionEvent actionEvent) {
        Destination destination1 = new Destination(truckDropdown.getValue(), checkpointListView.getItems(), driverDropdown.getValue(), managersListView.getItems(), cargoDropdown.getValue(), Status.PENDING);
        if (destination1.isValid()) {
            destinationHib.createDestination(destination1);
            try {
                openHomePage(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Destination created", "Destination was added to the database", "Congratulations!");
        } else {
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Destination is missing some fields", "Please fill in all the fields.");
        }
    }



    public void closeWindow() {
        driverDropdown.getScene().getWindow().hide();
    }

    public void addCheckpoint(ActionEvent actionEvent) {
        checkpointListView.getItems().add(checkpointDropdown.getSelectionModel().getSelectedItem());
    }

    public void addManager(ActionEvent actionEvent) {
        managersListView.getItems().add(managerDropdown.getSelectionModel().getSelectedItem());
    }

    public void removeManager(ActionEvent actionEvent) {
        managersListView.getItems().remove(managersListView.getSelectionModel().getSelectedIndex());
    }

    public void removeCheckpoint(ActionEvent actionEvent) {
        checkpointListView.getItems().remove(checkpointListView.getSelectionModel().getSelectedIndex());
    }
}