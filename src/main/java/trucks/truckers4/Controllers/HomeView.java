package trucks.truckers4.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import trucks.truckers4.Model.*;
import trucks.truckers4.hibernate.*;
import trucks.truckers4.utils.DriverDestinationTableParameters;
import trucks.truckers4.utils.FXutils;
import trucks.truckers4.utils.ManagerDestinationTableParameters;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HomeView {

    public ListView<Checkpoint> checkpointList;
    public ListView<Cargo> cargoList;
    public ListView<Truck> truckList;
    public ListView<Forum> forumsList;
    public ListView<User> usersList;


    public Tab homeTab;
    public Tab managerDestinationsTab;
    public Tab driverDestinationsTab;
    public Tab trucksTab;
    public Tab forumsTab;
    public TabPane allTabs;
    public Tab usersTab;

    public LineChart<String, Number> chart;

    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public ChoiceBox<Status> statusDropdown;

    public TableView<ManagerDestinationTableParameters> managersDestinationTable;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsId;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsStatus;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsDriver;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsCargo;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsDestination;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsArrival;
    public TableColumn<ManagerDestinationTableParameters, String> managersDestinationsDeparture;
    ObservableList<ManagerDestinationTableParameters> managerDestinationTableParametersObservableList = FXCollections.observableArrayList();

    public TableView<DriverDestinationTableParameters> driversDestinationTable;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsId;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsStatus;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsManager;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsCargo;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsDestination;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsArrival;
    public TableColumn<DriverDestinationTableParameters, String> driversDestinationsDeparture;
    ObservableList<DriverDestinationTableParameters> driverDestinationTableParametersObservableList = FXCollections.observableArrayList();

    public TableView<ManagerDestinationTableParameters> driversAllDestinationsTable;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsId;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsStatus;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsDriver;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsCargo;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsDestination;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsArrival;
    public TableColumn<ManagerDestinationTableParameters, String> allDriversDestinationsDeparture;

    List<User> allUsers;

    EntityManagerFactory emf;
    UserHib userHib;
    CheckpointHib checkpointHib;
    CargoHib cargoHib;
    TruckHib truckHib;
    DestinationHib destinationHib;
    ForumHib forumHib;
    User user;


    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        this.user = user;
        this.checkpointHib = new CheckpointHib(emf);
        this.cargoHib = new CargoHib(emf);
        this.truckHib = new TruckHib(emf);
        this.destinationHib = new DestinationHib(emf);
        this.forumHib = new ForumHib(emf);
        this.userHib = new UserHib(emf);

        if(user.getClass() == Manager.class){
            statusDropdown.setOnAction(event -> {
                filterManager(null);
            });
        }
        else if(user.getClass() == Admin.class){
            statusDropdown.setOnAction(event -> {
                filterAdmin(null);
            });

            fromDatePicker.setOnAction(event -> {
                filterAdmin(null);
            });

            toDatePicker.setOnAction(event -> {
                filterAdmin(null);
            });
        }


        allUsers = new ArrayList<>();

        managersDestinationTable.setEditable(true);
        driversDestinationTable.setEditable(true);

        setupManagerTable();
        setupDriverTable();
        setupAllDriverTable();

        disableTabs();
        fillLists();

        if (tab != null) {
            allTabs.getSelectionModel().select(tab);
        }
    }

    private void setupManagerTable() {
        managersDestinationsId.setCellValueFactory(new PropertyValueFactory<>("colId"));
        managersDestinationsStatus.setCellValueFactory(new PropertyValueFactory<>("colStatus"));
        managersDestinationsDriver.setCellValueFactory(new PropertyValueFactory<>("colDriver"));
        managersDestinationsCargo.setCellValueFactory(new PropertyValueFactory<>("colCargo"));
        managersDestinationsDestination.setCellValueFactory(new PropertyValueFactory<>("colDestination"));
        managersDestinationsArrival.setCellValueFactory(new PropertyValueFactory<>("colArrival"));
        managersDestinationsDeparture.setCellValueFactory(new PropertyValueFactory<>("colDeparture"));
    }

    private void setupDriverTable() {
        driversDestinationsId.setCellValueFactory(new PropertyValueFactory<>("colId"));
        driversDestinationsStatus.setCellValueFactory(new PropertyValueFactory<>("colStatus"));
        driversDestinationsManager.setCellValueFactory(new PropertyValueFactory<>("colManager"));
        driversDestinationsCargo.setCellValueFactory(new PropertyValueFactory<>("colCargo"));
        driversDestinationsDestination.setCellValueFactory(new PropertyValueFactory<>("colDestination"));
        driversDestinationsArrival.setCellValueFactory(new PropertyValueFactory<>("colArrival"));
        driversDestinationsDeparture.setCellValueFactory(new PropertyValueFactory<>("colDeparture"));
    }

    private void setupAllDriverTable() {
        allDriversDestinationsId.setCellValueFactory(new PropertyValueFactory<>("colId"));
        allDriversDestinationsStatus.setCellValueFactory(new PropertyValueFactory<>("colStatus"));
        allDriversDestinationsDriver.setCellValueFactory(new PropertyValueFactory<>("colDriver"));
        allDriversDestinationsCargo.setCellValueFactory(new PropertyValueFactory<>("colCargo"));
        allDriversDestinationsDestination.setCellValueFactory(new PropertyValueFactory<>("colDestination"));
        allDriversDestinationsArrival.setCellValueFactory(new PropertyValueFactory<>("colArrival"));
        allDriversDestinationsDeparture.setCellValueFactory(new PropertyValueFactory<>("colDeparture"));
    }


    private void fillLists() {
        if (user.getClass() == Manager.class) {
            Manager manager = userHib.getManagerById(user.getId());

            addToManagerTable(manager.getInDestinations());

            checkpointList.getItems().setAll(checkpointHib.getAllCheckpoints());
            cargoList.getItems().setAll(cargoHib.getAllCargos());
            truckList.getItems().setAll(truckHib.getAllTrucks());

            statusDropdown.getItems().setAll(Status.values());
        } else if (user.getClass() == Driver.class) {
            Driver driver = userHib.getDriverById(user.getId());
            List<Destination> myDestinations = driver.getInDestinations();
            addToDriverTable(driver);

            List<Destination> otherDestinations = destinationHib.getAllDestinations();
            otherDestinations.removeAll(myDestinations);
            addToAllDriversTable(otherDestinations);
        } else if (user.getClass() == Admin.class) {
            addToManagerTable(destinationHib.getAllDestinations());
            checkpointList.getItems().setAll(checkpointHib.getAllCheckpoints());
            cargoList.getItems().setAll(cargoHib.getAllCargos());
            truckList.getItems().setAll(truckHib.getAllTrucks());

            allUsers.clear();
            allUsers.addAll(userHib.getAllDrivers());
            allUsers.addAll(userHib.getAllManagers());
            usersList.getItems().setAll(allUsers);

            statusDropdown.getItems().setAll(Status.values());

        }

        forumsList.getItems().setAll(forumHib.getAllForums());
    }

    private void disableTabs() {
        if (user.getClass() == Driver.class) {
            allTabs.getTabs().remove(managerDestinationsTab);
            allTabs.getTabs().remove(trucksTab);
            allTabs.getTabs().remove(usersTab);
        } else if (user.getClass() == Manager.class) {
            allTabs.getTabs().remove(driverDestinationsTab);
            allTabs.getTabs().remove(usersTab);
        } else if (user.getClass() == Admin.class) {
            allTabs.getTabs().remove(driverDestinationsTab);
        }
    }

    private void addToManagerTable(List<Destination> destinations) {
        managersDestinationTable.getItems().clear();
        for (Destination d : destinations) {
            ManagerDestinationTableParameters params = new ManagerDestinationTableParameters();

            params.setColId(String.valueOf(d.getId()));
            params.setColStatus(String.valueOf(d.getStatus()));
            params.setColDriver(String.valueOf(d.getDriver()));
            params.setColCargo(String.valueOf(d.getCargo()));
            params.setColDestination(String.valueOf(d.getLastCheckpoint().getAddress()));
            params.setColDeparture(String.valueOf(d.getFirstCheckpoint().getDepartureDate()));
            params.setColArrival(String.valueOf(d.getLastCheckpoint().getArrivalDate()));
            managerDestinationTableParametersObservableList.add(params);
        }

        managersDestinationTable.setItems(managerDestinationTableParametersObservableList);
    }

    private void addToDriverTable(Driver driver) {
        driversDestinationTable.getItems().clear();

        List<Destination> destinations = driver.getInDestinations();
        for (Destination d : destinations) {
            DriverDestinationTableParameters params = new DriverDestinationTableParameters();

            params.setColId(String.valueOf(d.getId()));
            params.setColStatus(String.valueOf(d.getStatus()));
            params.setColManager(String.valueOf(d.getManager()));
            params.setColCargo(String.valueOf(d.getCargo()));
            params.setColDestination(String.valueOf(d.getLastCheckpoint().getAddress()));
            params.setColDeparture(String.valueOf(d.getFirstCheckpoint().getDepartureDate()));
            params.setColArrival(String.valueOf(d.getLastCheckpoint().getArrivalDate()));
            driverDestinationTableParametersObservableList.add(params);
        }

        driversDestinationTable.setItems(driverDestinationTableParametersObservableList);
    }

    private void addToAllDriversTable(List<Destination> destinations) {
        driversAllDestinationsTable.getItems().clear();

        for (Destination d : destinations) {
            ManagerDestinationTableParameters params = new ManagerDestinationTableParameters();

            params.setColId(String.valueOf(d.getId()));
            params.setColStatus(String.valueOf(d.getStatus()));
            params.setColDriver(String.valueOf(d.getDriver()));
            params.setColCargo(String.valueOf(d.getCargo()));
            params.setColDestination(String.valueOf(d.getLastCheckpoint().getAddress()));
            params.setColDeparture(String.valueOf(d.getFirstCheckpoint().getDepartureDate()));
            params.setColArrival(String.valueOf(d.getLastCheckpoint().getArrivalDate()));
            managerDestinationTableParametersObservableList.add(params);
        }

        driversAllDestinationsTable.setItems(managerDestinationTableParametersObservableList);
    }

    // Home

    public void disconnect(ActionEvent actionEvent) {
        user = null;
        try {
            FXutils.openLoginPage((Stage) allTabs.getScene().getWindow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Destination
    public void openDestinationCreationScreen(ActionEvent actionEvent) throws IOException {
        FXutils.openDestinationCreationPage((Stage) forumsList.getScene().getWindow(), emf, user, managerDestinationsTab);
    }

    public void updateDestination(ActionEvent actionEvent) throws IOException {
        FXutils.openDestinationUpdatePage(emf, user, getSelectedDestinationManagerList() , managerDestinationsTab);
    }

    public void viewManagerDestination(ActionEvent actionEvent) throws IOException {
        FXutils.openDestinationView(getSelectedDestinationManagerList());
    }

    public void viewDriverDestination(ActionEvent actionEvent) throws IOException {
        FXutils.openDestinationView(getSelectedDestinationDriverList());
    }

    public void viewOtherDriversDestination(ActionEvent actionEvent) throws IOException {
        FXutils.openDestinationView(getSelectedDestinationAllDriversList());
    }

    public void markAsArrived(ActionEvent actionEvent) {
        Destination destination = getSelectedDestinationDriverList();
        destination.setAsDelivered();

        destinationHib.updateDestination(destination);
        fillLists();
    }

    public void deleteDestination(ActionEvent actionEvent) {
        Destination destination = getSelectedDestinationManagerList();
        if (destination.getStatus() == Status.ENROUTE) {
            FXutils.alertMessage(Alert.AlertType.ERROR, "Can't perform action", "Can't delete this destination", "Active destinations cannot be deleted");
        } else {
            destinationHib.deleteDestination(destination);
            //fillLists();
        }
    }

    private Destination getSelectedDestinationManagerList() {
        int selectedItemIndex = managersDestinationTable.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(managersDestinationTable.getItems().get(selectedItemIndex).getColId());

        Destination destination;
        if(user.getClass() == Manager.class){
            Manager manager = userHib.getManagerById(user.getId());
            destination = manager.findDestination(id);
        }
        else{
            destination = findDestination(destinationHib.getAllDestinations(), id);
        }

        return destination;
    }

    private Destination getSelectedDestinationDriverList() {
        int selectedItemIndex = driversDestinationTable.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(driversDestinationTable.getItems().get(selectedItemIndex).getColId());

        Driver driver = (Driver) user;
        Destination destination =  driver.findDestination(id);

        return destination;
    }

    private Destination getSelectedDestinationAllDriversList() {
        int selectedItemIndex = driversAllDestinationsTable.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(driversAllDestinationsTable.getItems().get(selectedItemIndex).getColId());

        Destination destination = destinationHib.getDestinationById(id);
        return destination;
    }

    public void refreshChart(MouseEvent mouseEvent) {
        chart.getData().clear();
        Destination destination = getSelectedDestinationManagerList();

        // Checkpoint address, days to complete it
        //defining the axes
        final Axis<String> xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Addresses");

        //creating the chart
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);

        //defining a series
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Days");

        //populating the series with data
        List<Checkpoint> checkpoints = destination.getCheckpoint();
        int size = checkpoints.size();
        for(int i = 0; i < size-1; i++){
            series.getData().add(new XYChart.Data<String, Number>(checkpoints.get(i+1).getAddress(), ChronoUnit.DAYS.between(checkpoints.get(i).getDepartureDate(), checkpoints.get(i+1).getArrivalDate() )));
        }

        //Displaying
        chart.getData().add(series);
    }
    // Truck
    public void openTruckCreationScreen(ActionEvent actionEvent) throws IOException {
        FXutils.openTruckCreationPage((Stage) forumsList.getScene().getWindow(), emf, user, trucksTab);
    }

    public void viewTruck(ActionEvent actionEvent) throws IOException {
        FXutils.openTruckView(truckList.getSelectionModel().getSelectedItem());
    }

    public void updateTruck(ActionEvent actionEvent) throws IOException {
        FXutils.openTruckUpdatePage(emf, user, truckList.getSelectionModel().getSelectedItem(), trucksTab);
    }

    public void deleteTruck(ActionEvent actionEvent) {
        truckHib.deleteTruck(truckList.getSelectionModel().getSelectedItem());
        fillLists();
    }

    // Checkpoint
    public void openCheckpointCreationScreen(ActionEvent actionEvent) throws IOException {
        FXutils.openCheckpointCreationScreen((Stage) forumsList.getScene().getWindow(), emf, user, managerDestinationsTab);
    }

    public void updateCheckpoint(ActionEvent actionEvent) throws IOException {
        FXutils.openCheckpointUpdateScreen(emf, user, checkpointList.getSelectionModel().getSelectedItem(), managerDestinationsTab);
    }

    public void deleteCheckpoint(ActionEvent actionEvent) {
        checkpointHib.deleteCheckpoint(checkpointList.getSelectionModel().getSelectedItem());
        fillLists();
    }

    // Cargo
    public void openCargoCreationScreen(ActionEvent actionEvent) throws IOException {
        FXutils.openCargoCreationPage((Stage) allTabs.getScene().getWindow(), emf, user, trucksTab);
    }

    public void updateCargo(ActionEvent actionEvent) throws IOException {
        FXutils.openCargoUpdatePage(emf, user, cargoList.getSelectionModel().getSelectedItem(), trucksTab);
    }

    public void viewCargo(ActionEvent actionEvent) throws IOException {
        FXutils.openCargoView(cargoList.getSelectionModel().getSelectedItem());
    }

    public void deleteCargo(ActionEvent actionEvent) {
        cargoHib.deleteCargo(cargoList.getSelectionModel().getSelectedItem());
        fillLists();
    }

    public void refresh(ActionEvent actionEvent) {
        fillLists();
    }

    // Forums

    public void viewPost(MouseEvent keyEvent) throws IOException {
        FXutils.openPostViewPage((Stage) allTabs.getScene().getWindow(), emf, user, (Forum) forumsList.getSelectionModel().getSelectedItem(), forumsTab);
    }

    public void openPostCreationScreen(ActionEvent actionEvent) throws IOException {
        FXutils.openPostCreationPage((Stage) allTabs.getScene().getWindow(), emf, user, forumsTab);
    }


    // Users

    public void createNewUser(ActionEvent actionEvent) {
        FXutils.openRegistrationPage((Stage) forumsList.getScene().getWindow(), emf, usersTab);
    }

    public void updateUser(ActionEvent actionEvent) {
        FXutils.openMyAccountPage((Stage) forumsList.getScene().getWindow(), emf, user, (User) usersList.getSelectionModel().getSelectedItem(), usersTab);
    }

    public void deleteUser(ActionEvent actionEvent) {
        userHib.deleteUser((User) usersList.getSelectionModel().getSelectedItem());
        fillLists();
    }

    public void openMyAccountPage(ActionEvent actionEvent) {
        FXutils.openMyAccountPage((Stage) forumsList.getScene().getWindow(), emf, user, null);
    }

    // Utils

    public void filterManager(ActionEvent actionEvent) {
        Manager manager = (Manager) user;
        if(toDatePicker.getValue()!= null && fromDatePicker.getValue() != null && statusDropdown.getValue() != null){
            addToManagerTable(getDestinationsWithFilter(manager.getInDestinations() , fromDatePicker.getValue().minus(1, ChronoUnit.DAYS), toDatePicker.getValue().plus(1, ChronoUnit.DAYS), statusDropdown.getValue()));
        }
        else if(toDatePicker.getValue()!= null && fromDatePicker.getValue() != null){
            addToManagerTable(getDestinationsWithFilter(manager.getInDestinations(), fromDatePicker.getValue().minus(1, ChronoUnit.DAYS), toDatePicker.getValue().plus(1, ChronoUnit.DAYS)));
        }
        else if(statusDropdown.getValue() != null){
            addToManagerTable(getDestinationsWithFilter(manager.getInDestinations(), statusDropdown.getValue()));
        }
        else{
            addToManagerTable(manager.getInDestinations());
        }
    }

    public void filterAdmin(ActionEvent actionEvent) {
        List<Destination> allDestinations = destinationHib.getAllDestinations();
        if(toDatePicker.getValue()!= null && fromDatePicker.getValue() != null && statusDropdown.getValue() != null){
            addToManagerTable(getDestinationsWithFilter(allDestinations , fromDatePicker.getValue().minus(1, ChronoUnit.DAYS), toDatePicker.getValue().plus(1, ChronoUnit.DAYS), statusDropdown.getValue()));
        }
        else if(toDatePicker.getValue()!= null && fromDatePicker.getValue() != null){
            addToManagerTable(getDestinationsWithFilter(allDestinations, fromDatePicker.getValue().minus(1, ChronoUnit.DAYS), toDatePicker.getValue().plus(1, ChronoUnit.DAYS)));
        }
        else if(statusDropdown.getValue() != null){
            addToManagerTable(getDestinationsWithFilter(allDestinations, statusDropdown.getValue()));
        }
        else{
            addToManagerTable(allDestinations);
        }
    }

    public void resetStatus(ActionEvent actionEvent) {
        statusDropdown.getSelectionModel().select(null);
        if(user.getClass() == Manager.class){
            filterManager(null);
        }
        else if(user.getClass() == Admin.class){
            filterAdmin(null);
        }
    }

    public List<Destination> getDestinationsWithFilter(List<Destination> destinations, LocalDate from, LocalDate to, Status status){
        List<Destination> result = new ArrayList<>();

        for(Destination d : destinations){
            if(d.getFirstCheckpoint().getDepartureDate().isAfter(from) && d.getLastCheckpoint().getArrivalDate().isBefore(to) && d.getStatus().equals(status)){
                result.add(d);
            }
        }
        return result;
    }

    public List<Destination> getDestinationsWithFilter(List<Destination> destinations,LocalDate from, LocalDate to){
        List<Destination> result = new ArrayList<>();

        for(Destination d : destinations){
            if(d.getFirstCheckpoint().getDepartureDate().isAfter(from) && d.getLastCheckpoint().getArrivalDate().isBefore(to)){
                result.add(d);
            }
        }
        return result;
    }

    public List<Destination> getDestinationsWithFilter(List<Destination> destinations,Status status){
        List<Destination> result = new ArrayList<>();

        for(Destination d : destinations){
            if(d.getStatus().equals(status)){
                result.add(d);
            }
        }
        return result;
    }

    public Destination findDestination(List<Destination> destinations, int id){
        for(Destination d : destinations){
            if(d.getId() == id){
                return d;
            }
        }
        return null;
    }
}