package trucks.truckers4.utils;

import javafx.beans.property.SimpleStringProperty;

public class DriverDestinationTableParameters {
    SimpleStringProperty colId = new SimpleStringProperty();
    SimpleStringProperty colStatus = new SimpleStringProperty();
    SimpleStringProperty colManager = new SimpleStringProperty();
    SimpleStringProperty colCargo = new SimpleStringProperty();
    SimpleStringProperty colDestination = new SimpleStringProperty();
    SimpleStringProperty colDeparture = new SimpleStringProperty();
    SimpleStringProperty colArrival = new SimpleStringProperty();

    public String getColId() {
        return colId.get();
    }

    public SimpleStringProperty colIdProperty() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId.set(colId);
    }

    public String getColStatus() {
        return colStatus.get();
    }

    public SimpleStringProperty colStatusProperty() {
        return colStatus;
    }

    public void setColStatus(String colStatus) {
        this.colStatus.set(colStatus);
    }

    public String getColManager() {
        return colManager.get();
    }

    public SimpleStringProperty colManagerProperty() {
        return colManager;
    }

    public void setColManager(String colManager) {
        this.colManager.set(colManager);
    }

    public String getColCargo() {
        return colCargo.get();
    }

    public SimpleStringProperty colCargoProperty() {
        return colCargo;
    }

    public void setColCargo(String colCargo) {
        this.colCargo.set(colCargo);
    }

    public String getColDestination() {
        return colDestination.get();
    }

    public SimpleStringProperty colDestinationProperty() {
        return colDestination;
    }

    public void setColDestination(String colDestination) {
        this.colDestination.set(colDestination);
    }

    public String getColDeparture() {
        return colDeparture.get();
    }

    public SimpleStringProperty colDepartureProperty() {
        return colDeparture;
    }

    public void setColDeparture(String colDeparture) {
        this.colDeparture.set(colDeparture);
    }

    public String getColArrival() {
        return colArrival.get();
    }

    public SimpleStringProperty colArrivalProperty() {
        return colArrival;
    }

    public void setColArrival(String colArrival) {
        this.colArrival.set(colArrival);
    }
}
